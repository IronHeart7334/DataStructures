package dataStructures.binarySearchTree;

/**
 *
 * @author Matt
 */
public class AVLTreeNode extends BinarySearchTreeNode {
    private int height;
    public AVLTreeNode(int value) {
        super(value);
        height = 0;
    }
    
    public static final int getHeight(AVLTreeNode root){
        int ret = -1;
        if(root != null){
            ret = root.height;
        }
        return ret;
    }
    
    public static final void updateHeight(AVLTreeNode root){
        if(root != null){
            root.height = Math.max(
                getHeight(root.left), 
                getHeight(root.right)
            ) + 1;
        }
    }
    
    /**
     * AVL tree: the heights of each node's subtrees differ by at most 1.
     * Keeps time complexity of tree operations closer to O(log(n))
     * rather than O(n)
     * 
     * Should cache height to avoid recalculating multiple times
     * 
     * @param root
     * @param value
     * @return 
     */
    public static final AVLTreeNode insertAVL(AVLTreeNode root, int value){
        AVLTreeNode newRoot = root;
        if(root == null){
            newRoot = new AVLTreeNode(value);
        } else if(root.value > value){
            root.left = insertAVL((AVLTreeNode) root.left, value);
            newRoot = rebalance(newRoot);
        } else if(root.value < value){
            root.right = insertAVL((AVLTreeNode) root.right, value);
            newRoot = rebalance(newRoot);
        } else {
            // duplicate value, do nothing
        }
        
        return newRoot;
    }
    
    public static final AVLTreeNode deleteAVL(AVLTreeNode root, int value){
        AVLTreeNode newRoot = root;
        if(root == null){
            // do nothing
        } else if(root.value > value){
            root.left = deleteAVL((AVLTreeNode) root.left, value); 
        } else if(root.value < value){
            root.right = deleteAVL((AVLTreeNode) root.right, value);
        } else if(root.left == null && root.right == null){
            // found, so delete
            newRoot = null;
        } else if(root.left != null && root.right != null){
            // two children
            /*
            Two children.
            Replace this root with either:
            1. the smallest node from its right subtree
            or
            2. the largest node from its left subtree
            */
            AVLTreeNode swapMe = (AVLTreeNode) BinarySearchTreeNode.findMax(root.left);
            newRoot.value = swapMe.value;
            newRoot.left = deleteAVL((AVLTreeNode) root.left, value);
        } else if(root.left != null){
            // replace root with its left child
            newRoot = (AVLTreeNode) root.left;
        } else if(root.right != null){
            newRoot = (AVLTreeNode) root.right;
        } else {
            throw new RuntimeException();
        }
        newRoot = rebalance(newRoot);
        
        return newRoot;
    }
    
    public static final int getBalance(BinarySearchTreeNode root){
        int left = -1;
        int right = -1;
        if(root != null){
            left = getHeight(root.left);
            right = getHeight(root.right);
        }
        return left - right;
    }
    
    /**
     * In class, the professor had us identify 2 "k nodes":
     * - first k node is the imbalanced node
     * - second k node is the next node in the path to the last inserted node
     * - if the last inserted value is between the two k nodes, double rotate
     * - else, single rotation
     * - if double rotation, third k node is the node after k-2 in the path to
     *   the last inserted node
     * 
     * @param root
     * @return 
     */
    public static final AVLTreeNode rebalance(AVLTreeNode root){
        AVLTreeNode newRoot = root;
        if(root != null){
            updateHeight(root);
            int balance = getHeight(root.left) - getHeight(root.right);
            if(balance > 1){ // left heavy
                if(getBalance(root.left) < 0){ // left heavy inner 
                    root.left = leftRotate((AVLTreeNode) root.left); // rotate child
                }
                newRoot = rightRotate(root); // rotate parent
            } else if(balance < -1){ // right heavy
                if(getBalance(root.right) > 0){ // right heavy outer
                    root.right = rightRotate((AVLTreeNode) root.right); // rotate child
                }
                newRoot = leftRotate(root); // rotate parent
            }
        }
        return newRoot;
    }
    
    public static final AVLTreeNode leftRotate(AVLTreeNode root){
        AVLTreeNode newRoot = root;
        if(root != null){
            newRoot = (AVLTreeNode) root.right;
            root.right = newRoot.left;
            newRoot.left = root;
            updateHeight(root);
            updateHeight(newRoot);
        }
        return newRoot;
    }
    
    /**
     *      (4)
     *    /     \
     *  (2)     (6)
     * /  \     / \
     *(1) (3) (5) (7) 
     * 
     * to
     * 
     *      (2)
     *    /     \
     *  (1)     (4)
     *          / \
     *        (3) (6)
     *            / \
     *          (5) (7)
     * 
     * @param root
     * @return 
     */
    public static final AVLTreeNode rightRotate(AVLTreeNode root){
        AVLTreeNode newRoot = root;
        if(root != null){
            newRoot = (AVLTreeNode) root.left;
            root.left = newRoot.right;
            newRoot.right = root;
            updateHeight(root);
            updateHeight(newRoot);
        }
        return newRoot;
    }
    
    public static void main(String[] args){
        AVLTreeNode root = null;
        for(int i = 0; i < 10; i++){
            root = insertAVL(root, i);
            System.out.println();
            inOrder(root, System.out::println);
            System.out.println("Height is " + getHeight(root));
        }
        System.out.println("Height is " + getHeight(root));
        for(int i = 0; i < 10; i++){
            root = deleteAVL(root, i);
            System.out.println();
            inOrder(root, System.out::println);
            System.out.println("Height is " + getHeight(root));
        }
    }
}
