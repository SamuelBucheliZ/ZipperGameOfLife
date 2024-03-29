package ch.zuehlke.bcs.zipper;

import io.vavr.Function1;
import io.vavr.collection.Stream;


public class PlaneZipper<T> {

    private final ListZipper<ListZipper<T>> data;

    PlaneZipper(ListZipper<ListZipper<T>> data) {
        this.data = data;
    }

    PlaneZipper<T> up() {
        return new PlaneZipper<>(data.listLeft());
    }

    PlaneZipper<T> down() {
        return new PlaneZipper<>(data.listRight());
    }

    PlaneZipper<T> left() {
        return new PlaneZipper<>(data.fmap(ListZipper::listLeft));
    }

    PlaneZipper<T> right() {
        return new PlaneZipper<>(data.fmap(ListZipper::listRight));
    }

    private ListZipper<PlaneZipper<T>> horizontal() {
        return genericMove(PlaneZipper::left, PlaneZipper::right);
    }

    private ListZipper<PlaneZipper<T>> vertical() {
        return genericMove(PlaneZipper::up, PlaneZipper::down);
    }

    public T read() {
        return data.read().read();
    }

    public PlaneZipper<T> write(T value) {
        ListZipper<T> oldLine = data.read();
        ListZipper<T> newLine = oldLine.write(value);
        return new PlaneZipper<>(data.write(newLine));
    }

    private <S> PlaneZipper<S> fmap(Function1<T, S> f) {
        ListZipper<ListZipper<S>> mapped = data.fmap(l -> l.fmap(f));
        return new PlaneZipper<>(mapped);
    }

    public <S> ListZipper<S> mapLine(Function1<ListZipper<T>, S> f) {
        return data.fmap(f);
    }

    private PlaneZipper<PlaneZipper<T>> duplicate() {
        ListZipper<PlaneZipper<T>> vertical = vertical();
        ListZipper<ListZipper<PlaneZipper<T>>> horizontalMapped = vertical.fmap(PlaneZipper::horizontal);
        return new PlaneZipper<>(horizontalMapped);
    }

    public <S> PlaneZipper<S> extend(Function1<PlaneZipper<T>, S> f) {
        return duplicate().fmap(f);
    }

    private ListZipper<PlaneZipper<T>> genericMove(
            Function1<PlaneZipper<T>, PlaneZipper<T>> f,
            Function1<PlaneZipper<T>, PlaneZipper<T>> g) {
        Stream<PlaneZipper<T>> left = Stream.iterate(data, applyInternally(f)).map(PlaneZipper::new);
        PlaneZipper<T> cursor = this;
        Stream<PlaneZipper<T>> right = Stream.iterate(data, applyInternally(g)).map(PlaneZipper::new);
        return new ListZipper<>(left, cursor, right);
    }

    private static <T> Function1<ListZipper<ListZipper<T>>, ListZipper<ListZipper<T>>> applyInternally(Function1<PlaneZipper<T>, PlaneZipper<T>> f) {
        return l -> f.apply(new PlaneZipper<>(l)).data;
    }
}
