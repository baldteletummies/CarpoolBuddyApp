package com.example.carpoolbuddyappmain.Models;

import android.os.Parcel;

public class MiniBus extends Vehicle {
    private int numOfRooms;

    public MiniBus(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public MiniBus(String location, String model, int capacity, int price, boolean open, String type, String id, int nrOfRooms, String owner, String image) {
        super(location, model, capacity, price, open, type, id, owner, image);
        this.numOfRooms = nrOfRooms;
    }

    public MiniBus(Parcel in, int nrOfRooms) {
        super(in);
        this.numOfRooms = nrOfRooms;
    }

    public static final Creator<MiniBus> CREATOR = new Creator<MiniBus>() {
        @Override
        public MiniBus createFromParcel(Parcel in) {
            return new MiniBus(in);
        }

        @Override
        public MiniBus[] newArray(int size) {
            return new MiniBus[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(numOfRooms);
    }

    private MiniBus(Parcel in) {
        super(in);
        numOfRooms = in.readInt();
    }

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    @Override
    public String toString() {
        return "Mini Bus{" +
                "numOfRooms=" + numOfRooms +
                '}';
    }
}
