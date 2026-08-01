import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int width = 600;
    int height = 650;

    JFrame frame = new JFrame("Ticco_Tacco_Toe desu!!!");
    JLabel txtLab = new JLabel();
    JPanel txtPan = new JPanel();
    JPanel boaPan = new JPanel();

    JButton[][] buttons = new JButton[3][3];
    String px = "X";
    String po = "O";
    String current = po;

    boolean over = false;
    int moves = 0;

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        txtLab.setBackground(Color.DARK_GRAY);
        txtLab.setForeground(Color.YELLOW);
        txtLab.setFont(new Font("Monospaced", Font.BOLD, 44));
        txtLab.setHorizontalAlignment(JLabel.CENTER);
        txtLab.setText("Ticco Tacco Toe >w<");
        txtLab.setOpaque(true);

        txtPan.setLayout(new BorderLayout());
        txtPan.add(txtLab);
        frame.add(txtPan, BorderLayout.NORTH);

        boaPan.setLayout(new GridLayout(3, 3));
        boaPan.setBackground(Color.DARK_GRAY);
        frame.add(boaPan);

        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                JButton tile = new JButton();
                buttons[r][c] = tile;
                boaPan.add(tile);

                tile.setBackground(Color.black);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Monospaced", Font.BOLD, 100));
                tile.setFocusable(false);
                //tile.setText(current);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev){
                        if (over) return;
                        JButton t = (JButton) ev.getSource();
                        if (tile.getText() == ""){
                            tile.setText(current);
                            moves++;
                            checkWinner();
                            if (!over){
                                current = current == px ? po : px;
                                txtLab.setText("Now goes " + current + "!");
                            }
                            
                        }
                    }
                });
            }
        }
    }

    void checkWinner(){
        for (int r = 0; r < 3; r++){
            if (buttons[r][0].getText() == "") continue;

            if (buttons[r][0].getText() == buttons[r][1].getText() && 
                buttons[r][1].getText() == buttons[r][2].getText()){
                for (int c = 0; c < 3; c++){
                    setWinner(buttons[r][c]);
                }
                over = true;
                return;
            }
        }

        for (int c = 0; c < 3; c++){
            if (buttons[0][c].getText() == "") continue;

            if (buttons[0][c].getText() == buttons[1][c].getText() && 
                buttons[1][c].getText() == buttons[2][c].getText()){
                for (int r = 0; r < 3; r++){
                    setWinner(buttons[r][c]);
                }
                over = true;
                return;
            }
        }

        if (buttons[0][0].getText() != "" && 
            buttons[0][0].getText() == buttons[1][1].getText() && 
            buttons[1][1].getText() == buttons[2][2].getText()){
            for (int i = 0; i < 3; i++){
                setWinner(buttons[i][i]);
            }
            over = true;
            return;
        }

        if (buttons[0][2].getText() != "" && 
            buttons[0][2].getText() == buttons[1][1].getText() && 
            buttons[1][1].getText() == buttons[2][0].getText()){
            for (int i = 0; i < 3; i++){
                setWinner(buttons[i][2-i]);
            }
            over = true;
            return;
        }

        if (moves == 9){
            for (int r = 0; r < 3; r++){
                for (int c = 0; c < 3; c++){
                    setTie(buttons[r][c]);
                }
            }
            over = true;
        }
    }

    void setWinner(JButton tile){
        tile.setForeground(Color.GREEN);
        tile.setBackground(Color.GRAY);
        if (current.equals("O")){
            txtLab.setText(current + " wins! Yay!");
        }
        else{
            txtLab.setText(current + " wins! Yay!");
        }
    }

    void setTie(JButton tile){
        tile.setBackground(Color.ORANGE);
        tile.setForeground(Color.BLACK);
        txtLab.setText("It's a tie! :(");
    }
}
