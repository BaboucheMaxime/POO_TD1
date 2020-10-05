package fr.ubordeaux.miage.s7.poo.td1;

public class Main {

    public static void main(String[] args) {

        Catalog catalog = Catalog.getInstance();
        Catalog catalog1 = Catalog.getInstance();

        //vérification singleton references
        if(catalog == catalog1){
            //System.out.println("Is singleton");
            System.out.println(catalog.hashCode()+ " -- " + catalog1.hashCode());
        }
        else {
            //System.out.println("Not singleton");
            System.out.println(catalog.hashCode()+ " -- " + catalog1.hashCode());
        }

        int produit1 = catalog.addProduct("Claquettes", 19.99D, 21);
        int produit2 = catalog.addProduct("Chaussures", 39.99D, 25);
        int produit3 = catalog.addProduct("whisky", 19.99D, 21);
        int produit4 = catalog.addProduct("biere", 39.99D, 25);

        //profil valide
        Customer customer = new Customer("Maxime", "Poudroux", "11 rue du hasard 33000 Bordeaux",
                "maxime.poudroux@hotmail.fr", "0684485115");
        Customer customer1 = new Customer("Christophe", "Poudroux", "11 rue du hasard 33000 Bordeaux",
                "maxime.poudroux@hotmail.fr", "0684485115");

        System.out.println(customer.toString());

        System.out.println("Catalogue before basket: " + catalog.toString());

        Basket panier = new Basket(catalog, customer);
        System.out.println("Basket: " + panier.toString());
        catalog.setPrice(catalog, produit1, 12d);
        panier.addOrder(produit1, 5);
        System.out.println("Catalogue after basket1: " + catalog.toString());
        System.out.println("Basket: " + panier.toString());

        panier.addOrder(produit1, 3);
        System.out.println("Catalogue after basket2: " + catalog.toString());
        System.out.println("Basket: " + panier.toString());

        catalog.setPrice(catalog, produit2, 21D);
        catalog.setPrice(catalog, produit1,39d);

        System.out.println("Catalogue after modifying price: " + catalog.toString());
        System.out.println(panier.toString());
        System.out.println(panier.getPrice());
        System.out.println(panier.getPrice(produit1));
    }
}
