package sample;

import sample.Map;
import sample.Node;

import java.util.Vector;

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

    public static Vector<Node> SearchPath(String search, Map map, Node kiosk){

        Vector<Node> r = new Vector<>();

        if ( search !=  ""){
            for(int i = 0; i<map.getNodes().size();i++){
                if ((map.getNodes().get(i).getLongName().toLowerCase().contains(search.toLowerCase()))){
                    r.add(map.getNodes().get(i));
                }
            }
        }

        Node MinNode = kiosk;
        Double MinDistance=1000000.0;

        for(int i =0; i<r.size();i++ ){
            Double CurDist = map.TotalDistance(map.AStar(kiosk,r.get(i)));
            if(MinDistance  > CurDist){
                MinDistance =CurDist;
                MinNode = r.get(i);
            }
        }


        return map.Dijkstras(kiosk,MinNode);
    }




}

