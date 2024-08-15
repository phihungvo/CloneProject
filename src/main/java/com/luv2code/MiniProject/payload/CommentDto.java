package com.luv2code.MiniProject.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    long id;

    @NotEmpty(message = "Name should not be null or empty")
    String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    String body;
}
