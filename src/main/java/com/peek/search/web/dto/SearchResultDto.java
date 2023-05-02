package com.peek.search.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResultDto {

    private Integer id;
    private String title;
    private String url;
    private String showcaseContent;
}
