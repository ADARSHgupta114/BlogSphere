package com.BlogSphere.comment.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class commentResponse {
    private String name;
    private String email;
    private String body;
}
