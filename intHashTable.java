/**
 * @author Samuel Bernheim
 * @email bernheim@brandeis.edu
 * */

public class intHashTable {

    int size;
    private int numOfElements = 0;
    private int[] hashTable;
    private int val;

    public intHashTable(int tableSize) {
        this.size = tableSize;
        hashTable = new int[size];
    }

    /**
     * Running Time --> O(1)
     * Generates the key based on the process ID using the first hash function
     * @param process is the value for which a key will be generated
     * @return the associated key for the process
     */
    private int hOne(int process) {
        return Math.abs(process % size/2);
    }

    /**
     * Running Time --> O(1)
     * Generates the key based on the process ID using the second hash function
     * @param process is the value for which a key with be generated
     * @return the associated key for the process
     */
    private int hTwo(int process) {
        return Math.abs(size/2+(process % size/2));
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
     * Running Time --> O(1)
     * Inserts an element into the hash table following the appropriate rules for cuckoo hashing
     * @param process is the element which will be inserted
     */
    public void insert(int process) {
        if (hashTable[hOne(process)] == 0) {
            hashTable[hOne(process)] = process;
            numOfElements++;
        } else if (hashTable[hTwo(hashTable[hOne(process)])] == 0) {
            hashTable[hTwo(process)] = hashTable[hOne(process)];
            hashTable[hOne(process)] = process;
            numOfElements++;
        } else {
            throw new IllegalStateException("Unresolved Collision");
        }

        performRehash();
    }

    /**
     * Running Time --> O(n)
     * Rehashes the hash table when the load factor becomes >= to .6
     */
    private void performRehash() {
        double loadFactor = (double) numOfElements / (double) size;

        if (loadFactor >= .6) {
            int[] oldTable = hashTable;
            size *= 2;
            hashTable = new int[2*size];
            numOfElements = 0;

            for (int i : oldTable){
                if (i != 0){
                    insert(i);
                }
            }
        }
    }

    /**
     * Running Time --> O(1)
     * Searches the hash table for a specific value
     * @param process is the value which is being searched for in the hash table
     * @return true or false if the value is found in the table or not
     */
    public boolean search(int process) {
        return hashTable[hOne(process)] == process || hashTable[hTwo(process)] == process;
    }

    /**
     * Running Time --> O(1)
     * Searches the hash table for a specific value
     * @param process is the value which is will be deleted from the hash table
     */
    public void delete(int process){
        if (hashTable[hOne(process)] == process) {
            hashTable[hOne(process)] = 0;
        } else if (hashTable[hTwo(process)] == process) {
            hashTable[hTwo(process)] = 0;
        }
    }
}