package buttons;

import java.awt.Color;

/**
 * A rectangular shape, with a solid, single colour as its background
 * @author Lindon Holmes
 */
public class ColouredRectangleShape extends RectangleShape
{

    private Color shapeColour;         //is the colour of the main body of this rectangle

    
    private boolean hasBorder = false; //determines whether this rectangle has a border (true) or not (false)
    private int borderWidth;           //the width of this rectangle's border in pixels
    private Color borderColour;        //is the colour of the border

    /**
     * @param xCoord = the x coordinate of the top left corner of this rectangle 
     * @param yCoord = the y coordinate of the top left corner of this rectangle 
     * @param widthOfRect = the width (horizontal length), in pixels, of this rectangle 
     * @param heightOfRect = the height (vertical length), in pixels, of this rectangle 
     * @param colourRGBvalues = an RGB colour array in the form:
     *                               [int redValue, int greenValue, int blueValue]
     *                                  -where each value must be an integer from 0 to 255. 
     */
    public ColouredRectangleShape(int xCoord, int yCoord, int widthOfRect, int heightOfRect, int[] colourRGBvalues) {
        super(xCoord, yCoord, widthOfRect, heightOfRect);
        
        if (validColourArgument(colourRGBvalues)){
            shapeColour = new Color(colourRGBvalues[0], colourRGBvalues[1], colourRGBvalues[2]);
        }
        else{
            //default to black
            shapeColour = new Color(0,0,0);
        }
    }

    /**
     * @param colourArg = the array of RGB values relating to a colour
     * @return true if the colourArg is in the correct formatting for an RGB colour array:
     *      [int redValue, int greenValue, int blueValue]
     *          -where each value must be an integer from 0 to 255. 
     */
    private boolean validColourArgument(int[] colourArg)
    {
        if (colourArg.length == 3){
            for (int value : colourArg){
                if (!((value >= 0) && (value < 256))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @return the multiplier that would give the smallest possible border, when passed as a parameter to the 
     *         addBorder(double) method
     */
    private double getMinBorderMultiplier()
    {
        int minSideLen = Math.min(this.getWidth(),this.getHeight());
        if (minSideLen > 0){
            return 1/minSideLen;
        }
        return 0;
    }
    
    /**
     * adds a border to this rectangle
     * @param borderWidthAsPercentageOfRectShortestSide = an multiplier: (0 <= x <= 1), which represents the percentage of the 
     *                                                    shortest side taken up by the border
     *                                                    e.g. for a rectangle with width 50 and height 40, 
     *                                                         the height '40' would be the shortest side, so the border could have a possible
     *                                                         width of (min = 1, max = 40), with a multiplier of  (min = 0.025, max = 1)
     *                                                    note: 
     *                                                         -any multiplier smaller than the minimum, will be rounded up to the minimum value
     *                                                         -any multiplier larger than the maximum, will be rounded down to 1
     * @param borderColour = an RGB colour array in the form:
     *                               [int redValue, int greenValue, int blueValue]
     *                                  -where each value must be an integer from 0 to 255.                                                       
     */
    public void addBorder(double borderWidthAsPercentageOfRectShortestSide, int[] borderColour)
    {
        double minPercentMultiplier; //the multiplier that would give the smallest possible border
        
        minPercentMultiplier = getMinBorderMultiplier();

        //rounding invalid values
        if (borderWidthAsPercentageOfRectShortestSide > 1){
            borderWidthAsPercentageOfRectShortestSide = 1;
        }
        else if (borderWidthAsPercentageOfRectShortestSide < minPercentMultiplier){
            borderWidthAsPercentageOfRectShortestSide = minPercentMultiplier;
        }

        if (minPercentMultiplier != 0){
            //set the width
            borderWidth = (int) borderWidthAsPercentageOfRectShortestSide*Math.min(this.getWidth(),this.getHeight());

            //set the border's colour
            setBorderColour(borderColour);

            hasBorder = true;
        }
    }

    /**
     * adds a border to this rectangle
     * @param borderWidthInPixels = the width of the border in pixels.
     *                              must be in the range: 0 < borderWidthInPixels < (MIN(width, height) / 2)
     * @param borderColour = an RGB colour array in the form:
     *                               [int redValue, int greenValue, int blueValue]
     *                                  -where each value must be an integer from 0 to 255.     
     */
    public void addBorder(int borderWidthInPixels, int[] borderColour)
    {
        int minSideLength; //the shortest side length of this rectangle
        minSideLength =  Math.min(this.getWidth(),this.getHeight());

        //rounding invalid values
        if (borderWidthInPixels > minSideLength){
            borderWidthInPixels = minSideLength;
        }
        else if (borderWidthInPixels < minSideLength){
            borderWidthInPixels = 1;
        }
        
        //set the width
        borderWidth = borderWidthInPixels;

        //set the border's colour
        setBorderColour(borderColour);

        hasBorder = true;
    }

    /**
     * sets the border color to the borderColour if it is valid; sets to black otherwise
     * @param borderColour = an RGB colour array in the form:
     *                               [int redValue, int greenValue, int blueValue]
     *                                  -where each value must be an integer from 0 to 255.     
     */
    private void setBorderColour(int[] borderColour)
    {
        //set the border's colour
        if (validColourArgument(borderColour)){
            shapeColour = new Color(borderColour[0], borderColour[1], borderColour[2]);
        }
        else{
            //default to black
            shapeColour = new Color(0,0,0);
        }
    }


    //---GETTERS & SETTERS---
    /**
     * @return the colour of this shape
     */
    public Color getColour()
    {
        return shapeColour;
    }

    /**
     * @return the colour of the border of this shape, if it exists; null otherwise
     */
    public Color getBorderColour()
    {
        if (hasBorder){
            return borderColour;
        }
        else{
            return null;
        }
    }

    /**
     * @return the width of the border for this shape if it exists; 0 otherwise.
     */
    public int getBorderWidth()
    {
        if (hasBorder){
            return borderWidth;
        }
        else{
            return 0;
        }
        
    }
}