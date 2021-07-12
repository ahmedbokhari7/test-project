package com.ahmed.testbase;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

import com.ahmed.common.utilities.HelperClass;

public class TestNGExecute {

    public static void main(String[] args) {
        HelperClass helperClass = HelperClass.getInstance();
        run_automation(System.getProperty("groups"));
        System.exit(0);
    }

    private static void run_automation(String tags) {
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("/opt/seamless/test/rest-service-test/resources/test-suites/testng.xml");
        testNG.setTestSuites(suites);
        if (tags != null && !tags.isEmpty())
            testNG.setGroups(tags);
        testNG.setVerbose(1);
        testNG.run();
    }
}