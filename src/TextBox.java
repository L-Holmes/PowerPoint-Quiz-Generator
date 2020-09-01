
import javax.imageio.ImageIO;
import javax.swing.*;//used for gui generation

import java.awt.*;//used for layout managers
import java.awt.image.*;
import java.util.ArrayList;

/*
draws a text box in graphics 2d using the passed information

have the lines be seperate entries into their repective array (left or right (of the pointer)) 
then when I am drawing to the screen, I will add the dashes and split words in the way that I desire
*/


public class TextBox
{
	//drawing surface
    private Graphics2D graphicsHandler;					//graphics 2d object used to draw everything to the screen

	//dimensions
	private int boxX;									//x coordinate of the top left corner coordinate of the box
	private int boxY;									//y coordinate of the top left corner coordinate of the box
	private int boxW;									//width of the box
	private int boxH;									//height of the box
	private int boxRight;								//x coordinate of the bottom right corner coordinate of the box
	private int boxBottom;								//y coordinate of the bottom right corner coordinate of the box


	//text
	private ArrayList<String> leftText = new ArrayList<String>();  //holds a 2d array of all of the text to the left of the cursor 
	private ArrayList<String> rightText = new ArrayList<String>(); //holds a 2d array of all of the text to the right of the cursor 

	//text information for formatting
	//	for the outer arrayList: each entry represents a different line, with each index corresponding to the equivalent index within the [leftText] arrayList
	//	for the inner arrayList: each entry holds an integer, which represents:
	//     - 'newlineIndex' for deciding where to add a newline, to allow the text to fit latterly inside of the text box
	//
	//		*each 'line' is a line of text typed by the user, with seperate 'lines' seperated by the user clicking the 'enter' key on their keyboard (for a newline)
	/*
		e.g.
		outer{
			inner (line 1): 5, 10    //line1 was over 2 times longer than box width, so has a newline at index = 5 & index = 10
			inner (line 2): 2        //line2 was longer than the box width, so has 1 newline at index = 2
			inner (line 3): -1       //line3 is shorter than the box width, or has not been initialised, so has the null values of -1
		}
	*/
	ArrayList<ArrayList<Integer>> leftTextFormatInfo = new ArrayList<ArrayList<Integer>>();  //info with each index relating to the equivalent index within the leftText array list
	ArrayList<ArrayList<Integer>> rightTextFormatInfo = new ArrayList<ArrayList<Integer>>(); //info with each index relating to the equivalent index within the rightText array list


	//colours
	private Color textColour = new Color(0, 0, 0);					  //colour of the text 
	private Color boxColour = new Color(255, 255, 255); 			  //colour of the text box background
	private Color hoveringOverBoxColour = new Color(235, 235, 235);   //colour of the box when the user is hovering their mouse cursor over it
	private Color unselectedBoxColour = new Color(241, 241, 241);     //colour of the box when the user has not entered the text box
	private Color boxOutlineColour = new Color(0, 0, 0);              //colour of the outline of the text box
	private Color boxSelectedOutlineColour = new Color(245, 197, 22); //colour of the box outline when it is selected/entered      

	//entered/exited the text box indicators 
	private boolean entered = false;							//indicates whether the user has clicked the text box in order to type (true) or if they have exited the text box and cannot type in it (false)

	//cursor info
	private boolean cursorInBox = false; 						//indicates whether the mouse cursor lies inside of the text box (true) or if it doesn't (false)


	//---INITIALISATION---
    public static void main(String[] args)
    {
		System.out.println("main started ...");

		System.out.println("... main ended");
    }

	/**
	 * initializes the TextBox object
	 * @param graphicsObj = the graphics 2d object that will be used to draw to the screen
	 * @param xCoord = the x coordinate of the top left corner of the text box
	 * @param yCoord = the y coordinate of the top left corner of the text box
	 * @param width  = the width of the text box
	 * @param height = the height of the text box
	 */
    public TextBox(Graphics2D graphicsObj, int xCoord, int yCoord, int width, int height)
    {
		graphicsHandler = graphicsObj;
		boxX = xCoord;
		boxY = yCoord;
		boxW = width;
		boxH = height;

		leftText.add("");
		rightText.add("");
    }

    ///---TEXT ENTERING---


	/**
	 * handles the information regarding a user keyboard input
	 * converts common special key inputs to easier-to-understand strings
	 * passes on the typed key string to be further processed
	 * @param typedKey = the string representation of the character that has been typed
	 * @param extendedKeyCode = the extended key code of the typed key- which gives specific information about which key was pressed
	 * 							used to identify special keys, which do not have a character/string representation
	 */
    public void typeLetter(String typedKey, int extendedKeyCode)
    {
		if (extendedKeyCode == 8){
			typedKey = "backspace";
		}
		else if (extendedKeyCode == 10){
			typedKey = "enter";
		}
		else if (extendedKeyCode == 16777383){
			typedKey = "exit";
		}

		HandleTextEntered(typedKey);
	}
	
