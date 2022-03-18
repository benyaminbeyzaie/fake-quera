package ir.sharif.fakequera.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.sharif.fakequera.AnswerPage;
import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.viewModels.QuestionViewModel;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {

    private List<Answer> answers;
    private AnswerPage context;
    private QuestionViewModel questionViewModel;
    private UserViewModel userViewModel;

    public AnswerAdapter(List<Answer> asnwers, AnswerPage context) {
        this.answers = asnwers;
        this.context = context;
        questionViewModel = new QuestionViewModel(context.getApplication());
        userViewModel = new UserViewModel(context.getApplication());
    }

    public AnswerAdapter(AnswerPage context) {
        this.context = context;
        this.answers = new ArrayList<>();
        questionViewModel = new QuestionViewModel(context.getApplication());
        userViewModel = new UserViewModel(context.getApplication());
    }

    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_design, parent, false);

        return new AnswerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
        Answer current = answers.get(position);
        holder.textViewTitle.setText(userViewModel.getUserName(current.ownerId));
        holder.textViewDes.setText(current.content);
        holder.scoreView.setText("".concat(current.score+""));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.giveScore(holder.getAdapterPosition() , current);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.answers.size();
    }

    public Answer getAnswer(int position) {
        return answers.get(position);
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;

        Log.d("mym", "changed");
        notifyDataSetChanged();
//        notifyDataSetChanged();
    }

    class AnswerHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDes;
        TextView scoreView;
        private CardView cardView;

        public AnswerHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewStuName);
            textViewDes = itemView.findViewById(R.id.textViewStuAnswer);
            scoreView = itemView.findViewById(R.id.textViewScore2);
//            scoreView = itemView.findViewById(R.id.textViewScore);
            cardView = itemView.findViewById(R.id.answerCardView);
            Log.d("mym", "score is".concat(String.valueOf(scoreView == null)));
            Log.d("mym", "des is".concat(String.valueOf(textViewDes == null)));
        }
    }
}
