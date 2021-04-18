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
}
