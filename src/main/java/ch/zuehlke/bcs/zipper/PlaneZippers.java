package ch.zuehlke.bcs.zipper;

import io.vavr.collection.Stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlaneZippers {

    private PlaneZippers() {
        // only static methods
    }

    public static <T> PlaneZipper<T> from(List<List<T>> values, T fillValue) {
        List<ListZipper<T>> valueLines = values.stream()
                .map(v -> ListZippers.from(fillValue, v, fillValue))
                .collect(Collectors.toList());

        ListZipper<T> fillLine = ListZippers.repeat(fillValue);
        Stream<ListZipper<T>> leftReverse = Stream.continually(fillLine);
        ListZipper<T> cursor =  fillLine;
        Stream<ListZipper<T>> right = Stream.continually(fillLine).prependAll(valueLines);

        ListZipper<ListZipper<T>> data = new ListZipper<>(leftReverse, cursor, right);
        return new PlaneZipper<>(data);

    }

    public static <T> List<Function<PlaneZipper<T>, PlaneZipper<T>>> neighbors() {
        return Arrays.asList(
                PlaneZipper::left, PlaneZipper::right, // horizontal
                PlaneZipper::up, PlaneZipper::down, // vertical
                compose(PlaneZipper::left, PlaneZipper::up), // the diagonals
                compose(PlaneZipper::left, PlaneZipper::down),
                compose(PlaneZipper::right, PlaneZipper::up),
                compose(PlaneZipper::right, PlaneZipper::down)
        );
    }

    private static <R, S, T> Function<R, T> compose(Function<R, S> f, Function<S, T> g) {
        return f.andThen(g);
    }
}
