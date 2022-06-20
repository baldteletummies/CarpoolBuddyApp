package com.example.carpoolbuddyappmain.Models;


import android.os.Parcel;

public class Car extends Vehicle {
    private int range;

    public Car(int range) {
        this.range = range;
    }

    public Car(String location, String model, int capacity, int price, boolean open, String type, String id, int range, String owner, String image) {
        super(location, model, capacity, price, open, type, id, owner, image);
        this.range = range;
    }

    public Car(Parcel in, int maxSpeed) {
        super(in);
        this.range = range;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(range);
    }

    private Car(Parcel in) {
        super(in);
        range = in.readInt();
    }

    @Override
    public String toString() {
        return "Car{" +
                "range=" + range +
                '}';
    }
}
