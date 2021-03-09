package com.ahmed.ussd.stringbased;

import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ahmed.dataproviders.UssdDataProvider;
import com.ahmed.testbase.TestBase;

public class ErsUssdStringBased extends TestBase{

	

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod()
    {
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result)
    {
    }

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "ussdCreditTransfer", dataProviderClass = UssdDataProvider.class, groups = {
			"ussd-credit-transfer" }) // This method need to be replicated to have ussd-R2S with subscriber data
	public void verifyCreditTransfer(HashMap<String, String> testdata) {
		System.out.println("AHmed at function");
		String scenarioName = testdata.get("scenario");
		System.out.println(scenarioName);
	}

}
