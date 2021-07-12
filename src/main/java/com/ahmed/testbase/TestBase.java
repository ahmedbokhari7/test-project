package com.ahmed.testbase;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;

import com.ahmed.common.utilities.ConfigPropertyReader;

import com.ahmed.common.utilities.*;

public class TestBase {
    // Sample TestBase class
    protected static final String tcId = "DM_";
    protected static int tcNo = 0;
    public String tcName = null;
    private Logger log = LogManager.getLogger(this);
    protected ConfigPropertyReader reader = ConfigPropertyReader.getInstance();
    protected String ersReferencePattern = reader.readProperties("ERS_REFERENCE_PATTERN", "[0-9]{25,25}");
 //   protected ExtentReportIntegration extent = ExtentReportIntegration.getInstance();
  //  private JetmAnalyser jetmAnalyser = JetmAnalyser.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        log.info("Before Suite Initialized");
    }


    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
    	System.out.println("AFTER SUITE ");
 //       extent.generatereport();
  //      jetmAnalyser.showJetmStats();
  //      jetmAnalyser.stopAnalysing();
    }
}