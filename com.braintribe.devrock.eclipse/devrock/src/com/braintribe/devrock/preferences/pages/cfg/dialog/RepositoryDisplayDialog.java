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
package com.braintribe.devrock.preferences.pages.cfg.dialog;

import java.io.ByteArrayOutputStream;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.marshaller.yaml.YamlMarshaller;
import com.braintribe.devrock.model.repository.Repository;

public class RepositoryDisplayDialog extends Dialog {
	public static final int SHELL_STYLE = SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.APPLICATION_MODAL;
	private Font bigFont;
	private Shell parentShell;	
	private Repository repository;
	
	private YamlMarshaller marshaller;
	{
		marshaller = new YamlMarshaller();
		marshaller.setWritePooled(true);
	}
		
	
	
	public RepositoryDisplayDialog(Shell parentShell) {
		super(parentShell);
		this.parentShell = parentShell;
		
		setShellStyle(SHELL_STYLE);
				
	}
	
	@Configurable @Required
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	@Override
	protected Control createDialogArea(Composite parent) {	
		Font initialFont = parent.getFont();
		FontData [] fontDataBig = initialFont.getFontData();
		for (FontData data : fontDataBig) {
			data.setHeight( data.getHeight() + (data.getHeight() / 5));				
		}
		bigFont = new Font( getShell().getDisplay(), fontDataBig);
		
		initializeDialogUnits(parent);
		final Composite composite = new Composite(parent, SWT.NONE);

		int nColumns= 4;
        GridLayout layout= new GridLayout();
        layout.numColumns = nColumns;
        composite.setLayout( layout);
        composite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true));
        
        Label label = new Label( composite, SWT.NONE);
        label.setText("Backing resolution");
        label.setFont(bigFont);
        label.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false, 4,1));
     
        
		Composite browserComposite = new Composite(composite, SWT.NONE);
		browserComposite.setLayout( new FillLayout());
		browserComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true));

		// add data here as text entry
		Browser reasonText = new Browser( browserComposite, SWT.NONE);		
		
		String yamlDump;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			marshaller.marshall(out, repository);
			yamlDump = out.toString("UTF-8");
		} catch (Exception e) {
			yamlDump = e.getMessage();			
		}
						
		reasonText.setText("<pre>" +  yamlDump + "</pre>");

		
		parentShell.layout(true);
		return composite;
	}
	

	@Override
	protected Point getInitialSize() {		
		return new Point( 500, 600);
	}


	@Override
	public boolean close() {
		bigFont.dispose();		
		return super.close();
	}

}
