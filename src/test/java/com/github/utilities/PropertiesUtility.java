package com.github.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtility {
	
	static Properties propObj = new Properties();
	
	public static void loadProperty() {
		
		File file = new File(Constants.ENV_DETAILS);
		
		try {
			propObj.load( new FileInputStream(file));
		}
		catch (IOException e) {
			System.out.println("Exception occured during file read: "+ e.getMessage());
		}
	}
	
	
	public static String getProperty(String key) {
		return propObj.getProperty(key);
	}
	
	public static void setProperty(String key, String value) {
		propObj.setProperty(key, value);
	}

}
