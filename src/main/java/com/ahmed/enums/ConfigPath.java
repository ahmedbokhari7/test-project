package com.ahmed.enums;

public class ConfigPath {
//	private static final String customer = System.getProperty("customer") != null ? System.getProperty("customer")
//			: "ers-std";
	static String customer = "ers-std";
	private static final boolean local_run = Boolean.valueOf(System.getProperty("local.run"));

	public static final String DEFAULT_TEST_DATA = local_run ? "src/test/resources/customer/"
			: "/opt/seamless/test/standard-automation/resources/customer/";

//	public static final String CONFIG_PROPERTY_PATH = local_run ? "src/main/conf/properties/" + customer + ".properties"
//			: "/opt/seamless/conf/standard-automation/properties/" + customer + ".properties";
	public static final String CONFIG_PROPERTY_PATH =  "src/main/conf/properties/" + customer + ".properties";
//	public static final String REST_REQUEST_TEMPLATE_PATH = local_run ? "src/main/conf/templates/rest/request/" : "/opt/seamless/conf/standard-automation/templates/rest/request/";
	public static final String REST_REQUEST_TEMPLATE_PATH = "src/main/conf/templates/rest/request/";
	
	public static final String REST_RESPONSE_TEMPLATE_PATH = local_run ? "src/main/conf/templates/rest/response/" : "/opt/seamless/conf/standard-automation/templates/rest/response/";
    public static final String FILE_UPLOAD_PATH = local_run ? "src/main/conf/templates/imports/" : "/opt/seamless/conf/standard-automation/templates/imports/";
}
