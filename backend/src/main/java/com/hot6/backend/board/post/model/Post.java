package com.hot6.backend.board.post.model;

import com.hot6.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String content;
    private String image;
    private String category;

    @Builder.Default
    private Boolean isDeleted = false;

    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate created_at;

    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate updated_at;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDate.now();
        updated_at = LocalDate.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_type_idx")
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx", nullable = false)
    private User user;
}
