/**
 * @author samuelbernheim
 * @email bernheim@brandeis.edu
 *
 * This is the class code for a Minimum Priority Queue. The MPQ accepts only integer values. The MPQ takes in a hash
 * table in the constructor and acts as a middleman to pass this hashtable to the heap where it will be used to get the
 * associated priorities given an ID.
 */
public class MinPriorityQueue {

    private int size = 0;
    private int maxCapacity;
    MinHeap myHeap;

    public MinPriorityQueue(int size, HashTable hashedProcesses){
        this.maxCapacity = size;
        myHeap = new MinHeap(size, hashedProcesses);
    }

    /**
     * Running Time --> O(log(n))
     * Enqueues or inserts a value into the min priority queue
     * @param val is the value which is enqueued into the min priority queue
     * */
    public void enqueue(int val){
        if (size < maxCapacity) {
            myHeap.insert(val);
            size++;
        } else {
            throw new IllegalStateException("The priority queue is full");
        }
    }

    /**
     * Running Time --> O(log(n))
     * Dequeues the value with the lowest priority from the min priority queue --> SAME AS EXTRACT MIN
     * @return an int which is the element with the lowest priority in the min priority queue
     * */
    public int dequeue(){
        if (size > 0) {
            size--;
            return myHeap.extractMin();
        } else {
            throw new IllegalStateException("The queue is empty");
        }
    }

    /**
     * Running Time --> O(1)
     * @return an int representing the number of elements in the min priority queue
     * */
    public int size(){
        return this.size;
    }

    /**
     * Running Time --> O(1)
     * @return a boolean which is true if the min priority queue has no elements
     * */
    public Boolean isEmpty(){
        return size == 1;
    }

}
