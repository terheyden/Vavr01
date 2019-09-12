package com.terheyden.vavr01;

import static java.lang.String.format;
import static java.lang.System.out;

import java.util.stream.Stream;

import javax.annotation.ParametersAreNonnullByDefault;

import io.vavr.Tuple;
import io.vavr.collection.HashMultimap;
import io.vavr.collection.LinkedHashMultimap;
import io.vavr.collection.Multimap;
import io.vavr.collection.Traversable;
import io.vavr.collection.TreeMultimap;
import io.vavr.control.Option;

import org.junit.jupiter.api.Test;

@ParametersAreNonnullByDefault
public class Multimaps
{
    @Test
    public void basics()
    {
        String red = "RED";
        String yellow = "YELLOW";
        String green = "GREEN";

        // Hash, LinkedHash, Tree
        // Tree is Sorted.

        ////////////////////////////////////////
        // CREATION

        // Vavr MultiHashmaps are totally immutable.
        // They kind of suck.

        // First you need to select the type of Multimap.
        // There's HashMultimap, LinkedMultimap, and TreeMultimap.

        // HashMultimap is general purpose, smallest, and fastest.
        // There is no special ordering on keys.
        // withSeq() uses a Vavr Seq collection for the values.
        // It is analagous to a java List.
        Multimap<String, String> hash = HashMultimap.withSeq().empty();

        // LinkedHashMultimaps have linked keys, that remember their insert order,
        // and can be traversed.
        // withSet() means the collection of values will be stored in a Set.
        Multimap<String, String> linked = LinkedHashMultimap.withSet().empty();

        // A TreeMultimap has sorted keys.
        // withSortedSet() means the values will also be sorted.
        TreeMultimap<String, String> tree = TreeMultimap.withSortedSet().empty();

        // When you only have a few values, and you know them up front,
        // you can use Multimap.of() to create your Multimap.
        HashMultimap<String, String> colors = HashMultimap.withSet()
            .of("primary", red, "primary", yellow);

        // If you can create a stream of tuples (Vavr Tuple2),
        // you can collect the results into a Multimap.
        Multimap<Integer, String> lengthMap = Stream.of("one", "two", "three")
            .map(str -> Tuple.of(str.length(), str))
            .collect(HashMultimap.withSeq().collector());

        out.println(format("Numbers with a length of 3: %s",
            String.join(", ", lengthMap.get(3).get())));

        // You can add elements via put(),
        // but a new Multimap is returned.
        colors = colors.put("primary", green);

        Option<Traversable<String>> primaryColors = colors.get("primary");
        primaryColors.get().forEach(out::println);
    }
}
