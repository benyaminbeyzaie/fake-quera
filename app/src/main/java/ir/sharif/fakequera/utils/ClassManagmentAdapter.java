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

import ir.sharif.fakequera.ClassManagmentActivity;
import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Question;

public class ClassManagmentAdapter  extends RecyclerView.Adapter<ClassManagmentAdapter.QuestionHolder>{

    private List<Question> questions;
    private ClassManagmentActivity context;

//    public ClassManagmentAdapter(){
//        questions = new ArrayList<>();
//    }

    public ClassManagmentAdapter(ClassManagmentActivity context) {
        this.context = context;
        questions = new ArrayList<>();
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent, false);

        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        Question current = questions.get(position);
        holder.textViewTitle.setText(current.questionName);
        holder.textViewDes.setText(current.content);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.gotoQuestion(current.uid);
            }
        });
    }

    public Question getQuestion(int position){
        return questions.get(position);
    }

    public void setQuestions(List<Question> questions){
        this.questions = questions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDes;
        CardView cardView;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewStuName);
            textViewDes = itemView.findViewById(R.id.textViewStuAnswer);
            cardView = itemView.findViewById(R.id.answerCardView);
        }
    }

}
