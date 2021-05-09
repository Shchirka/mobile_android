package ua.kpi.comsys.iv8230;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movie {
    String title;
    String year;
    String imdbID;
    String type;
    String poster;

    public Movie(String title, String year, String imdbID, String type, String poster) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }

    String rated;
    String released;
    String runtime;
    String genre;
    String director;
    String writer;
    String actors;
    String plot;
    String language;
    String country;
    String awards;
    String imdbRating;
    String imdbVotes;
    String production;

    public Movie(String title, String year, String imdbID, String type, String poster, String rated, String released, String runtime, String genre, String director, String writer, String actors, String plot, String language, String country, String awards, String imdbRating, String imdbVotes, String production) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.production = production;
    }

    public Movie(String title, String year, String type) {
        this.title = title;
        this.year = year;
        this.type = type;
    }

    public Movie() {
    }

    public Map<String, String> CreateMovie(Movie movie) {
        Map<String, String> movie_array = new HashMap<>();
        movie_array.put("Title", movie.title);
        movie_array.put("Year", movie.year);
        movie_array.put("imdbID", movie.imdbID);
        movie_array.put("Type", movie.type);
        movie_array.put("Poster", movie.poster);
        return movie_array;
    }

    public Map<String, String> CreateMovieInformation(Movie movie) {
        Map<String, String> movie_array = new HashMap<>();
        movie_array.put("Title", movie.title);
        movie_array.put("Year", movie.year);
        movie_array.put("imdbID", movie.imdbID);
        movie_array.put("Type", movie.type);
        movie_array.put("Poster", movie.poster);
        movie_array.put("Actors", movie.actors);
        movie_array.put("Awards", movie.awards);
        movie_array.put("Country", movie.country);
        movie_array.put("Director", movie.director);
        movie_array.put("Genre", movie.genre);
        movie_array.put("Rating", movie.imdbRating);
        movie_array.put("Votes", movie.imdbVotes);
        movie_array.put("Language", movie.language);
        movie_array.put("Plot", movie.plot);
        movie_array.put("Production", movie.production);
        movie_array.put("Rated", movie.rated);
        movie_array.put("Released", movie.released);
        movie_array.put("Runtime", movie.runtime);
        movie_array.put("Writer", movie.writer);
        return movie_array;
    }

    public Movie getMovie(Map<String, String> movie) {
        Movie some_movie = new Movie(movie.get("Title"), movie.get("Year"), movie.get("imdbID"), movie.get("Type"), movie.get("Poster"));
        return some_movie;
    }

    public ArrayList<Map<String, String>> splitMovies(ArrayList<String> movies) {
        ArrayList<Map<String, String>> some_movie = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            Map<String, String> movie = new HashMap<>();
            int index_left = movies.get(i).indexOf("\"Title\":") + 9;
            int index_right = movies.get(i).indexOf("\"", index_left);
            movie.put("Title", movies.get(i).substring(index_left, index_right));
            index_left = movies.get(i).indexOf("\"Year\":") + 8;
            index_right = movies.get(i).indexOf("\"", index_left);
            movie.put("Year", movies.get(i).substring(index_left, index_right));
            index_left = movies.get(i).indexOf("\"imdbID\":") + 10;
            index_right = movies.get(i).indexOf("\"", index_left);
            movie.put("imdbID", movies.get(i).substring(index_left, index_right));
            index_left = movies.get(i).indexOf("\"Type\":") + 8;
            index_right = movies.get(i).indexOf("\"", index_left);
            movie.put("Type", movies.get(i).substring(index_left, index_right));
            index_left = movies.get(i).indexOf("\"Poster\":") + 10;
            index_right = movies.get(i).indexOf("\"", index_left);
            movie.put("Poster", movies.get(i).substring(index_left, index_right));
            some_movie.add(movie);
        }
        return some_movie;
    }

    public Map<String, String> splitInformation(String movies){
        Map<String, String> movie_information = new HashMap<>();
        int index_left = movies.indexOf("\"Title\":") + 9;
        int index_right = movies.indexOf("\"", index_left);
        movie_information.put("Title", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Year\":") + 8;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Year", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Rated\":") + 9;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Rated", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Released\":") + 12;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Released", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Runtime\":") + 11;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Runtime", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Genre\":") + 9;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Genre", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Director\":") + 12;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Director", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Writer\":") + 10;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Writer", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Actors\":") + 10;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Actors", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Plot\":") + 8;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Plot", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Language\":") + 12;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Language", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Country\":") + 11;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Country", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Awards\":") + 10;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Awards", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Poster\":") + 10;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Poster", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"imdbRating\":") + 14;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Rating", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"imdbVotes\":") + 13;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Votes", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"imdbID\":") + 10;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("imdbID", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Type\":") + 8;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Type", movies.substring(index_left, index_right));
        index_left = movies.indexOf("\"Production\":") + 14;
        index_right = movies.indexOf("\"", index_left);
        movie_information.put("Production", movies.substring(index_left, index_right));
        return movie_information;
    }
}
