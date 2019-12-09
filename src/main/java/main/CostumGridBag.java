package main;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class CostumGridBag extends JPanel {
    GridBagConstraints constraints = new GridBagConstraints();

    public CostumGridBag(JPanel esquerdo, JPanel direito) {
      setLayout(new GridBagLayout());
      constraints.fill = GridBagConstraints.BOTH;
      constraints.weighty = 1.0;
      constraints.weightx = 0.075;
      //esquerdo
      addGB(esquerdo, 0, 0);
      //fimesquerdo
      constraints.weightx = 1.0;
      //direito
      addGB(direito, 1,   0);
  
    }

    void addGB(Component component, int x, int y) {
      constraints.gridx = x;
      constraints.gridy = y;
      add(component, constraints);
    }
/*
    public static void main(String[] args) {
      JFrame frame = new JFrame("GridBag4");
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      frame.setSize(300, 100);
      frame.setLocation(200, 200);
      frame.setContentPane(new GridBag4());
      frame.setVisible(true);
    }
*/    
  }