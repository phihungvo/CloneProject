package com.luv2code.MiniProject.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    List<PostDto> content;
    int pageNo;
    int pageSize;
    long totalElements;
    int totalPages;
    boolean last;
}
