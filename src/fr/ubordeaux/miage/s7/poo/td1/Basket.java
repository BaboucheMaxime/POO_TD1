package fr.ubordeaux.miage.s7.poo.td1;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Basket {

    private Catalog catalog;
    private Customer customer;
    private int id;
    private static int nextId =1;
    private HashMap<Integer, Integer> basketHM = new HashMap<Integer, Integer>();

    public Basket(Catalog catalog, Customer customer){
        this.catalog = catalog;
        this.customer = customer;
        id = nextId;
        nextId+=1;
    }

    /**
     * Checks if the product is not already in the basket and if the quantity ordered is available.
     * Insert a new or update a product to the basket with its productID and its quantity.
     * @param productID
     * @param quantity
     */
    public void addOrder(int productID, int quantity){

        if (!this.basketHM.containsKey(productID)) {

            if (this.catalog.dec(this, productID, quantity)) {

                this.basketHM.computeIfAbsent(productID, k -> quantity);
                System.out.println(String.format("You added %s and %d unit of it to this basket",
                        this.catalog.getProductName(productID), quantity));
            } else {
                throw new IllegalArgumentException(String.format("Quantity ordered for %s" +
                        " is higher than quantity available", this.catalog.getProductName(productID)));
            }
        }
        else {
            if (this.catalog.dec(this, productID, quantity)) {
                this.basketHM.computeIfPresent(productID,
                        (key, val) -> val + quantity);

                System.out.println(String.format("You added %d unit of %s to this basket",
                        quantity, this.catalog.getProductName(productID)));
            } else {
                throw new IllegalArgumentException(String.format("Quantity ordered for %s" +
                        " is higher than quantity available", this.catalog.getProductName(productID)));
            }

            //throw new IllegalArgumentException(String.format("Product %s already exist in this basket.",
            //        this.catalog.getProductName(productID)));
        }
    }

    public void decOrder(int productID, int quantity) throws IllegalAccessException {

        if (this.basketHM.containsKey(productID)) {
            int oldQty = this.basketHM.get(productID);
            int newQty = oldQty - quantity;
            if (newQty >= 0) {
                this.basketHM.replace(productID, newQty);
                this.catalog.inc(this, productID, quantity);
            } else {
                throw new IllegalArgumentException(String.format(
                        "Quantity to decrease for %s is higher than basket quantity.",
                        this.catalog.getProductName(productID)));
            }
        }
        else{
            throw new IllegalArgumentException(String.format(
                    "Product %s does not exist in this basket.",
                    this.catalog.getProductName(productID)));
            }
        }

    /**
     * Return the price of every product in this basket
     * @return
     */
    public String getPrice(){

        StringBuilder content = new StringBuilder("[");

        Iterator iterator = basketHM.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry mapItem = (Map.Entry) iterator.next();
            int mapKey = (int)mapItem.getKey();
            String name = catalog.getProductName(mapKey);
            double price = catalog.getProductPrice(mapKey);
            content.append("{id: ");
            content.append(mapKey);
            content.append(", {produit: ");
            content.append(name);
            content.append(", {prix: ");
            content.append(price);
            content.append("}}, ");
        }

        content.append("]");

        return content.toString();
    }

    /**
     * Return the price of the given product in this basket
     * @productId
     * @return
     */
    public double getPrice(int productId){

        if (basketHM.containsKey(productId)) {
            return this.catalog.getProductPrice(productId);
        }
        else {
            throw new IllegalArgumentException(String.format(
                    "The product id %d does not exist in this basket", productId));
        }

    }

    @Override
    public String toString(){

        StringBuilder content = new StringBuilder("[");

        Iterator iterator = basketHM.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry mapItem = (Map.Entry) iterator.next();
            int mapKey = (int)mapItem.getKey();
            String name = catalog.getProductName(mapKey);
            double price = catalog.getProductPrice(mapKey);
            int mapValue = (int)mapItem.getValue();
            content.append("{id: ");
            content.append(mapKey);
            content.append(", {produit: ");
            content.append(name);
            content.append(", {prix: ");
            content.append(price);
            content.append(", commande: ");
            content.append(mapValue);
            content.append("}}, ");
        }

        content.append("]");

        return content.toString();
    }
}