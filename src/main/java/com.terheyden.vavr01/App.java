package com.terheyden.vavr01;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App
{
    public static void main(String[] args)
    {
        Arrays.asList("red", "yellow", "blue").stream()
            .map(color -> new DataBag()
                .set("color", color)
                .set("length", color.length()))
            .filter(data -> data.getInt("length") > 3);

    }


}
