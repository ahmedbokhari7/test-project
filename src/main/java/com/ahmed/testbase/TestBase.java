package com.ahmed.testbase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.seamless.common.automation.utilities.ConfigPropertyReader;

public class TestBase {
	protected ConfigPropertyReader reader = ConfigPropertyReader.getInstance();

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {

	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
	}

}