	/**
	 * handles special characters that have been entered and adds 
	 * characters to the left [of the cursor] string
	 * @param typedChar character / string (for special characters) of the typed input to the text box
	 */
	public void HandleTextEntered(String typedChar)
	{
		if (entered == true){
			if (typedChar == "exit"){
				//user wants to exit the text box
				entered = false;
				//resets the text box cursor 
			}
			else{
				updateText(typedChar);
			}
		}	
	}

	/**
	 * adjusts the left of cursor text, based on the typed user input
	 * @param typedChar the character / special string represnting the user's typed input to the text box
	 */
	public void updateText(String typedChar)
	{
		if (typedChar == "backspace"){
			//find the last item in the array 
			//ensure that the array is not empty
			//backspace string the last part of the left of cursor text

			int howBig = leftText.size();
			if (howBig > 0){
				String changedLine = leftText.get(howBig - 1);
				int lineLen = changedLine.length();
				if (lineLen > 0){
					String backspacedStr = backspaceString(changedLine);
					leftText.set(howBig-1, backspacedStr);

					//check if formatting info needs updating
					//only for if the last line has got shorter and needs to remove a new line, so check after the last newline
					updateLeftTextFormatInfo(false);
				}
				else{
					//remove the empty line
					if (howBig > 1){
						leftText.remove(howBig - 1);

						//remove the last entry from the formatting
						leftTextFormatInfo.remove(leftTextFormatInfo.size() - 1);
					}
				}
			}
		}
		else if (typedChar == "enter"){
			//add new entry for newline
			leftText.add("");
			//add the formatting info
			ArrayList<Integer> thisLineInfo = new ArrayList<Integer>(); 
			thisLineInfo.add(-1);  
			leftTextFormatInfo.add(thisLineInfo);
		}
		else{
			//add normal character
			int howBig = leftText.size();
			if (howBig > 0){
				String changedLine = leftText.get(howBig - 1) + typedChar;
				leftText.set(howBig-1, changedLine);

				//check if formatting info needs updating
				//only for if the last line has got longer and needs a new line, so check after the last newline
				updateLeftTextFormatInfo(true);
			}


		}
	}


	/**
	 * updates the formatting info, by 
	 * checking if the position of the newline needs to be moved, added or deleted for the text to be fitted properly
	 * 
	 * checks only the string after the last new line of the last entry, as this is where the cursor would lie and 
	 * thus the only position where the length of the line would change
	 * 
	 * @param longer = indicates whether the left text has got longer (true); or shorter (false)
	 */
	public void updateLeftTextFormatInfo(boolean longer)
	{
		//go to the last entry of the left text
		int leftTextSize = leftText.size();
		String lastLine = leftText.get(leftTextSize - 1);

		//get the info for the last newline of the last line (above)
		int leftFormatInfoSize = leftTextFormatInfo.size();
		ArrayList<Integer> formatInfoForEntireLine = leftTextFormatInfo.get(leftFormatInfoSize - 1);
		int formatInfoForEntireLineSize = formatInfoForEntireLine.size(); //////////////is this right or is it length???
		int lastNewLineFormatInfo = formatInfoForEntireLine.get(formatInfoForEntireLineSize - 1);

		if (longer == true){
			//-if text after the last new line is now longer than the text box width, add a new line-
			checkLeftTextOverhangs(lastNewLineFormatInfo, lastLine, formatInfoForEntireLine);
			
		}
		else{
			//-if text after the penultimate new line is shorter than the text box width, remove the last new line (or change index to -1)-
			checkLeftTextRemoveNewLine(lastNewLineFormatInfo, lastLine, formatInfoForEntireLineSize, formatInfoForEntireLine);
		}
	}

