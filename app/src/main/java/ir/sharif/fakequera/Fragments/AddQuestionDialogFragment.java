package ir.sharif.fakequera.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.activities.ClassManagmentActivity;


public class AddQuestionDialogFragment extends DialogFragment {

    Button cancel;
    Button ok;
    TextInputLayout name;
    TextInputLayout content;

    public AddQuestionDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_quesion_dialog, container, false);

        cancel = view.findViewById(R.id.renameButton);
        ok = view.findViewById(R.id.addButton);
        name = view.findViewById(R.id.dialogeinput);
        content = view.findViewById(R.id.dialogeinput2);

        ok.setOnClickListener(view1 -> {
            String nameInput = Objects.requireNonNull(name.getEditText()).getText().toString();
            String contentInput = Objects.requireNonNull(content.getEditText()).getText().toString();
            if (nameInput.equals("") || contentInput.equals("")) {
                if (nameInput.equals("")) {
                    name.setError("Enter question title");
                }
                if (contentInput.equals("")) {
                    content.setError("Enter question Description");
                }
            } else {
                ((ClassManagmentActivity) requireActivity()).takeData(nameInput, contentInput);
                Objects.requireNonNull(getDialog()).dismiss();
            }

        });

        cancel.setOnClickListener(view12 -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
}