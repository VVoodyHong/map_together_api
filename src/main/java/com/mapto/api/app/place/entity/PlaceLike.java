package com.mapto.api.app.place.entity;

import com.mapto.api.app.place.dto.PlaceLikeDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "place_like")
public class PlaceLike extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    private User user;
    @ManyToOne
    private Place place;

    @Builder
    PlaceLike(
            Long idx,
            User user,
            Place place
    ) {
        this.idx = idx;
        this.user = user;
        this.place = place;
    }

    public PlaceLikeDTO.Simple toPlaceLikeSimpleDTO() {
        return new ModelMapper().map(this, PlaceLikeDTO.Simple.class);
    }
}
