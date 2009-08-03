package github;

import java.util.*;

public class Language {

	public String name;
	public int lines;

	public Language(String name, int lines) {
		this.name = name;
		this.lines = lines;
	}

	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 **/
	public static class LinesComparator implements Comparator<Language> {

		public int compare(Language one, Language two) {
			return one.lines - two.lines;
		}

	}

}