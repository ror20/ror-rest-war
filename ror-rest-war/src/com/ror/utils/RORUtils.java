package com.ror.utils;

import com.google.gson.Gson;

public final class RORUtils {

	public static final String convertToJson(Object object) {
		return new Gson().toJson(object);
	}

	public static final Object convertToPOJO(Object key, Class<?> classObject) {
		return new Gson().fromJson((String) key, classObject);
	}

}
