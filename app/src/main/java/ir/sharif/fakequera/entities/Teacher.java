package ir.sharif.fakequera.entities;

public class Teacher extends User{
    public String universityName;
    public Teacher(String userName, String password, String mode) {
        super(userName, password, mode);
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
