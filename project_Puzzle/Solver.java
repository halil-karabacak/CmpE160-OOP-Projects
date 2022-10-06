
package project;
   
import java.io.File;


import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;




public class Solver {
	private PriorityObject winner;	
	
	// visited holds all Puzzle objects that we have visited so far in order to prevent any closed loops.
	private HashSet<Puzzle> visited = new HashSet<>();

	// priority = moves + Manhattan
	// if priority is low, it's good.
	// find a solution to the initial board
	
	public Solver(Puzzle root) {
		System.out.println("Starting the solver...");
		if (root == null)
			throw new IllegalArgumentException();
		solve(root);
		System.out.println("Solving is finished...");
	}
		
	private boolean isSolvable(Puzzle puzzle) {
		int[][] tiles = puzzle.getTiles();
		
		ArrayList<Integer> tilesList = new ArrayList<>();
		
		if (tiles.length % 2 == 0)
			return true;

		for (int c = 0; c < tiles.length; c++) {
			for (int d = 0; d < tiles.length; d++) {
				if (tiles[c][d] == 0)
				tilesList.add(tiles[c][d]);
			}
		}
		
		int invCount = 0;
		for (int first = 0; first < tilesList.size() - 1; first++) {
			for (int second = first + 1; second < tilesList.size(); second++) {
				if (tilesList.get(second) != 0 && tilesList.get(first) != 0 && tilesList.get(second) < tilesList.get(first))
					invCount++;
			}
		}
		return invCount % 2 == 0;	
	}
	
	
	/*
	 * 1 - I first clear the visited HashSet.
	 * 
	 * 2 - Then creating a PriorityObject, I add the root element of Queue whose <prev> is null.
	 * 
	 * 3 - Since Queue is a FIFO structure, I can use a combination of Breadth-First Search and Depth-First Search.
	 * 	*BFS comes from the structure that we use, if we didn't define a CustomComparator, it will completely be BFS.
	 * 	*However, we defined a CustomComparator which adds elements to the PriorityQueue with respect to their f values
	 * 		so smaller-f-having PriorityObject will lead the PriorityQueue, which makes the structure similar to DFS. 
	 * 
	 * 4 - Since we know, we always continue with the smallest f value(h value), we can conclude that when we find a solution
	 * it will have the smallest number of movement possible, if possible. But the order may be different, because other implementations
	 * may follow different paths. But at the end they have to find the same number, rather than same path.
	 * 
	 * After explaining the algorithm, 
	 * 	I poll from the pq unless it is empty.
	 * 	I add a PriorityQueue object to the pq if it has not been visited yet.
	 * 
	 */
	
	private void solve(Puzzle root) {
		visited.clear();
		
		if (!isSolvable(root)) {
			winner = null;
			return;
		}

		Queue<PriorityObject> pq = new PriorityQueue<>(100, new CustomComparator());
		pq.add(new PriorityObject(root, 0, null));
		
		int limit = 1000000000;
		
		while (!pq.isEmpty()) {
			PriorityObject curr = pq.poll();
			visited.add(curr.board);
			
			if (curr.board.isCompleted()) {		
				winner = curr;
				limit = Math.min(winner.g, limit);
				return;
			}
			
			for (Puzzle nextPuzzle : curr.board.getAdjacents()) {
				PriorityObject next = new PriorityObject(nextPuzzle, curr.g + 1, curr);
				
				if (next.g >= limit) {
					break;
				}
				
				boolean contr = true;
				Iterator<Puzzle> puzz = visited.iterator();	
				while (puzz.hasNext()) {
					if (puzz.next().equals(nextPuzzle)) {
						contr = false;
						continue;
					}
				}				
				if (contr)
					pq.add(next);
			}
		}
	}

	// number of moves is equal to the g parameter of winner.
	public int getMoves() {
		return winner.g;
	}
	
	/*
	 * We know the winner, so we can trace back until the root PriorityObject using prev field.
	 * While doing so, to not to lost them, I add them to an ArrayList, and return it.
	 */
	
	public Iterable<Puzzle> getSolution() {
		if (winner == null)
			return null;
		ArrayList<Puzzle> traceBack = new ArrayList<>();
		
		while (winner != null) {
			traceBack.add(winner.board);
			winner = winner.prev;
		}
		return traceBack;
	}
	

	private class PriorityObject {
		private Puzzle board;
		private int f;
		private PriorityObject prev;
		private int g;
		
		/*
		 * To be able to use HashSet with Priority Object class, I need to define hashCode and equals method. Since
		 * I compare PriorityObjects and Puzzle objects with respect to their tiles array, I use Arrays.hashCode() and
		 * Arrays.equals() method.
		 * Rest of the class is defined in the description file, I don't want to overcomment.
		 */
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(board.getTiles());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PriorityObject other = (PriorityObject) obj;
			return Arrays.equals(board.getTiles(), other.board.getTiles());
		}
		
		public PriorityObject(Puzzle board, int g, PriorityObject prev) {
			this.board = board;
			this.g = g;
			this.prev = prev;
			f = this.g + board.h();
		}
		
		protected int getF() {
			return this.f;
		}

		
		Comparator<PriorityObject> comparator(){
			return new CustomComparator();
		}
	}

	
	class CustomComparator implements Comparator<PriorityObject> {
		public int compare(PriorityObject o1, PriorityObject o2) {
			return o1.getF() - o2.getF(); 
		}
	}

	// test client
	public static void main(String[] args) throws IOException {

		File input = new File("input.txt");
		Scanner in = new Scanner(new FileReader(input));
		ArrayList<String> data = new ArrayList<>();
		
		while (in.hasNextLine()) {
			data.add(in.nextLine());
		}
		
		int n = Integer.parseInt(data.get(0));
		int[][] initialBoard = new int[n][n];
		
		for (int b = 1; b < n + 1; b++) {
			String[] tt = data.get(b).split(" ");
			for (int c = 0; c < n; c++) {
				initialBoard[b-1][c] = Integer.parseInt(tt[c]);
			}
		}
		
		
		Puzzle initial = new Puzzle(initialBoard);
		
		
		
		// Read this file int by int to create 
		// the initial board (the Puzzle object) from the file
		
		
		// solve the puzzle here. Note that the constructor of the Solver class already calls the 
		// solve method. So just create a solver object with the Puzzle Object you created above 
		// is given as argument, as follows:
		
		Solver solver = new Solver(initial);  // where initial is the Puzzle object created from input file

		// You can use the following part as it is. It creates the output file and fills it accordingly.
		File output = new File("output.txt");
		output.createNewFile();
		PrintStream write = new PrintStream(output);
		
		if (solver.winner == null) {
			write.println("No solution found.");
		}
		
		else {
			write.println("Minimum number of moves = " + solver.getMoves());
		
			ArrayList<Puzzle> tt = new ArrayList<>();
			
			for (Puzzle board : solver.getSolution())
				tt.add(board);
			
			for (int b = tt.size() - 1; b >= 0; b--)
				write.println(tt.get(b));
		}
		write.close();
	}
}

