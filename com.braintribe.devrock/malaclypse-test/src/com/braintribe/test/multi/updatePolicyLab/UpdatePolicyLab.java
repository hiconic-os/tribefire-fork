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
package com.braintribe.test.multi.updatePolicyLab;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import com.braintribe.artifacts.test.maven.framework.FakeLocalRepositoryProvider;
import com.braintribe.artifacts.test.maven.framework.FakeMavenSettingsPersistenceExpertImpl;
import com.braintribe.build.artifact.representations.artifact.maven.metadata.MavenMetaDataCodec;
import com.braintribe.build.artifact.test.repolet.LauncherShell;
import com.braintribe.build.artifact.test.repolet.LauncherShell.RepoType;
import com.braintribe.build.artifact.test.repolet.Repolet;
import com.braintribe.build.artifact.test.repolet.ZipBasedSwitchingRepolet;
import com.braintribe.codec.CodecException;
import com.braintribe.model.artifact.Identification;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.meta.MavenMetaData;
import com.braintribe.model.artifact.processing.version.VersionProcessor;
import com.braintribe.test.framework.TestUtil;
import com.braintribe.test.multi.AbstractWalkLab;
import com.braintribe.test.multi.ClashStyle;
import com.braintribe.test.multi.WalkDenotationTypeExpert;
import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

public abstract class UpdatePolicyLab extends AbstractWalkLab {
	private static LauncherShell launcherShell;
	private static File localRepository = new File ("res/updatePolicyLab/contents/repo");
	private static File contents = new File( "res/updatePolicyLab/contents");
	private static final File [] data = new File[] { new File( contents, "archiveBase.zip"), 
													 new File( contents, "archiveUpdate.zip"),													 
	};

	private static List<Repolet> launchedRepolets;	
	
	protected abstract String [] getResultsForFirstRun();
	protected abstract String [] getResultsForSecondRun();
	protected abstract void tweakEnvironment();


	protected static void before( File settings) {
		settingsPersistenceExpert = new FakeMavenSettingsPersistenceExpertImpl( settings);
		localRepositoryLocationProvider = new FakeLocalRepositoryProvider( localRepository);
		localRepository.mkdirs();
		int port = runBefore();
		
		// clean local repository
		TestUtil.delete(localRepository);
			
		// fire them up 
		launchRepolets( port);
				
	}

	private static void launchRepolets(int port) {
		String [] args = new String[1];
		args[0] = 	"archive," + data[0].getAbsolutePath() + "|" + data[1].getAbsolutePath();
		launcherShell = new LauncherShell( port);
		launchedRepolets = launcherShell.launch( args, RepoType.switchZip);
	}
	
	@AfterClass
	public static void after() {
		runAfter();
		launcherShell.shutdown();
	}
	
	@Test
	public void testUpdate() {
		try {
			Solution terminal = Solution.T.create();
			terminal.setGroupId( "com.braintribe.test.dependencies.updatePolicyTest");
			terminal.setArtifactId( "UpdatePolicyTestTerminal");
			terminal.setVersion( VersionProcessor.createFromString( "1.0"));
			
			// first test: set with only #1.0 versions 
			String[] expectedNames = getResultsForFirstRun();
			
			Collection<Solution> result = test( "testupdate", terminal, WalkDenotationTypeExpert.buildCompileWalkDenotationType((ClashStyle.optimistic)), expectedNames,1,0);
			testPresence(result, localRepository);
			testUpdates(result, localRepository);
			
			// let the repolet switch its content to the never versions .. 
			ZipBasedSwitchingRepolet repolet = (ZipBasedSwitchingRepolet) launchedRepolets.get(0);
			repolet.switchContents();
			
			// tweak environment
			tweakEnvironment();
			
			// second test: set with the #1.1 versions 
			expectedNames = getResultsForSecondRun();						
		
			scope.getPersistenceRegistry().clear();
			repositoryRegistry.clear();
			
			result = test( "testupdate", terminal, WalkDenotationTypeExpert.buildCompileWalkDenotationType((ClashStyle.optimistic)), expectedNames,1,0);
			testPresence(result, localRepository);
			testUpdates(result, localRepository);			
			
		} catch (Exception e) {
			Assert.fail( "exception [" + e + "] thrown");
		}
	}
	
	protected void touchUpdateData(Identification identification, final String repoId, Date dateToSetAsLastAccess) {
		File location = new File( localRepository, identification.getGroupId().replace('.',  File.separatorChar) + File.separator + identification.getArtifactId());
		File [] files = location.listFiles( new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.equalsIgnoreCase( "maven-metadata-" + repoId + ".xml"))
					return true;
				return false;
			}
		});

		MavenMetaDataCodec mavenMetaDataCodec = new MavenMetaDataCodec();
		for (File file : files) {
			Document document;
			try {
				document = DomParser.load().from(file);
			} catch (DomParserException e) {
				Assert.fail("cannot read file [" + file.getAbsolutePath() + "]");
				return;
			}
			MavenMetaData metadata;
			try {
				metadata = mavenMetaDataCodec.decode(document);
			} catch (CodecException e) {
				Assert.fail("cannot decode file [" + file.getAbsolutePath() + "]");
				return;
			}
			metadata.getVersioning().setLastUpdated(dateToSetAsLastAccess);
			try {
				document = mavenMetaDataCodec.encode(metadata);
			} catch (CodecException e) {
				Assert.fail("cannot encode file [" + file.getAbsolutePath() + "]");
				return;
			}
			try {
				DomParser.write().from(document).to( file);
			} catch (DomParserException e) {
				Assert.fail("cannot write file [" + file.getAbsolutePath() + "]");
				return;
			}
		}
		
	}
}
