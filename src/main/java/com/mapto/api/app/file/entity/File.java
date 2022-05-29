package com.mapto.api.app.file.entity;

import com.mapto.api.common.model.DateAudit;
import com.mapto.api.common.model.type.FileType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
