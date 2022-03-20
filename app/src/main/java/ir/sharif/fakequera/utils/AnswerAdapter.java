package ir.sharif.fakequera.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.sharif.fakequera.activities.AnswersActivity;
import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Answer;
import ir.sharif.fakequera.viewModels.UserViewModel;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {

    private List<Answer> answers;
    private final AnswersActivity context;
    private final UserViewModel userViewModel;


    public AnswerAdapter(AnswersActivity context) {
        this.context = context;
        this.answers = new ArrayList<>();
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
        holder.scoreView.setText("".concat(current.score + ""));
        holder.cardView.setOnClickListener(view -> context.giveScore(holder.getAdapterPosition(), current));

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
        notifyDataSetChanged();
//        notifyDataSetChanged();
    }

    static class AnswerHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDes;
        TextView scoreView;
        private final CardView cardView;

        public AnswerHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewStuName);
            textViewDes = itemView.findViewById(R.id.textViewStuAnswer);
            scoreView = itemView.findViewById(R.id.textViewScore2);
            cardView = itemView.findViewById(R.id.answerCardView);
        }
    }
}
