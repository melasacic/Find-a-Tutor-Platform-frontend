package com.example.findtutor.tutor.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Tutors implements Parcelable {
    public String id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String email;
    public String city;
    public String phoneNumber;


    // Constructor to convert JSON object into a Java class instance
    public Tutors(JSONObject object){
        try {
            this.firstName = object.getString("firstName");
            this.lastName = object.getString("lastName");
            this.username = object.getString("username");
            this.password= object.getString("password");
            this.email = object.getString("email");
            this.city = object.getString("city");
            this.phoneNumber = object.getString("phoneNumber");
            this.id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Tutors(String firstName,
                  String lastName,
                  String username,
                  String password,
                  String email,
                  String city,
                  String phoneNumber,
                  String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password= password;
        this.email = email;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public Tutors() {

    }

    protected Tutors(Parcel in) {
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
        city = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Tutors> CREATOR = new Creator<Tutors>() {
        @Override
        public Tutors createFromParcel(Parcel in) {
            return new Tutors(in);
        }

        @Override
        public Tutors[] newArray(int size) {
            return new Tutors[size];
        }
    };

    public static ArrayList<Tutors> getTutors(){
        ArrayList<Tutors> tutors = new ArrayList<Tutors>();
        return tutors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RegisterTutorModelActivity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(city);
        dest.writeString(phoneNumber);
    }
}
