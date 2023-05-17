package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;

public class Ex03RecordsTest extends JooqTestcontainersTest {

    @Test
    void film_title_order_by_length() {
        dsl
                .select(FILM.TITLE, FILM.LENGTH)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchInto(record -> System.out.println(record.get(FILM.TITLE)));
    }

    @Test
    void only_ids() {
        var result = dsl
                .select(FILM.FILM_ID)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchInto(Long.class);

        println(result);
    }

    @Test
    void stream() {
        var stream = dsl
                .select(FILM.FILM_ID)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .stream();

        stream.forEach(id -> println(id));
    }

    @Test
    void lazy() {
        try (var cursor = dsl
                .select(FILM.FILM_ID)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchLazy()) {

            while (cursor.hasNext()) {
                println(cursor.fetchNext());
            }
        }
    }
}
