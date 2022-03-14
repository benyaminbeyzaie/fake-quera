package ir.sharif.fakequera.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.sharif.fakequera.R;
import ir.sharif.fakequera.entities.Class;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassHolder> {

    private List<Class> classes;
    private Context context;

    public ClassAdapter(List<Class> classes, Context context) {
        this.classes = classes;
        this.context = context;
    }

    public ClassAdapter() {
        classes = new ArrayList<>();
    }

    public ClassAdapter(Context context) {
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return classes.size();

    }

    public Class getNote(int position) {
        return classes.get(position);
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
        notifyDataSetChanged();
    }

    class ClassHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDes;

        public ClassHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewClassName);
            textViewDes = itemView.findViewById(R.id.textViewDetail);
        }
    }


}
