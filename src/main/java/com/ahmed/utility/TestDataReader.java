package com.ahmed.utility;

import java.io.File;
import java.io.FileReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ahmed.common.automation.enums.DatasetType;

public class TestDataReader {

	private Logger log = java.util.logging.LogManager.getLogger(TestDataReader.class);

	private Object[][] readDataFromXMLMap(String channel, String feature, DatasetType dsType) {
		try {
			Object[][] data = null;
			File file = new File(testDataFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList datasetList, featuresList = doc.getElementsByTagName("feature");
			Node featureNode, channelNode;
			int flag = 0;
			for (int i = 0; i < featuresList.getLength(); i++) {
				featureNode = featuresList.item(i);
				channelNode = featureNode.getParentNode();
				if (featureNode.getNodeType() == Node.ELEMENT_NODE && channelNode.getNodeType() == Node.ELEMENT_NODE) {
					Element elementFeature = (Element) featureNode;
					Element elementChannel = (Element) channelNode;
					if (elementFeature.getAttribute("name").equalsIgnoreCase(feature)
							&& elementChannel.getAttribute("name").equalsIgnoreCase(channel)) {
						datasetList = elementFeature.getElementsByTagName("dataset");
						data = getFieldValuesFromXMLMap(datasetList, dsType);
						flag = 1;
						break;
					}
				}
			}
			if (flag == 0)
				throw new Exception("Invalid channel or feature name.");

			return data;
		} catch (Exception e) {
			log.error("Error occurred", e);
			return null;
		}
	}

	private Object[][] readDataFromJSONMap(String channel, String feature, DatasetType dsType) {
		try {
			Object[][] data = null;
			File file = new File(testDataFile);
			JSONParser parser = new JSONParser();
			Object jsonData = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) jsonData;
			JSONArray _channelList = (JSONArray) jsonObject.get("channel");
			JSONArray _featureList;
			JSONObject _feature;
			int flag = 0;
			for (Object obj : _channelList) {
				String name = ((JSONObject) obj).get("name").toString();
				if (name.equalsIgnoreCase(channel)) {
					_featureList = (JSONArray) ((JSONObject) obj).get("feature");
					for (Object a_featureList : _featureList) {
						_feature = (JSONObject) a_featureList;
						if (_feature.get("name").toString().equalsIgnoreCase(feature)) {
							data = getFieldValuesFromJSONMap(_feature, dsType);
							flag = 1;
							break;
						} else
							flag = 0;
					}
					if (flag == 1)
						break;
				}
			}
			if (flag == 0)
				throw new Exception("Invalid channel or feature name");
			return data;
		} catch (Exception e) {
			log.error("Error occurred", e);
			return null;
		}
	}

	public Object[][] readDataMap(String channel, String feature) // Channel=AgentPortal,AdminPortal,USSD,SOAP;
																	// feature=credit transfer,subscriber topup etc
	{
		isUssdMenu = (feature.toLowerCase().contains("ussdmenubased"))
				|| (feature.toLowerCase().contains("ussdTransactionStatus"));
		switch (testDataFileType.toLowerCase()) {
		case "xml":
			return readDataFromXMLMap(channel, feature, testType);
		case "json":
			return readDataFromJSONMap(channel, feature, testType);
		default: {
			log.warn("Invalid data source type. Must be xml or json. Taking xml as default");
			return readDataFromXML(channel, feature, testType);
		}
		}

	}

	private static class CommonDataReaderHelper {
		private static final TestDataReader instance = new TestDataReader();
	}

	public static TestDataReader getInstance() {
		return CommonDataReaderHelper.instance;
	}
}
