import java.util.LinkedList;

public final class Game {
	private LinkedList<Player> players = new LinkedList<Player>();
	private Board board = null;
	private Player winner;

	public Game(String[] playerNames, int numSquares,
				int[][] snakes, int[][] ladders, int[] deathSquares) {

		makeBoard(numSquares, ladders, snakes, deathSquares);
		makePlayers(playerNames);
	}

	public void play() {
		assert !players.isEmpty() : "No players to play";
		assert board!=null : "No scoreboard to play";

		Die die = new Die();
		initializeGame();

		System.out.println("Initial state : \n" + this);
		int numRounds = 0;
		while (notOver() && moreThanOnePlayerAlive()) {
			int roll = die.roll();
			System.out.println("Current player is " + currentPlayer() + " and rolls " + roll);
			movePlayer(roll);
			System.out.println("State : \n" + this);
			numRounds++;
		}
		if (!moreThanOnePlayerAlive()) {
			winner = players.get(0);
		}

		System.out.println(winner + " has won after " + numRounds + " rounds");
	}

	private void makeBoard(int numSquares, int[][] ladders, int[][] snakes, int[] deathSquares) {
		board = new Board(numSquares, ladders, snakes, deathSquares);
	}

	private void makePlayers(String[] playerNames) {
		assert playerNames.length>0 : "There must be some player" ;
		System.out.println("Players are : ");
		int i=1;
		for (String str : playerNames) {
			Player player = new Player(str);
			players.add(player);
			System.out.println(i + ". " + str);
			i++;
		}
	}

	private void initializeGame() {
		placePlayersAtFirstSquare();
		winner = null;
	}

	private void placePlayersAtFirstSquare() {
		for (Player player : players) {
			board.firstSquare().enter(player);
		}
	}

	private boolean notOver() {
		return winner == null;
	}

	private boolean moreThanOnePlayerAlive() {
		return players.size() > 1;
	}

	private void movePlayer(int roll) {
		Player currentPlayer = players.remove(); // the first element of the list
		currentPlayer.moveForward(roll);
		if (currentPlayer.isDead()) {
			return;
		}
		players.add(currentPlayer); // to the end of list, we're using the linked list as a queue
		if (currentPlayer.wins()) {
			winner = currentPlayer;
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Player player : players) {
			str.append(player.getName()).append(" is at square ").append(player.getPosition() + 1).append("\n");
		}
		return str.toString();
	}

	private Player currentPlayer() {
		assert !players.isEmpty();
		return players.peek();
	}
}
