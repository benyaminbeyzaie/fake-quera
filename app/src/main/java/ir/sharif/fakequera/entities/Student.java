package ir.sharif.fakequera.entities;

import androidx.annotation.NonNull;

public class Student extends User{

    public String studentNumber;
    public Student(String userName, String password, String mode) {
        super(userName, password, mode);
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
