
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



//when the text length increases or decreases, also need to update the first entry for the right text (info)

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

	//blinking cursor
	private int cursorX;								//x coordinate of the top left corner coordinate of the blinking cursor
	private int cursorY;								//y coordinate of the top left corner coordinate of the blinking cursor
	private int cursorW;								//width of the blinking cursor
	private int cursorH;								//height of the blinking cursor


	//text
	private ArrayList<String> leftText = new ArrayList<String>();  //holds a 2d array of all of the text to the left of the cursor 
	private ArrayList<String> rightText = new ArrayList<String>(); //holds a 2d array of all of the text to the right of the cursor 

	//text information for formatting
	//a newline is inserted directly before the character at the index.
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
	private boolean entered = true;							//indicates whether the user has clicked the text box in order to type (true) or if they have exited the text box and cannot type in it (false)

	//mouse info
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

		ArrayList<Integer> emptyEntry = new ArrayList<Integer>();
		emptyEntry.add(-1);
		leftTextFormatInfo.add(emptyEntry);
		rightTextFormatInfo.add(emptyEntry);
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

		handleTextEntered(typedKey);
	}
	
	/**
	 * handles special characters that have been entered and adds 
	 * characters to the left [of the cursor] string
	 * @param typedChar character / string (for special characters) of the typed input to the text box
	 */
	public void handleTextEntered(String typedChar)
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
		int formatInfoForEntireLineSize = formatInfoForEntireLine.size(); 
		int lastNewLineFormatInfo = formatInfoForEntireLine.get(formatInfoForEntireLineSize - 1);

		if (longer == true){
			//-if text after the last new line is now longer than the text box width, add a new line-
			checkLeftTextOverhangs(lastNewLineFormatInfo, lastLine, formatInfoForEntireLine,leftFormatInfoSize - 1 );
			
		}
		else{
			//-if text after the penultimate new line is shorter than the text box width, remove the last new line (or change index to -1)-
			checkLeftTextRemoveNewLine(lastNewLineFormatInfo, lastLine, formatInfoForEntireLineSize, formatInfoForEntireLine, true);
		}

		//also need to update the first right text entry, as this will also need the new lines re-positioning if it overhangs/underhangs
		updateFormattingOnEntireLine(false, 0, false);
	}

	/**
	 * checks if the last line before the cursor has got longer than the text box width.
	 * If the line is longer, adds a new 'new line index' entry to the  leftTextFormatInfo arraylist
	 * @param lastNewLineFormatInfo = the index of the last new line within the last line before the cursor
	 * @param lastLine = the line of text, as typed by the user, that lies before the cursor (not including formatting new lines)
	 * @param formatInfoForEntireLine = the arraylist containing the indexes of the formatting new line position for the lastLine text,
	 * 									which allow for the text to fit laterally into the text box
	 * @param lineNumber = the index used on the leftTextFormatInfo to get the formatting information for the lastLine.
	 */
	private void checkLeftTextOverhangs(int lastNewLineFormatInfo, String lastLine, ArrayList<Integer> formatInfoForEntireLine, int lineNumber)
	{
		if (lastNewLineFormatInfo == - 1){
			//--there are no new lines currently--
			//check if the entire string is longer than the textbox width
			int lastLineSize = graphicsHandler.getFontMetrics().stringWidth(lastLine);
			if (lastLineSize > boxW){
				//add new entry
				int newSplitPosition = findOverHangEntry(lastLine, lastLineSize);
				//add the split position to the last newline entry to get the 'cumulative' newline index
				//add a new format entry, with the new split position

				//
				int lastFormatEntry = leftTextFormatInfo.get(lineNumber).size() - 1;
				//
				ArrayList<Integer> currentLeftTextFormatForLastLine = leftTextFormatInfo.get(leftTextFormatInfo.size() - 1);
				//get the cumulative split position by adding to the previous new line entry index
				if (currentLeftTextFormatForLastLine.get(currentLeftTextFormatForLastLine.size() -1) != -1){
					newSplitPosition += currentLeftTextFormatForLastLine.get(currentLeftTextFormatForLastLine.size() -1);
				}

				setNewFormattingEntry(true, lineNumber, lastFormatEntry, newSplitPosition);
				
				//end of fix method
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

				//get the cumulative split position by adding to the previous new line entry index
				if (formatInfoForEntireLine.get(formatInfoForEntireLine.size() -1) != -1){
					newSplitPos += formatInfoForEntireLine.get(formatInfoForEntireLine.size() -1);
				}

				//add a new format entry, with the new split position
				//formatInfoForEntireLine.add(newSplitPos);
				addNewFormattingEntry(true, lineNumber, newSplitPos);
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
	private void checkLeftTextRemoveNewLine(int lastNewLineFormatInfo, String lastLine,  int formatInfoForEntireLineSize, ArrayList<Integer> formatInfoForEntireLine, boolean isLeft)
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
				if (entireStringSize <= boxW){
					//if so, change the entry to -1 (meaning no new lines)
					//formatInfoForEntireLine.set(0, -1);
					if (isLeft){
						setNewFormattingEntry(isLeft, leftTextFormatInfo.size() - 1, 0, -1);
					}


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

		int splitPos = lastLine.length() - 1 - Math.max(0, (moveBack -1));
		return splitPos;
	}

	/**
	 * finds the index of the first character within the string
	 * that overhangs out of the side of the textbox 
	 * @param lastLine = string of the last line (as seen on screen in the textbox) that occurs before the cursor (including the formatting newlines) 
	 * @param lastLineSize = the length (pixels) of the lastLine as calculated by graphics2d, which represents the horizontal distance spanned by this line
	 * @param averageCharWidth = the average width of a character in the font that the user is typing in
	 * @param lineStartDistFromBoxStart = distance that the line of text starts from to the x coordinate of the left side of the text box
	 * @return the index of the position that first extends past the width of the textbox
	 */
	private int findOverHangEntryFromAvgCharWidth(String lastLine, int lastLineSize, int averageCharWidth, int lineStartDistFromBoxStart)
	{
		/*
		if the string is longer than the box width,
		it estimates (using the averageCharWidth)
		the index that will be the first character to overhang.

		Then ensure that this is the first overhanging character by checking characters to the immediate left 
		or right of the split position, and finding the one closest to the box width.
		*/

		//
		int widthToFit = boxW - lineStartDistFromBoxStart;
		


		int splitPos = lastLine.length() - 1;


		if (lastLineSize > widthToFit){

			
			//
			int estimatedOverHangPoint = widthToFit / averageCharWidth;

			int lengthUpToEstimatePoint = graphicsHandler.getFontMetrics().stringWidth(lastLine.substring(0, estimatedOverHangPoint));
			

			

			if (lengthUpToEstimatePoint > widthToFit){
				//remove the last character, check again
				splitPos = estimatedOverHangPoint;


				//-loop through the characters moving left until the text is shorter than the box width-
				boolean foundOverHang = false;
				while (foundOverHang == false){
					splitPos--;
					if (splitPos > -1){
						int shorterEstimateLength = graphicsHandler.getFontMetrics().stringWidth(lastLine.substring(0, splitPos));
						
						


						if (shorterEstimateLength < widthToFit){
							splitPos ++; //moves back onto the first character that overhangs
							foundOverHang = true;
						}
					}
					else{
						foundOverHang = true;
					}
				}
				
			}
			else if (lengthUpToEstimatePoint < widthToFit){
				//add the next character to the string length total, check again
				splitPos = estimatedOverHangPoint;



				//-loop through the characters moving right until the text is longer than the box width-
				boolean foundOverHang = false;
				while (foundOverHang == false){
					splitPos++;
					if (splitPos < lastLine.length()){
						int longerEstimateLength = graphicsHandler.getFontMetrics().stringWidth(lastLine.substring(0, splitPos));
						

						if (longerEstimateLength > widthToFit){
							foundOverHang = true;
						}
					}
					else{
						foundOverHang = true;
					}
				}
			}
			else{

				splitPos = estimatedOverHangPoint;
			}	
		}
		

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
		//--initialising the font--
		graphicsHandler.setFont(new Font("Monospaced", Font.PLAIN, 12)); 

		//-SECTION 1: DETERMINE WHETHER THE NEW CURSOR POSITION IS BEFORE OR AFTER THE CURRENT POSITION AND SET THE FONT-

		System.out.println("\n\n -----------------start");
		System.out.println("x coord clicked = " + x);
		System.out.println("y coord clicked = " + y);
		System.out.println("avg char height = " + graphicsHandler.getFontMetrics().getAscent());
		System.out.println("avg char width = " + graphicsHandler.getFontMetrics().stringWidth("a"));

		//--determines whether the leftText (left of the cursor) or rightText (right of the cursor) was clicked--
		boolean clickedLeftText;
		if (y > cursorY + cursorH){
			//if the click position is below the bottom of the current cursor position
			//right text
			clickedLeftText = false;


		}
		else{
			//new click position is on the line of the current cursor position or higher up
			if ((y > cursorY) && (x > cursorX)){
				//right text
				clickedLeftText = false;
				
			}
			else{
				//left text
				clickedLeftText = true;
				

			}
		}

		System.out.println("was left text clicked? " + clickedLeftText);
 

		//-SECTION 2: GETTING INFORMATION ABOUT THE ROW THAT THE USER CLICKED-

		int avgCharHeight = graphicsHandler.getFontMetrics().getAscent();
		//--predicting the row number that the new cursor will lie in--
		int yDiff = y - boxY;														//y distance from the top of the box to the clicked position
		int rowNum = (int) Math.ceil(((double) yDiff / (double) avgCharHeight));	//predicted number of rows down (from the top of the box) that the user clicked


		System.out.println("y dist from box top = " + yDiff);
		System.out.println("number of rows down = " + rowNum);


		//if it is right, need to remove all of the left rows from the calc
		if (clickedLeftText == false){
			int topBoxToCursor = cursorY - boxY; 
			//number of rows above the cursor
			int leftRows = (int) Math.ceil(((double) topBoxToCursor / (double) avgCharHeight));
			rowNum = rowNum - leftRows;

			System.out.println("removed left rows to get new row number of: " + rowNum);
		}

		


		//--get the row at that number after the formatting (after newlines have been added to fit the text into the box)--
		int [] rowAfterFormattingInfo; 
		if (clickedLeftText == true){
			rowAfterFormattingInfo = getRowAfterFormatting(rowNum, leftTextFormatInfo);
		}
		else{
			rowAfterFormattingInfo = getRowAfterFormatting(rowNum, rightTextFormatInfo);
		}
		int newCursorRowIndex = rowAfterFormattingInfo[0];     
		int newCursorFormatIndex = rowAfterFormattingInfo[1];
		int substringStartIndex = rowAfterFormattingInfo[2];
		int substringEndIndex = rowAfterFormattingInfo[3];

		
		
		System.out.println("row index = " + newCursorRowIndex);
		System.out.println("format index = " + newCursorFormatIndex);
		System.out.println("substring start index = " + substringStartIndex);
		System.out.println("substring end index = " + substringEndIndex);
		


		//-SECTION 3: GETTING A STRING OF THE ROW THAT WAS CLICKED-

		//---getting a string containing the single row that the user clicked---
		String clickedLine;
		if (clickedLeftText == true){
			clickedLine = leftText.get(newCursorRowIndex);
		}
		else{
			clickedLine = rightText.get(newCursorRowIndex);
		}

		System.out.println("entire line that was clicked :" + clickedLine+":");
		
		String clickedSectionOfLine;
		if (substringStartIndex != 0)
		{
			
			if (substringEndIndex != -1){
				//there is at least 2 formatting new lines for this line
				clickedSectionOfLine = clickedLine.substring(substringStartIndex, substringEndIndex);
			}
			else{
				//there is at least 1 formatting new line for this line
				clickedSectionOfLine = clickedLine.substring(substringStartIndex);
			}
			
		}
		else{
			//there are no formatting new lines for this line
			clickedSectionOfLine = clickedLine;
		}

		System.out.println("clicked section of line :" + clickedSectionOfLine + ":");


		//-SECTION 4: FINDING THE COLUMN THAT THE USER CLICKED-

		//--finding the column number that the new click position lies in (each column = 1 character)--
		int xDiff = x - boxX;																					//x distance from box left to clicked position

		System.out.println("x distance from box left to clicked position = " + xDiff);

		//-if on the first line of the right text, need to remove all of the left characters that are also on this line-
		if ((clickedLeftText == false) && (newCursorRowIndex == 0)){
			//
			String leftTextAlsoOnThisLine;

			//get the last index of the last entry of the left text
			ArrayList<Integer> lastLeftEntry = leftTextFormatInfo.get(leftTextFormatInfo.size() - 1);
			int lastIndexForLastLeft = lastLeftEntry.get(lastLeftEntry.size() - 1);
			//if not equal to 0, get the substring of everything after the last index
			if (lastIndexForLastLeft != -1){
				leftTextAlsoOnThisLine = leftText.get(leftText.size() - 1).substring(lastIndexForLastLeft);
			}
			//else get the entire line
			else{
				leftTextAlsoOnThisLine = leftText.get(leftText.size() - 1);
			}

			//then get the length using graphics 2d and take this away from the xDiff.
			xDiff = Math.max(0, xDiff - graphicsHandler.getFontMetrics().stringWidth(leftTextAlsoOnThisLine));


			System.out.println("clicked the first line of the right text so removed all left characters to get an xDiff of: " + xDiff);
		}
		
		//calc col. num by going through all of the characters on the current line
		//and getting a cumulative sum of the width
		//until it reaches the character before the clicked position

		int colNum = 0;
		int cumulTextLength = 0;
		int previousLength = 0;
		for (int letterInd = 0; letterInd < clickedSectionOfLine.length(); letterInd++){
			String letter = "" + clickedSectionOfLine.charAt(letterInd);
			
			cumulTextLength += graphicsHandler.getFontMetrics().stringWidth(letter);
			

			colNum = letterInd;
			if (cumulTextLength >= xDiff){
				//difference between length up to this character and length up to previous character
				int diffOfLengths = cumulTextLength - previousLength;
				int midWayPoint = previousLength + diffOfLengths/2;
				if (xDiff < midWayPoint){
					//clicked point is closer to the previous character
					colNum--;
				}
				
				break;
			}

			//
			previousLength = cumulTextLength;
		}

		System.out.println("found the column number of: " + colNum);
		System.out.println("end-----------------------");


		//now we know which character was clicked.

		//-SECTION 5: UPDATE THE ARRAYLISTS TO HOLD THE NEW CURSOR POSITION-

		adjustCursorInformation(clickedLeftText, colNum, newCursorRowIndex, newCursorFormatIndex, substringStartIndex, substringEndIndex);

	}


	/**
	 * updates the left and right text arrays to 
	 * 		have the correct strings to the left and right of the new cursor position
	 * 
	 * updates the format text info for the left and right respecively, to hold the new, correct
	 * 		newline positions for each line of text
	 * 
	 * @param clickedLeftText = indicates (in reference to the new cursor position) whether the user clicked the text 
	 * 							to the left of the cursor (true) or the text to the right of the cursor (false)
	 * @param indexOfClickedCharWithinTheFormattedSectionOfTheLine = index of the character from the substring of text that was clicked 
	 * 																 (substring is the part of the line that was formatted with newlines
	 * 																  to fit into the text box, where the substring is the string unbroken
	 * 																  by formatting newlines, that the user clicked) i.e. each substring
	 * 																  takes up a single line at most, wheras the 'line' may span over 
	 * 																  multiple lines after being formatted. 
	 * @param clickedLineIndex = index used on the [leftText] array, to find the line that the user clicked (for the new cursor placement)
	 * @param clickPointLineStartFormatIndex = index used on the [leftTextFormatInfo] array, to find out which section of the split up line 
	 * 										   was clicked (since the line may have additional newlines for formatting purposes)
	 * @param substringStartIndex = the index used on the entire line, to find the start of the substring that makes up the single line of 
	 * 								text that the user clicked
	 * @param substringEndIndex = the index used on the entire line, to find the end of the substring that makes up the single line of text
	 * 							  that the user clicked
	 */
	private void adjustCursorInformation(boolean clickedLeftText, int indexOfClickedCharWithinTheFormattedSectionOfTheLine, int clickedLineIndex, int clickPointLineStartFormatIndex, int substringStartIndex, int substringEndIndex)
	{
		/*
		for the old left and right positions:
			-merge everything from the right into the left side, and have as one line
			-recheck breakpoints for old last formatting newline (for the left side) and everything after (so all of the new right side)

		for the new click point:
			-everything from the left (of the cursor position) to the start of the line, will be the last line entry in the leftText array
			-the rest of the clicked line will become the first entry in the rightText array, and will be joined to the rest of the lines

			-remove all formatting newline entries for the clicked line, for any indexes greater than the clicked position
			-calculate new format entries for the right side of the line. 

			-then combine this left with all of the previous left entries that occurred before the current line (alongside their
			 pre-existing formatting information) to form the new left
			-combine the new right start line with everything after the newly clicked line (and their formatting info) to get 
			 the new right
		*/

		
		//---SECTION 1: UPDATE THE LINES WHERE THE OLD CURSOR POSITION WAS LOCATED---

		//--merge lines to the immediate left and right of the old cursor position--

		//-concatenate the last left and first right entries, to give the new last leftText entry-
		String oldImmediateLeftLine = leftText.get(leftText.size() - 1);
		String oldImmediateRightLine = rightText.get(0);
		String newLine = oldImmediateLeftLine + oldImmediateRightLine;
		leftText.set(leftText.size() - 1, newLine);

		

		//-adjusting the column index if needed-
		//if the user clicked the first right line, need to add on the length of the left section to the index
		//since later on in this method, the old right of the cursor text is merged with the last left entry,
		//so will need to adjust the column information to reflect this
		if ((clickedLeftText == false) && (clickedLineIndex == 0)){
			indexOfClickedCharWithinTheFormattedSectionOfTheLine += oldImmediateLeftLine.length();
		}

		//-remove the first right text entry; replace with the second right text entry (if exists), or set as blank string-
		if (rightText.size() > 1){
			rightText.remove(0);
			rightTextFormatInfo.remove(0);
		}
		else{
			rightText.set(0, "");
			ArrayList<Integer> emptyRightInfo = new ArrayList<Integer>(); 
			emptyRightInfo.add(-1);  
			rightTextFormatInfo.set(0, emptyRightInfo);
			
		}

		


		//-recalculate the format info for the leftText's last entry-
		updateFormattingOnEntireLine(true, leftText.size() - 1, true);

		
		//---SECTION 2: GET THE ENTIRE LINE OF STRING THAT WAS CLICKED---
		String entireLine;
		if (clickedLeftText == true){
			entireLine = leftText.get(clickedLineIndex);
		}
		else{
			//if getting the first position of the right, get the last position of the 
			//left as in section 1, the end of the left and the start of the right 
			//where combined into the end of the left
			if (clickedLineIndex == 0){
				//
				entireLine = leftText.get(leftText.size() -1);
			}
			else{
				entireLine = rightText.get(clickedLineIndex);
			}
		}

		
		//---SECTION 3: REMOVING THE ENTIRE LINE THAT WAS CLICKED, SO IT CAN BE REPLACED WITH THE UPDATED LEFT/RIGHT SPLIT IN SECTION 5---

		if (clickedLeftText == true){
			

			//remove the clicked entry from the leftText
			leftText.remove(clickedLineIndex);
			leftTextFormatInfo.remove(clickedLineIndex);

		}
		else{

			if (clickedLineIndex != 0){
				//remove the clicked entry from the rightText
				rightText.remove(clickedLineIndex);
				rightTextFormatInfo.remove(clickedLineIndex);
			}
			else{
				//if the index = 0, then the first right text has been merged into the last left text by the previous sections
				leftText.remove(leftText.size() - 1);
				leftTextFormatInfo.remove(leftTextFormatInfo.size() - 1);
			}
			
		}

		

		
		//---SECTION 4: MOVE LINES FROM LEFT TEXT TO RIGHT TEXT (OR VICE VERSA), IN ORDER TO FIT THE NEWLY CLICKED POSITION---

		/*
		if clicked left:
			adds all of the lines below the newly clicked line to the right text
		if clicked right:
			adds all of the lines above the newly clicked line to the left text
		*/
		if (clickedLeftText == true){
			//user clicked the left text
			//-adds all of the lines below the newly clicked line to the right text-

			int swapLine = leftText.size() - 1;
			//while the line being added is below the newly clicked line,
			//inset the last line of the leftText into the first position of the rightText
			while (swapLine >= clickedLineIndex){
				//[above], have the '=' portion, since the clicked line was actually remove in section 3, so all lines above will move down 1 index
				
				String leftTextEndLine = leftText.get(leftText.size() - 1);
				ArrayList<Integer> leftTextEndLineFormatInfo = leftTextFormatInfo.get(leftTextFormatInfo.size() - 1);

				//set the start of the right text to have that entry
				rightText.add(0, leftTextEndLine);
				rightTextFormatInfo.add(0, leftTextEndLineFormatInfo);
				

				//remove the last element of the left text
				leftText.remove(leftText.size() - 1);
				leftTextFormatInfo.remove(leftTextFormatInfo.size() -1);

				//go onto the above line
				swapLine --;
			}
			

		}
		else{
			//user clicked the right text
			//-adds all of the lines above the newly clicked line to the left text-

			int swapLine = 0;
			//start at the first line of the right text,
			//add all of the lines that are above the newly clicked line to the left text
			while (swapLine < clickedLineIndex){
				String rightTextFirstLine = rightText.get(0);
				ArrayList<Integer> rightTextFirstLineFormatInfo = rightTextFormatInfo.get(0);

				//set the end of the left text to have that entry
				leftText.add(rightTextFirstLine);
				leftTextFormatInfo.add(rightTextFirstLineFormatInfo);

				//remove the last element of the left text
				rightText.remove(0);
				rightTextFormatInfo.remove(0);
				
				//go onto the below line
				swapLine ++;
			}

		}


		
		//---SECTION 5: UPDATE THE LINES WHERE THE NEW CURSOR POSITION IS LOCATED---

		//-add the substring of the portion of the line that is to the left of the new cursor position, as the final line of the leftText-
		String leftPortion;
		String rightPortion;
		//this is if the user wants to type at the very start of the line
		if (indexOfClickedCharWithinTheFormattedSectionOfTheLine>-1){
			//
			leftPortion = entireLine.substring(0, indexOfClickedCharWithinTheFormattedSectionOfTheLine);
			rightPortion = entireLine.substring(indexOfClickedCharWithinTheFormattedSectionOfTheLine);

		}
		else{
			//
			leftPortion = "";
			rightPortion = entireLine;
		}



		//adding the left side to the end of the left text & formatting arrays
		leftText.add(leftPortion);
		ArrayList<Integer> lastLeftInfo = new ArrayList<Integer>(); 
		lastLeftInfo.add(-1);  
		leftTextFormatInfo.add(lastLeftInfo);

		//adding the right side to the start of the right text & formatting arrays
		rightText.add(0, rightPortion);
		ArrayList<Integer> firstRightInfo = new ArrayList<Integer>(); 
		firstRightInfo.add(-1);  
		rightTextFormatInfo.add(0, firstRightInfo);

		

		//-updating the formatting info for the newly added sections-
		/*
		-need to check if the last entry of the leftText has lengthened (may need multiple formatting newlines)
		-need to check the first entry of the rightText for lengthening (may need multiple formatting newlines)
		*/
		updateFormattingOnEntireLine(true, leftText.size() - 1, false);
		updateFormattingOnEntireLine(false, 0, false);

	
	}

	/**
	 * checks if the passed line string is longer than the text box width.
	 * If the line is longer, adds a new 'new line index' entry to the  leftTextFormatInfo arraylist
	 * -adds newlines until the remainder of the string left is shorter than the textbox width
	 * 
	 * @param leftTextQuery = determines whether the line being updated is from the left text (true) or the right text (false)
	 * @param indexOfLineInTextArray = index within the [leftText/rightText] array, to get the line that is being updated
	 * @param checkAfterLastFormattingEntry = determines whether the entire line will be looked at for formatting (false); or whether 
	 * 										  only the substring of text after the last formatting index will be looked at (true)
	 */
	private void updateFormattingOnEntireLine(boolean leftTextQuery, int indexOfLineInTextArray, boolean checkAfterLastFormattingEntry)
	{


		//--get the string of characters that are being checked for being longer than the text box width--

		String stringToCheck;
		String entireString;
		ArrayList<Integer> formatInfoForTheString;
		int textStartRelativeToBoxX = 0; //how many pixels are there inbetween the left side of the text box and the start of the text?

		if (leftTextQuery == true){
			//left text
			entireString = leftText.get(indexOfLineInTextArray);
			formatInfoForTheString = leftTextFormatInfo.get(indexOfLineInTextArray);
		}
		else{
			//right text
			entireString = rightText.get(indexOfLineInTextArray);
			formatInfoForTheString = rightTextFormatInfo.get(indexOfLineInTextArray);

			//if first entry of right text
			if (indexOfLineInTextArray == 0){
				//need to account for the line not starting at the beginning of the text box since it comes straight after the end of the left text
				ArrayList<Integer> lastFormatInfoForTheleftText = leftTextFormatInfo.get(leftTextFormatInfo.size() - 1);
				int lastFormatNewLineForTheLeftText = lastFormatInfoForTheleftText.get(lastFormatInfoForTheleftText.size() - 1);
				String lastLineFromLeftText;
				if (lastFormatNewLineForTheLeftText != -1){
					//
					lastLineFromLeftText = leftText.get(leftText.size() -1).substring(lastFormatNewLineForTheLeftText);
				}
				else{
					lastLineFromLeftText = leftText.get(leftText.size() -1);
				}
				
				textStartRelativeToBoxX = graphicsHandler.getFontMetrics().stringWidth(lastLineFromLeftText);
			}

		}

		


		stringToCheck = entireString;
		

		int lastFormattingEntry = formatInfoForTheString.get(formatInfoForTheString.size() -1); 
		if (checkAfterLastFormattingEntry == true){
			if (lastFormattingEntry != -1){
				//
				stringToCheck = stringToCheck.substring(lastFormattingEntry);
			}
		}
		



		/////////////////
		//the following while loop is desined in a way that we 
		//need to remove all of the current entries first
		if (checkAfterLastFormattingEntry == false)
		{
			//
			if (leftTextQuery){
				//remove all of the entries apart form the first one
				for (int i = formatInfoForTheString.size() - 1; i > 0; i--){
					leftTextFormatInfo.get(indexOfLineInTextArray).remove(i);
				}
				//set the first entry to equal zero
				setNewFormattingEntry(true, indexOfLineInTextArray, 0, -1);
			}
			else{
				//remove all of the entries apart form the first one
				for (int i = formatInfoForTheString.size() - 1; i > 0; i--){
					rightTextFormatInfo.get(indexOfLineInTextArray).remove(i);
				}
				//set the first entry to equal zero
				setNewFormattingEntry(false, indexOfLineInTextArray, 0, -1);

			}
		}


		

		//--add the new entries, until the remaining substring is shorter than the text box width (changed = false) --
		boolean changed = true;
		while (changed == true){

			//need to update the format info for reference, since it is not a point:
			if (leftTextQuery == true){
				formatInfoForTheString = leftTextFormatInfo.get(indexOfLineInTextArray);
			}
			else{
				formatInfoForTheString = rightTextFormatInfo.get(indexOfLineInTextArray);
			}

			if (formatInfoForTheString.get(formatInfoForTheString.size() -1) == - 1){
				//--there are no new lines currently--
	
				//check if the entire string is longer than the textbox width
				int stringToCheckSize = graphicsHandler.getFontMetrics().stringWidth(stringToCheck) + textStartRelativeToBoxX;

				if (stringToCheckSize > boxW){
					//add new entry
					int avgCharWidth = graphicsHandler.getFontMetrics().stringWidth("a");
					int newSplitPosition = findOverHangEntryFromAvgCharWidth(stringToCheck, stringToCheckSize - textStartRelativeToBoxX, avgCharWidth, textStartRelativeToBoxX);
					
					//add new split position to the previous entry in the formatting to get the correct (cumulative) index
					int cumulativeSplitPosition = newSplitPosition;
					if (formatInfoForTheString.get(formatInfoForTheString.size() -1) != -1){
						cumulativeSplitPosition += formatInfoForTheString.get(formatInfoForTheString.size() -1);
					}
					//add a new format entry, with the new split position
					//formatInfoForTheString.set(0, cumulativeSplitPosition);
					if (checkAfterLastFormattingEntry == false){
						setNewFormattingEntry(leftTextQuery, indexOfLineInTextArray, 0, cumulativeSplitPosition);
						//update the string to check to be the remainder of the string after the newly added newline
						stringToCheck = entireString.substring(cumulativeSplitPosition);
					}
					else{
						addNewFormattingEntry(leftTextQuery, indexOfLineInTextArray, cumulativeSplitPosition);
						//update the string to check to be the remainder of the string after the newly added newline
						
						//since the 'entireString' is already a substring, don't need to use the cumulative index
						stringToCheck = entireString.substring(newSplitPosition);

					}

					
				}
				else{
					
					changed = false;
				}
				
				
			}
			else{
				//--there are at least 1 new lines currently--

				//get the string after the last new line, 
				int stringToCheckSize = graphicsHandler.getFontMetrics().stringWidth(stringToCheck) + textStartRelativeToBoxX;

				//check if that string is longer than the text box width
				if (stringToCheckSize > boxW){

					//if it is, add a new line entry at the overhang index
					int avgCharWidth = graphicsHandler.getFontMetrics().stringWidth("a");
					int newSplitPos = findOverHangEntryFromAvgCharWidth(stringToCheck, stringToCheckSize - textStartRelativeToBoxX, avgCharWidth, textStartRelativeToBoxX);

					//add new split position to the previous entry in the formatting to get the correct (cumulative) index
					if (formatInfoForTheString.get(formatInfoForTheString.size() -1) != -1){
						int prevNLIndex = formatInfoForTheString.get(formatInfoForTheString.size() -1);
						newSplitPos += prevNLIndex;
					}

					//add a new format entry, with the new split position
					//formatInfoForTheString.add(newSplitPos);
					addNewFormattingEntry(leftTextQuery, indexOfLineInTextArray, newSplitPos);
					

					//update the string to check to be the remainder of the string after the newly added newline
					stringToCheck = entireString.substring(newSplitPos);

				}
				else{
					changed = false;
				}
			}

			//cannot be on the first line after first iteration, so text must begin at the start of the text box X.
			textStartRelativeToBoxX = 0;
		}

		
	}

	
	/**
	 * returns information relating to the row that was clicked by the user in the process of selecting a new cursor position
	 * @param rowNum = the number of rows of text that spans the vertical distance between the 
	 * 				   newly selected cursor position and the top of the text box
	 * @param formatInfo = the format info for either the entire leftText or rightText
	 * 
	 * @return = an integer array containing:
	 * 			-newCursorRowIndex = index used on the [leftText] array, to find the line that the user clicked (for the new cursor placement)
	 *			-newCursorFormatIndex = index used on the [leftTextFormatInfo] array, to find out which section of the split up line was clicked (since the line may have additional newlines for formatting purposes)
	 *			-substringStartIndex = index of the character of the string (as found via the newCursorRowIndex), that starts the particular line
	 *			-substringEndIndex = index of the character of the string (as found via the newCursorRowIndex), that ends the particular line
	 */
	private int [] getRowAfterFormatting(int rowNum, ArrayList<ArrayList<Integer>> formatInfo)
	{
		int totalFoundRows = 0;
		ArrayList<Integer> lineInfo;

		int newCursorRowIndex = 0;
		int newCursorFormatIndex = 0;
		int substringStartIndex = -1;
		int substringEndIndex = -1;

		for (int userTypedLineIndex = 0; userTypedLineIndex < formatInfo.size(); userTypedLineIndex++){
			//for each line (lines seperated by a user typed 'newline' [enter key])
			lineInfo = formatInfo.get(userTypedLineIndex);
			totalFoundRows++;


			//-if the part of the line before the first formatting newline 
			// is the row that the user clicked-
			if (totalFoundRows >= rowNum){
				//found the row that the user clicked

				newCursorRowIndex = userTypedLineIndex;
				substringStartIndex = 0;
				
				if (lineInfo.size() > 0){
					//get the first newline position
					substringEndIndex = lineInfo.get(0);
				}
				break;
			}
			//

			for (int i = 0; i < lineInfo.size(); i++){
				//for each formatting newline (which are added to fit the text latterally into the textbox)
				if (lineInfo.get(i) != -1){
					//increment by 1 since found a position for a newline within the text
					totalFoundRows++;
				}
				if (totalFoundRows >= rowNum){
					//found the row that the user clicked
					newCursorRowIndex = userTypedLineIndex;
					newCursorFormatIndex = i;
					if (lineInfo.get(newCursorFormatIndex) != -1){
						substringStartIndex = lineInfo.get(newCursorFormatIndex);
					}
					if (lineInfo.size() - 1 > i){
						substringEndIndex = lineInfo.get(newCursorFormatIndex + 1);
					}
					break;
				}
			}
		}

		int [] returnArr = new int [4];
		returnArr[0] = newCursorRowIndex;
		returnArr[1] = newCursorFormatIndex;
		returnArr[2] = substringStartIndex;
		returnArr[3] = substringEndIndex;

		return returnArr;


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

	/**
	 * updates the coordinates for the top left corner of the blinking cursor
	 * (which typically lies at the end of the left text)
	 * @param newX = the new x coordinate for the cursor
	 * @param newY = the new y coordinate for the cursor
	 */
	public void updateCursorPosition(int newX, int newY, int tempH)
	{
		cursorX = newX;
		cursorY = newY;
		cursorH = tempH;
	}



	//---DRAWING STUFF---

	/**
	 * Calls all of the methods 
	 * to draw the text box onto the screen
	 */
	public void drawTextBox(Graphics2D updatedGraphicsHandle)
    {
		graphicsHandler = updatedGraphicsHandle;
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

		graphicsHandler.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
		graphicsHandler.setColor(textColour);

		int textHeight = graphicsHandler.getFontMetrics().getAscent();
		int textY = boxY - (textHeight*0);
		int textX = boxX;

		
		//-draw each line in the left text-
		for (int i = 0; i < leftText.size(); i++){

			String line = leftText.get(i);

			ArrayList<Integer> formattingEntriesForLine = leftTextFormatInfo.get(i);


			int startSplitIndex = 0;    
			int endSplitIndex;

			String toDrawString;
			
			//-formatting the lines to fit into the text box and drawing them-
			for (int j = 0; j < formattingEntriesForLine.size(); j++){
				//move the drawing position down to the next line
				textY += textHeight;
				//
				endSplitIndex = formattingEntriesForLine.get(j);
				if (endSplitIndex == -1){
					//no additional formatting newlines
					//draw from startIndex to the end of the line
					toDrawString = line;
					graphicsHandler.drawString(toDrawString, textX, textY);

					//if on the last line of the left text
					if (i == leftText.size()-1){
						//don't need to check j, since you aleady know that you're on the end of the text(since there are no formatting newlines)
						//update the x to be at the end of this text, since we are next drawing the right text (which starts at the end of the left)
						textX +=  graphicsHandler.getFontMetrics().stringWidth(toDrawString);
						updateCursorPosition(textX, textY - textHeight, textHeight);
					}
					

					break;

				}
				else{
					//draw between the two newline points
					toDrawString = line.substring(startSplitIndex, endSplitIndex);
					graphicsHandler.drawString(toDrawString, textX, textY);

					//if on the last index, also draw to the end of the line
					if (j == formattingEntriesForLine.size() - 1){
						textY += textHeight;
						toDrawString = line.substring(endSplitIndex);
						graphicsHandler.drawString(toDrawString, textX, textY);

						//if on the last line of the left text
						if (i == leftText.size()-1){
							//update the x to be at the end of this text, since we are next drawing the right text (which starts at the end of the left)
							textX +=  graphicsHandler.getFontMetrics().stringWidth(toDrawString);
							updateCursorPosition(textX, textY - textHeight, textHeight);
						}
					}
					//

				}
				//update the start index to be the end index so you don't redraw the previous section of text
				startSplitIndex = endSplitIndex;

			}

		}

		

		//-draw each line in the right text-
		for (int rightTextIndex = 0; rightTextIndex < rightText.size(); rightTextIndex++){

			String line = rightText.get(rightTextIndex);
			ArrayList<Integer> formattingEntriesForLine = rightTextFormatInfo.get(rightTextIndex);


			int startSplitIndex = 0;    
			int endSplitIndex;

			String toDrawString;
			//-formatting the lines to fit into the text box and drawing them-
			for (int rightTextFormatIndex = 0; rightTextFormatIndex < formattingEntriesForLine.size(); rightTextFormatIndex++){
				//
				endSplitIndex = formattingEntriesForLine.get(rightTextFormatIndex);
				if (endSplitIndex == -1){


					//no additional formatting newlines
					//draw from startIndex to the end of the line
					toDrawString = line;
					graphicsHandler.drawString(toDrawString, textX, textY);

					
					//need to update this stuff here, since it wouldn't be updated after breaking out of the loop
					startSplitIndex = endSplitIndex;
					textX = boxX;
					textY += textHeight;

					break;

				}
				else{


					//draw between the two newline points
					if (endSplitIndex != 0){
						toDrawString = line.substring(startSplitIndex, endSplitIndex- 1);
						graphicsHandler.drawString(toDrawString, textX, textY);

					}
					


					//if on the last index, also draw to the end of the line
					if (rightTextFormatIndex == formattingEntriesForLine.size() - 1){
						//reset the x back to the start of the line
						textX = boxX;
						//output the rest of the string
						textY += textHeight;
						if (endSplitIndex != 0){
							toDrawString = line.substring(endSplitIndex-1);
							
						}
						else{
							toDrawString = line;
						}
						graphicsHandler.drawString(toDrawString, textX, textY);

						
					}
					
					



				}

				//update the start to be the end so you don't redraw the previous section of text
				startSplitIndex = endSplitIndex;

				//make the text start at the beggining of the text box for everyline after the first
				textX = boxX;
				//move the drawing position down to the next line
				textY += textHeight;
				




			}

		}
		
	}

	//--FOR TESTING PURPOSES--

	/**
	 * outputs all of the contents of the arrays lists
	 * that contain the text and the info about formatting the text
	 */
	public void seeAllArrayContents()
    {
		System.out.println("\n printing all array contents:");
		System.out.println("-------------------------------");
		System.out.println("left text array:");
		for (int i = 0; i < leftText.size(); i++){
			System.out.println("	item: " + i+ ") = " + leftText.get(i));
		}
		System.out.println("left text formatting array:");
		for (int j = 0; j < leftTextFormatInfo.size(); j++){
			System.out.println("	for line number: " + j);
			for (int k = 0; k < leftTextFormatInfo.get(j).size(); k++){
				System.out.println("		entry #" + k+ " = "+ leftTextFormatInfo.get(j).get(k));
			}
		}


		System.out.println("right text array:");
		for (int l = 0; l < rightText.size(); l++){
			System.out.println("	item: " + l+ ") = " + rightText.get(l));
		}
		System.out.println("right text formatting array:");
		for (int m = 0; m < rightTextFormatInfo.size(); m++){
			System.out.println("	for line number: " + m);
			for (int n = 0; n < rightTextFormatInfo.get(m).size(); n++){
				System.out.println("		entry #" + n+ " = "+ rightTextFormatInfo.get(m).get(n));
			}
		}
		System.out.println("-------------------------------");

	}

	//--FOR THE ISSUE WITH ADDING/SETTING NEW ENTRIES--

	/**
	 * sets a new formatting index value to either the leftTextFormatInfo or rightTextFormatInfo
	 * @param isLeft = determines whether the leftTextFormatInfo (true) or the rightTextFormatInfo (false) is being editted
	 * @param outerIndex = the index representing each line within array list
	 * @param innerIndex = index representing the formatting values for a given line
	 * @param newValue = the value that will be added to the array list
	 */
	public void setNewFormattingEntry(boolean isLeft, int outerIndex, int innerIndex, int newValue)
	{
		/*
		here I copy across the contents of the last line as when I used my previous method (see below)
		it would change both the left and the right text format infos.
		original method:
		leftTextFormatInfo.get(outerIndex).set(innerIndex, newValue);
		*/
		//
		if (isLeft){
			ArrayList<Integer> formattingForTheGivenLine = leftTextFormatInfo.get(outerIndex);
			ArrayList<Integer> newEntry = new ArrayList<Integer>();
			//creates a copy of the formatting entries for the given line
			for (Integer newlineIndex : formattingForTheGivenLine) {
				newEntry.add((Integer)newlineIndex);
			}
			//replaces the new 'set' value
			newEntry.set(innerIndex, newValue);
			//updates the left text format info array list
			leftTextFormatInfo.set(outerIndex, newEntry);
		}
		else{

			

			ArrayList<Integer> formattingForTheGivenLine = rightTextFormatInfo.get(outerIndex);
			ArrayList<Integer> newEntry = new ArrayList<Integer>();
			//creates a copy of the formatting entries for the given line
			for (Integer newlineIndex : formattingForTheGivenLine) {
				newEntry.add((Integer)newlineIndex);
			}
			//replaces the new 'set' value
			newEntry.set(innerIndex, newValue);

			//updates the right text format info array list
			rightTextFormatInfo.set(outerIndex, newEntry);
			
		}
		
		
	}

	/**
	 * adds a new formatting index value to either the leftTextFormatInfo or rightTextFormatInfo
	 * @param isLeft = determines whether the leftTextFormatInfo (true) or the rightTextFormatInfo (false) is being editted
	 * @param outerIndex = the index representing each line within array list
	 * @param newValue = the value that will be added to the array list
	 */
	public void addNewFormattingEntry(boolean isLeft, int outerIndex, int newValue)
	{
		/*
		here I copy across the contents of the last line as when I used my previous method (see below)
		it would change both the left and the right text format infos.
		original method:
		leftTextFormatInfo.get(leftTextFormatInfo.size() - 1).set(0, newSplitPosition);
		*/
		//
		if (isLeft){
			ArrayList<Integer> formattingForTheGivenLine = leftTextFormatInfo.get(outerIndex);
			ArrayList<Integer> newEntry = new ArrayList<Integer>();
			//creates a copy of the formatting entries for the given line
			for (Integer newlineIndex : formattingForTheGivenLine) {
				newEntry.add((Integer)newlineIndex);
			}
			//adds the new value
			newEntry.add(newValue);
			//updates the left text format info array list
			leftTextFormatInfo.set(outerIndex, newEntry);
		}
		else{
			ArrayList<Integer> formattingForTheGivenLine = rightTextFormatInfo.get(outerIndex);
			ArrayList<Integer> newEntry = new ArrayList<Integer>();
			//creates a copy of the formatting entries for the given line
			for (Integer newlineIndex : formattingForTheGivenLine) {
				newEntry.add((Integer)newlineIndex);
			}
			//adds the new value
			newEntry.add(newValue);
			//updates the right text format info array list
			rightTextFormatInfo.set(outerIndex, newEntry);
		}
	}

	//** not sure about remove stuff? (think this is only for the backspace stuff (?))
}