package com.example.petcare;

public class CatTrainingFirebase {
    String Title;
    String Description;
    String Image;
    String Video;
    String Link;
    String trainingId;

    public CatTrainingFirebase(String title, String description, String image, String video, String link, String trainingId) {
        Title = title;
        Description = description;
        Image = image;
        Video = video;
        Link = link;
        this.trainingId = trainingId;
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

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }
}
