package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

public class Ex01FirstQueryTest extends JooqTestcontainersTest {

    @Test
    void query() {
        title("A simple query");

        var result = dsl.selectFrom(DSL.dual()).fetch();

        println(result);
    }
}
