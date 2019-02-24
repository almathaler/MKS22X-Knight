import java.util.Collections;
import java.util.ArrayList;
public class KnightBoard{
  private class move{
   int rChange;
   int cChange;
   private move(int rC, int cC){
     rChange = rC;
     cChange = cC;
   }
  }
  private int[][] board;
  //private int[][] moves = {{-1,-2}, {-2,-1}, {-2,1}, {1,-2}, {1,2}, {2,1}, {2,-1}, {-1,2}};
  ArrayList<move> moveList = new ArrayList<move>(8);
  private int[][] opBoard; //optimized board. will use this to decide next moves but will actually modify board for presentation

  /*
  1 Constructor
  @throws IllegalArgumentException when either parameter is <= 0.
  public KnightBoard(int rows,int cols)

      Initialize the board to the correct size and make them all 0's

  */
  public static void main(String[] args){
    for (int i = 1; i<7; i++){
      for (int k = 1; k<7; k++){
        KnightBoard example = new KnightBoard(i, k);
        System.out.println("For " + i + "x" + k + " :");
        System.out.println(example.opToString());
        example.solve(0,0);
        System.out.println(example.toString());
        System.out.println(example.opToString());
        example.clear();
        System.out.println("Possible solutions from (0, 0): " + example.countSolutions(0, 0));
        System.out.println("****\n");
      }
    }
  }

