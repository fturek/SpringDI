package uj.jwzp.w4.launchers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import uj.jwzp.w4.logic.CSVMovieFinder;
import uj.jwzp.w4.logic.MovieLister;
import uj.jwzp.w4.model.Movie;

@Slf4j
public class SpringMain {

    public static void main(String[] args) {

        ApplicationContext ctx = configurate(args);
        MovieLister lister = (MovieLister) ctx.getBean("movieLister");

        lister.moviesDirectedBy("Hoffman").stream()
                .map(Movie::toString)
                .forEach(log::info);
    }

    public static AnnotationConfigApplicationContext configurate (String ... args) {
        CommandLinePropertySource commandLinePropertySource = new SimpleCommandLinePropertySource(args);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(commandLinePropertySource);
        ctx.scan("uj.jwzp.w4.logic");
        ctx.refresh();

        return ctx;
    }
}
