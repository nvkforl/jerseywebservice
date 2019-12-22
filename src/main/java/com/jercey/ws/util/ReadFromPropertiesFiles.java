package com.jercey.ws.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class ReadFromPropertiesFiles {

	private static ReadFromPropertiesFiles instance = null;

	Map<String, String> map = new HashMap<String, String>();

	private ReadFromPropertiesFiles() throws IOException {
		Properties prop = new Properties();
		FileInputStream inputStream = null;
		try {
			ClassLoader objClassLoader = getClass().getClassLoader();
			inputStream = new FileInputStream(objClassLoader.getResource("config.properties").getFile());
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Some issue finding or loading file....!!! " + e.getMessage());

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		for (final Entry<Object, Object> entry : prop.entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}

	}

	public static synchronized ReadFromPropertiesFiles getInstance() throws IOException {
		if (instance == null) {
			instance = new ReadFromPropertiesFiles();
		}
		return instance;
	}

	public Map<String, String> getPropertyMap() throws IOException {
		return map;
	}

}
