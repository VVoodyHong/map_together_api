package com.mapto.api.app.tag.entity;

import com.mapto.api.common.model.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tag")
public class Tag extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;

    @Builder
    public Tag(
            Long idx,
            String name
    ) {
        this.idx = idx;
        this.name = name;
    }
}
