package ch.martinelli.edu.jooq.sakila.demo;

import ch.martinelli.edu.jooq.sakila.JooqTestcontainersTest;
import ch.martinelli.edu.jooq.sakila.db.tables.FilmInStock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static ch.martinelli.edu.jooq.sakila.db.Routines.inventoryInStock;
import static ch.martinelli.edu.jooq.sakila.db.Tables.FILM_IN_STOCK;
import static ch.martinelli.edu.jooq.sakila.db.tables.Actor.ACTOR;
import static ch.martinelli.edu.jooq.sakila.db.tables.Film.FILM;
import static ch.martinelli.edu.jooq.sakila.db.tables.Inventory.INVENTORY;
import static ch.martinelli.edu.jooq.sakila.db.tables.Store.STORE;
import static org.jooq.impl.DSL.lateral;

class Demo12StoredProceduresTest extends JooqTestcontainersTest {

    @Test
    void procedures() {
        title("Standalone procedure calls require a configuration argument");
        println("Inventory 1 in stock: " + inventoryInStock(dsl.configuration(), 1L));

        title("But stored functions can also be embedded in queries");
        dsl.select(INVENTORY.INVENTORY_ID, inventoryInStock(INVENTORY.INVENTORY_ID))
                .from(INVENTORY)
                .limit(5)
                .fetch();

        title("Table valued functions are particularly powerful with jOOQ");
        dsl.select(
                        FILM.TITLE,
                        STORE.STORE_ID,
                        STORE.address().city().CITY_,
                        FILM_IN_STOCK.P_FILM_COUNT)
                .from(FILM, STORE, lateral(FILM_IN_STOCK(FILM.FILM_ID, STORE.STORE_ID)).as(FilmInStock.FILM_IN_STOCK))
                .orderBy(FILM.FILM_ID, STORE.STORE_ID)
                .limit(10)
                .fetch();
    }

    @AfterEach
    void teardown() {
        cleanup(ACTOR, ACTOR.ACTOR_ID);
    }
}
