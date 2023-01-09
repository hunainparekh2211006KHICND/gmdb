package com.galvanize.gmdb.gmdb;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.gmdb.gmdb.controller.MovieController;
import com.galvanize.gmdb.gmdb.controller.ReviewController;
import com.galvanize.gmdb.gmdb.controller.ReviewerController;
import com.galvanize.gmdb.gmdb.model.Movie;
import com.galvanize.gmdb.gmdb.model.Review;
import com.galvanize.gmdb.gmdb.model.ReviewFeilds;
import com.galvanize.gmdb.gmdb.model.Reviewer;
import com.galvanize.gmdb.gmdb.repository.IMovieRepository;
import com.galvanize.gmdb.gmdb.repository.IReviewRepository;
import com.galvanize.gmdb.gmdb.repository.IReviewerRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class GmdbApplicationTests {

    private MockMvc mvc;
    private MockMvc mvc2;
    private MockMvc mvc3;

	@Mock
	private IMovieRepository movieRepo;
    @Mock
    private IReviewerRepository reviewerRepo;
    @Mock
    private IReviewRepository reviewRepo;

	@InjectMocks
	private MovieController movieController;
    @InjectMocks
    private ReviewerController reviewerController;
    @InjectMocks
    private ReviewController reviewController;

	private JacksonTester<Movie> jsonMovie;
	private JacksonTester<List<Movie>> jsonMovies;
    private JacksonTester<Reviewer> jsonReviewer;
    private JacksonTester<ReviewFeilds> jsonReview;

	@BeforeEach
	public void setUp(){
		JacksonTester.initFields(this, new ObjectMapper());
		mvc = MockMvcBuilders.standaloneSetup(movieController).build();
        mvc2 = MockMvcBuilders.standaloneSetup(reviewerController).build();
        mvc3 = MockMvcBuilders.standaloneSetup(reviewController).build();
	}

	// Stories for this project are shown below in order of value, with the highest value listed first.
    // This microservice will contain the CRUD operations required to interact with the GMDB movie database.
    // Other functionality (e.g. user authentication) is hosted in other microservices.
    //
    // 1. As a user
    //    I can GET a list of movies from GMDB that includes Movie ID | Movie Title | Year Released | Genre | Runtime
    //    so that I can see the list of available movies.

    @Test
	public void canGetAllMovies() throws Exception {
		Movie movie = new Movie(1L,"Harry Porter",1992,"Action",120);
		Movie movie2 = new Movie(2L,"My Movie",1993,"Romantic",180);
		List<Movie> movies = new LinkedList<Movie>();
		movies.add(movie);
		movies.add(movie2);
		when(movieRepo.findAll()).thenReturn(movies);
		mvc.perform(get("/movies")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMovies.write(movies).getJson()));
	}

    //
    // 2. As a user
    //    I can provide a movie ID and get back the record shown in story 1, plus a list of reviews that contains Review ID | Movie ID | Reviewer ID | Review Text | DateTime last modified
    //    so that I can read the reviews for a movie.

    @Test
	public void canGetMovieById() throws Exception {
		Movie movie = new Movie(1L,"Harry Porter",1992,"Action",120);
        Reviewer reviewer = new Reviewer(1L,"Hunain Parekh",Date.valueOf(LocalDate.now()),5L);
        Review review = new Review(1L,"Awesome Movie",Date.valueOf(LocalDate.now()),movie,reviewer);
        Review review2 = new Review(2L,"Good Movie",Date.valueOf(LocalDate.now()),movie,reviewer);
        List<Review> reviews = new LinkedList<Review>();
        reviews.add(review);
        reviews.add(review2);
        movie.setReviews(reviews);
		when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));
		mvc.perform(get("/movies/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonMovie.write(movie).getJson()));
	}
    //
    // 3. As a user
    //    I can provide a Reviewer ID and get back a record that contains Reivewer ID | Username | Date Joined | Number of Reviews
    //    so that I can see details about a particular reviewer.
    //
    @Test
	public void canGetReviewerById() throws Exception {
        Reviewer reviewer = new Reviewer(1L,"Hunain Parekh",Date.valueOf(LocalDate.now()),5L);
		when(reviewerRepo.findById(1L)).thenReturn(Optional.of(reviewer));
		mvc2.perform(get("/reviewers/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonReviewer.write(reviewer).getJson()));
	}

    // 4. As a user
    //    I can register as a reviewer by providing my Username. (Reviewer ID should be autogenerated)
    //    So that I can start reviewing movies.
    //
    @Test
	public void canCreateANewReviewer() throws Exception {
        Reviewer reviewer = new Reviewer("Hunain Parekh");
		mvc2.perform(post("/reviewers")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonReviewer.write(reviewer).getJson()))
			.andExpect(status().isOk());
	}

    // 5. As a reviewer
    //    I can post a review by providing my reviewer ID, a movie ID and my review text. (Review ID should be autogenerated)
    //    So that I can share my opinions with others.
    //
    @Test
	public void canCreateANewReview() throws Exception {
        Reviewer reviewer = new Reviewer("Hunain Parekh");
        reviewerRepo.save(reviewer);
        Movie movie = new Movie(1L,"Harry Porter",1992,"Action",120);
        movieRepo.save(movie);
        Review review = new Review("Awesome Movie");
        ReviewFeilds myReview = new ReviewFeilds(1l,1l,review);
		mvc3.perform(post("/reviews")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonReview.write(myReview).getJson()))
			.andExpect(status().isOk());
	}
    // 6. As a reviewer
    //    I can delete a review by providing my reviewer ID and a review ID
    //    So that I can remove reviews I no longer wish to share.
    //
    @Test
	public void canDeleteReview() throws Exception {
		mvc3.perform(delete("/reviews/1/1"))
			.andExpect(status().isOk());
	}
    // 7. As a reviewer
    //    I can update a review by providing my reviewer ID, a movie ID and my review text.
    //    So that I can modify the opinion I'm sharing with others.
    //
    @Test
	public void canUpdateReview() throws Exception {
        Reviewer reviewer = new Reviewer("Hunain Parekh");
        reviewerRepo.save(reviewer);
        Movie movie = new Movie(1L,"Harry Porter",1992,"Action",120);
        movieRepo.save(movie);
        Review review = new Review("Awesome Movie");
        reviewRepo.save(review);
        Review updatReview = new Review(1l,"Good Movie");
        ReviewFeilds myReview = new ReviewFeilds(1l,1l,updatReview);
		mvc3.perform(put("/reviews")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonReview.write(myReview).getJson()))
			.andExpect(status().isOk());
	}
    // 8. As an Admin
    //    I can add a new movie to the database by providing the data listed in story 1 (Movie ID should be autogenerated)
    //    so that I can share new movies with the users.
    @Test
	public void canCreateANewMovie() throws Exception {
        Movie movie = new Movie("Harry Porter",1992,"Action",120);
		mvc.perform(post("/movies")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonMovie.write(movie).getJson()))
			.andExpect(status().isOk());
	}
    // 9. As an Admin
    //    I can add update the entry for a movie by providing the data listed in Story 1.
    //    so that I can correct errors in previously uploaded movie entries.
    //
    @Test
	public void canUpdateAMovie() throws Exception {
        Movie movie = new Movie("Harry Porter",1992,"Action",120);
        movieRepo.save(movie);
        Movie updated_movie = new Movie(1L,"Harry Porter",1992,"Action",120);
		mvc.perform(put("/movies")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonMovie.write(updated_movie).getJson()))
			.andExpect(status().isOk());
	}
    //10. As an admin
    //    I can delete a movie by providing a movie ID
    //    so that I can remove movies I no longer wish to share.
    //
    @Test
	public void canDeleteMovie() throws Exception {
		mvc.perform(delete("/movies/1"))
			.andExpect(status().isOk());
	}
    //11. As an admin
    //    I can impersonate a reviewer and do any of the things they can do
    //    so that I can help confused reviewers.

	@Test
	public void contextLoads() {
	}

}
