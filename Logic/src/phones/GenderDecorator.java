package phones;

import java.util.Date;

import phones.InteractionModel.MenuDescriptor.MenuItem;

// chooses appropriate form for messages like
// Я долж{ен/на}
public class GenderDecorator extends IdentityIMDecorator {

	public GenderDecorator(InteractionModel model) {
		super(model);
	}

	boolean isMale() {
		return ((IGender)innerModel).isMale();
	}

	static String fixGender(String s, boolean male) {
		String result = "";
		int pos = 0;
		int i;
		while (true) {
			i = s.indexOf('{', pos);

			if (i == -1) {
				result += s.substring(pos);
				break;
			}
			result += s.substring(pos, i);
			int i2 = s.indexOf('/', i);
			int i3 = s.indexOf('}', i2);
			if (i2 == -1 || i3 == -1) {
				result += "***INVALID GENDER CONSTRUCTION***";
				break;
			}
			if (male)
				result += s.substring(i + 1, i2);
			else
				result += s.substring(i2 + 1, i3);
			pos = i3 + 1;
		}
		return result;
	}
	
	String fixGender(String s) {
		return fixGender(s, isMale());
	}

	public Descriptor whatNext(int passedSecs, Date currentTime) {
		Descriptor d = super.whatNext(passedSecs, currentTime);
		if (d instanceof SleepDescriptor) {
			SleepDescriptor sd = (SleepDescriptor)d;
			sd.status = fixGender(sd.status);
			return sd;
		}
		else {
			MenuDescriptor md = (MenuDescriptor)d;
			md.menuHeader = fixGender(md.menuHeader);
			for (int i = 0; i < md.options.size(); i++) {
				MenuItem mi = (MenuItem)md.options.elementAt(i);
				mi.ItemName = fixGender(mi.ItemName);
			}
			return md;
		}
	}

}
