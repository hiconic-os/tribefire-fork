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
package com.braintribe.devrock.zed.forensics.fingerprint.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;

/**
 * simple dumper for finger prints 
 * 
 * @author pit
 *
 */
public class FingerPrintDumper {
	private static FingerPrintMarshaller marshaller = new FingerPrintMarshaller();

	public static void dump( File contents, Artifact terminalArtifact, Map<FingerPrint, ForensicsRating> ratedCurrentFingerPrints) {
		FingerprintOverrideContainer fpovrc = new FingerprintOverrideContainer();
		fpovrc.setFingerprintOverrideRatings(ratedCurrentFingerPrints);		
		
		String terminalName = terminalArtifact.toVersionedStringRepresentation();
		terminalName = terminalName.replace( ':', '.');
		String name = terminalName + ".fpr.txt";
		File output = new File( contents, name);
		output.getParentFile().mkdirs();
		try (OutputStream out = new FileOutputStream( output)) {
			marshaller.marshall(out, fpovrc);
		}
		catch (Exception e) {
			throw new IllegalStateException("boink during safe", e);
		}
	}
	
	public static Map<FingerPrint, ForensicsRating> load( File contents, Artifact terminalArtifact) {
		String terminalName = terminalArtifact.toVersionedStringRepresentation();
		terminalName = terminalName.replace( ':', '.');
		String name = terminalName + ".fpr.txt";
		File input = new File( contents, name);
		try (InputStream in = new FileInputStream(input)) {
			FingerprintOverrideContainer fpovrc = (FingerprintOverrideContainer) marshaller.unmarshall(in);
			return fpovrc.getFingerprintOverrideRatings();
		} catch (Exception e) {
			throw new IllegalStateException("boink during load", e);
		}
	}
}
