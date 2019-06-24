package filmprojekt;

import filmprojekt.annotation.Property;

import java.time.LocalDate;
import java.util.*;

public class Movie {
    @Property("movie_id")
    private int movieId;

    @Property("movie_title")
    private String title;

    @Property("movie_plot")
    private String plot;

    @Property("genre_name")
    private String genre;

    @Property("movie_released")
    private Optional<LocalDate> date;

    @Property("movie_imdbVotes")
    private OptionalInt imbdVotes;

    @Property("movie_imdbRating")
    private OptionalDouble imbdRating;

    private final Set<Actor> actors = new HashSet<>();
    private final Set<Director> directors = new HashSet<>();

    /** Default constructor for reflective instantiation */
    private Movie() {}

    public Movie(int movieId, String title, String plot, String genre, Optional<LocalDate> date, OptionalInt imbdVotes, OptionalDouble imbdRating) {
        this.movieId = movieId;
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.date = date;
        this.imbdVotes = imbdVotes;
        this.imbdRating = imbdRating;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }

    public String getGenre() {
        return genre;
    }

    public Optional<LocalDate> getDate() {
        return date;
    }

    public OptionalInt getImbdVotes() {
        return imbdVotes;
    }

    public OptionalDouble getImbdRating() {
        return imbdRating;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", plot='" + plot + '\'' +
                ", genre='" + genre + '\'' +
                ", date=" + date +
                ", imbdVotes=" + imbdVotes +
                ", imbdRating=" + imbdRating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieId == movie.movieId &&
                Objects.equals(title, movie.title) &&
                Objects.equals(plot, movie.plot) &&
                Objects.equals(genre, movie.genre) &&
                Objects.equals(date, movie.date) &&
                Objects.equals(imbdVotes, movie.imbdVotes) &&
                Objects.equals(imbdRating, movie.imbdRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, title, plot, genre, date, imbdVotes, imbdRating);
    }
}
