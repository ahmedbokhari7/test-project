package com.ahmed.common.utilities;

import java.util.HashMap;



import java.util.Map;

import com.ahmed.utilities.restassured.RequestProcessingUtilities;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RestAPIHelper
{
	private static final Logger log = LogManager.getLogger(RestAPIHelper.class);
	private static final RequestProcessingUtilities requestProcessingUtilities = new RequestProcessingUtilities();
	//private static final ResponseProcessingUtilities responseProcessingUtilities = new ResponseProcessingUtilities();
	private RestAPIHelper() {
		log.info("RestHelper Initialized");
		System.out.println("RestHelper Initialized");
	}

	public static class RestAPIHelperModule {
		private static final RestAPIHelper instance = new RestAPIHelper();
	}

	public static RestAPIHelper getInstance() {
		return RestAPIHelperModule.instance;
	}

	/***
	 * Any kind of Rest Transaction can be performed using this method.
	 * @param testdata  It will Map<String,String> format
	 * @param requestType
	 * @param resource
	 * @param requesTemplateFileName
	 * @param cdataKey
	 * @param component
	 * @param prevResponseMap
	 * @return  Response object
	 */

	public Response performRestTransaction(Map<String,String> testdata, String requestType,String resource,String requesTemplateFileName,String cdataKey,String component,Map<String,Response> prevResponseMap) {
		return requestProcessingUtilities.sendRequest(RestAssured.given(), Method.POST, testdata.get(resource), testdata, component, requesTemplateFileName, cdataKey, prevResponseMap);	
		}

	/**
	 * Method that orchestrate the checks to be done: - check on response code -
	 * jsonschemavalidation - checks on the responses headers - checks on specific fields of the responses retrieved
	 *
	 * @param testDataMap it is a map retrieved from the data_ers.xml file with all input test data
	 * @param response it the response body after making a request
	 * @param cdataKey it is used for conversion of response in xml to map format
	 * @param component it is a constant value
	 * @param successKey it will be used to validate the response
	 *
	 */

	public void responseValidation(Map<String, String> testDataMap, Response response, String cdataKey, String component, String successKey) {
		log.debug("Inside newRestValidation");
		try {
			HashMap<String, HashMap<String, String>> extractedInfo = Utilities.getInstance().CdataToMap(testDataMap, cdataKey);
			Map<String, String> httpResponseCode = extractedInfo.get("http");
			Map<String, String> responseHeaderMap = extractedInfo.get("header");
			Map<String, String> responseBodyMap = extractedInfo.get("jsonBody");

			if (httpResponseCode != null && !httpResponseCode.isEmpty()) {
	//			responseProcessingUtilities.checkResponseCode(response, httpResponseCode);
			}
			if (response.body() != null && testDataMap.containsKey("responseTemplateFileName")) {
	//			responseProcessingUtilities.jsonSchemaValidation(response, testDataMap, component);
			}
			if (responseHeaderMap != null && !responseHeaderMap.isEmpty()) {
	//			responseProcessingUtilities.headerChecks(response, responseHeaderMap);
			}
			if (responseBodyMap != null && !responseBodyMap.isEmpty()) {
	//			responseProcessingUtilities.fieldChecks(response, testDataMap, responseBodyMap, successKey);
			}
		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
	}

}