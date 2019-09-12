package com.terheyden.vavr01;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;

public class UserValidation
{
    public static Validation<Seq<String>, User> validatePerson(User user)
    {
        return Validation.combine(validateName(user.getName()), validateAge(user.getAge()))
            .ap((name, age) -> new User(name, age));
    }

    private static Validation<String, String> validateName(String name)
    {
        if (name == null || name.isEmpty())
        {
            return Validation.invalid("Name cannot be blank.");
        }
        else
        {
            return Validation.valid(name);
        }
    }

    private static Validation<String, Integer> validateAge(int age)
    {
        return age > 17 ? Validation.valid(age) : Validation.invalid("You must be 18+.");
    }
}
