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
package com.braintribe.transport.ftp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.braintribe.logging.Logger;

import junit.framework.TestCase;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from "/applicationContext.xml"
//in the root of the classpath
@ContextConfiguration(locations={"/TestContext.xml"})
@Ignore
public class FtpBatchUploaderTester extends TestCase {
	static Logger logger = Logger.getLogger(FtpBatchUploaderTester.class);
	
	@Rule
    public TemporaryFolder folder= new TemporaryFolder();
	
	@Autowired
	protected FtpBatchUploader uploader;
	
	Set<File> clientFolders = new HashSet<>();
	HashMap<String,Integer> filesInClientFolders = new HashMap<>();
	
	/**
	 * @throws IOException 
	 * @throws ConnectionException 
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws IOException, ConnectionException, Exception {
		//foo
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		//bar
	}

	@Test
	public void downloadTestData() throws Exception {
		//TOOD check remotely if there are files
		this.uploader.executeAll();
	}

		
	
/*
 * Getters and Setters
 */
	public void setFtpPoller(FtpBatchUploader arg) {
		this.uploader = arg;
	}
}
