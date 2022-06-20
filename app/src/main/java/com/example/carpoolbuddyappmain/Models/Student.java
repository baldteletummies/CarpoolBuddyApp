package com.example.carpoolbuddyappmain.Models;

public class Student extends User {
    private String graduatingYear;

    public Student(){
        super();
    }

    public Student(String uid, String name, String email, String userType, double priceMultiplier, String graduatingYear) {
        super(uid, name, email, userType, priceMultiplier);
        this.graduatingYear = graduatingYear;
    }

    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }


    @Override
    public String toString() {
        return "Student{" +
                "graduatingYear='" + graduatingYear + '\'' +
                '}';
    }
}
