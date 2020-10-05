package fr.ubordeaux.miage.s7.poo.td1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

 public class Customer {

    private String firstname;
    private String lastname;
    private String streetAdress;
    private String mailAdress;
    private String phoneNumber;
    private int id;
    private static int nextId = 1;
    private static HashMap<Integer, String[] > customerHM = new HashMap<Integer, String[]>();

    public Customer(String firstname, String lastname, String streetAdress, String mailAdress, String phoneNumber) {
        id = nextId;
        nextId += 1;
        setIdentity(lastname, firstname);
        setStreetAdress(streetAdress);
        setMailAdress(mailAdress);
        setPhoneNumber(phoneNumber);
        System.out.println(String.format("You created the customer -> %s", this.toString()));
    }

    private boolean containsFirstnameLastname(String lastname, String firstname) {

        Iterator iterator = customerHM.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mapItem = (Map.Entry) iterator.next();
            String[] names = (String[]) mapItem.getValue();
            if (names[0] == lastname && names[1] == firstname)return true;
        }

        return false;
    }

    /**
     * check the identity formats
     * if true assigns firstname and lastname to the current instance of Customer
     * or throw an IllegalArgumentException
     * @param lastname
     * @param firstname
     */
    private void setIdentity(String lastname, String firstname){

        String regex = "([a-zA-Z]{2,30}\\s*)+";

        if (!containsFirstnameLastname(lastname, firstname)){

            boolean isTrue1 = isRegexTrue(lastname, regex);
            boolean isTrue2 = isRegexTrue(firstname, regex);

            if (isTrue1 && isTrue2){
                this.lastname = lastname;
                this.firstname = firstname;
                String[] val = {lastname, firstname};
                customerHM.put(id, val);
            }
            else{
                throw new IllegalArgumentException(String.format(
                        "Your identity is not valid : %s %s", firstname, lastname));}
            }
        else{
            throw new IllegalArgumentException(String.format(
                    "This customer already exists : %s %s", firstname, lastname));
        }
    }

     public void setFirstname(String firstname){

         String regex = "([a-zA-Z]{2,30}\\s*)+";

         if (!containsFirstnameLastname(this.lastname, firstname)) {

             boolean bool = isRegexTrue(firstname, regex);

             if (bool) {
                 this.firstname = firstname;
                 String[] val = {this.lastname, firstname};
                 customerHM.replace(this.id, val);
                 System.out.println(String.format(
                         "Changed customer firstname to %s", firstname));
             } else {
                 throw new IllegalArgumentException(String.format(
                         "This firstname is not valid : %s", firstname));
             }
         }
         else {
             throw new IllegalArgumentException(String.format(
                     "A customer with this identity already exists : %s %s", firstname, lastname));
         }
     }

     public void setLastname(String lastname){

         String regex = "([a-zA-Z]{2,30}\\s*)+";

         if (!containsFirstnameLastname(lastname, this.firstname)) {

             boolean bool = isRegexTrue(lastname, regex);

             if (bool) {
                 this.lastname = lastname;
                 String[] val = {lastname, this.firstname};
                 customerHM.replace(this.id, val);
                 System.out.println(String.format(
                         "Changed customer lastname to %s", lastname));
             } else {
                 throw new IllegalArgumentException(String.format(
                         "This lastname is not valid : %s", lastname));
             }
         }
         else {
             throw new IllegalArgumentException(String.format(
                     "A customer with this identity already exists : %s %s", this.firstname, lastname));
         }
     }

     public void setStreetAdress(String streetAdress){

        String regex = "[A-Za-z0-9'\\.\\-\\s\\,]";

        if (isRegexTrue(streetAdress, regex)){
            this.streetAdress = streetAdress;
        }
        else{
            throw new IllegalArgumentException(String.format("Your street adress is not valid : %s", streetAdress));
        }
    }

    /**
     * Check mail adress
     * if true assigns mailAdress to the current instance of Customer
     * else throw an IllegalArgumentException
     * @param mailAdress
     */
    public void setMailAdress(String mailAdress) {

        //copied from https://stackoverflow.com/questions/2762977/regular-expression-for-email-validation-in-java

        //String regex = "[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{2}";

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


        if (isRegexTrue(mailAdress, regex)){
            this.mailAdress = mailAdress;
        }
        else{
            throw new IllegalArgumentException(String.format("Your mail adress is not valid : %s", mailAdress));
        }
    }

     public void setPhoneNumber(String phoneNumber){

        String regex = "^((\\+)33|0)[1-9](\\d{2}){4}$";

        if (isRegexTrue(phoneNumber, regex)){
            this.phoneNumber = phoneNumber;
        }
        else{
            throw new IllegalArgumentException(String.format("Your phone number is not valid : %s", phoneNumber));
        }
    }

    private boolean isRegexTrue(String arg, String regex){

        boolean isTrue = false;

        Pattern pRegex = Pattern.compile(regex);
        Matcher matcher = pRegex.matcher(arg);
        isTrue = matcher.find();

        return isTrue;
    }

    @Override
    public String toString(){

        String personal = String.format("{id: %d, prénom: %s, nom: %s, adresse: %s, telephone: %s, " +
                        "adresse mail: %s}", this.id, this.firstname, this.lastname,
                this.streetAdress, this.phoneNumber, this.mailAdress);

        return personal;

    }

    }

