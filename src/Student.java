public class Student {
    private String name;
    private int rollNumber;
    private int attendance;

    public Student(String name, int rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.attendance = 0;
    }

    public Student(String name, int rollNumber, int attendance) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public int getAttendance() {
        return attendance;
    }

    public void markAttendance() {
        attendance++;
    }
}
