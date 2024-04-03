package com.beside.interviewserver.domain.bookmark;

import com.beside.interviewserver.api.bookmark.dto.request.BookmarkRequest;
import com.beside.interviewserver.domain.common.BaseEntity;
import com.beside.interviewserver.domain.recruit.Recruit;
import com.beside.interviewserver.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "`bookmark`")
@Entity
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;


    public static Bookmark createBookmark(User user , Recruit recruit ){
//    public static Bookmark createBookmark(BookmarkRequest bookmarkRequest , Recruit recruitId ){
        return  Bookmark.builder()
                .user(user)
                .recruit(recruit)
                .build();
    }
}
