package ch.martinelli.edu.jooq.sakila;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.jooq.generated.tables.Actor.ACTOR;

class Ex11QueryObjectModelTest extends JooqTestcontainersTest {

    // As of jOOQ 3.17, these features are *EXPERIMENTAL*!

    @Test
    void qom() {
        title("The query object model (QOM) API allows for accessing most query parts");

        var select = dsl
                .select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
                .from(ACTOR)
                .where(ACTOR.ACTOR_ID.lt(4L));

        println("SELECT: " + select.$select());
        println("FROM  : " + select.$from());
        println("WHERE : " + select.$where());

        title("You can also alter a property of a query, to create a new query (QOM operations are immutable):");
        println(
                select.$select(Stream.concat(Stream.of(ACTOR.ACTOR_ID), select.$select().stream()).toList())
                        .$orderBy(List.of(ACTOR.ACTOR_ID.asc()))
        );

        title("The old query is untouched:");
        println(select);
    }

    @Test
    void traversal() {
        title("The query object model (QOM) can be traversed easily");

        // This is a commercial only feature. Check out the commercial demo for details

//
//        var select = dsl
//            .select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
//            .from(ACTOR)
//            .where(ACTOR.ACTOR_ID.lt(4L));
//
//        println("All column expressions: " + select.$traverse(Traversers.findingAll(p -> p instanceof Field)));
//        println("All bind values: " + select.$traverse(Traversers.findingAll(p -> p instanceof Param)));
//
//        title("Any JDK Collector can be turned into a Traverser, too, e.g. collecting to a list");
//        select.$traverse(Traversers.collecting(toList())).forEach(System.out::println);
//
//        title("Or grouping query parts by type");
//        select.$traverse(Traversers.collecting(groupingBy(p -> p.getClass(), toList()))).forEach(
//            (type, parts) -> {
//                println("");
//                println("Type: " + type);
//                println("Parts:");
//
//                parts.forEach(part -> println("  " + part));
//            }
//        );
//
//
    }

    @Test
    void replacement() {
        title("The query object model (QOM) can be transformed easily");

        // This is a commercial only feature. Check out the commercial demo for details

//
//        var select1 = dsl
//            .select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
//            .from(ACTOR)
//            .where(ACTOR.ACTOR_ID.lt(4L));
//
//        title("Replacing bind values");
//        println(select1.$replace(p -> p instanceof Param ? val(5) : p));
//
//        title("Inverting the < predicate");
//        println(select1.$replace(p -> p instanceof QOM.Lt<?> lt ? lt.$converse() : p));
//
//        title("Appending a predicate");
//        println(select1.$replace(appendSecurityCheck()));
//
//        title("Appending a predicate even to subqueries");
//        var select2 = dsl
//            .select(ACTOR.FIRST_NAME, ACTOR.LAST_NAME)
//            .from(ACTOR)
//            .where(ACTOR.ACTOR_ID.lt(
//                select(max(ACTOR.ACTOR_ID)).from(ACTOR))
//            );
//        println(select2.$replace(appendSecurityCheck()));
//
//
    }


//
//    private Function<QueryPart, QueryPart> appendSecurityCheck() {
//        return p -> {
//            Condition c = condition("security_check()");
//
//            // Beware of performance and infinite recursions, though!
//            if (p instanceof Select<?> s)
//
//                // Append the predicate if there is no predicate
//                if (s.$where() == null)
//                    return s.$where(c);
//
//                // If there's already a predicate, check if the predicate contains the predicate already (don't recurse into subqueries)
//                else if (!s.$where().$traverse(Traversers.recursing(q -> !(q instanceof Select), Traversers.containing(c))))
//                    return s.$where(and(s.$where(), c));
//
//            return p;
//        };
//    }
//
//
}
