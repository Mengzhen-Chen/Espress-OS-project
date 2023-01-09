

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * EspressOS Mobile Phone Class.
 *
 *
 * EspressOSMobile
 * In this assignment you will be creating an EspressOS Mobile Phone as part of a simulation.
 * The Mobile phone includes several attributes unique to the phone and has simple functionality.
 * You are to complete 2 classes. EspressOSMobile and EspressOSContact
 *
 * The phone has data
 *  Information about the phone state. 
 *    If it is On/Off
 *    Battery level 
 *    If it is connected to network. 
 *    Signal strength when connected to network
 *  Information about the current owner saved as contact information. 
 *    First name
 *    Last name
 *    Phone number
 *  A list of 10 possible contacts.
 *    Each contact stores first name, last name, phone number and chat history up to 20 messages
 *  
 * The phone has functionality
 *  Turning on the phone
 *  Charging the phone. Increase battery level
 *  Change battery (set battery level)
 *  Use phone for k units of battery (decreases battery level by k)
 *  Search/add/remove contacts
 *
 * Attribute features
 *  if the phone is off. It is not connected. 
 *  if the phone is not connected there is no signal strength
 *  the attribute for battery life has valid range [0,100]. 0 is flat, 100 is full.
 *  the attribute for signal strength has a valid range [0, 5]. 0 is no signal, 5 is best signal.
 * 
 * Please implement the methods provided, as some of the marking is
 * making sure that these methods work as specified.
 *
 *
 */
public class EspressOSMobile 
{
	public static final int MAXIMUM_CONTACTS = 10;
	

	/* Use this to store contacts. Do not modify. */
	protected EspressOSContact[] contacts;
	private boolean phoneOn;
	private int  batteryLife;
	private  boolean isConnected;
	private int SignalStrength;
	private int numOfCons=1;
	private  EspressOSContact owner;
	private int tempSingleStrength;
	private  List<Apps> apps=new ArrayList<Apps>();
//	private Map<> a=new HashMap<>();
	/* Every phone manufactured has the following attributes
	 * 
	 * the phone is off
	 * the phone has battery life 25
	 * the phone is not connected
	 * the phone has signal strength 0
	 * Each of the contacts stored in the array contacts has a null value
	 * 
	 * the owner first name "EspressOS"
	 * the owner last name is "Incorporated"
	 * the owner phone number is "180076237867"
	 * the owner chat message should have only one message 
	 *         "Thank you for choosing EspressOS products"
	 *
	 */
	public EspressOSMobile() {
		/* given */
		
		phoneOn=false;
		batteryLife=25;
		isConnected=false;
		SignalStrength=0;
		contacts = new EspressOSContact[MAXIMUM_CONTACTS];
		 owner=  new EspressOSContact("EspressOS","Incorporated","180076237867");
		owner.addChatMessage("EspressOS","Thank you for choosing EspressOS products" );
				
		
		
	}

	/* returns a copy of the owner contact details
	 * return null if the phone is off
	 */
	public EspressOSContact getCopyOfOwnerContact() {
		if(phoneOn) {
			return owner.copy();
		}
		return null;
	}


	/* only works if phone is on
	 * will add the contact in the array only if there is space and does not exist
	 * The method will find an element that is null and set it to be the contact
	 */
	public boolean addContact(EspressOSContact contact) {
		if(phoneOn) {
			for(int i=0;i<contacts.length;i++) {
				if(contacts[i]==contact) {
					return false;
				}
			}
			int flag=-1;
			
			for(int i=0;i<contacts.length;i++) {
				if(contacts[i]==null) {
					flag=i;
					break;
				}
			}
			if(flag==-1) {
				return false;
			}else {
				contacts[flag]=contact;
				numOfCons++;
				return true;
			}
		}
		return false;
	}

	/* only works if phone is on
	 * find the object and set the array element to null
 	 * return true on successful remove
	 */
	public boolean removeContact(EspressOSContact contact) {
		if(phoneOn) {
			int flag=0;
			boolean checker=false;
			for(int i=0;i<10;i++) {
				if(contacts[i]==null){
					continue;
				}
				if(contacts[i]==contact) {
					flag=i;	
					checker=true;
					break;
				}
			}
			if(checker) {
				contacts[flag]=null;
				numOfCons--;
				return true;
			}
			return false;
		}
		return false;
	}

	/* only works if phone is on
	 * return the number of contacts, or -1 if phone is off
	 */
	private int numOfCons() {
		int counter=0;
		for(int i=0;i<contacts.length;i++) {
			if(contacts[i]!=null) {
				counter++;
			}
		}
		return counter;
	}
	
	public int getNumberOfContacts() {
		if(phoneOn) {
		
			return numOfCons();
		}
		return -1;
	
	}

