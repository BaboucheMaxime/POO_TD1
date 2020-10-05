package fr.ubordeaux.miage.s7.poo.td1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Catalog {

    private static Catalog catalogInstance;
    private HashMap<Integer, Product> productHM = new HashMap<Integer, Product>();
    private HashMap<Integer, Integer> quantityHM = new HashMap<Integer, Integer>();
    private static int nextId =1;

    private class Product {

        private String name;
        private double price;
        private int id;

        private Product(String name, double price){

            this.name = name;
            this.price = price;
            id = nextId;
            nextId+=1;
            System.out.println("You created the product -> " + name);
        }
    }

    //singleton
    private Catalog(){

        if (catalogInstance != null){
            throw new RuntimeException("Prevented singleton reflection. Use getInstance() method to get or initialize" +
                    "the singleton instance.");
        }
    }

    /**
     * Create a singleton instance of Catalog
     */
    public static Catalog getInstance() {

        if (catalogInstance == null){
            catalogInstance = new Catalog();
        }

        return catalogInstance;
    }

    /**
     * Add a product to the catalog
     * @param name
     * @param price
     * @param quantity
     * @return the product id
     */
    public int addProduct(String name, double price, int quantity){

        int id;

        //check if Product already exists in Catalog by referencing its name
        if (!checkProductNameAlreadyExists(name)) {
            Product product = new Product(name, price);
            id = product.id;
            productHM.put(id, product);
            quantityHM.put(id, quantity);
        }
        else{
            throw new IllegalArgumentException(String.format(
                    "Product <%s> already exists in catalog. Abort insertion", name));
        }

        return id;
    }

    /**
     * Get a product instance by its id or throw a IllegalArgumentException if id does not exist
     * @param key id
     * @return
     */
    public Product getProductById(int key) {

        if (productHM.containsKey(key)) {
            return productHM.get(key);
        } else {
            throw new IllegalArgumentException("This product id does not exist.");
        }
    }

    /**
     * Check if available quantity is sufficient for requested quantity
     * @param productID
     * @param requestedQty quantity to add to a basket for exemple
     * @return
     */
    public boolean checkAvailableQuantity(int productID, int requestedQty) {

        if (productHM.containsKey(productID)){

            int availableQty = quantityHM.get(productID);

            if (availableQty >= requestedQty) {
                quantityHM.replace(productID, availableQty - requestedQty);
                return true;
            } else {
                return false;
            }
        }
        else {
            throw new IllegalArgumentException(String.format("Product ID n°%s does not exist in catalog.", productID));
        }
    }

    public String getProductName(int id){

        return this.productHM.get(id).name;
    }

    @Override
    public String toString(){

        StringBuilder content = new StringBuilder("[");

        Iterator iterator = productHM.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry mapItem = (Map.Entry) iterator.next();
            int id = (int)mapItem.getKey();
            Product product = (Product) mapItem.getValue();
            String name = product.name;
            int quantity = quantityHM.get(id);
            content.append("{id: ");
            content.append(id);
            content.append(", {produit: ");
            content.append(name);
            content.append(", stock: ");
            content.append(quantity);
            content.append("}}, ");
        }

        content.append("]");

        return content.toString();
    }

    private boolean checkProductNameAlreadyExists(String name){

        Iterator iterator = productHM.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry mapItem = (Map.Entry) iterator.next();
            Product mapValue = (Product)mapItem.getValue();
            String productName = mapValue.name;
            if (name == productName) return true;
        }

        return false;
    }
}
