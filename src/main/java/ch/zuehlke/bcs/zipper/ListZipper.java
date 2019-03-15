package ch.zuehlke.bcs.zipper;

import ch.zuehlke.bcs.list.FunctionalList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static ch.zuehlke.bcs.list.FunctionalLists.iterate;

public class ListZipper<T> {

    private final FunctionalList<T> leftReverse;
    private final T cursor;
    private final FunctionalList<T> right;

    ListZipper(FunctionalList<T> leftReverse, T cursor, FunctionalList<T> right) {
        this.leftReverse = leftReverse;
        this.cursor = cursor;
        this.right = right;
    }

    ListZipper<T> listLeft() {
        FunctionalList<T> newLeftReverse = leftReverse.tail();
        T newCursor = leftReverse.head();
        FunctionalList<T> newRight = right.prepend(cursor);
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

    ListZipper<T> listRight() {
        FunctionalList<T> newLeftReverse = leftReverse.prepend(cursor);
        T newCursor = right.head();
        FunctionalList<T> newRight = right.tail();
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

    T read() {
        return cursor;
    }

    ListZipper<T> write(T value) {
        return new ListZipper<>(leftReverse, value, right);
    }

    public List<T> toList(int number) {
        List<T> list = new ArrayList<>(leftReverse.take(number));
        Collections.reverse(list);
        list.add(cursor);
        list.addAll(right.take(number));
        return list;
    }

    public <S> ListZipper<S> fmap(Function<T, S> f) {
        FunctionalList<S> newLeftReverse = leftReverse.fmap(f);
        S newCursor = f.apply(cursor);
        FunctionalList<S> newRight = right.fmap(f);
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

    ListZipper<ListZipper<T>> duplicate() {
        return genericMove(ListZipper::listLeft, ListZipper::listRight);
    }

    <S> ListZipper<S> extend(Function<ListZipper<T>, S> f) {
        return duplicate().fmap(f);
    }

    private ListZipper<ListZipper<T>> genericMove(Function<ListZipper<T>, ListZipper<T>> f, Function<ListZipper<T>, ListZipper<T>> g) {
        FunctionalList<ListZipper<T>> newLeftReverse = iterate(f, this).tail();
        ListZipper<T> newCursor = this;
        FunctionalList<ListZipper<T>> newRight = iterate(g, this).tail();
        return new ListZipper<>(newLeftReverse, newCursor, newRight);
    }

}
