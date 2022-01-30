package me.sxmurai.observant.util;

import org.reflections.Reflections;

import java.util.Set;

public class ReflectionUtil {
    public static Set<Class<?>> getClassesInPackage(String packageName, Class<?> klass) {
        return (Set<Class<?>>) new Reflections("me.sxmurai.observant.listeners").getSubTypesOf(klass);
    }
}
