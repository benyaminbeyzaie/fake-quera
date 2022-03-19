package ir.sharif.fakequera.Fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.utils.QueraSnackbar;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class SignupFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton studentButton;
    RadioButton teacherButton;
    TextInputLayout additional;
    TextInputLayout signupUserInput;
    TextInputLayout signupPassInput;
    TextInputLayout name;
    Button signupButton;
    UserViewModel userViewModel;
    int mode = 0; // student

    public SignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        studentButton = view.findViewById(R.id.studentButton);
        teacherButton = view.findViewById(R.id.teacherButton);
        additional = view.findViewById(R.id.additionalInformationInput);
        name = view.findViewById(R.id.signupNameInput);
        signupButton = view.findViewById(R.id.signupButton);
        signupUserInput = view.findViewById(R.id.signupUserInput);
        signupPassInput = view.findViewById(R.id.signupPassInput);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == studentButton.getId()) {
                mode = 0;
                additional.setHint(R.string.studentNumberHint);
                Objects.requireNonNull(additional.getEditText()).setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (i == teacherButton.getId()) {
                mode = 1;
                Objects.requireNonNull(additional.getEditText()).setInputType(InputType.TYPE_CLASS_TEXT);
                additional.setHint(R.string.universityHint);
            }
        });

        signupButton.setOnClickListener(v -> {
            String username = Objects.requireNonNull(signupUserInput.getEditText()).getText().toString();
            String password = Objects.requireNonNull(signupPassInput.getEditText()).getText().toString();
            String Name = Objects.requireNonNull(name.getEditText()).getText().toString();
            String addition = Objects.requireNonNull(additional.getEditText()).getText().toString();

            if (username.equals("") || password.equals("") || Name.equals("") || addition.equals("")) {
                if (username.equals("")) {
                    signupUserInput.setErrorEnabled(true);
                    signupUserInput.setError(getString(R.string.emptyWarning));
                } else {
                    signupUserInput.setErrorEnabled(false);
                }
                if (password.equals("")) {
                    signupPassInput.setErrorEnabled(true);
                    signupPassInput.setError(getString(R.string.emptyWarning));
                } else {
                    signupPassInput.setErrorEnabled(false);
                }
                if (Name.equals("")) {
                    name.setErrorEnabled(true);
                    name.setError(getString(R.string.emptyWarning));
                } else {
                    name.setErrorEnabled(false);
                }
                if (addition.equals("")) {
                    additional.setErrorEnabled(true);
                    additional.setError(getString(R.string.emptyWarning));
                } else {
                    additional.setErrorEnabled(false);
                }
            } else {
                if (mode == 0) {
                    userViewModel.insertStudent(new Student(username, password, Name, addition));
                } else {
                    userViewModel.insertTeacher(new Teacher(username, password, Name, addition));
                }
            }
        });

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> QueraSnackbar.showTopSnackBar(view, userViewModel.getMessage().getValue()));

        return view;
    }
}