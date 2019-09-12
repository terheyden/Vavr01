package com.terheyden.vavr01;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import io.vavr.control.Try;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

/**
 * Try can wrap a checked exception into an unchecked one.
 * It also represents either a success or fail without side effects
 * like throwing an Exception. CompletableFutures can do this also.
 *
 * https://tedvinke.wordpress.com/2019/04/29/functional-java-by-example-part-7-treat-failures-as-data-too/
 * https://www.scala-lang.org/api/current/scala/util/Try.html
 */
@ParametersAreNonnullByDefault
public class TryTest
{
    private static final Logger LOG = getLogger(TryTest.class);

    /**
     * Ways to escape Java's dumb checked exceptions.
     */
    @Test
    public void testCheckedExceptions()
    {
        // Wrap and ignore all thrown exceptions with run():
        Try.run(() -> checkedSleepMsg("Check #1", 500));

        // Wrap checked exceptions and rethrow them as unchecked by adding get():
        Try.run(() -> checkedSleepMsg("Check v2", 500)).get();

        // Wrap checked to unchecked and get a value:
        assertThrows(IllegalStateException.class, () ->
        {
            User user1 = Try.of(() -> loadUserFail("Cora")).get();
            LOG.error("This will never get called: {}", user1);
        });
    }

    @Test
    public void testNulls()
    {
        Map<String, String> map = new HashMap<>();

        // What happens when the try stream gets a null?

        Try.of(() -> map)
            .map(m -> m.get("null"))
            .andThen(str -> out.println(str.toUpperCase()))
            .onFailure(e -> out.println("Fail: " + e.getLocalizedMessage()));

    }

    /**
     * https://tedvinke.wordpress.com/2019/04/29/functional-java-by-example-part-7-treat-failures-as-data-too/
     */
    @Test
    public void testFunctional()
    {
        // Optional<> avoids null, but if something goes wrong, we don't know what.
        // Try<> gives us a result (Try.Success) or an exception (Try.Failure)
        // along with all the functional ways of dealing with them.
        // Similar to the Scala Try object.

        String name = "Cora";

        // This logs a method:
        loadUserTry(name)
            .onSuccess(u -> LOG.info("Successfully loaded: {}", u));

        // The failure exception doesn't throw, and onSuccess() doesn't run:
        loadUserTryFail(name)
            .onSuccess(u -> LOG.info("Successfully loaded again: {}", u))
            .onFailure(e -> LOG.error("Could not load: {}", e.getLocalizedMessage()));

        // There are all kinds of ways to deal with Try results:
        //   get()                   - just get the Success value or throw.
        //   getOrElse(User default) - get Success or use a default.
        //   orElse(Try other)       - if Failure, run this other Try.
        //   flatMap(Try next)       - on success, run this next Try.
        //   andThen(Supplier)       - on success, run this next thing (which is not a Try).
        //
        // Let's say there's a network timeout you want to retry,
        // or a known 'already exists' exception gets thrown... use recover().
        //
        // recover(NetworkTimeout.class, e -> doRetry())

        loadUserTry(name)
            .peek(u -> LOG.info(" ... updating user: {}", u))
            .flatMap(TryTest::saveUserFail)
            .andThen(u -> LOG.info("User updated successfully: {}", u));

        // Get a value, do something with it, with no result.
        loadUserTryFail(name)                               // Returns Try<User> but always throws.
            .andThen(u -> LOG.warn("Should not get called: {}", u)) // Skips since Error.
            .orElse(loadUserTry("CoraBackup"))                // Runs this other Try<User> method.
            .andThen(u -> LOG.info("Finally got: {}", u));          // This gets called.

        // Load the user and get their age.
        int age = Try.of(() -> loadUser(name))
            .map(u -> u.getAge())
            .get();
    }


    ////////////////////////////////////////

    private static final Random RAND = new Random();

    private static User loadUser(String name)
    {
        User user = new User(name, RAND.nextInt(100));
        LOG.info("Loaded user: {}", user);
        return user;
    }

    @NotNull
    private static User loadUserFail(String name)
    {
        LOG.warn("Something is wrong...");
        throw new IllegalStateException("User does not exist: " + name);
    }

    private static Try<User> loadUserTry(String name)
    {
        return Try.of(() -> loadUser(name));
    }

    private static Try<User> loadUserTryFail(String name)
    {
        return Try.of(() -> loadUserFail(name));
    }

    private static Try<User> saveUser(User user)
    {
        return Try.of(() -> user);
    }

    private static Try<User> saveUserFail(User user)
    {
        return Try.of(() ->
        {
            LOG.error("Ahh database is down!!");
            throw new DuplicateFormatFlagsException("User already exists, try update: " + user);
        });
    }

    private static void checkedSleepMsg(String msg, int ms) throws InterruptedException
    {
        LOG.info(msg);
        Thread.sleep(ms);
    }
}
