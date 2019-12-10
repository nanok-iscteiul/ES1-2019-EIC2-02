package tests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
@SelectClasses({ ApplicationTest.class, CustomGridBagTest.class, GUITest.class, MethodDataTest.class })
public class AllTests {

}
