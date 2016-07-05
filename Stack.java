/*
 * @author Samuel Bernheim
 * @email bernheim@brandeis.edu
 *
 * This is a class which represents a normal stack. All operations of push and pop are under last in first out (LIFO)
 * operations. Methods within the stack are isEmpty, push, pop, size and toString. The stack is implemented using an
 * array since all operations can be performed in constant time.
 */


import java.util.Arrays;

public class Stack<T> {

    /**
     * When a Stack is created, top is set to -1 because it is initially empty and the default constructor is used.
     */
    // top is used to keep track of the index of the most recent element that was pushed into the array
    private int top = -1;
    // this array is used as the stack
    private int totalCapacity = 100;


    /**
     * If the user chooses the default constructor can be overridden with the following constructor which allows the
     * user to control the maximum size of the stack. Otherwise the default constructor is used.
     */
    public Stack (int maxCapacity) {
        this.totalCapacity = maxCapacity;
    }

    /**
     * The default constructor.
     */
    public Stack () {
    }

    private T[] array = (T[]) new Object[totalCapacity];


    /**
     * Running Time --> O(1) since this method contains only if and return statements
     * A method which lets the user know if the stack is empty
     * @return true if the stack is empty or false if it is not.
     */
    public boolean isEmpty() {
        if (top == -1){
            return true;
        }
        return false;
    }

    /**
     * Running Time --> O(1)
     * Adds an element to the proper place in the stack if there is room
     * @param x is the element to be inserted
     */
    public void push(T x){
        if (top + 1 < array.length){
            array[top+1] = x;
            top++;
        } else {
            throw new IndexOutOfBoundsException("The Stack is Full");
        }
    }

    /**
     * Running Time --> O(1)
     * A method which will remove an element from the stack based on LIFO ordering.
     */
    public T pop(){
        if (!isEmpty()) {
            T temp = array[top];
            top--;
            return temp;
        } else {
            throw new IndexOutOfBoundsException("No Elements Left to Pop");
        }
    }

    /**
     * Running Time --> O(1)
     * @return the size of the stack.
     */
    public int size(){
        return top+1;
    }

    /**
     * Running Time --> O(1)
     * @return A string representation of the stack.
     */
    public String toString(){
        return Arrays.toString(array);
    }

}

