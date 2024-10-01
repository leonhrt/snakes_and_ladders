public class Death extends Square {
    public Death(int position, Board board){
        super(position, board);
    }

    @Override
    public void enter(Player p) {
        System.out.println("Landed on death square, player eliminated.");
        player = null;
        p.setDead(true);
    }
}
