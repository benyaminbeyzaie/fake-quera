package ir.sharif.fakequera.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.activities.MainActivity;
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginUserInput = view.findViewById(R.id.loginUserInput);
        loginPassInput = view.findViewById(R.id.dialogeinput);
        loginButton = view.findViewById(R.id.renameButton);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.authenticateWithSavedCredentials();


        loginButton.setOnClickListener(v -> {
            String username = Objects.requireNonNull(loginUserInput.getEditText()).getText().toString();
            String password = Objects.requireNonNull(loginPassInput.getEditText()).getText().toString();
            if (username.equals("") || password.equals("")) {
                if (username.equals("")) {
                    loginUserInput.setErrorEnabled(true);
                    loginUserInput.setError(getString(R.string.emptyWarning));
                } else {
                    loginUserInput.setErrorEnabled(false);
                }
                if (password.equals("")) {
                    loginPassInput.setErrorEnabled(true);
                    loginPassInput.setError(getString(R.string.emptyWarning));
                } else {
                    loginPassInput.setErrorEnabled(false);
                }
            } else {
                userViewModel.authenticate(username, password);
                loginUserInput.setErrorEnabled(false);
                loginPassInput.setErrorEnabled(false);
            }
        });

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                QueraSnackbar.showTopSnackBar(view, "User authenticated failed");
                return;
            }
            if (user.isCurrentUser) {
                if (user.isTeacher) {

                    QueraSnackbar.showTopSnackBar(view, "User authenticated successfully as teacher");
                    MainActivity mainActivity = (MainActivity) getActivity();
                    Objects.requireNonNull(mainActivity).takeTeacherData(user.uid);
                    refresh();
                } else {

                    QueraSnackbar.showTopSnackBar(view, "User authenticated successfully as student");
                    MainActivity mainActivity = (MainActivity) getActivity();
                    assert mainActivity != null;
                    mainActivity.takeStudentData(user.uid);
                    refresh();
                }
            } else {
                QueraSnackbar.showTopSnackBar(view, "User authenticated failed");
            }
        });

        return view;
    }


    public void refresh(){
        loginUserInput.setErrorEnabled(false);
        loginPassInput.setErrorEnabled(false);
        Objects.requireNonNull(loginUserInput.getEditText()).setText("");
        Objects.requireNonNull(loginPassInput.getEditText()).setText("");
    }

}