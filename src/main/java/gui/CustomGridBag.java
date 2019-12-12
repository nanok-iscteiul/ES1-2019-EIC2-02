package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * @author ES1-2019-EIC2-02
 *
 */
public class CustomGridBag extends JPanel {
	private static final long serialVersionUID = 1L;
	GridBagConstraints constraints = new GridBagConstraints();

	/**
	 * Class constructor. Creates a CustomGridBag with a left panel and a right panel.
	 * @param jPanelLeft the panel to put on the left of the GridBag
	 * @param jPanelRight the panel to put on the right of the GridBag
	 */
	public CustomGridBag(JPanel jPanelLeft, JPanel jPanelRight) {
		setLayout(new GridBagLayout());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		constraints.weightx = 0.0;
		addGB(jPanelLeft, 0, 0);
		constraints.weightx = 1.0;
		addGB(jPanelRight, 1, 0);

	}

	/**
	 * Places the component in the (x,y) position in the GridBagLayout.
	 * 
	 * @param component the object to place in the GridBagLayout
	 * @param x the x component for the position to put the component
	 * @param y	the y component for the position to put the component
	 */
	public void addGB(Component component, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);
	}
}