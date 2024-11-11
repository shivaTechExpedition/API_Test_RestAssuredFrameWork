package com.github.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ExtentReportUtility {
	public static ExtentReports report;
	public static ExtentSparkReporter spark;
	public static ExtentTest testLogger;
	private static ExtentReportUtility extentReportObj;
	
	private ExtentReportUtility() {
		
	}
	
	public static ExtentReportUtility getInstance() {
		if(extentReportObj == null) {
			extentReportObj = new ExtentReportUtility();
		}
		return extentReportObj;
	}
	
	public void startExtentReport() {
		report = new ExtentReports();
		spark = new ExtentSparkReporter(Constants.EXTENT_REPORT_FILE);
		report.setSystemInfo("Host Name", "Github API testing");
		report.setSystemInfo("Environment", "QA");
		report.setSystemInfo("UserName", "Shivakumari");
		
		spark.config().setDocumentTitle("GithubAPI test execution report");
		spark.config().setReportName("Github API tesing");
		spark.config().setTheme(Theme.DARK);
		report.attachReporter(spark);
	}
	
	public void startSingleTestReport(String methodName) {
		testLogger = report.createTest(methodName);
	}
	
	
	public void endReport() {
		report.flush();
	}
	
	public void logTestInfo(String text) {
		System.out.println("test logger object="+testLogger);
		testLogger.log(Status.INFO,text);
		
	}
	
	public void logTestpassed(String text) {
		testLogger.log(Status.PASS,MarkupHelper.createLabel(text, ExtentColor.GREEN));
	}
	
	public void logTestFailed(String text) {
		testLogger.log(Status.FAIL,MarkupHelper.createLabel(text, ExtentColor.RED));
	}
	
	public void logTestFailedWithException(Throwable e) {
		testLogger.log(Status.FAIL,e);
	
		}

}
