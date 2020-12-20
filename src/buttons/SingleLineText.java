package buttons;

import java.awt.*;//used for layout managers

/**
 * This is a single line of text, that contains the information necessary to be drawn via 
 *  a system using Graphics2D.
 * @author Lindon Holmes
 */
public class SingleLineText
{
    String textString;       //the actual text being displayed by this object
    int xCoordinate;         //the x coordinate of the centre of this string
    int yCoordinate;         //the y coordinate of the centre of this string
    Font textFont;           //the font that this text uses
    Color textColour;        //the colour of this text  


    public SingleLineText(String textToDisplay, int centreX, int centreY, Font fontOfText, Color colourOfText)
    {
        textString  = textToDisplay;
        xCoordinate = centreX;
        yCoordinate = centreY;
        textFont    = fontOfText;
        textColour  = colourOfText;
    }

    //---GETTERS AND SETTERS---

    /**
     * sets this text's x to the new value
     * @param newValue = the new text for this object 
     */
    public void setText(String newValue)
    {
        textString = newValue;
    }

    /**
     * @return the text string value for this piece of text
     */
    public String getText()
    {
        return textString;
    }

    /**
     * sets this text's centre x-coordinate to the new value
     * @param newValue = the new x-coordinate for the centre position of this text
     */
    public void setXCoordinate(int newValue)
    {
        xCoordinate = newValue;
    }

    /**
     * @return the x-coordinate value at the centre of this piece of text
     */
    public int getX()
    {
        return xCoordinate;
    }

    /**
     * sets this text's centre y-coordinate to the new value
     * @param newValue = the new y-coordinate for the centre position of this text
     */
    public void setYCoordinate(int newValue)
    {
        yCoordinate = newValue;
    }

    /**
     * @return the y-coordinate value at the centre of this piece of text
     */
    public int getY()
    {
        return yCoordinate;
    }

    /**
     * sets this text's font to the new value
     * @param newValue = the new font for this text
     */
    public void setFont(Font newValue)
    {
        textFont = newValue;
    }

    /**
     * @return the font that this text uses
     */
    public Font getFont()
    {
        return textFont;
    }

    /**
     * sets this text's colour to the new value
     * @param newValue = the new colour of this text
     */
    public void setColour(Color newValue)
    {
        textColour = newValue;
    }

    /**
     * @return the colour of this  piece of text
     */
    public Color getColour()
    {
        return textColour;
    }
}