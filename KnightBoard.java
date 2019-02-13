public class KnightBoard{
  int[][] board;
  /*
  **@throws IllegalArgumentException when either parameter is negative.
  */
  public KnightBoard(int startingRows,int startingCols) throws IllegalArgumentException{
    if (startingRows <= 0 || startingCols <= 0){
      throw new IllegalArgumentException("your board must have positive non-zero dimensions");
    }
    board = new int[startingRows][startingCols];
    for (int r = 0; r<startingRows; r++){
      for (int c = 0; c<startingRows; c++){
        board[r][c] = 0;
      }
    }
  }

      //Initialize the board to the correct size and make them all 0's

  /*
  **blank boards display 0's as underscores
  **you get a blank board if you never called solve or
  **when there is no solution
  */
  public String toString(){
    int numSpaces = 0;
    int temp = board.length * board[0].length;
    while (temp > 0){
       temp/=10;
       numSpaces++;
    }
    String toReturn = "";
    if (board[0][0] == 0){
      for (int r =0; r<board.length; r++){
        for (int c = 0; c<board[0].length; c++){
          toReturn += "_";
        }
        toReturn += "\n";
      }
      return toReturn;
    }
    for (int r =0; r<board.length; r++){
      for (int c = 0; c<board[0].length; c++){
        if ((board[r][c] + "").length() < numSpaces){
          for (int i = 0; i<(numSpaces - (board[r][c] + "").length()); i++){
            toReturn += " ";
          }
        }
        toReturn += board[r][c];
      }
      toReturn += "\n";
    }
    return toReturn;
  }

  /*
  **Modifies the board by labeling the moves from 1
  **(at startingRow,startingCol) up to the area of the board in proper knight move steps.
  **@throws IllegalStateException when the board contains non-zero values.
  **@throws IllegalArgumentException when either parameter is negative
  **or out of bounds.
  */
  public boolean solve(int startingRow, int startingCol){
    if (startingRow<0 || startingRow>=board.length || startingCol<0 || startingCol>=board[0].length){
      throw new IllegalStateException("you can't solve from an out-of-bounds position!");
    }
    return false;
  }
  /*
  **@throws IllegalStateException when the board contains non-zero values.
  **@throws IllegalArgumentException when either parameter is negative
  **or out of bounds.
  */
  public int countSolutions(int startingRow, int startingCol){}

  //Suggestion:
  //private boolean solveH(int row ,int col, int level)
  // level is the # of the knight
}
