package com.ror.utils;

import com.google.gson.Gson;

public final class RORUtils {

	public static String convertToJson(Object object) {
		return new Gson().toJson(object);
	}

	public static Object convertToPOJO(Object key, Class<?> classObject) {
		return new Gson().fromJson((String) key, classObject);
	}

}
