package com.ahmed.common.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.ahmed.enums.ConfigPath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class ConfigPropertyReader {

	private Properties properties = new Properties();
	private Logger log = LogManager.getLogger(ConfigPropertyReader.class);
	private String[] deprecatedConfigs = { "USSD.TRANSACTION_STATUS.TOPUP.SUCCESS.PARAMETERS",
			"USSD.TRANSACTION_STATUS.TOPUP.FAIL.PARAMETERS", "USSD.TRANSACTION_STATUS.TOPUP.SUCCESS.AMOUNT",
			"USSD.TRANSACTION_STATUS.TOPUP.SUCCESS.RECEIVER_MSISDN", "USSD.TRANSACTION_STATUS.TOPUP.FAIL.AMOUNT",
			"USSD.TRANSACTION_STATUS.TOPUP.FAIL.RECEIVER_MSISDN" };
	 private ConfigPropertyReader()     {
	        log.debug("Method called: ConfigPropertyReader");
	        
	        InputStream fileInput = null;
			try {
				fileInput = new FileInputStream(ConfigPath.CONFIG_PROPERTY_PATH);
				try {
					properties.load(fileInput);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        	
	        
	    }

	/**
	 * Creating instance variable.
	 */
	public static ConfigPropertyReader getInstance() {

		return ReadConfigFromPropertiesHelper.instance;

	}

	public String readProperties(String key) {
		String config = properties.getProperty(key);
		log.debug("Obtained config value for : " + key + " is : " + config);
		return config;
	}

	public String readProperties(String key, String defaultValue) {
		String value = properties.getProperty(key);
		log.debug("Obtained config value for : " + key + " is : " + value);
		if (value != null && !value.isEmpty())
			return value;

		return defaultValue;
	}

	/**
	 * Singleton Helper Class.
	 */
	private static class ReadConfigFromPropertiesHelper {

		private static final ConfigPropertyReader instance = new ConfigPropertyReader();
	}

	public Properties getProperties() {
		return properties;
	}

	private void lookupDeprecatedLogs() {
		for (String deprecatedConfig : deprecatedConfigs) {
			if (readProperties(deprecatedConfig) != null) {
				log.info("deprecate Configuration found " + deprecatedConfig);
			}
		}
	}

}
