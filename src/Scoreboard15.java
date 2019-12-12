import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Scoreboard15 extends JPanel
{
   private JLabel labelX, labelC, labelO;
   private int winX, cat, winO;
   public String current;
   public Scoreboard15()
   {
      winX = cat = winO = 0;
      current = "X";

      setLayout(new GridLayout(1, 9));
      setBackground(Color.black);

      add(new JLabel(""));
      labelX = new JLabel("X: 0", SwingConstants.CENTER);
      labelX.setOpaque(true);
      labelX.setBackground(Color.yellow);
      labelX.setForeground(Color.black);
      add(labelX);
      add(new JLabel(""));

      add(new JLabel(""));
      labelC = new JLabel("Cat: 0", SwingConstants.CENTER);
      labelC.setOpaque(true);
      labelC.setBackground(Color.black);
      labelC.setForeground(Color.white);
      add(labelC);
      add(new JLabel(""));

      add(new JLabel(""));
      labelO = new JLabel("O: 0", SwingConstants.CENTER);
      labelO.setOpaque(true);
      labelO.setBackground(Color.black);
      labelO.setForeground(Color.white);
      add(labelO);
      add(new JLabel(""));
   }
   public void toggle()
   {
      labelC.setBackground(Color.black);
      labelC.setForeground(Color.white);
      if(current.equals("X")){
         current = "O";
         labelX.setBackground(Color.yellow);
         labelX.setForeground(Color.black);
         labelO.setBackground(Color.black);
         labelO.setForeground(Color.white);
      }
      /**/else
      {
         current = "X";
         labelX.setBackground(Color.yellow);
         labelX.setForeground(Color.black);
         labelO.setBackground(Color.black);
         labelO.setForeground(Color.white);
      }/**/
    /*{
      current = "X";
      labelX.setBackground(Color.yellow);
      labelX.setForeground(Color.black);
      labelO.setBackground(Color.black);
      labelO.setForeground(Color.white);
    }*/
   }
   public  void winner()
   {
      if(current.equals("X")){
         winX++;
         labelX.setText("X: " + (winX-1));
      }else{
         winO++;
         labelO.setText("O: " + (winO-1));
      }
   }
   public void tie()
   {
      cat++;
      labelC.setText("Cat: " + cat);
      labelC.setBackground(Color.yellow);
      labelC.setForeground(Color.black);
      labelX.setBackground(Color.black);
      labelX.setForeground(Color.white);
      labelO.setBackground(Color.black);
      labelO.setForeground(Color.white);
   }
}