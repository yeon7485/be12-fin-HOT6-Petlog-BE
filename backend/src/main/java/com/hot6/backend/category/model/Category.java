package com.hot6.backend.category.model;

import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private String color;
    @Enumerated(EnumType.STRING)
    private CategoryType type;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();
}
