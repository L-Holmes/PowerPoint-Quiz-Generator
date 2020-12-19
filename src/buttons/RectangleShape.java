package buttons;

/**
 * Is a regular rectangler shape
 * i.e. has 4 sides, 4 vertices, 2 sets of parallel and equal-lengthed sides, 4 vertices with 90 degree internal angles
 * @author Lindon Holmes
 */
public abstract class RectangleShape
{
    private int x;       //the x coordinate of the top left corner of this rectangle shape
    private int y;       //the y coordinate of the top left corner of this rectangle shape
    private int width;   //the width (horizontal length), in pixels, of this rectangle shape
    private int height;  //the height (vertical length), in pixels, of this rectangle shape

    /**
     * @param xCoord = the x coordinate of the top left corner of this rectangle shape
     * @param yCoord = the y coordinate of the top left corner of this rectangle shape
     * @param widthOfRect = the width (horizontal length), in pixels, of this rectangle shape
     * @param heightOfRect = the height (vertical length), in pixels, of this rectangle shape
     */
    public RectangleShape(int xCoord, int yCoord, int widthOfRect, int heightOfRect)
    {
        x = xCoord;
        y = yCoord;
        width = widthOfRect;
        height = heightOfRect;
    }

    //--- ---

    /**
     * @return the y coordinate of the bottom of this shape
     */
    public int getBottomYCoordinate()
    {
        return this.getYCoordinate() + this.getHeight();
    }

    /**
     * @return the x coordinate of the right side of this shape
     */
    public int getRightXCoordinate()
    {
        return this.getXCoordinate() + this.getWidth();
    }

    //---GETTERS AND SETTERS---
    /**
     * @return the x-coordinate of the top left corner  of this rectangle
     */
    public int getXCoordinate()
    {
        return x;
    }

    /**
     * sets the x-coordinate of this rectangle to be the newValue
     * @param newValue = the new x coordinate of the top left corner of this rectangle
     */
    public void setXCoordinate(int newValue)
    {
        x = newValue;
    }

    /**
     * @return the y-coordinate of the top left corner of this rectangle
     */
    public int getYCoordinate()
    {
        return y;
    }

    /**
     * sets the y-coordinate of this rectangle to be the newValue
     * @param newValue = the new y coordinate of the top left corner of this rectangle
     */
    public void setYCoordinate(int newValue)
    {
        y = newValue;
    }

    /**
     * @return the width of this rectangle, in pixels
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * sets the width of this rectangle to be the newValue
     * @param newValue = the new width, in pixels, of this rectangle
     */
    public void setWitdh(int newValue)
    {
        width = newValue;
    }

    /**
     * @return the height in pixels of this rectangle
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * sets the height of this rectangle to be the newValue
     * @param newValue = the new height in pixels of this rectangle
     */
    public void setHeight(int newValue)
    {
        height = newValue;
    }
}