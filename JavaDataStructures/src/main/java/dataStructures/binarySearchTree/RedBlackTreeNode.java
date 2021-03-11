package dataStructures.binarySearchTree;

/**
 * 1. Each node has a color
 * 2. Tree root node must be black
 * 3. Right child must be black
 *    Left can be either red or black
 * 
 * 4. Cannot have two red nodes in a row
 * 
 * 5. Black Balanced Condition:
 *    every path from the root to a null node must have exact same number of black
 *    nodes (color of null is black)
 * 
 * @author Matt
 */
public class RedBlackTreeNode extends BinarySearchTreeNode {
    /*
    True: node is black
    False: node is red
    */
    protected boolean isBlack;
    
    public RedBlackTreeNode(int value, boolean isBlack) {
        super(value);
        this.isBlack = isBlack;
    }

}