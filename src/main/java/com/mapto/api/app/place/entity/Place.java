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
    private BigDecimal lat;
    private BigDecimal lng;

    @Builder
    public Place(
            Long idx,
            User user,
            PlaceCategory category,
            String name,
            String address,
            String description,
            BigDecimal lat,
            BigDecimal lng
    ) {
        this.idx = idx;
        this.user = user;
        this.category = category;
        this.name = name;
        this.address = address;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }

    public PlaceDTO.Basic toPlaceBasicDTO() {
        return new ModelMapper().map(this, PlaceDTO.Basic.class);
    }
}
