package com.hot6.backend.user.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class EmailVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String uuid;

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User user;

}