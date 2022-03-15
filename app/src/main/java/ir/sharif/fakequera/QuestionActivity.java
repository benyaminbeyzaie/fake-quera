package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.sharif.fakequera.utils.QueraSnackbar;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class QuestionActivity extends AppCompatActivity {

    private LinearLayout linearParent;
    private TextView txtQuestion;
    private EditText edtAnswer;
    private Button btnSubmit;


    private QuestionViewModel viewModel;
    private int questionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        initViews();
        checkArguments();
        listeners();

        viewModel.getQuestionLiveData().observe(this , question -> {
            if (question == null){
                txtQuestion.setText("Question Not Found !");
            }else {
                txtQuestion.setText("Question : " + question.content);
            }
        });

        viewModel.getAnswerLiveData().observe(this , answer -> {
            edtAnswer.setText(answer.content);
        });

        viewModel.getMessage().observe(this , s -> {
            QueraSnackbar.showTopSnackBar(linearParent,s);
        });


        viewModel.question(questionId);
        viewModel.answer(questionId);


    }

    private void initViews() {
        linearParent = findViewById(R.id.linearParent);
        txtQuestion = findViewById(R.id.txtQuestion);
        edtAnswer = findViewById(R.id.edtAnswer);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void checkArguments(){
        questionId = getIntent().getIntExtra("question_id" , 0);
    }

    private void listeners() {
        btnSubmit.setOnClickListener(view -> {

            String answer = edtAnswer.getText().toString();

            if (answer.isEmpty()) return;

            viewModel.addAnswer(questionId , answer);

        });
    }
}