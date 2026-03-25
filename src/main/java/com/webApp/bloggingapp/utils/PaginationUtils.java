package com.webApp.bloggingapp.utils;

import com.webApp.bloggingapp.payloads.PaginationResponse;
import com.webApp.bloggingapp.payloads.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginationUtils {
    public <T> PaginationResponse buildResponse(Page<T> page, List<PostDto> posts){
        PaginationResponse response = new PaginationResponse();
        response.setPosts(posts);
        response.setPageNo(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }
}
