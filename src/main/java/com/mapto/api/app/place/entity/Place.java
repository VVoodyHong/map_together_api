package com.mapto.api.app.place.entity;

import com.mapto.api.app.place.dto.PlaceDTO;
import com.mapto.api.app.placecategory.entity.PlaceCategory;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.DateAudit;
import lombok.AccessLevel;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "place")
public class Place extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    private User user;
    @ManyToOne
    private PlaceCategory category;
    private String name;
    private String address;
    private String description;
    private Double favorite;
    private BigDecimal lat;
    private BigDecimal lng;
    private String representImg;

    @Builder
    public Place(
            Long idx,
            User user,
            PlaceCategory category,
            String name,
            String address,
            String description,
            Double favorite,
            BigDecimal lat,
            BigDecimal lng,
            String representImg
    ) {
        this.idx = idx;
        this.user = user;
        this.category = category;
        this.name = name;
        this.address = address;
        this.description = description;
        this.favorite = favorite;
        this.lat = lat;
        this.lng = lng;
        this.representImg = representImg;
    }

    public void setRepresentImg(String representImg) { this.representImg = representImg; }

    public PlaceDTO.Basic toPlaceBasicDTO() {
        return new ModelMapper().map(this, PlaceDTO.Basic.class);
    }

    public PlaceDTO.Simple toPlaceSimpleDTO() {
        return new ModelMapper().map(this, PlaceDTO.Simple.class);
    }
}
