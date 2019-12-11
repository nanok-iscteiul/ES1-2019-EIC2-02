package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import application.Application;
import gui.GUI;

@RunWith(JUnitPlatform.class)
class GUITest {
	
	GUI gui;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gui = new GUI(new Application());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateButton() {
		gui.createButton("Button", 100, 50, 12, 10);
	}

	@Test
	void testFillTable() {
		gui.fillTable();
	}

	@Test
	void testReceiveOutputDefectDetection() {
		int [] countersIplasma = {1,2,3,4};
		int [] countersPmd = {5,6,7,8};
		gui.receiveOutputDefectDetection(countersIplasma, countersPmd);
	}

	@Test
	void testReceiveOutputDefectDetectionDefinedRules() {
		int [] counters = {1,2,3,4};
		gui.receiveOutputDefectDetectionDefinedRules("Long Method", counters);
	}

	@Test
	void testClearDefectDetectionTable() {
		gui.clearDefectDetectionTable();
	}

}
