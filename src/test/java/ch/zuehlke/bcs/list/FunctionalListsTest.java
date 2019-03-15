package ch.zuehlke.bcs.list;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FunctionalListsTest {


    @Test
    void fromList_headAndTail() {
        List<String> list = Arrays.asList("1", "2", "3");
        FunctionalList<String> functionalList = FunctionalLists.fromList(list);

        assertThat(functionalList.head(), is("1"));
        assertThat(functionalList.tail().head(), is("2"));
        assertThat(functionalList.tail().tail().head(), is("3"));
    }

    @Test
    void fromList_toList() {
        List<String> list = Arrays.asList("1", "2", "3", "4");
        FunctionalList<String> functionalList = FunctionalLists.fromList(list);

        assertThat(functionalList.take(3), is(Arrays.asList("1", "2", "3")));
    }

    @Test
    void sequenceFrom() {
        FunctionalList<Integer> sequence = FunctionalLists.sequenceFrom(0);

        assertThat(sequence.take(5), is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    void iterate() {
        FunctionalList<String> functionalList = FunctionalLists.iterate(s -> s + "x", "");

        assertThat(functionalList.take(3), is(Arrays.asList("", "x", "xx")));
    }


}
