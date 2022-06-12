package com.mapto.api.app.placereply.entity;

import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.placereply.dto.PlaceReplyDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.DateAudit;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "place_reply")
public class PlaceReply extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String reply;
    @ManyToOne
    private Place place;
    @ManyToOne
    private User user;

    @Builder
    public PlaceReply(
            String reply,
            Place place,
            User user
    ) {
        this.reply = reply;
        this.place = place;
        this.user = user;
    }

    public void setReply(String reply) { this.reply = reply; }
    public void setPlace(Place place) { this.place = place;}
    public void setUser(User user) { this.user = user; }

    public PlaceReplyDTO.Simple toPlaceReplySimpleDTO() {
        return new ModelMapper().map(this, PlaceReplyDTO.Simple.class);
    }
}
