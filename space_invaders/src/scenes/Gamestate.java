package scenes;

public enum Gamestate {
    pause   (0),
    normal  (1),
    cutcene (2),
    gameover(-1);

    private int state;
    Gamestate(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
