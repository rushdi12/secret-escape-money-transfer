package com.secret.escape.account.transfer.util;

import java.text.NumberFormat;


public class Convert {
	 
	
	public static String formatMinorDenominationAmount(String amount, int decimal_places, boolean group_thousands)	{
		double amount_in = Double.valueOf(amount)/ Math.pow(10, decimal_places);
		
	    NumberFormat number_formatter =  NumberFormat.getInstance();
	    number_formatter.setMinimumFractionDigits(decimal_places);
   	    number_formatter.setMaximumFractionDigits(decimal_places);
	    number_formatter.setGroupingUsed(group_thousands);
	    
	    return (number_formatter.format(amount_in));
	}
	
	public static String formatMinorDenominationAmount(String amount, int decimal_places, boolean group_thousands, String currency) {
		return currency + formatMinorDenominationAmount(amount, decimal_places, group_thousands);
	}
	
}
