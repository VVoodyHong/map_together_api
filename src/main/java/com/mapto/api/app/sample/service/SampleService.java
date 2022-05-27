package com.mapto.api.app.sample.service;

import com.mapto.api.app.sample.dto.SampleDTO;
import com.mapto.api.app.sample.entity.Sample;
import com.mapto.api.app.sample.repository.SampleRepository;
import com.mapto.api.common.model.RequestPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final SampleRepository sampleRepository;

    @Transactional(readOnly = true)
    public Object getSample(Long idx) {
        return sampleRepository.findSampleByIdx(idx);
    }

    @Transactional(readOnly = true)
    public Object getSamples(RequestPage pageRequest, String keyword) {
        Page<Sample> result = sampleRepository.findSamples(keyword, pageRequest.of());
        List<SampleDTO.Detail> list = result
                .stream()
                .map(Sample::toSampleDetailDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(list, result.getPageable(), result.getTotalElements());
    }
}
