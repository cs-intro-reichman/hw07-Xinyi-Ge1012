public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if(str.length() == 0){
			return "";
		} else {
			return str.substring(1);
		}
	}

	public static int levenshtein(String word1, String word2) {
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		int dis = 0;
		int length1 = word1.length();
		int length2 = word2.length();
		char head1 = word1.charAt(0);
		char head2 = word2.charAt(0);

		if(length1 == 0){
			dis = length2;
		} else if(length2 == 0){
			dis = length1;
		} else if(head1 == head2){
			dis = levenshtein(tail(word1), tail(word2));
		} else {
			dis = 1 + Math.min(levenshtein(tail(word1), word2), Math.min(levenshtein(word1, tail(word2)), levenshtein(tail(word1), tail(word2))));
		}
        return dis;
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < dictionary.length; i++) {
			dictionary[i] = in.readString();
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		int[] levenshteins = new int[dictionary.length];
		int min = 0;
		for (int i = 0; i < dictionary.length; i++) {
			levenshteins[i] = levenshtein(word, dictionary[i]);
		}
		for (int i = 0; i < dictionary.length; i++) {
			if (levenshteins[i] < levenshteins[min]) {
				min = i;
			}
		}
		if (levenshteins[min] <= threshold) {
			return dictionary[min];
		}

		return word;
	
	}

}
