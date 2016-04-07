package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

//https://www.youtube.com/watch?v=S_n3lryyGZM&list=WL&index=7
//1:10:18 Minute Mark!!!

public class Snake implements ActionListener, KeyListener {
	public static Snake snake;

	public JFrame jframe;

	public RenderPanel renderPanel;

	public Timer timer = new Timer(20, this);

	public ArrayList<Point> snakeParts = new ArrayList<Point>();

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;

	public boolean over = false, paused = false;

	public int ticks = 0, direction = DOWN, score, tailLength = 0;

	public Point head, cherry;

	public Random random;

	public Dimension dim;

	@SuppressWarnings("static-access")
	public Snake() {
		score = 0;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake"); // Title
		jframe.setVisible(true); // Visible
		jframe.setSize(800, 700); // Size
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height
				/ 2 - jframe.getHeight() / 2);// Location on screen
		jframe.addKeyListener(this);// Adding a key listener
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.add(renderPanel = new RenderPanel());

		startGame();
	}

	public void startGame() {
		over = false;
		score = 0;
		tailLength = 0;
		direction = DOWN;

		head = new Point(0, 0);
		random = new Random();
		cherry = new Point(random.nextInt(79), random.nextInt(67));

		for (int i = 0; i < tailLength; i++) {
			snakeParts.add(new Point(head.x, head.y));
		}
		timer.start();
	}

	public void actionPerformed(ActionEvent args) {
		renderPanel.repaint();
		ticks++;
		if (ticks % 2 == 0 && head != null && !over && !paused) // I added !over
		{
			snakeParts.add(new Point(head.x, head.y));
			while (true)
			{
				if (snakeParts.size() > tailLength) {
					snakeParts.remove(0);
				}
				else
					break;
			}
			if (direction == UP) {
				if (head.y - 1 >= 0 && noTailAtHead(head.x, head.y - 1))
					head = new Point(head.x, head.y - 1);
				else
					over = true;
			}

			if (direction == DOWN) {
				if (head.y + 1 <= 66 && noTailAtHead(head.x, head.y + 1)) {
					head = new Point(head.x, head.y + 1);
				} else
					over = true;
			}

			if (direction == LEFT) {
				if (head.x - 1 >= 0 && noTailAtHead(head.x - 1, head.y))
					head = new Point(head.x - 1, head.y);
				else
					over = true;
			}

			if (direction == RIGHT) {
				if (head.x + 1 <= 79 && noTailAtHead(head.x + 1, head.y))
					head = new Point(head.x + 1, head.y);
				else
					over = true;
			}

			if (cherry != null) {
				// System.out.println(cherry.x + ", " + cherry.y);
				if (head.equals(cherry)) {
					score += 10;
					tailLength+=6;
					cherry.setLocation(random.nextInt(79), random.nextInt(67));
				}
			}
		}
	}

	public boolean noTailAtHead(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y)))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		snake = new Snake();
	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == KeyEvent.VK_A && direction != RIGHT) {
			direction = LEFT;
		} else if (i == KeyEvent.VK_W && direction != DOWN) {
			direction = UP;
		} else if (i == KeyEvent.VK_D && direction != LEFT) {
			direction = RIGHT;
		} else if (i == KeyEvent.VK_S && direction != UP) {
			direction = DOWN;
		} else if (i == KeyEvent.VK_SPACE && over) {
			startGame();
		} else {
			if (paused)
				paused = false;
			else
				paused = true;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
