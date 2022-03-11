package ir.sharif.fakequera;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    TextInputEditText username;
    TextInputEditText password;
    Button login;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        login = view.findViewById(R.id.loginButton);
        username = view.findViewById(R.id.loginusernametext);
        password = view.findViewById(R.id.loginpasstext);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.takeData(userName , passWord);
                
            }
        });

        return view;
    }

    
}