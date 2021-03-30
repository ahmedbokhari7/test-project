package com.ahmed.dataproviders;

import java.util.logging.LogManager;


import org.testng.annotations.DataProvider;
import org.testng.log4testng.Logger;

import com.ahmed.utility.TestDataReader;

public class UssdDataProvider {
	private TestDataReader testDataReader = TestDataReader.getInstance();
//	private Logger log = LogManager.getLogger(UssdDataProvider.class);

	@DataProvider(name = "ussdCreditTransfer")
	public Object[][] getDataForUSSDStockTransfer() {
//		log.info("Method called: getDataForUSSDStockTransfer");
		System.out.println("ahmed at data provider ");
		return testDataReader.readDataMap("ussd", "ussdCreditTransfer");
	}

}
