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

public class RenameDialogFragment extends DialogFragment {

    Button cancle;
    Button ok;
    TextInputLayout textInputLayout;
    String name;
    int position;

    public RenameDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rename_dialog, container, false);
        cancle = view.findViewById(R.id.renameButton);
        ok = view.findViewById(R.id.addButton);
        textInputLayout = view.findViewById(R.id.dialogeinput);

        Bundle bundle = getArguments();

        assert bundle != null;
        name = bundle.getString("name");
        position = bundle.getInt("position");
        Objects.requireNonNull(textInputLayout.getEditText()).setText(name);

        ok.setOnClickListener(view1 -> {

            String input = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
            if (input.equals("")) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("fill in the blanks");
            } else {
                ((ClassManagmentActivity) requireActivity()).rename(input, position);
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        cancle.setOnClickListener(view12 -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
}