package github;

import java.util.*;
import java.text.*;

public class User implements Comparable<User> {

	public String id;
	public List<Repository> watching;

	public User(String id) {
		this.id = id;
		watching = new ArrayList<Repository>();
	}

	public void watch(Repository repo) {
		repo.followers++;
		watching.add(repo);
	}

	public static String parse(String line) {
		return line;
	}

	public boolean equals(User other) {
		return id.equals(other.id);
	}

	public int compareTo(User other) {
		return new Integer(id).compareTo(new Integer(other.id));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(id).append(":");
		sb.append("watching:");
		sb.append(watching.size());
		return sb.toString();
	}

	public String getWatches() {
		StringBuffer sb = new StringBuffer();
		Iterator<Repository> it = watching.iterator();
		int i = 0;
		while (it.hasNext()) {
			Repository repo = it.next();
			if (i > 0) {
				sb.append(",");
			}
			sb.append(repo.id);
			i++;
		}
		return sb.toString();
	}


	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 **/
	public static class WatchCountComparator implements Comparator<User> {

		public int compare(User one, User two) {
			return one.watching.size() - two.watching.size();
		}

	}



}