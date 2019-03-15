package ch.zuehlke.bcs.list;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class FunctionalLists {

    private FunctionalLists() {
        // only static methods
    }

    static <T> FunctionalList<T> fromList(List<T> list) {
        List<T> copy = new LinkedList<>(list);
        return new GeneratorList<>(copy.get(0), () -> fromList(copy.subList(1, copy.size())));
    }

    static FunctionalList<Integer> sequenceFrom(int i) {
        return new GeneratorList<>(i, () -> sequenceFrom(i+1));
    }

    public static <T> FunctionalList<T> iterate(Function<T, T> f, T initial) {
        return new GeneratorList<>(initial, () -> iterate(f, f.apply(initial)));
    }

    public static <T> FunctionalList<T> repeat(T value) {
        return new GeneratorList<>(value, () -> repeat(value));
    }
}
