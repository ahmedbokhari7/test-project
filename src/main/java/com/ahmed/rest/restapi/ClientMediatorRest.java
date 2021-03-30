/**
 * 
 */
/**
 * @author ahmedbokhari
 *
 */
package com.ahmed.rest.restapi;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ahmed.dataproviders.RestDataProvider;
import com.ahmed.testbase.TestBase;
import com.mysql.fabric.Response;


public class ClientMediatorRest extends TestBase {
    String tcName = null;
    static Logger log = LogManager.getLogger(ClientMediatorRest.class);
    RestHelper restService = RestHelper.getInstance();
    RestAPIHelper restAPIHelper= RestAPIHelper.getInstance();
    final String component="CLIENTMEDIATOR";


@Test(dataProvider = "transferRest",dataProviderClass = RestDataProvider.class,groups = {"client-mediator-rest-transfer",})
public void creditTransfer(Map<String, String> testData) {

    log.info("Scenario Name: " + testData.get("scenarioName"));
    log.info("Scenario Description: " + testData.get("scenarioDesc"));
    log.info("Expected Result: " + testData.get("expectedResult"));

    try
    {

//        String senderBalanceBeforeTransfer = query.getExactResellerBalance(query.getResellerIdUsingResellerMSISDN(testData.get("agentMSISDN")));
//       String receiverBalanceBeforeTransfer = query.getExactResellerBalance(testData.get("receiverID"));

        
        Response restResponse = restService.doRestTransaction(testData, component, "loginParameters", "requestParameters",null);
        restAPIHelper.responseValidation(testData,restResponse,"responseParameters",component,"REST.TRANSFER");
        if (testData.get("expectedResult").equals("SUCCESS")) {
            accountValidation.processBalanceCheck(true, testData.get("agentMSISDN"),testData.get("receiverMSISDN"), testData.get("amount"), senderBalanceBeforeTransfer,receiverBalanceBeforeTransfer);
        }else{
            accountValidation.processBalanceCheck(false, testData.get("agentMSISDN"),testData.get("receiverMSISDN"), testData.get("amount"), senderBalanceBeforeTransfer,receiverBalanceBeforeTransfer);
        }
        extent.getFeature().pass(MarkupHelper.createLabel("REST response verification passed for topup", ExtentColor.GREEN));
    }catch (Exception e)
    {
        log.error("Error occurred",e);
        Assert.fail();
    }
}

}
