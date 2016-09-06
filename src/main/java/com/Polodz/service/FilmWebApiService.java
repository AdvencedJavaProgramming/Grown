package com.Polodz.service;

import info.talacha.filmweb.connection.FilmwebException;

import java.util.List;

/**
 * Created by Łukasz on 2016-09-06.
 */
public interface FilmWebApiService {
    public String getInfo(String tytul)throws  FilmwebException;
    public String getRatingofFilm(String tytuł) throws FilmwebException;
    public String getInterestedOfFilm(String tytuł) throws FilmwebException;
}
