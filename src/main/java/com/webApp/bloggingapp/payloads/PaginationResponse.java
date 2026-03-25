package com.webApp.bloggingapp.payloads;

import lombok.Data;

import java.util.List;
@Data
public class PaginationResponse {
    private List<PostDto> posts;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
