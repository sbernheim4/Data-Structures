/**
 * @author Samuel Bernheim
 * This is the Person class associated with the client code, Relatives.java. Each unique person found in the input data
 * file is given their own Person obj. The person obj is used to store information about a person including their name,
 * mother, father, and an ArrayList of type Person which holds a person's children.
 **/

import java.util.*;

public class Person {
    private String name;
    private Person mother;
    private Person father;
    private ArrayList<Person> children = new ArrayList<Person>();


    // constructor for a person. Must be initialized with a name. All other fields are set to null.
    public Person(String name){
        this.name = name;
        this.mother = null;
        this.father = null;
    }

    public void setMother (Person mom){
        this.mother = mom;
    }

    public void setFather (Person dad){
        this.father = dad;
    }

    public void addChild (Person child){
        children.add(child);
    }

    public String getName() {
        return this.name;
    }

    public Person getMother(){
        return this.mother;
    }

    public Person getFather() {
        return this.father;
    }

    public ArrayList<Person> getChildren() {
        return this.children;
    }

    public String getMotherName(){
        if (hasMother()){
            return this.mother.getName();
        } else {
            return "unknown";
        }
    }

    public String getFatherName() {
        if (hasFather()){
            return this.father.getName();
        } else {
            return "unknown";
        }
    }

    public boolean hasMother(){
        if (getMother() == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean hasFather(){
        if (getFather() == null){
            return false;
        } else {
            return true;
        }
    }

}
