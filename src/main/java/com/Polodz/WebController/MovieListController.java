package com.Polodz.WebController;

import com.Polodz.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lpolatowski on 2016-09-07.
 */

@Controller
public class MovieListController {

    @Autowired
    private WebService service;

    @GetMapping("/movieList")
    public ModelAndView getMovieList() {
        return new ModelAndView("movieList").addObject("string", service.getMovieListString());
    }

}
