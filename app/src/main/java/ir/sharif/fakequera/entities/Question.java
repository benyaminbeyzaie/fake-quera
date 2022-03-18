package ir.sharif.fakequera.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "ownerClassId")
    public int ownerClassId;

    @ColumnInfo(name = "queston_name")
    public String questionName;

    @ColumnInfo(name = "content")
    public String content;

    public Question() {
    }

    @Ignore
    public Question(int ownerClassId, String questionName, String content) {
        this.ownerClassId = ownerClassId;
        this.questionName = questionName;
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOwnerClassId() {
        return ownerClassId;
    }

    public void setOwnerClassId(int ownerClassId) {
        this.ownerClassId = ownerClassId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
