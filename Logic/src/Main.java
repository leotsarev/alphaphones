import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;
import phones.InteractionModel;
import phones.InteractionModel.Descriptor;
import phones.InteractionModel.MenuDescriptor;
import phones.InteractionModel.TickDescriptor;
import phones.SampleIM;
import phones.Utils;


public class Main {

	static BufferedReader in;
	static PrintStream out;

	static InteractionModel im;
	
	static TickDescriptor tickDescriptor;
	static MenuDescriptor menuDescriptor;
	static int remainingTimeout;
	
	static String prevStatus = null;
	static Date prevTime, currentTime;
	
	static void next() {
		while (true) {
			long dt = (currentTime.getTime()-prevTime.getTime())/1000;
			Descriptor descriptor = im.whatNext((int)dt, currentTime);
			prevTime = currentTime;
			remainingTimeout = descriptor.timeout;
			if (descriptor instanceof TickDescriptor) {
				tickDescriptor = (TickDescriptor)descriptor;
				menuDescriptor = null;
			} else {
				menuDescriptor = (MenuDescriptor)descriptor;
				tickDescriptor = null;
			}
	
			if (tickDescriptor != null) {
				if (!tickDescriptor.status.equals(prevStatus)) {
					out.println("STATUS: "+tickDescriptor.status);
					prevStatus = tickDescriptor.status;
				}
			}
			else if (menuDescriptor != null) {
				out.println("Menu (TODO)");
			}
			else
				Utils.assert_(false);
			
			if (remainingTimeout > 0)
				break;
			
			if (tickDescriptor != null) {
				im.receiveTickTimeout();
			}
			else if (menuDescriptor != null) {
				im.receiveMenuTimeout();
			}
			else
				Utils.assert_(false);
		}
	}
	
	static void advanceTime(int dt) {
		Utils.assert_(dt >= 0);
		if (dt == 0)
			return;
		out.println(dt+" seconds later...");
		currentTime = new Date(currentTime.getTime()+1000*dt);
	}
	
	static void wait(int t) {
		while (t >= remainingTimeout) {
			int dt = remainingTimeout;
			t -= dt;
			
			advanceTime(dt);
			
			if (tickDescriptor != null) 
				im.receiveTickTimeout();
			else if (menuDescriptor != null)
				im.receiveMenuTimeout();
			else
				Utils.assert_(false);
			next();
		}
		
		remainingTimeout -= t;
		advanceTime(t);
		
		Utils.assert_(remainingTimeout > 0);
	}
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintStream(System.out, true, "utf-8"/*"866"*/);
		
		im = new SampleIM();
		im.reset();
		
		prevTime = currentTime = new Date(); 
		next();

		while (true) {
			out.print(">>> ");
			String input = in.readLine();
			
			if (input.equals("exit"))
				break;
			
			if (input.equals("help")) {
				out.println("Commands:");
				out.println("    help");
				out.println("    exit");
				out.println("    wait <seconds>");
				continue;
			}
			
			if (input.startsWith("wait ")) {
				try {
					int t = Integer.parseInt(input.substring(5));
					wait(t);
					continue;
				}
				catch (NumberFormatException e) {
				}
			}
			
			if (tickDescriptor != null) {
				try {
					Integer.parseInt(input);
					switch (im.codeStatus(input)) {
					case InteractionModel.VALID_CODE:
						im.receiveCode(input);
						next();
						continue;
					default:
						out.println("Code is not valid.");
						continue;
					}
				}
				catch (NumberFormatException e) {
				}
			}
			
			out.println("Unrecognized command. Type 'help' for help.");
		}
	}

}
