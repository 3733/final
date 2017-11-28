package sample;

public class SearchQuery {

    //Fields
    private int SearchID;
    //private Time SearchTime;
    private String TermSearched;
    private boolean Stairs;
    private boolean Elevator;
    private String AddStop;

    //Constructor
    public SearchQuery(int searchID, /*Time searchTime,*/ String termSearched, boolean stairs, boolean elevator, String addStop) {
        SearchID = searchID;
        //SearchTime = searchTime;
        TermSearched = termSearched;
        Stairs = stairs;
        Elevator = elevator;
        AddStop = addStop;
    }

    // Getters and Setters
    public int getSearchID() {
        return SearchID;
    }

    public void setSearchID(int searchID) {
        SearchID = searchID;
    }
    /*
        public Time getSearchTime() {
            return SearchTime;
        }

        public void setSearchTime(Time searchTime) {
            SearchTime = searchTime;
        }
    */
    public String getTermSearched() {
        return TermSearched;
    }

    public void setTermSearched(String termSearched) {
        TermSearched = termSearched;
    }

    public boolean isStairs() {
        return Stairs;
    }

    public void setStairs(boolean stairs) {
        Stairs = stairs;
    }

    public boolean isElevator() {
        return Elevator;
    }

    public void setElevator(boolean elevator) {
        Elevator = elevator;
    }

    public String getAddStop() {
        return AddStop;
    }

    public void setAddStop(String addStop) {
        AddStop = addStop;
    }
}

