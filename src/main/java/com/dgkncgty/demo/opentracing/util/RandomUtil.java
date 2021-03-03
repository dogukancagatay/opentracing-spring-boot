package com.dgkncgty.demo.opentracing.util;

import java.util.Random;

public class RandomUtil {
	private static final Random random = new Random();

	public static int randomInt(int max) {
		return random.nextInt(max);
	}

	public static boolean randomBool() {
		return random.nextBoolean();
	}

	public static String generateRandomString() {
	    return generateRandomString(5);
	}

	public static String generateRandomString(int length) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'

		return random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(length)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}
}
