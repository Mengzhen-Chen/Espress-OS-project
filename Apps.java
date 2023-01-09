interface AppsFeature {
public void start();
public void exit();
}
public class Apps extends BackgroundApps implements AppsFeature, NotifyApps {

	/* status 0 : =both Notify App & background App
	 * status 1: =  notify App &  non background App
	 * status 2 := non- notify App &  background App
	 * status 3 := none
	 */
	private BackgroundThread a;
	private boolean backGroundOn=false;
	private boolean on=false;
	private int  status;
	private String getStatus;
	private boolean available=false;
	public Apps( Boolean NotifyApp, Boolean BackgroundApp) {
		
		if(NotifyApp) {
			if(BackgroundApp) {
				status=0;
				getStatus="NotifyApp&BackgroundApp";
				a=new BackgroundThread(this);
			}else {
				status=1;
				getStatus="NotifyApp&NonBackgroundApp";
			}
		}else {
			if(BackgroundApp) {
				status=2;
				a=new BackgroundThread(this);
				getStatus="NonNotifyApp&BackgroundApp";
			}else {
				status=3;
				getStatus="NonNotifyApp&NonBackgroundApp";
			}
		}	
	}
	public String getStatus() {
		return getStatus;
	}
	// Since  only 1 non-background app can be run at a time, s.t. we do the checking thing before we run it.
	public void setChecker(boolean input) {
		available=input;
	}
	

	public void start() {
		if(status==1||status==3) {
			if(available) {
			on=true;
			return;
			}else {
				System.out.println("There is an App is currently running. ");
				return;
			}
		}
		if(status==0||status==2) {
			a.start();
			backGroundOn=true;
			return;
		}
	}


	public void exit() {
		// TODO Auto-generated method stub
		System.out.println("End");
		if(status==0||status==2) {
			a.exit();
			backGroundOn=false;
			
		}else if(status==1||status==3) {
			on=false;
		}
	}

	// implement  this method for backgroundThread class
	protected void backgroundStart() {
		
	}


	public String notifyOS() {
			if(status==0&&backGroundOn) {
				System.out.println("a");
				return "Testing A";
			}
			if(status==1&&on) {
				return "Testing B";
			
		}
			System.out.println(status);
		return null;
	}
	public boolean isRunning() {
		if(status==1||status==3) {
			return on;
		}
			return  backGroundOn;
	}
	public Object getData(Object input) {
		if(status==0&&backGroundOn||status==2&&backGroundOn) {
			return input;
		}
		return null;
		
	}
	
}
