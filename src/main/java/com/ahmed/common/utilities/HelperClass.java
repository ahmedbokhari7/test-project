/**
 * 
 */
/**
 * @author ahmedbokhari
 *
 */
package com.ahmed.common.utilities;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ahmed.enums.ConfigPath;
import com.ahmed.enums.Customer;
import com.ahmed.enums.Product;

public class HelperClass {
	private static Logger log = LogManager.getLogger(HelperClass.class);
	private String xmlDataSet;
	private Customer customer = Customer.ERS_STD;
	private Product product = Product.ERS;
	private final boolean skipBugs;

	public HelperClass() {

		if (System.getProperty("testDataFile") != null
				&& (FilenameUtils.getExtension(System.getProperty("testDataFile")).equalsIgnoreCase("xml")
						|| FilenameUtils.getExtension(System.getProperty("testDataFile")).equalsIgnoreCase("json"))) {
			xmlDataSet = System.getProperty("testDataFile");
			log.debug("Provided test data source path: " + xmlDataSet);
		} else {
			xmlDataSet = ConfigPath.DEFAULT_TEST_DATA + customer.getCustomer() + "/test-data/data_"
					+ product.getProductName() + ".xml";
			log.warn("No or invalid test data file provided. Taking " + xmlDataSet + " by default");
		}
		

		if (System.getProperty("skipBugs") != null)
			skipBugs = Boolean.getBoolean("skipBugs");
		else
			skipBugs = false;

		
	}

	public static HelperClass getInstance() {

		return HelperClassHelper.instance;

	}

	private static class HelperClassHelper {
		private static final HelperClass instance = new HelperClass();
	}

	public String getXmlDataSet() {
		return xmlDataSet;
	}

	public boolean isSkipBugs() {
		// TODO Auto-generated method stub
		return skipBugs;
	}

}