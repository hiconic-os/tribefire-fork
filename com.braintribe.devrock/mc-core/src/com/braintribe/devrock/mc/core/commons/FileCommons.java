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
package com.braintribe.devrock.mc.core.commons;

import java.io.File;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Function;

/**
 * @author pit / dirk
 *
 */
public class FileCommons {
	public static final String MARKER_OUTDATED = "outdated";
	
	public enum FileUpdateStatus {ASIS, MISSING, MARKED, OUTDATED}
	/**
	 * test if file's out-dated (via its time-stamp OR the marker file)
	 * @param file - the {@link File} to test
	 * @param duration - the {@link Duration}, i.e. how long before it goes stale
	 * @return - true if the file was found in need of updating  
	 */
	public static boolean requiresUpdate(File file, Duration duration) {
		return (requiresUpdateReflected(file, duration, false) != FileUpdateStatus.ASIS);
	}
	/**
	 * test if file's out-dated (via its time-stamp OR the marker file)
	 * @param file - the {@link File} to test
	 * @param duration - the {@link Duration}, i.e. how long before it goes stale
	 * @param isDynamicRepository - true if the repository asked for has RH support, false if it's a dumb one
	 * @return - true if the file was found in need of updating  
	 */
	public static boolean requiresUpdate( File file, Duration duration, boolean isDynamicRepository) {
		boolean requiresUpdate = false;		
		FileUpdateStatus updateStatus = FileCommons.requiresUpdateReflected(file, duration, isDynamicRepository);  
		switch( updateStatus) {
			case OUTDATED :
				if (isDynamicRepository)
					requiresUpdate = false;
				else
					requiresUpdate = true;
				break;
			case ASIS:
				requiresUpdate = false;
			break;
			default :
				requiresUpdate = true;							
		}
		
		return requiresUpdate;
	}
	
	/**
	 * test if file's out-dated (via its time-stamp OR the marker file) or missing
	 * @param file - the {@link File} to test
	 * @param duration - the {@link Duration}, i.e. how long before it goes stale
	 * @return - the {@link FileUpdateStatus} determined  
	 */
	
	public static FileUpdateStatus requiresUpdateReflected(File file, Duration duration, boolean isDynamicRepository) {
		if (!file.exists()) {
			return FileUpdateStatus.MISSING;
		}
		
		// no duration ('never' in maven) -> never update 
		if (duration == null) {
			return FileUpdateStatus.ASIS;
		}

		// check for marker if it's a dynamic repo (with duration != null)
		if (isDynamicRepository) {
			File markerFile = markerFile(file);
			if (markerFile.exists()) {
				return FileUpdateStatus.MARKED;
			}
		}
				
		// check modification date : duration's set, not a dynamic repo
		if (duration != null) {
			long lastModified = file.lastModified();
			long now = new Date().getTime();
			long lapse = duration.toMillis();
			long diff = (now - lastModified);
			if (diff > lapse) {		
				return FileUpdateStatus.OUTDATED;
			}
		}
		return FileUpdateStatus.ASIS;		
	}
	
	/**
	 * creates a correctly named update-marker file for the passed file 
	 * @param fileToMark - the file to create the marker for 
	 * @return - a correctly named and placed marker file
	 */
	public static File markerFile( File fileToMark) {
		return new File( fileToMark.getParentFile(), fileToMark.getName() + "." + MARKER_OUTDATED);
	}
	
	/**
	 * savely removes a no-longer-needed marker file of the passed file 
	 * @param lockSupplier - the supplier for the {@link Lock}
	 * @param fileMarked - the marked file (not the marker file)
	 */
	public static void removeMarkerFile( Function<File,ReadWriteLock> lockSupplier, File fileMarked) {
		// delete the marker file
		File markerFile = markerFile( fileMarked);
		if (!markerFile.exists())
			return;
		Lock writeLock = lockSupplier.apply( markerFile).writeLock();
		writeLock.lock();
		try {
			if (!markerFile.delete()) {
				throw new IllegalStateException( "cannot delete marker file [" + markerFile.getAbsolutePath() + "]");
			}
		}
		finally {
			writeLock.unlock();
		}
	}
	
}
