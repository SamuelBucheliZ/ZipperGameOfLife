package ch.zuehlke.bcs.game;

public enum CellState {
    ALIVE("*"), DEAD(".");

    private final String symbol;

    CellState(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
