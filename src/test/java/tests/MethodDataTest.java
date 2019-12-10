package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import application.MethodData;

@RunWith(JUnitPlatform.class)
class MethodDataTest {
	MethodData m;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		m = new MethodData(1,"packageTest","classTest","methodTest",1,2,3,4,true,false,true,false);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSetIs_long_method_by_rules() {
		m.setIs_long_method_by_rules(true);
		assertEquals(true,m.getIs_long_method_by_rules());
		m.setIs_long_method_by_rules(false);
		assertEquals(false,m.getIs_long_method_by_rules());
	}

	@Test
	void testSetIs_feature_envy_by_rules() {
		m.setIs_feature_envy_by_rules(true);
		assertEquals(true,m.getIs_feature_envy_by_rules());
		m.setIs_feature_envy_by_rules(false);
		assertEquals(false,m.getIs_feature_envy_by_rules());
	}

	@Test
	void testGetMethodId() {
		assertEquals(1,m.getMethodId());
	}

	@Test
	void testGetPackageName() {
		assertEquals("packageTest",m.getPackageName());
	}

	@Test
	void testGetClassName() {
		assertEquals("classTest",m.getClassName());
	}

	@Test
	void testGetMethodName() {
		assertEquals("methodTest",m.getMethodName());
	}

	@Test
	void testGetLoc() {
		assertEquals(1,m.getLoc());
	}

	@Test
	void testGetCyclo() {
		assertEquals(2,m.getCyclo());
	}
	
	@Test
	void testGetAtfd() {
		long atfd = (long) m.getAtfd();
		assertEquals(3,atfd);
	}

	@Test
	void testGetLaa() {
		long laa= (long) m.getLaa();
		assertEquals(4,laa);
	}

	@Test
	void testGetIs_long_method() {
		assertEquals(true,m.getIs_long_method());
	}

	@Test
	void testGetIs_feature_envy() {
		assertEquals(false,m.getIs_feature_envy());
	}

	@Test
	void testGetIplasma() {
		assertEquals(true,m.getIplasma());
	}

	@Test
	void testGetPmd() {
		assertEquals(false,m.getPmd());
	}

	@Test
	void testGetIs_long_method_by_rules() {
		m.setIs_long_method_by_rules(true);
		assertTrue(m.getIs_long_method_by_rules());
	}

	@Test
	void testGetIs_feature_envy_by_rules() {
		m.setIs_feature_envy_by_rules(false);
		assertFalse(m.getIs_feature_envy_by_rules());
	}

}
