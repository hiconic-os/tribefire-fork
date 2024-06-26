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
package com.braintribe.crypto.mac;

import java.net.InetAddress;
import java.net.NetworkInterface;

import com.braintribe.crypto.CryptoServiceException;
import com.braintribe.crypto.utils.TextUtils;
 
/**
 * gives access to the MAC address of the network adapter 
 * bound the the local host ip.
 * 
 * @author pit
 *
 */
public class MacAddressExtractor {
	
	/*
     * Get NetworkInterface for the current host and then read the 
     * hardware address.
     */
	  public static byte [] getMacAddress()  throws CryptoServiceException{
		  try {
			InetAddress address = InetAddress.getLocalHost();		            
			  NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			  byte[] mac = ni.getHardwareAddress();
			  return mac;
		} catch (Exception e) {
			 throw new CryptoServiceException("cannot retrieve MAC address as " + e, e);		
		}
 
	  }

	  public static String getMacAddressAsString() throws CryptoServiceException{
	        try {  	         
	        	byte [] mac = getMacAddress();
	            /*
	             * Extract each array of mac address and convert it to hex with the 
	             * following format like 08-00-27-DC-4A-9E.
	             */
	            StringBuilder builder = new StringBuilder();
	            for (int i = 0; i < mac.length; i++) {
	             builder.append( String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));                
	            }
	            return builder.toString();
	        } catch (Exception e) {
	            throw new CryptoServiceException("cannot retrieve MAC address as " + e, e); 
	        }
	    }
    
    public static void main(String [] args) {
    	try {
			byte [] mac = getMacAddress();
			System.out.println( TextUtils.convertToHex( mac));
			System.out.println( TextUtils.convertToHex2( mac));
		} catch (CryptoServiceException e) {		
			e.printStackTrace();
		}
    }
}
