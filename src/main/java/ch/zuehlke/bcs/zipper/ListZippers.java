package ch.zuehlke.bcs.zipper;

import ch.zuehlke.bcs.list.FunctionalList;
import ch.zuehlke.bcs.list.FunctionalLists;

import java.util.List;

class ListZippers {

    private ListZippers() {
        // only static methods
    }

    static <T> ListZipper<T> repeat(T value) {
        return new ListZipper<>(FunctionalLists.repeat(value), value, FunctionalLists.repeat(value));
    }

    static <T> ListZipper<T> from(T leftRepeat, List<T> list, T rightRepeat) {
        FunctionalList<T> leftReverse = FunctionalLists.repeat(leftRepeat);
        T cursor = leftRepeat;
        FunctionalList<T> right = FunctionalLists.repeat(rightRepeat).prepend(list);
        return new ListZipper<>(leftReverse, cursor, right);
    }
}
