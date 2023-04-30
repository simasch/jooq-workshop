package ch.martinelli.edu.jooq.sakila;

import org.jooq.DSLContext;
import org.jooq.Meta;
import org.jooq.Select;
import org.jooq.conf.ParseWithMetaLookups;
import org.jooq.impl.ParserException;
import org.junit.jupiter.api.Test;

class Ex07ParserTest extends JooqTestcontainersTest {

    @Test
    void parser() {
        title("jOOQ can also parse SQL strings to jOOQ expression trees");
        Select<?> select = dsl.parser().parseSelect("select instr('abcd', 'bc'), instr('a a a a a', 'a', 3) from dual");
        println(select);
        select.fetch();
    }

    @Test
    void parseMetaLookups() {
        title("The parser can validate your schema, too. There are different types of meta data sources");
        Meta meta = dsl.meta(
                """
                        create table author (
                          author_id int not null primary key, 
                          first_name text not null, 
                          last_name text not null
                        );
                        create table book (
                          book_id int not null primary key,
                          author_id int not null references author, 
                          title text not null
                        );
                        """
        );

        DSLContext c = dsl
                .configuration()
                .derive(() -> meta)
                .deriveSettings(s -> s.withParseWithMetaLookups(ParseWithMetaLookups.THROW_ON_FAILURE))
                .dsl();

        title("Check the projection produced by the parsed query");
        println(c.parser().parseSelect("select * from book").getSelect());

        try {
            c.parser().parseSelect("select id from book");
        } catch (ParserException e) {
            title("Parse errors now include meta data lookup errors");
            e.printStackTrace();
        }
    }

}
