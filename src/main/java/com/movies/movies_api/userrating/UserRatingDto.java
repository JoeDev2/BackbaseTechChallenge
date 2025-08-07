package com.movies.movies_api.userrating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingDto {

    @NotNull
    @DecimalMin("1.0")
    @DecimalMax("10.0")
    @Digits(integer = 2, fraction = 1)
    private Double rating;
}