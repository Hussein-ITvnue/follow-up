package com.medido.followup.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

public class Mappers {

    private static MapperFactory factory =
            new DefaultMapperFactory.Builder().build();

    static {

    }

    /**
     * Maps an object to the required class
     *
     * @param dest   The class of the dest
     * @param object The object to be mapped
     * @return A new object of type dest
     */
    public static <T> T map(Class dest, Object object) {
        if (object == null) {
            return null;
        }
        MapperFacade mapper = factory.getMapperFacade();
        return (T) mapper.map(object, dest);
    }

    /**
     * Maps list of objects to the required class
     *
     * @param dest    The class of the dest
     * @param objects The list of objects to be mapped
     * @return A new list of objects of type dest
     */
    public static <T> List<T> mapAsList(Class dest, List<Object> objects) {
        if (objects == null) {
            return null;
        }
        MapperFacade mapper = factory.getMapperFacade();
        return (List<T>) mapper.mapAsList(objects, dest);
    }
}
