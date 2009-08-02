package github;

import java.util.*;
import java.text.*;
import java.util.regex.*;

public class Languages {

	public String repo;
	public List<Language> languages;

	static final Pattern colon = Pattern.compile(":");
	static final Pattern comma = Pattern.compile(",");
	static final Pattern semicolon = Pattern.compile(";");

	public Languages() {
	}

	/**
	 * Parse the following format
	 *     57493:C;29382
	 *     73920:JavaScript;9759,ActionScript;12781
     *
	 * Assumes well formed input
	 * @returns filled Watch
	 **/
	public static Languages parse(String line) {
		Languages lang = new Languages();
		String[] data = colon.split(line, 0);
		lang.repo = data[0];
		lang.languages = new ArrayList<Language>();
		data = comma.split(data[1], 0);
		for (int i = 0; i < data.length; i++) {
			String[] l = semicolon.split(data[i], 0);
			lang.languages.add(new Language(l[0], Integer.parseInt(l[1])));
		}
		return lang;
	}

}