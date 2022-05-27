package com.example.petcare;

public class PetVaccineFirebase {
    String PetName;
    String Time;
    String Date;
    String VaccineIntake;
    String Cared;
    String Note;
    String VaccineId;

    PetVaccineFirebase(){

    }

    public PetVaccineFirebase(String petName, String time, String date, String vaccineIntake, String cared, String note, String vaccineId) {
        PetName = petName;
        Time = time;
        Date = date;
        VaccineIntake = vaccineIntake;
        Cared = cared;
        Note = note;
        VaccineId = vaccineId;
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

    public String getVaccineIntake() {
        return VaccineIntake;
    }

    public void setVaccineIntake(String vaccineIntake) {
        VaccineIntake = vaccineIntake;
    }

    public String getCared() {
        return Cared;
    }

    public void setCared(String cared) {
        Cared = cared;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getVaccineId() {
        return VaccineId;
    }

    public void setVaccineId(String vaccineId) {
        VaccineId = vaccineId;
    }
}
