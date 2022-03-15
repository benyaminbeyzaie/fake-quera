package ir.sharif.fakequera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class AddClassDialogFragment extends androidx.fragment.app.DialogFragment {

    Button cancle;
    Button ok;
    TextInputLayout textInputLayout;

    public AddClassDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        cancle = view.findViewById(R.id.cancleButton);
        ok = view.findViewById(R.id.addButton);
        textInputLayout = view.findViewById(R.id.dialogeinput);

        ok.setOnClickListener(view1 -> {

            String input = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
            if (input.equals("")) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("fill in the blanks");
            } else {
                ((TeacherMainActivity) getActivity()).takeDate(input);
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        cancle.setOnClickListener(view12 -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
}