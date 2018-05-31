package com.ror.utils;

import static com.ror.constants.RORConstants.EEE_MMM_DD_HH_MM_SS_Z_YYYY_DATE;
import static com.ror.constants.RORConstants.MM_DD_YYYY_HH_MM_SS_DATE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

public final class RORUtils {


	public static final String convertToJson(Object object) {
		return new Gson().toJson(object);
	}

	public static final Object convertToPOJO(Object key, Class<?> classObject) {
		return new Gson().fromJson((String) key, classObject);
	}

	public static final Date convertStringToDateRORFormat(String inputTimeStamp) throws Exception{
		String normalDateFormat = TimeStampConverter(EEE_MMM_DD_HH_MM_SS_Z_YYYY_DATE, inputTimeStamp, MM_DD_YYYY_HH_MM_SS_DATE);
		return new Date(normalDateFormat);
	}
	
	private static String TimeStampConverter(final String inputFormat, String inputTimeStamp, final String outputFormat)
			throws ParseException {
		return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(inputFormat).parse(inputTimeStamp));
	}
}
