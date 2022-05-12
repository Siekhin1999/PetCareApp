package com.example.petcare;

public class CatTrainingFirebase {
    String Title;
    String Description;
    String Image;
    String Video;
    String LinkUrl;
    String trainingId;

    CatTrainingFirebase(){

    }

    public CatTrainingFirebase(String title, String description, String image, String video, String linkUrl, String trainingId) {
        Title = title;
        Description = description;
        Image = image;
        Video = video;
        LinkUrl = linkUrl;
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

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        LinkUrl = linkUrl;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }
}
