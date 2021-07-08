/**
 * 
 */
/**
 * @author ahmedbokhari
 *
 */
package com.ahmed.rest.restapi;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ahmed.dataproviders.RestDataProvider;
import com.ahmed.testbase.TestBase;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.mysql.fabric.Response;
import com.seamless.common.automation.utilities.RestAPIHelper;
import com.seamless.test.dataproviders.AccountDataProvider;
import com.seamless.test.methods.FeatureMethods;
import com.seamless.test.testclass.AccountSystem;
import com.ahmed.common.utilities.*;




public class RestService extends TestBase {
	// Below is the sample test class
	String tcName = null;
	static Logger log = LogManager.getLogger(RestService.class);
	RestAPIHelper restService = RestAPIHelper.getInstance();
	final String component = "ams";
	HashMap<String, Response> responseHashMap = new HashMap<>();
//	FeatureMethods featureMethods = FeatureMethods.getInstance();

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		tcName = tcId + tcNo++;
	}

	@Test(dataProvider = "addBook", dataProviderClass = RestDataProvider.class, groups = {
			"add-book" }, priority = 1)
	public void addAccountType(Map<String, String> testData) {

		log.info("Scenario Name: " + testData.get("scenarioName"));
		log.info("Scenario Description: " + testData.get("scenarioDesc"));
		log.info("Expected Result: " + testData.get("expectedResult"));
//		extent.startFeature(tcName + ":" + testData.get("scenarioName"), testData.get("scenarioDesc"));
		try {
//			extent.getFeature().info("Sending REST request to createFeatureRest and verifying the response");
			// responseHashMap = featureMethods.rootlogin();
			Response response = restService.performRestTransaction(testData, "POST", "resource",
					"requestTemplateFileName", "requestParameters", component, responseHashMap);

			responseHashMap.put("createFeatureResponse", response);
			restService.responseValidation(testData, response, "responseParameters", component, "");
//			extent.getFeature().pass(MarkupHelper.createLabel("REST response verification passed for createFeatureRest",
//					ExtentColor.GREEN));
		} catch (Exception e) {
			log.error("Error occurred", e);
			throw (e);
		}
	}
}

}
