package com.example.carpoolbuddyappmain.Models;


public class Teacher extends User {
    private String subject;

    public Teacher(){
        super();
    }

    public Teacher(String uid, String name, String email, String userType, double priceMultiplier, String subject) {
        super(uid, name, email, userType, priceMultiplier);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "subject='" + subject + '\'' +
                '}';
    }
}