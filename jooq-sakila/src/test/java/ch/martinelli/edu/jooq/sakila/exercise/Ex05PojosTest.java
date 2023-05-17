package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.tables.Category.CATEGORY;
import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;
import static ch.martinelli.edu.jooq.sakila.db.tables.FilmCategory.FILM_CATEGORY;
import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.select;

public class Ex05PojosTest extends JooqTestcontainersTest {

    @Test
    void film_and_length() {
        var result = dsl
                .select(FILM.TITLE, FILM.LENGTH)
                .from(FILM)
                .orderBy(FILM.LENGTH)
                .fetchInto(FilmAndLength.class);

        println(result);
    }

    @Test
    void multiset() {
        var result = dsl
                .select(CATEGORY.NAME,
                        DSL.multiset(
                                select(FILM.TITLE)
                                        .from(FILM)
                                        .join(FILM_CATEGORY).on(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID)
                                        )
                        ).convertFrom(r -> r.map(mapping(CategoryWithFilms.Film::new)))
                )
                .from(CATEGORY)
                .fetch(mapping(CategoryWithFilms::new));

        println(result);
    }
}
