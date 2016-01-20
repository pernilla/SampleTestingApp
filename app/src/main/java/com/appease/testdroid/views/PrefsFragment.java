package com.appease.testdroid.views;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.appease.testdroid.R;

public class PrefsFragment extends PreferenceFragment {

    public PrefsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //addPreferencesFromIntent();
        addPreferencesFromResource(R.xml.preferences);
    }
}