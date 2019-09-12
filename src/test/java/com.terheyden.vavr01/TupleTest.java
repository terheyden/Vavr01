package com.terheyden.vavr01;

import java.util.Map;
import java.util.Optional;

import io.vavr.Tuple;
import io.vavr.Tuple1;
import io.vavr.Tuple2;

import org.junit.jupiter.api.Test;

public class TupleTest
{
    @Test
    public void test()
    {
        // Tuples are hella useful and hella powerful.
        Tuple1<String> tup1 = new Tuple1<>("Cora");

        // Append! Great for fluent / streams / react:
        Tuple2<String, Integer> tup2 = tup1.append(8);

        // Another way to make tuples:
        Tuple2<String, Integer> tup3 = Tuple.of("Mika", 12);

        // Apply all your tuple vals to something useful.
        User tupUser = tup2.apply((name, age) -> new User(name, age));

        // To and from map entries:
        Map.Entry<String, Integer> mapEntry = tup2.toEntry();

        Tuple2<String, Integer> tup4 = Tuple.fromEntry(mapEntry);

        // Look at all the neat things we can do.
        String userName = "Tashi";

        Optional.of(userName)
            .map(Tuple::of)
            .map(user -> user.append(getUserAge(user._1())))
            .map(user -> user.map((name, age) -> Tuple.of(name.toUpperCase(), age)))
            .map(user -> user.apply((name, age) -> new User(name, age)));
    }

    private int getUserAge(String name)
    {
        return 8;
    }

}
