package com.ahmed.enums;

public class ConfigPath {
	private static final String customer = System.getProperty("customer") != null ? System.getProperty("customer")
			: "ers-std";
	private static final boolean local_run = Boolean.valueOf(System.getProperty("local.run"));

	public static final String DEFAULT_TEST_DATA = local_run ? "src/test/resources/customer/"
			: "/opt/seamless/test/standard-automation/resources/customer/";

	public static final String CONFIG_PROPERTY_PATH = local_run ? "src/main/conf/properties/" + customer + ".properties"
			: "/opt/seamless/conf/standard-automation/properties/" + customer + ".properties";

}
