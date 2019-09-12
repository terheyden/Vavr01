package com.terheyden.vavr01;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static java.lang.System.out;

import javax.annotation.ParametersAreNonnullByDefault;

import org.junit.jupiter.api.Test;

@ParametersAreNonnullByDefault
public class MatchCase
{
    @Test
    public void test()
    {
        User cora = new User("Cora", 8, Role.Meower);
        User mika = new User("Mika", 12, Role.LapWarmer);
        User tashi = new User("Tashi", 11, Role.Scrapper);

        // Decisions, decision.

        // Java switch case currently only works with primary types, enums, and strings.
        // It also has that weird break syntax.
        // You can't use switch to return / extract a value directly.
        // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html

        switch (cora.getRole())
        {
            case Scratcher:
                out.println("No scratching the furniture!");
                break;

            case Meower:
                out.println("Shh no meowing!");
                break;

            default:
                out.println("Hello kitty.");
        }

        // Vavr offers a powerful match / case feature that mimics the one in scala.
        // It looks strange. And it is hard to use. But it is powerful.
        // https://www.vavr.io/vavr-docs/#_pattern_matching

        Match(cora.getRole()).of(
            Case($(Role.Scratcher), o -> run(() -> out.println("No scratching!"))),
            Case($(Role.Meower), o -> run(() -> out.println("Shhh kitty!"))),
            Case($(), o -> run(() -> out.println("Hello kitty!")))
        );

        // Why reinvent the wheel when there's a perfectly good,
        // compact way to handle conditional branches in java?

        if (cora.getRole() == Role.Scratcher)   { out.println("No scratching the furniture!"); }
        else if (cora.getRole() == Role.Meower) { out.println("Shh no meowing!"); }
        else                                    { out.println("Hello kitty."); }

        // We could also drop the braces.
        // For years we've been conditioned to always include them,
        // but since Java 8, we have new compact forms like Streams and React.
        // Maybe it's time to reevaluate:

        if (cora.getRole() == Role.Scratcher)   out.println("No scratching the furniture!");
        else if (cora.getRole() == Role.Meower) out.println("Shh no meowing!");
        else                                    out.println("Hello kitty.");

        // I actually think both of the compact styles above are more readable
        // than our current usual code style:

        if (cora.getRole() == Role.Scratcher)
        {
            out.println("No scratching the furniture!");
        }
        else if (cora.getRole() == Role.Meower)
        {
            out.println("Shh no meowing!");
        }
        else
        {
            out.println("Hello kitty.");
        }

        if (cora.getRole() == Role.Scratcher) { out.println("No scratching the furniture!"); }
        else if (cora.getRole() == Role.Meower) { out.println("Shh no meowing!"); }
        else { out.println("Hello kitty."); }


    }

}
