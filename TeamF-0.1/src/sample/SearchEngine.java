package sample;


import java.util.HashMap;
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
     * @return  ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */

    public static Node SearchClosestNode(String search) {

        HashMap<String,Vector<String>> TagMap = new HashMap<>();

        Vector<String> food = new Vector<>();
        food.add("au bon pain");
        food.add("restaurant");
        food.add("drinks");
        food.add("cafe");
        food.add("beverage");
        TagMap.put("food",food);

        Vector<String> drinks = new Vector<>();
        drinks.add("au bon pain");
        drinks.add("restaurant");
        drinks.add("food");
        drinks.add("cafe");
        drinks.add("beverage");
        TagMap.put("drinks",drinks);

        Vector<String> cafe = new Vector<>();
        cafe.add("au bon pain");
        cafe.add("restaurant");
        cafe.add("drinks");
        cafe.add("food");
        cafe.add("beverage");
        TagMap.put("cafe",cafe);

        Vector<String> beverage = new Vector<>();
        beverage.add("au bon pain");
        beverage.add("restaurant");
        beverage.add("drinks");
        beverage.add("cafe");
        beverage.add("food");
        TagMap.put("beverage",beverage);

        Vector<String> lab = new Vector<>();
        lab.add("laboratory");
        TagMap.put("lab",lab);

        Vector<String> laboratory = new Vector<>();
        laboratory.add("lab");
        TagMap.put("laboratory",laboratory);

        Vector<String> mri = new Vector<>();
        mri.add("magnetic resonance imaging");
        TagMap.put("mri",mri);

        Vector<String> catScan = new Vector<>();
        catScan.add("ct scan");
        catScan.add("computer tomography");
        TagMap.put("cat Scan",catScan);

        Vector<String> computerTomography = new Vector<>();
        computerTomography.add("ct scan");
        computerTomography.add("cat scan");
        TagMap.put("computer tomography",computerTomography);

        Vector<String> ctScan = new Vector<>();
        ctScan.add("cat scan");
        ctScan.add("computer tomography");
        TagMap.put("ct Scan",ctScan);

        Vector<String> legDoctor = new Vector<>();
        mri.add("podiatrist");
        TagMap.put("leg doctor",legDoctor);

        Vector<String> childDoctor = new Vector<>();
        mri.add("pediatrist");
        TagMap.put("child Doctor",childDoctor);

        Vector<String> police = new Vector<>();
        police.add("security");
        police.add("safety");
        TagMap.put("police",police);

        Vector<String> security = new Vector<>();
        security.add("police");
        security.add("safety");
        TagMap.put("security",security);

        Vector<String> safety = new Vector<>();
        safety.add("security");
        safety.add("police");
        TagMap.put("safety",safety);

        Vector<String> garage = new Vector<>();
        garage.add("parking");
        garage.add("parking garage");
        TagMap.put("garage",garage);

        Vector<String> parking = new Vector<>();
        parking.add("garage");
        parking.add("parking garage");
        TagMap.put("parking",parking);

        Vector<String> parkingGarage = new Vector<>();
        parkingGarage.add("garage");
        parkingGarage.add("parking");
        TagMap.put("parkingGarage",parkingGarage);

        Vector<String> wc = new Vector<>();
        wc.add("bathroom");
        wc.add("restroom");
        TagMap.put("wc",wc);

        Vector<String> bathroom = new Vector<>();
        bathroom.add("wc");
        bathroom.add("restroom");
        TagMap.put("bathroom",bathroom);

        Vector<String> restroom = new Vector<>();
        restroom.add("bathroom");
        restroom.add("wc");
        TagMap.put("restroom",restroom);

        Vector<String> cash = new Vector<>();
        cash.add("atm");
        TagMap.put("cash",cash);

        Vector<String> money = new Vector<>();
        money.add("atm");
        TagMap.put("money",money);

        Vector<String> egress = new Vector<>();
        egress.add("exit");
        TagMap.put("egress",egress);

        Vector<String> escape = new Vector<>();
        escape.add("exit");
        TagMap.put("escape",escape);

        Vector<String> wayOut = new Vector<>();
        wayOut.add("exit");
        TagMap.put("wayOut",wayOut);

        Vector<String> escalator = new Vector<>();
        escalator.add("stairs");
        TagMap.put("escalator",escalator);

        Vector<String> entry = new Vector<>();
        entry.add("entrance");
        TagMap.put("entry",entry);

        Vector<String> entryway = new Vector<>();
        entryway.add("entrance");
        TagMap.put("entryway",entryway);

        Vector<String> admission = new Vector<>();
        admission.add("entrance");
        TagMap.put("admission",admission);

        Vector<String> noEnglish = new Vector<>();
        noEnglish.add("international patient center");
        TagMap.put("no English",noEnglish);

        Vector<String> noAnglais = new Vector<>();
        noAnglais.add("international patient center");
        TagMap.put("no Anglais",noAnglais);

        Vector<String> noIngles = new Vector<>();
        noIngles.add("international patient center");
        TagMap.put("no Ingles",noIngles);



        boolean Cont = false;
        Vector<String> PossibleSearches = new Vector<>();
        PathAlgorithm pathFinder = new PathAlgorithm(new Dijkstras());

        for (String key : TagMap.keySet()) {
            if (key.toLowerCase().contains(search.trim().toLowerCase())){
                Cont= true;
                PossibleSearches = TagMap.get(key);
                break;
            }
        }
        if(Cont){
            Node curr ;
            Node minNode = null;
            double minDist = 100000000.0;
            double currNodeDist;
            Vector<Node> r = new Vector<>();
            for(String str :PossibleSearches){
                for (Node n: data.graph.getNodes()) {
                    if ((n.getLongName().toLowerCase().contains(str.toLowerCase()))){
                        r.add(n);
                    }
                }
            }
            for (Node n: r) {
                currNodeDist = data.graph.TotalDistance(pathFinder.executeStrategy(data.kiosk, n, data.graph));
                if(minDist>currNodeDist){
                    minDist=currNodeDist;
                    minNode = n;
                }
            }
            return minNode;
        }
        return getMaxNode(search);
    }


    public static Node getMaxNode(String search){

        double maxLav = 0;
        Node maxNode = data.graph.getNodes().firstElement();
        double currNodeDist = 0;
        double maxNodeDist = 0;
        double score;
        PathAlgorithm pathFinder = new PathAlgorithm(new Dijkstras());

        for (Node n: data.graph.getNodes()) {
            score = scoreAlg(search, n.getLongName());
            if(score > maxLav){
                if(data.kiosk != n) {
                    maxLav = score;
                    maxNode = n;
                }
            }else if (score == maxLav) {
                if(data.kiosk != n){
                    currNodeDist = data.graph.TotalDistance(pathFinder.executeStrategy(data.kiosk, n, data.graph));
                    maxNodeDist = data.graph.TotalDistance(pathFinder.executeStrategy(data.kiosk, maxNode,data.graph));
                    if (currNodeDist < maxNodeDist) {
                        maxNode = n;
                    }
                }
            }
        }

        return maxNode;
    }


    public static Node SearchNode(String search) {


        Vector<Node> r = data.graph.getNodes();

        double maxLav = 0;
        Node maxNode = null;

        for (int i = 0; i < r.size(); i++) {
            if (scoreAlg(search, r.get(i).getLongName()) > maxLav) {
                if(data.kiosk != r.get(i)) {
                    maxLav = scoreAlg(search, r.get(i).getLongName());
                    maxNode = r.get(i);
                }
            }
        }

        return maxNode;



    }


    public static Vector<Node> NodeToNode(Node EndNode,int CurrAlgo){

        Vector<Node> path = new Vector<>();

        switch (CurrAlgo){
            case 1:
                PathAlgorithm pathFinder1 = new PathAlgorithm(new Astar());
                path = pathFinder1.executeStrategy(data.kiosk,EndNode, Data.data.graph);
                break;
            case 2:
                PathAlgorithm pathFinder2 = new PathAlgorithm(new BFSearch());
                path = pathFinder2.executeStrategy(data.kiosk,EndNode, Data.data.graph);
                break;
            case 3:
                PathAlgorithm pathFinder3 = new PathAlgorithm(new DFSearch());
                path = pathFinder3.executeStrategy(data.kiosk,EndNode, Data.data.graph);
                break;
            case 4:
                PathAlgorithm pathFinder4 = new PathAlgorithm(new Dijkstras());
                path = pathFinder4.executeStrategy(data.kiosk,EndNode, Data.data.graph);
                break;
            case 5:
                PathAlgorithm pathFinder5 = new PathAlgorithm(new BeamFirstSearch());
                path = pathFinder5.executeStrategy(data.kiosk,EndNode, Data.data.graph);
                break;
            case 6:
                PathAlgorithm pathFinder6 = new PathAlgorithm(new BestFirstSearch());
                path = pathFinder6.executeStrategy(data.kiosk,EndNode, Data.data.graph);
                break;
        }

        return path;
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