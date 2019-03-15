package ch.zuehlke.bcs.list;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public interface FunctionalList<T> {

    T head();

    FunctionalList<T> tail();

    default FunctionalList<T> prepend(T value) {
        return prepend(Collections.singletonList(value));
    }

    FunctionalList<T> prepend(List<T> values);

    <S> FunctionalList<S> fmap(Function<T, S> f);

    List<T> take(int n);

}
