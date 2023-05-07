package ch.martinelli.edu.jooq.sakila.demo;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import ch.martinelli.edu.jooq.sakila.db.tables.daos.ActorDao;
import ch.martinelli.edu.jooq.sakila.db.tables.pojos.Actor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;

class Demo09DaoTest extends JooqTestcontainersTest {

    @Test
    void pojos() {
        title("jOOQ's code generator produces a POJO for every table");
        List<Actor> actors =
                dsl.selectFrom(ACTOR)
                        .where(ACTOR.ACTOR_ID.lt(4L))
                        .fetchInto(Actor.class);

        actors.forEach(System.out::println);
    }

    @Test
    void daos() {
        title("Daos further simplify CRUD when working with jOOQ");
        ActorDao dao = new ActorDao(dsl.configuration());
        dao.insert(
                new Actor(201L, "John", "Doe", null),
                new Actor(202L, "Jane", "Smith", null)
        );

        dao.fetchByActorId(201L, 202L).forEach(System.out::println);
    }

    @AfterEach
    void teardown() throws SQLException {
        cleanup(ACTOR, ACTOR.ACTOR_ID);
    }
}
