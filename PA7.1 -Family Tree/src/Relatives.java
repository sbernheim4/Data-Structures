/**
 * @author Samuel Bernheim
 * This program accepts a data file as input where the first section consists of the names of each unique person
 * followed by the word END. After this END the data file lists a persons name followed by their mother followed by
 * their father with this pattern repeating until there is nothing left. If a mother or father is not known the word
 * unknown is written in its place. This program then takes that file, and prints out information on an individual of
 * the users choice. This information is printed in the format of a family tree applying proper indentation. First the
 * ancestry information is printed followed by information on the descendants of the person. Information on each person
 * is collected and each unique person has its own Person obj. All of the person objs are stored in an ArrayList.
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Relatives {

    public static void main(String[] args) throws FileNotFoundException {
        // scanner for the console
        Scanner console = new Scanner(System.in);

        // dataFile
        File dataFile = loadFile(console);

        // get the person's name whose lineage you want to know
        System.out.print("Person's name ('quit' to end')? ");
        String name = console.next();

        // ArrayList to store all unique people in the data file
        ArrayList<Person> personArrayList = processData(dataFile, name);

        // only run the remainder of the algorithm if the personArrayList is not null
        if(personArrayList != null){
            // fills in the fields for each person obj in the arrayList with their parent
            populateParents(dataFile, personArrayList);

            // gets all the children for person A and adds those children to the ArrayList field for the person A object
            populateChildren(personArrayList);

            // finds the index of the person whose info you want to know in the ArrayList and returns that Person which is then stored in the variable start
            Person start = personArrayList.get(getPersonFromArrayList(personArrayList, name));

            // prints the info for ancestors
            System.out.println("Ancestors:");
            printAncestors(start, 1);

            System.out.println("Descendants:");
            printDescendants(start, 1);
        }
    }

    /**
     * loadFile will ask the user for the name of the file, and will return the file
     * */
    public static File loadFile(Scanner console){
        console.useDelimiter("\n"); // Using a delimiter because otherwise .nextLine for getting the name of the person does not work

        System.out.print("What is the input file? ");
        String fileName = console.next();
        File dataFile = new File(fileName);
        return dataFile;
    }

    /**
     * Takes in the dataFile loaded from loadFile in addition to a name which is the name for which the user wants info
     *
     * If the name the user enters is quit then the method returns null.
     * If the user enters a name which is not
     * found in the first section of the data file (which lists all the unique people in the file) then the method
     * returns null
     * Else the program continues and creates a Person obj for each unique person adding them to an ArrayList
     *
     * The method returns this ArrayList
     * */

    public static ArrayList<Person> processData(File dataFile, String name) throws FileNotFoundException {

        ArrayList<Person> personArrayList = new ArrayList<Person>();

        if (name.equals("quit")) {
            return null;
        } else if (!nameExists(name, dataFile)) {
            System.out.println("Name does not exist.");
            return null;
        } else {
            // scanner for the file
            Scanner dataScanner = new Scanner(dataFile);
            // String which keeps track of the current person that is being processed
            String nameBeingProcessed = dataScanner.nextLine();
            // create a person obj for each person in the first section of the data file and store that person into an ArrayList
            while (dataScanner.hasNextLine() && !nameBeingProcessed.equals("END")){
                Person personOne = createPerson(nameBeingProcessed, dataFile);
                personArrayList.add(personOne);
                nameBeingProcessed = dataScanner.nextLine();
            }
        }
        return personArrayList;
    }

    /**
     * This method checks to make sure the name entered by the user is a person in the data file and returns a boolean
     * saying if the person exists or not. The person is identified by name which is passed as a parameter into the
     * method.
     * */
    public static boolean nameExists(String name, File dataFile) throws FileNotFoundException {
        Scanner dataScanner = new Scanner (dataFile);
        boolean exists = false;
        String line = dataScanner.nextLine();

        // only need to review the first section of the input data file because that section contains the names of everyone
        // the boolean is set to true if the name is found and otherwise is false and is returned.
        while (dataScanner.hasNextLine() && !line.equals("END") && !exists){
            if (line.equals(name)){
                exists = true;
            } else {
                line = dataScanner.nextLine();
            }
        }
        return exists;
    }

    /**
     * creates a new person obj for each name in the first section of the input data file
     * */
    public static Person createPerson(String name, File dataFile) throws FileNotFoundException {
        // bring the scanner to the beginning of the Second Section
        //.nextLine would produce the first name of the second section --> Arthur
        Scanner fileScanner = new Scanner(dataFile);
        String line = fileScanner.nextLine();
        while (fileScanner.hasNextLine() && !line.equals("END")){
            line = fileScanner.nextLine();
        }
        return new Person(name);
    }

    /**
     * For a specific name, this method locates that person obj within the personArrayList and returns the index of that
     * Person, in the personArrayList
     * */
    public static int getPersonFromArrayList(ArrayList<Person> personArrayList, String name){
        int i=0;
        for (Person person : personArrayList){
            if (person.getName().equals(name)){
                break;
            }
            i++;
        }
        return i;
    }

    /**
     * goes through the personArrayList adding a mother and father (if they exist) to each person obj in the AarrayList
     * */
    public static void populateParents(File dataFile, ArrayList<Person> personArrayList)throws FileNotFoundException {

        for (Person currentPerson : personArrayList){

            // brings the scanner down to the second section
            Scanner fileScanner =  new Scanner(dataFile);
            String line = fileScanner.nextLine();
            while (fileScanner.hasNextLine() && !line.equals("END")){
                line = fileScanner.nextLine();
            }

            // NOTE: This sets line to be the first name in the second section.
            line = fileScanner.nextLine();
            int counter = 0;

            // while there is another line to be read and the current line != END
            while (fileScanner.hasNextLine() && !line.equals("END")) {
                //if the scanner is on the name of the person whose info you want to get, and counter = 0

                //(this is because each name can appear multiple times and so only the names that are 0 mod 3 represent a
                //family member. Otherwise the name represents a mother or father)

                //set that person's mother and father fields as the next two names respectively a

                // otherwise skip to the next section of people aka to the next occurrence of 0 mod 3
                if (line.equals(currentPerson.getName()) && counter == 0) {
                    String motherName = fileScanner.nextLine();
                    String fatherName = fileScanner.nextLine();

                    // if the mother's name is not unknown assign it. otherwise the field will remain null
                    if (!motherName.equals("unknown") ) {
                        personArrayList.get(getPersonFromArrayList(personArrayList, line)).setMother(personArrayList.get(getPersonFromArrayList(personArrayList, motherName)));
                    }

                    // if the father's name is not unknown assign it. otherwise the field will remain null
                    if (!fatherName.equals("unknown") ) {
                        personArrayList.get(getPersonFromArrayList(personArrayList, line)).setFather(personArrayList.get(getPersonFromArrayList(personArrayList, fatherName)));
                    }

                    // move on to the next group / increase counter by 1 / set counter to itself mod 3
                    line = fileScanner.nextLine();
                    counter++;
                    counter %=3;
                } else {
                    // move down to the next grouping of three / THE REASON I DO NOT CHANGE COUNTER IS BECAUSE I WOULD
                    // ADD 3 BUT THAT WOULD NOT CHANGE THE VALUE OF COUNTER % 3 --> 1 % 3 = 1 and 1+3 = 4 and 4 % 3 = 1
                    fileScanner.nextLine();
                    fileScanner.nextLine();
                    line = fileScanner.nextLine();
                }
            }
        }
    }

    /**
     * Goes through each person in the personArrayList and then finds all of their children and adds those children to
     * the ArrayList field in the class
     * */
    public static void populateChildren(ArrayList<Person> personArrayList){
        for (Person currentPerson : personArrayList){
            for (int i = 0; i<personArrayList.size(); i++){
                // if the person at position i in the personArrayList has a mother or father whose name is equal to currentPerson
                // then the person at position i is the child of currentPerson.
                if(personArrayList.get(i).getMotherName().equals(currentPerson.getName()) || personArrayList.get(i).getFatherName().equals(currentPerson.getName())){
                    personArrayList.get(getPersonFromArrayList(personArrayList, currentPerson.getName())).addChild(personArrayList.get(i));
                }
            }
        }
    }

    /**
     * Prints all the ancestors of the given person by recursively calling this method on each new person that is found
     * */
    public static void printAncestors(Person startingPoint, int indentLevel){

        // print the right level of indentation, followed by the name
        for (int i=0; i < indentLevel; i++){
            System.out.print("    ");
        }
        System.out.println(startingPoint.getName());

        indentLevel++;

        // if the person does have a mother, make a recursive call on her
        if (startingPoint.getMother() != null){
            printAncestors(startingPoint.getMother(), indentLevel);
        }

        // if the person does have a father, make a recursive call on him
        if (startingPoint.getFather() != null){
            printAncestors(startingPoint.getFather(), indentLevel);
        }
    }

    /**
     * Prints all the descendants of the given person by recursively calling this method on each child of each child etc
     * */
    public static void printDescendants(Person startingPoint, int indentLevel) {
        // print the right level of indentation
        for (int i =0; i < indentLevel; i++){
            System.out.print("    ");
        }

        System.out.println(startingPoint.getName());
        indentLevel++;

        // for each child that startingPoint has, if that child has children, call printDescendants on that child.
        for (Person child : startingPoint.getChildren()){
            printDescendants(child, indentLevel);
        }
    }
}