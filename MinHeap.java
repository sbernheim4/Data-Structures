/**
 * @author samuelbernheim
 * @email bernheim@brandeis.edu
 *
 * This is the class code for a Min Heap structure. It is implemented using an array stores only integers. It contains
 * a two hash tables as fields. One to ensure that only unique values are being added and another to use as reference to
 * find the priority of difference Process objects based on the ID of a process object.
 *
 */

import java.util.Arrays;

public class MinHeap {
    private int maxCapacity;
    private int length = 0;
    private int[] array; // integer array for IDs
    intHashTable addedValues; // values that have been added to the heap so far
    HashTable processes; // the hash table containing the process object



    //Constructor for the Min Heap
    public MinHeap(int size, HashTable hashedProcesses){
        this.maxCapacity = size;
        array = new int[maxCapacity];
        addedValues = new intHashTable(maxCapacity);
        this.processes = hashedProcesses;
    }

    /**
     * Running Time --> O(log(n))
     * Heapify Up is primarily used when a new value is inserted to maintain the heap properties
     * @param i is the index from which heapify up is first performed
     * */
    private void heapifyUp(int i){
        // while the current index has a parent and if the value at the current index is smaller than the parent, swap
        // the two values and then set the current index to its parent to keep checking if more swaps need to be made
        while (i>=0 && processes.search(array[i]).getPriority() < processes.search(array[parent(i)]).getPriority()) {
            int temp = array[i];
            array[i] = array[parent(i)];
            array[parent(i)] = temp;
            i = parent(i);
        }
    }
    /**
     * Running Time --> O(log(n))
     * Heapify down is used primarily when a value from the Heap is needed and the heap properties need to be maintained
     * @param i is the index where the first heapify down will take place */
    private void heapifyDown(int i){
        int leftChild = leftChild(i);
        int rightChild = rightChild(i);
        int smallest = i;

        int lChildPriority;
        int rChildPriority;
        int currPriority = processes.search(array[i]).getPriority();

        if (leftChild < length && leftChild >= 0) {

            // if the left child is in the array and the value at the left child is smaller than the value at the current
            // index the smaller value so far is the left child. Otherwise, the current index is smaller.
            lChildPriority = processes.search(array[leftChild]).getPriority();
            if (lChildPriority < currPriority) {
                smallest = leftChild;
            }
        }

        if (rightChild < length && rightChild >=0){
            rChildPriority = processes.search(array[rightChild]).getPriority();
            int smallestPriority = processes.search(array[smallest]).getPriority();

            // determine if the right child is smaller than what is currently being thought of as the smaller of the three
            // values (left child, current index, right child)
            if (rChildPriority < smallestPriority) {
                smallest = rightChild;
            }
        }

        // if the smallest value is different from the current index perform the swap between the smallest value and the
        // current index and now call heapify down on the swapped index value.
        if (smallest != i) {
            int temp = array[i];
            array[i] = array[smallest];
            array[smallest] = temp;
            heapifyDown(smallest);
        }
    }

    /**
     * Running Time --> O(log(n))
     * Inserts a value into the min heap if the value is not already in the min heap
     * @param val is the value to be inserted into the min heap
     * */
    public void insert(int val){
        // if val is not already in the heap then insert the value into the first open spot and call heapify up
        if(!addedValues.search(val)) {
            array[length] = val;
            heapifyUp(length);
            length++;
            addedValues.insert(val);
        }
    }

    /**
     * Running Time --> O(log(n))
     * Deletes the smallest value (always the first item in the array/the root of the tree) in the min heap
     * @return an int which is smallest value in the min heap
     * */
    public int extractMin(){
        if (length > 0) {
            int min = array[0];
            array[0] = array[length - 1];
            array[length-1] = 0;
            length--;
            heapifyDown(0);
            return min;
        } else {
            throw new IllegalStateException("The heap is empty.");
        }

    }

    /**
     * Running Time --> O(1)
     * Return the minimum value in the heap
     * @return the minimum value in the heap
     * */
    public int minimum(){
        return array[0];
    }

    /**
     * Running Time --> O(1)
     * @return an int representing the total number of elements the heap can hold
     * */
    public int getMaxCapacity(){
        return maxCapacity;
    }

    /**
     * Running Time --> O(1)
     * @return an int representing the number of elements currently in the heap
     * */
    public int size(){
        return length;
    }

    /**
     * Running Time --> O(1)
     * @param index is an index in the Min Heap
     * @return an int representing the index of the parent of the parameter passed in
     * */
    public int parent(int index){
        return (int) Math.floor((index-1)/2);
    }

    /**
     * Running Time --> O(1)
     * @param index is an index in the Min Heap
     * @return an int representing the left child of the index parameter
     * */
    public int leftChild(int index){
        return 2 * index + 1;
    }

    /**
     * Running Time --> O(1)
     * @param index is an index in the Min Heap
     * @return an int representing the right child of the index parameter
     * */
    public int rightChild(int index){
        return 2*index + 2;
    }

    /**
     * @return a String representation of the min heap
     * */
    public String toString(){
        return Arrays.toString(array);
    }

}