	/**
	 * checks if the last line before the cursor has got longer than the text box width.
	 * If the line is longer, adds a new 'new line index' entry to the  leftTextFormatInfo arraylist
	 * @param lastNewLineFormatInfo = the index of the last new line within the last line before the cursor
	 * @param lastLine = the line of text, as typed by the user, that lies before the cursor (not including formatting new lines)
	 * @param formatInfoForEntireLine = the arraylist containing the indexes of the formatting new line position for the lastLine text,
	 * 									which allow for the text to fit laterally into the text box
	 */
	private void checkLeftTextOverhangs(int lastNewLineFormatInfo, String lastLine, ArrayList<Integer> formatInfoForEntireLine)
	{
		if (lastNewLineFormatInfo == - 1){
			//--there are no new lines currently--

			//check if the entire string is longer than the textbox width
			int lastLineSize = graphicsHandler.getFontMetrics().stringWidth(lastLine);
			if (lastLineSize > boxW){
				//add new entry
				int newSplitPosition = findOverHangEntry(lastLine, lastLineSize);
				//add a new format entry, with the new split position
				formatInfoForEntireLine.add(newSplitPosition);
			}
		}
		else{
			//--there are at least 1 new lines currently--

			//get the string after the last new line, 
			String afterLastNewLine = lastLine.substring(lastNewLineFormatInfo);
			int afterLastNewLineSize = graphicsHandler.getFontMetrics().stringWidth(afterLastNewLine);

			//check if that string is longer than the text box width
			if (afterLastNewLineSize > boxW){
				//if it is, add a new line entry at the overhang index
				int newSplitPos = findOverHangEntry(afterLastNewLine, afterLastNewLineSize);
				//add a new format entry, with the new split position
				formatInfoForEntireLine.add(newSplitPos);
			}	 
		}
	}

	/**
	 * checks if the last line before the cursor has got longer than the text box width.
	 * If the line is longer, adds a new 'new line index' entry to the  leftTextFormatInfo arraylist
	 * @param lastNewLineFormatInfo = the index of the last new line within the last line before the cursor
	 * @param lastLine = the line of text, as typed by the user, that lies before the cursor (not including formatting new lines)
	 * @param formatInfoForEntireLineSize = the number of items within the formatInfoForEntireLine arraylist
	 * @param formatInfoForEntireLine = the arraylist containing the indexes of the formatting new line position for the lastLine text, 
	 *                                  which allow for the text to fit laterally into the text box
	 */
	private void checkLeftTextRemoveNewLine(int lastNewLineFormatInfo, String lastLine,  int formatInfoForEntireLineSize, ArrayList<Integer> formatInfoForEntireLine)
	{
		if (lastNewLineFormatInfo != -1){
			if (formatInfoForEntireLineSize > 1){
				//--there is a penultimate new line--

				int penultimateNewLineFormatInfo = formatInfoForEntireLine.get(formatInfoForEntireLineSize - 2);
				//get all of the string after the penultimate new line point
				String afterPenultimateNewLine = lastLine.substring(penultimateNewLineFormatInfo);
				 
				int afterPenultimateSize = graphicsHandler.getFontMetrics().stringWidth(afterPenultimateNewLine);
				//check if that string is shorter than the text box width
				if (afterPenultimateSize < boxW){
					//if it is, remove the last new line entry
					formatInfoForEntireLine.remove(formatInfoForEntireLineSize - 1);

				}
				

			}
			else{
				//--just one new line--

				//get the entire string (lastLine), check if it is shorter than the text box width
				int entireStringSize = graphicsHandler.getFontMetrics().stringWidth(lastLine);
				if (entireStringSize < boxW){
					//if so, change the entry to -1 (meaning no new lines)
					formatInfoForEntireLine.set(0, -1);

				}

				
			}
		}
	}


	/**
	 * finds the index of the first character within the string
	 * that overhangs out of the side of the textbox 
	 * @param lastLine = string of the last line (as seen on screen in the textbox) that occurs before the cursor (including the formatting newlines) 
	 * @param lastLineSize = the length (pixels) of the lastLine as calculated by graphics2d, which represents the horizontal distance spanned by this line
	 * @return the index of the position that first extends past the width of the textbox
	 */
	private int findOverHangEntry(String lastLine, int lastLineSize)
	{
		int lastCharLength;
		int reformattedLength = lastLineSize;  
		int moveBack = 0; //starts at the last index
		while (reformattedLength > boxW){
			lastCharLength = graphicsHandler.getFontMetrics().stringWidth("" + lastLine.charAt(lastLine.length() - 1 - moveBack));
			reformattedLength -= lastCharLength;
			moveBack++;
		}

		int splitPos = lastLine.length() - 1 - moveBack;
		return splitPos;
	}

