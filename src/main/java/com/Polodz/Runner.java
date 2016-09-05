package com.Polodz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Polodz.View.MainWindow;
import com.Polodz.controller.Controller;
import com.Polodz.controller.MainController;

/* *
 * GUI Runner
 * @author Thomas Menet
 **/
@Profile("!test")
@Component
public class Runner implements CommandLineRunner {

    /**
     * Jframe Set Up
     */
    @Autowired
    private MainController controllerToView;

    @Override
    public void run(String... args) throws Exception {
        /* *
         * use AWT EventQueue 
         * */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
            	controllerToView.frame().setVisible(true);
            }
        });
    }

}
