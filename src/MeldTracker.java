import java.util.*;

public class MeldTracker {

    //Purpose: Convert a HashMap to a HashSet
    //Assumptions: None
    //Inputs: meldMap: HashMap containing the meld
    //Post-Conditions: New HashSet variable is created and returned
    HashSet<Card> toHashSet(HashMap<String, ArrayList<Card>> meldMap) {
        HashSet<Card> melds = new HashSet<>();
        for (Map.Entry<String, ArrayList<Card>> entry : meldMap.entrySet()) {                     //For each entry in HashMap
            melds.addAll(entry.getValue());
        }
        return melds;
    }


    //Purpose: Find the meld in as per requirement 4a
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns a HashMap containing a list for every set of meld
    HashSet<Card> findRankMeld(ArrayList<Card> hand) {
        HashMap<String, ArrayList<Card>> rankMeld = new HashMap<>();       //Create HashMap
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        HashMap<String, ArrayList<Card>> realMeld = new HashMap<>();
        for (String rank : ranks) {
            rankMeld.put(rank, new ArrayList<>());                     //Add each rank to count and create an empty array list
        }
        for (Card card : hand) {
            if (rankMeld.containsKey(card.getRank())) {
                rankMeld.get(card.getRank()).add(card);                //Add the card we have to the arrayList
            }
        }
        for (Map.Entry<String, ArrayList<Card>> entry : rankMeld.entrySet()) {                     //For each entry in HashMap
            if (entry.getValue().size() >= 3) {                                                    //If list has less than 3 cards then it is not meld, remove from hash map
                realMeld.put(entry.getKey(), entry.getValue());
            }
        }
        return toHashSet(realMeld);
    }


    //Purpose: Find the meld in as per requirement 4b
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns a HashSet containing the rank of the meld
    HashSet<Card> findRunMeld (ArrayList<Card> hand, HashSet<Card> melds) {
        ArrayList<Card> sortedByRank = new ArrayList<>(hand);
        sortedByRank.removeIf(melds::contains);
        sortedByRank.sort(Comparator.comparing(Card::getRankNumber));           //Sort hand by rank number so it is easier to find runs
        for (int i = 0; i < sortedByRank.size() - 3; i++) {
            Card first = sortedByRank.get(i);
            Card second = sortedByRank.get(i + 1);
            Card third = sortedByRank.get(i + 2);
            if ((first.getRankNumber() - second.getRankNumber() == -1) && (first.getRank().equals(second.getRank())) && (second.getRankNumber() - third.getRankNumber() == -1) && (second.getRank().equals(third.getRank()))) {
                melds.add(first);
                melds.add(second);
                melds.add(third);
            }
        }
        return melds;
    }


}
