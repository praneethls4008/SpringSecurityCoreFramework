package org.springframeworkcore.mvc.javaannotationbased.utils;

import java.util.UUID;

public class GenerateUUID {
	public static String getRandom() {
		return UUID.randomUUID().toString();
	}
}
