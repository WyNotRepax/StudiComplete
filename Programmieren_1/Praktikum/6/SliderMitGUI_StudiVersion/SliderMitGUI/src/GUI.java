import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * GUI zu einem Spielfeld
 * Das Spielfeld wird abgebildet auf ein 2-dimensionales Feld von Buttons.
 * Die Buttons erhalten als Beschriftung den Wert der Zellen des Spielfeldes (als Text).
 * Der zu der freien Zelle gehörende Button erhält keine Beschriftung  
 * Die GUI ist der ActionListener für alle Buttons, jeder Button erhält als ActionCommand
 * die initiale Beschriftung der Zellen des Spielfeldes.
 * Während des Spielens werden nur die Beschriftungen der Buttons geändert.
 * 
 * TODO
 * Beenden des Spiels, wenn das Puzzle gelöst ist.
 * 
 * @author Friedhelm Tappe
 * @version 31.07.2014
 */


public class GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Spielfeld einSpielfeld;

	private JPanel contentPane;
		
	private JButton[][] buttonFeld;
	
	private int sizeXY;
	
	/**
	 * 
	 * @param einSpielfeld Referenz auf das Spielfeld
	 */
	public GUI(Spielfeld einSpielfeld) {
		
		this.einSpielfeld = einSpielfeld;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sizeXY = einSpielfeld.getSizeXY();
		
		setBounds(100, 100, sizeXY*100, sizeXY*75);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(sizeXY, sizeXY, 0, 0));
		
		buttonFeld = new JButton[sizeXY][sizeXY];
		
		for(int y = 0; y < sizeXY; y++) {
			for(int x = 0; x < sizeXY; x++) {
				buttonFeld[x][y] = new JButton(einSpielfeld.toStringZelle(x, y));
				// TODO
				// dynamische Anpassung der Schriftgroesse an Spielfeldgroesse
				buttonFeld[x][y].setFont(buttonFeld[x][y].getFont().deriveFont(20.0f));
				buttonFeld[x][y].setActionCommand(Integer.toString(x+(y*sizeXY+1)));
				buttonFeld[x][y].addActionListener(this);
				panel.add(buttonFeld[x][y]);
			}
		}		
		displaySpielfeld();
		
	}
	
	/**
	 * Setzen der Beschriftungen der Buttons in Abhängigkeit der Werte
	 * der Zellen des Spielfeldes.
	 */
	public void displaySpielfeld() {
		for(int y = 0; y < sizeXY; y++) {
			for(int x = 0; x < sizeXY; x++) {
				buttonFeld[x][y].setText(einSpielfeld.toStringZelle(x, y));
			}
		}
	}
	
	/**
	 * Zu implementierende Methode des Interfaces ActionListener
	 * Anhand des ActionCommands werden die Koordinaten des gedrückten Buttons ermittelt.
	 * Im Spielfeld wird , wenn möglich, die entsprechende Zelle verschoben.
	 * TODO
	 * Spielfeld nur dann neu ausgeben, wenn ein Verschieben der Zelle möglich war.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		String cmd = ae.getActionCommand();
		try {
			int xy = Integer.parseInt(cmd);
			int btnX = (xy - 1) % sizeXY;
			int btnY = (xy - 1) / sizeXY;
			einSpielfeld.verschiebeZelle(btnX, btnY);
			displaySpielfeld();
		} catch (NumberFormatException nex) {
		}
	}
	
}
