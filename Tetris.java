package tetris;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.Random;

public class Tetris extends JPanel{
    private int BoardWidth = 12;
    private int BoardHeight = 26;
    private int OldScore;
    private int score;
    private int Level;
    private int DelayTime;

    private int FPS = 30;
    private double speed = 7;

    private String sequence;
    private String sequence_copy;
    private int x;
    private int y;
    private int CellDim;
    private int Text_x;
    private int Text_y;
    private int FontSize;
    private int OldWidth;
    JPanel HelpPanel = new JPanel();
    JButton NewGame = new JButton("New Game");
    JButton Help = new JButton("Help");
    JButton QuitGame = new JButton("Quit Game");
    private Timer timer;

    private boolean apply_down;
    private boolean pause;
    private boolean gameover;
    private boolean selected;
    private boolean HelpOpen;
    private int turns;

    private Color[][] Board;

    private int CurrentBlock;
    private int NextBlock;


    private Color Block_Colour[] = {
            new Color(0xf23d3a), new Color(0xf2993a), new Color(0xf2ec3a),
            new Color(0x3df23a), new Color(0x4bdefc), new Color(0x5c71f9),
            new Color(0xa36fed),
    };

    private Point[][][] Blocks= {
            // I-Piece
            {
                    { new Point(1, 0), new Point(1, 1),
                      new Point(1, 2), new Point(1, 3) },
                    { new Point(0, 1), new Point(1, 1),
                      new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1),
                      new Point(1, 2), new Point(1, 3) },
                    { new Point(0, 1), new Point(1, 1),
                      new Point(2, 1), new Point(3, 1) }

            },

            // L-Piece
            {
                    { new Point(1, 0), new Point(1, 1),
                      new Point(1, 2), new Point(2, 2) },
                    { new Point(0, 1), new Point(1, 1),
                      new Point(2, 1), new Point(0, 2) },
                    { new Point(1, 0), new Point(1, 1),
                      new Point(1, 2), new Point(0, 0) },
                    { new Point(0, 1), new Point(1, 1),
                      new Point(2, 1), new Point(2, 0) }

            },

            // J-Piece
            {
                    { new Point(1, 0), new Point(1, 1),
                      new Point(1, 2), new Point(0, 2) },
                    { new Point(0, 1), new Point(1, 1),
                      new Point(2, 1), new Point(0, 0) },
                    { new Point(1, 0), new Point(1, 1),
                      new Point(1, 2), new Point(2, 0) },
                    { new Point(0, 1), new Point(1, 1),
                      new Point(2, 1), new Point(2, 2) }

            },

