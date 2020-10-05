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
        containsFirstnameLastname(firstname, lastname);
        id = nextId;
        nextId += 1;
        checkIdentity(firstname, lastname);
        checkStreetAdress(streetAdress);
        checkMailAdress(mailAdress);
        checkPhoneNumber(phoneNumber);
        System.out.println(String.format("You created the customer -> %s", this.toString()));
    }

    private void containsFirstnameLastname(String firstname, String lastname) {

        Iterator iterator = customerHM.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mapItem = (Map.Entry) iterator.next();
            String[] names = (String[]) mapItem.getValue();
            if (names[0] == lastname && names[1] == firstname)
                throw new IllegalArgumentException(String.format("This customer already exists : %s %s",
                        firstname, lastname));

        }
    }

    /**
     * check the identity formats
     * if true assigns firstname and lastname to the current instance of Customer
     * or throw an IllegalArgumentException
     * @param firstname
     * @param lastname
     */
    private void checkIdentity(String firstname, String lastname){

        String regex = "([a-zA-Z]{2,30}\\s*)+";

        Pattern pRegex = Pattern.compile(regex);
        Matcher mFirstname = pRegex.matcher(firstname);
        Matcher mLastname = pRegex.matcher(lastname);
        boolean isTrue1 = mFirstname.find();
        boolean isTrue2 = mLastname.find();

        if (isTrue1 && isTrue2){
            this.firstname = firstname;
            this.lastname = lastname;
            String[] val = {lastname, firstname};
            customerHM.put(id, val);
        }
        else{
            throw new IllegalArgumentException(String.format("Your identity is not valid : %s %s", firstname, lastname));
        }
    }

    private void checkStreetAdress(String streetAdress){

        String regex = "[A-Za-z0-9'\\.\\-\\s\\,]";

        Pattern pRegex = Pattern.compile(regex);
        Matcher mAdress = pRegex.matcher(streetAdress);
        boolean isTrue = mAdress.find();

        if (isTrue==true){
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
    private void checkMailAdress(String mailAdress) {

        //copied from https://stackoverflow.com/questions/2762977/regular-expression-for-email-validation-in-java

        //String regex = "[a-zA-Z0-9]{1,}[@]{1}[a-z]{5,}[.]{1}+[a-z]{2}";

        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pRegex = Pattern.compile(regex);
        Matcher mMail = pRegex.matcher(mailAdress);
        boolean isTrue = mMail.find();

        if (isTrue==true){
            this.mailAdress = mailAdress;
        }
        else{
            throw new IllegalArgumentException(String.format("Your mail adress is not valid : %s", mailAdress));
        }
    }

    private void checkPhoneNumber(String phoneNumber){

        String regex = "^((\\+)33|0)[1-9](\\d{2}){4}$";

        Pattern pRegex = Pattern.compile(regex);
        Matcher mPhone = pRegex.matcher(phoneNumber);
        boolean isTrue = mPhone.find();

        if (isTrue==true){
            this.phoneNumber = phoneNumber;
        }
        else{
            throw new IllegalArgumentException(String.format("Your phone number is not valid : %s", phoneNumber));
        }
    }

    @Override
    public String toString(){

        String personal = String.format("{id: %d, prénom: %s, nom: %s, adresse: %s, telephone: %s, " +
                        "adresse mail: %s}", this.id, this.firstname, this.lastname,
                this.streetAdress, this.phoneNumber, this.mailAdress);

        return personal;

    }

    }

