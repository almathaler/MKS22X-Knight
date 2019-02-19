public class KnightBoard{
  private int[][] board;
  private int[][] moves = {{-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}};

  /*
  1 Constructor
  @throws IllegalArgumentException when either parameter is <= 0.
  public KnightBoard(int rows,int cols)

      Initialize the board to the correct size and make them all 0's

  */
  public static void main(String[] args){
    KnightBoard example = new KnightBoard(5, 5);
    System.out.println(example.solve(0,0));
    System.out.println(example.toString());
  }

  public KnightBoard(int rows, int cols){
    if (rows <= 0 || cols <= 0){
      throw new IllegalArgumentException("invalid input for board size");
    }
    board = new int[rows][cols];
    for (int r = 0; r<rows; r++){
      for (int c = 0; c<cols; c++){
        board[r][c] = 0;
      }
    }
  }

  /*
  2 toString
  -you get a blank board if you never called solve or when there is no solution
  -blank boards display 0's as underscores
  @returns the properly formatted string (see format for toString later in the post)
  public String toString()
  */
  public String toString(){
    String toReturn = "";
    for (int r = 0; r<board.length; r++){
      for (int c = 0; c<board[0].length; c++){
        if (board[r][c] == 0){ //if the board is in an unfinished state, return all the '_'s
          for (int k = 0; k<board.length; k++){
            for (int l = 0; l<board[0].length; l++){
              toReturn += "_ ";
            }
            toReturn += "\n";
          }
          return toReturn;
        }
      }
    }
    for (int rN = 0; rN<board.length; rN++){
      for (int cN = 0; cN<board[0].length; cN++){
        for (int i = (board.length*board[0].length + "").length() - (board[rN][cN]+"").length(); i>0; i--){
          toReturn+=" ";
        }
        toReturn+=(board[rN][cN] + " ");
      }
      toReturn += "\n";
    }
    return toReturn;
  }
  private String toStringDebug(){
    String toReturn = "";
    for (int rN = 0; rN<board.length; rN++){
      for (int cN = 0; cN<board[0].length; cN++){
        for (int i = (board.length*board[0].length + "").length() - (board[rN][cN]+"").length(); i>0; i--){
          toReturn+=" ";
        }
        toReturn+=(board[rN][cN] + " ");
      }
      toReturn += "\n";
    }
    return toReturn;
  }
  /*
  3 solve
  Modifies the board by labeling the moves from 1 (at startingRow,startingCol) up to the area of the board in proper knight move steps.
  @throws IllegalStateException when the board contains non-zero values.
  @throws IllegalArgumentException when either parameter is negative
   or out of bounds.
  @returns true when the board is solvable from the specified starting position
  */
  public boolean solve(int startingRow, int startingCol){
    if (startingRow < 0 || startingRow >= board.length || startingCol < 0 || startingCol > board[0].length){
      throw new IllegalArgumentException("you can't solve from somewhere that doesn't exist!");
    }
    for (int r = 0; r<board.length; r++){
      for (int c =0; c<board[0].length; c++){
        if (board[r][c] != 0){
          throw new IllegalStateException("board contains non-zero values, can't solve");
        }
      }
    }
    return solveH(startingRow, startingCol, 1);
  }
  private boolean solveH(int row ,int col, int moveNumber){
    System.out.println("In new call of solveH(" + row + ", " + col + ", " + moveNumber + ")");
    board[row][col] = moveNumber;
    System.out.println(toStringDebug());
    if (moveNumber == board.length * board[0].length){
      return true;
    }
    for (int i = 0; i<8; i++){
      int newR = row + moves[i][0];
      int newC = col + moves[i][1];
      if (newR>= 0 && newR<board.length && newC>=0 && newC < board[0].length && board[newR][newC] == 0){
        System.out.println("Now going to call solveH(" + newR + ", " + newC + ", " + (moveNumber+1) + ")");
        if(solveH(newR, newC, moveNumber+1)){
          return true;
        } //can't have it just return solveH, bc minute it gets into a corner w no possible moves false will be returned
        //rather than all other possibilities searched for a true
      }
    }
    board[row][col] = 0; //if there are no good solveHs from here, erase what you put down so that other passes can go over it
    return false; //if you can't move from herer ^^, you have to reset it and return false bc it's a bad path. not being able to move means
    //either every position from u is out of bounds or there are no free positions around u. if for all 8 possible moves nothing is available,
    //the loop will terminate without returning true (won't even check next recursive call). so you need to delete your addition and return false
    //showing it's a bad pathway
    //good pathways will always have valid moves, and so the if statement will always be true until you're in a solveH call where moveNUmber is mx
    //and true can be returned 
  }
  /*
  4 countSolutions
  @throws IllegalStateException when the board contains non-zero values.
  @throws IllegalArgumentException when either parameter is negative
   or out of bounds.
  @returns the number of solutions from the starting position specified
  public int countSolutions(int startingRow, int startingCol)




  Suggestion:
  private boolean solveH(int row ,int col, int moveNumber)
  */
}
