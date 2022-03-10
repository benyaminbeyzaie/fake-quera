package ir.sharif.fakequera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

public class SignupFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton studentButton;
    RadioButton teacherButton;
//    TextInputEditText textInputEditText;
    TextInputLayout textInputLayout;
    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signup, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        studentButton = view.findViewById(R.id.studentButton);
        teacherButton = view.findViewById(R.id.teacherButton);
        textInputLayout = view.findViewById(R.id.additionalInformationInput);


        System.out.println(radioGroup.getId());
        System.out.println(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == studentButton.getId()){
                textInputLayout.setHint(R.string.studentNumberHint);

            }else if (i == teacherButton.getId()){
                textInputLayout.setHint(R.string.universityHint);

            }

        });

        return view;
    }
}