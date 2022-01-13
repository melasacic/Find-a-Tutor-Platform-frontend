package com.example.findtutor.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Tutors {

    public String firstName;
    public String lastName;
    public String username;
    public String email;
    public String city;
    public String phoneNumber;

    // Constructor to convert JSON object into a Java class instance
    public Tutors(JSONObject object){
        try {
            this.firstName = object.getString("firstName");
            this.lastName = object.getString("lastName");
            this.username = object.getString("username");
            this.email = object.getString("email");
            this.city = object.getString("city");
            this.phoneNumber = object.getString("phoneNumber");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Tutors(String firstName,
                  String lastName,
                  String username,
                  String email,
                  String city,
                  String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    public static ArrayList<Tutors> getTutors(){
        ArrayList<Tutors> tutors = new ArrayList<Tutors>();
        return tutors;
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
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


}
