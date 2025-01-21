package com.uokclubmanagement.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContentSchedule {

    private LocalDate publishedDate;
    private LocalTime publishedTime;
    private String publisherName;
    private String responseClub;
    private String description;
//    private List<String> membersLike =new ArrayList<>();
//    private List<String> membersDislike =new ArrayList<>();


    public ContentSchedule() {}

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public LocalTime getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(LocalTime publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getResponseClub() {
        return responseClub;
    }

    public void setResponseClub(String responseClub) {
        this.responseClub = responseClub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<String> getMembersLike() {
//        return membersLike;
//    }
//
//    public void setMembersLike(List<String> membersLike) {
//        this.membersLike = membersLike;
//    }
//
//    public List<String> getMembersDislike() {
//        return membersDislike;
//    }
//
//    public void setMembersDislike(List<String> membersDislike) {
//        this.membersDislike = membersDislike;
//    }
}
