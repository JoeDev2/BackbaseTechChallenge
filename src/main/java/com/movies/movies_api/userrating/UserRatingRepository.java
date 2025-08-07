package com.movies.movies_api.userrating;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    @Query("SELECT ur FROM UserRating ur ORDER BY ur.rating DESC")
    List<UserRating> findTop10OrderByRatingDesc(Pageable pageable);

    Optional<UserRating> findByMovieId(Long id);

}