	/* only works if phone is on
	 * returns all contacts that match firstname OR lastname
	 * if phone is off, or no results, null is returned
	 */
		public EspressOSContact[] searchContact(String name) {
		ArrayList<EspressOSContact> result=new ArrayList<EspressOSContact>();
		boolean checker=false;
		if(phoneOn) {
			for(int i=0;i<MAXIMUM_CONTACTS;i++) {
				if(contacts[i]==null) {
					continue;
				}
				if(contacts[i].getFirstName()==name||contacts[i].getLastName()==name) {
					result.add(contacts[i]);
					checker=true;
				}
			}
			
			if(checker) {
				int counter=0;
				EspressOSContact[] a=new EspressOSContact[result.size()];
				for(EspressOSContact resultStr:result) {
					a[counter]=resultStr;
					counter++;
				}
				return a;
			}
			return null;
		}
		return null;
	}

	/* returns true if phone is on
	 */
	public boolean isPhoneOn() {
		return phoneOn;
	}

	/* when phone turns on, it costs 5 battery for startup. network is initially disconnected
	 * when phone turns off it costs 0 battery, network is disconnected
	 * always return true if turning off
	 * return false if do not have enough battery level to turn on
	 * return true otherwise
	 */
	
	 public boolean setPhoneOn(boolean on) {
		if(on) {
			if(phoneOn) {
				return true;
			}else{
				if(batteryLife<=5) {
					return false;
				}else {
					phoneOn=true;
					batteryLife-=5;
					// signalStrength
					isConnected=false;
					// disconnectNetwork();
					return true;
				}
			}
		}else {
			phoneOn=false;
			isConnected=false;
			//*
			// 	tempSingleStrength=SignalStrength;
			// SignalStrength=0;
		
			return  true;
		}
	}
	
	/* Return the battery life level. if the phone is off, zero is returned.
	 */
	public int getBatteryLife() {
		if(phoneOn) {
			return batteryLife;
		}
		return 0;
	}
	
	/* Change battery of phone.
	 * On success. The phone is off and new battery level adjusted and returns true
	 * If newBatteryLevel is outside manufacturer specification of [0,100], then 
	 * no changes occur and returns false.
	 */
	public boolean changeBattery(Battery battery) {
		if(battery==null) {
			return false;
		}
		if(battery.getLevel()<=100&&battery.getLevel()>=0) {
			setPhoneOn(false);
			batteryLife=battery.getLevel();
			return true;
					}
		return false;
	}
	//
	/* only works if phone is on. 
	 * returns true if the phone is connected to the network
	 */
	public boolean isConnectedNetwork() {
		if(phoneOn) {
			return isConnected;
		}
		return false;
	}
	
	/* only works if phone is on. 
	 * when disconnecting, the signal strength becomes zero
	 */
	public void disconnectNetwork() {
		if(phoneOn) {
			isConnected=false;
			SignalStrength=0;
		}

	}
	
	/* only works if phone is on. 
	 * Connect to network
	 * if already connected do nothing
	 * if connecting: 
	 *  1) signal strength is set to 1 if it was 0
	 *  2) signal strength will be the previous value if it is not zero
	 *  3) it will cost 2 battery life to do so
	 * returns the network connected status
	 */
	public boolean connectNetwork() {
		//if phone is on
		if(phoneOn) {
		//is connected
		if(isConnected) {
			return isConnected;
		}else {
			//is not and has enough battery 
			if(batteryLife>2) {
				if(SignalStrength==0) {
					SignalStrength=1;
				}
				batteryLife-=2;
				isConnected=true;
				return isConnected;
			}else {
				setPhoneOn(false);
				return false;
			}
		}
		}else {
			return false;
		}
		
		

	}
	
	/* only works if phone is on. 
	 * returns a value in range [1,5] if connected to network
	 * otherwise returns 0
	 */
	public int getSignalStrength() {
		if(isConnected&&phoneOn) {
			return SignalStrength;
		}
		return 0;

	}

	/* only works if phone is on. 
	 * sets the signal strength and may change the network connection status to on or off
	 * signal of 0 disconnects network
	 * signal [1,5] can connect to network if not already connected
	 * if the signal is set outside the range [0,5], nothing will occur and will return false
	 */
	public boolean setSignalStrength(int x) {
		if(phoneOn) {
			if(x>5||x<0) {
				return false;
			}else {
				if(x==0) {
					SignalStrength=x;
					isConnected=false;
					return true;
				}else {
					SignalStrength=x;
					isConnected=true;
					return true;
				}
			}
		}
		return false;

    }
	
