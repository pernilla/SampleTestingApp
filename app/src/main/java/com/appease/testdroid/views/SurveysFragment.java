package com.appease.testdroid.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appease.testdroid.R;
import com.appease.testdroid.common.Constant;
import com.appease.testdroid.views.dialogs.OkDialogFragment;

public class SurveysFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = SurveysFragment.class.getSimpleName();

    private EditText textFirstName;
    private EditText textLastName;
    private EditText textAddress;
    private EditText textPostalAddress;

    public SurveysFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(Constant.Debug) Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(Constant.Debug) Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(Constant.Debug) Log.d(TAG, "onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_surveys, container, false);

        textFirstName = (EditText) rootView.findViewById(R.id.editFirstName);
        textLastName = (EditText) rootView.findViewById(R.id.editLastName);
        textAddress = (EditText) rootView.findViewById(R.id.editSurveyAddress);
        textPostalAddress = (EditText) rootView.findViewById(R.id.editSurveyPostalAdress);

        Button buttonSend = (Button)rootView.findViewById(R.id.buttonSend);
        Button buttonCancel = (Button)rootView.findViewById(R.id.buttonCancel);

        buttonSend.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View v) {
        if(Constant.Debug) Log.d(TAG, "onClick()");

        if(v.getId() == R.id.buttonSend) {
            if(hasMissingFields()) {
                Toast.makeText(getActivity(), "All values are required", Toast.LENGTH_LONG).show();
            } else {
                if (Constant.Debug) Log.d(TAG, "onClick() Send");
                clearForm();
                Toast.makeText(getActivity(), "Done...", Toast.LENGTH_LONG).show();
            }
        }

        if(v.getId() == R.id.buttonCancel) {
            if(Constant.Debug) Log.d(TAG, "onClick() Cancel");
            Toast.makeText(getActivity(), "Cancel....", Toast.LENGTH_LONG).show();
            clearForm();
        }
    }

    private boolean hasMissingFields() {
        return TextUtils.isEmpty(textFirstName.getText()) || TextUtils.isEmpty(textLastName.getText()) || TextUtils.isEmpty(textAddress.getText()) || TextUtils.isEmpty(textPostalAddress.getText());
    }

    private void clearForm() {
        textFirstName.setText("");
        textLastName.setText("");
        textAddress.setText("");
        textPostalAddress.setText("");
    }
}