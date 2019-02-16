import java.util.ArrayList;
public class KnightBoard{
  private class move{
    int rChange;
    int cChange;
    private move(int rC, int cC){
      rChange = rC;
      cChange = cC;
    }
    public String toString(){
      String toReturn = "(" + rChange + ", " + cChange + ")";
      return toReturn;
    }

  }
  //have to check (r-2, c-1), (r-2, c+1), (r-1, c-2), (r+1, c-2), (r+2, c-1), (r+2, c+1), (r-1, c+2), (r+1, c+2))
  int[][] board;
  ArrayList<move> moveList = new ArrayList<move>(5);
  public static void main(String[] args){
    KnightBoard example = new KnightBoard(5, 5);
    System.out.println(example.moveList);
    example.solve(0, 0);
    System.out.println(example.toString());
  }
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
    move m0 = new move(-2, -1);
    moveList.add(m0);
    move m1 = new move(-2, 1);
    moveList.add(m1);
    move m2 = new move(-1, -2);
    moveList.add(m2);
    move m3 = new move(1, -2);
    moveList.add(m3);
    move m4 = new move(2, -1);
    moveList.add(m4);
    move m5 = new move(2, 1);
    moveList.add(m5);
    move m6 = new move(-1, 2);
    moveList.add(m6);
    move m7 = new move(1, 2);
    moveList.add(m7);
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
    /*
    boolean anyZero = false;
    for (int r =0; r<board.length; r++){
      for (int c = 0; c<board[0].length; c++){
        if(board[r][c] == 0){
          anyZero = true;
        }
      }
    }
    if (anyZero){ //change to check everything
      for (int r =0; r<board.length; r++){
        for (int c = 0; c<board[0].length; c++){
          toReturn += "_";
        }
        toReturn += "\n";
      }
      return toReturn;
    }
    */
    for (int r =0; r<board.length; r++){
      for (int c = 0; c<board[0].length; c++){
        if ((board[r][c] + "").length() < numSpaces){
          for (int i = 0; i<(numSpaces - (board[r][c] + "").length()); i++){
            toReturn += " ";
          }
        }
        toReturn += board[r][c] + " ";
      }
      toReturn += "\n";
    }
    return toReturn;
  }

  /*
  **Modifies the board by labeling the moves from 1
  **(at startingRow,startingCol) up to the area of the board in proper knight move steps.board[r]
  **@throws IllegalStateException when the board contains non-zero values.
  **@throws IllegalArgumentException when either parameter is negative
  **or out of bounds.
  */
  public boolean solve(int startingRow, int startingCol){
    if (startingRow<0 || startingRow>=board.length || startingCol<0 || startingCol>=board[0].length){
      throw new IllegalStateException("you can't solve from an out-of-bounds position!");
    }
    return solveH(startingRow, startingCol, 1);
  }

  private boolean clearBoard(){
    for (int r = 0; r<board.length; r++){
      for (int c = 0; c<board[0].length; c++){
        board[r][c] = 0;
      }
    }
    return true;
  }

  private boolean solveH(int r, int c, int level){
    if (board[r][c] == 0){ //if you can add
      board[r][c] = level;
      //System.out.println(r + ", " + c + " is free. setting that to  " + level);
      //System.out.println(toString());
      if (level == board.length * board[0].length){
        return true; //if you added the lsat move, true
      }
      for (int i = 0; i<moveList.size(); i++){ //if not, try all 8 moves
        try{ //some will be outofbounds. if they are, j continue on w the loop
          //System.out.println("\n***Now calling solveH in for loop to check " + (r+moveList.get(i).rChange) + ", " + (c+moveList.get(i).cChange));
          if (solveH(r+moveList.get(i).rChange, c+moveList.get(i).cChange, level+1)){ //if any of the moves are valid
            return true; //if you can keep adding until you get to the last, return true
          }
        }catch(ArrayIndexOutOfBoundsException e){
          //don't do anything j let loop continue
        }
      }
      //System.out.println("no possible move from here, so setting the board at " + r + ", " + c + " to 0.");
      board[r][c] = 0;
      //System.out.println(toString());
    }
    return false; //if you can't add to this spot return false
    /*
    //have to check (r-2, c-1), (r-2, c+1), (r-1, c-2), (r+1, c-2), (r+2, c-1), (r+2, c+1), (r-1, c+2), (r+1, c+2))
    for (int i = 0; i<moveList.size(); i++){ //go through every possible move
      try{ //try block so things that are out of bounds are just skipped
        int newR = r + moveList.get(i).rChange; //based on the changes in the list, make new coords for next call
        int newC = c + moveList.get(i).cChange;
        System.out.println("\n\nIn solveH, checking to see if board at " + r + ", " + c + " is available for spot " + level);
        if (board[r][c] == 0 || (i > 0 && board[r][c] == level)){ //if the current value can have something placed on it
          System.out.println("It is available, changing it to " + level);
          //System.out.println(toString());
          board[r][c] = level; //place the new number
          System.out.println(toString());
          if (level == board.length * board[0].length){ //if this number was the area, you're done and return true
            return true;
          }
          System.out.println("Now checking moving by " + moveList.get(i).rChange + ", " + moveList.get(i).cChange);
          if (solveH(newR, newC, level+1)){
            return true;
          }//otherwise, after adding, just go to the next spot (determined by moveList) and see where you can assign the next val
        }
      }catch(ArrayIndexOutOfBoundsException e){
        //don;t do anything just try the next one
      }
    }
    */
    //System.out.println("No move possible from " + r + ", " + c);
    //System.out.println(toString());
    //clearBoard(); // if it didn't work, clear the board so that other calls won't be messed up
    //return false;
  }
  /*
  **@throws IllegalStateException when the board contains non-zero values.
  **@throws IllegalArgumentException when either parameter is negative
  **or out of bounds.
  */
  public int countSolutions(int startingRow, int startingCol){
    return -1;
  }

  //Suggestion:
  //private boolean solveH(int row ,int col, int level)
  // level is the # of the knight
}
