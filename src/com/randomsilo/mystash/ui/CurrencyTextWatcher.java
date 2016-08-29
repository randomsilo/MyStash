package com.randomsilo.mystash.ui;

import java.text.NumberFormat;

import android.text.Editable;
import android.text.TextWatcher;

public class CurrencyTextWatcher implements TextWatcher {

	boolean mEditing;

	public CurrencyTextWatcher() {
		mEditing = false;
	}

	public synchronized void afterTextChanged(Editable s) {
		if (!mEditing) {
			mEditing = true;

			String input = s.toString();
			 
			// format string when decimal point has more than 2 positions
			if(input.contains(".") && ((input.indexOf(".") +2) < input.length())) {  
				NumberFormat nf = NumberFormat.getCurrencyInstance();
	
				try {
					String formatted = nf.format(Double.parseDouble(input));
					s.replace(0, s.length(), formatted);
				} catch (NumberFormatException nfe) {
					s.clear();
				}
			}

			mEditing = false;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

}
