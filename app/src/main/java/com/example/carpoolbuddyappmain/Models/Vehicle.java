package com.example.carpoolbuddyappmain.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Vehicle implements Serializable, Parcelable {
    private String location;
    private String model;
    private int capacity;
    private int remainingCapacity;
    private String vehicleID;
    private ArrayList<String> reservedUIDs;
    private int price;
    private boolean open;
    private String type;
    private String owner;

    private String image;

    public Vehicle() {

    }

    public Vehicle(String location, String model, int capacity, int price, boolean open, String type, String vehicleID, String owner, String image) {
        this.location = location;
        this.model = model;
        this.capacity = capacity;
        this.remainingCapacity = capacity;
        this.vehicleID = vehicleID;
        this.reservedUIDs = new ArrayList<>();
        ;
        this.price = price;
        this.open = open;
        this.type = type;
        this.owner = owner;
        this.image = image;

    }

    protected Vehicle(Parcel in) {
        location = in.readString();
        model = in.readString();
        capacity = in.readInt();
        remainingCapacity = in.readInt();
        vehicleID = in.readString();
        reservedUIDs = in.createStringArrayList();
        price = in.readInt();
        open = in.readByte() != 0;
        type = in.readString();
        owner = in.readString();
        image = in.readString();
    }

    /*public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };*/

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public ArrayList<String> getReservedUIDs() {
        return reservedUIDs;
    }

    public void addReservedUIDs(String inUid) {
        this.reservedUIDs.add(inUid);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(location);
        dest.writeString(model);
        dest.writeInt(capacity);
        dest.writeInt(remainingCapacity);
        dest.writeString(vehicleID);
        dest.writeStringList(reservedUIDs);
        dest.writeInt(price);
        dest.writeByte((byte) (open ? 1 : 0));
        dest.writeString(type);
        dest.writeString(owner);
        dest.writeString(image);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setReservedUIDs(ArrayList<String> reservedUIDs) {
        this.reservedUIDs = reservedUIDs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "location='" + location + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", remainingCapacity=" + remainingCapacity +
                ", vehicleID='" + vehicleID + '\'' +
                ", reservedUIDs=" + reservedUIDs +
                ", price=" + price +
                ", open=" + open +
                ", type='" + type + '\'' +
                ", owner='" + owner + '\'' +
                ", imageLinks='" + image + '\'' +
                '}';
    }
}