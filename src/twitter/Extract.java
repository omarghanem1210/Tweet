/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.size()==0){
            throw new IllegalArgumentException("Expected a non empty list");
        }
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        for (int i = 1; i < tweets.size(); i++){
            if (start.isAfter(tweets.get(i).getTimestamp())){
                start = tweets.get(i).getTimestamp();
            }
            else if (end.isBefore(tweets.get(i).getTimestamp())){
                end = tweets.get(i).getTimestamp();
            }
        }
        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Pattern pattern = Pattern.compile("(?<![a-z[0-9][._-]])@[^ ][a-z[0-9][._-]]*", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        Set <String> mentionedUsers = new HashSet<>();
        for (int i = 0; i < tweets.size(); i++){
            matcher = pattern.matcher(tweets.get(i).getText());
            while (matcher.find()){
                int start = matcher.start();
                int end = matcher.end();
                mentionedUsers.add(tweets.get(i).getText().substring(start+1, end).toLowerCase());
            }
        }
        return mentionedUsers;
    }

}
