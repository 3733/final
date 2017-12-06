package sample;

import org.apache.commons.lang3.StringUtils;
import sample.Map;
import sample.Node;

import java.util.Vector;



public class SearchEngine {

    public static final double MIN_SCORE = 0.7;

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

    public static Node SearchPath(String search, Map map, Node kiosk) {

/*
        Vector<Node> K = new Vector<>();
        if ( search !=  ""){
            search = search.toLowerCase();
            for(int i = 0; i<map.getNodes().size();i++){
                if ((map.getNodes().get(i).getLongName().toLowerCase().contains(search.toLowerCase()))){
                    K.add(map.getNodes().get(i));
                }
            }
        }
*/

        Vector<Node> r = map.getNodes();

        double maxLav = 0;
        Node maxNode = null;

        for (int i = 0; i < r.size(); i++) {
            if (computeDistance(search, r.get(i).getLongName()) > maxLav) {
                maxLav = computeDistance(search, r.get(i).getLongName());
                maxNode = r.get(i);
            }
        }

        return maxNode;

    }



    public static int computeDistance(String search,String cmpStr) {
        int score = 0;
        search = search.toLowerCase().trim();
        cmpStr = cmpStr.toLowerCase().trim();

        if (search == "") {
            return 0;
        }

        if (search.equals(cmpStr)) {
            return Integer.MAX_VALUE;
        }

        String sub;
        for (int i = 1; i < search.length() - 1; i++) {
            sub = "" + search.charAt(i - 1) + search.charAt(i) + search.charAt(i + 1);
            if (cmpStr.contains(sub)) {
                score++;
            }
        }

        for (String i : search.split(" ")) {
            if (cmpStr.contains(i)) {
                score += i.length()*i.length();
            }
        }



        return score;
    }

}