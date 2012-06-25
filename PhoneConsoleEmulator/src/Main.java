import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;

import alpha.AlphaIM;

import phones.GenderDecorator;
import phones.InteractionModel;
import phones.InteractionModelCheckDecorator;
import phones.StringSerializer;
import phones.Utils;
import phones.InteractionModel.*;


public class Main {

	private static final String ENCODING = "utf-8";//"utf-8" / "866"
	static BufferedReader in;
	static PrintStream out;

	static InteractionModel im;
	
	static SleepDescriptor sleepDescriptor;
	static MenuDescriptor menuDescriptor;
	static int remainingTimeout;
	
	static String prevStatus = null;
	static Date prevTime, currentTime;
	private static Date startTime;
	
	static void next() {
		while (true) {
			long dt = (currentTime.getTime()-prevTime.getTime())/1000;
			checkSerialize();
			Descriptor descriptor = im.whatNext((int)dt, currentTime);
			prevTime = currentTime;
			remainingTimeout = descriptor.timeout == -1 ? 100000 : descriptor.timeout;
			if (descriptor instanceof SleepDescriptor) {
				sleepDescriptor = (SleepDescriptor)descriptor;
				menuDescriptor = null;
				
				if (!sleepDescriptor.status.equals(prevStatus) || true) {
					out.println(getCurrentTime() + " STATUS: " + sleepDescriptor.status);
					prevStatus = sleepDescriptor.status;
				}
				
				if (remainingTimeout > 0)
					break;
				// TODO: signal sleep timeout
			} else {
				menuDescriptor = (MenuDescriptor)descriptor;
				sleepDescriptor = null;
				out.println(menuDescriptor.menuHeader);
				String[] names = menuDescriptor.getNames();
				for (int i = 0; i < names.length; i++)
					out.println("  " + (i + 1) + ". " + names[i]);
				out.println("(timeout: " + menuDescriptor.timeout + "s)");
				
				if (remainingTimeout > 0)
					break;
				signalMenuTimeout();
			}
		}
	}

	private static void checkSerialize() {
		StringSerializer ser = new StringSerializer();
		im.serialize(ser);
	}

	private static void signalMenuTimeout() {
		im.assertCommandWord(menuDescriptor.timeoutCommand);
	}
	
	static void advanceTime(int dt) {
		Utils.assert_(dt >= 0);
		if (dt == 0)
			return;
		// TODO: accumulate time and only print on status change or menu
		//out.println(dt+" seconds later...");
		currentTime = new Date(currentTime.getTime()+1000*dt);
	}
	
	static void wait(int t) {
		while (t >= remainingTimeout) {
			int dt = remainingTimeout;
			t -= dt;
			
			advanceTime(dt);
			
			// TODO: sleep descriptor timeout
			if (menuDescriptor != null)
				signalMenuTimeout();

			next();
		}
		
		remainingTimeout -= t;
		advanceTime(t);
		
		Utils.assert_(remainingTimeout > 0);
	}
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintStream(System.out, true, ENCODING);
		
		im = new GenderDecorator(new AlphaIM());
		im = new InteractionModelCheckDecorator(im);
		
		
		startTime = prevTime = currentTime = new Date(); 
		next();

		while (true) {
			out.print( ">>> ");
			String input = in.readLine();
			
			if (input.equals("exit"))
				break;
			
			if (input.equals("help")) {
				out.println("Commands:");
				out.println("    help");
				out.println("    exit");
				out.println("    wait <seconds>");
				out.println("    dump");
				continue;
			}
			
			if (input.startsWith("dump")) {
				
					StringSerializer ser = new StringSerializer();
					im.serialize(ser);
					ser.print();
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
			
			if (input.startsWith("hour")) {
				try {
					wait(60*60);
					continue;
				}
				catch (NumberFormatException e) {
				}
			}
			
			
			
			if (sleepDescriptor != null) {
				try {
					Integer.parseInt(input);
					switch (im.checkCommandWord(input)) {
					case InteractionModel.CODE_VALID:
						im.assertCommandWord(input);
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
			
			if (menuDescriptor != null) {
				try {
					int option = Integer.parseInt(input);
					if (option >= 1 && option <= menuDescriptor.getCount()) {
						im.assertCommandWord(menuDescriptor.getCommand(option-1));
						next();
						continue;
					}
					// FIXME: you can enter valid instead of menu option
				}
				catch (NumberFormatException e) {
				}
			}
			
			if (im.checkCommandWord(input) == InteractionModel.CODE_VALID)
			{
				im.assertCommandWord(input);
				next();
				continue;
			}
			
			out.println("Unrecognized command (" + input + ") Type 'help' for help.");
		}
	}

	private static long getCurrentTime() {
		return (currentTime.getTime() -  startTime.getTime())/1000;
	}

}
