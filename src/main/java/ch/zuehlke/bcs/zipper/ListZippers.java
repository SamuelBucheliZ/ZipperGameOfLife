package ch.zuehlke.bcs.zipper;

import io.vavr.collection.Stream;

import java.util.List;

class ListZippers {

    private ListZippers() {
        // only static methods
    }

    static <T> ListZipper<T> repeat(T value) {
        return new ListZipper<>(Stream.continually(value), value, Stream.continually(value));
    }

    static <T> ListZipper<T> from(T leftRepeat, List<T> list, T rightRepeat) {
        Stream<T> leftReverse = Stream.continually(leftRepeat);
        T cursor = leftRepeat;
        Stream<T> right = Stream.continually(rightRepeat).prependAll(list);
        return new ListZipper<>(leftReverse, cursor, right);
    }
}
