package ir.sharif.fakequera.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "ownerClassId")
    public int ownerClassId;

    @ColumnInfo(name = "content")
    public String content;


    public Question(int ownerClassId, String content) {
        this.ownerClassId = ownerClassId;
        this.content = content;
    }
}
