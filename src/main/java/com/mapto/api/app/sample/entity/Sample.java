package com.mapto.api.app.sample.entity;

import com.mapto.api.common.model.DateAudit;
import com.mapto.api.app.sample.dto.SampleDTO;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "sample")
public class Sample extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String sampleText;
    private Integer sampleInt;

    public SampleDTO.Detail toSampleDetailDTO() {
        return new ModelMapper().map(this, SampleDTO.Detail.class);
    }
}
