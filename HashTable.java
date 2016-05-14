/**
 * @author Samuel Bernheim
 * @email bernheim@brandeis.edu
 *
 * This is the class code for a hash table which utilizes the cuckoo hashing technique.
 * */

public class HashTable {

    int size;
    private int numOfElements = 0;
    private Process[] hashTable;

    public HashTable(int tableSize) {
        this.size = tableSize;
        hashTable = new Process[size];
    }

    /**
     * Running Time --> O(1)
     * Generates the key based on the process ID using the first hash function
     * @param processID is the value for which a key will be generated
     * @return the associated key for the process
     */
    private int hOne(int processID) {
        final double A = (Math.sqrt(5)-1)/2;
        return (int) Math.floor(size/2*((processID*A)-Math.floor(processID*A)));
    }

    /**
     * Running Time --> O(1)
     * Generates the key based on the process ID using the second hash function
     * @param processID is the value for which a key with be generated
     * @return the associated key for the process
     */
    private int hTwo(int processID) {
        final double A = (Math.sqrt(5)-1)/2;
        return (int) Math.floor(size/2 + Math.floor(size/2*((processID*A)-Math.floor(processID*A))));
    }

    /**
     * Running Time --> O(1)
     * Gets the total number of elements the hash table can contain
     * @return an int representing the total number of elements the hash table can contain
     */
    public int totalCapcity() {
        return this.size;
    }

    /**
     * Running Time --> O(1)
     * Gets the number of elements which have been inserted into the hash table
     * @return an int representing the number of elements which have been inserted into the hash table
     */
    public int size() {
        return this.numOfElements;
    }

    /**
     * Running Time --> O(n)
     * Inserts an element into the hash table following the appropriate rules for cuckoo hashing
     * @param process is the element which will be inserted
     */
    public void insert(Process process){
        int detectCycle = -1;
        insert(process, hOne(process.getID()) , detectCycle);
        numOfElements++;
        performRehash(false);
    }

    /**
     * Running Time --> O(n)
     * Inserts an element into the hash table following the appropriate rules for cuckoo hashing
     * @param process is the element which will be inserted
     * @param hashedValue is the key of a hash function
     * @param detectCycle is used to determine if there is a cycle
     */
    public void insert(Process process, int hashedValue, int detectCycle){
        if(hashedValue==detectCycle){
            performRehash(true);
        }

        if(hashTable[hOne(process.getID())]==null){
            hashTable[hOne(process.getID())]=process;
        } else if (hashTable[hashedValue] == null){
            hashTable[hashedValue] = process;
        }  else if(hashTable[hashedValue].getID() != process.getID()){
            Process old = hashTable[hashedValue];
            hashTable[hashedValue] = process;
            detectCycle = hashedValue;
            insert(old, hTwo(old.getID()), detectCycle);
        }
    }

    /**
     * Running Time --> O(n)
     * Rehashes the hash table when the load factor becomes >= to .6
     */
    private void performRehash(boolean forced) {
        double loadFactor = (double) numOfElements / (double) size;

        if (loadFactor >= .6 || forced) {
            Process[] oldTable = hashTable;
            size *= 2;
            hashTable = new Process[size];
            numOfElements = 0;

            for (Process i : oldTable){
                if (i != null){
                    insert(i);
                }
            }
        }
    }

    /**
     * Running Time --> O(1)
     * Searches the hash table for a process object based on the ID of the process object
     * @param processID is the id of the process object being searched for
     * @return a process object whose ID is the id that was passed as a parameter or a "null" process object
     * */
    public Process search(int processID) {
        if (hashTable[hOne(processID)] != null && processID == hashTable[hOne(processID)].getID()) {
            return hashTable[hOne(processID)];
        } else if (hashTable[hTwo(processID)] != null && processID == hashTable[hTwo(processID)].getID()) {
            return hashTable[hTwo(processID)];
        } else {
            return new Process(-1,-1,"EMPTY");
        }
    }

    /**
     * Running Time --> O(1)
     * Deletes a specific value from the hash table
     * @param process is the value which is will be deleted from the hash table
     * */
    public void delete(Process process){
        if (hashTable[hOne(process.getID())] == process) {
            hashTable[hOne(process.getID())] = null;
        } else if (hashTable[hTwo(process.getID())] == process) {
            hashTable[hTwo(process.getID())] = null;
        }
    }

    /**
     * Running Time --> O(1)
     * Gets the value at a certain index of the hash table
     * @return the element at an index
     * */
    public Process getValAtIndex(int i){
        return hashTable[i];
    }
}