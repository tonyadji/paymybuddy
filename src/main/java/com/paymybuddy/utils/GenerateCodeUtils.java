/**
 * 
 */
package com.paymybuddy.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.Base64.Encoder;

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
	
	public static String generateCode() {
		final SecureRandom random = new SecureRandom();
		final byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		final Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		return encoder.encodeToString(bytes);
	}
}
