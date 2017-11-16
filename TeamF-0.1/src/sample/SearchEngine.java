package sample;

import java.util.HashMap;
import java.util.Vector;

public class SearchEngine {

    //Fields
    private Vector<Node> nodeInput;

    // Constructor

    public SearchEngine(Vector<Node> nodeInput) {
        this.nodeInput = nodeInput;
    }


    //Getters and Setters


    public Vector<Node> getNodeInput() {
        return nodeInput;
    }

    public void setNodeInput(Vector<Node> nodeInput) {
        this.nodeInput = nodeInput;
    }

    /**
     * This is the A* algorithm to find the most efficient path
     * <p>
     *   A* is designed so that it finds the most cost efficient path. Now with multiple floors based
     *   on elevators and stairs
     * </p>
     * @param   S  string
     * @return  ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */

    public Vector<Node> SearchPath(String S){

        Vector<Node> r = new Vector<>();

        if ( S !=  ""){
            for(int i = 0; i<nodeInput.size();i++){
                if ((nodeInput.get(i).getLongName().toLowerCase().contains(S.toLowerCase()))){
                    r.add(nodeInput.get(i));
                }
            }
        }

        return r;
    }

}

