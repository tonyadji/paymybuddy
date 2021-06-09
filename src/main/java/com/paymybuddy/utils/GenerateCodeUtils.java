/**
 * 
 */
package com.paymybuddy.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author tonys
 *
 */
public class GenerateCodeUtils {

	private GenerateCodeUtils() {}
	
	public static String generateXDigitNumber(int length) {
		Random randomizer;
		try {
			randomizer = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "0000000000";
		}
		return randomizer.ints(length, 1, 10).toString();
	}
}
