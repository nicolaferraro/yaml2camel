package me.nicolaferraro.yaml2camel;

import java.util.Map;
import java.util.Optional;

public class ObjectUtils {

    public static <T> Optional<T> get(Object tree, String child, Class<T> clz) {
        if (tree instanceof Map && child != null) {
            Object childValue = ((Map) tree).get(child);
            if (clz.isInstance(childValue)) {
                return Optional.of(clz.cast(childValue));
            }
        }
        return Optional.empty();
    }

}
