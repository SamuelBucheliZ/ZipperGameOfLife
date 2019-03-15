package ch.zuehlke.bcs.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

class GeneratorList<T> implements FunctionalList<T> {

    private final T head;
    private final Supplier<FunctionalList<T>> tailGenerator;

    GeneratorList(T head, Supplier<FunctionalList<T>> tailGenerator) {
        this.head = head;
        this.tailGenerator = tailGenerator;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public FunctionalList<T> tail() {
        return tailGenerator.get();
    }

    @Override
    public FunctionalList<T> prepend(List<T> values) {
        if (values.isEmpty()) {
            return this;
        }
        return new GeneratorList<>(values.get(0), () -> prepend(values.subList(1, values.size())));
    }

    @Override
    public <S> FunctionalList<S> fmap(Function<T, S> f) {
        return new GeneratorList<>(f.apply(head), () -> tail().fmap(f));
    }

    @Override
    public List<T> take(int n) {
        if (n==0){
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>(tail().take(n-1));
        result.add(0, head);
        return result;
    }
}
