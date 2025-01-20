package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Event;
import com.uokclubmanagement.entity.News;
import com.uokclubmanagement.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    @Autowired
    private LikeService contentScheduleService;

    @PostMapping("/{newsId}/addLikeNews/{clubId}/{memberId}")
    public News addLikeToNews(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.addLikeToNews(newsId, clubId, memberId);
    }

    @PostMapping("/{eventId}/addLikeEvent/{clubId}/{memberId}")
    public Event addLikeToEvent(@PathVariable("eventId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.addLikeToEvent(newsId, clubId, memberId);
    }

    @PostMapping("/{newsId}/removeLikeNews/{clubId}/{memberId}")
    public News removeLikeFromNews(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.removeLikeFromNews(newsId, clubId, memberId);
    }

    @PostMapping("/{eventId}/removeLikeEvent/{clubId}/{memberId}")
    public Event removeLikeFromEvent(@PathVariable("eventId") String eventId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.removeLikeFromEvent(eventId, clubId, memberId);
    }

    @PostMapping("/{newsId}/likeCountNews/{clubId}/{memberId}")
    public int likeCountNews(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.getNewsLikeCount(newsId, clubId, memberId);
    }

    @PostMapping("/{newsId}/dislikeCountNews/{clubId}/{memberId}")
    public int dislikeCountNews(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.getNewsDislikeCount(newsId, clubId, memberId);
    }

    @PostMapping("/{eventId}/likeCountEvent/{clubId}/{memberId}")
    public int likeCountEvent(@PathVariable("eventId") String eventId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.getEventLikeCount(eventId, clubId, memberId);
    }

    @PostMapping("/{eventId}/dislikeCountEvent/{clubId}/{memberId}")
    public int dislikeCountEvent(@PathVariable("eventId") String eventId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return contentScheduleService.getEventDislikeCount(eventId, clubId, memberId);
    }
}