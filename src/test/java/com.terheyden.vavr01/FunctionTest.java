package com.terheyden.vavr01;

import io.vavr.Function2;

import org.junit.jupiter.api.Test;

public class FunctionTest
{
    @Test
    public void test()
    {
        Function2<String, Integer, User> createUser = (name, age) -> new User(name, age);

        User cora = createUser.apply("Cora").apply(8);
    }

}
