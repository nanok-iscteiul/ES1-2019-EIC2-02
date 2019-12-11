package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CustomGridBag extends JPanel {
	private static final long serialVersionUID = 1L;
	GridBagConstraints constraints = new GridBagConstraints();

	public CustomGridBag(JPanel jPanel, JPanel jPanel2) {
		setLayout(new GridBagLayout());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		// painel esquerdo
		constraints.weightx = 0.0;
		addGB(jPanel, 0, 0);
		// fim painel esquerdo
		constraints.weightx = 1.0;
		// painel direito
		addGB(jPanel2, 1, 0);

	}

	public void addGB(Component component, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);
	}
}