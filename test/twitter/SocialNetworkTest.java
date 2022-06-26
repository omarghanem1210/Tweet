/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2017-03-15T14:00:00Z");
    private static final Instant d4 = Instant.parse("2016-05-15T14:00:00Z");
    private static final Instant d5 = Instant.parse("2015-05-15T14:02:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "@mark is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "mark", "@Alyssa certainly it is reasonable as @guy will attest", d2);
    private static final Tweet tweet3 = new Tweet(3, "Guy", "This will end badly", d3);
    private static final Tweet tweet4 = new Tweet(4, "Girl", "@alyssa and @mark are arguing @Guy is ignoring them", d4);
    private static final Tweet tweet5 = new Tweet(5, "captain", "Will you stop commenting on everything @girl like @omar", d5);



    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphTwoTweets(){
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));
        assertEquals("map of length 3", 3, followsGraph.size());
        assertTrue("alyssa follows mark", followsGraph.get("alyssa").contains("mark"));
        assertTrue("mark follows alyssa and guy ", followsGraph.get("mark").containsAll(Arrays.asList("alyssa", "guy")));
        assertTrue("guy follows no one", followsGraph.get("guy").isEmpty());
    }

    @Test
    public void testGuessFollowsGraphMultipleTweets(){
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(
                tweet1, tweet2, tweet3, tweet4, tweet5));
        System.out.println(followsGraph.toString());
        assertEquals("map of length 6",6, followsGraph.size());
    }


    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersOne() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("expected list of length 2", 2, influencers.size());
        assertTrue("Expected list to contain mark", influencers.contains("mark"));
    }

    @Test
    public void testInfluencersMultiple() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(
                tweet1, tweet2, tweet3, tweet4, tweet5));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("expected list of length 6", 6, influencers.size());
        assertTrue("Expected number 1 to be mark or girl", Objects.equals(influencers.get(0), "mark") ||
                Objects.equals(influencers.get(0), "alyssa") || Objects.equals(influencers.get(0), "guy"));
        assertSame("Expected last to be captain", "captain", influencers.get(influencers.size()-1));


    }




        /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
