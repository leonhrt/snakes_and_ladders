public class SnakeOrLadder extends Square {
    private final int transport;

    public SnakeOrLadder(int pos, Board b, int transport) {
        super(pos, b);
        this.transport = transport;
    }

    @Override
    protected Square landHereOrGoHome() {
        String ladderOrSnake = (transport > 0) ? "ladder" : "snake";

        System.out.println(ladderOrSnake + " from " + (position + 1) + " to "
                + (destination().getPosition() + 1));
        return destination().landHereOrGoHome();
    }

    private Square destination() {
        return findRelativeSquare(transport);
    }
}
