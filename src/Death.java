public class Death extends Square {
    public Death(int position, Board board){
        super(position, board);
    }

    @Override
    protected Square landHereOrGoHome() {
        System.out.println("Death square landed: player is eliminated.");
        return new Square(-1, board);
    }
}
