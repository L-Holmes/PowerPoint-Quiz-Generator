import javax.imageio.ImageIO;
import javax.swing.*;//used for gui generation

import java.awt.*;//used for layout managers
import java.awt.image.*;
import java.util.ArrayList;

public class LineOfTextBoxString
{
    Graphics2D graphicsHandler;
	public static ArrayList<LineOfTextBoxString> allLines = new ArrayList<LineOfTextBoxString>();        //contains all of the line objects

    private ArrayList<Integer> cumulativeStrWidth = new ArrayList<Integer>(); //holds the cumulative width of the string at the end of each character (at the equivalent index) within the string 

	private String fullText;												 //entire string
    private ArrayList<Integer> newLineIndexes = new ArrayList<Integer>();  //holds a list of all of the indexes where a newline is added, in order to fit this line into the text box
    
    int numLinesTakenUp = 0;

	public LineOfTextBoxString(String thisLine, Graphics2D g)
	{
		//
        fullText = thisLine;
        graphicsHandler = g;
        allLines.add(this);
	}

	/**
	 * @return the newLineIndexes array list which contains all of 
	 * the indexes where a new line would be inserted to allow
	 * this line to fit into the text box correctly
	 */
	public ArrayList<Integer> getNewLineIndexes ()
	{
		return newLineIndexes;
	}

	/**
	 * @return the number of new lines that have been added
	 * to allow this text to be correctly aligned within the 
	 * text box
	 */
	public int getNumNewLines()
	{
		return newLineIndexes.size() - 1;
	}

	/**
	 * @return the entire string for this line
	 */
	public String getText()
	{
		return fullText;
    }
    
    /**
     * @param newChar is added to the end of the full Text
     */
    public void addChar(String newChar)
    {
        //need to add the char length to the cumulativeStrWidth array
        cumulativeStrWidth.add(graphicsHandler.getFontMetrics().stringWidth(newChar));

    }

    /**
     * removes the last character from the end of the full text
     * @return indicates whether this line needs to be deleted (meaning that it is empty); true = delete line; false = do nothing, everything fine
     */
    public boolean removeEnd()
    {
        //acts like a backspace
        return false;
    }


	/**
	 * finds the positions where the line would be longer than the text box
	 * width, and adds newlines to allow the text to fit entirely into the box
	 * 
	 * call when:
	 * -the line has changed in length (line has been changed)
	 * -special occassion where the left text meets the right text -- so if first line of the right text, need to check the last line of the left text
	 * -text box width has changed
	 */
	public void recalculateNewLines(int textBoxWidth)
	{
        int lineWidth = graphicsHandler.getFontMetrics().stringWidth(fullText);
		int splits = (int) Math.floor(textBoxWidth / lineWidth);
		if (splits > 0){
            newLineIndexes.clear(); //empties the newLineIndexes array list
            numLinesTakenUp = 0;

            int prevLinesLength = 0;
            int spaceTakenOnThisLine;
            for (int letterInd = 0; letterInd < cumulativeStrWidth.size(); letterInd++){
                spaceTakenOnThisLine = cumulativeStrWidth.get(letterInd) - prevLinesLength;
                if (spaceTakenOnThisLine > textBoxWidth){
                    //if it exceeds the text box width, add a new line at the index that overhangs
                    if (letterInd > 0){
                        newLineIndexes.add(letterInd- 1);
                        numLinesTakenUp++;
                        prevLinesLength = (cumulativeStrWidth.get(letterInd - 1));
                    }
                }
            }
        }
        
        //for each existing new line point
        /*
        if the string before the new line is shorter than the point, combine it with everything up to the next string point and calculate
        a new break point; rince and repeat
        */
	}


} 