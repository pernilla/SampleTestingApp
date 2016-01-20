package com.appease.testdroid.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appease.testdroid.R;
import com.appease.testdroid.listeners.OkClickListener;


public class OkDialogFragment extends DialogFragment {

    private static Fragment parentFragment;

    public static OkDialogFragment newInstance(int title, Fragment parentFragment) {
        OkDialogFragment.parentFragment = parentFragment;
        OkDialogFragment frag = new OkDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
//                .setPositiveButton(R.string.yes, parentFragment.getActivity())
//                .setNegativeButton(R.string.no, parentFragment.getActivity())
                .create();
    }
}