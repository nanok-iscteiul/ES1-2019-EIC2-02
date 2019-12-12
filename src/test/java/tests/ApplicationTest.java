package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import application.Application;
import application.MethodData;

@RunWith(JUnitPlatform.class)
class ApplicationTest {
	
	Application a;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		a = new Application();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSetPath() {
		a.setPath("Hello World");
		assertEquals("Hello World",a.getFileName());
	}

	@Test
	void testLongMethod() {
		a.longMethod(15, 10);
	}

	@Test
	void testFeature_envy() {
		a.feature_envy(15, 10, "and");
		a.feature_envy(15, 10, "or");
	}

	@Test
	void testDefectDetection() {
		a.defectDetection();
	}

	@Test
	void testDefectDetectionDefinedRules() {
		a.defectDetectionDefinedRules(0);
		a.defectDetectionDefinedRules(1);
	}

	@Test
	void testLoadFile() {
		a.setPath("C:\\Users\\nicha\\Desktop\\Long-Method.xlsx");
		a.loadFile();
	}

	@Test
	void testGetFileName() {
		a.setPath("Hello World");
		assertEquals("Hello World",a.getFileName());
	}

	@Test
	void testGetMethodsData() {
		List<MethodData> methodsData = a.getMethodsData();
		assertEquals(0,methodsData.size());
		
	}

}
