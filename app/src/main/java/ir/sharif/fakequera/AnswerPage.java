package ir.sharif.fakequera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.entities.Question;
import ir.sharif.fakequera.utils.AnswerAdapter;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class AnswerPage extends AppCompatActivity {

    private int classUid;
    private QuestionViewModel questionViewModel;
    private TextView questionTitle;
    private EditText questionContent;
    RecyclerView recyclerView;


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

        recyclerView = findViewById(R.id.answerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(AnswerPage.this));

        AnswerAdapter answerAdapter = new AnswerAdapter(AnswerPage.this);
        recyclerView.setAdapter(answerAdapter);

        questionViewModel = new QuestionViewModel(getApplication() , this.classUid);
        questionViewModel.getQuestion(this.classUid).observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                questionTitle.setText(question.questionName);
                questionContent.setText(question.content);
            }
        });

        questionViewModel.getAnswerOfQuestuion(this.classUid).observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(List<Answer> answers) {
                Log.d("mym" , answers.toString());
                answerAdapter.setAnswers(answers);
            }
        });





    }
}