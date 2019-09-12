package com.terheyden.vavr01;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import io.vavr.Lazy;

import org.slf4j.Logger;

@ParametersAreNonnullByDefault
public class LazyTest
{
    private static final Logger LOG = getLogger(LazyTest.class);

    /**
     * Lazy.of() creates a normal Lazy T, which you can get with myLazy.get().
     * Lazy.val() wraps an interface with a lazy implementation!
     */
    @SuppressWarnings("unchecked")
    private static final Map<String, String> map = Lazy.val(() ->
        {
            LOG.info("Creating lazy map...");
            return new HashMap<String, String>();
        },
        Map.class);

    public static void main(String... args)
    {
        // Test our secret lazy map.
        map.put("hey", "now");
        map.put("Cora", "cat");
        LOG.info("Cora = " + map.get("Cora"));
    }
}
