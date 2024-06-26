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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.braintribe.logging.Logger;
import com.braintribe.model.ftp.BatchFtpJob;
import com.braintribe.model.ftp.FtpConnection;

import junit.framework.TestCase;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from "/applicationContext.xml"
//in the root of the classpath
@ContextConfiguration(locations={"/TestContext.xml"})
@Ignore
public class FtpBatchDownloaderTester extends TestCase {
	static Logger logger = Logger.getLogger(FtpBatchDownloaderTester.class);
	
	@Rule
    public TemporaryFolder folder= new TemporaryFolder();
	
	@Autowired
	protected FtpBatchDownloader poller;
	
	Set<File> clientFolders = new HashSet<>();
	HashMap<String,Integer> filesInClientFolders = new HashMap<>();
	
	/**
	 * @throws IOException 
	 * @throws ConnectionException 
	 * @throws java.lang.Exception
	 */
//	@Before //FIXME setup for junit foldes does not work
	@Override
	public void setUp() throws IOException, ConnectionException, Exception {
		this.folder.create();
		//override job definition and put downloaded files in temporary folder
		for (BatchFtpJob job : this.poller.getJobs()) {
			String remotePath = job.getRemotePath();
			File localDirectory= this.folder.newFolder(remotePath);
			job.setLocalPath( localDirectory.getAbsolutePath() );
			this.clientFolders.add(localDirectory);
		}

		/*
		 * look how many files are there for download
		 */
		for (BatchFtpJob job : this.poller.getJobs()) {
			FtpConnection connection = job.getConnection();
			FtpConnector ftp = new FtpConnector(connection);
			if (ftp.connect() == false)
				throw new ConnectionException(connection,String.format("could not establish connection.id '%s' to '%s', reply was '%s'.", connection.getId(), connection.getHost(), ftp.getReply()));
			ftp.changeWorkingDirectory("/");
			
			if (!ftp.changeWorkingDirectory(job.getRemotePath()))
				throw new Exception(String.format("could not change to directory '%s'", job.getRemotePath()));
			
			List<FTPFile> files = new LinkedList<>();
			List<FTPFile> folders = new LinkedList<>();

			for (FTPFile f : ftp.listFiles())
				if (f.isFile() && f.getName() != null)
					files.add(f);
				else
					folders.add(f);

			this.filesInClientFolders.put(job.getRemotePath(), files.size());
			ftp.disconnect();
		}		
	
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
		this.poller.executeAll();
		
		for (File f : this.clientFolders)
			//check for the correct number of children
			assertTrue( f.list().length == this.filesInClientFolders.get(f.getName()) );
	}

		
	
/*
 * Getters and Setters
 */
	public void setFtpPoller(FtpBatchDownloader ftpPoller) {
		this.poller = ftpPoller;
	}
}
