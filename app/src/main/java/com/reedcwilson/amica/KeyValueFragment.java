package com.reedcwilson.amica;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class KeyValueFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    private KeyValueDialogListener listener;
    private KeyValueDialogType type;
    private CrudType operation;
    private EditText key;
    private EditText value;

    public String getKey() {
        return key.getText().toString();
    }

    public String getValue() {
        return value.getText().toString();
    }

    public interface KeyValueDialogListener {
        void onKeyValueSet(KeyValueFragment dialog, KeyValueDialogType type, CrudType operation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.key_value_dialog, container, false);
        key = view.findViewById(R.id.key);
        value = view.findViewById(R.id.value);
        return view;
    }

    @Override
    public void setArguments(Bundle bundle) {
        type = (KeyValueDialogType) bundle.get(KeyValueDialogType.class.toString());
        operation = (CrudType) bundle.get(CrudType.class.toString());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.key_value_dialog, null))
                .setPositiveButton(R.string.set, (dialog, id) -> {
                    listener.onKeyValueSet(this, type, operation);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> KeyValueFragment.this.getDialog().cancel());
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the KeyValueDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the KeyValueDialogListener so we can send events to the host
            listener = (KeyValueDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement KeyValueDialogListener");
        }
    }
}
