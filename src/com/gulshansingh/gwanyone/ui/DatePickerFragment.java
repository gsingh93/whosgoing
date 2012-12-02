package com.gulshansingh.gwanyone.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.holoeverywhere.app.DatePickerDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.DialogFragment;
import org.holoeverywhere.widget.DatePicker;
import org.holoeverywhere.widget.TextView;

import android.os.Bundle;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private static TextView textView;
	private DatePickerDialog d;
	private static Date date;

	public static void initView(TextView v, Date d) {
		textView = v;
		date = d;
	}

	public DatePickerDialog getDatePicker() {
		return d;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		d = new DatePickerDialog(getActivity(), this, year, month, day);
		return d;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		date = cal.getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"EEE, MMM d, yyyy", Locale.US);
		textView.setText(dateFormatter.format(cal.getTime()));
	}

	public static Date getDate() {
		return date;
	}
}
