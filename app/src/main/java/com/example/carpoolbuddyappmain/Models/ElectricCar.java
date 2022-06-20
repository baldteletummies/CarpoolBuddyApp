package com.example.carpoolbuddyappmain.Models;

import android.os.Parcel;

public class ElectricCar extends Vehicle {

    private int batterySize;

    public ElectricCar(int batterySize) {
        this.batterySize = batterySize;
    }

    public ElectricCar(String location, String model, int capacity, int price, boolean open, String type, String id, int batterySize, String owner, String image) {
        super(location, model, capacity, price, open, type, id, owner, image);
        this.batterySize = batterySize;
    }

    public ElectricCar(Parcel in, int batterySize) {
        super(in);
        this.batterySize = batterySize;
    }

    public int getBatterySize() {
        return batterySize;
    }

    public void setBatterySize(int batterySize) {
        this.batterySize = batterySize;
    }

    public static final Creator<ElectricCar> CREATOR = new Creator<ElectricCar>() {
        @Override
        public ElectricCar createFromParcel(Parcel in) {
            return new ElectricCar(in);
        }

        @Override
        public ElectricCar[] newArray(int size) {
            return new ElectricCar[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(batterySize);
    }

    private ElectricCar(Parcel in) {
        super(in);
        batterySize = in.readInt();
    }

/*    public void readFromParcel(Parcel in) {
        super(in);
        batterySize = in.readInt();
    }*/

    @Override
    public String toString() {
        return "ElectricCar{" +
                "batterySize=" + batterySize +
                '}';
    }

}
