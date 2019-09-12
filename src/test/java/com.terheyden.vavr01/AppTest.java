package com.terheyden.vavr01;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test() {

        String name = "Cora";
        List<String> list = Collections.singletonList(name);

        assertThat(list, is(not(nullValue()))); // Only need is() by itself.
        assertThat(list, is(notNullValue()));   // These are all the same.
        assertThat(list, notNullValue());

        assertThat(name, is("Cora"));
        assertThat(name, equalToIgnoringCase("CORA"));
        assertThat(name, startsWith("C"));
        assertThat(name, containsString("ora"));
        assertThat(name, endsWith("a"));
        assertThat(name.length(), greaterThan(3));

        assertThat(list, not(empty()));

        // Ands, ors:

        assertThat(name.length(), allOf(greaterThan(1), lessThan(100)));
        assertThat(name, anyOf(notNullValue(), containsString("ora")));
    }
}
