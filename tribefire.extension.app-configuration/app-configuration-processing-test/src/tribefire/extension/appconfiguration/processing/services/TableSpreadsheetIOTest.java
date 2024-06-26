// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package tribefire.extension.appconfiguration.processing.services;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static tribefire.extension.appconfiguration.processing.services.GeneralTools.list;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.testing.test.AbstractTest;
import com.braintribe.utils.lcd.StringTools;

/**
 * Provides tests for {@link TableSpreadsheetIO}.
 */
public class TableSpreadsheetIOTest extends AbstractTest {

	private static final int EMPTY_TEST_ROW_INDEX = 2;
	private static final int EMPTY_TEST_COLUMN_INDEX = 3;

	/**
	 * Reads from a simple test spreadsheet.
	 */
	@Test
	public void testReadSimple() {
		File workbookFile = testFile("simple.xlsx");

		Maybe<Table> maybeTable;

		try (FileInputStream fileInputStream = new FileInputStream(workbookFile); Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			maybeTable = TableSpreadsheetIO.readTableFromSheet(workbook, TableSpreadsheetIO.DEFAULT_SHEET_NAME, Table.class);
		} catch (IOException e) {
			throw new UncheckedIOException("Error while reading from file " + workbookFile + "!", e);
		}

		logger.info("Read table from workbook file " + workbookFile + ".");

		assertThat(maybeTable.isSatisfied()).isTrue();

		Table actualTable = maybeTable.get();

		assertThat(actualTable).isNotNull();

		logger.info("Table:\n" + actualTable.toMultilineString());

		Table expectedTable = new Table();
		expectedTable.addCellsToNewRow(list("Key", "us", "ge"));
		expectedTable.addCellsToNewRow(list("key_thing", "thing", "ding"));
		expectedTable.addCellsToNewRow(list("key_time", "time", "zeit"));
		expectedTable.addCellsToNewRow(list("key_color", "color", null));

		assertThat(actualTable.toMultilineString()).isEqualToWithVerboseErrorMessageAndLogging(expectedTable.toMultilineString());
		assertThat(actualTable).isEqualTo(expectedTable);
	}

	/**
	 * Verifies that there are proper error messages for unsupported cell types (such as dates or formulas).
	 */
	@Test
	public void testReadWithErrorMessagesForUnsupportedTypes() {
		File workbookFile = testFile("unsupported-types.xlsx");

		Maybe<Table> maybeTable;

		try (FileInputStream fileInputStream = new FileInputStream(workbookFile); Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			maybeTable = TableSpreadsheetIO.readTableFromSheet(workbook, TableSpreadsheetIO.DEFAULT_SHEET_NAME, Table.class);
		} catch (IOException e) {
			throw new UncheckedIOException("Error while reading from file " + workbookFile + "!", e);
		}

		logger.info("Read table from workbook file " + workbookFile + ".");

		assertThat(maybeTable.value()).isNotNull();
		assertThat(maybeTable.isUnsatisfied()).isTrue();

		String reasonString = maybeTable.whyUnsatisfied().asFormattedText();
		logger.info("(Expectedly) found some errors:\n" + reasonString);

		assertThat(reasonString).containsAll( //
				"Cell B5 has unsupported type 'BOOLEAN'.", //
				"Cell B6 has unsupported type 'FORMULA'.", //
				"Cell B7 has unsupported type 'FORMULA'.");
	}

	/**
	 * Generates a {@link Table} with random data, {@link TableSpreadsheetIO#addTableToNewSheet(Workbook, String, Table)
	 * writes} that table to a test file, {@link TableSpreadsheetIO#readTableFromSheet(Workbook, String, Class) reads} the
	 * table again and asserts that the two tables are equal.
	 */
	@Test
	public void testWriteAndRead() {
		Table table = generateTestTable();

		logger.info("Created table with test data:\n\n" + table.toMultilineString());

		File workbookFile = testFile("generated.xlsx");

		logger.info("Writing table to workbook file " + workbookFile + ".");

		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOutputStream = new FileOutputStream(workbookFile)) {
			TableSpreadsheetIO.addTableToNewSheet(workbook, TableSpreadsheetIO.DEFAULT_SHEET_NAME, table);
			// set value to EMPTY, but only in workbook, not in table (because the table is used in asserts below)
			workbook.getSheet(TableSpreadsheetIO.DEFAULT_SHEET_NAME).getRow(EMPTY_TEST_ROW_INDEX).getCell(EMPTY_TEST_COLUMN_INDEX)
					.setCellValue(TableSpreadsheetIO.EMPTY);
			workbook.write(fileOutputStream);
		} catch (IOException e) {
			throw new UncheckedIOException("Error while writing to file " + workbookFile + "!", e);
		}

		logger.info("Wrote table to workbook file " + workbookFile + ".");

		logger.info("Reading table from workbook file " + workbookFile + ".");

		Maybe<Table> maybeTable;

		try (FileInputStream fileInputStream = new FileInputStream(workbookFile); Workbook workbook = new XSSFWorkbook(fileInputStream)) {
			maybeTable = TableSpreadsheetIO.readTableFromSheet(workbook, TableSpreadsheetIO.DEFAULT_SHEET_NAME, Table.class);
		} catch (IOException e) {
			throw new UncheckedIOException("Error while reading from file " + workbookFile + "!", e);
		}

		logger.info("Read table from workbook file " + workbookFile + ".");

		assertThat(maybeTable.isSatisfied()).isTrue();
		Table actualTable = maybeTable.get();
		assertThat(actualTable.toMultilineString()).isEqualToWithVerboseErrorMessageAndLogging(table.toMultilineString());
		assertThat(actualTable).isEqualTo(table);
		assertThat(actualTable.cell(EMPTY_TEST_ROW_INDEX, EMPTY_TEST_COLUMN_INDEX)).isEmpty();
	}

	/**
	 * Generates a {@link Table} filled with random data where cell values are strings of various lengths, sometimes also
	 * multiline strings.
	 */
	private static Table generateTestTable() {
		Table table = new Table();
		Random random = new Random(0);
		int columnCount = 5;
		int rowCount = 10;

		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			List<String> row = new ArrayList<>();
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				StringBuilder builder = new StringBuilder();
				if (rowIndex == EMPTY_TEST_ROW_INDEX && columnIndex == EMPTY_TEST_COLUMN_INDEX) {
					// the expected value AFTER writing to workbook and reading again from workbook
					builder.append("");
				} else {
					builder.append("Cell " + rowIndex + "." + columnIndex + " - " + StringTools.getFilledString(random.nextInt(1, 10), 'x'));
					while (random.nextBoolean()) {
						builder.append("\nadditional line");
					}
				}

				row.add(builder.toString());
			}
			table.addCellsToNewRow(row);
		}
		return table;
	}
}
