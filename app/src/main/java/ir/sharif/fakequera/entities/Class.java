package ir.sharif.fakequera.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "class_table")
public class Class {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "owner_teacher_id")
    public int ownerTeacherId;

    @ColumnInfo(name = "class_name")
    public String className;


    public Class() {
    }

    public Class(int ownerTeacherId, String className) {
        this.ownerTeacherId = ownerTeacherId;
        this.className = className;
    }

    public Class(String className) {
        this.className = className;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOwnerTeacherId() {
        return ownerTeacherId;
    }

    public void setOwnerTeacherId(int ownerTeacherId) {
        this.ownerTeacherId = ownerTeacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Class{" +
                "uid=" + uid +
                ", ownerTeacherId=" + ownerTeacherId +
                ", className='" + className + '\'' +
                '}';
    }
}
