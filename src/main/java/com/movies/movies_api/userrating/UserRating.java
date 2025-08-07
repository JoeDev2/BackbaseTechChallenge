package com.movies.movies_api.userrating;

import com.movies.movies_api.movie.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @NotNull
    @DecimalMin("1.0")
    @DecimalMax("10.0")
    @Digits(integer = 2, fraction = 1)
    private Double rating;

    public UserRating(Movie movie, Double rating) {
        this.movie = movie;
        this.rating = rating;
    }

}