package Poker;

public enum round {
    PREFLOP,
    FLOP,
    TURN,
    RIVER,
    Nothing {
        @Override
        public round next() {
            return PREFLOP;
        };
    };
    public round next() {
        return values()[ordinal() + 1];
    }
}
