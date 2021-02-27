package com.ahmed.testbase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {

	
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

    }


    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
    }

}
