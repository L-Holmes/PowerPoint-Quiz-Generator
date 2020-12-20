package buttons;

/**
 * This is a solid coloured regular rectangle, which can be clicked via checking if a clicked point
 *  was inside this rectangle.
 * @author Lindon Holmes
 */
public class ClickableColouredRect extends ColouredRectangleShape implements ClickableShape
{

    /**
     * @param xCoord = the x coordinate of the top left corner of this rectangle 
     * @param yCoord = the y coordinate of the top left corner of this rectangle 
     * @param widthOfRect = the width (horizontal length), in pixels, of this rectangle 
     * @param heightOfRect = the height (vertical length), in pixels, of this rectangle 
     * @param colourRGBvalues = an RGB colour array in the form:
     *                               [int redValue, int greenValue, int blueValue]
     *                                  -where each value must be an integer from 0 to 255. 
     */
    public ClickableColouredRect(int xCoord, int yCoord, int widthOfRect, int heightOfRect, int[] colourRGBvalues) {
        super(xCoord, yCoord, widthOfRect, heightOfRect, colourRGBvalues);
    }

    //---CLICKABLE SHAPE METHODS---

    @Override
    public void handleNewWindowClick(int clickedXCoordinate, int clickedYCoordinate)
    {
        if (isPointInsideThisShape(clickedXCoordinate, clickedYCoordinate)){
            activateClickedProcedure();
        }
    }

    @Override
    public boolean isPointInsideThisShape(int clickedXCoordinate, int clickedYCoordinate)
    {
        if ( (clickedXCoordinate > this.getXCoordinate()) && (clickedXCoordinate < this.getRightXCoordinate()) ){
			//it is in the x range
			if ( (clickedYCoordinate < this.getBottomYCoordinate()) && (clickedYCoordinate > this.getYCoordinate()) ){
				//it has been clicked (since it is also in the y range)
				return true;
			}
		}
		return false;
    }

    @Override
    public void activateClickedProcedure()
    {
        //
    }
}