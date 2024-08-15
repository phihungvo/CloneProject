package com.luv2code.MiniProject.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    String description;

    @NotEmpty
    String content;

    Set<CommentDto> comments;

    long categoryId;
}
