package ch.martinelli.edu.jooq.sakila;

import ch.martinelli.edu.jooq.sakila.db.tables.records.ActorRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;

class Ex05BatchTest extends JooqTestcontainersTest {

    @Test
    void batchUpdatableRecords() {
        title("A set of updatable records can be conveniently batch stored, inserted, updated");
        ActorRecord a1 = dsl.newRecord(ACTOR);
        ActorRecord a2 = dsl.newRecord(ACTOR);

        a1.setActorId(201L);
        a1.setFirstName("John");
        a1.setLastName("Doe");

        a2.setActorId(202L);
        a2.setFirstName("Jane");
        a2.setLastName("Smith");

        dsl.batchStore(a1, a2).execute();
    }

    @Test
    void batchedConnection() {
        title("Just collect all JDBC statements, and delay them until appropriate to run them in a batch");

        dsl.batched(c -> {
            // Don't use dsl here, but the nested configuration

            title("This doesn't actually store the record yet, it just caches the query for later storage");
            c.dsl().insertInto(ACTOR)
                    .columns(ACTOR.ACTOR_ID, ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                    .values(201L, "Jon", "Doe")
                    .execute();

            title("Again, nothing is stored just yet");
            c.dsl().insertInto(ACTOR)
                    .columns(ACTOR.ACTOR_ID, ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                    .values(202L, "Jane", "Smith")
                    .execute();

            title("When a different query string is encountered, then we 'flush' the batch for the query to produce correct results");
            c.dsl().select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                    .from(ACTOR)
                    .where(ACTOR.ACTOR_ID.gt(200L))
                    .fetch();
        });
    }

    @Test
    void batchManually() {
        dsl.batch(
                        dsl.insertInto(ACTOR)
                                .columns(ACTOR.ACTOR_ID, ACTOR.FIRST_NAME, ACTOR.LAST_NAME)

                                // Pass a few dummy values here
                                .values((Long) null, null, null)
                )
                .bind(201L, "Jon", "Doe")
                .bind(202L, "Jane", "Smith")
                .execute();
    }

    @AfterEach
    void teardown() throws SQLException {
        cleanup(ACTOR, ACTOR.ACTOR_ID);
    }
}
