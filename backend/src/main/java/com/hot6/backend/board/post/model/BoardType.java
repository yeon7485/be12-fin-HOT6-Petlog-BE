package com.hot6.backend.board.post.model;

import com.hot6.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String boardName;

    @OneToMany(mappedBy = "boardType")
    private List<Post> postList;
}
