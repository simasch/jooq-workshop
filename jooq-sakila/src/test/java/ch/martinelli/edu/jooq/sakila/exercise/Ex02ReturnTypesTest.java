package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;
import static ch.martinelli.edu.jooq.sakila.db.tables.Customer.CUSTOMER;
import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;

public class Ex02ReturnTypesTest extends JooqTestcontainersTest {

    @Test
    void first_last_name_of_first_actor() {
        var result = dsl
                .select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .from(ACTOR)
                .fetch();

        println(result);
    }

    @Test
    void films_ordered() {
        var result = dsl
                .selectFrom(FILM)
                .orderBy(FILM.RELEASE_YEAR.desc())
                .offset(1)
                .limit(500)
                .fetch();

        println(result);
    }

    @Test
    void option_customer() {
        var result = dsl
                .selectFrom(CUSTOMER)
                .where(CUSTOMER.FIRST_NAME.eq("Simon"))
                .fetchOptional();

        println(result);
    }
}
