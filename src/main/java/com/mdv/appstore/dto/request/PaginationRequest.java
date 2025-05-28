package com.mdv.appstore.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationRequest {
    @Min(value = 1, message = "Page must be greater than 0")
    int page = 1;

    @Min(value = 1, message = "Size must be greater than 0")
    int size = 10;

    String sortBy = "name";

    @Pattern(regexp = "ASC|DESC", message = "Sort direction must be ASC or DESC")
    String sortDirection = "ASC";
}
