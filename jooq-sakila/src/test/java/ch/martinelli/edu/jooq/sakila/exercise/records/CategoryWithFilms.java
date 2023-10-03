package ch.martinelli.edu.jooq.sakila.exercise.records;

import java.util.List;

public record CategoryWithFilms(String name, List<Film> films) {

    public record Film(String name) {
    }
}
