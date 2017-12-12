package sample;

/**This is the public class food
 * Food is related to the service requests
 * @param String name, Double price, String fileLocation, boolean defaultFood
 */

public class Food {

    String name;
    Double price;
    String fileLocation;
    boolean defaultFood;

    public Food(String name, Double price, String fileLocation, boolean defaultFood) {
        this.name = name;
        this.price = price;
        this.fileLocation = fileLocation;
        this.defaultFood = defaultFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public boolean getDefaultFood() {return defaultFood; }

    public void setDefaultFood(boolean defaultFood) { this.defaultFood = defaultFood; }
}
