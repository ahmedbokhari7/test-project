package com.ahmed.testbase;

import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;

import com.ahmed.common.utilities.ConfigPropertyReader;
import com.ahmed.common.utilities.*;

public class TestBase {
	protected static final String tcId = "DM_";
	protected static int tcNo = 0;
	public String tcName = null;

	protected ConfigPropertyReader reader = ConfigPropertyReader.getInstance();
	protected static HelperClass helperClass = HelperClass.getInstance();

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {

	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
	}

}
