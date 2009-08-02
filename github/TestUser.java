package github;

import java.util.*;
import java.text.*;
import java.util.regex.*;

public class TestUser extends User {

	static int TOTAL_SUGGEST = 10;
	static final Pattern colon = Pattern.compile(":");

	public int remaining;
	public List<Repository> suggested;
	public int bestScore;
	public User bestUser;
	public List<User> alternateUser;

	public TestUser(String id) {
		super(id);
		this.remaining = TOTAL_SUGGEST;
		this.suggested = new ArrayList<Repository>();
		this.alternateUser = new ArrayList<User>();
	}

	public TestUser(User user) {
		this(user.id);
		this.watching = user.watching;
	}


	public void suggest(Repository repo) {
		remaining--;
		suggested.add(repo);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append(":");
		sb.append("suggested:");
		sb.append(suggested.size());
		return sb.toString();
	}

	public String getSuggestions() {
		StringBuffer sb = new StringBuffer();
		Iterator<Repository> it = suggested.iterator();
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

	public String getAlternates() {
		StringBuffer sb = new StringBuffer();
		Iterator<User> it = alternateUser.iterator();
		int i = 0;
		while (it.hasNext()) {
			User user = it.next();
			sb.append(":");
			sb.append(user.id);
			i++;
		}
		return sb.toString();
	}

	public int similarTo(User other) {
		// try to find a user that is most like this one
		/*if (watching.size() < Recommend.CONFIDENCE || other.watching.size() < Recommend.CONFIDENCE) { // Our confidence factor will be low or zero
			if (watching.size() < Recommend.CONFIDENCE) { // We can't profile this user inidicate to caller by returning -1
				return -1;
			} else {
				// We can't profile against other user
				return 0;
			}
		} else {*/
			// Get 1 point for every project that is the same
			int score = 0;
			Iterator<Repository> otherIt = other.watching.iterator();
			Iterator<Repository> it = watching.iterator();
			Repository otherRepo = null;
			Repository repo = null;
			otherRepo = otherIt.hasNext()?otherIt.next():null;
			repo = it.hasNext()?it.next():null;
			while(repo != null && otherRepo != null) {
				int comp = repo.compareTo(otherRepo);
				if (comp == 0) {
					score++;
					repo = it.hasNext()?it.next():null;
					otherRepo = otherIt.hasNext()?otherIt.next():null;
				} else if (comp < 1) {
					repo = it.hasNext()?it.next():null;
				} else {
					otherRepo = otherIt.hasNext()?otherIt.next():null;
				}
			}
			//System.out.println(score);
			return score;
		//}
	}

	/**
	 * Parse
	 * testUser:bestUser:score
	 **/
	public static TestUser parseProfile(String line, Recommend rec) {
		String[] data = colon.split(line, 0);
		User baseUser = rec.users.get(data[0]);
		TestUser testUser = null;
		if (baseUser == null) {
			//Shouldn't happen!
			testUser = new TestUser(data[0]);
		} else {
			testUser = new TestUser(baseUser);
		}
		if (data.length > 1) {
			testUser.bestUser = rec.users.get(data[1]);
			testUser.bestScore = Integer.parseInt(data[2]);
		}
		for (int i = 3; i < data.length; i++) {
			User user = rec.users.get(data[i]);
			testUser.alternateUser.add(user);
		}
		return testUser;
	}

}