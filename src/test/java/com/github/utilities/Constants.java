package com.github.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
	public static final String CURRENT_DIR = System.getProperty("user.dir");
	public static final String ENV_DETAILS = CURRENT_DIR + "/src/test/resources/EnvironmentVariables/env.properties";
	public static final String TEST_DETAILS_FILE = CURRENT_DIR + "/src/test/resources/testData/testData.properties";
	public static final String REPONAMES = CURRENT_DIR+"/src/test/resources/testData/reponames.xlsx";
	public static final String NEW_REPONAMES = CURRENT_DIR+"/src/test/resources/testData/newRepo.properties";
	public final static String EXTENT_REPORT_FILE = CURRENT_DIR + "/NewExtentReports/"+ new SimpleDateFormat("dd-MM-yyyy HH-mm").format(new Date()) + ".html";
}
