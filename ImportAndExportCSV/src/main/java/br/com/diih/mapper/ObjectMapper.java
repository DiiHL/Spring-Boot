package br.com.diih.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origins, Class<D> destinations) {
        return mapper.map(origins, destinations);
    }

    public static <O, D> List<D> parseListObjects(List<O> origins, Class<D> destinations) {
        List<D> destinationObjects = new ArrayList<>();

        for (Object o : origins) {
            destinationObjects.add(mapper.map(o, destinations));
        }
        return destinationObjects;
    }
}