	/**
	 * removes the last character of a string and returns the result
	 * @param str = the string to be shortened
	 */
	public String backspaceString(String str) {
		if (str != null && str.length() > 0 ) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	//---MOVING THE CURSOR---

    /**
	 * when a new cursor position has been 'clicked'
	 * sets the text left of the cursor to be everything left of the new 
	 * cursor position, and vice versa for everything to the right of 
	 * the new cursor position
	 * @param x = the new x coordinate of the click point within the text box
	 * @param y = the new y coordinate of the click point within the text box
	 */
	public void updateLeftAndRightText(int x, int y)
	{
		//--getting the entire string--
		String totalString = textBoxTextLeftOfCursor + textBoxTextRightOfCursor;
		int totalStringLength = totalString.length();

		//--getting the average character dimensions--
		graphicsHandler.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
		int avgCharWidth = graphicsHandler.getFontMetrics().stringWidth("t");
		int avgCharHeight = graphicsHandler.getFontMetrics().getAscent();

		//--predicting the number of rows & columns (each character representing a col.) that the new cursor will lie in--
		int yDiff = y - boxY;
		int rowNum = (int) Math.ceil(((double) yDiff / (double) avgCharHeight) + ((double)scrollAmount/(double)avgCharHeight));

		int xDiff = x - boxX;
		int colNum = 0;
		
		//calc col. num by going through all of the characters on the current line
		//and getting a cumulative sum of the width
		//until it reaches the character before the clicked position
		String clickedRow;
		boolean leftTextQuery;
		//----------WHAT ABOUT WHERE THE LEFT AND RIGHT TEXT MEET?
		if (rowNum <= leftText.length){
			//think it is in the left text
			clickedRow = leftText[rowNum];
			leftTextQuery = true;
		}
		else{
			//think it is in the right text
			clickedRow = rightText[rowNum];
			leftTextQuery = false;
		}

		int cumulTextLength = 0;
		for (int letterInd = 0; letterInd < clickedRow.length(); letterInd++){
			String letter = "" + clickedRow.charAt(letterInd);
			cumulTextLength += graphicsHandler.getFontMetrics().stringWidth(letter);
			if (cumulTextLength > xDiff){
				colNum = letterInd;
				break;
			}
		}

		//--getting all of the string on the lines above & below the line that the cursor is suspected to be in--
		int indexOfNewLineBeforeClickPos = ordinalIndexOf(totalString, "\n", rowNum - 1);
		String stringAboveSelectedLine;
		System.out.println("index of the newline before the click position: "+ indexOfNewLineBeforeClickPos);
		if (indexOfNewLineBeforeClickPos != -1)
		{
			if (rowNum != 1){
				//
				stringAboveSelectedLine = totalString.substring(0, indexOfNewLineBeforeClickPos);
			}
			else{
				stringAboveSelectedLine = "";
			}
		}
		else{
			stringAboveSelectedLine = "";
		}

		System.out.println("string above selected line: "+ stringAboveSelectedLine);

		int indexOfNewLineAfterClickPos = ordinalIndexOf(totalString, "\n", rowNum);
		System.out.println("index of the newline after the click position: "+ indexOfNewLineAfterClickPos);

		String stringBelowSelectedLine; 
		if (indexOfNewLineAfterClickPos != -1)
		{
			stringBelowSelectedLine = totalString.substring(indexOfNewLineAfterClickPos);
		}
		else{
			stringBelowSelectedLine = "";
		}



		//--getting the suspected index of character that the cursor will lie after--
		int totalCharsToLeft = stringAboveSelectedLine.length();
		int splitPosition = totalCharsToLeft + colNum;

		if (splitPosition> totalStringLength){
			splitPosition = totalStringLength;
		}
		
		//--creating the new strings from the left and right of the new cursor position--
		String newLeftOfCursor = totalString.substring(0, splitPosition);
		String newRightOfCursor = totalString.substring(splitPosition);

		textBoxTextLeftOfCursor = newLeftOfCursor;
		textBoxTextRightOfCursor = newRightOfCursor;


	}

	/**
	 * @param cursorStatus = true if cursor lies within the text box; false if the cursor does not lie within the text box
	 */
	public void setCursorInBox(boolean cursorStatus)
	{
		cursorInBox = cursorStatus;
	}

	/**
	 * @return boolean value indicating whether the cursor lies within the box (true) or not (false)
	 */
	public boolean isCursorInsideBox()
	{
		return cursorInBox;
	}


	//---DRAWING STUFF---

	/**
	 * Calls all of the methods 
	 * to draw the text box onto the screen
	 */
	public void drawTextBox()
    {
		drawBox();
		drawText();
	}

	/**
	 * draws the white box that the text will go in
	 */
	public void drawBox()
	{
		//drawing the main box
		if (entered == true){
			graphicsHandler.setColor(boxColour);
		}
		else if (cursorInBox == true){
			graphicsHandler.setColor(hoveringOverBoxColour);
		}
		else{
			graphicsHandler.setColor(unselectedBoxColour);
		}
	
		graphicsHandler.fillRect(boxX, boxY, boxW, boxH);
	
		//drawing outline 
		graphicsHandler.setColor(boxOutlineColour);
		if (entered == true){
			graphicsHandler.setColor(boxSelectedOutlineColour);
			graphicsHandler.setStroke(new BasicStroke(2));
		}
		graphicsHandler.drawRect(boxX, boxY, boxW, boxH);
	
		graphicsHandler.setStroke(new BasicStroke(1));//resets the stroke back to default
	
	}


	/**
	 * draws the text inside of the text box
	 */
	public void drawText()
	{
		//
	}
}