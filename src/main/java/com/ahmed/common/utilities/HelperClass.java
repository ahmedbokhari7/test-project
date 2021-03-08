/**
 * 
 */
/**
 * @author ahmedbokhari
 *
 */
package com.ahmed.common.utilities;


public class HelperClass {
	
	
	
    public static HelperClass getInstance()
    {

        return HelperClassHelper.instance;

    }

    private static class HelperClassHelper {
        private static final HelperClass instance = new HelperClass();
    }

	
}