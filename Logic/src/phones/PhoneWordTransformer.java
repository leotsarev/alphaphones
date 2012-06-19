package phones;

public final class PhoneWordTransformer {

	public static String transformToPhoneWord(String fixedWord) {
		StringBuffer buffer = new StringBuffer(fixedWord.toLowerCase());
		for (int i=0; i < fixedWord.length(); i++)
		{
			buffer.setCharAt(i, transformToPhoneDigit(buffer.charAt(i)));
		}
		String word = buffer.toString();
		return word;
	}

	private static char transformToPhoneDigit(char charAt) {
		switch (charAt) {
		case '1':
			return '1';
		case 'a':
		case 'b':
		case 'c':
		case '2':
			return '2';
		case 'd':
		case 'e':
		case 'f':
		case '3':
			return '3';
		case 'g':
		case 'h':
		case 'i':
		case '4':
			return '4';
		case 'j':
		case 'k':
		case 'l':
		case '5':
			return '5';
		case 'm':
		case 'n':
		case 'o':
		case '6':
			return '6';
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case '7':
			return '7';
		case 't':
		case 'u':
		case 'v':
		case '8':
			return '8';
		case 'w':
		case 'x':
		case 'y':
		case 'z':
		case '9':
			return '9';
		}
		return '0';
	}
}
