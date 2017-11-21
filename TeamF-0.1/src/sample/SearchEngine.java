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

    public static Vector<Node> SearchPath(String search, Map map, Node kiosk){


        Vector<Node> r = new Vector<>();


        if ( search !=  ""){
            for(int i = 0; i<map.getNodes().size();i++){
                if ((map.getNodes().get(i).getLongName().toLowerCase().contains(search.toLowerCase()))){
                    r.add(map.getNodes().get(i));
                }
            }
        }


        //Vector<Node> r = findMatches(search,map.getNodes());
/*
        for(int i=0;i<r.size();i++){
            System.out.println(i+": "+r.get(i).getLongName());
        }
        */

        Node MinNode = kiosk;
        Double MinDistance=1000000.0;

        for(int i =0; i<r.size();i++ ){
            Double CurDist = map.TotalDistance(map.AStar(kiosk,r.get(i)));
            if(MinDistance  > CurDist){
                MinDistance =CurDist;
                MinNode = r.get(i);
            }
        }

        return map.AStar(kiosk,MinNode);

        /*

        int editDistance = StringUtils.getLevenshteinDistance(searchName, NodeName);
        System.out.println();
        double score = (NodeName.length() - editDistance) / NodeName.length();

        if (searchName.charAt(0) != NodeName.charAt(0)) {
            score = Math.min(score, MAX_SCORE_FOR_NO_FIRST_LETTER_MATCH);
        }

        return Math.max(0.0, Math.min(score, 1.0));
        */
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


    scoreName

*/

}