	/* changes the antenna object
	 * signal strength is set to default and is not connected to a network
	 * if this constraint is violated then the antenna should not be changed.
	 * return true if antenna is changed.
	 */
	public boolean changeAntenna(Antenna antenna) {
		if(antenna==null) {
			return false;
		}
		//if antenna removed
		if(antenna==null||antenna.getSignalStrength()==0) {
			isConnected=false;
			SignalStrength=0;
			return false;
		}else if(antenna.getSignalStrength()>=0&&antenna.getSignalStrength()<=5) {
			setSignalStrength(antenna.getSignalStrength());
			return (phoneOn)?true:false;
			
		}else {
			return false;
		}

	}
	
	/* each charge increases battery by 10
	 * the phone has overcharge protection and cannot exceed 100
	 * returns true if the phone was charged by 10
	 */
	public boolean chargePhone() {
		if((batteryLife+10)<=100) {
			batteryLife+=10;
			return true;
		}else {
			batteryLife=100;
			return false;
		}

	}
	
	/* Use the phone which costs k units of battery life.
	 * if the activity exceeds the battery life, the battery automatically 
	 * becomes zero and the phone turns off.
	 */
	public void usePhone(int k) {	
		if(phoneOn) {
			if(batteryLife>k) {
				batteryLife-=k;
				return;
			}else {
			setPhoneOn(false);
			}
		}

	}
	/*install
	 *	Installs an app on the operating system. If the object passed is null then an app will not be installed.
 	 *	If the app has been installed already, then the app will not be installed. The method returns the value true
 	 *	 if the app has been successfully installed, otherwise false.
	 */
//	public boolean install(Object application) {
//		if(application ==null) {
//			return false;
//		}else {
//			return true;
//		}
//	}
	/*uninstall
	 *	Given a name of an app, it will find the app and remove it from the operating system.
	 *	If the app exists and has been uninstalled it will return true, otherwise the method returns false.
	 */
	public boolean install(Apps newApp) {
		for(Apps a:apps) {
			if(a==newApp) {
				return false;
			}
		}
		if(newApp==null) {
			return false;
		}else {
			apps.add(newApp);
			
			return true;
		}
	}
	public boolean uninstall(Apps input) {
		for(Apps a:apps) {
			if(a==input) {
				apps.remove(input);
				return true;
			}
		}
		return false;
	}
	public List<Apps> getInstalledApps() {
		System.out.println(apps);
		if(apps.size()==0) {
			return null;
		}
		return  apps;
	
	}
	public List<Apps> getBackgroundApps () {
		List<Apps> backGroundApps=new ArrayList<Apps>();
		for(Apps a:apps) {
			if(a.getStatus()=="NotifyApp&BackgroundApp"||a.getStatus()=="NonNotifyApp&BackgroundApp") {
				backGroundApps.add(a);
			}
		}
		if(backGroundApps.size()==0) {
			return null;
		}
		return backGroundApps;
	}
	
	public List<Apps> getNotificationApp () {
		List<Apps> NotificationApp=new ArrayList<Apps>();
		for(Apps a:apps) {
			if(a.getStatus()=="NotifyApp&BackgroundApp"||a.getStatus()=="NotifyApp&NonBackgroundApp") {
				NotificationApp.add(a);
			}
		}
		if(NotificationApp.size()==0) {
			return null;
		}
		return NotificationApp;
	}
	
	public List<String> getNotifications () {
		List<String> Notification=new ArrayList<String>();
		for(Apps a:apps) {
			if(a.getStatus()=="NotifyApp&BackgroundApp"||a.getStatus()=="NotifyApp&NonBackgroundApp") {
				Notification.add(a.notifyOS());
			}
		}
		if(Notification.size()==0) {
			return null;
		}
		return Notification;
	}
	
	public List<Apps> getRunningApps () {
		List<Apps> RunningApps=new ArrayList<Apps>();
		for(Apps a:apps) {
			if(a.isRunning()) {
				RunningApps.add(a);
			}
		}
		if(RunningApps.size()==0) {
			return null;
		}
		return RunningApps;
	}
	public void run(Apps input) {
		if(input==null) {
			return;
		}
		if(input.getStatus()=="NotifyApp&BackgroundApp"||input.getStatus()=="NonNotifyApp&BackgroundApp") {
			input.start();
			return;
		}
		boolean AProgramIsRunning=false;
		for(Apps a:apps) {
			if(a.getStatus()=="NotifyApp&NonBackgroundApp"||a.getStatus()=="NonNotifyApp&NonBackgroundApp") {
				if(a.isRunning()==true) {
					AProgramIsRunning=true;
				}
			}
		}
		if(AProgramIsRunning==false) {
			
			input.setChecker(true);
		}
	
		input.start();
	}
	// It can either turn off BackgroundApp or nonBackgroundApp
	public void exit(Apps input) {
		if(input==null) {
			return;
		}
		input.exit();
	}	
	
}
