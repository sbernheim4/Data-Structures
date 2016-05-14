/**
 * @author Samuel Bernheim
 */

public class MyNode<E> {
    private E data;
    public MyNode next;
    public MyNode prev;

    public MyNode(MyNode pred, E data, MyNode succ){
        this.prev = pred;
        this.data = data;
        this.next = succ;
    }

    public void setData(E data){
        this.data = data;
    }

    public E getData(){
        return this.data;
    }

    public String toString(){
        return ""+ data;
    }
}
