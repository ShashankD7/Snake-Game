import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {
    static int width = 1200;
    static int height = 600;
    static int unit = 50;
    Timer timer;   //to specify the time in which new frame is displayed
    Random random;   //deciding food location randomly
    int foodx, foody;
    int Score;
    int length = 3;
    char dir = 'R';
    boolean flag = false;   // for specify game has ended or not (If flag == true; then game is running ans if flag == false; then game is over
    static int delay = 240;   // 160 in millisecond -> higher than 160 snake will move slow and lower than 160 snake will move very fast
    int x = 0;
    int[] xsnake = new int[288];   // to store x and y coordinated of body of snake.
    int[] ysnake = new int[288];

    panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        random = new Random();
        this.addKeyListener(new myKey());
        gamestart();
    }

    public void gamestart() {
        spawnfood();
        flag = true;
        timer = new Timer(delay,this);
        timer.start();
    }

    public void spawnfood() {
        foodx = random.nextInt((int)width/unit) * unit;
        foody = random.nextInt((int)height/unit) * unit;
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        draw(graphic);

    }

    public void draw(Graphics graphic) {
        if (flag == true) {
            graphic.setColor(Color.RED);
            graphic.fillOval(foodx, foody, unit, unit);

            for (int i=0; i<length; i++) {
                if (i==0) {
                    graphic.setColor(Color.orange);
                }
                else {
                    graphic.setColor(Color.green);
                }
                graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
            }

            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics f = getFontMetrics(graphic.getFont());
            graphic.drawString("Score : "+Score, (width-(f.stringWidth("Score : "+Score)))/2, graphic.getFont().getSize());

        }
        else {
            gameover(graphic);
        }
    }

    public void gameover(Graphics graphic) {
        // to display final score
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics f = getFontMetrics(graphic.getFont());
        graphic.drawString("Score : "+Score, (width-(f.stringWidth("Score : "+Score)))/2, graphic.getFont().getSize());

        // to display game over message
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        FontMetrics f2 = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER!", (width-(f2.stringWidth("GAME OVER!")))/2, height/2);

        // to display restart message
        graphic.setColor(Color.WHITE);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        graphic.drawString("Press 'R' to replay the game.", (width-(f.stringWidth("Press R to replay the game.")))/2, height/2 + 150);

        graphic.setColor(Color.WHITE);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 30));
        FontMetrics f3 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press 'X' to close the game.", (width-(f3.stringWidth("Press 'X' to close the game.")))/2 + 10, height/2 + 200);
    }

    public void checkHit() {
        if (xsnake[0] < 0) {
            flag = false;
        }
        else if (xsnake[0] > 1200) {
            flag = false;
        }
        else if (ysnake[0] < 0) {
            flag = false;
        }
        else if (ysnake[0] > 600) {
            flag = false;
        }

        for (int i=length; i>0; i--) {
            if ((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])) {
                flag = false;
            }
        }

        if (!flag) {
            timer.stop();
        }
    }

    public void eat() {
        if ((xsnake[0] == foodx) && (ysnake[0] == foody)) {
            length++;
            Score++;
            if (Score > x+2) {
                x = Score;
                timer = new Timer(delay-20,this);
                timer.start();
            }
            spawnfood();
        }
    }

    public void move() {
        for (int i=length; i>0; i--) {
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }

        switch (dir) {
            case 'R' :
                xsnake[0] = xsnake[0] + unit;
                break;
            case 'L' :
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'U' :
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D' :
                ysnake[0] = ysnake[0] + unit;
                break;
        }
    }

    public class myKey extends KeyAdapter {
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_UP :
                    if (dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN :
                    if (dir != 'U') {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT :
                    if (dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT :
                    if (dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_R :
                    if (!flag) {
                        Score = 0;
                        length = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        gamestart();
                    }
                    break;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (flag) {
            move();
            eat();
            checkHit();
        }
        repaint();

    }
}
