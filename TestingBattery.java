import org.junit.Test;
import static org.junit.Assert.*;

public class  TestingBattery {


@Test
public void testLevelOver100() {

	EspressOSMobile a=new EspressOSMobile();
	TestingA b=new TestingA(101);	
	TestingA c=new TestingA(199);	
	assertTrue(a.changeBattery(b)==false);
	assertTrue(a.changeBattery(c)==false);
}
@Test
public void testLevelBelow0() {

	EspressOSMobile a=new EspressOSMobile();
	TestingA b=new TestingA(-1);	
	TestingA c=new TestingA(-12);	
	assertTrue(a.changeBattery(b)==false);
	assertTrue(a.changeBattery(c)==false);
}
@Test
public void testLevel() {
	EspressOSMobile a=new EspressOSMobile();
	TestingA b=new TestingA(0);	
	TestingA d=new TestingA(0);	
	assertTrue(a.changeBattery(b)==true);
	assertTrue(a.changeBattery(d)==true);
}
@Test
public void testNull() {
	EspressOSMobile a=new EspressOSMobile();
	assertTrue(a.changeBattery(null)==false);
}
@Test
public void testGetBatteryLife() {
	EspressOSMobile a=new EspressOSMobile();
	TestingA d=new TestingA(18);	
	a.changeBattery(d);
	a.setPhoneOn(true);
	assertTrue(a.getBatteryLife()==13);
	TestingA c=new TestingA(0);	
	a.changeBattery(c);
	assertTrue(a.getBatteryLife()==0);
}
@Test
public void testBadBattery() {
	EspressOSMobile a=new EspressOSMobile();
	int temp=a.getBatteryLife();
	TestingA d=new TestingA(18);	
	a.changeBattery(null);
	assertTrue(a.getBatteryLife()==temp);
}

}

