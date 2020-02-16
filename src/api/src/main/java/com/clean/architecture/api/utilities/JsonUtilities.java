package com.clean.architecture.api.utilities;

import com.clean.architecture.core.exceptions.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtilities {
	
	private static final Gson gson = new Gson();

	public static void validateJson(String jsonString) throws JsonParseException {
		try {
			gson.fromJson(jsonString, Object.class);
		} catch (JsonSyntaxException ex) {
			throw new JsonParseException("Invalid JSON syntax: " + ex.getMessage());
		}
	}
}
