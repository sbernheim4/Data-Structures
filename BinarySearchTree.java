import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Samuel Bernheim
 * @email bernheim@brandeis.edu
 * This is the class for a Binary Search AVL type tree which preserves the ordering of elements based on their values
 * and rotates nodes as needed after every insertion.
 */

public class BinarySearchTree<E extends Comparable<E>> {
    private E data;
    private BinarySearchTree<E> left;
    private BinarySearchTree<E> right;
    private BinarySearchTree<E> parent;

    public BinarySearchTree(E data) {
        this.data = data;
    }

    public BinarySearchTree() {

    }

    /**
     * Running Time --> O(1)
     * Determines if the node has a left node or not.
     * @return true or false
     */
    public boolean hasLeft (){
        return this.left != null;
    }

    /**
     * Running Time --> O(1)
     * Determines if the node has a right node or not.
     * @return true or false
     */
    public boolean hasRight(){
        return this.right != null;
    }

    /**
     * Running Time --> O(1)
     * Determines if the node is a leaf or not.
     * @return true or false
     */
    public boolean isLeaf(){
        return !(hasLeft() && hasRight());
    }

    /**
     * Running Time --> O(1)
     * Determines if the node contains data.
     * @return true or false
     */
    public boolean isEmpty(){
        return this.parent == null && this.right == null && this.left == null && this.data == null;
    }

    /**
     * Running Time --> O(1)
     * Determines if the node is the root or not.
     * @return true or false
     */
    public boolean isRoot(){
        return this.parent == null;
    }

    /**
     * Running Time --> O(1)
     * Determines if the node is the left node of its parent
     * @return true or false
     */
    public boolean isLeftChild(){
        return this.parent != null && this.parent.left == this;
    }

    /**
     * Running Time --> O(1)
     * Determines if the node is the right of its parent
     * @return true or false
     */
    public boolean isRightChild(){
        return this.parent != null && this.parent.right == this;
    }

    /**
     * Running Time --> O(1)
     * Determines if the node has a parent
     * @return true or false
     */
    public boolean hasParent(){
        return this.parent != null;
    }

    /**
     * Running Time --> O(log n)
     * Looks for the node which contains the data the user specifies
     * @param e the data the user is searching for
     * @return the node containing the data is returned and if it does not exist in the tree then a null node is
     * returned.
     */
    public BinarySearchTree<E> findNode(E e) {
        if (this.data.compareTo(e) == 0){
            return this;
        }

        if (this.data.compareTo(e) > 0 && hasLeft()){
            return this.left.findNode(e);
        } else if (this.data.compareTo(e) < 0 && hasRight()){
            return this.right.findNode(e);
        } else {
            return null;
        }
    }

    /**
     * Running Time O(log n)
     * A path from the root of the tree to its farthest left is followed to obtain the minimum value.
     * @return the node containing the minimum value is returned
     */
    public BinarySearchTree<E> findMin() {
        if (this.left == null){
            return this;
        } else {
            BinarySearchTree<E> temp = this.left;
            while (temp.hasLeft()){
                temp = temp.left;
            }
            return temp;
        }
    }

    /**
     * Running Time --> O(log n)
     * Finds the successor of a node
     * @return the successor of the node.
     */
    public BinarySearchTree<E> findSuccessor() {

        if (hasLeft() || hasRight()) {
            if (this.hasRight()) {
                return this.right.findMin();
            } else {
                BinarySearchTree<E> temp = this.left;
                while (temp.hasRight()){
                    temp = temp.right;
                }
                return temp;
            }
        } else {
            throw new NullPointerException("No successor exists. There is only one element in the tree");
        }

    }

    /**
     * Running Time --> O(log n)
     * Sets the root of the tree's data field equal to e if the tree is empty
     * @param e the data which will be contained in the new root of the tree
     * @return the binary search tree
     */
    public BinarySearchTree<E> addRoot(E e) {
        if(isEmpty() && isRoot()){
            this.data = e;
        } else {
            throw new NullPointerException("A root already exists");
        }
        return getRoot();
    }

