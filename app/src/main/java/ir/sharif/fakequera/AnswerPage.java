package ir.sharif.fakequera;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.utils.AnswerAdapter;
import ir.sharif.fakequera.viewModels.AnswerViewModel;
import ir.sharif.fakequera.viewModels.QuestionViewModel;

public class AnswerPage extends AppCompatActivity {

    private int classUid;
    private QuestionViewModel questionViewModel;
    private AnswerViewModel answerViewModel;
    private TextView questionTitle;
    private EditText questionContent;
    RecyclerView recyclerView;
    AnswerAdapter answerAdapter;


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

        answerAdapter = new AnswerAdapter(AnswerPage.this);
        recyclerView.setAdapter(answerAdapter);



        questionViewModel = new QuestionViewModel(getApplication());
        answerViewModel = new AnswerViewModel(getApplication());

        questionViewModel.question(this.classUid).observe(this, question -> {
            questionTitle.setText(question.questionName);
            questionContent.setText(question.content);
        });


        LiveData<List<Answer>> answerOfQuestuion = answerViewModel.getAnswerOfQuestuion();

        answerOfQuestuion.observe(this, answers -> {
            Log.d("mym" , answers.toString());
            answerAdapter.setAnswers(answers);
        });

        answerViewModel.getAnswerOfQuestuion(this.classUid);
    }

    public void giveScore(int position , Answer answer){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putDouble("score" , answer.score);
        bundle.putInt("position" , position);
        bundle.putString("content" , answer.content);
        ScoreDialoge scoreDialoge = new ScoreDialoge();
        scoreDialoge.setArguments(bundle);
        scoreDialoge.show(fragmentManager, "ScoreDialoge");
    }
    public void takeData(double score , int position) {
        Answer answer = answerAdapter.getAnswer(position);
        answer.score = score;
        answerViewModel.giveScoreToAnswer(answer);
    }
}