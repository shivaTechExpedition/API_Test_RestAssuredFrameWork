package com.github.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.github.utilities.ExtentReportUtility;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class GithubListeners implements ITestListener {
	public static ExtentReportUtility extentReportUtilityObj = ExtentReportUtility.getInstance();
	
	@Override
	public void onTestStart(ITestResult result) {
		extentReportUtilityObj.startSingleTestReport(result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentReportUtilityObj.logTestpassed(result.getMethod().getMethodName()+" is passed.");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentReportUtilityObj.logTestFailed(result.getMethod().getMethodName()+" is failed.");
		extentReportUtilityObj.logTestFailedWithException(result.getThrowable());
	}

	@Override
	public void onStart(ITestContext context) {
		//extentReportUtilityObj = ExtentReportUtility.getInstance();
		System.out.println("report utility object created : "+ extentReportUtilityObj.toString());
		extentReportUtilityObj.startExtentReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReportUtilityObj.endReport();
		
	}

}



