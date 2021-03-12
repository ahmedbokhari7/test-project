package com.ahmed.ussd.stringbased;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ahmed.common.database.DatabaseQuery;
import com.ahmed.dataproviders.UssdDataProvider;
import com.ahmed.testbase.TestBase;

public class ErsUssdStringBased extends TestBase {
	private static Logger log = LogManager.getLogger(ErsUssdStringBased.class);
	 private DatabaseQuery query = DatabaseQuery.getInstance();

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownMethod(ITestResult result) {
	}

	@SuppressWarnings("deprecation")
	@Test(dataProvider = "ussdCreditTransfer", dataProviderClass = UssdDataProvider.class, groups = {
			"ussd-credit-transfer" }) // This method need to be replicated to have ussd-R2S with subscriber data
	public void verifyCreditTransfer(HashMap<String, String> testdata) {
		System.out.println("AHmed at function");
		// String scenarioName = testdata.get("scenario");
		System.out.println(testdata.get("scenario"));
		System.out.println("AHMED SYSTEM VALUE" + System.getProperty("local.run"));

		/////////////////

		String scenarioName = testdata.get("scenario");
		String scenarioDesc = testdata.get("scenarioDesc");
		String expectedResult = testdata.get("expectedResult");
		String url = reader.readProperties("ussd.url.transfer");
		String senderMSISDN = testdata.get("agentMSISDN");
		String receiverMSISDN = testdata.get("receiverMSISDN");
		String amount = testdata.get("transferAmount");
		String confirmReceiverMSISDN = null;
		String pin = testdata.get("pin");
		String[] params;
		System.out.println("CASSIM" + scenarioName);

		params = new String[] { senderMSISDN, receiverMSISDN, amount, pin };
		log.info("Scenario: " + scenarioName);
		log.info("Scenario Description: " + scenarioDesc);
		url = helperClass.returnUSSDUrl(url, params);
		
		String senderBalanceBefore=query.getResellerBalance("PMG","RESELLER");
		System.out.println("HASHIM"+senderBalanceBefore);

	}

}
