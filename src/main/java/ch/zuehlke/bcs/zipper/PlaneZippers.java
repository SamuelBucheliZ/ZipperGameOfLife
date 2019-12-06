package ch.zuehlke.bcs.zipper;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

public class PlaneZippers {

    private PlaneZippers() {
        // only static methods
    }

    public static <T> PlaneZipper<T> from(List<List<T>> values, T fillValue) {
        List<ListZipper<T>> valueLines = values
                .map(v -> ListZippers.from(fillValue, v, fillValue));

        ListZipper<T> fillLine = ListZippers.repeat(fillValue);
        Stream<ListZipper<T>> leftReverse = Stream.continually(fillLine);
        ListZipper<T> cursor =  fillLine;
        Stream<ListZipper<T>> right = Stream.ofAll(valueLines).extend(fillLine);

        ListZipper<ListZipper<T>> data = new ListZipper<>(leftReverse, cursor, right);
        return new PlaneZipper<>(data);

    }

    public static <T> List<Function1<PlaneZipper<T>, PlaneZipper<T>>> neighbors() {
        return List.of(
                PlaneZipper::left, PlaneZipper::right, // horizontal
                PlaneZipper::up, PlaneZipper::down, // vertical
                compose(PlaneZipper::left, PlaneZipper::up), // the diagonals
                compose(PlaneZipper::left, PlaneZipper::down),
                compose(PlaneZipper::right, PlaneZipper::up),
                compose(PlaneZipper::right, PlaneZipper::down)
        );
    }

    private static <R, S, T> Function1<R, T> compose(Function1<R, S> f, Function1<S, T> g) {
        return f.andThen(g);
    }
}
