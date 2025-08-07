package com.movies.movies_api.movie.model;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer releaseYear;
    private String category;
    private String nominee;
    private Boolean won;
    private String runtime;
    private String genre;
    private String director;
    private BigDecimal boxOffice;
    private Double imdbRating;
    private String response;

    public Movie(Integer releaseYear, String category, String nominee, Boolean won) {
        this.releaseYear = releaseYear;
        this.category = category;
        this.nominee = nominee;
        this.won = won;
    }

    public Movie(Long id, String title, Integer releaseYear, BigDecimal boxOffice) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.boxOffice = boxOffice;
    }
}