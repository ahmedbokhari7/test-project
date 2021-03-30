/**
 * 
 */
/**
 * @author ahmedbokhari
 *
 */
package com.ahmed.utilities.restassured;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;

import com.ahmed.common.utilities.ConfigPropertyReader;
import com.ahmed.common.utilities.HelperClass;
import com.ahmed.common.utilities.Utilities;
import com.ahmed.enums.ConfigPath;
import com.jayway.jsonpath.DocumentContext;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

public class RequestProcessingUtilities
{
	private static final Logger log = LogManager.getLogger(RequestProcessingUtilities.class);
	private HelperClass helperClass = HelperClass.getInstance();
	private ConfigPropertyReader reader = ConfigPropertyReader.getInstance();


	public Response sendRequest(RequestSpecification specification,Method requestType, String urlKey, Map<String,String> testdata,String component,String fileKey,String cdataKey,Map<String,Response> prevResponseMap) {
		String url = reader.readProperties(urlKey);
		specification.baseUri(helperClass.returnRestBaseUri());
		if(testdata!=null && !testdata.isEmpty()) {
			HashMap<String, HashMap<String, String>> extractedinfo = Utilities.getInstance().CdataToMap(testdata, cdataKey,prevResponseMap);
			specification = addJsonBody(specification, extractedinfo.get("jsonBody"), component, testdata.get("version"), testdata.get(fileKey));
			specification = addparams(specification, extractedinfo.get("pathParam"), true);
			specification = addparams(specification, extractedinfo.get("param"), false);
			specification = addheaders(specification, extractedinfo.get("header"));
			specification = addMultiPartFile(specification, extractedinfo.get("multiPart"),component);
		}
		log.debug(RequestPrinter.print((FilterableRequestSpecification) specification, requestType.name(),
				((FilterableRequestSpecification) specification).getBaseUri() + url, LogDetail.ALL,
				Collections.emptySet(), System.out, true));

		Response response= specification.request(requestType, url);

		log.debug(ResponsePrinter.print(response, response.getBody(),
				System.out, LogDetail.ALL, true,Collections.emptySet()));

		return response;
	}

	private RequestSpecification addMultiPartFile(RequestSpecification specification, HashMap<String, String> multiPartFileMap,String component)
	{
		if (multiPartFileMap !=null && !multiPartFileMap.isEmpty()) {
			String filepath= ConfigPath.FILE_UPLOAD_PATH + component.toLowerCase() +"/"
					+ multiPartFileMap.get("fileName");
			try {
				specification.multiPart
						(new MultiPartSpecBuilder("Test-Content-In-File".getBytes())
								.fileName(filepath).controlName("file")
								.mimeType(multiPartFileMap.get("mimeType")).build());

			} catch (Exception e) {
				log.error("Error occurred ", e.getCause());
				throw new RuntimeException(e);
			}
		}
		return specification;
	}

	public RequestSpecification addJsonBody(RequestSpecification spec, Map<String,String> jsonPathsMap, String component, String version, String filename){
		if (jsonPathsMap !=null && !jsonPathsMap.isEmpty()) {
			String filepath=ConfigPath.REST_REQUEST_TEMPLATE_PATH + component.toLowerCase()
					+ (null == version || version.isEmpty() ?
					"/" : "_" + version.replace(".", "_") + "/")
					+ filename;
			try {
				String requestFromFile = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);
				String payload = modifyJsonWithParameter(requestFromFile, jsonPathsMap);
				spec.body(payload);

			} catch (IOException e) {
				log.error("Error occurred ", e.getCause());
				throw new RuntimeException(e);
			}
			spec.contentType(ContentType.JSON);
		}
		return spec;
	}

	private String modifyJsonWithParameter(String originalJsonString, Map<String,String> parameterMap){
		com.jayway.jsonpath.Configuration config = com.jayway.jsonpath.Configuration.defaultConfiguration();
		DocumentContext jsonDocContext = com.jayway.jsonpath.JsonPath.using(config).parse(originalJsonString);
		for(String jsonPathParameter : parameterMap.keySet()) {
			try {
				setRequestParam(jsonDocContext,jsonPathParameter,parameterMap);
			}
			catch (Exception e){
				log.error("Could not resolve the following JsonPath : "+jsonPathParameter, e.getCause());
			}
		}
		return jsonDocContext.jsonString();
	}

	void setRequestParam(DocumentContext jsonDocContext, String jsonPathParameter, Map<String,String> parameterMap)
	{
		if(jsonDocContext.read(jsonPathParameter)==null)
		{
			log.error("The value in the template is null for "+":"+jsonPathParameter);
			throw new NullPointerException("Null values are not allowed in the json templates." +
					"If needed set the null value in test data");
		}
		else if(jsonDocContext.read(jsonPathParameter).getClass()==Integer.class)
		{
			jsonDocContext.set(jsonPathParameter, Integer.parseInt(parameterMap.get(jsonPathParameter)));
		}
		else if(jsonDocContext.read(jsonPathParameter).getClass()==Boolean.class)
		{
			jsonDocContext.set(jsonPathParameter,Boolean.parseBoolean(parameterMap.get(jsonPathParameter)));
		}
		else if(jsonDocContext.read(jsonPathParameter).getClass()==Long.class)
		{
			jsonDocContext.set(jsonPathParameter,Long.parseLong(parameterMap.get(jsonPathParameter)));
		}
		else if(jsonDocContext.read(jsonPathParameter).getClass()==Double.class)
		{
			jsonDocContext.set(jsonPathParameter,Double.parseDouble(parameterMap.get(jsonPathParameter)));

		}
		else if(jsonDocContext.read(jsonPathParameter).getClass()==Float.class)
		{
			jsonDocContext.set(jsonPathParameter,Float.parseFloat(parameterMap.get(jsonPathParameter)));

		}
		else if(parameterMap.get(jsonPathParameter).compareTo("null")==0)
		{
			jsonDocContext.set(jsonPathParameter,null);
		}
		else
		{
			jsonDocContext.set(jsonPathParameter, parameterMap.get(jsonPathParameter));
		}
	}

	public RequestSpecification addheaders(RequestSpecification spec,Map<String,String> headers){
		if(headers!=null && !headers.isEmpty())
			spec.headers(headers);
		return spec;
	}

	public RequestSpecification addparams(RequestSpecification spec,Map<String,String> params,Boolean isPathParams) {
		if (isPathParams) {
			if (params!=null && !params.isEmpty())
				spec.pathParams(params);
		} else {
			if (params!=null && !params.isEmpty())
				spec.params(params);
		}
		return spec;
	}
}
