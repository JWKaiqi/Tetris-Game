package tetris;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JButton;
/**
 * Created by Jennifer on 2016-09-19.
 */


public class TetrisMain {
    public static int Width = 11 * 25, Height = 26 * 25;

    public static void main(String[] args) {
       // System.out.println("Hello, Tetris!");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ProgramArgs a = ProgramArgs.parseArgs(args);
                    Tetris tetris = new Tetris(a.getFPS(), a.getSpeed(), a.getSequence());

                    JFrame frame = new JFrame("Tetris");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(12 * 25 + 250, 25 * 25 + 44);

                    frame.addKeyListener(new KeyAdapter(){
                         public void keyPressed(KeyEvent e){
                             int key = e.getKeyCode();
                             if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_NUMPAD4){
                                 tetris.move(1); //move left
                             }
                             if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_NUMPAD6){
                                 tetris.move(2); //move right
                             }
                             if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_NUMPAD8){
                                 tetris.drop();
                             }
                             if(key == KeyEvent.VK_UP || key == KeyEvent.VK_X ||
                                     key == KeyEvent.VK_NUMPAD1 || key == KeyEvent.VK_NUMPAD5 ||
                                     key == KeyEvent.VK_NUMPAD9){
                                 tetris.rotate(1);//rotate right
                             }
                             if(key == KeyEvent.VK_CONTROL || key == KeyEvent.VK_Z ||
                                     key == KeyEvent.VK_NUMPAD3 || key == KeyEvent.VK_NUMPAD7){
                                 tetris.rotate(-1); //rotate left
                             }
                             if(key == KeyEvent.VK_P) {
                                 tetris.Pause();
                             }
                         }
                     }
                    );
                    frame.addMouseListener(new MouseAdapter(){
                       @Override
                       public void mouseClicked(MouseEvent e) {
                       }

                       @Override
                       public void mousePressed(MouseEvent e) {

                           tetris.Mouse_Pressed();
                       }

                       @Override
                       public void mouseReleased(MouseEvent e) {
                       }

                    }
                    );

                    frame.addMouseMotionListener(new MouseMotionAdapter() {
                         @Override
                         public void mouseMoved(MouseEvent e) {
                             tetris.MouseMoved(e);
                         }
                     }
                    );
                    frame.addMouseWheelListener(new MouseWheelListener(){
                        public void mouseWheelMoved(MouseWheelEvent e){
                            if (e.getWheelRotation() < 0) {
                                tetris.rotate(1);
                            } else {
                                tetris.rotate(-1);
                            }
                        }

                    });
                    frame.addComponentListener(new ComponentAdapter(){

                        public void componentResized(ComponentEvent e) {
                            Component c = e.getComponent();
                            tetris.PanelResize(c.getSize().height, c.getSize().width);
                        }
                    });
                    frame.setVisible(true);

                    frame.setResizable(true);
                    frame.add(tetris);
                    tetris.init();

                } catch (IllegalArgumentException e) {
                    System.out.println(e);
                }
            }
        });
    }
}