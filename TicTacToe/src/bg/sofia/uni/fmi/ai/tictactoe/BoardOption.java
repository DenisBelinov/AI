package bg.sofia.uni.fmi.ai.tictactoe;

public enum BoardOption {
    EMPTY(0),
    HUMAN_PLAYER(1),
    PC_PLAYER(-1);

    private final int value;
    private BoardOption(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
