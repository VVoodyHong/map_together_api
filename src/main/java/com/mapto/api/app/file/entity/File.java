package com.mapto.api.app.file.entity;

import com.mapto.api.app.file.dto.FileDTO;
import com.mapto.api.app.place.dto.PlaceDTO;
import com.mapto.api.common.model.DateAudit;
import com.mapto.api.common.model.type.FileType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file")
public class File extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    @Enumerated(EnumType.STRING)
    private FileType type;
    private String url;

    @Builder
    public File(
            Long idx,
            String name,
            FileType type,
            String url
    ) {
        this.idx = idx;
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public FileDTO.Basic toFileBasicDTO() {
        return new ModelMapper().map(this, FileDTO.Basic.class);
    }

    public FileDTO.Simple toFileSimpleDTO() {
        return new ModelMapper().map(this, FileDTO.Simple.class);
    }
}
