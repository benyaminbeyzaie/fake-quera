package ir.sharif.fakequera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ir.sharif.fakequera.utils.QueraSnackbar;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class LoginFragment extends Fragment {

    TextInputLayout loginUserInput;
    TextInputLayout loginPassInput;
    Button loginButton;
    UserViewModel userViewModel;


    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        loginUserInput = view.findViewById(R.id.loginUserInput);
        loginPassInput = view.findViewById(R.id.dialogeinput);
        loginButton = view.findViewById(R.id.renameButton);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.authenticateWithSavedCredentials();


        loginButton.setOnClickListener(v -> {
            String username = Objects.requireNonNull(loginUserInput.getEditText()).getText().toString();
            String password = Objects.requireNonNull(loginPassInput.getEditText()).getText().toString();
            userViewModel.authenticate(username, password);
        });

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                QueraSnackbar.showTopSnackBar(view, "User authenticated failed");
                return;
            }
            if (user.isCurrentUser) {
                if (user.isTeacher) {
                    QueraSnackbar.showTopSnackBar(view,"User authenticated successfully as teacher");
                    MainActivity mainActivity = (MainActivity) getActivity();
                    Objects.requireNonNull(mainActivity).takeTeacherData(user.uid);
                } else {
                    QueraSnackbar.showTopSnackBar(view,"User authenticated successfully as student");
                    MainActivity mainActivity = (MainActivity) getActivity();
                    assert mainActivity != null;
                    mainActivity.takeStudentData();
                }
            } else {
                QueraSnackbar.showTopSnackBar(view,"User authenticated failed");
            }
        });

        return view;
    }


}