    /**
     * Running Time --> O(log n)
     * Inserts a new node into the tree while maintaining the BST AVL properties.
     * @param e the node which will be inserted into the tree
     * @return the root of the tree
     */
    public BinarySearchTree<E> insert(E e) {

        // make sure the node the user wants to insert is not already in the tree
        if (findNode(e) != null){
            throw new IllegalStateException("Node is already in the tree");
        }

        BinarySearchTree<E> nodeToInsert = new BinarySearchTree<E>(e);
        BinarySearchTree<E> parent = null;
        BinarySearchTree<E> w = getRoot();

        // move down to find the parent of the new node
        while (w != null){
            parent = w;
            if (nodeToInsert.data.compareTo(w.data) < 0){
                w = w.left;
            } else {
                w = w.right;
            }
        }


        nodeToInsert.parent = parent;

        // insert the new node into the left or right subtree based on its value
        if (parent != null){
            if (nodeToInsert.data.compareTo(parent.data) < 0){
                parent.left = nodeToInsert;
            } else {
                parent.right = nodeToInsert;
            }
        }

        // find where the balance factor is not 0, 1 or -1
        BinarySearchTree<E> curr = nodeToInsert;
        while (curr != null && Math.abs(curr.balanceFactor()) < 2) {
            curr = curr.parent;
        }
        // balance that node whose balance factor is not 0, 1 or -1
        if (curr != null){
            curr.balance();
        }

        return getRoot();
    }

    /**
     * Running Time --> O(log n)
     * Calls findNode to search for the node containing the data the user is looking for
     * @param e the data a user is trying to find
     * @return the node containing the data the user wants to find or an empty BST
     */
    public BinarySearchTree<E> search(E e) {
        return findNode(e);
    }

    /**
     * Running Time --> O(log n)
     * Deletes a node from the tree while preserving the BST AVL properties
     * @param e the node to delete contains e as its data
     * @return the root of the binary search tree
     */
    public BinarySearchTree<E> delete(E e) {
        BinarySearchTree<E> nodeToDelete = findNode(e);

        // No Children
        if (!nodeToDelete.hasLeft() && !nodeToDelete.hasRight()){
            if (nodeToDelete.isLeftChild()){
                nodeToDelete.parent.left = null;

                // set the nodeToDelete field's all to null to completely remove it from the tree
                deleteNodeData(nodeToDelete);
            } else {
                nodeToDelete.parent.right = null;

                // set the nodeToDelete field's all to null to completely remove it from the tree
                deleteNodeData(nodeToDelete);
            }

            // Two Children
        } else if (nodeToDelete.hasLeft() && nodeToDelete.hasRight()) {
            BinarySearchTree<E> successor = nodeToDelete.findSuccessor();

            // based on if the successor is a right or left child, its parent's left or right field is set to null to
            // help detach the node
            if (successor.isRightChild()){
                successor.parent.right = null;
            } else {
                successor.parent.left = null;
            }

            //sets the successor's fields (left, right, parent) to be the fields of the node which will be deleted
            successor.parent = nodeToDelete.parent;
            successor.left = nodeToDelete.left;
            successor.right = nodeToDelete.right;

            if (nodeToDelete.isLeftChild()){
                successor.parent.left = successor;
            } else {
                successor.parent.right = successor;
            }

            // makes all the fields of the node which will be deleted null;
            deleteNodeData(nodeToDelete);

            // One Child
        } else {
            BinarySearchTree<E> successor = nodeToDelete.findSuccessor();

            // set the successor's parent left or right's field to null based on if successor is the left or right
            // node of its parent
            if (successor.isRightChild()){
                successor.parent.right = null;
            } else {
                successor.parent.left = null;
            }

            successor.parent = nodeToDelete.parent;

            //make the nodeToDelete parent's left or right point to the successor
            if (nodeToDelete.isRightChild()){
                nodeToDelete.parent.right = successor;
            } else {
                nodeToDelete.parent.left = successor;
            }

            // set the successor's left and right nodes to point to the nodeToDelete's left and right node
            successor.right = nodeToDelete.right;
            successor.left = nodeToDelete.left;

            // set the nodeToDelete field's all to null to completely remove it from the tree
            deleteNodeData(nodeToDelete);
        }
        return getRoot();

    }

    /**
     * Running Time --> O(log n)
     * Gives the number of nodes a tree has
     * @return an integer representing the number of nodes in the tree
     */
    public int size() {
        if (isEmpty()){
            return 0;
        } else if (hasLeft() && hasRight()){
            return 1 + this.left.size() + this.right.size();
        } else if(hasLeft() && !hasRight()){
            return 1 + this.left.size();
        } else if(!hasLeft() && hasRight()){
            return  1+this.right.size();
        } else {
            return 1;
        }
    }

    /**
     * Running Time --> O(log n)
     * Gets the balance factor of a node in the tree
     * @return an integer representing the balance factor of the node
     */
    public int balanceFactor() {

        if (this.hasLeft() && this.hasRight()){
            return this.left.height() - this.right.height();
        } else if (this.hasLeft() && !this.hasRight()){
            return this.left.height() + 1;
        } else if (!this.hasLeft() && this.hasRight()){
            return -1 - this.right.height();
        } else {
            return 0;
        }
    }

