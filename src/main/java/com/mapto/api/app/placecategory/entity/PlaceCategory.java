package com.mapto.api.app.placecategory.entity;

import com.mapto.api.app.placecategory.dto.PlaceCategoryDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.DateAudit;
import com.mapto.api.common.model.type.PlaceCategoryType;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "place_category")
public class PlaceCategory extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    private User user;
    private String name;
    @Enumerated(EnumType.STRING)
    private PlaceCategoryType type;

    @Builder
    public PlaceCategory(
            User user,
            String name,
            PlaceCategoryType type
    ) {
        this.user = user;
        this.name = name;
        this.type = type;
    }

    public void setUser(User user) { this.user = user; }
    public void setName(String name) { this.name = name; }
    public void setType(PlaceCategoryType type) { this.type = type;}

    public PlaceCategoryDTO.Basic toPlaceCategoryBasicDTO() {
        return new ModelMapper().map(this, PlaceCategoryDTO.Basic.class);
    }

    public PlaceCategoryDTO.Simple toPlaceCategorySimpleDTO() {
        return new ModelMapper().map(this, PlaceCategoryDTO.Simple.class);
    }
}
