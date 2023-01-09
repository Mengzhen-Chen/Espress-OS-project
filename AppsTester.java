
import org.junit.Test;
import static org.junit.Assert.*;

public class AppsTester {
	@Test
	public void AppsStatusTesting() {
		Apps a=new Apps(true,true);
		Apps b=new Apps(false,true);
		Apps c=new Apps(true,false);
		Apps d=new Apps(false,false);
		assertTrue(a.getStatus()=="NotifyApp&BackgroundApp");
		assertTrue(b.getStatus()=="NonNotifyApp&BackgroundApp");
		assertTrue(c.getStatus()=="NotifyApp&NonBackgroundApp");
		assertTrue(d.getStatus()=="NonNotifyApp&NonBackgroundApp");
	}
	@Test
	public void TestingNotifyOS_Null() {
		Apps testA=new Apps(true,false);
		testA.setChecker(false);
		assertTrue(testA.notifyOS()==null);
		
		Apps testB=new Apps(true,false);
		assertTrue(testB.notifyOS()==null);
		
		Apps testC=new Apps(true,true);
		testC.start();
		testC.exit();
		assertTrue(testC.notifyOS()==null);
		
		Apps testD=new Apps(true,true);
		testD.backgroundStart();
		testD.exit();
		System.out.println(testD.notifyOS());
		assertTrue(testD.notifyOS()==null);
		
		Apps testE=new Apps(true,false);
		testE.setChecker(true);
		testE.start();
		testE.exit();
		assertTrue(testE.notifyOS()==null);
	}
	@Test
	public void TestingNotifyOSWithNonBackgroundApp() {
		Apps testA=new Apps(true,false);
		testA.setChecker(true);
		testA.start();
		assertTrue(testA.notifyOS()=="Testing B");
	}
	
	@Test
	public void TestingNotifyOSWithBackgroundApp() {
		Apps testA =new Apps(true,true);
		testA.start();
		assertTrue(testA.notifyOS()=="Testing A");
		testA.exit();	
	}
	@Test
	 public void TestingGetData_null() {
		Apps testA=new Apps(false, false);
		Apps testB=new Apps(true, false);
		testA.setChecker(true);
		testB.setChecker(true);
		testA.start();
		testB.start();
		assertEquals(testA.getData(testB),null);
		assertEquals(testB.getData(testA),null);
	}
	@Test
	public void TestingGetData_returnObject() {
		Apps testA=new Apps(false, true);
		Apps testB=new Apps(true, true);
		testA.start();
		testB.start();
		assertEquals(testA.getData(testB),testB);
		assertEquals(testB.getData(testA),testA);
	}
	@Test
	public void TestingInstall_null() {
		EspressOSMobile a=new EspressOSMobile();
		a.install(null);
		a.install(null);
		a.install(null);
		assertEquals(null,a.getInstalledApps());
		assertEquals(null,a.getBackgroundApps());
		assertEquals(null,a.getNotificationApp());
		assertEquals(null,a.getRunningApps());
		assertEquals(null,a.getNotifications());
	}

	@Test
	public void TestingUnstall_InstallAndGetApp() {
		Apps testA=new Apps(false, true);
		testA.start();
		// b=1
		Apps testB=new Apps(true, true);
		testB.start();
		//b=2,n=1
		Apps testC=new Apps(true, false);
		testC.setChecker(true);
		testC.start();
		//b=2,n=2
		Apps testD=new Apps(true, false);
		testD.setChecker(true);
		testD.start();
		//b=2,n=3
		EspressOSMobile a=new EspressOSMobile();
		
		a.install(testA);
		
		a.install(testB);
		a.install(testB);
		
		a.install(testC);
		a.install(testD);
		a.uninstall(testD);
		//b=2,n=2
		assertTrue(a.getBackgroundApps().size()==2);
		assertTrue(a.getBackgroundApps().contains(testA));
		assertTrue(a.getNotificationApp().size()==2);
		assertTrue((a.getBackgroundApps().contains(testB)));
		assertTrue(a.getRunningApps().size()==3);
		// Which also implied that testB only installed once
		assertTrue(a.getRunningApps().contains(testA));
		assertTrue(a.getRunningApps().contains(testB));
		assertTrue(a.getRunningApps().contains(testC));
		assertTrue(!(a.getRunningApps().contains(testD)));
		// Since testD has been removed
	}
	@Test
	public void TestingRunNull() {
		Apps testA=new Apps(false, true);
		EspressOSMobile a=new EspressOSMobile();
		a.install(testA);
		a.run(null);
		// if run null Doing nothing
	}
	public void TestingExitNull() {
		Apps testA=new Apps(false, true);
		EspressOSMobile a=new EspressOSMobile();
		a.install(testA);
		a.exit(null);
	}
	@Test
	public void TestRunAndExit() {
		Apps testA=new Apps(false, true);
		Apps testB=new Apps(true, true);
		Apps testC=new Apps(true, false);
		Apps testD=new Apps(false, false);
		EspressOSMobile a=new EspressOSMobile();
		a.install(testA);
		a.install(testB);
		a.install(testC);
		a.install(testD);
		a.run(testA);
		a.run(testB);
		a.run(testC);
		// testD would not run since we can only run a non-background app at a time
		a.run(testD);
		assertTrue(a.getRunningApps().size()==3);
		assertTrue(!(a.getRunningApps().contains(testD)));
		a.exit(testC);
		assertTrue(a.getRunningApps().size()==2);
		a.exit(testB);
		a.uninstall(null);
		assertTrue(a.getRunningApps().size()==1);
	}
	@Test
	public void TestingGetNotification() {
		Apps testA=new Apps(false, true);
		Apps testB=new Apps(true, true);
		Apps testC=new Apps(true, false);
		EspressOSMobile a=new EspressOSMobile();
		a.install(testA);
		a.install(testB);
		a.install(testC);
		a.install(null);
		assertTrue(a.getNotificationApp().size()==2);
	}
	
}

