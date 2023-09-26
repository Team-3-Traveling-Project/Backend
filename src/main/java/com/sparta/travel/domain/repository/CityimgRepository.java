package com.sparta.travel.domain.repository;

import com.sparta.travel.domain.entity.Cityimg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityimgRepository extends JpaRepository<Cityimg, Long> {
    Cityimg findByName(String city);
}
