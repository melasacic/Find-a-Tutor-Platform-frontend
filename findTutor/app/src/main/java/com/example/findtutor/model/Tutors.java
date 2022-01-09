package com.example.findtutor.model;

public class Tutors {

    private String firstName;
    private String lastName;
    private String username;
  //  private String password;
    private String email;
    private String city;
    private String phoneNumber;

    public Tutors(String firstName,
                  String lastName,
                  String username,
                  //    String password,
                  String email,
                  String city,
                  String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
      //  this.password = password;
        this.email = email;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    public Tutors() {
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

   // public String getPassword() {
    //    return password;
    //}

  //  public void setPassword(String password) {
   //     this.password = password;
   // }

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
               // ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
