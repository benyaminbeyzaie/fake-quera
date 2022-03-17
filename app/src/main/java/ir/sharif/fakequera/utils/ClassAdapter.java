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

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.TeacherMainActivity;
import ir.sharif.fakequera.entities.Class;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassHolder> {

    private List<Class> classes;
    private TeacherMainActivity context;

    public ClassAdapter(List<Class> classes, TeacherMainActivity context) {
        this.classes = classes;
        this.context = context;
    }

    public ClassAdapter() {
        classes = new ArrayList<>();
    }

    public ClassAdapter(TeacherMainActivity context) {
        this.context = context;
        classes = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent, false);

        return new ClassHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ClassHolder holder, int position) {

        Class current = classes.get(position);
        holder.textViewTitle.setText(current.className);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.goToQuestion(current.uid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classes.size();

    }

    public Class getNote(int position) {
        return classes.get(position);
    }

    public void setClasses(List<Class> classes) {
        Log.d("mym" , "here");
        this.classes = classes;
        notifyItemInserted(classes.size()-1);
//        notifyDataSetChanged();
    }

    class ClassHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDes;
        private CardView cardView;

        public ClassHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewStuName);
            textViewDes = itemView.findViewById(R.id.textViewStuAnswer);
            cardView = itemView.findViewById(R.id.answerCardView);
        }
    }


}
