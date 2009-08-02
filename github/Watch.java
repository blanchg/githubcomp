package github;

import java.util.*;
import java.text.*;
import java.util.regex.*;

public class Watch {

	public String user;
	public String repo;


	static final Pattern colon = Pattern.compile(":");

	public Watch() {
	}

	/**
	 * Parse the following format
	 *   301:6979
     *
	 * Assumes well formed input
	 * @returns filled Watch
	 **/
	public static Watch parse(String line) {
		Watch watch = new Watch();
		String[] data = colon.split(line, 0);
		watch.user = data[0];
		watch.repo = data[1];
		return watch;
	}

}