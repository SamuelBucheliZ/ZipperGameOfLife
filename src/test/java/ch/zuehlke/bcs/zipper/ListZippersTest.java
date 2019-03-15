package ch.zuehlke.bcs.zipper;


import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ListZippersTest {

    @Test
    void repeat() {
        ListZipper<Integer> repeat = ListZippers.repeat(5);

        assertThat(repeat.read(), is(5));
        assertThat(repeat.listRight().read(), is(5));
        assertThat(repeat.listLeft().read(), is(5));
    }

    @Test
    void from() {
        ListZipper<Integer> from = ListZippers.from(1, Arrays.asList(2, 3), 4);

        assertThat(from.read(), is(1));

        assertThat(from.listLeft().read(), is(1));
        assertThat(from.listLeft().listLeft().read(), is(1));

        assertThat(from.listRight().read(), is(2));
        assertThat(from.listRight().listRight().read(), is(3));
        assertThat(from.listRight().listRight().listRight().read(), is(4));
        assertThat(from.listRight().listRight().listRight().listRight().read(), is(4));
    }

    @Test
    void duplicate() {
        ListZipper<Integer> from = ListZippers.from(1, Arrays.asList(2, 3), 4);

        assertThat(from.duplicate().listRight().read().read(), is(from.listRight().read()));
        assertThat(from.duplicate().listLeft().read().read(), is(from.listLeft().read()));
    }

}
