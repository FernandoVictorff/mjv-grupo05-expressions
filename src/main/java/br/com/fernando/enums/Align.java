package br.com.fernando.enums;

public enum Align {
    LEFT("-"),
    RIGHT("");

    private String symbol;

    Align(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
