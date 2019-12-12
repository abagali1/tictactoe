import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Panel15 extends JPanel
{
    private Scoreboard15 scoreboard;
    private Gameboard15 gameboard;
    private JButton reset;
    private JLabel label;
    Panel15(int difficulty)
    {
        setLayout(new BorderLayout());

        scoreboard = new Scoreboard15();
        add(scoreboard, BorderLayout.NORTH);

        gameboard = new Gameboard15(new Callback(), difficulty);
        add(gameboard, BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.setLayout(new FlowLayout());
        south.setBackground(Color.black);
        add(south, BorderLayout.SOUTH);

        reset = new JButton("Reset");
        reset.addActionListener(new Listener());
        reset.setFocusPainted(false);
        reset.setEnabled(false);
        south.add(reset);

        label = new JLabel("");
        label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
        label.setForeground(Color.yellow);
        south.add(label);
    }
    private class Callback implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if((gameboard.gameState(gameboard.getGame())==-10)&&(gameboard.getCount()>=5))
            {
                scoreboard.current = "X";
                scoreboard.winner();
                gameboard.freeze();
                reset.setEnabled(true);
                label.setText("You Win!");
            }else if(gameboard.gameState(gameboard.getGame())==10&&(gameboard.getCount()>=5)&&gameboard.winner()){
                scoreboard.current = "O";
                scoreboard.winner();
                gameboard.freeze();
                reset.setEnabled(true);
                label.setText("You Lose!");
                //label.setText("Winner!");
            }else if(gameboard.noMovesLeft(gameboard.getGame()))
            {
                scoreboard.tie();
                reset.setEnabled(true);
                label.setText("Cat!");
            }
            else
            {
                scoreboard.toggle();
            }
        }
    }
    private class Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            reset.setEnabled(false);
            gameboard.reset();
            label.setText("");
            scoreboard.toggle();
        }
    }

}