package com.mapto.api.app.place.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Subselect("select place_idx, count(idx) as like_count from place_like group by place_idx")
@Synchronize("place_like")
public class PlaceLikeGroupByPlaceIdx {
    @Id
    private Long placeIdx;
    @Column(name = "like_count")
    private Long likeCount;
}
