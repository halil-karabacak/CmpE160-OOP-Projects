package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Puzzle {
	private final int[][] tiles;
	private final int[][] goalState;
	private int dimension;
	private final HashMap<Integer, int[]> locations = new HashMap<>();
	
	/*
	 * 
	 * Constructor call. Changing <tiles>, <dimension> and <goalState>.
	 * Also creating <locations> HashMap which I will use when I calculate Heuristic values of boards.
	 * 
	 */

	public Puzzle(int[][] tiles) {
		int dimension = tiles.length;
		this.dimension = dimension;
		goalState = new int[dimension][dimension];
		
		this.tiles = new int[dimension][dimension];
		for (int b = 0; b < dimension; b++) {
			for (int c = 0; c < dimension; c++) {
				this.tiles[b][c] = tiles[b][c];
				goalState[b][c] = b * dimension + c + 1;
				int[] loc = new int[2];
				loc[0] = b;
				loc[1] = c;

				if (b == dimension - 1 && c == dimension - 1) 
					locations.put(0, loc);
				else
					locations.put((b * dimension + c + 1), loc);
			}
		}
		goalState[dimension - 1][dimension - 1] = 0;
	}
	
	// Returns tiles of the object.
	protected int[][] getTiles(){
		return this.tiles;
	}
	
	
	// toString method
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(tiles.length + "\n");
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				str.append(" "+tiles[i][j]);
			}
			str.append("\n");
		}
		return str.toString();
	}
	
	// returns dimension of the board
	public int dimension() {
		return this.dimension;
	}

	// sum of Manhattan distances between tiles and goal
	// The Manhattan distance between a board and the goal board is the sum
	// of the Manhattan distances (sum of the vertical and horizontal distance)
	// from the tiles to their goal positions.
	
	
	/*
	 * Calculating Manhattan distance is done by using a HashMap whose id's are tile values and
	 * whose values are integer arrays length of 2. First element of the array equals to the y,
	 * and second element of the array equals to the x coordinate on the board.
	 */
	
	public int h() {
		int heuristic = 0;
		int dimension = this.tiles.length;
		for (int b = 0; b < dimension; b++) {
			for (int c = 0; c < dimension; c++) {
				int currentNum = this.tiles[b][c];
				
				if (currentNum == 0)
					continue;
				
				heuristic += Math.abs(locations.get(currentNum)[0] - b);
				heuristic += Math.abs(locations.get(currentNum)[1] - c);
			}
		}
		return heuristic;
	}
	
	// If sum of Manhattan distances are 0, then the board is completed.
	public boolean isCompleted() {
		return h() == 0;
	}
	
	/*
	 * To compare any 2 <Puzzle> objects I need to define an equals method. I compare the two boards with respect to their
	 * tiles. If their tiles are identical, without looking any further, İ say these two Puzzle objects are same.
	 * I use this method in order prevent any closed loop in my solution part.
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Puzzle other = (Puzzle) obj;
		return Arrays.deepEquals(tiles, other.tiles);
	}
	
	// Returns any kind of collection that implements iterable.
	// For this implementation, I choose stack.
	
	/*
	 * To find all adjacent states, I created a 2D array namely <possibleMovements> which holds all possible directions.
	 * However, when the <0> is on the edge or on the corner, the number of movements it can do changes which I look with 2 if 
	 * statements. I check whether index exceeds the dimension or drops below the zero, in both cases I would get an error. So,
	 * I just continue.
	 * 
	 * After making swap operations on <copyTiles> array, I create <Puzzle> object and add it to an ArrayList.
	 * 
	 * I return arraylist.
	 * 
	 */
	public Iterable<Puzzle> getAdjacents() {
		int[][] possibleMovements = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
		ArrayList<Puzzle> adjacents = new ArrayList<>();
		
		for (int firstIndex = 0; firstIndex < this.dimension; firstIndex++) {
			for (int secondIndex = 0; secondIndex < this.dimension; secondIndex++) {
				
				if (tiles[firstIndex][secondIndex] == 0) {
					for (int[] possibleMovement : possibleMovements) {
						int[][] copyTiles = this.copy(this.tiles);
						if (firstIndex + possibleMovement[0] >= this.dimension || firstIndex + possibleMovement[0] < 0) 
							continue;
						
						if (secondIndex + possibleMovement[1] >= this.dimension || secondIndex + possibleMovement[1] < 0)
							continue;
						
						int exchangeValue = copyTiles[firstIndex + possibleMovement[0]][secondIndex + possibleMovement[1]];
						copyTiles[firstIndex][secondIndex] = exchangeValue;
						copyTiles[firstIndex + possibleMovement[0]][secondIndex + possibleMovement[1]] = 0;
						Puzzle temporaryPuzzle = new Puzzle(copyTiles);
						adjacents.add(temporaryPuzzle);
					}
				}
			}
		}
		return adjacents;
	}
	
	/*
	 * Creates a copy of the source array.
	 */
	private int[][] copy(int[][] source) {
		int[][] copyArr = new int[this.dimension][this.dimension];
		
		for (int c = 0; c < this.dimension; c++) {
			for (int k = 0; k < this.dimension; k++) {
				copyArr[c][k] = source[c][k];
			}
		}
		return copyArr;
		
	}


	// You can use this main method to see your Puzzle structure.
	// Actual solving operations will be conducted in Solver.main method
	/*
	public static void main(String[] args) {
		int[][] array = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
		Puzzle board = new Puzzle(array);
		System.out.println(board);
		System.out.println(board.dimension());
		System.out.println(board.h());
		System.out.println(board.isCompleted());
		Iterable<Puzzle> itr = board.getAdjacents();
		for (Puzzle neighbor : itr) {
			System.out.println(neighbor);
			System.out.println(neighbor.equals(board));
		}
	}
	*/
}

