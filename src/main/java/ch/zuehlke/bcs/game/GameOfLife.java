package ch.zuehlke.bcs.game;

import ch.zuehlke.bcs.zipper.ListZipper;
import ch.zuehlke.bcs.zipper.PlaneZipper;
import ch.zuehlke.bcs.zipper.PlaneZippers;
import io.vavr.collection.List;

public class GameOfLife {

    private final PlaneZipper<CellState> cells;

    private GameOfLife(PlaneZipper<CellState> cells) {
        this.cells = cells;
    }

    public static GameOfLife from(List<List<CellState>> initialConfiguration) {
        PlaneZipper<CellState> game = PlaneZippers.from(initialConfiguration, CellState.DEAD);
        return new GameOfLife(game);
    }


    public GameOfLife evolve() {
        PlaneZipper<CellState> evolved = cells.extend(GameOfLife::rule);
        return new GameOfLife(evolved);
    }

    private static CellState rule(PlaneZipper<CellState> z) {
        int aliveNeighborCount = aliveNeighbors(z);
        switch (aliveNeighborCount) {
            case 2: return z.read();
            case 3: return CellState.ALIVE;
            default: return CellState.DEAD;
        }
    }

    private static int aliveNeighbors(PlaneZipper<CellState> z) {
        return PlaneZippers.<CellState>neighbors()
                .map(dir -> dir.apply(z))
                .count(GameOfLife::isAlive);
    }

    private static boolean isAlive(PlaneZipper<CellState> neighbor) {
        return neighbor.read() == CellState.ALIVE;
    }

    public String display(int size) {
        return String.join("\n", cells.mapLine(l -> displayLine(l, size)).toList(size));
    }

    private static String displayLine(ListZipper<CellState> l, int size) {
        return String.join("", l.fmap(CellState::getSymbol).toList(size));
    }


}
