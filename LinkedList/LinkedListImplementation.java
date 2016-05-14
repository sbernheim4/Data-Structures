/**
 * @author Samuel Bernheim
 */

public class LinkedListImplementation<E> {
    private int size;
    private MyNode head = new MyNode(null,null,null);
    private MyNode tail = head;

    public LinkedListImplementation(){

    }

    // adding to the end of a list
    public void add(E data){
        linkAfter(data);
    }

    // add a node to a specific index
    public void add(int index, E data){
        if (index == 0 && size > 0) {
            linkBefore(data, head.next);
        } else if (index == 0 && size == 0){
            linkAfter(data);
        } else if (index < 0){
            throw new IllegalStateException();
        } else if (index == size){
            linkAfter(data);
        } else {
            int i = 0;
            MyNode curr = head;
            while (i < index){
                curr = curr.next;
                i++;
            }
            linkBefore(data, curr.next);
        }
    }

    private void linkAfter(E data){
        MyNode lastNode = tail;
        MyNode newNode = new MyNode(lastNode,data, null);
        tail = newNode;

        if (lastNode == null){
            head = newNode;
        } else {
            lastNode.next = newNode;
            newNode.prev = lastNode;
        }
        size++;
    }

    private void linkBefore(E data, MyNode succ){
        MyNode pred = succ.prev;
        MyNode newNode = new MyNode(pred,data,succ);
        succ.prev = newNode;

        if (pred == null){
            newNode.next = succ;
            succ.prev = newNode;
            pred.next = newNode;
            newNode.prev = pred;
            head = newNode;
        } else {
            pred.next = newNode;
            newNode.prev = pred;
            newNode.next = succ;
            succ.prev = newNode;
        }
        size++;
    }

    public Object remove(){
        return unLinkBefore();
    }

    public Object remove(int index){

        Object temp;
        if (index == size-1){
            temp = tail.getData();
            tail = tail.prev;
            tail.next = null;
        } else {
            return unlinkAfter(index);
        }
        return temp;
    }

    private Object unLinkBefore (){
        head.next.prev = null;
        MyNode origFirst = head;
        head = head.next;
        origFirst.next = null;

        size--;
        return origFirst.getData();

    }

    private Object unlinkAfter (int index){
        int i = 0;
        MyNode curr = head;
        while (i < index+1){
            curr = curr.next;
            i++;
        }
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        curr.next = null;
        curr.prev = null;
        size--;
        return curr.getData();
    }


    public int size(){
        return this.size;
    }

    public void printInfo(){
        MyNode curr = head.next;
        System.out.print("[");
        while (curr.next != null){
            System.out.print(curr.getData() + ", ");
            curr = curr.next;
        }

        System.out.println(curr.getData()+ "]");
    }

    public MyNode head(){
        return this.head;
    }

    public MyNode tail(){
        return this.tail;
    }

}
