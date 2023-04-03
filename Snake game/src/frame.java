import javax.swing.*;
public class frame extends JFrame{

    frame(){
        this.add(new panel());
        this.pack();
        this.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Snake Game");
        this.setVisible(true);
    }
}
