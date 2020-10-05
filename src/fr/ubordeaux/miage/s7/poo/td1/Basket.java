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

    private boolean containsProduct(int productID){
        if (basketHM.containsKey(productID)){
            return true;
        }
        return false;
    }

    /**
     * Checks if the product is not already in the basket and if the quantity ordered is available.
     * Insert a new or update a product to the basket with its productID and its quantity.
     * @param productID
     * @param quantity
     */
    public void addOrder(int productID, int quantity){

        if (!containsProduct(productID)) {

            boolean isQtyAvailable = this.catalog.checkAvailableQuantity(productID, quantity);

            if (isQtyAvailable) {
                basketHM.computeIfPresent(productID,
                        (key, val) -> val + quantity);

                basketHM.computeIfAbsent(productID, k -> quantity);
                System.out.println(String.format("You added %d unit of %s to this basket",
                        quantity, this.catalog.getProductName(productID)));
            } else {
                throw new IllegalArgumentException(String.format("Quantity ordered for %s" +
                        " is higher than quantity available", this.catalog.getProductName(productID)));
            }
        }
        else {
            throw new IllegalArgumentException(String.format("Product %s already exist in this basket.",
                    this.catalog.getProductName(productID)));
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
            int mapValue = (int)mapItem.getValue();
            content.append("{id: ");
            content.append(id);
            content.append(", {produit: ");
            content.append(name);
            content.append(", commande: ");
            content.append(mapValue);
            content.append("}}, ");
        }

        content.append("]");

        return content.toString();

    }


}
