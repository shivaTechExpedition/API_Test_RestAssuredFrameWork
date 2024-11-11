package com.github.base;

import org.testng.annotations.BeforeSuite;

import com.github.utilities.PropertiesUtility;
import com.github.utilities.TestDataUtility;


public class BaseTest {
	
	@BeforeSuite
	public void beforeSuiteSetUp() {
		PropertiesUtility.loadProperty();
		TestDataUtility.loadTestData();
	}
	

}
