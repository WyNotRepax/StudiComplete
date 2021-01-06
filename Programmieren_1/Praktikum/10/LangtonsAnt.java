import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LangtonsAnt {

	private static final int SIZE = 200;
	private static final int STEPS = 40000;

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// erzeugt eine Welt der Groesse SIZE * SIZE
				World world = new World(LangtonsAnt.SIZE);

				AntWorldPanel worldPanel = new AntWorldPanel(world);
				AntFrame frame = new AntFrame(worldPanel);
				frame.setLocation(100, 100);
				frame.setResizable(false);
				frame.setVisible(true);
				AntSimulation simulation = new AntSimulation(world,
						LangtonsAnt.STEPS, worldPanel);
				simulation.start();
			}
		});
	}
}

class AntSimulation extends Thread {

	private World world;
	private int steps;
	private AntWorldPanel panel;

	AntSimulation(World world, int steps, AntWorldPanel panel) {
		this.world = world;
		this.steps = steps;
		this.panel = panel;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < this.steps; i++) {

				// holt sich die Ameise aus der Welt
				Ant ant = this.world.getAnt();

				// fuehrt einmal den Ameisenalgorithmus aus
				ant.nextStep();

				if (i % 30 == 0) {
					javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							AntSimulation.this.panel.repaint();
						}
					});
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}
}

class AntFrame extends JFrame {

	AntFrame(AntWorldPanel world) {
		super("Langtons Ant");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(world);
		this.pack();
	}
}

class AntWorldPanel extends JPanel {

	private World world;
	private BufferedImage image;

	AntWorldPanel(World world) {
		this.world = world;
		this.setPreferredSize(new Dimension(world.getSize(), world.getSize()));
		this.image = new BufferedImage(world.getSize(), world.getSize(),
				BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics buffer = this.image.getGraphics();
		buffer.setColor(Color.WHITE);
		buffer.fillRect(0, 0, this.image.getWidth(), this.image.getHeight());

		for (int row = 0; row < this.world.getSize(); row++) {
			for (int column = 0; column < this.world.getSize(); column++) {

				// liefert true, wenn die Zelle in Zeile row und Spalte column
				// sich im Zustand schwarz befindet
				if (this.world.isCellBlack(row, column)) {
					buffer.setColor(Color.BLACK);
				} else {
					buffer.setColor(Color.WHITE);
				}
				buffer.fillRect(column, row, 1, 1);
			}
		}
		g.drawImage(this.image, 0, 0, null);

	}
}
