package sample;

import org.apache.commons.lang3.StringUtils;
import sample.Map;
import sample.Node;

import java.util.Vector;



public class SearchEngine {

    public static final double MAX_SCORE_FOR_NO_FIRST_LETTER_MATCH = 0.3;
    public static final double MIN_SCORE = 0.3;

    /**
     * This is the SearchPath algorithm to find the most efficient path given String,denoting the destination of the user.
     * <p>
     *   SearchPath is designed so that it finds the most cost efficient path. Now with multiple floors based
     *   on elevators and stairs
     * </p>
     * @param   search  string
     * @param   map  Map
     * @param   kiosk  Node
     * @return  ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */

    public static Node SearchPath(String search, Map map, Node kiosk){


        search = search.toLowerCase();


        Vector<Node> r = new Vector<>();


        if ( search !=  ""){
            for(int i = 0; i<map.getNodes().size();i++){
                if ((map.getNodes().get(i).getLongName().toLowerCase().contains(search.toLowerCase()))){
                    r.add(map.getNodes().get(i));
                }
            }
        }

/*
        double maxLav = 0;
        Node maxNode = null;
        for(int i=0; i<map.getNodes().size();i++){

            if(scoreName(search,map.getNodes().get(i).getLongName().toLowerCase())>maxLav){
                maxLav = scoreName(search,map.getNodes().get(i).getLongName().toLowerCase());
                maxNode = map.getNodes().get(i);
            }
            System.out.println((i+1)+")  "+scoreName(search,map.getNodes().get(i).getLongName().toLowerCase()));
        }

        System.out.println(maxNode.getLongName());

        Vector<Node> r = findMatches(search,map.getNodes());

        for(int i=0;i<r.size();i++){
            System.out.println(i+": "+r.get(i).getLongName());
        }

        */


        Node MinNode = kiosk;
        Double MinDistance=1000000.0;

        for(int i =0; i<r.size();i++ ){
            Double CurDist = map.TotalDistance(map.Dijkstras(kiosk,r.get(i)));
            if(MinDistance  > CurDist){
                MinDistance =CurDist;
                MinNode = r.get(i);
            }
        }

        return MinNode;


    }


/*


    public static Vector<Node> findMatches(String searchLocation, Vector<Node> ListOfNodes) {

        Vector<Node> results = new Vector<>();

        for(int i = 0;i<ListOfNodes.size();i++) {

            double score = scoreName(searchLocation, ListOfNodes.get(i).getLongName());

            if (score > MIN_SCORE) {
                results.add(ListOfNodes.get(i));
            }
        }

        return results;
    }


    public static double scoreName(String searchName, String candidateName) {
        if (searchName.equals(candidateName)) return 1.0;

        double editDistance = computeLevenshteinDistance(searchName, candidateName);

        // Normalize for length:
        double score = (editDistance - ((double) candidateName.length()) / editDistance);
        //System.out.println(score);
        // Artificially reduce the score if the first letters don't match
        if (searchName.charAt(0) != candidateName.charAt(0)) {
            score = Math.min(score, MAX_SCORE_FOR_NO_FIRST_LETTER_MATCH);
        }

        // Try Soundex or other matching here.  Remember that you don't want
        // to go above 1.0, so you may want to create a second score and
        // return the higher.

        return Math.max(0.0, Math.min(score, 1.0));
    }


    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int computeLevenshteinDistance(String lhs,String rhs) {
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= lhs.length(); i++)
            for (int j = 1; j <= rhs.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

        return distance[lhs.length()][rhs.length()];
    }
*/

}