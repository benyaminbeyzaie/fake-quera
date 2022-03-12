package ir.sharif.fakequera.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Answer {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "owner_id")
    public int ownerId;

    @ColumnInfo(name = "question_id")
    public int questionId;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "score")
    public double score;
}
