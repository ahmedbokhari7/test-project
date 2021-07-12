package com.ahmed.common.utilities;

import java.io.IOException;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Utilities {
    private static final Logger log = LogManager.getLogger(RestAPIHelper.class);

    private Utilities() {
        log.info("Utilities Initialized");
    }

    public static class UtilitiesHelper {
        private static final Utilities instance = new Utilities();
    }

    public static Utilities getInstance() {
        return UtilitiesHelper.instance;
    }


    public HashMap<String,HashMap<String,String>> CdataToMap(Map<String,String> testdata, String CDataKey) {
        HashMap<String, HashMap<String,String>> map = new HashMap<>();
        try {
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(testdata.get(CDataKey)));
            NodeList fieldList= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is)
                    .getChildNodes().item(0).getChildNodes();
            Node field = fieldList.item(0);
            if (!(field instanceof CharacterData)) {
                throw new DOMException(DOMException.TYPE_MISMATCH_ERR, "Invalid CDATA");
            }
            String rawCData = ((CharacterData) field).getData().trim();
            for (String row : rawCData.split("\\n")) {
                String[] tempStringArray = row.split(Pattern.quote("|||"));
                map.putIfAbsent(tempStringArray[0].trim(),new HashMap<>());
                if (tempStringArray.length == 2) {

                    map.get(tempStringArray[0].trim()).put(tempStringArray[1].trim(), "");
                } else {
                    map.get(tempStringArray[0].trim()).put(tempStringArray[1].trim(), tempStringArray[2]);
                }
            }
        } catch (ParserConfigurationException e) {
            log.error("Error reading Parser configuration", e);
        } catch (SAXException e) {
            log.error(e);
        } catch (IOException e) {
            log.error("", e);
        }
        return map;
    }

    public HashMap<String,HashMap<String,String>> CdataToMap(Map<String,String> testdata,String CDataKey,Map<String,Response> prevResponseMap) {
        HashMap<String, HashMap<String,String>> map = new HashMap<>();
        try {
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(testdata.get(CDataKey)));
            NodeList fieldList= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is)
                    .getChildNodes().item(0).getChildNodes();
            Node field = fieldList.item(0);
            if (!(field instanceof CharacterData)) {
                throw new DOMException(DOMException.TYPE_MISMATCH_ERR, "Invalid CDATA");
            }
            String rawCData = ((CharacterData) field).getData().trim();
            for (String row : rawCData.split("\\n")) {
                String[] tempStringArray = row.split(Pattern.quote("|||"));
                map.putIfAbsent(tempStringArray[0].trim(),new HashMap<>());
                if (tempStringArray.length == 2) {
                    map.get(tempStringArray[0].trim()).put(tempStringArray[1].trim(), "");
                } else {
                    String temp="";
                    if(prevResponseMap != null && !prevResponseMap.isEmpty() && tempStringArray[2].matches("\\#\\{(.*)\\}")){
                        for(Map.Entry<String, Response> prevResponse : prevResponseMap.entrySet()){
                            if(prevResponse!=null){
                                try{
                                    Matcher matcher = Pattern.compile("\\#\\{(.*)\\}").matcher(tempStringArray[2]);
                                    matcher.find();
                                    String key = matcher.group(1);
                                    log.info("Value of Key is: "+ key);

                                String responseKey[]=key.split("\\|");

                                    if(prevResponse.getKey().equalsIgnoreCase(responseKey[0])){
                                        String arr[]=responseKey[1].split("\\-");
                                        if(arr[0].equalsIgnoreCase("responseBody")){
                                            JsonPath jpath=prevResponse.getValue().jsonPath();
                                            temp=jpath.get(arr[1]).toString();
                                            log.info("Value extracted from response body is: "+temp);
                                        }else if(arr[0].equalsIgnoreCase("responseHeader")){
                                            temp=prevResponse.getValue().getHeader(arr[1]);
                                            log.info("Value extracted from response header is: "+temp);
                                        }
                                    }
                                }catch (NullPointerException e){
                                    log.debug("Not able to extract relevant data from: "+prevResponse.getKey());
                                }

                            }
                        }
                    }
                    else{
                        temp=tempStringArray[2];
                    }

                    map.get(tempStringArray[0].trim()).put(tempStringArray[1].trim(), temp);
                }
            }
        } catch (ParserConfigurationException e) {
            log.error("Error reading Parser configuration", e);
        } catch (SAXException e) {
            log.error(e);
        } catch (IOException e) {
            log.error("", e);
        }
        return map;
    }
}
