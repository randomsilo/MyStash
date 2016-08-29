package com.randomsilo.mystash.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import android.util.Log;

public class MoneyHelper {

	public static Double convertString(String s) {
		Double d = null;
		
		if(s != null) {
			try {
				d = Double.parseDouble(s);
			} catch(Exception e) {
				Log.e("MyStash", "convertString: " + s, e);
			}
		}
		
		return d;
	}
	
	public static String convertDouble(Double d) {
		String s ="";
		
		if(d != null) {
			try {
				NumberFormat nf = NumberFormat.getCurrencyInstance();
				DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
				decimalFormatSymbols.setCurrencySymbol("");
				((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
				s = nf.format(d);
			} catch(Exception e) {
				Log.e("MyStash", "convertDouble: " + d.toString(), e);
			}
		}
		
		return s;
	}
}
