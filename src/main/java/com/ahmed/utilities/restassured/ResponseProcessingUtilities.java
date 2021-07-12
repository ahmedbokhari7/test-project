package com.ahmed.utilities.restassured;
import com.ahmed.enums.ConfigPath;
import com.ahmed.common.utilities.CommonHelper;
import com.ahmed.common.utilities.ConfigPropertyReader;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by ahmed on 01/02/21.
 */
public class ResponseProcessingUtilities
{
	private static final Logger log = LogManager.getLogger(ResponseProcessingUtilities.class);
	private ConfigPropertyReader reader = ConfigPropertyReader.getInstance();
	private CommonHelper commonModuleHelper=CommonHelper.getInstance();

	protected static final ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);


	/**
	 * Check of the response code of the retrieved response.
	 *
	 * @param httpResponseCode it is a map retrieved from the xml data file with containg data from responseParameters
	 * @param response         it is the response we find from a get request
	 */

	public void checkResponseCode(Response response, Map<String, String> httpResponseCode) {
		log.debug("Inside checkResponseCode");
		if (response == null) {
			log.error("response is null");
		} else {
			response.then().assertThat().statusCode(equalTo(Integer.parseInt(httpResponseCode.get("ResponseCode"))));
		}
	}

	/**
	 * Check of the jsonSchemaValidation of the retrieved response.
	 *
	 * @param testDataMap it is a map retrieved from the xml data file with containg data from responseParameters
	 * @param response    it is the response we fiund from a get request
	 * @param component   it is the constant passed for the rest component
	 */

	public void jsonSchemaValidation(Response response, Map<String, String> testDataMap, String component) {
		log.debug("Inside jsonSchemaValidation");
		String filepath = ConfigPath.REST_RESPONSE_TEMPLATE_PATH + component.toLowerCase()
				+ (null == testDataMap.get("version") || testDataMap.get("version").isEmpty() ?
				"/" : "_" + testDataMap.get("version").replace(".", "_") + "/")
				+ testDataMap.get("responseTemplateFileName");
		try {
			String requestFromFile = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);
			response.then().assertThat().body(matchesJsonSchema(requestFromFile));
			log.debug("JsonSchemaValidation Passed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check of the response code of the retrieved response.
	 *
	 * @param responseHeaderMap it is a map retrieved from the xml data file with containing header data from responseParameters
	 * @param response          it is the response we find from a get request
	 */

	public void headerChecks(Response response, Map<String, String> responseHeaderMap) {
		log.debug("Inside headerChecks");
		if (response == null) {
			log.error("response is null");
		} else {
			for (Map.Entry<String, String> entry : responseHeaderMap.entrySet()) {
				response.then().assertThat().header(entry.getKey(), equalTo(entry.getValue()));
			}
		}
		log.debug("Header Checks Passed");
	}

	/**
	 * Check of the response code of the retrieved response.
	 *
	 * @param response        it is the response we find from a get request
	 * @param testDataMap     it is the testDataMap we find from a get test data xml field
	 * @param responseBodyMap it is a map retrieved from the xml data file with containing body data from responseParameters
	 * @param successKey      it is the response we find from a get request
	 */

	public void fieldChecks(Response response, Map<String, String> testDataMap, Map<String, String> responseBodyMap, String successKey) {
		log.debug("Inside fieldChecks");
		try {
			for (Map.Entry<String, String> entry : responseBodyMap.entrySet()) {
				//Below we are using groovy path expression to find an element from json response
				String actualResult = response.path(entry.getKey()).toString();
				actualResult  =  formatDecimal(actualResult, entry.getValue());
				if ((successKey != null && !successKey.isEmpty()) && entry.getValue().matches("\\$\\{(.*)\\}")) {
					Matcher matcher = Pattern.compile("\\$\\{(.*)\\}").matcher(entry.getValue());
					matcher.find();
					String key = matcher.group(1);

					String procesedExpectedResult = testDataMap.get(key).equalsIgnoreCase("SUCCESS") ?
							commonModuleHelper.processMessageBuild(reader.readProperties(successKey), testDataMap) :
							commonModuleHelper.processMessageBuild(testDataMap.get(key), testDataMap);

					log.info("<<<<---------After Processing Expected Result Value is---------->>>> " + procesedExpectedResult);
					if (Pattern.compile(procesedExpectedResult).matcher(actualResult).matches()) {
						softAssert.get().assertTrue(true, "Matched in validation at " + entry.getKey() + " ");
					} else {
						softAssert.get().assertTrue(false, "Mismatch in validation at " + entry.getKey() + " ");
					}
				} else {
					softAssert.get().assertEquals(actualResult, entry.getValue());
				}
			}
			softAssert.get().assertAll();
			log.debug("Field Checks Passed");
		} catch (AssertionError e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			softAssert.remove();
		}
	}

	/***
	 * Check string is numeric
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	/***
	 * 
	 * @param actualResult is suppose 20000.0
	 * @param original is 20000.00
	 * @return will append .00 and result will be 20000.00
	 */
	    private String formatDecimal(String actualResult, String original) {
	        if(isNumeric(actualResult) && actualResult.contains(".")){
	            String temp = original.substring(original.indexOf("."), original.length());
	            String first = actualResult.substring(0, actualResult.indexOf("."));
	            return first.concat(temp);
	        }
	        return actualResult;
	    }
	

}