            // T-Piece
            {
                    { new Point(0, 1), new Point(1, 1),
                      new Point(2, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(0, 1),
                      new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(0, 1),
                      new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(1, 1),
                      new Point(2, 1), new Point(1, 2) }
            },

            // O-Piece
            {
                    { new Point(0, 0), new Point(0, 1),
                      new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1),
                      new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1),
                      new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1),
                      new Point(1, 0), new Point(1, 1) }
            },

            // S-Piece
            {
                    { new Point(1, 0), new Point(2, 0),
                      new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1),
                      new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(2, 0),
                      new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1),
                      new Point(1, 1), new Point(1, 2) }
            },
            // Z-Piece
            {
                    { new Point(0, 0), new Point(1, 0),
                      new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1),
                      new Point(1, 1), new Point(0, 2) },
                    { new Point(0, 0), new Point(1, 0),
                      new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1),
                      new Point(1, 1), new Point(0, 2) }
            }
    };

    public Tetris(int fps, double speed, String sequence) {
        setLayout(null);
        this.FPS = fps;
        this.speed = speed;
        this.sequence = sequence;
        this.sequence_copy = sequence;
        apply_down = true;
        CellDim = 25;
        NewGame.setBounds(370,200,100,50);
        NewGame.setFocusable(false);
        NewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bt_NewGame();
                repaint();
            }
        });

        Help.setBounds(370,300,100,50);
        Help.setFocusable(false);
        Help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!HelpOpen){
                    HelpOpen = true;
                }
                else {
                    HelpOpen = false;

                }
                bt_Help();
                repaint();
            }
        });

        //JButton QuitGame = new JButton("Quit Game");
        QuitGame.setBounds(370,400,100,50);
        QuitGame.setFocusable(false);
        QuitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                repaint();
            }
        });
        HelpPanelInit();
        add(NewGame);
        add(Help);
        add(QuitGame);
        add(HelpPanel);
        HelpOpen = false;
        pause = false;
        gameover = false;
        selected = false;
        CellDim = 25;
        FontSize = 40;
        Text_x = 350;
        Text_y = 100;
        OldWidth = 25 * 25 + 44;
        DelayTime = 500;
        Timer timer = new Timer(DelayTime, new TimerListener());
        timer.start();
    }

    public void HelpPanelInit(){
        HelpPanel.setVisible(false);
        HelpPanel.setBorder(BorderFactory.createLineBorder(new Color(0xc8bad1)));
        HelpPanel.setBackground(new Color(0xe5daed));
        HelpPanel.setLayout(null);
        JLabel Title = new JLabel();
        Title.setText("Help Menu");
        Title.setBounds(CellDim*4, 50, 200, 50);
        Title.setFont(new Font("Verdana",1,20));
        Title.setForeground(new Color(0x6c6272));

        JLabel Ktext = new JLabel();
        Ktext.setText("<html><p>" +
                "Move Lefe:&nbsp&nbsp&nbsp&nbsp LEFT Arrow;&nbsp&nbsp&nbsp Numpad 4<br/>" +
                "Move Right:&nbsp&nbsp&nbsp RIGHT Arrow;&nbsp Numpad 6<br/>" +
                "Drop:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Space Bar;" +
                "&nbsp&nbsp&nbsp&nbsp Numpad 8 <br/>" +
                "Rotate Right:&nbsp UP Arrow, X;&nbsp&nbsp Numpad 1, 5, 9<br/>" +
                "Rotate Left:&nbsp&nbsp&nbsp Control, Z;&nbsp&nbsp&nbsp&nbsp Numpad 3, 7<br/>" +
                "Pause:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp P</p></html>");
        int Kpos_x = CellDim/3 + CellDim;
        Ktext.setBounds(Kpos_x, 100, 500, 150);
        Ktext.setFont(new Font("Verdana",0,10));

        JLabel Mtext = new JLabel();
        Mtext.setText("<html><p>" +
                "MousePress (on unselected falling piece): <br/>&nbsp&nbsp Select " +
                "the piece for further manipulation.<br/><br/>" +
                "MousePress (on selected falling piece): <br/>&nbsp&nbsp Drop " +
                "the selected piece into position.<br/><br/>" +
                "MouseMotion:<br/>&nbsp&nbsp Selected piece follows the mouse<br/>" +
                "&nbsp&nbsp from side-to-side.<br/><br/>" +
                "MouseWheel: <br/>&nbsp&nbsp Rotate the selected piece.</p></html>");
        int Mpos_x = CellDim/2 + CellDim;
        Mtext.setBounds(Mpos_x, 200, 300, 300);
        Mtext.setFont(new Font("Verdana",0,10));

        Ktext.setForeground(new Color(0x6c6272));
        Mtext.setForeground(new Color(0x6c6272));

        JLabel msg = new JLabel();
        msg.setText("Click help button to resume game.");
        msg.setBounds(CellDim*2, 350, 300, 300);
        msg.setFont(new Font("Verdana",1,10));
        msg.setForeground(new Color(0x6c6272));

        HelpPanel.add(Title);
        HelpPanel.add(Ktext);
        HelpPanel.add(Mtext);
        HelpPanel.add(msg);

    }

    public void init() {
        Board = new Color[BoardWidth][BoardHeight];
        x = 3;
        y = 0;
        turns = 0;
        score = 0;
        OldScore = 0;
        Level = 0;

        for (int i = 0; i < BoardWidth; i++) {
            for (int j = 0; j < BoardHeight-1; j++) {
                if (i == 0 || i == 11 || j == 24) {
                    Board[i][j] = Color.gray;
                } else {
                    Board[i][j] = Color.BLACK;
                }
            }
        }
        FindAndUpdateSeq();
    }


    public int MapBlockID(char type) {
        int BlockID = 0;
        if (type == 'I') {
            BlockID = 0;
        }
        if (type == 'L') {
            BlockID = 1;
        }
        if (type == 'J') {
            BlockID = 2;
        }
        if (type == 'T') {
            BlockID = 3;
        }
        if (type == 'O') {
            BlockID = 4;
        }
        if (type == 'S') {
            BlockID = 5;
        }
        if (type == 'Z') {

            BlockID = 6;
        }
        return BlockID;
    }

    public void FindAndUpdateSeq() {

        String type = sequence.substring(0, 1);
        char[] seqChars = type.toCharArray();
        CurrentBlock = MapBlockID(seqChars[0]);
        sequence = sequence.substring(1);
        sequence += type;
        //CurrentBlock = 4;
    }


    public void MakeNewBlock() {
        x = 3;
        y = 0;
        turns = 0;
        CheckGameOver();
        if(gameover == true){
            sequence = sequence_copy;
            init();
            gameover = false;
        }
        else {
            apply_down = true;
            selected = false;
            turns = 0;
            FindAndUpdateSeq();
         }
        repaint();
    }

    public void move(int dir) {
        if(!pause) {
            if (dir == 1) { //move left
                if (CheckActionIsOkay(x - 1, y, turns)) {
                    x -= 1;
                }
                repaint();
            }

            if (dir == 2) { //move right
                if (CheckActionIsOkay(x + 1, y, turns)) {
                    x += 1;
                }
                repaint();

            }
        }
    }

    public void rotate(int dir) {
        if(!pause) {
            int tempturn = (turns + dir) % 4;
            if(tempturn < 0){
                tempturn = 3;
            }
            if (CheckActionIsOkay(x, y, tempturn)) {
                turns = tempturn;
            }
            repaint();
        }
    }

    public void down() {
        if (!pause) {
            if (CheckActionIsOkay(x, y + 1, turns)) {
                y += 1;
            } else {
                apply_down = false;
                AddToBottom(); //Note: AddToBottom should reset apply_down to true
            }
            repaint();
        }
    }

    public void drop() {
        if(!pause) {
            while (CheckActionIsOkay(x, y + 1, turns)) {
                y += 1;
            }
            apply_down = false;
            AddToBottom(); //Note: AddToBottom should reset apply_down to true
            repaint();
        }
    }

    public boolean CheckActionIsOkay(int x, int y, int turns) {
        for (Point p : Blocks[CurrentBlock][turns]) {
            if (Board[p.x + x][p.y + y] != Color.BLACK) {
                return false;
            }
        }
        return true;
    }

    public void AddToBottom() {
        int k = 0;
        for (Point p : Blocks[CurrentBlock][turns]) {
            Board[x + p.x][y + p.y] = Block_Colour[CurrentBlock];
        }
        CheckFullRow();
        MakeNewBlock();
    }

    public void ClearRow(int row) {
        for (int r = row - 1; r > 0; r--) {
            for (int c = 0; c < BoardWidth-1; c++) {
                Board[c][r + 1] = Board[c][r];
            }
        }
        repaint();
    }

    public void CheckFullRow() {
        boolean full;
        for (int row = 23; row > 0; row--) {
            full = true;
            for (int col = 0; col < 11; col++) {
                if (Board[col][row] == Color.BLACK) {
                    full = false;
                    break;
                }
            }
            if (full) {
                ClearRow(row); //Add Score if needed
                row++;
                score += 10;
            }
        }
    }

    public void CheckGameOver() {
       if (CheckActionIsOkay(x, y, turns) == true) {
            gameover = false;
        } else {
            gameover = true;
        }
    }

    public void Pause() {
        if (pause == false) {
            pause = true;
        } else {
            pause = false; //resume
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint the board
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, BoardWidth * CellDim, 25 * CellDim);
        for (int i = 0; i < BoardWidth; i++) {
            for (int j = 0; j < BoardHeight - 1; j++) {
                g.setColor(Board[i][j]);
                g.fillRect(i * CellDim, j * CellDim, CellDim-1, CellDim-1);
            }
        }
        //paint current block
        for (Point p : Blocks[CurrentBlock][turns]) {
            g.setColor(Block_Colour[CurrentBlock]);
            g.fillRect((p.x + x) * CellDim,(p.y + y) * CellDim,CellDim-1, CellDim-1);
        }
        g.setColor(new Color(0x736577));
        g.setFont(new Font("TimesRoman", Font.PLAIN, FontSize));
        g.drawString("Score: " + score, Text_x, Text_y);
        //g.setFont(new Font("TimesRoman", Font.PLAIN, FontSize - 5));
       // g.drawString("Level: " + Level, Text_x+CellDim/3, Text_y + CellDim*2);
    }

    public void Mouse_Pressed(){
        //Select the piece for further manipulation
        if(selected == false){
            selected = true;
        }
        else {
            selected = false;
            drop();
        }
    }
    public void MouseMoved(MouseEvent e){
        if(selected){
            Point mousePoint = new Point(0,0);
            mousePoint.setLocation(e.getPoint());
            if(mousePoint.x != x) {
                if (mousePoint.x > (x+4)*25)
                {
                    move(2);
                }
                if (mousePoint.x < x*25) {
                    move(1);
                }
            }
        }
    }
    public void bt_NewGame(){
        sequence = sequence_copy;
        init();
        gameover = false;
        repaint();
    }

    public void bt_Help(){
        //HelpPanel.setLocation(250, 250);
        Pause();
        if(HelpOpen) {
            HelpPanel.setSize(CellDim * 12, CellDim * 25);
            HelpPanel.setVisible(true);
        }
        else{
            HelpPanel.setVisible(false);
        }
    }

    public void PanelResize(int height, int width){
        if(pause) {
            CellDim = height / 28;
            if(OldWidth > width) {
                if (12 * CellDim + 30 < Text_x) {
                    Text_x = CellDim * 12 + width/7;
                    NewGame.setBounds(CellDim * 12 + width/6, height/3, width/6, height/13);
                    Help.setBounds(CellDim * 12 + width/6, height/3 + height/6, width/6, height/13);
                    QuitGame.setBounds(CellDim * 12 + width/6, height/3 + height/3, width/6, height/13);
                }
            }
            else {
                Text_x = CellDim * 12 + width/6;
                NewGame.setBounds(CellDim * 12 + width/6, height/3, width/6, height/13);
                Help.setBounds(CellDim * 12 + width/6, height/3 + height/6, width/6, height/13);
                QuitGame.setBounds(CellDim * 12 + width/6, height/3 + height/3, width/6, height/13);
            }
            Text_y = height/6;
            FontSize = width /17;
            OldWidth = width;
            HelpPanel.setSize(CellDim*12, CellDim*25);


            repaint();

        }
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            repaint();
            if (!pause && !gameover) {
                down();
            }
        }
    }
}