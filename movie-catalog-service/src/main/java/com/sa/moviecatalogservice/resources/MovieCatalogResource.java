package com.sa.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sa.moviecatalogservice.model.CatalogItem;
import com.sa.moviecatalogservice.model.Movie;
import com.sa.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	WebClient.Builder webClientBuilder;
	

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
		
		return ratings.getUserRating().stream().map(rating -> {
					// For each movie ID, call movie info service and get details
					Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
					// Put them all together
					return new CatalogItem(movie.getName(), "Sample Description", rating.getRating());
				}).collect(Collectors.toList());

	}
	
	// Sort of reactive:
	/*
	Movie movie = webClientBuilder.build()
					.get()
					.uri("http://localhost:8082/movies/"+rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
	*/
}
