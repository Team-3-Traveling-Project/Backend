package com.sparta.travel.domain.dto;

import com.sparta.travel.domain.entity.Place;
import lombok.Getter;

@Getter
public class PlaceResponseDto {
    private Long id;
    private String place_name;
    private String address_name;
    private String road_address_name;
    private String x;
    private String y;
    private String img_url;


    public PlaceResponseDto(Place place){
        this.id = place.getId();
        this.place_name = place.getPlace_name();
        this.address_name = place.getAddress_name();
        this.road_address_name = place.getRoad_address_name();
        this.x = place.getX();
        this.y = place.getY();
        this.img_url = place.getImg_url();
    }
}
