package ir.sharif.fakequera;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class ScoreDialoge extends DialogFragment {

    TextView content;
    TextInputLayout textInputLayout;
    Button cancle;
    Button ok;

    int position;
    double previousScore;

    public ScoreDialoge() {
        // Required empty public constructor
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_score_dialoge, container, false);

        cancle = view.findViewById(R.id.cancleButton);
        ok = view.findViewById(R.id.addButton);
        textInputLayout = view.findViewById(R.id.dialogeinput2);
        content = view.findViewById(R.id.contentTextView);

        Bundle bundle = getArguments();
        previousScore = bundle.getDouble("score");
        position = bundle.getInt("position");

        textInputLayout.getEditText().setText(String.valueOf(previousScore));

        ok.setOnClickListener(view1 -> {
            String Score = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
            if (Score.equals("") ) {
                    textInputLayout.setError("Enter question title");
            }else {
                double score = Double.parseDouble(Score);
                ((AnswerPage)getActivity()).takeData(score , position);
                Objects.requireNonNull(getDialog()).dismiss();
            }

        });

        cancle.setOnClickListener(view12 -> {
            Objects.requireNonNull(getDialog()).dismiss();
        });

        return view;
    }
}