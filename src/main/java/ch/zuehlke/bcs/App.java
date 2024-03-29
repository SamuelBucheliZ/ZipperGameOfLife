package ch.zuehlke.bcs;

import ch.zuehlke.bcs.game.CellState;
import ch.zuehlke.bcs.game.GameOfLife;
import io.vavr.collection.List;

import java.time.LocalDateTime;

import static ch.zuehlke.bcs.game.CellState.ALIVE;
import static ch.zuehlke.bcs.game.CellState.DEAD;

public class App {

    private static final int NUMBER_OF_ITERATIONS = 10;
    private static final int DISPLAY_SIZE = 10;

    public static void main(String... args) {
        GameOfLife gameOfLife = GameOfLife.from(glider());
        print(gameOfLife);

        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            gameOfLife = gameOfLife.evolve();
            print(gameOfLife);
        }

    }

    private static void print(GameOfLife gameOfLife) {
        System.out.println("--- " + LocalDateTime.now());
        System.out.println(gameOfLife.display(DISPLAY_SIZE));
    }

    private static List<List<CellState>> glider()  {
        return List.of(
                        List.of(DEAD, ALIVE, DEAD),
                        List.of(DEAD, DEAD, ALIVE),
                        List.of(ALIVE, ALIVE, ALIVE)
                );
    }

}
