package phones;

import java.util.Enumeration;
import java.util.Hashtable;

public class SerializeUtils {

	// J2ME DON'T HAVE SORT
	// THIS IS A FUCKING SORT FROM THE FUCKING INTERNETS
	// SO IT'S LIKELY TO BE RETARDED AND PLAIN WRONG, BUT IDC
	private static void bubbleSort(String[] p_array) {
		boolean anyCellSorted;
		int length = p_array.length;
		String tmp;
		for (int i = length; --i >= 0;) {
			anyCellSorted = false;
			for (int j = 0; j < i; j++) {
				if (p_array[j].compareTo(p_array[j + 1]) > 0) {
					tmp = p_array[j];
					p_array[j] = p_array[j + 1];
					p_array[j + 1] = tmp;
					anyCellSorted = true;
				}
	
			}
			if (anyCellSorted == false) {
				return;
			}
		}
	}
	// IT'S TWO THOUSAND-TWELVE GODDAMMIT!
	// WHEN ARE THESE MORONS GONNA JOIN US IN THE 21ST CENTURY?

	static String[] getSortedDictKeys(Hashtable dict) {
		String[] keys = new String[dict.size()];
		int i = 0;
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			keys[i++] = (String) e.nextElement();
		}
		bubbleSort(keys);
		return keys;
	}

}
