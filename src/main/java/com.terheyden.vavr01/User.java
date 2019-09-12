package com.terheyden.vavr01;

import java.util.UUID;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Classes used in lambdas like to be Serializable.
 */
@ParametersAreNonnullByDefault
public class User
{
    private final UUID id;
    private String name;
    private int age;
    private Role role;

    public User()
    {
        this.id = UUID.randomUUID();
    }

    public User(String name, int age)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, Role role)
    {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public User setName(String name)
    {
        this.name = name;
        return this;
    }

    public int getAge()
    {
        return age;
    }

    public User setAge(int age)
    {
        this.age = age;
        return this;
    }

    public Role getRole()
    {
        return role;
    }

    public User setRole(Role role)
    {
        this.role = role;
        return this;
    }

    @Override
    public String toString()
    {
        return String.format("%s age %d (%s)", getName(), getAge(), getId());
    }
}
