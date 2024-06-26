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
package com.braintribe.devrock.api.ui.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import com.braintribe.cfg.Configurable;

/**
 * lil' helper to create an editor that handles a boolean choice
 * @author pit
 *
 */
public class ColorEditor extends AbstractEditor implements SelectionListener {
	private Color startValue;
	private Color selectedColor;
	private Button choose;
	
	private String labelToolTip;
	private String checkToolTip;
	private SelectionListener selectionListener;
	private Shell shell;
	
	@Configurable
	public void setLabelToolTip(String labelToolTip) {
		this.labelToolTip = labelToolTip;
	}

	@Configurable
	public void setCheckToolTip(String checkToolTip) {
		this.checkToolTip = checkToolTip;
	}
	
	@Configurable
	public void setSelectionListener(SelectionListener selectionListener) {
		this.selectionListener = selectionListener;
	}
	
	public ColorEditor( Shell shell) {
		this.shell = shell;		
	}

	public Composite createControl( Composite parent, String tag) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout(layout);
		
		Label label = new Label( composite, SWT.NONE);
		label.setText( tag);
		
		label.setLayoutData( new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		if (labelToolTip != null) 
			label.setToolTipText(labelToolTip);
		
		choose = new Button( composite, SWT.BORDER);
		choose.setText("    ");
		
		choose.setLayoutData( new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		if (startValue != null) {
			choose.setBackground(startValue);
		}
		
		if (checkToolTip != null) {
			choose.setToolTipText(checkToolTip);
		}
		if (selectionListener != null) {
			choose.addSelectionListener(selectionListener);
		}
		choose.addSelectionListener(this);
	 		
		return composite;
	}
	
	public Color getSelection() {
		return selectedColor;
	}
	
	public void setSelection( Color selection){
		if (choose == null) {
			startValue = selection;
		} else {
			selectedColor = selection;
			choose.setBackground(selection);			
		}
	}
	public Widget getWidget() {
		return choose;
	}
	
	public Button getChoose() {
		return choose;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		if (event.widget == choose) {
			ColorDialog cdlg = new ColorDialog(shell);
			if (selectedColor == null) {
				selectedColor = startValue;
			}
			if (selectedColor != null) {
				cdlg.setRGB( selectedColor.getRGB());
			}
			RGB returnedRGB = cdlg.open();
			if (returnedRGB != null) {
				selectedColor = new Color(returnedRGB);
				choose.setBackground(selectedColor);
			}
		}
	}
		
	
}
