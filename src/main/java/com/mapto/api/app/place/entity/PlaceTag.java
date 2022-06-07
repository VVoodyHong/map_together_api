package com.mapto.api.app.place.entity;

import com.mapto.api.app.tag.entity.Tag;
import com.mapto.api.common.model.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "place_tag")
public class PlaceTag extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    private Place place;
    @ManyToOne
    private Tag tag;

    @Builder
    PlaceTag(
            Long idx,
            Place place,
            Tag tag
    ) {
        this.idx = idx;
        this.place = place;
        this.tag = tag;
    }
}
