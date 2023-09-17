package com.sparta.travel.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "place")
@Getter
@NoArgsConstructor

public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "placeName",nullable = false)
    private String placeName;

    @Column(name = "addressName", nullable = false)
    private String addressName;

    @Column(name = "roadAddressName", nullable = false)
    private String roadAddressName;

    @Column(name = "x", nullable = false)
    private String x;

    @Column(name = "y", nullable = false)
    private String y;

    @Column(name = "img_url", nullable = false)
    private String img_url;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;


}
