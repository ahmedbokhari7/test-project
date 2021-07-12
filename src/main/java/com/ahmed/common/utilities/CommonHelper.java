package com.ahmed.common.utilities;

import com.google.common.base.Splitter;
import com.ahmed.common.database.DatabaseQuery;
import com.ahmed.enums.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ahmed on 01/02/21.
 */
public class CommonHelper
{
	private static final Logger log = LogManager.getLogger(CommonHelper.class);
	private HelperClass helperClass = HelperClass.getInstance();
	private ConfigPropertyReader reader = ConfigPropertyReader.getInstance();
	private DatabaseQuery query=DatabaseQuery.getInstance();
	String ersReferencePattern="[0-9]{25,25}";
	String time_pattern="[0-9]{2,2}:[0-9]{2,2}:[0-9]{2,2}";

	private CommonHelper() {
		log.info("Commonhelper Initialized");
	}

	public static class CommonModuleHelper {
		private static final CommonHelper instance = new CommonHelper();
	}

	public static CommonHelper getInstance() {
		return CommonModuleHelper.instance;
	}

	public String processMessageBuild(String unprocessedExpectedResult, Map<String, String> ussdInfo) {

		if (unprocessedExpectedResult.contains("CURRENCY")) {
			String currency = reader.readProperties("CURRENCY");
			unprocessedExpectedResult = unprocessedExpectedResult.replace("CURRENCY", currency);
		}

		if (unprocessedExpectedResult.contains("ERS_REFERENCE")) {
			unprocessedExpectedResult = unprocessedExpectedResult.replace("ERS_REFERENCE", ersReferencePattern);
		}

		if (unprocessedExpectedResult.contains("DATE")) {
			String date = DateManager.getUssdResponseDate();
			unprocessedExpectedResult = unprocessedExpectedResult.replace("DATE", date);
		}

		if (unprocessedExpectedResult.contains("TIME")) {
			unprocessedExpectedResult = unprocessedExpectedResult.replace("TIME", time_pattern);
		}

		if (unprocessedExpectedResult.contains("STD_PLAN_NAME")) {
			String stdPlanName = "";
			if (ussdInfo.containsKey("stdPlanName")) {
				stdPlanName = ussdInfo.get("stdPlanName");
			}
			unprocessedExpectedResult = unprocessedExpectedResult.replace("STD_PLAN_NAME", stdPlanName);
		}

		if (unprocessedExpectedResult.contains("PLAN_NAME")) {
			String planName = "";
			if (ussdInfo.containsKey("planName")) {
				planName = ussdInfo.get("planName");
			}
			unprocessedExpectedResult = unprocessedExpectedResult.replace("PLAN_NAME", planName);
		}

//		if (unprocessedExpectedResult.contains("SENDER_BALANCE")) {
//			String senderMSISDN = "";
//			if (ussdInfo.containsKey("senderMSISDN")) {
//				senderMSISDN = ussdInfo.get("senderMSISDN");
//			}
//			String balance = query.getExactResellerBalance(query.getResellerIdUsingResellerMSISDN(senderMSISDN));
//			balance = helperClass.formatToMoney(balance);
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("SENDER_BALANCE", balance);
//		}
//
//		if (unprocessedExpectedResult.contains("RECEIVER_BALANCE")) {
//			String receiverMSISDN = "";
//			if (ussdInfo.containsKey("receiverMSISDN")) {
//				receiverMSISDN = ussdInfo.get("receiverMSISDN");
//			}
//			String balance = query.getExactResellerBalance(query.getResellerIdUsingResellerMSISDN(receiverMSISDN));
//			balance = helperClass.formatToMoney(balance);
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("RECEIVER_BALANCE", balance);
//		}

//		if (unprocessedExpectedResult.contains("BALANCE")) {
//			String resellerMSISDN = "";
//			if (ussdInfo.containsKey("resellerMSISDN")) {
//				resellerMSISDN = ussdInfo.get("resellerMSISDN");
//			} else if (ussdInfo.containsKey("agentMSISDN")) {
//				resellerMSISDN = ussdInfo.get("agentMSISDN");
//			}
//	//		String balance = query.getExactResellerBalance(query.getResellerIdUsingResellerMSISDN(resellerMSISDN));
//			balance = helperClass.formatToMoney(balance);
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("BALANCE", balance);
//		}

//		if (unprocessedExpectedResult.contains("TFC")) {
//			Map<String, String> properties = Splitter.on(";").withKeyValueSeparator(":").split(ussdInfo.get("senderTFC"));
//			for (String key : properties.keySet()) {
//				unprocessedExpectedResult = unprocessedExpectedResult.replace("TFC:" + key.toUpperCase(), helperClass.formatToMoney(properties.get(key).replace("-", "")));
//			}
//			if (unprocessedExpectedResult.contains("TFC:[")) {
//				Matcher n = Pattern.compile("TFC:\\[[-+A-Za-z0-9.]+\\]")
//						.matcher(unprocessedExpectedResult);
//
//				while (n.find()) {
//					String key = n.group();
//					String value = key.toUpperCase().substring(5, key.length() - 1);
//					for (String s : properties.keySet()) {
//						value = value.replace(s.toUpperCase(), properties.get(s).replace("-",""));
//					}
//					try {
//						value = value.replace("AMOUNT", ussdInfo.get("amount"));
//						value=((new ScriptEngineManager().getEngineByName("JavaScript")).eval(value)).toString();
//					} catch (ScriptException e) {
//						log.error(e);
//					}
//					unprocessedExpectedResult = unprocessedExpectedResult.replace(key, helperClass.formatToMoney(value));
//				}
//			}
//		}
//
//		if (unprocessedExpectedResult.contains("INPUTAMOUNT")) {
//			String amount = ussdInfo.get("amount");
//			if(helperClass.getCustomer().equals(Customer.ZAIN_SA.getCustomer()))
//			{
//				amount =  helperClass.formatToMoney(ussdInfo.get("amount"),false);
//			}
//			else
//			{
//				amount = helperClass.formatToMoney(ussdInfo.get("amount"));
//			}
//
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("INPUTAMOUNT", amount);
//		}
//
//		if (unprocessedExpectedResult.contains("AMOUNT")) {
//
//			String  amount = helperClass.formatToMoney(ussdInfo.get("amount"));
//
//
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("AMOUNT", amount);
//		}
//
//		if (unprocessedExpectedResult.contains("SUBSCRIBER_MSISDN")) {
//			String subscriberMSISDN = "";
//			if (ussdInfo.containsKey("subscriberMSISDN")) {
//				subscriberMSISDN = ussdInfo.get("subscriberMSISDN");
//			}
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("SUBSCRIBER_MSISDN", subscriberMSISDN);
//		}
//
//		if (unprocessedExpectedResult.contains("AGENT_MSISDN")) {
//			String agentMSISDN = "";
//			if (ussdInfo.containsKey("agentMSISDN")) {
//				agentMSISDN = ussdInfo.get("agentMSISDN");
//			}
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("AGENT_MSISDN", agentMSISDN);
//		}
//
//		if (unprocessedExpectedResult.contains("SENDER_MSISDN")) {
//			String senderMSISDN = "";
//			if (ussdInfo.containsKey("senderMSISDN")) {
//				senderMSISDN = ussdInfo.get("senderMSISDN");
//			}
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("SENDER_MSISDN", senderMSISDN);
//		}
//
//		if (unprocessedExpectedResult.contains("AGENT_ID")) {
//			String agentID = "";
//			if (ussdInfo.containsKey("agentID")) {
//				agentID = ussdInfo.get("agentID");
//			} else if (ussdInfo.containsKey("agentMSISDN")) {
//				agentID = query.getResellerIdUsingResellerMSISDN(ussdInfo.get("agentMSISDN"));
//			}
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("AGENT_ID", agentID);
//		}
//
//		if (unprocessedExpectedResult.contains("SENDER_ID")) {
//			String agentID = "";
//			if (ussdInfo.containsKey("senderID")) {
//				agentID = ussdInfo.get("senderID");
//			} else if (ussdInfo.containsKey("senderMSISDN")) {
//				agentID = query.getResellerIdUsingResellerMSISDN(ussdInfo.get("senderMSISDN"));
//			}
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("SENDER_ID", agentID);
//		}
//
//		if (unprocessedExpectedResult.contains("RECEIVER_ID"))
//		{
//			String receiverID = "";
//			if (ussdInfo.containsKey("receiverID"))
//			{
//				receiverID = ussdInfo.get("receiverID");
//			}
//			else if (ussdInfo.containsKey("receiverMSISDN"))
//			{
//				receiverID = query.getResellerIdUsingResellerMSISDN(ussdInfo.get("receiverMSISDN"));
//			}
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("RECEIVER_ID", receiverID);
//		}
//
//        /*
//         *RECEIVER_MSISDN is expected as full qualified msisdn in ussd-transaction-status.
//         *RECEIVER_MSISDN is expected with no changes in ___fill here___.
//         */
//		if (unprocessedExpectedResult.contains("RECEIVER_MSISDN")) {
//			String receiverMSISDN = "";
//			if (ussdInfo.containsKey("anonymousID")) {
//				receiverMSISDN = ussdInfo.get("anonymousID");
//			} else if (ussdInfo.containsKey("tsn")) {
//				receiverMSISDN = ussdInfo.get("tsn");
//			} else if (ussdInfo.containsKey("receiverMSISDN")) {
//				receiverMSISDN = helperClass.formatMSISDN(ussdInfo.get("receiverMSISDN"));
//			}
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("RECEIVER_MSISDN", receiverMSISDN);
//		}
//
//		if (unprocessedExpectedResult.contains("TSN")) {
//
//			String resellerMSISDN = "";
//			if (ussdInfo.containsKey("resellerMSISDN")) {
//				resellerMSISDN = ussdInfo.get("resellerMSISDN");
//			} else if (ussdInfo.containsKey("agentMSISDN")) {
//				resellerMSISDN = ussdInfo.get("agentMSISDN");
//			} else if (ussdInfo.containsKey("initiatorMSISDN")) {
//				resellerMSISDN = ussdInfo.get("initiatorMSISDN");
//			}
//
//			String tsn = query.getTsn(resellerMSISDN);
//			unprocessedExpectedResult = unprocessedExpectedResult.replace("TSN", tsn);
//		}
		log.info("expected result created as :=> " + unprocessedExpectedResult);
		return unprocessedExpectedResult;
	}
}
