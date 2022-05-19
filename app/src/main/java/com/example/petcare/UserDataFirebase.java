package com.example.petcare;

public class UserDataFirebase {
    String PetPName;
    String PetAge;
    String PetGender;
    String userName;
    String Email;
    String userId;
    String Image;

    UserDataFirebase(){

    }

    public UserDataFirebase(String petpName, String petAge, String petGender, String userName, String email, String userId, String image) {
        PetPName = petpName;
        PetAge = petAge;
        PetGender = petGender;
        this.userName = userName;
        Email = email;
        this.userId = userId;
        Image = image;
    }

    public String getPetPName() {
        return PetPName;
    }

    public void setPetPName(String petpName) {
        PetPName = petpName;
    }

    public String getPetAge() {
        return PetAge;
    }

    public void setPetAge(String petAge) {
        PetAge = petAge;
    }

    public String getPetGender() {
        return PetGender;
    }

    public void setPetGender(String petGender) {
        PetGender = petGender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
