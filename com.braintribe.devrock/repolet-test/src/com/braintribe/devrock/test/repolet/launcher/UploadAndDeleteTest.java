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
package com.braintribe.devrock.test.repolet.launcher;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;

import com.braintribe.devrock.repolet.AbstractRepolet;
import com.braintribe.devrock.repolet.launcher.builder.api.LauncherCfgBuilderContext;
import com.braintribe.devrock.test.repolet.launcher.utils.TestUtils;

public class UploadAndDeleteTest extends AbstractLauncherTest {
	private File initial = new File( getRoot(), "initial");
	private File uploads = new File( initial, "uploads");
	private File target = new File( getRoot(), "uploads");
	
	{
		launcher = LauncherCfgBuilderContext.build()
				.repolet()
					.name("archiveA")							
					.filesystem()
						.filesystem( new File( initial, "remoteRepoA"))
					.close()
					.uploadFilesystem()
						.filesystem( new File( target, "remoteRepoA"))
					.close()
				.close()
				.repolet()
					.name("archiveB")					
					.filesystem()
						.filesystem( new File( initial, "remoteRepoB"))
					.close()
					.uploadFilesystem()
						.filesystem( new File( target, "remoteRepoB"))
					.close()
				.close()
			.done();				
	}
	
	@Override
	protected File getRoot() {		
		return new File( res, "upload.folder");
	}
	
	
	@Override
	protected void runBeforeBefore() {
		TestUtils.ensure(target);	
	}



	private void test(File fileToUpload) {	
		Path path = fileToUpload.toPath();
		Path uploadPath = uploads.toPath();
		Path relPath = uploadPath.relativize(path);
		String relPathAsString = relPath.toString().replace("\\", "/");		
				
		Map<String, String> launchedRepolets = launcher.getLaunchedRepolets();
		System.out.println();
		
		CloseableHttpClient client = client();
		HttpPut httpPut = new HttpPut( launchedRepolets.get( "archiveA") + "/" + relPathAsString);
		FileEntity fileEntity = new FileEntity( fileToUpload);
				
		Map<String, String> hashes = AbstractRepolet.generateHash( fileToUpload, Arrays.asList("sha1", "md5", "SHA-256"));		
		httpPut.setHeader("X-Checksum-Sha1", hashes.get("sha1"));
		httpPut.setHeader("X-Checksum-MD5", hashes.get("md5"));
		httpPut.setHeader("X-Checksum-SHA256", hashes.get("SHA-256"));
		
		httpPut.setEntity( fileEntity);
		int code = -1;
		try {
			CloseableHttpResponse response = client.execute(httpPut);
			code = response.getStatusLine().getStatusCode();
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("exception [" + e.getMessage() + "] thrown");
			return;
		} 
		Assert.assertTrue("unexpected return code [" + code + "] returned", code == 200);		
	}
	@Test
	public void test() {
		test( new File( uploads, "com/braintribe/devrock/test/artifact/1.0/artifact-1.0.pom"));
	}
	
	

}
