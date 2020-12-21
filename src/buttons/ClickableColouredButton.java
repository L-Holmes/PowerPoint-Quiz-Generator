package buttons;

import java.awt.Color;
import java.util.LinkedList;


/**
 * a button, with a solid colour, (can optionally have an outline & text)
 * if the clicked coordinates are found to be inside of it
 * @author Lindon Holmes
 */
public class ClickableColouredButton extends ClickableColouredRect
{
    LinkedList<SingleLineText> textForThisButton = new LinkedList<SingleLineText>();

    private boolean isClicked = false;   //determines whether the button is in the 'has just been clicked' state (true); or not (false)

    private Color clickedColour;         //this is the colour that the main body of this button will appear to be,
                                         //when the button 'isClicked'. i.e. the this button has just been clicked
                                        
    private Color hoveredOverColour;     //this is the colour that the main body of this button will appear to be, 
                                         //when the user hovers the mouse pointer over this button

    private int clickedCounter = 0;      //this counter will be used to record how long this button has been in the 'isClicked' state
                                         //via the clickedCounter representing the number of game loop cycles that have occurred since
                                         //'isClicked' was set to true

    private int maxClickCount;           //this is the number of game loop cycles, for which this button will remain 'isClicked', after 
                                         //first entering the isClicked = true state. i.e. this is the limit for the clickedCounter.

    public ClickableColouredButton(int xCoord, int yCoord, int widthOfRect, int heightOfRect, int[] colourRGBvalues, int[]  clickCol, int[]  hovCol, int maxCount) {
        super(xCoord, yCoord, widthOfRect, heightOfRect, colourRGBvalues);

        if (validColourArgument(clickCol)){
            clickedColour = new Color(clickCol[0], clickCol[1], clickCol[2]);
        }
        else{
            //default to black
            clickedColour = new Color(0,0,0);
        }

        if (validColourArgument(hovCol)){
            hoveredOverColour = new Color(hovCol[0], hovCol[1], hovCol[2]);
        }
        else{
            //default to black
            hoveredOverColour = new Color(0,0,0);
        }
        
        maxClickCount = maxCount;
    }

    /**
     * @param currentMouseXCoordinate = the current x-coordinate of the mouse pointer
     * @param currentMouseYCoordinate = the current y-coordinate of the mouse pointer
     * @return the appropriate colour (from this button's colour palette), that should be displayed
     *         in the current circumstance
     */
    public Color getDisplayColour(int currentMouseXCoordinate, int currentMouseYCoordinate)
    {
        if (isClicked == true){
            return clickedColour;
        }
        else if (this.isPointInsideThisShape(currentMouseXCoordinate, currentMouseYCoordinate) == true){
            //user is hovering over this button
            return hoveredOverColour;
        }
        else{
            return this.getColour();
        }
    }

    /**
     * updates the click counter if this button is in the 'clicked down' state. 
     * this allows the button to remain in its 'clicked down' state for as long as necessary,
     * and return to the 'not clicked' state when the necessary amount of clock cycles have passed
     */
    public void updateClickCounter()
    {
        if (this.isClicked){
            if (clickedCounter >= maxClickCount){
                isClicked = false;
                clickedCounter = 0;
            }
            else{
                clickedCounter++;
            }
        }
    }


    //---GETTERS AND SETTERS---

    /**
     * @return true if this button has just been clicked (is in the clicked down phase); false otherwise
     */
    public boolean isThisButttonClicked()
    {
        return isClicked;
    }

    /**
     * sets this button's clicked state to be the newValue 
     *  (i.e. true implies that this button has just been clicked, and is in the clicked down phase; false otherwise)
     * @param newValue = the new value of this button's clicked state
     */
    public void setButtonClickedState(boolean newValue)
    {
        isClicked = newValue;
    }

    /**
     * @return the colour of this button when it is in the clicked down (just been clicked) phase
     */
    public Color getClickedColour()
    {
        return clickedColour;
    }

    /**
     * sets this button's clicked colour to be the newValue
     * @param newValue = the new colour that this button will appear when it is in the clicked down / just been clicked phase
     */
    public void setClickedColour(Color newValue)
    {
        clickedColour = newValue;
    }

    /**
     * @return the colour of this button when the user is hovering over it with the mouse cursor
     */
    public Color getHoveredOverColour()
    {
        return hoveredOverColour;
    }

    /**
     * sets the colour that this button will appear to be, when the user is hovering over it with their mouse cursor
     * @param newValue = the new hovered over colour for this button
     */
    public void setHoveredOverColour(Color newValue)
    {
        hoveredOverColour = newValue;
    }

    /**
     * @return the number of game cycles that this button will appear to be in its 'clicked down' state for
     */
    public int getMaxClickDownCount()
    {
        return maxClickCount;
    }

    /**
     * sets the number of game cycles that this button will appear to be in its 'clicked down' state for
     * @param newValue = the new value num. cycles that this button will appear to be 'clicked'
     */
    public void setMaxClickDownCount(int newValue)
    {
        maxClickCount = newValue;
    }

    /**
     * adds the newLineOfText to be displayed on top of this button
     * @param newLineOfText = the new line of text, that is to be displayed onto this button
     */
    public void addText(SingleLineText newLineOfText)
    {
        textForThisButton.add(newLineOfText);
    }

    /**
     * @param indexToRemove = the index from within this button's text linked list, that will be removed (if valid)
     *                        if the value is -1, the last item in this button's linked list of text will be removed.
     */
    public void removeTextFromThisButton(int indexToRemove)
    {
        if ((textForThisButton.size()>0) && (indexToRemove < textForThisButton.size())&&(indexToRemove >= -1)){
            switch(indexToRemove){
                case -1 -> textForThisButton.removeLast();
                default -> textForThisButton.remove(indexToRemove);
            }
        }
    }

    /**
     * @return an array containing all of the lines of text on this button
     */
    public SingleLineText[] getText()
    {
        return textForThisButton.toArray(new SingleLineText[textForThisButton.size()]);
    }


}