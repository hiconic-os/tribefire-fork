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
package com.braintribe.devrock.commands.zed;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.zed.api.comparison.SemanticVersioningLevel;
import com.braintribe.devrock.zed.api.comparison.ZedComparison;
import com.braintribe.devrock.zed.core.comparison.BasicComparator;
import com.braintribe.devrock.zed.ui.comparison.ZedComparisonResultViewer;
import com.braintribe.devrock.zed.ui.comparison.ZedComparisonTargetSelector;
import com.braintribe.devrock.zed.ui.comparison.ZedComparisonViewerContext;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.FingerPrint;

public class RunComparisonCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		// Dialog to select analysis target	
		Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay());

		ZedComparisonTargetSelector selector = new ZedComparisonTargetSelector(shell);
		int retval = selector.open();
		if (retval == org.eclipse.jface.dialogs.Dialog.CANCEL) {
			return null;
		}
		
		Pair<Artifact,Artifact> pair = selector.getSelectedExtractions();
		SemanticVersioningLevel semanticLevel = selector.getSelectedSemanticLevel();
		
		System.out.println(pair.getFirst().asString() + " -> " + pair.getSecond().asString());
		
		ZedComparison comparator = new BasicComparator();
		boolean comparisonResult = comparator.compare( pair.getFirst(), pair.getSecond());
		List<FingerPrint> fingerPrints = comparator.getComparisonContext().getFingerPrints();
		if (!comparisonResult) {
			System.out.println("number of issues found during comparison : " + fingerPrints.size());
		}
		
		// viewer
		
		ZedComparisonResultViewer resultViewer = new ZedComparisonResultViewer( shell);
		ZedComparisonViewerContext context = new ZedComparisonViewerContext();
		context.setBaseArtifact( pair.first);
		context.setOtherArtifact( pair.second);
		context.setFingerPrints(fingerPrints);
		context.setSemanticComparisonLevel(semanticLevel);
		resultViewer.setContext(context);
		resultViewer.open();
		
		return null;
	}
	
	

}
