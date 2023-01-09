import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * EspressOS Mobile Phone Contact Class.
 *
 * EspressOSContact
 * 
 * == Contact data ==
 * Each EspressOSContact stores the first name, last name and phone number of a person. 
 * These can be queried by calling the appropriate get method. They are updated 
 * with new values. The phone number can only be a 6 - 14 digit number.
 * The chat history is also stored. 
 * 
 * 
 * == Chat history ==
 * Each EspressOSContact stores the history of chat messages related to this contact. 
 * Suppose there is a conversation between Angus and Beatrice:
 * 
 * Angus: Man, I'm so hungry! Can you buy me a burrito?
 * Beatrice: I don't have any money to buy you a burrito.
 * Angus: Please? I haven't eaten anything all day.
 * 
 * Each time a message is added the name of the person and the message is 
 * combined as above and recorded in the sequence it was received.
 * 
 * The messages are stored in the instance variable String array chatHistory. Provided for you.
 * Unfortunately there are only 20 messages maximum to store and no more. 
 * When there are more than 20 messages, oldest messages from this array are discarded and 
 * only the most recent 20 messages remain. 
 * 
 * The functions for chat history are 
 *   addChatMessage
 *   getLastMessage
 *   getOldestMessage
 *   clearChatHistory()
 *
 * Using the above conversation as an example
 *   addChatMessage("Angus", "Man, I'm so hungry! Can you buy me a burrito?");
 *   addChatMessage("Beatrice", "I don't have any money to buy you a burrito.");
 *   addChatMessage("Angus", "Please? I haven't eaten anything all day.");
 *
 *   getLastMessage() returns "Angus: Please? I haven't eaten anything all day."
 *   getOldestMessage() returns "Angus: Man, I'm so hungry! Can you buy me a burrito?"
 *
 *   clearChatHistory()
 *   getLastMessage() returns null
 *   getOldestMessage() returns null
 *
 *
 * == Copy of contact ==
 * It is necessary to make copy of this object that contains exactly the same data. 
 * There are many hackers working in other parts of EspressOS, so we cannot trust them 
 * changing the data. A copy will have all the private data and chat history included.
 *
 *
 * Please implement the methods provided, as some of the marking is
 * making sure that these methods work as specified.
 *
 *
 */
public class EspressOSContact
{
	public static final int MAXIMUM_CHAT_HISTORY = 20;	
	
	/* given */
	protected String[] chatHistory;
	private String fname;
	private String lname;
	private String pnumber;
	private int currentPointer=0;
	
	public EspressOSContact(String fname, String lname, String pnumber) {
		/* given */
		this.pnumber=pnumber;
		updateFirstName(fname);
		updateLastName(lname);
		chatHistory = new String[MAXIMUM_CHAT_HISTORY];
	}
	
	public String getFirstName() {
		return fname;

	}
	public String getLastName() {
		return lname;
	}
	public String getPhoneNumber() {
		return pnumber;
	}

	/* if firstName is null the method will do nothing and return
	 */
	public void updateFirstName(String firstName) {
		if(firstName==null||firstName.length()==0) {
			return;
		}
		this.fname=firstName;

	}
	/* if lastName is null the method will do nothing and return
	 */
	public void updateLastName(String lastName) {
		if(lastName==null||lastName.length()==0) {
			return;
		}
		this.lname=lastName;
	}
	
	/* only allows integer numbers (long type) between 6 and 14 digits
	 * no spaces allowed, or prefixes of + symbols
	 * leading 0 digits are allowed
	 * return true if successfully updated
	 * if number is null, number is set to an empty string and the method returns false
	 */
	public boolean updatePhoneNumber(String number) {

		if(number==null) {
			this.pnumber=null;
			return false;
		}
		int digits=number.length();
		if(digits<6||digits>14||number.charAt(0)=='+') {
			return false;
		}
		try {
			for(int i=0;i<number.length();i++) {
				Character.getNumericValue(number.charAt(i));  
				if(number.charAt(i)==' ') {
					return false;
				}
			}
			
		}catch(Exception e) {
			return false;
		}
		this.pnumber=number;
		return true;
	}
	
	/* add a new message to the chat
	 * The message will take the form
	 * whoSaidIt + ": " + message
	 * 
	 * if the history is full, the oldest message is replaced
	 * Hint: keep track of the position of the oldest or newest message!
	 */
	public void addChatMessage(String whoSaidIt, String message) {
		String cache;
		cache=whoSaidIt+": "+message;
		if(currentPointer<20) {
			chatHistory[currentPointer]=cache;
			currentPointer++;
		}else if( currentPointer==20) {
			ArrayList<String>temp=new ArrayList<String>(Arrays.asList(chatHistory));
			temp.remove(0);
			temp.add(cache);
			IntStream.range(0, MAXIMUM_CHAT_HISTORY).forEach(i->chatHistory[i]=temp.get(i));
		}
		
		
	}

	/* after this, both last and oldest message should be referring to index 0
	 * all entries of chatHistory are set to null
	 */
	public void clearChatHistory() {
		String[] temp=new String[MAXIMUM_CHAT_HISTORY];
		chatHistory=temp;
		currentPointer=0;
	}

	/* returns the last message 
	 * if no messages, returns null
	 */
	public String getLastMessage() {
		if(chatHistory==null||chatHistory.length==0) {
			return null;
		}
		if(currentPointer==0){
			return null;
		}
		return chatHistory[currentPointer-1];
	}
	
	/* returns the oldest message in the chat history
	 * depending on if there was ever MAXIMUM_CHAT_HISTORY messages
	 * 1) less than MAXIMUM_CHAT_HISTORY, returns the first message
	 * 2) more than MAXIMUM_CHAT_HISTORY, returns the oldest
	 * returns null if no messages exist
	 */
	public String getOldestMessage() {
		if(chatHistory==null||chatHistory.length==0) {
			return null;
		}
		return chatHistory[0];
	}


	/* creates a copy of this contact
	 * returns a new EspressOSContact object with all data same as the current object
	 */
		public EspressOSContact copy() 
	{
		EspressOSContact copyE=new EspressOSContact(this.getFirstName(),this.getLastName(),this.getPhoneNumber());
		copyE.setChatHistory(chatHistory);
		copyE.setCurrentPointer(currentPointer);
		System.out.println(copyE.currentPointer);
		return copyE;

	}
	private void setChatHistory(String[] chatHistory) {
		String[] newchatHistory=new String[MAXIMUM_CHAT_HISTORY];
		int counter=0;
		for(String s:chatHistory) {
			newchatHistory[counter]=s;
			counter++;
		}
		this.chatHistory=newchatHistory;
		
	}
	private void setCurrentPointer(int cp) {
		this.currentPointer=cp;
	}

		
	/* -- NOT TESTED --
	 * You can impelement this to help with debugging when failing ed tests 
	 * involving chat history. You can print whatever you like
	 * Implementers notes: the format is printf("%d %s\n", index, line); 
	 */
	public void  printMessagesOldestToNewest() {
		System.out.println("[ Name: "+fname+ "| Surname: "+lname+"| Phone Number: "+pnumber);
		System.out.println(Arrays.toString(chatHistory));
		
	}
}
