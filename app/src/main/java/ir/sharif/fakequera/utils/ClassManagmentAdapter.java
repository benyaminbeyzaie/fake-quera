package ir.sharif.fakequera.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_design, parent, false);

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
//        notifyItemChanged(questions.size()-1);
//        notifyDataSetChanged();
        Log.d("mym" , "questionset changed");
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDes;
        Button renameButton;
        CardView cardView;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewStuName);
            textViewDes = itemView.findViewById(R.id.textViewStuAnswer);
            cardView = itemView.findViewById(R.id.answerCardView);
            renameButton = itemView.findViewById(R.id.renameButton);
            renameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.renameAction(textViewTitle.getText().toString() , getAdapterPosition());
                }
            });
        }
    }

}
