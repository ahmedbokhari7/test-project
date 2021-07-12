package com.ahmed.common.utilities;


import com.ahmed.common.utilities.HelperClass;

/**
 * Created by kalyanroy on 01/02/21.
 */
public class DateManager
{
	private static HelperClass helperClass = HelperClass.getInstance();

	public static String getUssdResponseDate() {
		switch (helperClass.getCustomer()) {
			case "zm-mtn":
				return helperClass.getDesiredDate("today", "MMM d, yyyy");
			case "ng-mtn":
				return helperClass.getDesiredDate("today", "MMM d, yyyy");
			case "gh-mtn":
				return helperClass.getDesiredDate("today", "MMM d, yyyy");
			case "ers-std":
				return helperClass.getDesiredDate("today", "MMM d, yyyy");
			case "bj-mtn":
				return helperClass.getDesiredDate("today", "MMM d, yyyy");
			default:
				return helperClass.getDesiredDate("today", "yyyy-MM-dd");
		}
	}
}
