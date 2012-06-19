package phones;


public class Utils {

	public static void assert_(boolean condition, String msg) {
		if (!condition) {
			System.out.println(msg);
			throw new RuntimeException(msg);
		}
	}
	public static void assert_(boolean condition) {
		assert_(condition, "assert_");
	}
	
	public static String join(String sep, String[] items) {
		String result = "";
		for (int i = 0; i < items.length; i++) {
			if (i > 0)
				result += sep;
			result += items[i];
		}
		return result;
	}
	
	public static String[] split(String s, String sep) {
		int cnt = 1;
		int pos = 0;
		// counting pass
		while (true) {
			int newpos = s.indexOf(sep, pos);
			if (newpos == -1)
				break;
			pos = newpos + sep.length();
			cnt += 1;
		}
		
		int i = 0;
		String[] result = new String[cnt];
		pos = 0;
		while (true) {
			int newpos = s.indexOf(sep, pos);
			if (newpos == -1)
				break;
			result[i++] = s.substring(pos, newpos);
			pos = newpos + sep.length();
			cnt += 1;
		}
		result[i] = s.substring(pos, s.length());
		
		assert_(join(sep, result).equals(s));
		return result;
	}
}
