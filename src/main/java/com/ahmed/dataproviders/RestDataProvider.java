package com.ahmed.dataproviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.ahmed.utility.TestDataReader;

//import com.seamless.ers.standard.testutility.TestDataReader;


public class RestDataProvider
{
    private  Logger log = LogManager.getLogger(RestDataProvider.class);
    private TestDataReader testDataReader = TestDataReader.getInstance();

    @DataProvider(name = "addBook")
    public  Object[][] getAddBook() {
        log.debug("Method: getAddBook");
        return testDataReader.readDataMap("rest","addBook");
    }


}
