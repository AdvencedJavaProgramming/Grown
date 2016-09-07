package com.Polodz.service;

import org.springframework.stereotype.Service;

/**
 * Created by lpolatowski on 2016-09-07.
 */
@Service
public class WebService {

	private static String movieListString;

	public String getMovieListString() {
		return movieListString;
	}

	public static void setMovieListString(String value) {
		movieListString = value;
	}

}
