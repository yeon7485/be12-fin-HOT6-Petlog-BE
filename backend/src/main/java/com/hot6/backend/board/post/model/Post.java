package com.hot6.backend.board.post.model;

import com.hot6.backend.common.BaseEntity;
import com.hot6.backend.user.model.User;
import com.hot6.backend.board.post.images.PostImage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String content;
    private String image;
    private String category;

    @Builder.Default
    private Boolean isDeleted = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_type_idx")
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImageList;
}
