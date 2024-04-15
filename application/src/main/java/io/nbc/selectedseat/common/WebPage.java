package io.nbc.selectedseat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebPage<T> {

    private int dataSize;
    private Long totalElementCount;
    private int currentPage;
    private int pageCount;
    private T data;
}
