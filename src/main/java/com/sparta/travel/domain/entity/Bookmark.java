package com.sparta.travel.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bookmark")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String roadName;

    @Column(nullable = false)
    private String x;

    @Column(nullable = false)
    private String y;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String img_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
