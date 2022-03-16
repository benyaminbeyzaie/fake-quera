package ir.sharif.fakequera;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class DialogFragment extends androidx.fragment.app.DialogFragment {

    Button cancle;
    Button ok;
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    public DialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        cancle = view.findViewById(R.id.cancleButton);
        ok = view.findViewById(R.id.addButton);
        textInputLayout = view.findViewById(R.id.dialogeinput);
        textInputEditText = view.findViewById(R.id.dialogInputText);

        ok.setOnClickListener(view1 -> {

            String input = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
            if (input.equals("")){
                textInputLayout.setError("fill in the blanks");
            }else {
                ((TeacherMainActivity) getActivity()).takeDate(input);
                getDialog().dismiss();
            }
        });

        cancle.setOnClickListener(view12 -> {
            Objects.requireNonNull(getDialog()).dismiss();
        });

        return view;
    }
}