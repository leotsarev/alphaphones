import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.zip.InflaterOutputStream;

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
	static String prevStatus = null;
	static Date prevTime, currentTime;
	static int remainingTimeout;
	
	static void next() {
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
	}
	
	static void advanceTime(int dt) {
		out.println(dt+" seconds later...");
		currentTime = new Date(currentTime.getTime()+1000*dt);
	}
	
	static void wait(int t) {
		while (t >= remainingTimeout) {
			int dt = remainingTimeout;
			t -= dt;
			advanceTime(dt);
			next();
		}
		remainingTimeout -= t;
		advanceTime(t);
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
				out.println("    help - show this help message");
				out.println("    exit - exit, obviously");
				out.println("    wait <seconds> - wait, obviously");
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
			
			out.println("Unrecognized command.");
		}
	}

}
