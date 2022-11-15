abstract class Piece {
	int value;
	int color;
	
	public Piece(int val, int color) {
		this.value = val;
		this.color = color;
	}
	
	public void printPiece() {
		System.out.print("[" + Integer.toString(this.value) + " " + Integer.toString(this.color) + "]");
	}
}

class Pawn extends Piece {

	Pawn(int color) {
		super(1, color);
	}

	private boolean isInitial(int i) {
		return i == this.color % 7;
	}
	
	private int legalMoveIDir() {
		return this.color;
	}
	
	private static int calcIDir(int i1,int i2) {
		return (i2 - i1) / (Math.abs(i2 - i1));
	}
	
	private static int calcJDir(int j1, int j2) {
		return (j2 - j1) / (Math.abs(j2 - j1));
	}
	
	public boolean isLegalMove(int i1, int j1, int i2, int j2) {
		int iInc = i2 - i1;
		int jInc = j2 - j1;
		
		//replace with case switch
		if(jInc != 0) {
			return false;
		}
		if(calcIDir(i1, i2) != this.legalMoveIDir()) {
			return false;
		}
		return true;
	}
	
	static boolean isLegalCapture(int i1, int i2, int j1, int j2) {
		return true;
	}
}

class King extends Piece {

	King(int color) {
		super(10, color);
	}
	
	public static boolean isLegalMove(int i1, int j1, int i2, int j2) {
		return Math.abs(i2 - i1) < 2 && 
				Math.abs(j2 - j1) < 2 && 
				i2 > 0 && i2 < 8 && 
				j2 > 0 && j2 < 8;
	}
}


class Board {
	final static int N = 8;
	Piece[][] grid;

	public Board() {
		
		this.grid = new Piece[N][N];
		this.init();

	}
	
	public void init() {
		for(int i = 0; i < N; i++) {
			this.grid[1][i] = new Pawn(0);
			this.grid[6][i] = new Pawn(1);
		}
		this.grid[0][4] = new King(0);
		this.grid[7][4] = new King(1);
	}

	public void printBoard() {
		for(int i = N - 1; i > -1; i--) {
			for(int j = 0; j < N; j++) {
				if(this.grid[i][j] == null) {
					System.out.print("   ");
				} else {
					this.grid[i][j].printPiece();
				}
				System.out.print("  ");
			}
		System.out.println("");
		}
	}
	
	private void nullSquare(int i, int j) {
		this.grid[i][j] = null;
	}
	
	/**
	 * precondition: this.grid[i[0]][i[1]] != null;
	 * 
	 * @param i is a 2-array pointing at piece current location
	 * @param f is a 2-array pointing at location to move to
	 */
	public void movePiece(int i1, int j1, int i2, int j2) {
		if(this.grid[i2][j2] == null) {
			this.grid[i2][j2] = this.grid[i1][j1];
			this.nullSquare(i1, j1);
		} else {
			Piece pieceToMove = this.grid[i1][j1];
			Piece pieceAtLocation = this.grid[i2][j2];
			if(pieceToMove.color == pieceAtLocation.color) {
				return;
			} else {
				this.nullSquare(i2, j2);
				this.grid[i2][j2] = pieceToMove;
				this.nullSquare(i1, j1);
			}
		}
	}
}


public class PlayChess {
	public static void main(String[] a) {
		Board board = new Board();
		board.printBoard();
		board.movePiece(1, 1, 2, 1);
		System.out.println("");
		board.printBoard();
	}

}
