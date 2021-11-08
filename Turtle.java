import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Turtle {

static class MyFrame extends JFrame {
		
		//constant
		static int BLOCK_ROWS = 14;
		static int BLOCK_COLUMNS = 14;
		static int BLOCK_WIDTH = 50;
		static int BLOCK_HEIGHT = 50;
		static int BLOCK_GAP = 3;
		static int CANVAS_WIDTH = 714 + (BLOCK_GAP * BLOCK_COLUMNS) - BLOCK_GAP;
		static int CANVAS_HEIGHT = 827;
		ImageIcon wall = new ImageIcon("wall.png");
		ImageIcon empty = new ImageIcon("empty.png");
		ImageIcon pacman = new ImageIcon("pacman.png");
		JLabel label = new JLabel(wall);
		//variable
		static MyPanel myPanel = null;
		static int score = 0;
		static Timer timer = null;
		static Block[][] blocks = new Block[BLOCK_ROWS][BLOCK_COLUMNS];
		static boolean isGameFinish = false;
		
		static class Block {
			int x = 0;
			int y = 0;
			int width = BLOCK_WIDTH;
			int height = BLOCK_HEIGHT;
			int color = 0; //0 : white 1 : yellow 2 : blue 3 : mazanta 4 : red
			boolean isHidden = false; //after collision, block will be hidden.
		}
		
		static class MyPanel extends JPanel { //VANVAS for Draw!
			public MyPanel() {
				this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
				this.setBackground(Color.BLACK);
			}
			
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D)g;
				
				drawUI( g2d );
			}
			private void drawUI(Graphics2D g2d) {
				//draw Blocks
				for(int i = 0; i < BLOCK_ROWS; i++) {
					for(int j = 0; j < BLOCK_COLUMNS; j++) {
						if(blocks[i][j].isHidden) {
							continue;
						}
						if(blocks[i][j].color == 0 || blocks[i][j].color == 13) {
							g2d.setColor(Color.WHITE);
						}
						else if(blocks[j][i].color == 0 || blocks[j][i].color == 13) {
							g2d.setColor(Color.WHITE);
						}
						else {
							g2d.setColor(Color.BLACK);
						}
						g2d.fillRect(blocks[i][j].x, blocks[i][j].y,
								blocks[i][j].width, blocks[i][j].height);
					}
					
					//draw score
					g2d.setColor(Color.WHITE);
					g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
					g2d.drawString("score : " + score, CANVAS_WIDTH/2 - 50, 20);
					if ( isGameFinish ) {
						g2d.setColor(Color.RED);
						g2d.drawString("Game Finished!", CANVAS_WIDTH/2 - 70, 50);
					}
				}
			}
		}
		
		public MyFrame(String title) {
			super(title);
			this.setVisible(true);
			this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
			this.setLocation(125, 0);
			this.setLayout(new BorderLayout());
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			initData();
			
			myPanel = new MyPanel();
			this.add( "Center", myPanel);
			
			startTimer();
		}
		public void initData() {
			
			for(int i = 0; i < BLOCK_ROWS; i++) {
				for(int j = 0; j < BLOCK_COLUMNS; j++) {
					blocks[i][j] = new Block();
					blocks[i][j].x = BLOCK_WIDTH * j + BLOCK_GAP * j;
					blocks[i][j].y = 50 + BLOCK_HEIGHT * i + BLOCK_GAP * i;
					blocks[i][j].width = BLOCK_WIDTH;
					blocks[i][j].height = BLOCK_HEIGHT;
					blocks[i][j].color = i; //0 : white 1 : yellow 2 : blue 3 : mazanta 4 : red
					blocks[i][j].isHidden = false;
				}
			}
			 
		}
		
		public void startTimer() {
			timer = new Timer(20, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { //Timer Event
					myPanel.repaint(); //Redraw!
					
					isGameFinish();
				}
			});
			timer.start(); //Start Timer!
		}
		public void isGameFinish() {
			//Game Success!
			int count = 0;
			for(int i = 0; i < BLOCK_ROWS; i++) {
				for(int j = 0; j < BLOCK_COLUMNS; j++) {
					Block block = blocks[i][j];
					if( block.isHidden )
						count++;
				}
			}
			if( count == BLOCK_ROWS * BLOCK_COLUMNS) {
				//Game Finished!
				//timer.stop();
				isGameFinish = true;
			}
		}

		public boolean duplRect(Rectangle rect1, Rectangle rect2) {
			return rect1.intersects(rect2); //check two Rect is Duplicated!
		}
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyFrame("Turtle Game");

	}

}

