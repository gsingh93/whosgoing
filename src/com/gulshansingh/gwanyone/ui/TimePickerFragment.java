package com.gulshansingh.gwanyone.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.DialogFragment;
import org.holoeverywhere.app.TimePickerDialog;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.TimePicker;

import android.os.Bundle;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private static TextView textView;
	private TimePickerDialog d;
	private static Date date;

	public static void initView(TextView v, Date d) {
		textView = v;
		date = d;
	}

	public TimePickerDialog getDatePicker() {
		return d;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		d = new TimePickerDialog(getActivity(), this, hour, minute, false);
		return d;
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		date = cal.getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a",
				Locale.US);
		textView.setText(dateFormatter.format(cal.getTime()));
	}
}
