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
@Subselect("select place_idx, count(idx) as reply_count from place_reply group by place_idx")
@Synchronize("place_reply")
public class PlaceReplyGroupByPlaceIdx {
    @Id
    private Long placeIdx;
    @Column(name = "reply_count")
    private Long replyCount;
}
