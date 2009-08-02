package github;

import java.util.*;
import java.text.*;
import java.util.regex.*;

public class Repository implements Comparable<Repository> {

	public String id;
	public String name;
	public Date created;
	public String fork;
	public int followers;
	public List<Language> languages;

	static final DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

	static final Pattern colon = Pattern.compile(":");
	static final Pattern comma = Pattern.compile(",");

	public Repository() {
	}

	/**
	 * Parse one of the following formats
	 *   123339:amazingsyco/technicolor-networking,2008-11-22
     *   123340:netzpirat/radiant-scoped-admin-extension,2009-02-27,53611
     *
	 * Assumes well formed input
	 * @returns filled Repository
	 **/
	public static Repository parse(String line) throws ParseException {
		Repository repo = new Repository();
		String[] data = colon.split(line, 0);
		repo.id = data[0];
		data = comma.split(data[1], 0);
		repo.name = data[0];
		repo.created = df.parse(data[1]);
		if (data.length > 3) {
			repo.fork = data[2];
		}
		return repo;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(id).append(":");
		sb.append(name).append(",");
		sb.append(df.format(created)).append(",");
		sb.append(fork).append(",followers:");
		sb.append(followers);
		if (languages != null) {
			sb.append(",languages:" + languages.size());
		}
		return sb.toString();
	}

	public boolean equals(Repository other) {
		return new Integer(id).equals(new Integer(other.id));
	}

	public int compareTo(Repository other) {
		return new Integer(id).compareTo(new Integer(other.id));
	}

	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 **/
	public static class FollowerComparator implements Comparator<Repository> {

		public int compare(Repository one, Repository two) {
			return one.followers - two.followers;
		}

	}

}
