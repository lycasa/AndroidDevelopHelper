package com.yunchao.androiddevelophelper.demos.weather;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.yunchao.androiddevelophelper.R;

public class SetupActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setup);
	}
}
