package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.*;
import com.uokclubmanagement.repository.ClubAdminRepository;
import com.uokclubmanagement.repository.ClubRepository;
import com.uokclubmanagement.repository.MemberRepository;
import com.uokclubmanagement.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.uokclubmanagement.service.EventServiceImpl.contentScheduleUpdating;


@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ClubAdminRepository clubAdminRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public News createNews(News news, String clubId, String clubAdminId) {

        // Find club and clubAdmin are exist
        Optional<ClubAdmin> clubAdminOptional = clubAdminRepository.findById(clubAdminId);
        Optional<Club> clubOptional = clubRepository.findById(clubId);

        if(clubAdminOptional.isEmpty()){
            throw new RuntimeException("Invalid Club Admin");
        }

        else if(clubOptional.isEmpty()){
            throw new RuntimeException("Invalid Club ID");
        }

        // Get ClubAdmin
        ClubAdmin clubAdmin = clubAdminOptional.get();

        if (!clubAdmin.getClubId().equals(clubId)){
            throw new RuntimeException("Club ID does not match with Club Admin ID");
        }

        else {

            // Set the newsId
            if (news.getNewsId() == null || news.getNewsId().isEmpty()) {
                long seqValue = sequenceGeneratorService.generateSequence("News Sequence");
                String newsId = String.format("News-%04d", seqValue);
                news.setNewsId(newsId);
            }

            // Set the organizing club and publisher name
            news.setResponseClub(clubId);
            news.setPublisherName(clubAdmin.getFullName());

            // Set publish date and time without seconds
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            LocalTime timeWithoutSeconds = currentTime.withNano(0);

            news.setPublishedDate(currentDate);
            news.setPublishedTime(timeWithoutSeconds);
            return newsRepository.save(news);
        }

    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News getNewsById(String newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);

        if (optionalNews.isEmpty()){
            throw new RuntimeException("News not found with: "+newsId);
        }

        return optionalNews.get();
    }

    @Override
    public News updateNewsById(String clubAdminId, String newsId, News news) {

        // Find event and clubAdmin are existing
        Optional<News> findNews = newsRepository.findById(newsId);
        Optional<ClubAdmin> findClubAdmin = clubAdminRepository.findById(clubAdminId);

        if(findNews.isEmpty()) {
            throw new RuntimeException("Invalid Event ID");
        }

        else if(findClubAdmin.isEmpty()) {
            throw new RuntimeException("Invalid Club Admin ID");
        }

        else if (!findClubAdmin.get().getClubId().equals(findNews.get().getResponseClub())){
            throw new RuntimeException("Club Admin ID does not match with response club ID");
        }

        else {
            News exisitingNews = findNews.get();

            // Set news title and description
            exisitingNews.setNewsTitle(news.getNewsTitle());
            contentScheduleUpdating(exisitingNews, news);

            // Set publisher name
            exisitingNews.setPublisherName(findClubAdmin.get().getFullName());

            // Save updated fields on events collection
            return newsRepository.save(exisitingNews);
        }
    }

    @Override
    public void deleteNewsById(String newsId) {
        Optional<News> deleteNews = newsRepository.findById(newsId);
        if (deleteNews.isPresent()) {
            newsRepository.delete(deleteNews.get());
            System.out.println("Deleted news: " + newsId);
        }
        else{
            throw new RuntimeException("Invalid news ID: "+newsId);
        }
    }

    @Override
    public News addLikeToNews(String newsId, String clubId, String memberId) {

        News news = validateClubIdWithNewsAndMembers(newsId, clubId, memberId);

        // Check already add a like
        if(news.getMembersLike().contains(memberId)){
            throw new RuntimeException("Already added a like to the news");
        }
        // If user already dislike the post dislike will delete
        else if(news.getMembersDislike().contains(memberId)){
            news.getMembersDislike().remove(memberId);
            newsRepository.save(news);
            return news;
        }
        // Add a like
        else{
            news.getMembersLike().add(memberId);

            newsRepository.save(news);

            return news;
        }
    }

    @Override
    public News removeLikeFromNews(String newsId, String clubId, String memberId) {

        News news = validateClubIdWithNewsAndMembers(newsId, clubId, memberId);

        // Check already dislike to the news
        if(news.getMembersDislike().contains(memberId)){
            throw new RuntimeException("Already disliked to the news");
        }
        // If user already like the news and like will delete
        else if(news.getMembersLike().contains(memberId)){
            news.getMembersLike().remove(memberId);
            newsRepository.save(news);
            return news;
        }
        // Add a dislike
        else {
            news.getMembersDislike().add(memberId);
            newsRepository.save(news);
            return news;
        }
    }

    @Override
    public Integer getNewsLikeCount(String newsId, String clubId, String memberId) {

        News news = validateClubIdWithNewsAndMembers(newsId, clubId, memberId);

        int likeCount = news.getMembersLike().size();
        return likeCount;

    }

    @Override
    public Integer getNewsDislikeCount(String newsId, String clubId, String memberId) {

        News news = validateClubIdWithNewsAndMembers(newsId, clubId, memberId);

        int dislikeCount = news.getMembersDislike().size();
        return dislikeCount;
    }

    private News validateClubIdWithNewsAndMembers(String newsId, String clubId, String memberId) {

        Optional<News> optionalNews = newsRepository.findById(newsId);
        Optional<Club> clubOptional = clubRepository.findById(clubId);
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if(optionalNews.isEmpty()) {
            throw new RuntimeException("Invalid News ID");
        }
        else if(clubOptional.isEmpty()){
            throw new RuntimeException("Invalid Club ID");
        }
        else if(memberOptional.isEmpty()){
            throw new RuntimeException("Invalid Member ID");
        }
        else if(!optionalNews.get().getResponseClub().equals(clubId) || !memberOptional.get().getAssociatedClubs().contains(clubId)){
            throw new RuntimeException("Club ID error");
        }

        return optionalNews.get();
    }
}
