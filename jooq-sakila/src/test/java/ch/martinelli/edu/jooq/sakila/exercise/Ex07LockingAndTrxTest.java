package ch.martinelli.edu.jooq.sakila.exercise;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static ch.martinelli.edu.jooq.sakila.db.tables.Rental.RENTAL;

public class Ex07LockingAndTrxTest extends JooqTestcontainersTest {

    @Test
    void optimistic_locking() {
        dsl
                .selectFrom(RENTAL)
                .where(RENTAL.RENTAL_ID.eq(854L))
                .fetchOptional()
                .ifPresent(rental -> {
                    rental.setRentalDate(LocalDateTime.now());
                    rental.store();
                });
    }

}
