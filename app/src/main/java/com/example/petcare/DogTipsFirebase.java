package com.example.petcare;

public class DogTipsFirebase {
    String Title;
    String Description;
    String Image;
    String tipsId;

    DogTipsFirebase(){

    }

    public DogTipsFirebase(String title, String description, String image, String tipsId) {
        Title = title;
        Description = description;
        Image = image;
        this.tipsId = tipsId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTipsId() {
        return tipsId;
    }

    public void setTipsId(String tipsId) {
        this.tipsId = tipsId;
    }
}
