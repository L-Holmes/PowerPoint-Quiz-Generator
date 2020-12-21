package buttons;


/**
 * This is represents a 2d shape which can detect whether it
 * has been clicked via passing the coordinates of a click
 * and determining whether the coordinates lie within [this] shape
 * @author Lindon Holmes
 */
public interface ClickableShape
{

    /**
     * method is called when a click is detected in the window that this ClickableShape is 
     * currently residing in. 
     * 
     * determines whether this shape was clicked, and responds accordingly if this shape was clicked
	 * 
     * @param clickedXCoordinate = the x coordinate of the pixel on screen that was clicked
	 * @param clickedYCoordinate = the y coordinate of the pixel on screen that was clicked
     */
    public void handleNewWindowClick(int clickedXCoordinate, int clickedYCoordinate);

    /**
     * checks if this shape was clicked
     * i.e. 
     *     determines whether the  coordinates of a point (i.e. a point clicked on screen)
	 *     intercept the area of this shape (acting as a button).
	 * 
     * @param clickedXCoordinate = the x coordinate of the pixel on screen that was clicked
	 * @param clickedYCoordinate = the y coordinate of the pixel on screen that was clicked
     * @return true, if this shape was clicked; false otherwise. 
     */
    public boolean isPointInsideThisShape(int clickedXCoordinate, int clickedYCoordinate, boolean adjustForMacCursorPositionDetectionIssue);

    /**
     * This is the called after a click on this shape has been detected. 
     * This methods starts the procedure that the developer would want to
     *  occur after this ClickableShape has been clicked
     */
    public abstract void activateClickedProcedure();
}