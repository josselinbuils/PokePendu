package com.example.pokependu;
import java.text.Collator;

public class Word {
	private String word;
	private char[] wordArray;
	private char[] foundLettersArray;
	private Collator collator; // Sert à comparer du texte sans prendre en compte la casse ni les accents
	
	public Word() {
		collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
	}
	
	public void addEntry(String str) {
		char[] entry = str.toCharArray();
		
		for (int i = 0; i < entry.length; i++) {
			for (int j = 0; j < wordArray.length; j++) {
				if (collator.compare(String.valueOf(entry[i]), String.valueOf(wordArray[j])) == 0) {
					foundLettersArray[j] = entry[i];
				}
			}
		}
	}
	
	public char[] getFoundLettersArray() {
		return foundLettersArray;
	}
	
	public char[] getWordArray() {
		return wordArray;
	}

	public boolean winTest() {
		boolean result = true;

		for (int i = 0; i < wordArray.length; i++) {
			if (collator.compare(String.valueOf(foundLettersArray[i]), String.valueOf(wordArray[i])) != 0) {
				result = false;
				break;
			}
		}
		
		return result;
	}

	public void selectWord(String str) {
		word = str.toUpperCase();
		wordArray = word.toCharArray();
		
		foundLettersArray = new char[wordArray.length];
		
		for (int i = 0; i < foundLettersArray.length; i++) {
			foundLettersArray[i] = '_';
		}
	}
	
	public boolean testWord(String str) {
		boolean result = false;

		if (str.length() == 1) {
			for (int i = 0; i < wordArray.length; i++) {
				if (collator.compare(str, String.valueOf(wordArray[i])) == 0) {
					result = true;
					break;
				}
			}
		} else {
			result = (collator.compare(str, word) == 0);
		}
		
		return result;
	}
}