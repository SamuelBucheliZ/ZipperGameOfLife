package ch.zuehlke.bcs.zipper;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.collection.Stream;


public class ListZipper<T> {

    private final Stream<T> leftReverse;
    private final T cursor;
    private final Stream<T> right;

    ListZipper(Stream<T> leftReverse, T cursor, Stream<T> right) {
        this.leftReverse = leftReverse;
        this.cursor = cursor;
        this.right = right;
    }

    ListZipper<T> listLeft() {
        Stream<T> newLeftReverse = leftReverse.tail();
        T newCursor = leftReverse.head();
        Stream<T> newRight = right.prepend(cursor);
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

    ListZipper<T> listRight() {
        Stream<T> newLeftReverse = leftReverse.prepend(cursor);
        T newCursor = right.head();
        Stream<T> newRight = right.tail();
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

    T read() {
        return cursor;
    }

    ListZipper<T> write(T value) {
        return new ListZipper<>(leftReverse, value, right);
    }

    public List<T> toList(int number) {
        return Stream.of(
                leftReverse.take(number).reverse().toStream(),
                Stream.of(cursor),
                right.take(number)
        )
                .flatMap(x -> x)
                .toList();
    }

    public <S> ListZipper<S> fmap(Function1<T, S> f) {
        Stream<S> newLeftReverse = leftReverse.map(f);
        S newCursor = f.apply(cursor);
        Stream<S> newRight = right.map(f);
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

    ListZipper<ListZipper<T>> duplicate() {
        return genericMove(ListZipper::listLeft, ListZipper::listRight);
    }

    <S> ListZipper<S> extend(Function1<ListZipper<T>, S> f) {
        return duplicate().fmap(f);
    }

    private ListZipper<ListZipper<T>> genericMove(Function1<ListZipper<T>, ListZipper<T>> f, Function1<ListZipper<T>, ListZipper<T>> g) {
        Stream<ListZipper<T>> newLeftReverse = Stream.iterate(this, f).tail();
        ListZipper<T> newCursor = this;
        Stream<ListZipper<T>> newRight = Stream.iterate(this, g).tail();
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

}
