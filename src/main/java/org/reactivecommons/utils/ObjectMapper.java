package org.reactivecommons.utils;

public interface ObjectMapper {
    <T> T map(Object source, Class<T> target);
}
