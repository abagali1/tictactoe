import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// X -> player -> 1
// O -> computer - > 0

class Move{
   int x, y;
   Move(int x, int y){
      this.x = x;
      this.y = y;
   }
}

public class Gameboard15 extends JPanel
{
   private int[][] game; // logical board
   private JButton[][] board; // display board

   private String gamepiece; // current piece (X or O)
   private Color color; // current color (X=green, O=blue)
   private int count;
   private int maxDepth;
   // Piece specific constants
   private final Font PIECE_FONT = new Font("Serif", 1, 40);

   private final int PLAYER = 1;
   private final String PLAYER_PIECE = "X";
   private final Color PLAYER_COLOR = Color.green;

   private final int COMPUTER = 0;
   private final String COMPUTER_PIECE = "O";
   private final Color COMPUTER_COLOR = Color.blue;

   private final int EMPTY = 2;
   private final String EMPTY_PIECE = "-";
   private final Color EMPTY_COLOR = Color.WHITE;
   private final Font EMPTY_FONT = new Font("Monospaced", Font.PLAIN, 20);

   Gameboard15(ActionListener PanelListener, int difficulty)
   {
      this.gamepiece = this.PLAYER_PIECE;
      this.color = this.PLAYER_COLOR;
      this.count = 0;
      if(difficulty == 0){
         this.maxDepth = 1;
      } else{
         this.maxDepth = Integer.MAX_VALUE;
      }

      setLayout(new GridLayout(3, 3));
      setBackground(Color.black);

      this.board = new JButton[3][3];
      this.game = new int[3][3];
      for(int x = 0; x < 3; x++)
         for(int y = 0; y < 3; y++)
         {
            this.board[x][y] = new JButton("-");
            this.board[x][y].setFont(this.EMPTY_FONT);
            this.board[x][y].setBackground(this.EMPTY_COLOR);
            this.board[x][y].setFocusPainted(false);
            this.board[x][y].addActionListener(PanelListener); // <---Add this one first!!!!!
            this.board[x][y].addActionListener(new Listener(x, y));
            add(this.board[x][y]);
            this.game[x][y] = this.EMPTY;
         }
   }
   public boolean filled()
   {
      return this.count==9;
   }
   boolean winner()
   {
      return(((game[0][0]==1)&&(game[0][1]==1)&&(game[0][2]==1)) || ((game[1][0]==1)&&(game[1][1]==1)&&(game[1][2]==1)) || ((game[2][0]==1)&&(game[2][1]==1)&&(game[2][2]==1)) || ((game[0][0]==1)&&(game[1][0]==1)&&(game[2][0]==1)) || ((game[0][1]==1)&&(game[1][1]==1)&&(game[2][1]==1)) || ((game[0][2]==1)&&(game[1][2]==1)&&(game[2][2]==1)) || ((game[0][0]==1)&&(game[1][1]==1)&&(game[2][2]==1)) || ((game[0][2]==1)&&(game[1][1]==1)&&(game[2][0]==1))/*O*/|| ((game[0][0]==0)&&(game[0][1]==0)&&(game[0][2]==0)) || ((game[1][0]==0)&&(game[1][1]==0)&&(game[1][2]==0)) || ((game[2][0]==0)&&(game[2][1]==0)&&(game[2][2]==0)) || ((game[0][0]==0)&&(game[1][0]==0)&&(game[2][0]==0)) || ((game[0][1]==0)&&(game[1][1]==0)&&(game[2][1]==0)) || ((game[0][2]==0)&&(game[1][2]==0)&&(game[2][2]==0)) || ((game[0][0]==0)&&(game[1][1]==0)&&(game[2][2]==0)) || ((game[0][2]==0)&&(game[1][1]==0)&&(game[2][0]==0)));
   }
   int getCount(){
      return this.count;
   }
   int[][] getGame(){
      return this.game;
   }
   void freeze()
   {
      for(JButton[] x: this.board){
         for(JButton y: x){
            y.setEnabled(false);
         }
      }
   }
   void reset()
   {
      for(int x=0;x<=this.board.length-1;x++){
         for(int y=0;y<=this.board[0].length-1;y++){
            this.board[x][y].setText(this.EMPTY_PIECE);
            this.board[x][y].setFont(this.EMPTY_FONT);
            this.board[x][y].setBackground(this.EMPTY_COLOR);
            this.board[x][y].setFocusPainted(false);
            this.board[x][y].setEnabled(true);
            this.game[x][y]=this.EMPTY;
         }
      }
      this.count =0;
      this.gamepiece = this.PLAYER_PIECE;
      this.color = this.PLAYER_COLOR;
   }
   boolean noMovesLeft(int[][] board){
      for(int[] i: board){
         for(int j: i){
            if(j == this.EMPTY)
               return false;
         }
      }
      return true;
   }
   int gameState(int[][] b){
      for (int row = 0; row < 3; row++) {
         if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
            if (b[row][0] == this.COMPUTER)
               return 10;
            else if (b[row][0] == this.PLAYER)
               return -10;
         }
      }
      for (int col = 0; col < 3; col++) {
         if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
            if (b[0][col] == this.COMPUTER)
               return 10;
            else if (b[0][col] == this.PLAYER)
               return -10;
         }
      }
      if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
         if (b[0][0] == this.COMPUTER)
            return 10;
         else if (b[0][0] == this.PLAYER)
            return -10;
      }
      if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
         if (b[0][2] == this.COMPUTER)
            return 10;
         else if (b[0][2] == this.PLAYER)
            return -10;
      }
      return 0;
   }
   private int minmax(int[][] board, int depth, boolean isMax) {
      int state = this.gameState(board);
      if(depth >= this.maxDepth)
         return 0;
      if(state == 10 || state == -10)
         return state + depth*(state / 10);
      if(this.noMovesLeft(board))
         return 0;

      if(isMax){
         int max = -11;
         for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
               if(board[i][j] == this.EMPTY){
                  board[i][j] = this.COMPUTER;
                  max = Math.max(max, this.minmax(board, depth+1, false));
                  board[i][j] = this.EMPTY;
               }
            }
         }
         return max;
      }else{
         int min = 11;
         for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
               if(board[i][j] == this.EMPTY){
                  board[i][j] = this.PLAYER;
                  min = Math.min(min, this.minmax(board, depth+1, true));
                  board[i][j] = this.EMPTY;
               }
            }
         }
         return min;
      }
   }

   private Move findBestMove(){
      int max = -1000;
      Move best = new Move(-1,-1);
      for(int i=0;i<3;i++){
         for(int j=0;j<3;j++){
            if(this.game[i][j] == this.EMPTY){
               this.game[i][j] = this.COMPUTER;
               int move = this.minmax(game, 0, false);
               this.game[i][j] = this.EMPTY;
               if(move > max){
                  best.x = i;
                  best.y = j;
                  max = move;
               }
            }
         }
      }
      return best;
   }
   private class Listener implements ActionListener
   {
      private int myX, myY;
      Listener(int x, int y)
      {
         myX = x;
         myY = y;
      }
      public void actionPerformed(ActionEvent e)
      {
         board[this.myX][this.myY].setText(gamepiece);
         board[this.myX][this.myY].setBackground(color);
         board[this.myX][this.myY].setFont(PIECE_FONT);
         board[this.myX][this.myY].setEnabled(false);
         count ++;
         if (PLAYER_PIECE.equals(gamepiece)) // Player ('X') has moved
         {
            gamepiece = COMPUTER_PIECE;
            color = COMPUTER_COLOR;
            game[myX][myY] = PLAYER;
            if(!winner()){
               Move m = findBestMove();
               if(m.x != -1 && m.y != -1)
                  board[m.x][m.y].doClick();
            }
         }else{ // Computer ('O') has moved
            gamepiece = PLAYER_PIECE;
            color = PLAYER_COLOR;
            game[myX][myY] = COMPUTER;
         }
      }
   }
}