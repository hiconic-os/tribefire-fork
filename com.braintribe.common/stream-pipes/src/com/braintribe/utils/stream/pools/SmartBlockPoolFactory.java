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
package com.braintribe.utils.stream.pools;

import static com.braintribe.utils.lcd.IOTools.SIZE_1M;
import static com.braintribe.utils.lcd.IOTools.SIZE_4K;
import static com.braintribe.utils.lcd.IOTools.SIZE_64K;

import java.io.File;

import com.braintribe.common.lcd.Numbers;
import com.braintribe.logging.Logger;
import com.braintribe.utils.IOTools;

/**
 * @author Neidhart.Orlich
 *
 */
public class SmartBlockPoolFactory {
	private static final long MAX_MEMORY = Numbers.GIBIBYTE * 32;

	private final static Logger logger = Logger.getLogger(SmartBlockPoolFactory.class);
	
	private int smallBlocksSize = SIZE_4K;
	private int mediumBlocksSize = SIZE_64K;
	private int largeBlocksSize = SIZE_1M;
	
	private int smallBlocksAmountFraction = 10; 
	private int mediumBlocksAmountFraction = 25; 
	private int largeBlocksAmountFraction = 65;
	
	private long totalMemory = 100 * SIZE_1M; // in bytes
	
	private File streamPipeFolder;
	
	public static SmartBlockPoolFactory usingAvailableMemory(double percent) {
		SmartBlockPoolFactory smartBlockPoolFactory = new SmartBlockPoolFactory();
		long maxMemory = Runtime.getRuntime().maxMemory();
		
		if (maxMemory == Long.MAX_VALUE) {
			smartBlockPoolFactory.totalMemory = 100 * SIZE_1M;
			logger.warn("Could not retrieve information about available memory. Allocating " + smartBlockPoolFactory.totalMemory + " bytes for stream pipes.");
			return smartBlockPoolFactory;
		} 

		if (maxMemory > MAX_MEMORY) {
			logger.info("Found unusually high amount of available memory: " + maxMemory + " bytes. Using " + MAX_MEMORY + " bytes instead as a base for stream pipes buffer size calculation.");
			maxMemory = MAX_MEMORY;
		}
		
		smartBlockPoolFactory.totalMemory = (long) (maxMemory * percent);
			
		return smartBlockPoolFactory;
	}
	
	public CompoundBlockPool create() {
		int sumOfFractions = smallBlocksAmountFraction + mediumBlocksAmountFraction + largeBlocksAmountFraction;
		
		float smallBlocksPercent =  (float)smallBlocksAmountFraction / (float)sumOfFractions;
		float mediumBlocksPercent = (float)mediumBlocksAmountFraction / (float)sumOfFractions;
		float largeBlocksPercent = (float)largeBlocksAmountFraction / (float)sumOfFractions;
		
		int numSmallBlocks = (int)((float)totalMemory / (float)smallBlocksSize * smallBlocksPercent);
		int numMediumBlocks = (int)((float)totalMemory / (float)mediumBlocksSize * mediumBlocksPercent);
		int numLargeBlocks = (int)((float)totalMemory / (float)largeBlocksSize * largeBlocksPercent);
		
//		For tweaking labs you can use the following output:
//		System.out.println("Small Blocks: " + numSmallBlocks + " totaling " + numSmallBlocks * smallBlocksSize / IOTools.SIZE_1M + "MB");
//		System.out.println("Medium Blocks: " + numMediumBlocks + " totaling " + numMediumBlocks * mediumBlocksSize/ IOTools.SIZE_1M + "MB");
//		System.out.println("Large Blocks: " + numLargeBlocks + " totaling " + numLargeBlocks * largeBlocksSize/ IOTools.SIZE_1M + "MB");

		return CompoundBlockPoolBuilder.start() //
				.appendInMemoryBlockPool(smallBlocksSize, numSmallBlocks)
				.appendInMemoryBlockPool(mediumBlocksSize, numMediumBlocks)
				.appendSoftReferencedInMemoryBlockPool(largeBlocksSize, numLargeBlocks)
				.appendDynamicFileBlockPool(streamPipeFolder) //
				.build();
	}
	
	public int getSmallBlocksSize() {
		return smallBlocksSize;
	}

	public void setSmallBlocksSize(int smallBlocksSize) {
		this.smallBlocksSize = smallBlocksSize;
	}

	public int getMediumBlocksSize() {
		return mediumBlocksSize;
	}

	public void setMediumBlocksSize(int mediumBlocksSize) {
		this.mediumBlocksSize = mediumBlocksSize;
	}

	public int getLargeBlocksSize() {
		return largeBlocksSize;
	}

	public void setLargeBlocksSize(int largeBlocksSize) {
		this.largeBlocksSize = largeBlocksSize;
	}

	public int getSmallBlocksAmountFraction() {
		return smallBlocksAmountFraction;
	}

	public void setSmallBlocksAmountFraction(int smallBlocksAmountFraction) {
		this.smallBlocksAmountFraction = smallBlocksAmountFraction;
	}

	public int getMediumBlocksAmountFraction() {
		return mediumBlocksAmountFraction;
	}

	public void setMediumBlocksAmountFraction(int mediumBlocksAmountFraction) {
		this.mediumBlocksAmountFraction = mediumBlocksAmountFraction;
	}

	public int getLargeBlocksAmountFraction() {
		return largeBlocksAmountFraction;
	}

	public void setLargeBlocksAmountFraction(int largeBlocksAmountFraction) {
		this.largeBlocksAmountFraction = largeBlocksAmountFraction;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public void setStreamPipeFolder(File streamPipeFolder) {
		this.streamPipeFolder = streamPipeFolder;
	}
	
}
