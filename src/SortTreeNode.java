public class SortTreeNode
{
    private int identifier;
    private int weight;
    private SortTreeNode motherNode = null;
    private SortTreeNode leftPointer = null;
    private SortTreeNode rightPointer = null;

    public SortTreeNode(int ident, int w)
    {
        //
        identifier = ident;
        weight = w;
    }

    public int getWeight()
    {
        return weight;
    }

    public int getID()
    {
        return identifier;
    }

//LEFT STUFF
    public void setLeft(SortTreeNode newLeft)
    {
        leftPointer = newLeft;
    }

    public SortTreeNode getLeft()
    {
        return leftPointer;
    }

    public boolean gotLeft()
    {
        if (leftPointer != null){
            return true;
        }
        return false;
    }

//RIGHT STUFF
    public void setRight(SortTreeNode newRight)
    {
        rightPointer = newRight;
    }

    public SortTreeNode getRight()
    {
        return rightPointer;
    }

    public boolean gotRight()
    {
        if (rightPointer != null){
            return true;
        }
        return false;
    }

//MOTHER STUFF
    public void setMother(SortTreeNode newMother)
    {
        motherNode = newMother;
    }

    public SortTreeNode getMother()
    {
        return motherNode;
    }

    public boolean gotMother()
    {
        if (motherNode != null){
            return true;
        }
        return false;
    }

}