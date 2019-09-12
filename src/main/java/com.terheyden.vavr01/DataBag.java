package com.terheyden.vavr01;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class DataBag
{
    private final Map<String, Object> map = new HashMap<>();

    /**
     * Set a key value pair.
     */
    @Nonnull
    public DataBag set(String key, Object val)
    {
        if (val == null)
        {
            throw new IllegalArgumentException("Value may not be null.");
        }

        map.put(key, val);
        return this;
    }

    /**
     * Set a key value pair. Use a backup default value in case the main value is null.
     */
    @Nonnull
    public DataBag setWithDefault(String key, @Nullable Object val, Object orIfNull)
    {
        map.put(key, val != null ? val : orIfNull);
        return this;
    }

    /**
     * Get a value by key. Throws if not found.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> T get(String key)
    {
        if (!map.containsKey(key))
        {
            throw new IllegalArgumentException("DataBag does not contain: " + key);
        }

        return (T) map.get(key);
    }

    public String getStr(String key)
    {
        return get(key);
    }

    public int getInt(String key)
    {
        return get(key);
    }

    /**
     * Get the value assoicated with the specified key. Null if not found.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T getOrNull(String key)
    {
        return (T) map.get(key);
    }

    /**
     * Get the associated value, or use the default value provided if not present.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> T getOrElse(String key, Object defaultVal)
    {
        return map.containsKey(key) ? (T) map.get(key) : (T) defaultVal;
    }

    /**
     * Try to get the value by key, returing {@link Optional}.
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> Optional<T> tryGet(String key)
    {
        return map.containsKey(key) ? Optional.of((T) map.get(key)) : Optional.empty();
    }
}
