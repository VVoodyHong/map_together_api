package com.mapto.api.app.follow.entity;

import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "follow")
public class Follow extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    @JoinColumn(name = "from_user_idx")
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "to_user_idx")
    private User toUser;

    @Builder
    public Follow(
            Long idx,
            User fromUser,
            User toUser
    ) {
        this.idx = idx;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
