package tests;

import java.awt.Component;

import javax.swing.JPanel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import gui.CustomGridBag;

@RunWith(JUnitPlatform.class)
class CustomGridBagTest {
	CustomGridBag cgb;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		cgb = new CustomGridBag(new JPanel(), new JPanel());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddGB() {
		Component c = new Component() {
			private static final long serialVersionUID = 1L;
		};
		cgb.addGB(c,0,0);
	}

}
