package com.webApp.bloggingapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class CategoryDto {
    private int categoryId;
    @NotEmpty
    private String categoryTitle;
    @NotEmpty
    private String categoryDescription;
}
