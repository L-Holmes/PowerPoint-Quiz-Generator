import java.util.stream.IntStream;

public class SortTree
{
    private int totalNumNodes = 0;
    private SortTreeNode rootNode = null;

    public SortTree(){
        //
    }

    public void addNode(int identifier, int weight)
    {
        SortTreeNode newNode;
        boolean foundPlace = false;
        SortTreeNode compareNode;
        int compareWeight;

        newNode = new SortTreeNode(identifier, weight);
        //if there is not root node, set this node as the root node
        if (rootNode == null){
            rootNode = newNode;
        }
        else{
            //
            compareNode = rootNode;
            while (foundPlace ==  false){
                //
                compareWeight = compareNode.getWeight();
                if (weight < compareWeight){
                    //move down less branch
                    if (compareNode.gotLeft() == true){
                        //then repeat with this node
                        compareNode = compareNode.getLeft();
                    }
                    else{
                        //set this Node as the left node
                        compareNode.setLeft(newNode);
                        newNode.setMother(compareNode);
                        

                        foundPlace = true;
                    }
                }
                else{
                    //move than greater than or equal to branch
                    if (compareNode.gotRight() == true){
                        //then repeat with this node
                        compareNode = compareNode.getRight();
                    }
                    else{
                        //set this Node as the right node
                        compareNode.setRight(newNode);
                        newNode.setMother(compareNode);

                       

                        foundPlace = true;
                    }
                }
            }
            
        }
        totalNumNodes++;
    }

    public int[] getIdentifierArraySortedWeightDescending()
    {

        int returnArrayPointer = 0;
        int[] returnArray = new int[totalNumNodes];

        //start at root node 
        //move down right pointer until null
        //add this node's identifier to list
        //
        SortTreeNode otherNode;
        int otherWeight;
        SortTreeNode currentNode;
        int currentWeight;

        if (rootNode != null){
            //
            currentNode = rootNode;
            
            //--find the most right node in the entire tree--
            currentNode = findRightmostNode(currentNode);
            //add this node's id to the array
            //since this node has the largest value
            returnArray[returnArrayPointer] = currentNode.getID();
            returnArrayPointer++;


            boolean foundRoot = false;
            //--find the rest of the nodes--
            while (returnArrayPointer < returnArray.length ){

                //--check if node has a left pointer--
                if (currentNode.gotLeft() == true){
                    currentNode = currentNode.getLeft();

                    //if has left node, find the rightmost value of the stem
                    if (currentNode.gotRight() == true){
                        currentNode = findRightmostNode(currentNode);
                    }

                    //add this node's id to the array
                    //since this node has the largest value
                    returnArray[returnArrayPointer] = currentNode.getID();
                    returnArrayPointer++;

                }
                else{
                    //hasn't got a left pointer

                    //--moves up the tree until it  finds a node that has not been added to the return array--
                    boolean foundUnaddedNode = false;
                    while (foundUnaddedNode == false)
                    {
                        if (currentNode.getMother() != null){
                            //then it is not the root node
                            currentNode = currentNode.getMother();
                            int currentID = currentNode.getID();
                            boolean contains = IntStream.of(returnArray).anyMatch(x -> x == currentID);
                            if (contains == false) {
                                //add this node's id to the array
                                //since this node has the largest value
                                returnArray[returnArrayPointer] = currentNode.getID();
                                returnArrayPointer++;
                                foundUnaddedNode = true;

                            }
                            
                        }
                        else{
                            //at the root node so need to go left!!!!!!!!!
                            foundUnaddedNode = true;
                            foundRoot = true;
                        }
                    }
                    
                }
            }

            
        }
        else{
            //there is no root node
            //error!!!
        }

        return returnArray;
    }

    /**
     * follows the right pointer down of the 
     * passed node until no more right pointers exist
     * so finds the rightmost stem of this brach
     * @param startNode
     * @return
     */
    public SortTreeNode findRightmostNode(SortTreeNode startNode)
    {
        SortTreeNode currentNode = startNode;
        boolean foundRightmost = false;

        //--find the right most node and add its id to the array
        while(foundRightmost == false){
            if (currentNode.gotRight() == true){
                //then move down the right branch
                currentNode = currentNode.getRight();
            }
            else{
                //found the right most node
                foundRightmost = true;
            }
        }
        return currentNode;
    }
}