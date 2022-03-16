package ir.sharif.fakequera.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Class {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "owner_teacher_id")
    public int ownerTeacherId;

    @ColumnInfo(name = "students")
    public ArrayList<Integer> students;
}
