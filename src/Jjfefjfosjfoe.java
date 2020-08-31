


public class Jjfefjfosjfoe
{
    //UserEnteredTextLine
	public static ArrayList<StringLine> allLines = new ArrayList<StringLine>();        //contains all of the line objects
	public static ArrayList<Integer> lineRowEndIndexes = new ArrayList<Integer>(); //each index represents the line number (each seperate line object); the value at a given index represents the row number (going down = increment by 1) that this line ends on 

	private String fullText;												 //entire string
	private ArrayList<Integer> newLineIndexes = new ArrayList<Integer>();  //holds a list of all of the indexes where a newline is added, in order to fit this line into the text box

	public StringLine(String thisLine)
	{
		//
		fullText = thisLine;
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
	 * finds the positions where the line would be longer than the text box
	 * width, and adds newlines to allow the text to fit entirely into the box
	 * 
	 * call when:
	 * -the line has changed in length (line has been changed)
	 * -special occassion where the left text meets the right text -- so if first line of the right text, need to check the last line of the left text
	 * -text box width has changed
	 */
	public void recalculateNewLines(int textBoxWidth, Graphics2D graphicsHandler)
	{
		int splits = floor(textBoxWidth / thisLineWidth);
		if (splits > 0){
			//
		}
	}


} 