    /**
     * Running Time --> O(log n)
     * Gets the height of the tree
     * @return an integer representing the height of the tree
     */
    public int height(){
        if (this.hasLeft() && this.hasRight()) {
            return 1 + Math.max(this.left.height(), this.right.height());
        } else if (this.hasLeft() && !this.hasRight()){
            return 1 + this.left.height();
        } else if (!this.hasLeft() && this.hasRight()){
            return 1 + this.right.height();
        } else {
            return 0;
        }
    }


    /**
     * Running Time --> O(log n)
     * Gets the depth of the tree
     * @return an integer representing the depth of the binary search tree
     */
    public int depth() {
        if (this.hasParent()){
            return 1 + this.parent.depth();
        } else {
            return 0;
        }
    }

    /**
     * Running Time --> O(log n)
     * Balances the tree through single left, single right or a double rotation based on if the tree is right or left
     * heavy
     */
    public void balance() {
        if (this.balanceFactor() < 0){
            if (this.right.balanceFactor() > 0){
                this.right.rightRotation();
                this.leftRotation();
            } else {
                this.leftRotation();
            }
        } else if (this.balanceFactor() > 0){
            if (this.left.balanceFactor() < 0){
                this.left.leftRotation();
                this.rightRotation();
            } else {
                this.rightRotation();
            }
        }
    }

    /**
     * Running Time --> O(1)
     * Performs a right rotation on a tree
     */
    public void rightRotation() {
        BinarySearchTree<E> u = this.parent;
        BinarySearchTree<E> w = this.left;
        BinarySearchTree<E> x = w.right;

        w.right = this;
        this.parent = w;
        this.left = x;

        if (x!= null){
            x.parent = this;
        }


        if (u != null) {
            if (u.left == this) {
                u.left = w;
            } else {
                u.right = w;
            }
        }

        w.parent = u;
    }


    /**
     * Running Time --> O(1)
     * Performs a left rotation on a tree
     */
    public void leftRotation() {
        BinarySearchTree<E> u = this.parent;
        BinarySearchTree<E> v = this.right;
        BinarySearchTree<E> x = v.left;

        this.right = x;
        if (x!=null) {
            x.parent = this;
        }

        v.left = this;
        this.parent = v;

        if (u != null){
            if (u.right == this){
                u.right = v;
            } else {
                u.left = v;
            }
        }

        v.parent = u;
    }


    /**
     * Running Time --> O(n)
     * Returns the post order traversal of the tree as a string
     * @return a string representation of the data contained in each node in a postorder notation
     */
    public String postorder() {

        String left = "";
        String right = "";
        if (this.hasLeft()){
            left = this.left.postorder();
        }

        if (this.hasRight()){
            right = this.right.postorder();
        }
        return left + right + data.toString();
    }

    public void levelOrder(){
        Queue<BinarySearchTree> queue = new LinkedList<BinarySearchTree>();
        queue.add(this);
        while (!queue.isEmpty()){

            BinarySearchTree<E> temp = queue.remove();

            System.out.println("Level: " + temp.depth() + " Data: " + temp.data);

            if (temp.hasLeft()){
                queue.add(temp.left);
            }
            if(temp.hasRight()){
                queue.add(temp.right);
            }
        }
    }

    /**
     * Running Time --> O(1)
     * Returns the left child of the node
     * @return the left child of the node
     */
    public BinarySearchTree<E> left(){
        return this.left;
    }

    /**
     * Running Time --> O(1)
     * Returns the right child of the node
     * @return the right child of the node
     */
    public BinarySearchTree<E> right(){
        return this.right;
    }

    /**
     * Running Time --> O(1)
     * Returns the parent of the node
     * @return the parent of the node
     */
    public BinarySearchTree<E> parent(){
        return this.parent;
    }

    /**
     * Running Time --> O(1)
     * Gives the string representation of the data in the node
     * @return a string representation of the data in the node
     */
    public String data() {
        return this.data.toString();
    }

    /**
     * Running Time --> O(1)
     * This deletes all the information that a node contains
     * @param node the node whose data will be erased
     */
    private void deleteNodeData(BinarySearchTree<E> node){
        node.parent = null;
        node.left = null;
        node.right = null;
        node.data = null;
    }

    /**
     * Running Time --> O(log n)
     * Gets the root of the tree
     * @return the root of the tree
     */
    private BinarySearchTree<E> getRoot(){
        BinarySearchTree<E> curr = this;
        while (curr.hasParent()){
            curr = curr.parent;
        }
        return curr;
    }
}
