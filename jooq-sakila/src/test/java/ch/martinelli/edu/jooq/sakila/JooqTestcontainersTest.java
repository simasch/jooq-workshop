package ch.martinelli.edu.jooq.sakila;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
public abstract class JooqTestcontainersTest {

    @Autowired
    protected DSLContext dsl;

    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    public static void title(String title) {
        println("");
        println(title);
        println("-".repeat((String.valueOf(title)).length()));
    }

    public static <T> void println(T t) {
        System.out.println(t);
    }

    protected void cleanup(Table<?> actor, Field<Long> actorId) {
        dsl.delete(actor)
                .where(actorId.gt(200L))
                .execute();
    }
}
