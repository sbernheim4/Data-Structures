public class Main {

    public static void main(String[] args) {
        LinkedListImplementation<Integer> test = new LinkedListImplementation<Integer>();

        for (int i = 0; i<50; i++){
            test.add(i);
        }


        // traverse through every element in the list from start to end
        MyNode currNode = test.head();
        while (currNode.next != null){
            currNode = currNode.next;
            System.out.println(currNode);
        }
    }
}
