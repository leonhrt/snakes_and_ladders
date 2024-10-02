import java.util.ArrayList;

public final class Board {
	private ArrayList<Square> squares = new ArrayList<>();
	private static final int MIN_NUM_SQUARES = 10;

	public Board(int numSquares, int[][] ladders, int[][] snakes, int[] deathSquares) {
		assert numSquares > MIN_NUM_SQUARES : "There must be at least " + MIN_NUM_SQUARES + " squares";
		makeSquares(numSquares);
		makeSnakesOrLadders(ladders);
		makeSnakesOrLadders(snakes);
		makeDeaths(deathSquares);
	}

	public Square firstSquare() {
		return squares.get(0);
	}

	public Square lastSquare() {
		return squares.get(squares.size()-1);
	}

	public Square findSquare(int position) {
		assert (position>0) && (position<numberOfSquares()) : "inexistent square";
		return squares.get(position);
	}

	public int numberOfSquares() {
		assert !squares.isEmpty();
		return squares.size();
	}

	private void makeSquares(int numSquares) {
		System.out.println("There are " + numSquares + " squares");
		squares.add(new FirstSquare(this));
		for (int position=1 ; position<numSquares ; position++) {
			Square square = new Square(position, this);
			squares.add(square);
		}
		assert squares.get(numSquares-1).isLastSquare();
	}

	private void makeDeaths(int[] deathSquares) {
		assert deathSquares.length < squares.size() - 1 : "All squares can't be death squares";

		for (int pos : deathSquares) {
			assert pos < squares.size() && pos >= 0;
			System.out.println("death square at " + pos);
			squares.set(pos - 1, new Death(pos - 1, this));
		}
	}

	private void makeSnakesOrLadders(int[][] toFrom) {
		for (int[] snakeOrLadder : toFrom) {
			assert snakeOrLadder.length == 2;

			int fromPosition = snakeOrLadder[0] - 1;
			int toPosition = snakeOrLadder[1] - 1;
			int transport = toPosition - fromPosition;

			assert toPosition < numberOfSquares() - 1 && fromPosition > 0;
			assert toPosition > 0 && toPosition < numberOfSquares() - 1;

			String ladderOrSnake = (transport > 0) ? "ladder" : "snake";

			System.out.println(ladderOrSnake + " from " + (fromPosition + 1) + " to " + (toPosition + 1));
			squares.set(fromPosition, new SnakeOrLadder(fromPosition, this, transport));
		}
	}

    private void makeSnakes(int[][] snakes) {
        for (int[] snake : snakes) {
            assert snake.length == 2;

            int fromPosition = snake[0] - 1;
            int toPosition = snake[1] - 1;
            int transport = toPosition - fromPosition;

            assert transport < 0 : "In snake, destination equal or after origin";
            assert (toPosition > 0) && (toPosition < numberOfSquares() - 1);
            assert (fromPosition < numberOfSquares() - 1) && (fromPosition > 0);

            System.out.println("snake from " + (fromPosition + 1) + " to " + (toPosition + 1));
            squares.set(fromPosition, new Snake(fromPosition, this, transport));
        }
    }
	
    private void makeLadders(int[][] ladders) {
        for (int[] ladder : ladders) {
            assert ladder.length == 2;

            int fromPosition = ladder[0] - 1;
            int toPosition = ladder[1] - 1;
            int transport = toPosition - fromPosition;

            assert transport > 0 : "In ladder, origin equal or after destination";
            assert (toPosition < numberOfSquares()) && (toPosition > 0);
            assert (fromPosition > 0) && (fromPosition < numberOfSquares());

            System.out.println("ladder from " + (fromPosition + 1) + " to " + (toPosition + 1));
            squares.set(fromPosition, new Ladder(fromPosition, this, transport));
        }
    }    
}
