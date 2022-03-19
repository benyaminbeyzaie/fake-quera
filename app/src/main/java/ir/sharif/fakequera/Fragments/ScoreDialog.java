package ir.sharif.fakequera.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.activities.AnswersActivity;


public class ScoreDialog extends DialogFragment {

    TextView content;
    TextInputLayout textInputLayout;
    Button cancel;
    Button ok;

    int position;
    double previousScore;
    String answer;

    public ScoreDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score_dialoge, container, false);

        cancel = view.findViewById(R.id.renameButton);
        ok = view.findViewById(R.id.addButton);
        textInputLayout = view.findViewById(R.id.dialogeinput2);
        content = view.findViewById(R.id.contentTextView);

        Bundle bundle = getArguments();
        assert bundle != null;
        previousScore = bundle.getDouble("score");
        position = bundle.getInt("position");
        answer = bundle.getString("content");


        Objects.requireNonNull(textInputLayout.getEditText()).setText(String.valueOf(previousScore));
        content.setText(answer);

        ok.setOnClickListener(view1 -> {
            String Score = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
            if (Score.equals("")) {
                textInputLayout.setError("Enter question title");
            } else {
                double score = Double.parseDouble(Score);
                ((AnswersActivity) requireActivity()).takeData(score, position);
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        cancel.setOnClickListener(view12 -> Objects.requireNonNull(getDialog()).dismiss());

        return view;
    }
}