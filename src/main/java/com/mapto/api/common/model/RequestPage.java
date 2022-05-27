package com.mapto.api.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor
public final class RequestPage {
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 100;

    @Getter
    private int page = 1;

    @Getter
    private int size = 20;

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        if(size > MAX_SIZE) {
            this.size = MAX_SIZE;
        } else this.size = Math.max(size, MIN_SIZE);
    }

    public PageRequest of(){
        return PageRequest.of(page -1, size);
    }
}
