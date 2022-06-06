package com.mapto.api.app.place.entity;

import com.mapto.api.app.file.entity.File;
import com.mapto.api.common.model.DateAudit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "place_file")
public class PlaceFile extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    private Place place;
    @ManyToOne
    private File file;

    @Builder
    PlaceFile(
            Long idx,
            Place place,
            File file
    ) {
        this.idx = idx;
        this.place = place;
        this.file = file;
    }
}
