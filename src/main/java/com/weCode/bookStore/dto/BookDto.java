package com.weCode.bookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/*
 * @Data will create getter - setter
 * @Builder enables the Builder design pattern (which will create a constructor accepting all fields without declaring one)
 * @AllArgsConstructor generates a constructor with 1 parameter for each field in your class.
 * @NoArgsConstructor will generate a constructor with no parameters.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private UUID id;
    private String title;
    private String description;
    private int releaseYear;
}
