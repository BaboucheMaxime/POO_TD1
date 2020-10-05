package fr.ubordeaux.miage.s7.poo.td1;

public class Main {

    public static void main(String[] args) {

        Catalog catalog = Catalog.getInstance();
        Customer customer = new Customer("Maxime", "Poudroux", "11 rue du hasard 33000 Bordeaux",
                "maxime.poudroux@hotmail.fr", "0684085111");
        Customer customer1 = new Customer("Pierre", "Poudroux", "11 rue du random 33000 Bordeaux",
                "maxime.poudroux@hotmail.fr", "0684085111");
        Customer customer2 = new Customer("Pierre", "Domenach", "11 rue du aleatoire 33000 Bordeaux",
                        "maxime.poudroux@hotmail.fr", "0684085111");

        int produit1 = catalog.addProduct("Claquettes", 19.99D, 21);
        int produit2 = catalog.addProduct("Basket", 39.99D, 25);
        int produit3 = catalog.addProduct("Pantoufles", 9.99D, 18);

        Basket panier = new Basket(catalog, customer);
        panier.addOrder(produit1, 5);
        panier.addOrder(produit2, 9);
        panier.addOrder(produit3, 18);

    }
}
