package ch.martinelli.edu.jooq.sakila;

import org.jooq.DSLContext;
import org.jooq.ExecuteListener;
import org.jooq.VisitListener;
import org.junit.jupiter.api.Test;

import static org.jooq.generated.tables.Actor.ACTOR;
import static org.jooq.impl.DSL.using;

class Ex10SpiTest extends JooqTestcontainersTest {

    @Test
    void executeListener() {
        title("The ExecuteListener SPI allows for intercepting the execution lifecycle");

        DSLContext c = dsl.configuration().derive(
                ExecuteListener
                        .onRenderEnd(e -> e.sql("/* some telemetry comment */ " + e.sql()))
                        .onExecuteStart(e -> println("Executing: " + e.sql()))
                        .onRecordEnd(e -> println("Fetched record: " + e.record().formatJSON()))
        ).dsl();

        c.select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .from(ACTOR)
                .where(ACTOR.ACTOR_ID.lt(4L))
                .fetch();
    }

    @Test
    void visitListener() {
        title("The VisitListener SPI allows for intercepting the SQL rendering process");

        DSLContext c = dsl.configuration().derive(
                VisitListener.onVisitStart(vc -> println("Visiting: " + using(dsl.family()).render(vc.queryPart())))
        ).dsl();

        c.select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .from(ACTOR)
                .where(ACTOR.ACTOR_ID.lt(4L))
                .fetch();
    }

    // There are many more SPIs, check out Configuration::derive methods!
}
