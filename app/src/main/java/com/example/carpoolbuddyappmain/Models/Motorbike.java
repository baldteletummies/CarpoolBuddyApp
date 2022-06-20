package com.example.carpoolbuddyappmain.Models;

import android.os.Parcel;

public class Motorbike extends Vehicle {

    private int maxSpeed;

    public Motorbike(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Motorbike(String location, String model, int capacity, int price, boolean open, String type, String id, int maxSpeed, String owner, String image) {
        super(location, model, capacity, price, open, type, id, owner, image);
        this.maxSpeed = maxSpeed;
    }

    public Motorbike(Parcel in, int maxSpeed) {
        super(in);
        this.maxSpeed = maxSpeed;
    }

    public static final Creator<Motorbike> CREATOR = new Creator<Motorbike>() {
        @Override
        public Motorbike createFromParcel(Parcel in) {
            return new Motorbike(in);
        }

        @Override
        public Motorbike[] newArray(int size) {
            return new Motorbike[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(maxSpeed);
    }

    private Motorbike(Parcel in) {
        super(in);
        maxSpeed = in.readInt();
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Motorbike{" +
                "maxSpeed=" + maxSpeed +
                '}';
    }
}
