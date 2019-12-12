import javax.swing.*;

public  class Driver15{
    public static void main(String[] args){
        JFrame frame = new JFrame("TicTacToe");
        String d = JOptionPane.showInputDialog("Choose difficulty: 0(easy), 1(medium), 2(impossible");
        if(d == null){
            return;
        }
        while(!("0".equals(d) || "1".equals(d) || "2".equals(d)) ){
            JOptionPane.showMessageDialog(null,"Please enter a valid choice");
            d = JOptionPane.showInputDialog("Choose difficulty: 0(easy), 1(medium), 2(impossible");
            if(d == null){
                return;
            }
        }
        int diff = Integer.parseInt(d);
        frame.setSize(320,320);
        frame.setLocation(200,100);
        frame.getContentPane().add(new Panel15(diff));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}