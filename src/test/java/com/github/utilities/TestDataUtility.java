package com.github.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestDataUtility {
	
	static Properties  testPropObj = new Properties();
	
	public static void loadTestData() {
		File test_file = new File(Constants.TEST_DETAILS_FILE);
		try {
			testPropObj.load(new FileInputStream(test_file));
		}
		catch (IOException e) {
			System.out.println("Exception occured during file read: "+ e.getMessage());
		}
		
	}
	
	public static String getProperty(String key) {
		return testPropObj.getProperty(key);
	}
	

}
