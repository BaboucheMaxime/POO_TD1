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

        private Product(String name, double price, int id){

            this.name = name;
            this.price = price;
            this.id = id;
            System.out.println("You updated the price of the product -> " + name);
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

    public void setPrice(Object instance, int id, double price) {

        if (!(instance instanceof Basket)){
            if (productHM.containsKey(id)) {
                String name = this.productHM.get(id).name;
                int quantity = this.quantityHM.get(id);

                this.productHM.remove(id);
                this.quantityHM.remove(id);

                Product newProduct = new Product(name, price, id);

                productHM.put(id, newProduct);
                quantityHM.put(id, quantity);
            }
            else {
                throw new IllegalArgumentException(String.format(
                        "Product id <%d> does not exist in catalog. Abort update", id));

            }}
        else {
            throw new IllegalCallerException("Cannot be accessed by a Basket Object");
        }
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

    public void inc(Object instance, int productID, int qty) {

        if (instance instanceof Basket) {
            int availableQty = quantityHM.get(productID);

            quantityHM.replace(productID, availableQty + qty);
        }
        else {
            throw new IllegalCallerException("Can only be accessed by a basket");
        }
    }

    public boolean dec(Object instance, int productID, int qty){

        if (instance instanceof Basket) {
            int availableQty = quantityHM.get(productID);

            if (availableQty - qty >= 0) {
                quantityHM.replace(productID, availableQty - qty);
                return true;
            }
            else {
                throw new IllegalArgumentException(String.format(
                        "Out of stock for %s. Requested %d but only %d available in stock."
                        , this.productHM.get(productID).name, qty, availableQty));
            }
        }
        else {
            throw new IllegalCallerException("Can only be accessed by a basket");
        }
    }

    public String getProductName(int id){

        return this.productHM.get(id).name;
    }

    public double getProductPrice(int id){

        return this.productHM.get(id).price;
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
            double price = product.price;
            int quantity = quantityHM.get(id);
            content.append("{id: ");
            content.append(id);
            content.append(", {produit: ");
            content.append(name);
            content.append(", {price: ");
            content.append(price);
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
