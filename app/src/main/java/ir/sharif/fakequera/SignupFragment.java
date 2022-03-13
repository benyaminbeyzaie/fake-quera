package ir.sharif.fakequera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ir.sharif.fakequera.entities.Student;
import ir.sharif.fakequera.entities.Teacher;
import ir.sharif.fakequera.utils.QueraSnackbar;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class SignupFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton studentButton;
    RadioButton teacherButton;
    TextInputLayout textInputLayout;
    TextInputLayout signupUserInput;
    TextInputLayout signupPassInput;
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
        textInputLayout = view.findViewById(R.id.additionalInformationInput);
        signupButton = view.findViewById(R.id.signupButton);
        signupUserInput = view.findViewById(R.id.signupUserInput);
        signupPassInput = view.findViewById(R.id.signupPassInput);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == studentButton.getId()) {
                mode = 0;
                textInputLayout.setHint(R.string.studentNumberHint);
            } else if (i == teacherButton.getId()) {
                mode = 1;
                textInputLayout.setHint(R.string.universityHint);
            }
        });

        signupButton.setOnClickListener(v -> {
            String username = Objects.requireNonNull(signupUserInput.getEditText()).getText().toString();
            String password = Objects.requireNonNull(signupPassInput.getEditText()).getText().toString();
            if (mode == 0) {
                userViewModel.insertStudent(new Student(username, password));
            } else {
                userViewModel.insertTeacher(new Teacher(username, password));
            }
        });

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                QueraSnackbar.showTopSnackBar(view, "User authenticated failed");
                return;
            }
            if (user.isCurrentUser) {
                if (user.isTeacher) {
                    QueraSnackbar.showTopSnackBar(view,"User authenticated successfully as teacher");
                } else {
                    QueraSnackbar.showTopSnackBar(view,"User authenticated successfully as student");
                }
            } else {
                QueraSnackbar.showTopSnackBar(view,"User authenticated failed");
            }
        });

        return view;
    }
}