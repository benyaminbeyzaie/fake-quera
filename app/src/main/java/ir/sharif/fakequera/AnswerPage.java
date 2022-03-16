package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class AnswerPage extends AppCompatActivity {

    private int classUid;
    private QuestionViewModel questionViewModel;
    private TextView questionTitle;
    private EditText questionContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_page);

        Intent i = getIntent();
        this.classUid = i.getIntExtra("uid", 0);

        questionTitle = findViewById(R.id.questionTitle);
        questionContent = findViewById(R.id.questionContent);
        questionContent.setClickable(false);
        questionContent.setFocusable(false);
        questionContent.setCursorVisible(false);

        questionViewModel = new QuestionViewModel(getApplication() , this.classUid);
        questionViewModel.getQuestion(this.classUid).observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                questionTitle.setText(question.questionName);
                questionContent.setText(question.content);
            }
        });


    }
}