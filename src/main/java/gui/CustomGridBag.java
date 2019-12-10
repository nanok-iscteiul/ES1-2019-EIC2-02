package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class CustomGridBag extends JPanel {
	private static final long serialVersionUID = 1L;
	GridBagConstraints constraints = new GridBagConstraints();

	public CustomGridBag(JPanel esquerdo, JPanel direito) {
		setLayout(new GridBagLayout());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		constraints.weightx = 0.075;
		// esquerdo
		addGB(esquerdo, 0, 0);
		// fim esquerdo
		constraints.weightx = 1.0;
		// direito
		addGB(direito, 1, 0);

	}

	public void addGB(Component component, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);
	}
}