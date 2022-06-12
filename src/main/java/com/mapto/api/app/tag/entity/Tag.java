package com.mapto.api.app.tag.entity;

import com.mapto.api.app.tag.dto.TagDTO;
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

    public TagDTO.Simple toTagSimpleDTO() {
        return new ModelMapper().map(this, TagDTO.Simple.class);
    }
}
