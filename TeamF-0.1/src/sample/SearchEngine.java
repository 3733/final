package sample;


import java.util.Vector;

import static sample.Data.data;

public class SearchEngine {


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

        if ( search !=  ""){
            search = search.toLowerCase();
            for(int i = 0; i<map.getNodes().size();i++){
                if ((map.getNodes().get(i).getLongName().toLowerCase().contains(search.toLowerCase()))){
                    K.add(map.getNodes().get(i));
                }
            }
        }
*/


/*
        Vector<Node> r = map.getNodes();

        double maxLav = 0;
        Node maxNode = r.firstElement();
        PathAlgorithm pathFinder = new PathAlgorithm(new Dijkstras());
        double bestD = Double.MAX_VALUE;

        for (int i = 0; i < r.size(); i++) {
            if (scoreAlg(search, r.get(i).getLongName()) > maxLav) {
                if(kiosk != r.get(i)) {
                    maxLav = scoreAlg(search, r.get(i).getLongName());
                    maxNode = r.get(i);
                    bestD = map.TotalDistance(pathFinder.executeStrategy(kiosk, maxNode,map));
                }
            }else if(scoreAlg(search, r.get(i).getLongName()) == maxLav){
                if(map.TotalDistance(pathFinder.executeStrategy(kiosk, r.get(i), map)) < bestD){
                    maxLav = scoreAlg(search, r.get(i).getLongName());
                    maxNode = r.get(i);
                    bestD = map.TotalDistance(pathFinder.executeStrategy(kiosk, maxNode,map));
                }
            }
        }

        return maxNode;

        */
/*
        Vector<Node> r = map.getNodes();

        double maxLav = 0;
        Node maxNode = null;

        for (int i = 0; i < r.size(); i++) {
            if (scoreAlg(search, r.get(i).getLongName()) > maxLav) {
                if(kiosk != r.get(i)) {
                    maxLav = scoreAlg(search, r.get(i).getLongName());
                    maxNode = r.get(i);
                }
            }
        }

        return maxNode;
*/


        double maxLav = 0;
        Node maxNode = map.getNodes().firstElement();
        double currNodeDist = 0;
        double maxNodeDist = 0;
        double score;
        PathAlgorithm pathFinder = new PathAlgorithm(new Dijkstras());

        for (Node n: map.getNodes()) {
            score = scoreAlg(search, n.getLongName());
            if(score > maxLav){
                if(kiosk != n) {
                    maxLav = score;
                    maxNode = n;
                }
            }else if (score == maxLav) {
                if(data.kiosk != n){
                    currNodeDist = map.TotalDistance(pathFinder.executeStrategy(data.kiosk, n, map));
                    maxNodeDist = map.TotalDistance(pathFinder.executeStrategy(data.kiosk, maxNode,map));
                    if (currNodeDist < maxNodeDist) {
                        maxNode = n;
                    }
                }
            }
        }

        return maxNode;

    }



    public static int scoreAlg(String search, String cmpStr) {
        int score = 0;
        search = search.toLowerCase().trim();
        cmpStr = cmpStr.toLowerCase().trim();

        if (search == "") {
            return 0;
        }

        if (search.equals(cmpStr)) {
            return Integer.MAX_VALUE;
        }

        if(search.length()<3){
            if(cmpStr.contains(search))
                return 1;
            return 0;
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