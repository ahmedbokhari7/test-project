/**
 * 
 */
/**
 * @author ahmedbokhari

 *
 */
package com.ahmed.common.utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ahmed.enums.ConfigPath;
import com.ahmed.enums.Customer;
import com.ahmed.enums.Product;

public class HelperClass {
	private static Logger log = LogManager.getLogger(HelperClass.class);
	private String xmlDataSet;
	private Customer customer = Customer.ERS_STD;
	private Product product = Product.ERS;
	private final boolean skipBugs;
	private String restHost;
	String testDataFile="src/test/resources/customer/ers-std/test-data/data_ers.xml";
	private static ConfigPropertyReader reader = ConfigPropertyReader.getInstance();

	public HelperClass() {
		
		System.out.println("AHMED HERE 1");
//		System.out.println(System.getProperty("testDataFile"));
//		if (System.getProperty("testDataFile") != null
//				&& (FilenameUtils.getExtension(System.getProperty("testDataFile")).equalsIgnoreCase("xml")
//						|| FilenameUtils.getExtension(System.getProperty("testDataFile")).equalsIgnoreCase("json"))) {
//			xmlDataSet = System.getProperty("testDataFile");
//			System.out.println("AHMED 1");
//			log.debug("Provided test data source path: " + xmlDataSet);
//		} else {
//			System.out.println("AHMED 2");
//			xmlDataSet = ConfigPath.DEFAULT_TEST_DATA + customer.getCustomer() + "/test-data/data_"
//					+ product.getProductName() + ".xml";
//			log.warn("No or invalid test data file provided. Taking " + xmlDataSet + " by default");
//		}
		xmlDataSet=testDataFile;
		

		if (System.getProperty("skipBugs") != null)
			skipBugs = Boolean.getBoolean("skipBugs");
		else
			skipBugs = false;

	}
	public String getCustomer()
    {
        return customer.getCustomer();
    }

	public static HelperClass getInstance() {

		return HelperClassHelper.instance;

	}

	private static class HelperClassHelper {
		private static final HelperClass instance = new HelperClass();
	}

	public String getXmlDataSet() {
		return xmlDataSet;
	}

	public boolean isSkipBugs() {
		// TODO Auto-generated method stub
		return skipBugs;
	}

	public String returnUSSDUrl(String url, String[] options) {
		long sessionId = getRandomSessionId();
		return formUssdUrl(sessionId, url, options);
	}

	public long getRandomSessionId() {
		Date date = new Date();
		return new Timestamp(date.getTime()).getTime();
	}
	
    private String formUssdUrl(long sessionId,String url,String [] options) throws NullPointerException,IndexOutOfBoundsException
    {
        String appEnvironment = "10.10.0.62";//hard coded given
        url = url.replace("HOST",appEnvironment)
                .replace("SESSION_ID",String.valueOf(sessionId));
        if(options != null && options.length >= 1)
        {
            for(int i=0;i<options.length;i++)
            {
                url = url.replace("${"+i+"}",options[i].replace(" ","%20"));
            }
        }
        log.info("USSD url: "+url);
        return url;
    }

    public String returnRestBaseUri(String component)
	{
		String uri=null;
		try
		{
			String restProtocol = reader.readProperties("REST."+ component.toUpperCase()+".PROTOCOL");
			String restPort = reader.readProperties("REST."+ component.toUpperCase()+".PORT");
		//	String server = restHost;
			String server = "localhost";
			uri = restProtocol + server + restPort;
			log.info("Generated REST API url: "+uri);
			System.out.println(uri);

		}
		catch (Exception e)
		{
			log.error("Error occurred",e);
		}
		return uri;
	}
    private String calculateDesiredDate(Date date,String dateFormat, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, offset);
        Date newDate = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(newDate);
    }


    public String getDesiredDate(String dateKeyword,String dateFormat) {
        dateKeyword = dateKeyword.toLowerCase();
        Date date = new Date();
        String desiredDate;
        switch (dateKeyword)
        {
            case "yesterday":
                desiredDate = calculateDesiredDate(date,dateFormat,-1);
                break;
            case "today":
                desiredDate = calculateDesiredDate(date,dateFormat,0);
                break;
            case "tomorrow":
                desiredDate = calculateDesiredDate(date,dateFormat,1);
                break;
            default:
                desiredDate = dateKeyword;
                break;
        }
        return desiredDate;
    }

    
	public String returnRestBaseUri()
	{
		String uri=null;
		try
		{
			String restProtocol = reader.readProperties("REST.PROTOCOL");
			String restPort = reader.readProperties("REST.PORT");
			String server = restHost;
			uri = restProtocol + server + restPort;
			log.info("Generated REST API url: "+uri);

		}
		catch (Exception e)
		{
			log.error("Error occurred",e);
		}
		return uri;
	}


}