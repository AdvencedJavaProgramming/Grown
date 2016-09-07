package com.Polodz.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lpolatowski on 2016-09-07.
 */
@Service
public class WebService {

    private String movieListString;

    public List<String> getMovieListString() {
        List<String> movieList = Arrays.asList(movieListString.split("\\n"));
        return movieList;
    }

    public void setMovieListString(String value) {
        movieListString = value;
    }

}
