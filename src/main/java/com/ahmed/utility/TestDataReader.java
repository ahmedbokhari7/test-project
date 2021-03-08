package com.ahmed.utility;

import java.io.File;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ahmed.common.utilities.HelperClass;
import com.ahmed.enums.DatasetType;

public class TestDataReader {

	private DatasetType testType;
	private String testDataFileType;
	private HelperClass helperClass = HelperClass.getInstance();
	private Logger log = LogManager.getLogger(TestDataReader.class);
	private String testDataFile;
	private Boolean isUssdMenu;
	private final boolean skipBugs;
	
	  private TestDataReader() {
	        testType = testType.REGRESSION;
	        testDataFile = helperClass.getXmlDataSet();
	        testDataFileType =FilenameUtils.getExtension(testDataFile);
	    }


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

	private Object[][] getFieldValuesFromXMLMap(NodeList datasetList, DatasetType dsType) {
		try {
			NodeList fieldList;
			Node field;
			List<Map> total = new LinkedList<>();
			int fieldCount = 0, k = 0;
			int numberOfDataSet;
			for (int i = 0; i < datasetList.item(0).getChildNodes().getLength(); i++) {
				field = datasetList.item(0).getChildNodes().item(i);
				if (field.getNodeType() == Node.ELEMENT_NODE)
					fieldCount++;
			}
			log.debug("Dataset count: " + datasetList.getLength());
			log.debug("Data field lenght: " + fieldCount);
			if (dsType == DatasetType.SMOKE)
				numberOfDataSet = datasetList.getLength();// change to 2 when regression test are ready
			else if (dsType == DatasetType.REGRESSION)
				numberOfDataSet = datasetList.getLength();
			else if (dsType == DatasetType.SANITY)
				numberOfDataSet = 1;
			else
				numberOfDataSet = 0;

			for (int i = 0; i < datasetList.getLength() && i < numberOfDataSet; i++) {
				if (skipBugs) {
					if (datasetList.item(i).hasAttributes()) {
						String id = datasetList.item(i).getAttributes().getNamedItem("bugId").getNodeValue();
						log.info("Identified known bug" + id);
						if (null != id) {
							// helperClass.addBug(id);
							log.info("skipping test data : " + nodeToString(datasetList.item(i)));
							continue;
						}
					}
				}
				fieldList = datasetList.item(i).getChildNodes();
				log.debug("Dataset: " + (i + 1));
				Map<String, String> map = new LinkedHashMap<>();
				for (int j = 0; j < fieldList.getLength(); j++) {
					field = fieldList.item(j);
					if (field.getNodeType() == Node.ELEMENT_NODE) {
						if (field.getChildNodes().getLength() > 1 || field instanceof CharacterData) {
							log.debug("Field: " + field.getNodeName() + "\t\tValue: " + nodeToString(field));
							k++;
							map.put(field.getNodeName(), nodeToString(field));
						} else if (field.getChildNodes().item(0) == null) {
							log.debug("Field: " + field.getNodeName() + "\t\tValue: ");
							k++;
							map.put(field.getNodeName(), "");
						} else {
							log.debug("Field: " + field.getNodeName() + "\t\tValue: "
									+ field.getChildNodes().item(0).getNodeValue());
							k++;
							if (map.containsKey(field.getNodeName()))
								throw new RuntimeException(
										"Duplicate key Identified at dataset " + (i + 1) + " at line number " + k);
							map.put(field.getNodeName(), field.getChildNodes().item(0).getNodeValue());
						}

					}
				}
				if (k != fieldCount && !isUssdMenu) // to check if every dataset has the same number of child nodes
					throw new Exception("Inconsistent dataset found at dataset position " + (i + 1)
							+ ". Please check the xml file.");
				k = 0;
				total.add(map);
			}

			Object[][] mapdata = new Object[total.size()][1];

			for (int i = 0; i < total.size(); i++) {
				mapdata[i][0] = total.get(i);
			}
			return mapdata;
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
		default: {
			log.warn("Invalid data source type. Must be xml or json. Taking xml as default");
			return readDataFromXMLMap(channel, feature, testType);
		}
		}

	}

	private String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}

	private static class CommonDataReaderHelper {
		private static final TestDataReader instance = new TestDataReader();
	}

	public static TestDataReader getInstance() {
		return CommonDataReaderHelper.instance;
	}
}