  public KnightBoard(int rows, int cols){
    if (rows <= 0 || cols <= 0){
      throw new IllegalArgumentException("invalid input for board size");
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
    board = new int[rows][cols];
    opBoard = new int[rows][cols];
    //the optimized board for moves
    for (int r = 0; r<rows; r++){
      for (int c = 0; c<cols; c++){
        int toAssign = 0;
        for (int moveNum = 0; moveNum<8; moveNum++){
          int newR = r + moveList.get(moveNum).rChange; //based on the changes in the list, make new coords for next call
          int newC = c + moveList.get(moveNum).cChange;
          try{
            opBoard[newR][newC] = opBoard[newR][newC]; //just check if it's not outofbounds
            toAssign++; //if it is out of bounds, it won't get to this part. if it's not, then it will
          }catch (IndexOutOfBoundsException e){
            //do nothing
          }
        }
        opBoard[r][c] = toAssign;
      }
    }
    //the board we will actually be working on/writing down steps on
    for (int r = 0; r<rows; r++){
      for (int c = 0; c<cols; c++){
        board[r][c] = 0;
      }
    }
    //initializing moveList
  }
  private void clear(){
    for (int r = 0; r<board.length; r++){
      for (int c = 0; c<board[0].length; c++){
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
          for (int k = 0; k<board.length; k++){ //do i even need clear if my toString does this? yea bc under the hood it's still messed up
            for (int l = 0; l<board[0].length; l++){ //^this case should never happen then, if j the first square is zero
              toReturn += "_ "; //that means all board is 0 so i don't need to check so much. Also is this n^4? idk it's
            }                   //not doing a n^2 loop for every board[r][c], it only does it once it's like a n^2/2 then n^2
            toReturn += "\n";
          }
          return toReturn;
        }
      }
    }
    for (int rN = 0; rN<board.length; rN++){
      for (int cN = 0; cN<board[0].length; cN++){
        for (int i = (board.length*board[0].length + "").length() - (board[rN][cN]+"").length(); i>0; i--){
          toReturn+=" "; //^can right-adjust every board
        }
        toReturn+=(board[rN][cN] + " ");
      }
      toReturn += "\n";
    }
    return toReturn;
  }
  private String opToString(){
    String toReturn = "";
    for (int rN = 0; rN<opBoard.length; rN++){
      for (int cN = 0; cN<opBoard[0].length; cN++){
        for (int i = (opBoard.length*opBoard[0].length + "").length() - (opBoard[rN][cN]+"").length(); i>0; i--){
          toReturn+=" ";
        }
        toReturn+=(opBoard[rN][cN] + " ");
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
//creates a new list of optimized moves every time you move the piece
  private ArrayList<move> opMoves(int r, int c){
    ArrayList<Integer> vals = new ArrayList<Integer>(); //this will have the sorted values from opBoard
    ArrayList<move> toReturn = new ArrayList<move>(); //this will have the moves sorted by which yields the lowest val from opBoard
    for (int i = 0; i<8; i++){ //check every possible move
      int newR  = r + moveList.get(i).rChange;
      int newC = c + moveList.get(i).cChange;
      try{ //this catches out of bounds moves which shouldn't be added to our list
        Integer value = opBoard[newR][newC]; //make it an Integer
        if (value >= 0){ //ignore values that are -1, bc they already have been visited by the knight
          vals.add(value); //add this value
          Collections.sort(vals); //sort this list (will be sorted max 8 times)
          int index = vals.indexOf(value); //the index of the value that has just been added
          toReturn.add(index, moveList.get(i)); //is the index in toReturn that we want to add the current move to.
        }
      }catch (IndexOutOfBoundsException e){
        //don't do anything
      }
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
    if (solveH(startingRow, startingCol, 1)){
      //clear();
      return true;
    }else{
      clear();
      return false;
    }
    //return solveH(startingRow, startingCol, 1);
  }
  private boolean solveH(int row ,int col, int moveNumber){
    //System.out.println("In new call of solveH(" + row + ", " + col + ", " + moveNumber + ")");
    board[row][col] = moveNumber;
    int oldOpVal = opBoard[row][col]; //remember this in case you have to undo a move
    opBoard[row][col] = -1; //modify opBoard
    for(int k = 0; k<8; k++){ //also lower values of everything around it
      try{
        int newR = row + moveList.get(k).rChange;
        int newC = col + moveList.get(k).cChange;
        //MADE A CHANGE HERE
        if (opBoard[newR][newC] >= 0){
          opBoard[newR][newC] = opBoard[newR][newC]-1;
        }
        //opBoard[newR][newC] = opBoard[newR][newC]-1; //the reason the final board is so negative is because it -1s even spots that already have a knight
      }catch (IndexOutOfBoundsException e){
        //just don't modify what doesn't exist
      }
    }
    //System.out.println(toStringDebug());
    if (moveNumber == board.length * board[0].length ){ //a 1x1 board doesn't count
      return true;
    }
    ArrayList<move> opMoves = opMoves(row, col);
    for (int i = 0; i<opMoves.size(); i++){ //the length of opMoves will be 0 if no possible outgoing moves, leading to returning false from this branch
      int newR = row + opMoves.get(i).rChange; //based on the changes in the list, make new coords for next call
      int newC = col + opMoves.get(i).cChange;
      //idt you have to check this bc opMoves() doesn't add non-valid moves, but just in case:
      if (newR>= 0 && newR<board.length && newC>=0 && newC < board[0].length && board[newR][newC] == 0){ //if it's a valid spot
        //System.out.println("Now going to call solveH(" + newR + ", " + newC + ", " + (moveNumber+1) + ")");
        if(solveH(newR, newC, moveNumber+1)){ //keep checking if everything ahead of it is valid. if it is
          return true; //return true
        } //can't have it just return solveH, bc minute it gets into a corner w no possible moves false will be returned
        //rather than all other possibilities searched for a true
      }
    }
    //System.out.println("Nothing worked, so removing: ");
    board[row][col] = 0;
//have to undo the opBoard too
    opBoard[row][col] = oldOpVal;
    for(int j = 0; j<8; j++){ //also lower values of everything around it
      try{
        int newR = row + moveList.get(j).rChange;
        int newC = col + moveList.get(j).cChange;
        opBoard[newR][newC] = opBoard[newR][newC]+1;
      }catch (IndexOutOfBoundsException e){
        //just don't modify what doesn't exist
      }
    }
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
  */
  public int countSolutions(int startingRow, int startingCol){
    if (startingRow < 0 || startingRow >= board.length || startingCol < 0 || startingCol > board[0].length){
      throw new IllegalArgumentException("you can't solve from somewhere that doesn't exist!");
    }
    for (int r = 0; r<board.length; r++){
      for (int c =0; c<board[0].length; c++){
        if (board[r][c] != 0){
          throw new IllegalStateException("board contains non-zero values, can't countSolutions");
        }
      }
    }
    int toReturn = 0;
    toReturn = countH(startingRow, startingCol, 1);
    clear();
    return toReturn;
  }
  private int countH(int curR, int curC, int moveNumber){
      int toReturn = 0;
      if (moveNumber == board.length * board[0].length){
        //System.out.println("found a solution! returning 1 and it should be added to toReturn");
        return 1;
      }
      for (int i = 0; i<8; i++){
        board[curR][curC] = moveNumber;
        int newR = curR + moveList.get(i).rChange; //based on the changes in the list, make new coords for next call
        int newC = curC + moveList.get(i).cChange;
        if (newR>= 0 && newR<board.length && newC>=0 && newC < board[0].length && board[newR][newC] == 0){
          //System.out.println("Now going to call solveH(" + newR + ", " + newC + ", " + (moveNumber+1) + ")");
          toReturn += countH(newR, newC, (moveNumber+1));
          //can't have it just return solveH, bc minute it gets into a corner w no possible moves false will be returned
          //rather than all other possibilities searched for a true
        }
      }
      board[curR][curC] = 0; //if countH just returns 0, erase what u did so you can go over it
      //System.out.println("now about to return toReturn");
      return toReturn;
  }
}
