package ir.sharif.fakequera.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "ownerClassId")
    public String ownerClassId;

    @ColumnInfo(name = "content")
    public String content;
}
