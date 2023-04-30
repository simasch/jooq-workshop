package ch.martinelli.edu.jooq.sakila;

import ch.martinelli.edu.jooq.sakila.db.tables.records.ActorRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;

class Ex04DMLTest extends JooqTestcontainersTest {

    @Test
    void updatableRecords() {
        title("By default, every table has a corresponding TableRecord or UpdatableRecord, which work like active records");
        ActorRecord a1 = dsl.newRecord(ACTOR);
        a1.setActorId(201L);
        a1.setFirstName("Jane");
        a1.setLastName("Doe");
        a1.insert();

        title("You can fetch them in a type safe way by using the special selectFrom() method, or appropriate mappers");
        ActorRecord a2 =
                dsl.selectFrom(ACTOR)
                        .where(ACTOR.ACTOR_ID.eq(201L))
                        .fetchSingle();

        title("UpdatableRecord.store() will decide itself, whether it should insert() or update()");
        a2.setLastName("Smith");
        a2.store();
    }

    @Test
    void dml() {
        title("All sorts of classic bulk DML statements are available");

        dsl.insertInto(ACTOR)
                .columns(ACTOR.ACTOR_ID, ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .values(201L, "Jon", "Doe")
                .values(202L, "Jane", "Smith")
                .execute();

        dsl.update(ACTOR)
                .set(ACTOR.LAST_NAME, "X")
                .where(ACTOR.ACTOR_ID.gt(200L))
                .execute();
    }

    @AfterEach
    void teardown() {
        cleanup(ACTOR, ACTOR.ACTOR_ID);
    }
}
