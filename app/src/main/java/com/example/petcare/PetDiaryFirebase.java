package com.example.petcare;

public class PetDiaryFirebase {
    String PetName;
    String Time;
    String Date;
    String FoodIntake;
    String WaterIntake;
    String Outdoor;
    String Health;
    String DiaryId;

    PetDiaryFirebase(){

    }

    public PetDiaryFirebase(String petName, String time, String date, String foodIntake, String waterIntake, String outdoor, String health, String diaryId) {
        PetName = petName;
        Time = time;
        Date = date;
        FoodIntake = foodIntake;
        WaterIntake = waterIntake;
        Outdoor = outdoor;
        Health = health;
        DiaryId = diaryId;
    }

    public String getPetName() {
        return PetName;
    }

    public void setPetName(String petName) {
        PetName = petName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFoodIntake() {
        return FoodIntake;
    }

    public void setFoodIntake(String foodIntake) {
        FoodIntake = foodIntake;
    }

    public String getWaterIntake() {
        return WaterIntake;
    }

    public void setWaterIntake(String waterIntake) {
        WaterIntake = waterIntake;
    }

    public String getOutdoor() {
        return Outdoor;
    }

    public void setOutdoor(String outdoor) {
        Outdoor = outdoor;
    }

    public String getHealth() {
        return Health;
    }

    public void setHealth(String health) {
        Health = health;
    }

    public String getDiaryId() {
        return DiaryId;
    }

    public void setDiaryId(String diaryId) {
        DiaryId = diaryId;
    }
}
