import javax.imageio.ImageIO;
import javax.swing.*;//used for gui generation

import java.awt.*;//used for layout managers
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.event.*;


/*
the cursor does not update when typing in a new position
since it is repeatedly going to the spot that the user last clicked instead of going to the new most left spot-
maybe solbe this by just only re-adjusting it at the point of the click as any time after the user has began
typing again it must be at the end of the left text.
*/


// source to get click detection to work
// https://stackoverflow.com/questions/27678603/how-to-check-mouse-click-in-2d-graphics-java
// accessed: 12/05/2020

class ClickPanel extends JPanel implements MouseListener, KeyListener {
	 
	//Initialising drawing surface here
	Graphics2D drawingSurface;
	BufferedImage i;
	Graphics2D g;

	// JFrame object
	WindowHandle thisWindow;

	// used to close the window
	private boolean exiting = false;

	// determines whether graphics have been drawn onto the screen
	private boolean rendered = false;

	//location of the powerpoint
	String slideImgLocation = "images/"; // converted images from pdf document are saved here
	String slidePDFLocation = "test_pp.pdf";
	boolean slideImageUpdated;

	//new slide stuff
	int imagePageNumber = 1; 
	

	//slide thread handler
	//UpdateSlideThread slideThreadHandler;
	boolean setSlideThread = false;

	//mouse coordinate stuff
	Point currentPointerPosition;
	int pointXCoord;
	int pointYCoord;

	boolean setCoordinateStuff;

	//next question button details 
	boolean nextQuestionClicked;

	//forward page button details
	boolean forwardPageButtonClicked;

	//backward page button details
	boolean backwardPageButtonClicked;

	//tick button details
	boolean tickButtonClicked;

	//'X' button details
	boolean xButtonClicked;

	//text box details
	boolean textBoxEntered; 

	//text box text stuff
	String textBoxTextLeftOfCursor;
	String textBoxTextRightOfCursor = "";
	int totalNumTextBoxLines;

	//text box scroll stuff
	int scrollAmount;
	boolean canScroll;

	//question stuff
	int questionsComplete;
	int currentQuestion;
	boolean currentQuestionComplete;
	boolean needToConfirmCorrectness;

	//pdf handler
	ConvertPDFPagesToImages pdfHandler;
	boolean pdfHandlerSet;

	//indicator text stuff
	boolean mostRecentSlide;
	boolean displayNoMoreSlidesText;
	boolean firstSlide;


	//---DRAWING STUFF START---

	//--page indicator--
	String currentPage;

	//--setting the window dimensions--
	int windowWidth;
	int windowHeight;
	boolean setWindowDimensions;

	//--drawing title string stuff--
	String titleString;
	int titleTextWidth;
	int titleTextHeight;
	int titleTextX;
	int titleTextY;

	boolean setTitleDrawingStuff;

	//--drawing the slide image--
	File slideImg;
	String introImgLocation; 
	File introImg;
	BufferedImage img;
	int slideImageWidth;
	int slideImageHeight;
	int slideWidth;
	int slideHeight;
	BufferedImage resizedImg;
	int imgTopLeftX;
	int imgTopLeftY;

	boolean setSlideDrawingStuff;

	//--slide additional/relative dimensions--
	int slideTopY;
	int slideLeftX ;
	int slideBottom;
	int distSlideBottomToScreenBottom;

	boolean setSlideAdditionalDimensions;

	//--drawing the questions complete text stuff--
	String questionsCompleteFullString;	
	int questionsCompleteTextWidth;
	int questionsCompleteTextHeight;
	int questionsCompleteTextX;
	int questionsCompleteTextY;

	boolean setQuestionsCompleteTextStuff;

	//--drawing the current question text--
	String currentQuestionFullString;
	int currentQuestionTextWidth;
	int currentQuestionTextHeight;
	int currentQuestionTextX;
	int currentQuestionTextY;

	boolean setCurrentQuestionTextStuff;

	//--drawing the button spacing stuff--
	int buttonSpacing;
	int buttonWidth;
	int buttonHeight;

	boolean setButtonSpacingStuff;

	//--drawing the back page button--
	int backPageButtonX;
	int backPageButtonY;
	int backPageButtonRight;
	int backPageButtonBottom;

	boolean setBackPageButtonStuff;

	//--drawing the arrow on the back page button--
	int backPageArrowCentreX;
	int backPageArrowCentreY;
	int arrowHeight;
	int backPageArrowTopStartX;
	int backPageArrowTopStartY;
	int backPageArrowTopEndX;
	int backPageArrowTopEndY;
	int backPageArrowBottomStartX;
	int backPageArrowBottomStartY;
	int backPageArrowBottomEndX;
	int backPageArrowBottomEndY;

	boolean setBackPageButtonArrowStuff;

	//--drawing the forward page button--
	int forwardPageButtonX;
	int forwardPageButtonY;
	int nextPageButtonRight;
	int nextPageButtonBottom;

	boolean setForwardPageButtonStuff;

	//--drawing the arrow on the forward page button--
	int forwardPageArrowCentreX;
	int forwardPageArrowCentreY;
	int forwardPageArrowTopStartX;
	int forwardPageArrowTopStartY;
	int forwardPageArrowTopEndX;
	int forwardPageArrowTopEndY;
	int forwardPageArrowBottomStartX;
	int forwardPageArrowBottomStartY;
	int forwardPageArrowBottomEndX;
	int forwardPageArrowBottomEndY;

	boolean setForwardPageButtonArrowStuff;

	//--drawing the next question button--
	int nextQuestionButtonX;
	int nextQuestionButtonY;
	int nextQuestionButtonRight;
	int nextQuestionButtonBottom;
	int stringWidth;
	int stringHeight;
	int nextQuestionButtonTextX;
	int nextQuestionButtonTextY;

	boolean setNextQuestionButtonStuff;

	//--drawing the answer text box--
	int answerTextBoxSpacing;
	int answerTextBoxWidth;
	int answerTextBoxHeight;
	int answerTextBoxX;
	int answerTextBoxY; 
	int answerTextBoxRight;
	int answerTextBoxBottom;

	boolean setAnswerTextBoxStuff;

	
	//--drawing text in the text box--
	int textBoxTextLeftOfCursorWidth;
	int textBoxTextLeftOfCursorHeight;
	int textBoxTextLeftOfCursorX;
	int textBoxTextLeftOfCursorY;
	int drawingTextStartY;
	boolean exceededTextBoxHeight;
	int lineCount;
	int lastSpace;
	int lastNewLine;
	String lastWord;
	String textMinusLastWord;
	String textMinusLastWordWithNewLine;
	int textLength;
	String lastTwoCharacters;
	String lastTwoRemoved;

	int blinkingCursorX;
	int blinkingCursorY;
	int blinkingCursorW;
	int blinkingCursorH;
	int blinkingCursorCount = 0;
	int textBoxNewClickPositionX = -1;
	int textBoxNewClickPositionY = -1;

	boolean setDrawingTextInTextBoxStuff;

	//--drawing the right text in the text box--
	int textBoxTextRightOfCursorWidth;
	int textBoxTextRightOfCursorHeight;
	int textBoxTextRightOfCursorX;
	int textBoxTextRightOfCursorY;
	int drawingRightTextStartY;		
	int leftLineWidth = 0;
	int linesLastTime = 0;
	
	boolean setDrawingRightTextInTextBoxStuff = false;


	//--drawing the red 'X' button--
	String redXButtonImgLocation; 
	File redXButtonImg;
	BufferedImage redXBimg;
	int redXButtonWidth;
	int redXButtonHeight;
	BufferedImage redXBimgResized;
	int redXButtonSpacing;
	int redXButtonX;
	int redXButtonY; 
	int redXButtonRight;
	int redXButtonBottom ;
	double redXBrightness;
	RescaleOp redXBrightnessRescaleOp;
	int redXOutlineX;
	int redXOutlineY;
	int redXOutlineW;
	int redXOutlineH;

	boolean setRedXStuff;

	//--drawing the green tick button--
	String greenTickButtonImgLocation; 
	File greenTickButtonImg;
	BufferedImage greenTickBimg;
	int greenTickButtonWidth;
	int greenTickButtonHeight;
	BufferedImage greenTickBimgResized;
	int greenTickButtonSpacing;
	int greenTickButtonX;
	int greenTickButtonY; 
	int greenTickButtonRight;
	int greenTickButtonBottom ;
	double greenTickBrightness;
	RescaleOp greenTickBrightnessRescaleOp;
	int greenTickOutlineX;
	int greenTickOutlineY;
	int greenTickOutlineW;
	int greenTickOutlineH;

	boolean setGreenTickStuff;

	//--drawing the done question tick--
	String doneTickImgLocation; 
	File doneTickImg;
	BufferedImage doneTickBimg;
	int doneTickWidth;
	int doneTickHeight;
	BufferedImage doneTickBimgResized;
	

	boolean setDoneQuestionTickStuff;

	//--drawing the error text--
	String errorTextString;
	int errorTextStringWidth ;
	int errorTextStringHeight;
	int errorTextStringX;
	int errorTextStringY;

	boolean setErrorTextStuff;

	//--drawing the back to menu button--
	int backToMenuButtonX;
	int backToMenuButtonY;
	int backToMenuButtonRight;
	int backToMenuButtonBottom;
	boolean backToMenuButtonClicked;
	String backToMenuButtonText;
	int backToMenuButtonTextX;
	int backToMenuButtonTextY;
	int backToMenuButtonTextWidth;
	int backToMenuButtonTextHeight;


	boolean setBackToMenuButtonStuff;
							
	
	//---}}---

	//---DRAWING THE START PAGE START---

	//--drawing the start page title--
	int startPageTitleWidth;
	int startPageTitleHeight;
	int startPageTitleX;
	int startPageTitleY;
	String startPageTitleTextUpper;
	int startPageTitleUpperTextWidth;
	int startPageTitleUpperTextHeight;
	int startPageTitleUpperTextX;
	int startPageTitleUpperTextY;
	String startPageTitleTextLower;
	int startPageTitleLowerTextWidth;
	int startPageTitleLowerTextHeight;
	int startPageTitleLowerTextX;
	int startPageTitleLowerTextY;

	boolean setStartPageTitleStuff;

	//--drawing the start page main box--
	int startPageTitleBottom;
	int startPageMainBoxOuterWidth;
	int startPageMainBoxOuterHeight;
	int startPageMainBoxOuterX;
	int startPageMainBoxOuterY;
	int startPageMainBoxInnerWidth;
	int startPageMainBoxInnerHeight;
	int startPageMainBoxInnerX;
	int startPageMainBoxInnerY;

	boolean setStartPageMainBoxStuff;

	//--drawing the loaded powerpoint text--
	String startPageLoadedPowerpointText;
	int startPageLoadedPowerpointTextWidth;
	int startPageLoadedPowerpointTextHeight;
	int startPageLoadedPowerpointTextX;
	int startPageLoadedPowerpointTextY;

	boolean setLoadedPowerpointTextStuff;

	//--initiating the side spacing variables--
	int sideSpacing;
	boolean setBoxSpacing;

	//--drawing the loaded filename--
	int startPageLoadedFilenameBoxWidth;
	int startPageLoadedFilenameBoxHeight;
	int startPageLoadedFilenameBoxX;
	int startPageLoadedFilenameBoxY;
	String startPageLoadedFilenameText;
	int startPageLoadedFilenameTextWidth;
	int startPageLoadedFilenameTextHeight;
	int startPageLoadedFilenameTextX;
	int startPageLoadedFilenameTextY;

	boolean setLoadedFilenameStuff;

	//--drawnig the launch quiz button--
	int startPageLaunchQuizButtonWidth;
	int startPageLaunchQuizButtonHeight;
	int startPageLaunchQuizButtonX;
	int startPageLaunchQuizButtonY;
	String startPageLaunchQuizButtonText;
	int startPageLaunchQuizButtonTextWidth;
	int startPageLaunchQuizButtonTextHeight;
	int startPageLaunchQuizButtonTextX;
	int startPageLaunchQuizButtonTextY;
	boolean startPageLaunchQuizButtonClicked;
	int startPageLaunchQuizButtonClickedTick;

	boolean setLaunchQuizButtonStuff;

	//--drawing the change file button stuff--
	int startPageChangeFileButtonWidth;
	int startPageChangeFileButtonHeight;
	int startPageChangeFileButtonX;
	int startPageChangeFileButtonY;
	String startPageChangeFileButtonText;
	int startPageChangeFileButtonTextWidth;
	int startPageChangeFileButtonTextHeight;
	int startPageChangeFileButtonTextX;
	int startPageChangeFileButtonTextY;
	boolean startPageChangeFileButtonClicked;
	int startPageChangeFileButtonClickedTick;


	boolean setChangeFileButtonStuff;

	//--drawing the reset completed questions file stuff--
	int startPageResetCompletedQuestionsButtonWidth;
	int startPageResetCompletedQuestionsButtonHeight;
	int startPageResetCompletedQuestionsButtonX;
	int startPageResetCompletedQuestionsButtonY;
	String startPageResetCompletedQuestionsButtonText;
	int startPageResetCompletedQuestionsButtonTextWidth;
	int startPageResetCompletedQuestionsButtonTextHeight;
	int startPageResetCompletedQuestionsButtonTextX;
	int startPageResetCompletedQuestionsButtonTextY;
	boolean startPageResetCompletedQuestionsButtonClicked;
	int startPageResetCompletedQuestionsButtonClickedTick;
	
	boolean setResetCompletedQuestionsButtonStuff;


	//--drawing the slide mode text--
	String startPageSlideModeText;
	int startPageSlideModeTextWidth ;
	int startPageSlideModeTextHeight;
	int startPageSlideModeTextX ;
	int startPageTextY;
	
	boolean setSlideModeTextStuff = false;

	//--drawing the slide mode 1 button--
	int startPageSlideMode1ButtonWidth;
	int startPageSlideMode1ButtonHeight;
	int startPageSlideMode1ButtonX;
	int startPageSlideMode1ButtonY;
	boolean startPageSlideMode1ButtonClicked;
	int startPageSlideMode1ButtonClickedTick;
	String startPageSlideMode1ButtonText;
	int startPageSlideMode1ButtonTextWidth;
	int startPageSlideMode1ButtonTextHeight;
	int startPageSlideMode1ButtonTextX;
	int startPageSlideMode1ButtonTextY;
	boolean startPageSlideMode1ButtonSelected = true;
	int startPageSlideMode1ButtonOutlineX;
	int startPageSlideMode1ButtonOutlineY;
	int startPageSlideMode1ButtonOutlineW;
	int startPageSlideMode1ButtonOutlineH;

	boolean setSlideMode1ButtonStuff = false;

	//--drawing the slide mode 2 button--
	int startPageSlideMode2ButtonWidth;
	int startPageSlideMode2ButtonHeight;
	int startPageSlideMode2ButtonX;
	int startPageSlideMode2ButtonY;
	boolean startPageSlideMode2ButtonClicked;
	int startPageSlideMode2ButtonClickedTick;
	String startPageSlideMode2ButtonText;
	int startPageSlideMode2ButtonTextWidth;
	int startPageSlideMode2ButtonTextHeight;
	int startPageSlideMode2ButtonTextX;
	int startPageSlideMode2ButtonTextY;
	boolean startPageSlideMode2ButtonSelected = false;
	int startPageSlideMode2ButtonOutlineX;
	int startPageSlideMode2ButtonOutlineY;
	int startPageSlideMode2ButtonOutlineW;
	int startPageSlideMode2ButtonOutlineH;
	
	boolean setSlideMode2ButtonStuff = false;

	//--drawing the slide mode 3 button--
	int startPageSlideMode3ButtonWidth;
	int startPageSlideMode3ButtonHeight;
	int startPageSlideMode3ButtonX;
	int startPageSlideMode3ButtonY;
	boolean startPageSlideMode3ButtonClicked;
	int startPageSlideMode3ButtonClickedTick;
	String startPageSlideMode3ButtonText;
	int startPageSlideMode3ButtonTextWidth;
	int startPageSlideMode3ButtonTextHeight;
	int startPageSlideMode3ButtonTextX;
	int startPageSlideMode3ButtonTextY;
	boolean startPageSlideMode3ButtonSelected = false;
	int startPageSlideMode3ButtonOutlineX;
	int startPageSlideMode3ButtonOutlineY;
	int startPageSlideMode3ButtonOutlineW;
	int startPageSlideMode3ButtonOutlineH;
	
	boolean setSlideMode3ButtonStuff = false;

	//--drawing the slide order text--
	String startPageSlideOrderText;
	int startPageSlideOrderTextWidth;
	int startPageSlideOrderTextHeight;
	int startPageSlideOrderTextX ;
	int startPageSlideOrderTextY;
	
	boolean setSlideOrderTextStuff = false;
	
	//--drawing the slide order 1 button--
	int startPageSlideOrder1ButtonWidth;
	int startPageSlideOrder1ButtonHeight;
	int startPageSlideOrder1ButtonX;
	int startPageSlideOrder1ButtonY;
	boolean startPageSlideOrder1ButtonClicked;
	int startPageSlideOrder1ButtonClickedTick;
	String startPageSlideOrder1ButtonText;
	int startPageSlideOrder1ButtonTextWidth;
	int startPageSlideOrder1ButtonTextHeight;
	int startPageSlideOrder1ButtonTextX;
	int startPageSlideOrder1ButtonTextY;
	boolean startPageSlideOrder1ButtonSelected = true;
	int startPageSlideOrder1ButtonOutlineX;
	int startPageSlideOrder1ButtonOutlineY;
	int startPageSlideOrder1ButtonOutlineW;
	int startPageSlideOrder1ButtonOutlineH;

	boolean setSlideOrder1ButtonStuff = false;

	//--drawing the slide order 2 button--
	int startPageSlideOrder2ButtonWidth;
	int startPageSlideOrder2ButtonHeight;
	int startPageSlideOrder2ButtonX;
	int startPageSlideOrder2ButtonY;
	boolean startPageSlideOrder2ButtonClicked;
	int startPageSlideOrder2ButtonClickedTick;
	String startPageSlideOrder2ButtonText;
	int startPageSlideOrder2ButtonTextWidth;
	int startPageSlideOrder2ButtonTextHeight;
	int startPageSlideOrder2ButtonTextX;
	int startPageSlideOrder2ButtonTextY;
	boolean startPageSlideOrder2ButtonSelected = false;
	int startPageSlideOrder2ButtonOutlineX;
	int startPageSlideOrder2ButtonOutlineY;
	int startPageSlideOrder2ButtonOutlineW;
	int startPageSlideOrder2ButtonOutlineH;

	boolean setSlideOrder2ButtonStuff = false;

	//--drawing the reset completed questions indication text--
	String startPageResetQuestionsIndicationText;
	int startPageResetQuestionsIndicationTextWidth;
	int startPageResetQuestionsIndicationTextHeight;
	int startPageResetQuestionsIndicationTextX;
	int startPageResetQuestionsIndicationTextY;
	boolean startPageResetQuestionsIndicationTextActivated;
	int startPageResetQuestionsIndicationTextActivatedTick;

	boolean setResetQuestionsIndicationText;
	

	//---}}---

	/**
	 * initialisation for the creation of the click panel object
	 */
	public ClickPanel(int width, int height, WindowHandle motherTable) {

		thisWindow = motherTable;

		// Add mouse Listener
		addMouseListener(this);
		//

		setPreferredSize(new Dimension(width, height));
		initializeDefaultValueVariables();

	}

	/**
	 * resets everything that has 
	 * a default value to what it would be 
	 * if the application was closed and re-opened
	 */
	public void initializeDefaultValueVariables()
	{
		slideImageUpdated = true;
		//
		setCoordinateStuff = false;

		//next question button details 
		
		nextQuestionClicked = false;

		//forward page button details
		
		forwardPageButtonClicked = false;


		//backward page button details		
		backwardPageButtonClicked = false;

		//tick button details
		tickButtonClicked = false;

		//'X' button details
		xButtonClicked = false;

		//text box details
	
		textBoxEntered = false; 

		textBoxTextLeftOfCursor = "";
		totalNumTextBoxLines = 1;

		scrollAmount = 0;
		canScroll = false;

		questionsComplete = 0;
		currentQuestion = 0;
		currentQuestionComplete = false;
		needToConfirmCorrectness = false;

		pdfHandlerSet = false;

		mostRecentSlide = false;
		displayNoMoreSlidesText = false;
		firstSlide = false;


		currentPage = "1";

		//set stuff
		setWindowDimensions = false;
		setTitleDrawingStuff = false;
		setSlideDrawingStuff = false;
		setSlideAdditionalDimensions = false ;
		setQuestionsCompleteTextStuff = false;
		setCurrentQuestionTextStuff = false;
		setButtonSpacingStuff = false;
		setBackPageButtonStuff = false;
		setBackPageButtonArrowStuff = false;
		setForwardPageButtonStuff = false;
		setForwardPageButtonArrowStuff = false;
		setNextQuestionButtonStuff = false;
		setAnswerTextBoxStuff = false;
		setDrawingTextInTextBoxStuff = false;
		setRedXStuff = false;
		setGreenTickStuff = false;
		setDoneQuestionTickStuff = false;
		setErrorTextStuff = false;
		setBackToMenuButtonStuff = false;
		setStartPageTitleStuff = false;
		setStartPageMainBoxStuff = false;
		setLoadedPowerpointTextStuff = false;
		setBoxSpacing = false;
		setLoadedFilenameStuff = false;
		setLaunchQuizButtonStuff = false;
		setChangeFileButtonStuff = false;
		setResetCompletedQuestionsButtonStuff = false;
		setResetQuestionsIndicationText = false;
	}


	

//--GETTERS & SETTERS--

	/**
	 * @return the slide mode 1 value
	 */
	public boolean getSlideMode1()
	{
		return startPageSlideMode1ButtonSelected;
	}

	/**
	 * @return the slide mode 2 value
	 */
	public boolean getSlideMode2()
	{
		return startPageSlideMode2ButtonSelected;
	}

	/**
	 * @return the slide mode 3 value
	 */
	public boolean getSlideMode3()
	{
		return startPageSlideMode3ButtonSelected;
	}

	/**
	 * @return the slide order 1 value
	 */
	public boolean getSlideOrder1()
	{
		return startPageSlideOrder1ButtonSelected;
	}

	/**
	 * @return the slide order 2 value
	 */
	public boolean getSlideOrder2()
	{
		return startPageSlideOrder2ButtonSelected;
	}
	
	/**
	 * @return the file path to the powerpoint pdf file
	 */
	public String getPowerPointLocation()
	{
		return slidePDFLocation;
	}

	/**
	 * sets the need to confirm correctness variable to the passed value
	 */
	public void setNeedToConfirmCorrectness(boolean state)
	{
		needToConfirmCorrectness = state;
	}

	/**
	 * @return the correctness variable indication
	 */
	public boolean getCorrectness()
	{
		return needToConfirmCorrectness;
	}

	/**
	 * @param needsUpdating = the new value of the slideImageUpdated 
	 * sets the slide image update variable to the passed value
	 */
	public void setJustChangedSlide(boolean needsUpdating)
	{
		slideImageUpdated = needsUpdating;

	}

	/**
	 * @param x = the new x coordinate of the click point within the text box
	 * @param y = the new y coordinate of the click point within the text box
	 */
	public void setTextBoxNewClickPositions(int x, int y)
	{
		textBoxNewClickPositionX = x;
		textBoxNewClickPositionY = y;
	}

	/**
	 * sets the text left of the cursor to be everything left of the new 
	 * cursor position, and vice versa for everything to the right of 
	 * the new cursor position
	 * @param x = the new x coordinate of the click point within the text box
	 * @param y = the new y coordinate of the click point within the text box
	 */
	public void updateLeftAndRightText(int x, int y)
	{
		//need to get the letter that the click is the closest to 
		//-need to add the scroll amount to the click position
		//-jump

	

		/*
		if ((y <= defaultCursorY + g.getFontMetrics().getHeight()))
		{
			if ((y >= defaultCursorY) && (x > defaultCursorX)){
			}
			else{
				int avgCharWidth = g.getFontMetrics().stringWidth("a");
				int avgCharHeight = g.getFontMetrics().getHeight();
				
				int numberOfVerticalRows = (int) Math.rint((y - answerTextBoxY) / avgCharHeight);
				blinkingCursorY =answerTextBoxY + numberOfVerticalRows*avgCharHeight;
				int numberOfHorizontalColumns = (int) Math.rint((x- answerTextBoxX) / avgCharWidth);
				blinkingCursorX =answerTextBoxX + numberOfHorizontalColumns*avgCharWidth;

			}
		}
		*/


		//--getting the entire string--
		String totalString = textBoxTextLeftOfCursor + textBoxTextRightOfCursor;

		System.out.println("1");

		//--getting the average character dimensions--
		int avgCharWidth = g.getFontMetrics().stringWidth("a");
		int avgCharHeight = g.getFontMetrics().getHeight();

		System.out.println("2");

		//--predicting the number of rows & columns (each character representing a col.) that the new cursor will lie in--
		int yDiff = answerTextBoxY - y;
		int approxRowNum = (yDiff / avgCharHeight) + (scrollAmount/avgCharHeight);

		int xDiff = x - answerTextBoxX;
		int approxColNum = (xDiff / avgCharWidth);

		System.out.println("3");

		//--getting all of the string on the lines above & below the line that the cursor is suspected to be in--
		int indexOfNewLineBeforeClickPos = ordinalIndexOf(totalString, "\n", approxRowNum-1);
		String stringAboveSelectedLine;
		System.out.println("3.25");
		if (indexOfNewLineBeforeClickPos != -1)
		{
			stringAboveSelectedLine = totalString.substring(0, indexOfNewLineBeforeClickPos);
		}
		else{
			stringAboveSelectedLine = "";
		}

		System.out.println("3.5");

		int indexOfNewLineAfterClickPos = ordinalIndexOf(totalString, "\n", approxRowNum);
		System.out.println("3.75");

		String stringBelowSelectedLine; 



		if (indexOfNewLineAfterClickPos != -1)
		{
			stringBelowSelectedLine = totalString.substring(indexOfNewLineAfterClickPos);
		}
		else{
			stringBelowSelectedLine = "";
		}

		System.out.println("4");
		//--getting the suspected index of character that the cursor will lie after--
		int totalCharsToLeft = stringAboveSelectedLine.length();
		int splitPosition = totalCharsToLeft + approxColNum;

		//--creating the new strings from the left and right of the new cursor position--
		String newLeftOfCursor = totalString.substring(0, splitPosition);
		String newRightOfCursor = totalString.substring(splitPosition);

		System.out.println("new left of cursor = :" +newLeftOfCursor+":");
		System.out.println("new right of cursor = :" +newRightOfCursor+":");

		textBoxTextLeftOfCursor = newLeftOfCursor;
		textBoxTextRightOfCursor = newRightOfCursor;


	}
	

//START OF TEXT INDICATOR METHODS

	/**
	 * most resent slide is the last answer slide of the most resent question 
	 * that was displayed by the user clicking the next question button
	 * @param status = the new indicator of whether the user is currently on the most recent slide or not
	 */
	public void setMostResentSlide(boolean status)
	{
		mostRecentSlide = status;
	}

	/**
	 * no more slides is displayed when 
	 * the user has visited all of the slides in the powerpoint
	 * and there are no slides that have not been checked
	 * @param status = the new indicator of whether to display the no more slides text
	 */
	public void setNoMoreSlideText(boolean status)
	{
		displayNoMoreSlidesText = status;
	}

	/**
	 * this text is displayed when the user is on the first slide that was displayed after initially clicking the 
	 * next question button, in order to initiate the quiz
	 * @param status = the new indicator relating to whether the user is on the first slide or not
	 */
	public void setFirstSlideStatus(boolean status)
	{
		firstSlide = status;
	}

//END OF TEXT INDICATOR METHODS

//--}}--


    /**
     * Called by the OS to
     * draw onto the screen
	 * @param gr = the graphics that will be used to draw onto the screen
     */
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);

		// getting the items from the table JFrame
		if (setWindowDimensions == false){
			//
			windowWidth = thisWindow.getwindowWidth();
			windowHeight = thisWindow.getwindowHeight();
			setWindowDimensions = true;
		}
		

        //sets the size of the JPanel if it has not been rendered
		if (!rendered) {
			this.setSize(windowWidth, windowHeight);
			i = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);

			rendered = true;
		}

        //setting up the graphical elements 
		drawingSurface = (Graphics2D) gr;
		g = i.createGraphics();



		//makes shapes have smooth edges (I think)
		g.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		//makes the text smoother (no jagged edges)
		g.setRenderingHint(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// no more than one thread will be able to access the code inside that block.
		// make sure that in a multi-threaded application,
		// only one thread can access a critical data structure at a given time.

		// prevents multiple [things] from trying to access this at once
		// to prevent corruption
		synchronized (this) {
			// if the window is running
			if (!this.exiting) {
				// this is where to draw on screen

				//--GETTING THE CURRENT MOUSE COORDINATES--
				currentPointerPosition = MouseInfo.getPointerInfo().getLocation();
				pointXCoord = (int) currentPointerPosition.getX();
				pointYCoord = (int) currentPointerPosition.getY();

				setCoordinateStuff = true; //indicates that the above variables can be referenced

				//--DRAWNIG THE BACKGROUND--
				g.setColor(new Color(172, 200, 255));
				g.fillRect(0, 0, windowWidth, windowHeight);

				//--DRAWING THE REST OF THE PAGE--
				if (currentPage == "2"){
					drawQuiz();
				}
				else if (currentPage == "1"){
					//
					drawStartPage();
				}

			}
            
            //draws onto the screen
			drawingSurface.drawImage(i, this.getInsets().left, this.getInsets().top, this);

		}

	}

	/*
	https://stackoverflow.com/questions/9417356/bufferedimage-resize
	*/
	/**
	 * @param img = the image that is to be resized
	 * @param newW = the new width of the image after the transformation
	 * @param newH = the new height of the image after the transformation
	 * @return the newly resized image
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 

		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
	
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
	
		return dimg;
	}

//CLICK DETECTION & REACTION
  
    //Required methods for MouseListener, though the only one you care about is click
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    /** Called whenever the mouse clicks.
		* Could be replaced with setting the value of a JLabel, etc.
		@param e = the fetched input from the action that the mouse performed */
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        double pointXCoord = p.getX();
        double pointYCoord = p.getY();
		if (currentPage == "1") {
			startPageLaunchQuizButtonClickCheck(pointXCoord, pointYCoord);
			startPageChangeFileButtonClickCheck(pointXCoord, pointYCoord);
			startPageResetCompletedQuestionsButtonClickCheck(pointXCoord, pointYCoord);
			startPageSlideMode1ButtonClickCheck(pointXCoord, pointYCoord);
			startPageSlideMode2ButtonClickCheck(pointXCoord, pointYCoord);
			startPageSlideMode3ButtonClickCheck(pointXCoord, pointYCoord);
			startPageSlideOrder1ButtonClickCheck(pointXCoord, pointYCoord);
			startPageSlideOrder2ButtonClickCheck(pointXCoord, pointYCoord);

		} else if (currentPage == "2") {
			nextQuestionButtonClickCheck(pointXCoord, pointYCoord);
			forwardButtonClickCheck(pointXCoord, pointYCoord);
			backwardButtonClickCheck(pointXCoord, pointYCoord);
			tickButtonClickCheck(pointXCoord, pointYCoord);
			xButtonClickCheck(pointXCoord, pointYCoord);
			textBoxClickCheck(pointXCoord, pointYCoord);
			backToMenuButtonClickCheck(pointXCoord, pointYCoord);
		}
        
	}

	/**
	 * returns whether the passed coordinates
	 * of a click intercept the passed values regarding 
	 * a rectangle acting as a button
	 * (checks if the button has been clicked)
	 * @param clickedXCoord
	 * @param clickedYCoord
	 * @param topLeftX
	 * @param topLeftY
	 * @param buttonWidth
	 * @param buttonHeight
	 * @return whether the buttons has been clicked (true) or not (false)
	 */
	public boolean rectButtonClickCheck(double clickedXCoord, double clickedYCoord, int topLeftX, int topLeftY, int buttonWidth, int buttonHeight)
	{
		int rightX = topLeftX + buttonWidth;
		int bottomY = topLeftY + buttonHeight;

		if ( (clickedXCoord > topLeftX) && (clickedXCoord < rightX) ){
			//it is in the x range
			if ( (clickedYCoord < bottomY) && (clickedYCoord > topLeftY) ){
				//it has been clicked (it is in the y range)
				return true;
			}
		}
		return false;
	}

	/**
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 * checks if the mouse click was inside of the next question button
	 * if so, sets the button to clicked and gets the question
	 */
	public void nextQuestionButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setNextQuestionButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, nextQuestionButtonX, nextQuestionButtonY, buttonWidth, buttonHeight)){
				//
				nextQuestionClicked = true;
				getNextQuestion();
			}			
		}
	}


	/**
	 * checks whether the forward button has been clicked
	 * 
	 */
	public void forwardButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setForwardPageButtonStuff == true){
			//
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, forwardPageButtonX, forwardPageButtonY, buttonWidth, buttonHeight)){
				//
				forwardPageButtonClicked = true;
				getForwardSlide();
			}	
		}
	}

	/**
	 * checks whether the backward button has been clicked
	 * 
	 */
	public void backwardButtonClickCheck(double clickedXCoord, double clickedYCoord) 
	{
		if (setBackPageButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord,backPageButtonX, backPageButtonY, buttonWidth, buttonHeight)){
				backwardPageButtonClicked = true;
				getBackwardSlide();
				
			}
		}
	}

	/**
	 * checks whether the tick button has been clicked
	 * 
	 */
	public void tickButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setGreenTickStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, greenTickButtonX, greenTickButtonY, greenTickButtonWidth, greenTickButtonHeight)){
				tickButtonClicked = true;
				greenTickClicked();
			}
		}
	}

	/**
	 * checks whether the 'X button has been clicked
	 * 
	 */
	public void xButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setRedXStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord,  redXButtonX, redXButtonY, redXButtonWidth, redXButtonHeight)){
				xButtonClicked = true;
				redXClicked();
			}
		}
	}

	/**
	 * checks whether the textbox has been clicked
	 * 
	 */
	public void textBoxClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setAnswerTextBoxStuff == true){
			//
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, answerTextBoxX, answerTextBoxY, answerTextBoxWidth, answerTextBoxHeight)){
					//if the user is already in the text box and wants to change their cursor position
					if (textBoxEntered == true){
						setTextBoxNewClickPositions((int) clickedXCoord, (int) clickedYCoord);
						updateLeftAndRightText((int) clickedXCoord, (int) clickedYCoord);
					}
					textBoxEntered = true;
			}
			else{
				textBoxEntered = false;
				//resets the text box cursor 
				setTextBoxNewClickPositions(-1, -1);

			}
		}
		else{
			textBoxEntered = false;
			//resets the text box cursor 
			setTextBoxNewClickPositions(-1, -1);
		}
	}

	public void backToMenuButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setBackToMenuButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, backToMenuButtonX, backToMenuButtonY, buttonWidth, buttonHeight)){
				currentPage = "1";
				backToMenuButtonClicked = true;
				//close the file since going back
				saveQuestionsCompleted();
				closePPFile();
				initializeDefaultValueVariables();

			}
		}
	}

//CHANGE TO START PAGE CLICK CHECKS

	public void startPageLaunchQuizButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setLaunchQuizButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageLaunchQuizButtonX, startPageLaunchQuizButtonY, startPageLaunchQuizButtonWidth, startPageLaunchQuizButtonHeight)){
				currentPage = "2";
				startPageLaunchQuizButtonClicked = true;
			}
		}
	}


	public void startPageChangeFileButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setChangeFileButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageChangeFileButtonX, startPageChangeFileButtonY, startPageChangeFileButtonWidth, startPageChangeFileButtonHeight)){
				changeLoadedFile();
				startPageChangeFileButtonClicked = true;
			}
		}
	}

	public void startPageResetCompletedQuestionsButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setResetCompletedQuestionsButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageResetCompletedQuestionsButtonX, startPageResetCompletedQuestionsButtonY, startPageResetCompletedQuestionsButtonWidth, startPageResetCompletedQuestionsButtonHeight)){
				setAllSlidesToUnseen(slidePDFLocation);
				startPageResetQuestionsIndicationTextActivated = true;
				startPageResetCompletedQuestionsButtonClicked = true;
			}
		}
	}
	
	public void startPageSlideMode1ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setSlideMode1ButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageSlideMode1ButtonX, startPageSlideMode1ButtonY, startPageSlideMode1ButtonWidth, startPageSlideMode1ButtonHeight)){
				//do stuff
				if (startPageSlideMode1ButtonSelected == false){
					startPageSlideMode1ButtonSelected = true;
				}
				else{
					if ((startPageSlideMode2ButtonSelected == true) || (startPageSlideMode3ButtonSelected == true)){
						//
						startPageSlideMode1ButtonSelected = false;
					}
				}
				//
				startPageSlideMode1ButtonClicked = true;
			}
		}
	}

	public void startPageSlideMode2ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setSlideMode2ButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageSlideMode2ButtonX, startPageSlideMode2ButtonY, startPageSlideMode2ButtonWidth, startPageSlideMode2ButtonHeight)){
				//do stuff
				if (startPageSlideMode2ButtonSelected == false){
					startPageSlideMode2ButtonSelected = true;
				}
				else{
					if ((startPageSlideMode1ButtonSelected == true) || (startPageSlideMode3ButtonSelected == true)){
						//
						startPageSlideMode2ButtonSelected = false;
					}
				}
				//
				startPageSlideMode2ButtonClicked = true;
			}
		}
	}

	public void startPageSlideMode3ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setSlideMode3ButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageSlideMode3ButtonX, startPageSlideMode3ButtonY, startPageSlideMode3ButtonWidth, startPageSlideMode3ButtonHeight)){
				//do stuff
				if (startPageSlideMode3ButtonSelected == false){
					startPageSlideMode3ButtonSelected = true;
				}
				else{
					if ((startPageSlideMode1ButtonSelected == true) || (startPageSlideMode2ButtonSelected == true)){
						//
						startPageSlideMode3ButtonSelected = false;
					}
				}
				//
				startPageSlideMode3ButtonClicked = true;
			}
		}
	}

	public void startPageSlideOrder1ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setSlideOrder1ButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageSlideOrder1ButtonX, startPageSlideOrder1ButtonY, startPageSlideOrder1ButtonWidth, startPageSlideOrder1ButtonHeight)){
				//do stuff
				if (startPageSlideOrder1ButtonSelected == false){
					startPageSlideOrder2ButtonSelected = false;
					startPageSlideOrder1ButtonSelected = true;	
				}			
				//
				startPageSlideOrder1ButtonClicked = true;
			}
		}
	}

	public void startPageSlideOrder2ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setSlideOrder2ButtonStuff == true){
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, startPageSlideOrder2ButtonX, startPageSlideOrder2ButtonY, startPageSlideOrder2ButtonWidth, startPageSlideOrder2ButtonHeight)){
				//do stuff
				if (startPageSlideOrder2ButtonSelected == false){
					startPageSlideOrder1ButtonSelected = false;
					startPageSlideOrder2ButtonSelected = true;	
				}			
				//
				startPageSlideOrder2ButtonClicked = true;
			}
		}
	}

	public void closePPFile()
	{
		if(pdfHandlerSet == true){
			//
			pdfHandler.closeDocument();
			pdfHandlerSet = false;
		}
	}

	public void saveQuestionsCompleted()
	{
		if(pdfHandlerSet == true){
			pdfHandler.exportCompletedSlides(true);
		}
	}
//}}
	

	/**
	 * changes the slide reference image to the appropriate question
	 * 
	 */
	public void getNextQuestion()
	{
		//
		if (pdfHandlerSet == false){
			pdfHandler = new ConvertPDFPagesToImages(this);
			pdfHandlerSet = true;

		}

		imagePageNumber = pdfHandler.changeSlide("newQuestion");/////////////////////////
		setMostResentSlide(false);
	}

	/**
	 * changes the slide reference image to the appropriate slide
	 * moving forward through the seen slides
	 * 
	 */
	public void getForwardSlide()
	{
		//
		if (pdfHandlerSet == true){
			imagePageNumber = pdfHandler.changeSlide("forward");/////////////////
		}
	}

	/**
	 * changes the slide reference image to the appropriate slide
	 * moving backward through the seen slides
	 * 
	 */
	public void getBackwardSlide()
	{
		//
		if (pdfHandlerSet == true){
			imagePageNumber = pdfHandler.changeSlide("back");////////////////
			setNoMoreSlideText(false);
			setMostResentSlide(false);
		}
	}

	/**
	 * 

	 */
	public void greenTickClicked()
	{
		textBoxTextLeftOfCursor = "";
		if (pdfHandlerSet == true){
			//write green tick stuff here 
			pdfHandler.handleGreenTickClicked();	
		}
	}
	
	public void redXClicked() 
	{
		textBoxTextLeftOfCursor = "";
		if (pdfHandlerSet == true){
			pdfHandler.handleRedXClicked();
			
		}
	}

	/**
	 * slightly different in that it adjust for Mac 
	 * being slightly off with the mouse y coord
	 * 
	 * @param pointX
	 * @param pointY
	 * @param rectLeft
	 * @param rectRight
	 * @param rectTop
	 * @param rectBottom
	 * @return
	 */
	public boolean isPointCollisionWithRectangle(int pointX, int pointY, int rectLeft, int rectRight, int rectTop, int rectBottom)
	{
		int macAdjust = 45;
		rectTop = rectTop + macAdjust;
		rectBottom = rectBottom + macAdjust;
		if ( (pointX > rectLeft) && (pointX < rectRight) ){
			//it is in the x range
			if ( (pointY < rectBottom) && (pointY > rectTop) ){
				// (it is in the y range)
				return true;
			}
		}
		return false;
	}

	public void incrementQuestionsCompleted()
	{
		questionsComplete++;
	}

	public void setCurrentQuestion(int currentQuestionNumber)
	{
		currentQuestion = currentQuestionNumber;

		//if not on page 1
		if (currentQuestion > 1){
			setFirstSlideStatus(false);
		}
	}



	//--key listener required methods--
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		String typedKey = String.valueOf(e.getKeyChar());
		
		if (e.getExtendedKeyCode() == 8){
			typedKey = "backspace";
		}
		else if (e.getExtendedKeyCode() == 10){
			typedKey = "enter";
		}
		else if (e.getExtendedKeyCode() == 16777383){
			typedKey = "exit";
		}


		HandleTextEntered(typedKey);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void HandleTextEntered(String typedChar)
	{
		if (currentPage == "1"){
			//start page
			if (typedChar == "enter"){
				//launch quiz button click
				currentPage = "2";
				startPageLaunchQuizButtonClicked = true;
			}
			else if (typedChar.equals("c")){
				//change file button click
				changeLoadedFile();
				startPageChangeFileButtonClicked = true;
			}
			else if (typedChar.equals("r")){
				//reset button click
				setAllSlidesToUnseen(slidePDFLocation);
				startPageResetQuestionsIndicationTextActivated = true;
				startPageResetCompletedQuestionsButtonClicked = true;
			}
			else if (typedChar.equals("1")){
				//slide mode 1 button click
				if (startPageSlideMode1ButtonSelected == false){
					startPageSlideMode1ButtonSelected = true;
				}
				else{
					if ((startPageSlideMode2ButtonSelected == true) || (startPageSlideMode3ButtonSelected == true)){
						//
						startPageSlideMode1ButtonSelected = false;
					}
				}
				//
				startPageSlideMode1ButtonClicked = true;
				
			}
			else if (typedChar.equals("2")){
				//slide mode 2 button click
				if (startPageSlideMode2ButtonSelected == false){
					startPageSlideMode2ButtonSelected = true;
				}
				else{
					if ((startPageSlideMode1ButtonSelected == true) || (startPageSlideMode3ButtonSelected == true)){
						//
						startPageSlideMode2ButtonSelected = false;
					}
				}
				//
				startPageSlideMode2ButtonClicked = true;
				
			}
			else if (typedChar.equals("3")){
				//slide mode 3 button click
				if (startPageSlideMode3ButtonSelected == false){
					startPageSlideMode3ButtonSelected = true;
				}
				else{
					if ((startPageSlideMode1ButtonSelected == true) || (startPageSlideMode2ButtonSelected == true)){
						//
						startPageSlideMode3ButtonSelected = false;
					}
				}
				//
				startPageSlideMode3ButtonClicked = true;
				
			}
			else if (typedChar.equals("[")){
				//slide option 1 button click
				if (startPageSlideOrder1ButtonSelected == false){
					startPageSlideOrder2ButtonSelected = false;
					startPageSlideOrder1ButtonSelected = true;	
				}			
				//
				startPageSlideOrder1ButtonClicked = true;
				
			}
			else if (typedChar.equals("]")){
				//slide option 2 button click
				if (startPageSlideOrder2ButtonSelected == false){
					startPageSlideOrder1ButtonSelected = false;
					startPageSlideOrder2ButtonSelected = true;	
				}			
				//
				startPageSlideOrder2ButtonClicked = true;
				
			}
			


		}
		else if (currentPage == "2"){
			//start page
			if (textBoxEntered == true){
				if (typedChar == "exit"){
					//user wants to exit the text box
					textBoxEntered = false;
					//resets the text box cursor 
					setTextBoxNewClickPositions(-1, -1);

				}
				else{
					//
					if (typedChar == "enter"){
						//
						typedChar = "\n";
					}
					updatetextBoxTextLeftOfCursor(typedChar);
				}
				
			}
			else{
				//not in text box
				if (typedChar == "enter"){
					//next question button clicked
					nextQuestionClicked = true;
					getNextQuestion();
					
				}
				else if (typedChar.equals("t")){
					//text box clicked
					textBoxEntered = true;
					
				}
				else if (typedChar.equals("/")){
					//text box clicked
					textBoxTextLeftOfCursor = "";
					
				}
				else if (typedChar.equals("r")){
					//green tick button clicked
					tickButtonClicked = true;
					greenTickClicked();
					
				}
				else if (typedChar.equals("w")){
					//red x button clicked
					xButtonClicked = true;
					redXClicked();
					
				}
				else if (typedChar.equals("[")){
					//move left in seen slides button clicked
					backwardPageButtonClicked = true;
					getBackwardSlide();
					
				}
				else if (typedChar.equals("]")){
					//move right in seen slides button clicked
					forwardPageButtonClicked = true;
					getForwardSlide();
					
				}
				else if (typedChar.equals("b")){
					//back to menu button clicked
					currentPage = "1";
					backToMenuButtonClicked = true;
					//close the file since going back
					saveQuestionsCompleted();
					closePPFile();
					initializeDefaultValueVariables();
					
				}
			}
		}
		
		
	}

	public void updatetextBoxTextLeftOfCursor(String typedChar)
	{
		//
		if (typedChar == "backspace"){
			textBoxTextLeftOfCursor = backspaceString(textBoxTextLeftOfCursor);
		}
		else{
			textBoxTextLeftOfCursor = textBoxTextLeftOfCursor + typedChar;
		}
	}

	public String backspaceString(String str) {
		if (str != null && str.length() > 0 ) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public void changeLoadedFile()
	{
		String foundPath = FileSelector.selectPpfFile();
        if (foundPath != null){
			slidePDFLocation = foundPath;
        }   
	}

//DRAWING QUIZ STUFF START

public void drawQuiz()
{
	//--DRAWING THE TITLE--
	drawTitle();

	//--DRAWING THE SLIDE--	
	drawSlide();

	//--SLIDE DIMENSIONS--
	setSlideDimensions();
	
	//--QUESTIONS COMPLETE TEXT--
	setQuestioncompleteText();

	//--DRAWING THE CURRENT QUESTION TEXT-- 
	drawCurrentQuestionText();

	//--BUTTONS SPACING--
	setButtonSpacing();

	//--DRAWING BACK PAGE BUTTON--
	drawBackPageButton();

	//--DRAWING THE ARROW ON THE BACK PAGE BUTTON--
	drawArrowOnBackPageButton();

	//--DRAWING FORWARED PAGE BUTTON--
	drawForwardPageButton();

	//--DRAWING THE ARROW ON THE FORWARD PAGE BUTTON--
	drawArrowOnForwardPageButton();

	//--DRAWING NEXT QUESTION BUTTON--
	drawNextQuestionButton();

	//--DRAWING ANSWER TEXT BOX--
	drawAnswerTextBox();

	//--DRAWING TEXT IN THE TEXT BOX--
	drawTextInTextBox();

	//--DRAWING THE RIGHT OF CURSOR TEXT IN THE TEXT BOX--
	drawRightTextInTextBox();

	//--DRAWING RED X BUTTON--
	drawRedXButton();

	//--DRAWING GREEN TICK BUTTON--
	drawGreenTickButton();

	//--DRAWING THE DONE QUESTION TICK--
	drawDoneQuestionTick();

	//--DRAWING THE ERROR TEXT--
	drawErrorText();

	//--DRAWING THE BACK TO THE MENU BUTTON--
	drawBackToMenuButton();
}

public void drawTitle()
{
	if (setTitleDrawingStuff == false){
		//
		g.setFont(new Font("Monospaced", Font.PLAIN, 40)); 
		titleString = (ConvertPDFPagesToImages.getFileNameFromLocation(slidePDFLocation));
		titleTextWidth = g.getFontMetrics().stringWidth(titleString);
		titleTextHeight = g.getFontMetrics().getAscent();
		titleTextX = (int) (windowWidth*0.5) - (int) (titleTextWidth/2) ;
		titleTextY = ((int) ((float) (windowWidth)*0.02)) + (int) (titleTextHeight);
		setTitleDrawingStuff = true;//indicates that the above variables can be referenced
	}
	g.setFont(new Font("Monospaced", Font.PLAIN, 40)); 
	g.setColor(new Color(0, 0, 0));
	

	g.drawString(titleString, titleTextX, titleTextY);

}

public void drawSlide()
{
	//the intro slide location
	introImgLocation = "greyBackground.png"; 

	try {
		if ((imagePageNumber != -1) || (pdfHandlerSet == true)){
			//
			if (slideImageUpdated == true){
				//--normal code to run--
				String specificSlideLocation = "images/slideImage" + imagePageNumber+ ".png";
				slideImg = new File(specificSlideLocation);
				introImg = new File(introImgLocation);
				if (pdfHandlerSet == true){
					img = ImageIO.read(slideImg);
				}
				else{
					img = ImageIO.read(introImg);
				}
	
				//resizing
				slideImageWidth          = img.getWidth();
				slideHeight         = img.getHeight();
	
				slideWidth = (int) (windowWidth*0.6);
				slideHeight = (int) (slideWidth*0.56);
	
				//setting coordinates
				resizedImg = resize(img, slideWidth, slideHeight);
				imgTopLeftX = (int) ((float) (windowWidth)*0.02);
				imgTopLeftY = (int) (windowHeight/2 - slideHeight/2);
	
				//--}}
				setJustChangedSlide(false);
			}
			
			//drawing to the screen
			g.drawImage(resizedImg, imgTopLeftX, imgTopLeftY, this);
	
			//drawing outline 
			g.setColor(new Color(0, 0, 0));
			g.drawRect(imgTopLeftX, imgTopLeftY, slideWidth, slideHeight);
			//
	
			if (setSlideDrawingStuff == false){
				setSlideDrawingStuff = true;//indicates that the above variables can be referenced
			}
		}
		else{
			System.out.println("image ref is -1!");
			slideWidth = (int) (windowWidth*0.6);
			slideHeight = (int) (slideWidth*0.56);
			imgTopLeftX = (int) ((float) (windowWidth)*0.02);
			imgTopLeftY = (int) (windowHeight/2 - slideHeight/2);
		}

	} catch (IOException ex) {
		System.out.println("could not attain the slide image file");
	}


}

public void setSlideDimensions()
{
	if (setSlideAdditionalDimensions == false){
		//
		slideTopY = (int) (windowHeight/2 - slideHeight/2);
		slideLeftX = (int) ((float) (windowWidth)*0.02);
		slideBottom = (slideTopY + slideHeight);

		//getting relative dimensions
		distSlideBottomToScreenBottom = windowHeight- slideBottom;

		setSlideAdditionalDimensions = true;//indicates that the above variables can be referenced
	}
}

public void setQuestioncompleteText()
{
	questionsCompleteFullString = ("questions complete: " + String.valueOf(questionsComplete));
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	questionsCompleteTextWidth = g.getFontMetrics().stringWidth(questionsCompleteFullString);
	questionsCompleteTextHeight = g.getFontMetrics().getAscent();
	questionsCompleteTextX = windowWidth - questionsCompleteTextWidth - slideLeftX;
	questionsCompleteTextY = (int) (windowWidth*0.02);
	
	
	

	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));

	g.drawString(questionsCompleteFullString, questionsCompleteTextX, questionsCompleteTextY);

	setQuestionsCompleteTextStuff = true;//indicates that the above variables can be referenced


}


/**
 * --DRAWING THE CURRENT QUESTION TEXT--
 * @param g
 * @param slideLeftX
 * @param slideTopY
 */
public void drawCurrentQuestionText()
{
		
	currentQuestionFullString = ("current question: " + String.valueOf(currentQuestion));
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	currentQuestionTextWidth = g.getFontMetrics().stringWidth(currentQuestionFullString);
	currentQuestionTextHeight = g.getFontMetrics().getAscent();
	currentQuestionTextX = slideLeftX;
	currentQuestionTextY = slideTopY - currentQuestionTextHeight;
		

	
	g.setColor(new Color(0, 0, 0));

	g.drawString(currentQuestionFullString, currentQuestionTextX, currentQuestionTextY);

	setCurrentQuestionTextStuff = true;


}

public void setButtonSpacing()
{
	if (setButtonSpacingStuff == false){
		//
		buttonSpacing = (int) (slideWidth*0.03);
		buttonWidth = (int) (buttonSpacing*4);
		buttonHeight=(int) (buttonWidth*0.56);

		setButtonSpacingStuff = true;//indicates that the above variables can be referenced
	}
	
}

public void drawBackPageButton()
{
	if (setBackPageButtonStuff == false){
		//
		backPageButtonX= slideLeftX +  (int) (slideWidth*0.25);
		backPageButtonY=slideBottom + (distSlideBottomToScreenBottom/2 - buttonHeight/2);

		//changing back page button colour to be darker if it is hovered over
		backPageButtonRight = backPageButtonX + buttonWidth;
		backPageButtonBottom = backPageButtonY + buttonHeight;

		setBackPageButtonStuff = true; //indicates that the above variables can be referenced

	}
	
	if (backwardPageButtonClicked){
		//
		g.setColor(new Color(190, 190, 190));
		backwardPageButtonClicked = false;
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, backPageButtonX, backPageButtonRight, backPageButtonY, backPageButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(175, 175, 175));
	}
	else{
		//
		g.setColor(new Color(180, 180, 180));
	}

	//drawing the button
	g.fillRect(backPageButtonX, backPageButtonY, buttonWidth, buttonHeight);

	//drawing outline 
	g.setColor(new Color(0, 0, 0));
	g.drawRect(backPageButtonX, backPageButtonY, buttonWidth, buttonHeight);
	//

}

public void drawArrowOnBackPageButton()
{
	if (setBackPageButtonArrowStuff == false){
		//
		backPageArrowCentreX = (int) (backPageButtonX + (buttonWidth / 2));
		backPageArrowCentreY = (int) (backPageButtonY + (buttonHeight / 2));
		
		arrowHeight = 40;

		//---top line---
		backPageArrowTopStartX = backPageArrowCentreX + (int) (arrowHeight/4);
		backPageArrowTopStartY = backPageArrowCentreY - (int) (arrowHeight/4);
		backPageArrowTopEndX = backPageArrowCentreX - (int) (arrowHeight/4);
		backPageArrowTopEndY = backPageArrowCentreY ;

		//---bottom line---
		backPageArrowBottomStartX = backPageArrowCentreX + (int) (arrowHeight/4);
		backPageArrowBottomStartY = backPageArrowCentreY + (int) (arrowHeight/4);
		backPageArrowBottomEndX = backPageArrowCentreX - (int) (arrowHeight/4);
		backPageArrowBottomEndY = backPageArrowCentreY ;

		setBackPageButtonArrowStuff = true;//indicates that the above variables can be referenced
	}
	

	g.setColor(new Color(0, 0, 0));
	//making the line thicker
	g.setStroke(new BasicStroke(5));
	g.drawLine(backPageArrowTopStartX, backPageArrowTopStartY, backPageArrowTopEndX, backPageArrowTopEndY);

	
	g.setColor(new Color(0, 0, 0));
	//making the line thicker
	g.setStroke(new BasicStroke(5));
	g.drawLine(backPageArrowBottomStartX, backPageArrowBottomStartY, backPageArrowBottomEndX, backPageArrowBottomEndY);

	g.setStroke(new BasicStroke(1));//resets the stroke back to default


	
}

public void drawForwardPageButton()
{
	if (setForwardPageButtonStuff == false){
		//
		forwardPageButtonX=slideLeftX + (int) (slideWidth*0.25) + buttonWidth + buttonSpacing;
		forwardPageButtonY=slideBottom + (distSlideBottomToScreenBottom/2 - buttonHeight/2);

		//changing next page button colour to be darker if it is hovered over
		nextPageButtonRight = forwardPageButtonX + buttonWidth;
		nextPageButtonBottom = forwardPageButtonY + buttonHeight;

		setForwardPageButtonStuff = true;//indicates that the above variables can be referenced

	}
	
	if (forwardPageButtonClicked){
		//
		g.setColor(new Color(190, 190, 190));
		forwardPageButtonClicked = false;
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, forwardPageButtonX, nextPageButtonRight, forwardPageButtonY, nextPageButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(175, 175, 175));
	}
	else{
		//
		g.setColor(new Color(180, 180, 180));
	}

	g.fillRect(forwardPageButtonX, forwardPageButtonY, buttonWidth, buttonHeight);

	//drawing outline 
	g.setColor(new Color(0, 0, 0));
	g.drawRect(forwardPageButtonX, forwardPageButtonY, buttonWidth, buttonHeight);
	//

}

public void drawArrowOnForwardPageButton()
{
	if (setForwardPageButtonArrowStuff == false){
		forwardPageArrowCentreX = (int) (forwardPageButtonX + (buttonWidth / 2));
		forwardPageArrowCentreY = (int) (forwardPageButtonY + (buttonHeight / 2));
		
		//---top line---
		forwardPageArrowTopStartX = forwardPageArrowCentreX - (int) (arrowHeight/4);
		forwardPageArrowTopStartY = forwardPageArrowCentreY - (int) (arrowHeight/4);
		forwardPageArrowTopEndX = forwardPageArrowCentreX + (int) (arrowHeight/4);
		forwardPageArrowTopEndY = forwardPageArrowCentreY;

		//---bottom line---
		forwardPageArrowBottomStartX = forwardPageArrowCentreX - (int) (arrowHeight/4);
		forwardPageArrowBottomStartY = forwardPageArrowCentreY + (int) (arrowHeight/4);
		forwardPageArrowBottomEndX = forwardPageArrowCentreX + (int) (arrowHeight/4);
		forwardPageArrowBottomEndY = forwardPageArrowCentreY;

		setForwardPageButtonArrowStuff = true;//indicates that the above variables can be referenced
	}
	

	g.setColor(new Color(0, 0, 0));
	//making the line thicker
	g.setStroke(new BasicStroke(5));
	g.drawLine(forwardPageArrowTopStartX, forwardPageArrowTopStartY, forwardPageArrowTopEndX, forwardPageArrowTopEndY);

	

	g.setColor(new Color(0, 0, 0));
	//making the line thicker
	g.setStroke(new BasicStroke(5));
	g.drawLine(forwardPageArrowBottomStartX, forwardPageArrowBottomStartY, forwardPageArrowBottomEndX, forwardPageArrowBottomEndY);

	g.setStroke(new BasicStroke(1));//resets the stroke back to default
	
}

public void drawNextQuestionButton()
{
	
	//https://stackoverflow.com/questions/22628357/how-to-write-text-inside-a-rectangle
	if (setNextQuestionButtonStuff == false){
		//
		nextQuestionButtonX= slideLeftX + (int) (slideWidth*0.25) + buttonWidth + buttonSpacing + buttonWidth + buttonSpacing;
		nextQuestionButtonY=slideBottom + (distSlideBottomToScreenBottom/2 - buttonHeight/2);

		//changing next question button colour to be darker if it is hovered over
		nextQuestionButtonRight = nextQuestionButtonX + buttonWidth;
		nextQuestionButtonBottom = nextQuestionButtonY + buttonHeight;

		g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
		stringWidth = g.getFontMetrics().stringWidth("Next");
		stringHeight = g.getFontMetrics().getAscent();

		nextQuestionButtonTextX = nextQuestionButtonX  + ((buttonWidth-stringWidth)/2);
		nextQuestionButtonTextY = nextQuestionButtonY + stringHeight + (((buttonHeight-stringHeight)/2));

		setNextQuestionButtonStuff = true;//indicates that the above variables can be referenced

	}
				
	
	if (nextQuestionClicked){
		g.setColor(new Color(190, 190, 190));
		nextQuestionClicked = false;
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, nextQuestionButtonX, nextQuestionButtonRight, nextQuestionButtonY, nextQuestionButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(175, 175, 175));
	}
	else{
		//
		g.setColor(new Color(180, 180, 180));
	}

	//box
	g.fillRect(nextQuestionButtonX, nextQuestionButtonY, buttonWidth, buttonHeight);

	//drawing outline 
	g.setColor(new Color(0, 0, 0));
	g.drawRect(nextQuestionButtonX, nextQuestionButtonY, buttonWidth, buttonHeight);
	//

	//text
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	
	g.setColor(new Color(0, 0, 0));

	g.drawString("Next", nextQuestionButtonTextX, nextQuestionButtonTextY);

}

public void drawAnswerTextBox()
{
	if (setAnswerTextBoxStuff == false){
		//
		answerTextBoxSpacing = (int) ((float) (windowWidth)*0.02);//
		answerTextBoxWidth = (int) (windowWidth*0.34);
		answerTextBoxHeight=(int) (answerTextBoxWidth*0.56);

		answerTextBoxX = windowWidth - answerTextBoxSpacing - answerTextBoxWidth;//
		answerTextBoxY=(int) (windowHeight/2 - (windowWidth*0.6*0.56)/2); //lines up with slide image

		//changing next question button colour to be darker if it is hovered over
		answerTextBoxRight = answerTextBoxX + answerTextBoxWidth;
		answerTextBoxBottom = answerTextBoxY + answerTextBoxHeight;

		setAnswerTextBoxStuff = true;//indicates that the above variables can be referenced

	}
	
	if (textBoxEntered == true){
		g.setColor(new Color(255, 255, 255));
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, answerTextBoxX, answerTextBoxRight, answerTextBoxY, answerTextBoxBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(235, 235, 235));
	}
	else{
		//
		g.setColor(new Color(241, 241, 241));
	}


	g.fillRect(answerTextBoxX, answerTextBoxY, answerTextBoxWidth, answerTextBoxHeight);

	//drawing outline 
	g.setColor(new Color(0, 0, 0));
	if (textBoxEntered == true){
		g.setColor(new Color(245, 197, 22));
		g.setStroke(new BasicStroke(2));
	}
	g.drawRect(answerTextBoxX, answerTextBoxY, answerTextBoxWidth, answerTextBoxHeight);
	//

	g.setStroke(new BasicStroke(1));//resets the stroke back to default


}

public void drawTextInTextBox()
{
	//https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
	//--keyboard inputs: https://stackoverflow.com/questions/24892726/how-to-get-keyboard-input-and-display-it-on-a-jpanel-in-java 

	//for the cursor positoin
	int defaultCursorY = answerTextBoxY;
	int defaultCursorX = answerTextBoxX;
	//

	g.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
	g.setColor(new Color(0, 0, 0));
	textBoxTextLeftOfCursorWidth = g.getFontMetrics().stringWidth(textBoxTextLeftOfCursor);
	textBoxTextLeftOfCursorHeight = g.getFontMetrics().getAscent();
	textBoxTextLeftOfCursorX = answerTextBoxX;
	textBoxTextLeftOfCursorY = answerTextBoxY +textBoxTextLeftOfCursorHeight - scrollAmount;
					

	//g.drawString(textBoxTextLeftOfCursor, textBoxTextLeftOfCursorX, textBoxTextLeftOfCursorY);
	drawingTextStartY = textBoxTextLeftOfCursorY - g.getFontMetrics().getHeight();

	exceededTextBoxHeight = false;

	lineCount = 0;
	for (String line : textBoxTextLeftOfCursor.split("\n")){
		lineCount++;
		leftLineWidth = g.getFontMetrics().stringWidth(line);
		//adding a new line if the text becomes longer than the text box
		if (g.getFontMetrics().stringWidth(line) > answerTextBoxWidth ){
			//add a new line onto the end 


			//get last space
			lastSpace = textBoxTextLeftOfCursor.lastIndexOf(" ") + 1;
			lastNewLine = textBoxTextLeftOfCursor.lastIndexOf("\n");
			//-if this line has a space in it-
			if ((lastSpace > 0) && (lastNewLine < lastSpace)){
				
				//get everything after space
				lastWord = textBoxTextLeftOfCursor.substring(lastSpace);
				if (lastWord.equals("")){
					//then there was a space which caused the line to be longer than the text box
					//add \n at last space
					textMinusLastWord = textBoxTextLeftOfCursor.substring(0, lastSpace - 1);
					textMinusLastWordWithNewLine = textMinusLastWord + "\n";
					//add everything back on
					textBoxTextLeftOfCursor = textMinusLastWordWithNewLine;
					textBoxTextLeftOntoNewLine();
					leftLineWidth = g.getFontMetrics().stringWidth(lastWord);
				}
				else{
					//add \n at last space
					textMinusLastWord = textBoxTextLeftOfCursor.substring(0, lastSpace);
					textMinusLastWordWithNewLine = textMinusLastWord + "\n";
					//add everything back on
					textBoxTextLeftOfCursor = textMinusLastWordWithNewLine + lastWord;
					textBoxTextLeftOntoNewLine();
					leftLineWidth = g.getFontMetrics().stringWidth(lastWord);

				}
				
			}
			else{
				//-there are no spaces- 


				//length of string:
				textLength = Math.max(textBoxTextLeftOfCursor.length() - 2, 0);
				//get the last 2 characters
				lastTwoCharacters = textBoxTextLeftOfCursor.substring(textLength);
				//remove the last 2 characters
				lastTwoRemoved = textBoxTextLeftOfCursor.substring(0, textLength);
				//add "-\n" and add last two back on
				textBoxTextLeftOfCursor = lastTwoRemoved + "-\n" + lastTwoCharacters;
				textBoxTextLeftOntoNewLine();
				leftLineWidth = g.getFontMetrics().stringWidth(lastTwoCharacters);

			}

			//if do a space then type without spaces until the end of the line breaks it

			//increase the total number of lines
			lineCount++;

			
		}

		if (lineCount > totalNumTextBoxLines){
			//checking if can scroll
			if ((lineCount*g.getFontMetrics().getHeight()) > answerTextBoxHeight){
				exceededTextBoxHeight = true;
				scrollAmount = scrollAmount + g.getFontMetrics().getHeight();
			}
		}

		//drawing the line
		g.drawString(line, textBoxTextLeftOfCursorX, drawingTextStartY += g.getFontMetrics().getHeight());

		//updating the default cursor details
		defaultCursorY = drawingTextStartY - g.getFontMetrics().getHeight() + 4;
		defaultCursorX = answerTextBoxX + g.getFontMetrics().stringWidth(line);


	}

	//makes the cursor move to the next line if there is a new line at the end of the text
	if (textBoxTextLeftOfCursor.length() >= 2){
		//
		String lastTwoChars = textBoxTextLeftOfCursor.substring(textBoxTextLeftOfCursor.length() - 2);
		//
		if (lastTwoChars.contains("\n")){
			defaultCursorY = defaultCursorY + g.getFontMetrics().getHeight();
			defaultCursorX = answerTextBoxX;
		}

	}
	else{
		if (textBoxTextLeftOfCursor.equals("\n")){
			defaultCursorY = defaultCursorY + g.getFontMetrics().getHeight();
			defaultCursorX = answerTextBoxX;
		}
	}
	

	if (lineCount < totalNumTextBoxLines){
		//checking if can scroll
		if (scrollAmount >= g.getFontMetrics().getHeight() ){
			exceededTextBoxHeight = true;
			scrollAmount = scrollAmount - g.getFontMetrics().getHeight();
		}
	}

	if (linesLastTime > textBoxTextLeftOfCursor.length()){
		textBoxTextLeftRemovedNewLine();
		
	}
	linesLastTime = textBoxTextLeftOfCursor.length();
	

	if (lineCount > 0){
		//
		totalNumTextBoxLines = lineCount;
	}	

	if (exceededTextBoxHeight == true){
		//
		canScroll = true;
	}

	//--if the user has clicked a different position to place their cursor--
	boolean setNewCursorPosition = false;
	if (textBoxEntered == true){

		//if the y is greater than the max y
		//or if y is equal to the max y and x is greater than the max x, just put cursor in default position
		if ((textBoxNewClickPositionX != -1) && (textBoxNewClickPositionY != -1)){
			//then the user has clicked a new position

			if ((textBoxNewClickPositionY <= defaultCursorY + g.getFontMetrics().getHeight()))
			{
				if ((textBoxNewClickPositionY >= defaultCursorY) && (textBoxNewClickPositionX > defaultCursorX)){
					//then the user has clicked into a space with no text in it
				}
				else{
					//get the position
					//find the y of the line that is closest to that point--> text box y + font height
					int numberOfVerticalRows = (int) Math.rint((textBoxNewClickPositionY - answerTextBoxY) / g.getFontMetrics().getHeight());
					blinkingCursorY =answerTextBoxY + numberOfVerticalRows*g.getFontMetrics().getHeight();
					//find the x of the letter that will be closest to that point--> text box x + font width
					int numberOfHorizontalColumns = (int) Math.rint((textBoxNewClickPositionX- answerTextBoxX) / g.getFontMetrics().stringWidth("a"));
					blinkingCursorX =answerTextBoxX + numberOfHorizontalColumns*g.getFontMetrics().stringWidth("a");

					setNewCursorPosition = true;
				}
			}
		}
	}

	//--drawing the blinking cursor--
	//blinking cursor will always come after the left side (not the right)
	if (textBoxEntered == true){
		//
		if ((blinkingCursorCount !=0) && (blinkingCursorCount > 5)){
			//
			blinkingCursorCount++;
			if (blinkingCursorCount== 13){
				blinkingCursorCount = 0;
			}
			//if default
			if (setNewCursorPosition == false){
				//
				blinkingCursorY = defaultCursorY;
				blinkingCursorX = defaultCursorX;
			}
			//
			blinkingCursorW = (int) ((g.getFontMetrics().getHeight())/6);
			blinkingCursorH = (int) ((g.getFontMetrics().getHeight())*0.9);
			//blinkingCursorCount;
	
			g.setColor(new Color(0, 0, 0));
			g.fillRect(blinkingCursorX, blinkingCursorY, blinkingCursorW, blinkingCursorH);
	
			//-default = end of the currently typed string
			//find the location of the last letter
	
			//-other: if the user clicks, the find the letter closest to that point and put the cursor after that 
		}
		else{	
			blinkingCursorCount++;
		}
	}
	
	

	setDrawingTextInTextBoxStuff = true;//indicates that the above variables can be referenced
}

public void textBoxTextLeftOntoNewLine(){
	//
	//if the left text goes onto a new line, if the first 2 chars of right text is newline, remove them
	if (textBoxTextRightOfCursor.length() > 1){
		//
		String firstTwoLines = textBoxTextRightOfCursor.substring(0, 2);
		if (firstTwoLines.contains("\n")){
			//
			textBoxTextRightOfCursor = textBoxTextRightOfCursor.substring(1);
		}
	}
	
}

public void textBoxTextLeftRemovedNewLine(){
	//
	//if the left text goes onto a new line, if the first 2 chars of right text is newline, remove them
	if (textBoxTextRightOfCursor.length() > 1){
		//
		String firstTwoLines = textBoxTextRightOfCursor.substring(0, 1);
		if (firstTwoLines.contains("\n")){
			//
			textBoxTextRightOfCursor = textBoxTextRightOfCursor.substring(1);
		}



		int pos = textBoxTextRightOfCursor.indexOf("\n");
		if (pos != -1) {
			String left = textBoxTextRightOfCursor.substring(0, pos);
			String right = textBoxTextRightOfCursor.substring(pos+1);

			textBoxTextRightOfCursor = left+right;
		}
		

		
	}
	
}


/**
 * draws the text that is to the right of the cursor into the text box 
 */
public void drawRightTextInTextBox()
{

	//for the cursor position
	int defaultCursorY = answerTextBoxY;
	int defaultCursorX = answerTextBoxX;
	//

	g.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
	g.setColor(new Color(0, 0, 0));
	textBoxTextRightOfCursorWidth = g.getFontMetrics().stringWidth(textBoxTextRightOfCursor);
	textBoxTextRightOfCursorHeight = g.getFontMetrics().getAscent();
	//set the start coordinates to be straight after the left section
	textBoxTextRightOfCursorX = blinkingCursorX;
	textBoxTextRightOfCursorY = blinkingCursorY;
					

	drawingRightTextStartY = blinkingCursorY  - (int) ((float) (g.getFontMetrics().getHeight())/3.5);

	String lastWordOnThisLine;
	int lastSpaceOnThisLine;
	String remainingTextAfterThisLine;
	int indexOfNextNewLine;

	/*
	FOR THE RIGHT, NEED TO CHECK IF RIGHTING ONTO THE END OF THE LEFT TEXT AND THEN ADJUST FOR THE 
	TEXT BOX WIDTH CHECKS
	*/

	String[] splitLinesArray = textBoxTextRightOfCursor.split("\n");
	int numLines = splitLinesArray.length;
	for (int i = 0; i < numLines; i++){
		
		String line = splitLinesArray[i];

		String nextLine;
		int nextLineWidth;
		if (i != numLines - 1){
			nextLine = splitLinesArray[i+1];
			nextLineWidth = g.getFontMetrics().stringWidth(nextLine);
		}
		else{
			nextLine = "";
			nextLineWidth = 0;
		}

		//for a line that is on the same line as the end of the left
		//need to split the line in two if it overhangs greater than the text box
		if (g.getFontMetrics().stringWidth(line) > answerTextBoxWidth - leftLineWidth){

			/*
			if the difference between the next two new lines + right split < text box width: 
			just add the right bit to the start of the next line
			*/

			

			if (!line.equals("")){
				//
				//seperating the overhang into two strings
				int overhangLength = g.getFontMetrics().stringWidth(line) - (answerTextBoxWidth - leftLineWidth);
				overhangLength =  g.getFontMetrics().stringWidth(line) - overhangLength;
				int estimateOfCharactersToSpanOverhang = overhangLength / g.getFontMetrics().stringWidth("a");
				if (estimateOfCharactersToSpanOverhang == 0){
					estimateOfCharactersToSpanOverhang++;
				}
				String stringLeftOfOverhang = line.substring(0, estimateOfCharactersToSpanOverhang);
				String stringRightOfOverhang = line.substring(estimateOfCharactersToSpanOverhang);
				
				
				if (stringRightOfOverhang.equals("")){
					stringRightOfOverhang = stringLeftOfOverhang;
					stringLeftOfOverhang = "";
				}
				
				int rightOfOverhangWidth = g.getFontMetrics().stringWidth(stringRightOfOverhang);
				//just want to add a new line since you cannot move the right text onto the next line
				String leftWithNewLine = stringLeftOfOverhang + "\n";

				if (nextLineWidth == 0 || (nextLineWidth + rightOfOverhangWidth > answerTextBoxWidth)){

					
					

					//getting the rest of the string after the overhang
					int nextNewLineIndex = ordinalIndexOf(textBoxTextRightOfCursor, "\n", i+1);
					String restOfString;
					if (nextNewLineIndex != -1){
						//
						restOfString = textBoxTextRightOfCursor.substring(nextNewLineIndex);
					}
					else{
						//
						restOfString = "";
					}

					
					

					//update the entire right of cursor string to not have the overhang
					textBoxTextRightOfCursor = leftWithNewLine + stringRightOfOverhang + restOfString;

				}
				else{
					//just want to move the right overhang onto the beginning of the next line
					String rightOverhangPlusNextLine = stringRightOfOverhang + nextLine;


					//getting the rest of the string after the overhang
					int nextNewLineIndex = ordinalIndexOf(textBoxTextRightOfCursor, "\n", i+2);
					String restOfString;
					if (nextNewLineIndex != -1){
						//
						restOfString = textBoxTextRightOfCursor.substring(nextNewLineIndex);
					}
					else{
						//
						restOfString = "";
					}


					textBoxTextRightOfCursor = leftWithNewLine + rightOverhangPlusNextLine + restOfString;



				}
			}

			
			
		}


		//drawing the line
		g.drawString(line, textBoxTextRightOfCursorX, drawingRightTextStartY += g.getFontMetrics().getHeight());

		//since not on the line that connects the left to the right text (since that is the first line)
		leftLineWidth = 0;
		textBoxTextRightOfCursorX = answerTextBoxX;

	}

	if (numLines < totalNumTextBoxLines){
		//checking if can scroll
		if (scrollAmount >= g.getFontMetrics().getHeight() ){
			exceededTextBoxHeight = true;
			scrollAmount = scrollAmount - g.getFontMetrics().getHeight();
		}
	}

	if (numLines > 0){
		totalNumTextBoxLines = totalNumTextBoxLines + numLines - 1;
	}	

	if (exceededTextBoxHeight == true){
		canScroll = true;
	}

	setDrawingRightTextInTextBoxStuff = true;//indicates that the above variables can be referenced
}



//https://stackoverflow.com/questions/3976616/how-to-find-nth-occurrence-of-character-in-a-string
//accessed: 22/08/2020
public static int ordinalIndexOf(String str, String substr, int n) {
    int pos = str.indexOf(substr);
    while (--n > 0 && pos != -1)
        pos = str.indexOf(substr, pos + 1);
    return pos;
}

public void drawRedXButton()
{
	
	//--begin the process--
	try {
		if (setRedXStuff == false){
			//
			//the img location
			redXButtonImgLocation = "red_cross.png"; 
			redXButtonImg = new File(redXButtonImgLocation);

			redXBimg = ImageIO.read(redXButtonImg);
			setRedXStuff = true;//indicates that the above variables can be referenced

			
		}

		//resizing
		redXButtonWidth = (int) ((windowWidth*0.34)*0.5*0.98);
		redXButtonHeight=(int) (0.145*windowWidth*0.95);
		redXBimgResized = resize(redXBimg, redXButtonWidth, redXButtonHeight);
		

		//setting coordinates
		redXButtonSpacing = (int) ((float) (windowWidth)*0.02);//

		redXButtonX = windowWidth - redXButtonSpacing - redXButtonWidth;//
		redXButtonY=(int) (slideBottom - redXButtonHeight); 

		//--changing the image brightness --
		redXButtonRight = redXButtonX + redXButtonWidth;
		redXButtonBottom = redXButtonY + redXButtonHeight;
		
		double previousRedXBrightness = redXBrightness;
		if (xButtonClicked == true){
			//
			redXBrightness= 1.3;
			xButtonClicked = false;
		}
		else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, redXButtonX, redXButtonRight, redXButtonY, redXButtonBottom) == true){
			//user is hovering over this button
			redXBrightness= 0.9;
		}
		else{
			//
			redXBrightness= 1.0;
		}
		if (previousRedXBrightness != redXBrightness){
			//the brightness has changed, so update
			redXBrightnessRescaleOp = new RescaleOp((float) redXBrightness, 0, null);
		}
		redXBrightnessRescaleOp.filter(redXBimgResized, redXBimgResized);



		//for the highlighted outline
		if (needToConfirmCorrectness == true){
			//draw highlighted outline 
			redXOutlineX = redXButtonX - 2;
			redXOutlineY = redXButtonY - 2;
			redXOutlineW = redXButtonWidth + 4;
			redXOutlineH = redXButtonHeight + 4;

			g.setColor(new Color(245, 197, 22));
			g.fillRect(redXOutlineX, redXOutlineY, redXOutlineW, redXOutlineH);
		}

		//drawing to the screen
		g.drawImage(redXBimgResized, redXButtonX, redXButtonY, this);

	} catch (IOException e1) {
		// TODO Auto-generated catch block
		System.out.println("could not read the red X file");
	}

	
}

public void drawGreenTickButton()
{
	//--begin the process--
	try {

		if (setGreenTickStuff == false){
			//
			//the img location
			greenTickButtonImgLocation = "green_tick.png"; 
			greenTickButtonImg = new File(greenTickButtonImgLocation);


			greenTickBimg = ImageIO.read(greenTickButtonImg);

			setGreenTickStuff = true;//indicates that the above variables can be referenced
		}
		

		//resizing
		greenTickButtonWidth = (int) ((windowWidth*0.34)*0.5*0.98);
		greenTickButtonHeight=(int) (0.145*windowWidth*0.95);
		greenTickBimgResized = resize(greenTickBimg, greenTickButtonWidth, greenTickButtonHeight);

		//setting coordinates
		greenTickButtonSpacing = (int) ((float) (windowWidth)*0.02);//
		greenTickButtonX = windowWidth - ((int) ((float) (windowWidth)*0.02)) - ((int) (windowWidth*0.34));
		greenTickButtonY=(int) (slideBottom - greenTickButtonHeight); 

		//--changing the image brightness --
		greenTickButtonRight = greenTickButtonX + greenTickButtonWidth;
		greenTickButtonBottom = greenTickButtonY + greenTickButtonHeight;

		double previousGreenTickBrightness = greenTickBrightness;
		if (tickButtonClicked == true){
			//
			greenTickBrightness= 1.3;
			tickButtonClicked = false;
		}
		else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, greenTickButtonX, greenTickButtonRight, greenTickButtonY, greenTickButtonBottom) == true){
			//user is hovering over this button
			greenTickBrightness= 0.9;
		}
		else{
			//
			greenTickBrightness= 1.0;
		}
		if (previousGreenTickBrightness != greenTickBrightness){
			//the brightness has changed, so update
			greenTickBrightnessRescaleOp = new RescaleOp((float) greenTickBrightness, 0, null);
		}
		greenTickBrightnessRescaleOp.filter(greenTickBimgResized, greenTickBimgResized);
		

		//for the highlighted outline
		if (needToConfirmCorrectness == true){
			//draw highlighted outline 
			greenTickOutlineX = greenTickButtonX - 2;
			greenTickOutlineY = greenTickButtonY - 2;
			greenTickOutlineW = greenTickButtonWidth + 4;
			greenTickOutlineH = greenTickButtonHeight + 4;

			g.setColor(new Color(245, 197, 22));
			g.fillRect(greenTickOutlineX, greenTickOutlineY, greenTickOutlineW, greenTickOutlineH);
		}

		//drawing to the screen
		g.drawImage(greenTickBimgResized, greenTickButtonX, greenTickButtonY, this);


	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("could not read the green tick image");
	}

}

public void drawDoneQuestionTick()
{
	if (pdfHandlerSet == true){
		//
		if (pdfHandler.isComplete(currentQuestion)){
			//draw the tick
			//the img location
			doneTickImgLocation = "doneTick.png"; 
			doneTickImg = new File(doneTickImgLocation);

			try {
				if (setDoneQuestionTickStuff == false){
					//
					//the img location
					doneTickImgLocation = "doneTick.png"; 
					doneTickImg = new File(doneTickImgLocation);

					doneTickBimg = ImageIO.read(doneTickImg);

					setDoneQuestionTickStuff = true;
				}
				//resizing
				doneTickWidth = (int) (windowWidth*0.02);
				doneTickHeight=(int) (windowWidth*0.02);
				doneTickBimgResized = resize(doneTickBimg, doneTickWidth, doneTickHeight);

				//setting coordinates
				//int greenTickButtonSpacing = (int) ((float) (windowWidth)*0.02);//

				greenTickButtonX = currentQuestionTextX + currentQuestionTextWidth + 10;
				greenTickButtonY= currentQuestionTextY - (int) (currentQuestionTextHeight); 

				

				//drawing to the screen
				g.drawImage(doneTickBimgResized, greenTickButtonX, greenTickButtonY, this);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("could not read the done question tick");
			}

		}
	}
}

public void drawErrorText()
{
	errorTextString = ".";
	if (firstSlide == true){
		errorTextString = "On the first slide";
	}
	if (mostRecentSlide == true){
		errorTextString = "This is the most recent slide";
	}
	if (displayNoMoreSlidesText == true){
		errorTextString = "No more slides left!";
	}

	errorTextStringWidth = g.getFontMetrics().stringWidth(errorTextString);
	errorTextStringHeight = g.getFontMetrics().getAscent();
	errorTextStringX = slideLeftX;
	errorTextStringY = slideBottom + errorTextStringHeight*2;

	if ((firstSlide== true) || (mostRecentSlide== true) || (displayNoMoreSlidesText== true))
	{
		//
		g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
		g.setColor(new Color(180, 10, 10));
		g.drawString(errorTextString, errorTextStringX, errorTextStringY);
		
	}
	
	setErrorTextStuff = true;

}

public void drawBackToMenuButton()
{
	if (setBackToMenuButtonStuff == false){
		//
		backToMenuButtonX= (int) (windowWidth*0.02);
		backToMenuButtonY= (int) (windowHeight*0.02);

		//changing back page button colour to be darker if it is hovered over
		backToMenuButtonRight = backToMenuButtonX + buttonWidth;
		backToMenuButtonBottom = backToMenuButtonY + buttonHeight;

		//--drawing the text on the button--
		g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
		backToMenuButtonText = "Back";
		backToMenuButtonTextWidth = g.getFontMetrics().stringWidth(backToMenuButtonText);;
		backToMenuButtonTextHeight = g.getFontMetrics().getAscent();;
		backToMenuButtonTextX = backToMenuButtonX + (int) ((buttonWidth - backToMenuButtonTextWidth)/2);
		backToMenuButtonTextY = backToMenuButtonY + (int) ((buttonHeight - backToMenuButtonTextHeight)/2) + backToMenuButtonTextHeight;

		setBackToMenuButtonStuff = true; //indicates that the above variables can be referenced

	}
	
	if (backToMenuButtonClicked){
		//
		g.setColor(new Color(190, 190, 190));
		backToMenuButtonClicked = false;
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, backToMenuButtonX, backToMenuButtonRight, backToMenuButtonY, backToMenuButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(175, 175, 175));
	}
	else{
		//
		g.setColor(new Color(180, 180, 180));
	}

	//drawing the button
	g.fillRect(backToMenuButtonX, backToMenuButtonY, buttonWidth, buttonHeight);

	//drawing outline 
	g.setColor(new Color(0, 0, 0));
	g.drawRect(backToMenuButtonX, backToMenuButtonY, buttonWidth, buttonHeight);
	//

	//--drawing the text on the button--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));

	
	

	g.drawString(backToMenuButtonText, backToMenuButtonTextX, backToMenuButtonTextY);


}

//DRAWING  QUIZ STUFF END

//DRAWING START PAGE STUFF START 

public void drawStartPage()
{

	drawStartPageTitle();

	drawStartPageMainBox();

	drawLoadedPowerpointText();

	initiateBoxSpacing();

	drawLoadedFilename();

	drawLaunchQuizButton();

	drawChangeFileButton();

	drawResetCompletedQuestionsButton();

	drawSlideModeText();

	drawSlideMode1Button();

	drawSlideMode2Button();

	drawSlideMode3Button();

	drawSlideOrderText();

	drawSlideOrder1Button();

	drawSlideOrder2Button();

	drawResetQuestionsTextIndication();

}

public void drawStartPageTitle()
{
	if (setStartPageTitleStuff == false){
		//--box that the string goes in--
		startPageTitleWidth = (int) (windowWidth*0.33);
		startPageTitleHeight = (int) (windowHeight*0.15);
		startPageTitleX = (int) (windowWidth*0.5 - startPageTitleWidth*0.5);
		startPageTitleY = (int) (windowHeight * 0.01);

		//--text--
		g.setFont(new Font("Monospaced", Font.PLAIN, 50)); 
		
		//top text
		startPageTitleTextUpper = "Powerpoint";
		startPageTitleUpperTextWidth = g.getFontMetrics().stringWidth(startPageTitleTextUpper);
		startPageTitleUpperTextHeight = g.getFontMetrics().getAscent();
		startPageTitleUpperTextX = startPageTitleX + (int) ((startPageTitleX - startPageTitleUpperTextWidth)/2);
		startPageTitleUpperTextY = startPageTitleY + (int) (startPageTitleUpperTextHeight);

		g.setFont(new Font("Monospaced", Font.PLAIN, 50)); 
		//bottom text
		startPageTitleTextLower = "Quiz Generator";
		startPageTitleLowerTextWidth = g.getFontMetrics().stringWidth(startPageTitleTextLower);
		startPageTitleLowerTextHeight = g.getFontMetrics().getAscent();
		startPageTitleLowerTextX = startPageTitleX + (int) ((startPageTitleX - startPageTitleLowerTextWidth)/2);
		startPageTitleLowerTextY = startPageTitleUpperTextY + startPageTitleUpperTextHeight + (int) (windowHeight*0.015);

		setStartPageTitleStuff = true;

	}
	

	g.setColor(new Color(255, 255, 255));
	g.fillRect(startPageTitleX, startPageTitleY, startPageTitleWidth, startPageTitleHeight);

	//black border around the box
	g.setColor(new Color(1, 1, 1));
	g.drawRect(startPageTitleX, startPageTitleY, startPageTitleWidth, startPageTitleHeight);



	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 50)); 
	g.setColor(new Color(0, 0, 0));
	
	
	g.drawString(startPageTitleTextUpper, startPageTitleUpperTextX, startPageTitleUpperTextY);
	
	g.drawString(startPageTitleTextLower, startPageTitleLowerTextX, startPageTitleLowerTextY);




}

public void drawStartPageMainBox()
{
	if (setStartPageMainBoxStuff == false){
		//other stuff/ dependencies
		startPageTitleBottom = (startPageTitleY + startPageTitleHeight);
		//--outer box--
		startPageMainBoxOuterWidth = (int) (windowWidth*0.8);
		startPageMainBoxOuterHeight = (int) (windowHeight-startPageTitleBottom - 2*(windowHeight*0.05));
		startPageMainBoxOuterX =(int)  ((windowWidth - startPageMainBoxOuterWidth)/2);
		startPageMainBoxOuterY = startPageTitleBottom + (int) ((windowHeight - startPageTitleBottom - startPageMainBoxOuterHeight)/2);

		//--inner box--
		startPageMainBoxInnerWidth = startPageMainBoxOuterWidth - (int) (2*(windowWidth*0.01));
		startPageMainBoxInnerHeight = startPageMainBoxOuterHeight - (int) (2*(windowHeight*0.01));
		startPageMainBoxInnerX = startPageMainBoxOuterX + (int) ((startPageMainBoxOuterWidth - startPageMainBoxInnerWidth)/2);
		startPageMainBoxInnerY = startPageMainBoxOuterY + (int) ((startPageMainBoxOuterHeight - startPageMainBoxInnerHeight)/2);

		setStartPageMainBoxStuff = true;
	}
	

	g.setColor(new Color(22, 122, 24));
	g.drawRect(startPageMainBoxOuterX, startPageMainBoxOuterY, startPageMainBoxOuterWidth, startPageMainBoxOuterHeight);
	
	
	
	g.setColor(new Color(22, 207, 159));
	g.fillRect(startPageMainBoxInnerX, startPageMainBoxInnerY, startPageMainBoxInnerWidth, startPageMainBoxInnerHeight);

	//outline for the inner box
	g.setColor(new Color(0, 0, 0));
	g.drawRect(startPageMainBoxInnerX, startPageMainBoxInnerY, startPageMainBoxInnerWidth, startPageMainBoxInnerHeight);

}



public void drawLoadedPowerpointText()
{
	//--text--

	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	startPageLoadedPowerpointText = "Loaded Powerpoint";
	startPageLoadedPowerpointTextWidth = g.getFontMetrics().stringWidth(startPageLoadedPowerpointText);
	startPageLoadedPowerpointTextHeight = g.getFontMetrics().getAscent();
	startPageLoadedPowerpointTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageLoadedPowerpointTextWidth)/2);
	startPageLoadedPowerpointTextY = startPageMainBoxInnerY + startPageLoadedPowerpointTextHeight + (int) (windowHeight*0.01);
	
	g.drawString(startPageLoadedPowerpointText, startPageLoadedPowerpointTextX, startPageLoadedPowerpointTextY);

	setLoadedPowerpointTextStuff = true;
}

public void initiateBoxSpacing()
{
	//
	if (setBoxSpacing == false){
		//
		sideSpacing = (int) (windowWidth*0.01);
		setBoxSpacing = true;
	}
	
}

public void drawLoadedFilename()
{
	//--box that the string goes in--
	startPageLoadedFilenameBoxWidth = startPageMainBoxInnerWidth - (int) (2*sideSpacing);
	startPageLoadedFilenameBoxHeight = (int) (windowHeight*0.05);
	startPageLoadedFilenameBoxX = startPageMainBoxInnerX + sideSpacing;
	startPageLoadedFilenameBoxY = startPageLoadedPowerpointTextY + (int) (windowHeight*0.02);

	g.setColor(new Color(250, 250, 250));
	g.fillRect(startPageLoadedFilenameBoxX, startPageLoadedFilenameBoxY, startPageLoadedFilenameBoxWidth, startPageLoadedFilenameBoxHeight);

	//black border around the box
	g.setColor(new Color(100, 100, 100));
	g.drawRect(startPageLoadedFilenameBoxX, startPageLoadedFilenameBoxY, startPageLoadedFilenameBoxWidth, startPageLoadedFilenameBoxHeight);



	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	startPageLoadedFilenameText = (ConvertPDFPagesToImages.getFileNameFromLocation(slidePDFLocation));
	startPageLoadedFilenameTextWidth = g.getFontMetrics().stringWidth(startPageLoadedFilenameText);
	startPageLoadedFilenameTextHeight = g.getFontMetrics().getAscent();
	startPageLoadedFilenameTextX = startPageLoadedFilenameBoxX + (int) (windowWidth*0.01);
	startPageLoadedFilenameTextY = startPageLoadedFilenameBoxY +startPageLoadedFilenameTextHeight + (int) (windowHeight*0.01);
	
	g.drawString(startPageLoadedFilenameText, startPageLoadedFilenameTextX, startPageLoadedFilenameTextY);

	setLoadedFilenameStuff = true;
}

public void drawLaunchQuizButton()
{
	//
	//--box that the string goes in--
	startPageLaunchQuizButtonWidth = (int) (startPageMainBoxInnerWidth- (2*sideSpacing));
	startPageLaunchQuizButtonHeight = (int) (windowHeight*0.15);
	startPageLaunchQuizButtonX = startPageMainBoxInnerX + sideSpacing;
	startPageLaunchQuizButtonY = (startPageMainBoxInnerY + startPageMainBoxInnerHeight - startPageLaunchQuizButtonHeight) - sideSpacing;
	int startPageLaunchQuizButtonRight = startPageLaunchQuizButtonX + startPageLaunchQuizButtonWidth;
	int startPageLaunchQuizButtonBottom = startPageLaunchQuizButtonY + startPageLaunchQuizButtonHeight;

	if (startPageLaunchQuizButtonClicked == true){
		g.setColor(new Color(110, 240, 25));

		if (startPageLaunchQuizButtonClickedTick == 3){
			startPageLaunchQuizButtonClicked = false;
			startPageLaunchQuizButtonClickedTick = 0;
		}
		else{
			startPageLaunchQuizButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageLaunchQuizButtonX, startPageLaunchQuizButtonRight, startPageLaunchQuizButtonY, startPageLaunchQuizButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(85, 215, 0));
	}
	else{
		//
		g.setColor(new Color(92, 222, 8));
	}
	g.fillRect(startPageLaunchQuizButtonX, startPageLaunchQuizButtonY, startPageLaunchQuizButtonWidth, startPageLaunchQuizButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	g.drawRect(startPageLaunchQuizButtonX, startPageLaunchQuizButtonY, startPageLaunchQuizButtonWidth, startPageLaunchQuizButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageLaunchQuizButtonText = "Lauch Quiz";
	startPageLaunchQuizButtonTextWidth = g.getFontMetrics().stringWidth(startPageLaunchQuizButtonText);
	startPageLaunchQuizButtonTextHeight = g.getFontMetrics().getAscent();
	startPageLaunchQuizButtonTextX = startPageLaunchQuizButtonX + (int) ((startPageLaunchQuizButtonWidth-startPageLaunchQuizButtonTextWidth)/2);
	startPageLaunchQuizButtonTextY = startPageLaunchQuizButtonY + startPageLaunchQuizButtonTextHeight + (int) ((startPageLaunchQuizButtonHeight-startPageLaunchQuizButtonTextHeight)/2);;
	
	g.drawString(startPageLaunchQuizButtonText, startPageLaunchQuizButtonTextX, startPageLaunchQuizButtonTextY);

	setLaunchQuizButtonStuff = true;

}

public void drawChangeFileButton()
{
	//--box that the string goes in--
	startPageChangeFileButtonWidth = (int) ((startPageMainBoxInnerWidth- (3*sideSpacing)) *0.5);
	startPageChangeFileButtonHeight = (int) (windowHeight*0.1);
	startPageChangeFileButtonX = startPageMainBoxInnerX + sideSpacing;
	//bottom of loaded filename box + (dist. from: bottom of loaded filename box to: launch quiz button)/2 [for middle] - (button height/2)
	//	startPageChangeFileButtonY =(startPageLoadedFilenameBoxY + startPageLoadedFilenameBoxHeight) + (int)  (  (startPageLaunchQuizButtonY - (startPageLoadedFilenameBoxY + startPageLoadedFilenameBoxHeight))/2 -(startPageChangeFileButtonHeight/2)  );
	startPageChangeFileButtonY = startPageLoadedFilenameBoxY + startPageLoadedFilenameBoxHeight + sideSpacing;
	int startPageChangeFileButtonRight = startPageChangeFileButtonX + startPageChangeFileButtonWidth;
	int startPageChangeFileButtonBottom = startPageChangeFileButtonY + startPageChangeFileButtonHeight ;

	if (startPageChangeFileButtonClicked == true){
		g.setColor(new Color(208, 208, 208));


		if (startPageChangeFileButtonClickedTick == 3){
			startPageChangeFileButtonClicked = false;
			startPageChangeFileButtonClickedTick = 0;
		}
		else{
			startPageChangeFileButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageChangeFileButtonX, startPageChangeFileButtonRight, startPageChangeFileButtonY, startPageChangeFileButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(183, 183, 183));
	}
	else{
		//
		g.setColor(new Color(190, 190, 190));
	}
	g.fillRect(startPageChangeFileButtonX, startPageChangeFileButtonY, startPageChangeFileButtonWidth, startPageChangeFileButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	g.drawRect(startPageChangeFileButtonX, startPageChangeFileButtonY, startPageChangeFileButtonWidth, startPageChangeFileButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageChangeFileButtonText = "Change File";
	startPageChangeFileButtonTextWidth = g.getFontMetrics().stringWidth(startPageLaunchQuizButtonText);
	startPageChangeFileButtonTextHeight = g.getFontMetrics().getAscent();
	startPageChangeFileButtonTextX = startPageChangeFileButtonX + (int) ((startPageChangeFileButtonWidth-startPageChangeFileButtonTextWidth)/2);
	startPageChangeFileButtonTextY = startPageChangeFileButtonY + startPageChangeFileButtonTextHeight + (int) ((startPageChangeFileButtonHeight-startPageChangeFileButtonTextHeight)/2);;
	
	g.drawString(startPageChangeFileButtonText, startPageChangeFileButtonTextX, startPageChangeFileButtonTextY);

	setChangeFileButtonStuff = true;
}

public void drawResetCompletedQuestionsButton()
{
	//--box that the string goes in--
	startPageResetCompletedQuestionsButtonWidth = (int) ((startPageMainBoxInnerWidth- (3*sideSpacing)) *0.5);
	startPageResetCompletedQuestionsButtonHeight = (int) (windowHeight*0.1);
	startPageResetCompletedQuestionsButtonX = startPageMainBoxInnerX + sideSpacing + startPageChangeFileButtonWidth + sideSpacing;
	//bottom of loaded filename box + (dist. from: bottom of loaded filename box to: launch quiz button)/2 [for middle] - (button height/2)
	startPageResetCompletedQuestionsButtonY =startPageLoadedFilenameBoxY + startPageLoadedFilenameBoxHeight + sideSpacing;
	int startPageResetCompletedQuestionsButtonRight = startPageResetCompletedQuestionsButtonX + startPageResetCompletedQuestionsButtonWidth;
	int startPageResetCompletedQuestionsButtonBottom = startPageResetCompletedQuestionsButtonY + startPageResetCompletedQuestionsButtonHeight;

	if (startPageResetCompletedQuestionsButtonClicked == true){
		g.setColor(new Color(208, 208, 208));

		if (startPageResetCompletedQuestionsButtonClickedTick == 3){
			startPageResetCompletedQuestionsButtonClicked = false;
			startPageResetCompletedQuestionsButtonClickedTick = 0;
		}
		else{
			startPageResetCompletedQuestionsButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageResetCompletedQuestionsButtonX, startPageResetCompletedQuestionsButtonRight, startPageResetCompletedQuestionsButtonY, startPageResetCompletedQuestionsButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(183, 183, 183));
	}
	else{
		//
		g.setColor(new Color(190, 190, 190));
	}
	g.fillRect(startPageResetCompletedQuestionsButtonX, startPageResetCompletedQuestionsButtonY, startPageResetCompletedQuestionsButtonWidth, startPageResetCompletedQuestionsButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	g.drawRect(startPageResetCompletedQuestionsButtonX, startPageResetCompletedQuestionsButtonY, startPageResetCompletedQuestionsButtonWidth, startPageResetCompletedQuestionsButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageResetCompletedQuestionsButtonText = "RESET";
	startPageResetCompletedQuestionsButtonTextWidth = g.getFontMetrics().stringWidth(startPageLaunchQuizButtonText);
	startPageResetCompletedQuestionsButtonTextHeight = g.getFontMetrics().getAscent();
	startPageResetCompletedQuestionsButtonTextX = startPageResetCompletedQuestionsButtonX + (int) ((startPageResetCompletedQuestionsButtonWidth-startPageResetCompletedQuestionsButtonTextWidth)/2);
	startPageResetCompletedQuestionsButtonTextY = startPageResetCompletedQuestionsButtonY + startPageResetCompletedQuestionsButtonTextHeight + (int) ((startPageResetCompletedQuestionsButtonHeight-startPageResetCompletedQuestionsButtonTextHeight)/2);;
	
	g.drawString(startPageResetCompletedQuestionsButtonText, startPageResetCompletedQuestionsButtonTextX, startPageResetCompletedQuestionsButtonTextY);

	setResetCompletedQuestionsButtonStuff = true;
}

////////////////////////////////////////////***************************** */
public void drawSlideModeText()
{
	//--text--

	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	startPageSlideModeText = "Slide Mode";
	startPageSlideModeTextWidth = g.getFontMetrics().stringWidth(startPageSlideModeText);
	startPageSlideModeTextHeight = g.getFontMetrics().getAscent();
	startPageSlideModeTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageSlideModeTextWidth)/2);
	startPageTextY = startPageResetCompletedQuestionsButtonY + startPageResetCompletedQuestionsButtonHeight + startPageSlideModeTextHeight + sideSpacing;
	
	g.drawString(startPageSlideModeText, startPageSlideModeTextX, startPageTextY);

	setSlideModeTextStuff = true;
}


public void drawSlideMode1Button()
{
	//for the highlighted outline
	if (startPageSlideMode1ButtonSelected == true){
		//draw highlighted outline 
		startPageSlideMode1ButtonOutlineX = startPageSlideMode1ButtonX - 2;
		startPageSlideMode1ButtonOutlineY = startPageSlideMode1ButtonY - 2;
		startPageSlideMode1ButtonOutlineW = startPageSlideMode1ButtonWidth + 4;
		startPageSlideMode1ButtonOutlineH = startPageSlideMode1ButtonHeight + 4;

		g.setColor(new Color(245, 197, 22));
		g.fillRect(startPageSlideMode1ButtonOutlineX, startPageSlideMode1ButtonOutlineY, startPageSlideMode1ButtonOutlineW, startPageSlideMode1ButtonOutlineH);
	}

	//--box that the string goes in--
	startPageSlideMode1ButtonWidth = (int) ((startPageMainBoxInnerWidth- (4*sideSpacing)) *0.33);
	startPageSlideMode1ButtonHeight = (int) (windowHeight*0.1);
	startPageSlideMode1ButtonX = startPageMainBoxInnerX + sideSpacing;
	startPageSlideMode1ButtonY = startPageTextY + sideSpacing;
	int startPageSlideMode1ButtonRight = startPageSlideMode1ButtonX + startPageSlideMode1ButtonWidth;
	int startPageSlideMode1ButtonBottom = startPageSlideMode1ButtonY + startPageSlideMode1ButtonHeight;

	if (startPageSlideMode1ButtonClicked == true){
		g.setColor(new Color(138, 243, 255));

		if (startPageSlideMode1ButtonClickedTick == 3){
			startPageSlideMode1ButtonClicked = false;
			startPageSlideMode1ButtonClickedTick = 0;
		}
		else{
			startPageSlideMode1ButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageSlideMode1ButtonX, startPageSlideMode1ButtonRight, startPageSlideMode1ButtonY, startPageSlideMode1ButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(113, 218, 230));
	}
	else{
		//
		g.setColor(new Color(120, 225, 237));

	}
	g.fillRect(startPageSlideMode1ButtonX, startPageSlideMode1ButtonY, startPageSlideMode1ButtonWidth, startPageSlideMode1ButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	if (startPageSlideMode1ButtonSelected == true){
		g.setColor(new Color(245, 197, 22));
	}
	g.drawRect(startPageSlideMode1ButtonX, startPageSlideMode1ButtonY, startPageSlideMode1ButtonWidth, startPageSlideMode1ButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageSlideMode1ButtonText = "Uncomplete";
	startPageSlideMode1ButtonTextWidth = g.getFontMetrics().stringWidth(startPageSlideMode1ButtonText);
	startPageSlideMode1ButtonTextHeight = g.getFontMetrics().getAscent();
	startPageSlideMode1ButtonTextX = startPageSlideMode1ButtonX + (int) ((startPageSlideMode1ButtonWidth-startPageSlideMode1ButtonTextWidth)/2);
	startPageSlideMode1ButtonTextY = startPageSlideMode1ButtonY + startPageSlideMode1ButtonTextHeight + (int) ((startPageSlideMode1ButtonHeight-startPageSlideMode1ButtonTextHeight)/2);;
	
	g.drawString(startPageSlideMode1ButtonText, startPageSlideMode1ButtonTextX, startPageSlideMode1ButtonTextY);


	setSlideMode1ButtonStuff = true;
}

public void drawSlideMode2Button()
{
	//for the highlighted outline
	if (startPageSlideMode2ButtonSelected == true){
		//draw highlighted outline 
		startPageSlideMode2ButtonOutlineX = startPageSlideMode2ButtonX - 2;
		startPageSlideMode2ButtonOutlineY = startPageSlideMode2ButtonY - 2;
		startPageSlideMode2ButtonOutlineW = startPageSlideMode2ButtonWidth + 4;
		startPageSlideMode2ButtonOutlineH = startPageSlideMode2ButtonHeight + 4;

		g.setColor(new Color(245, 197, 22));
		g.fillRect(startPageSlideMode2ButtonOutlineX, startPageSlideMode2ButtonOutlineY, startPageSlideMode2ButtonOutlineW, startPageSlideMode2ButtonOutlineH);
	}

	//--box that the string goes in--
	startPageSlideMode2ButtonWidth = (int) ((startPageMainBoxInnerWidth- (4*sideSpacing)) *0.33);
	startPageSlideMode2ButtonHeight = (int) (windowHeight*0.1);
	startPageSlideMode2ButtonX = startPageMainBoxInnerX + sideSpacing + startPageSlideMode1ButtonWidth + sideSpacing;
	startPageSlideMode2ButtonY = startPageTextY + sideSpacing;
	int startPageSlideMode2ButtonRight = startPageSlideMode2ButtonX + startPageSlideMode2ButtonWidth;
	int startPageSlideMode2ButtonBottom = startPageSlideMode2ButtonY + startPageSlideMode2ButtonHeight;

	if (startPageSlideMode2ButtonClicked == true){
		g.setColor(new Color(138, 248, 255));

		if (startPageSlideMode2ButtonClickedTick == 3){
			startPageSlideMode2ButtonClicked = false;
			startPageSlideMode2ButtonClickedTick = 0;
		}
		else{
			startPageSlideMode2ButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageSlideMode2ButtonX, startPageSlideMode2ButtonRight, startPageSlideMode2ButtonY, startPageSlideMode2ButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(113, 218, 230));
	}
	else{
		//
		g.setColor(new Color(120, 225, 237));
	}
	g.fillRect(startPageSlideMode2ButtonX, startPageSlideMode2ButtonY, startPageSlideMode2ButtonWidth, startPageSlideMode2ButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	if (startPageSlideMode2ButtonSelected == true){
		g.setColor(new Color(245, 197, 22));
	}
	g.drawRect(startPageSlideMode2ButtonX, startPageSlideMode2ButtonY, startPageSlideMode2ButtonWidth, startPageSlideMode2ButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageSlideMode2ButtonText = "Wrong";
	startPageSlideMode2ButtonTextWidth = g.getFontMetrics().stringWidth(startPageSlideMode2ButtonText);
	startPageSlideMode2ButtonTextHeight = g.getFontMetrics().getAscent();
	startPageSlideMode2ButtonTextX = startPageSlideMode2ButtonX + (int) ((startPageSlideMode2ButtonWidth-startPageSlideMode2ButtonTextWidth)/2);
	startPageSlideMode2ButtonTextY = startPageSlideMode2ButtonY + startPageSlideMode2ButtonTextHeight + (int) ((startPageSlideMode2ButtonHeight-startPageSlideMode2ButtonTextHeight)/2);;
	
	g.drawString(startPageSlideMode2ButtonText, startPageSlideMode2ButtonTextX, startPageSlideMode2ButtonTextY);

	

	setSlideMode2ButtonStuff = true;
}

public void drawSlideMode3Button()
{
	//for the highlighted outline
	if (startPageSlideMode3ButtonSelected == true){
		//draw highlighted outline 
		startPageSlideMode3ButtonOutlineX = startPageSlideMode3ButtonX - 2;
		startPageSlideMode3ButtonOutlineY = startPageSlideMode3ButtonY - 2;
		startPageSlideMode3ButtonOutlineW = startPageSlideMode3ButtonWidth + 4;
		startPageSlideMode3ButtonOutlineH = startPageSlideMode3ButtonHeight + 4;

		g.setColor(new Color(245, 197, 22));
		g.fillRect(startPageSlideMode3ButtonOutlineX, startPageSlideMode3ButtonOutlineY, startPageSlideMode3ButtonOutlineW, startPageSlideMode3ButtonOutlineH);
	}

	//--box that the string goes in--
	startPageSlideMode3ButtonWidth = (int) ((startPageMainBoxInnerWidth- (4*sideSpacing)) *0.33);
	startPageSlideMode3ButtonHeight = (int) (windowHeight*0.1);
	startPageSlideMode3ButtonX = startPageMainBoxInnerX + sideSpacing + startPageSlideMode1ButtonWidth + sideSpacing + startPageSlideMode2ButtonWidth + sideSpacing;
	startPageSlideMode3ButtonY = startPageTextY + sideSpacing;
	int startPageSlideMode3ButtonRight = startPageSlideMode3ButtonX + startPageSlideMode3ButtonWidth;
	int startPageSlideMode3ButtonBottom = startPageSlideMode3ButtonY + startPageSlideMode3ButtonHeight;

	if (startPageSlideMode3ButtonClicked == true){
		g.setColor(new Color(138, 248, 255));

		if (startPageSlideMode3ButtonClickedTick == 3){
			startPageSlideMode3ButtonClicked = false;
			startPageSlideMode3ButtonClickedTick = 0;
		}
		else{
			startPageSlideMode3ButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageSlideMode3ButtonX, startPageSlideMode3ButtonRight, startPageSlideMode3ButtonY, startPageSlideMode3ButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(113, 218, 230));
	}
	else{
		//
		g.setColor(new Color(120, 225, 237));
	}
	g.fillRect(startPageSlideMode3ButtonX, startPageSlideMode3ButtonY, startPageSlideMode3ButtonWidth, startPageSlideMode3ButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	if (startPageSlideMode3ButtonSelected == true){
		g.setColor(new Color(245, 197, 22));
	}
	g.drawRect(startPageSlideMode3ButtonX, startPageSlideMode3ButtonY, startPageSlideMode3ButtonWidth, startPageSlideMode3ButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageSlideMode3ButtonText = "Wrong Last Attempt";
	startPageSlideMode3ButtonTextWidth = g.getFontMetrics().stringWidth(startPageSlideMode3ButtonText);
	startPageSlideMode3ButtonTextHeight = g.getFontMetrics().getAscent();
	startPageSlideMode3ButtonTextX = startPageSlideMode3ButtonX + (int) ((startPageSlideMode3ButtonWidth-startPageSlideMode3ButtonTextWidth)/2);
	startPageSlideMode3ButtonTextY = startPageSlideMode3ButtonY + startPageSlideMode3ButtonTextHeight + (int) ((startPageSlideMode3ButtonHeight-startPageSlideMode3ButtonTextHeight)/2);;
	
	g.drawString(startPageSlideMode3ButtonText, startPageSlideMode3ButtonTextX, startPageSlideMode3ButtonTextY);

	setSlideMode3ButtonStuff = true;
}

public void drawSlideOrderText()
{
	//--text--

	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	startPageSlideOrderText = "Slide Order";
	startPageSlideOrderTextWidth = g.getFontMetrics().stringWidth(startPageSlideOrderText);
	startPageSlideOrderTextHeight = g.getFontMetrics().getAscent();
	startPageSlideOrderTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageSlideOrderTextWidth)/2);
	startPageSlideOrderTextY = startPageSlideMode1ButtonY + startPageSlideMode1ButtonHeight + sideSpacing + startPageSlideOrderTextHeight;
	
	g.drawString(startPageSlideOrderText, startPageSlideOrderTextX, startPageSlideOrderTextY);

	setSlideOrderTextStuff = true;
}

public void drawSlideOrder1Button()
{
	//for the highlighted outline
	if (startPageSlideOrder1ButtonSelected == true){
		//draw highlighted outline 
		startPageSlideOrder1ButtonOutlineX = startPageSlideOrder1ButtonX - 2;
		startPageSlideOrder1ButtonOutlineY = startPageSlideOrder1ButtonY - 2;
		startPageSlideOrder1ButtonOutlineW = startPageSlideOrder1ButtonWidth + 4;
		startPageSlideOrder1ButtonOutlineH = startPageSlideOrder1ButtonHeight + 4;

		g.setColor(new Color(245, 197, 22));
		g.fillRect(startPageSlideOrder1ButtonOutlineX, startPageSlideOrder1ButtonOutlineY, startPageSlideOrder1ButtonOutlineW, startPageSlideOrder1ButtonOutlineH);
	}

	//--box that the string goes in--
	startPageSlideOrder1ButtonWidth = (int) ((startPageMainBoxInnerWidth- (3*sideSpacing)) *0.5);
	startPageSlideOrder1ButtonHeight = (int) (windowHeight*0.1);
	startPageSlideOrder1ButtonX = startPageMainBoxInnerX + sideSpacing;
	startPageSlideOrder1ButtonY = startPageSlideOrderTextY + sideSpacing;
	int startPageSlideOrder1ButtonRight = startPageSlideOrder1ButtonX + startPageSlideOrder1ButtonWidth;
	int startPageSlideOrder1ButtonBottom = startPageSlideOrder1ButtonY + startPageSlideOrder1ButtonHeight;

	if (startPageSlideOrder1ButtonClicked == true){
		g.setColor(new Color(138, 243, 255));

		if (startPageSlideOrder1ButtonClickedTick == 3){
			startPageSlideOrder1ButtonClicked = false;
			startPageSlideOrder1ButtonClickedTick = 0;
		}
		else{
			startPageSlideOrder1ButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageSlideOrder1ButtonX, startPageSlideOrder1ButtonRight, startPageSlideOrder1ButtonY, startPageSlideOrder1ButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(113, 218, 230));
	}
	else{
		//
		g.setColor(new Color(129, 225, 237));
	}
	g.fillRect(startPageSlideOrder1ButtonX, startPageSlideOrder1ButtonY, startPageSlideOrder1ButtonWidth, startPageSlideOrder1ButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	if (startPageSlideOrder1ButtonSelected == true){
		g.setColor(new Color(245, 197, 22));
	}
	g.drawRect(startPageSlideOrder1ButtonX, startPageSlideOrder1ButtonY, startPageSlideOrder1ButtonWidth, startPageSlideOrder1ButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageSlideOrder1ButtonText = "Random";
	startPageSlideOrder1ButtonTextWidth = g.getFontMetrics().stringWidth(startPageSlideOrder1ButtonText);
	startPageSlideOrder1ButtonTextHeight = g.getFontMetrics().getAscent();
	startPageSlideOrder1ButtonTextX = startPageSlideOrder1ButtonX + (int) ((startPageSlideOrder1ButtonWidth-startPageSlideOrder1ButtonTextWidth)/2);
	startPageSlideOrder1ButtonTextY = startPageSlideOrder1ButtonY + startPageSlideOrder1ButtonTextHeight + (int) ((startPageSlideOrder1ButtonHeight-startPageSlideOrder1ButtonTextHeight)/2);;
	
	g.drawString(startPageSlideOrder1ButtonText, startPageSlideOrder1ButtonTextX, startPageSlideOrder1ButtonTextY);

	setSlideOrder1ButtonStuff = true;
}

public void drawSlideOrder2Button()
{
	//for the highlighted outline
	if (startPageSlideOrder2ButtonSelected == true){
		//draw highlighted outline 
		startPageSlideOrder2ButtonOutlineX = startPageSlideOrder2ButtonX - 2;
		startPageSlideOrder2ButtonOutlineY = startPageSlideOrder2ButtonY - 2;
		startPageSlideOrder2ButtonOutlineW = startPageSlideOrder2ButtonWidth + 4;
		startPageSlideOrder2ButtonOutlineH = startPageSlideOrder2ButtonHeight + 4;

		g.setColor(new Color(245, 197, 22));
		g.fillRect(startPageSlideOrder2ButtonOutlineX, startPageSlideOrder2ButtonOutlineY, startPageSlideOrder2ButtonOutlineW, startPageSlideOrder2ButtonOutlineH);
	}

	//--box that the string goes in--
	startPageSlideOrder2ButtonWidth = (int) ((startPageMainBoxInnerWidth- (3*sideSpacing)) *0.5);
	startPageSlideOrder2ButtonHeight = (int) (windowHeight*0.1);
	startPageSlideOrder2ButtonX = startPageMainBoxInnerX + sideSpacing + startPageSlideOrder1ButtonWidth+ sideSpacing;
	startPageSlideOrder2ButtonY = startPageSlideOrderTextY + sideSpacing;
	int startPageSlideOrder2ButtonRight = startPageSlideOrder2ButtonX + startPageSlideOrder2ButtonWidth;
	int startPageSlideOrder2ButtonBottom = startPageSlideOrder2ButtonY + startPageSlideOrder2ButtonHeight;

	if (startPageSlideOrder2ButtonClicked == true){
		g.setColor(new Color(138, 243, 255));

		if (startPageSlideOrder2ButtonClickedTick == 3){
			startPageSlideOrder2ButtonClicked = false;
			startPageSlideOrder2ButtonClickedTick = 0;
		}
		else{
			startPageSlideOrder2ButtonClickedTick++;
		}
	}
	else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, startPageSlideOrder2ButtonX, startPageSlideOrder2ButtonRight, startPageSlideOrder2ButtonY, startPageSlideOrder2ButtonBottom) == true){
		//user is hovering over this button
		g.setColor(new Color(113, 218, 230));
	}
	else{
		//
		g.setColor(new Color(120, 225, 237));
	}
	g.fillRect(startPageSlideOrder2ButtonX, startPageSlideOrder2ButtonY, startPageSlideOrder2ButtonWidth, startPageSlideOrder2ButtonHeight);

	//outline of the button
	g.setColor(new Color(0, 0, 0));
	if (startPageSlideOrder2ButtonSelected == true){
		g.setColor(new Color(245, 197, 22));
	}
	g.drawRect(startPageSlideOrder2ButtonX, startPageSlideOrder2ButtonY, startPageSlideOrder2ButtonWidth, startPageSlideOrder2ButtonHeight);

	//--text--
	g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
	g.setColor(new Color(0, 0, 0));
	
	//top text
	startPageSlideOrder2ButtonText = "Descending Wrongness";
	startPageSlideOrder2ButtonTextWidth = g.getFontMetrics().stringWidth(startPageSlideOrder2ButtonText);
	startPageSlideOrder2ButtonTextHeight = g.getFontMetrics().getAscent();
	startPageSlideOrder2ButtonTextX = startPageSlideOrder2ButtonX + (int) ((startPageSlideOrder2ButtonWidth-startPageSlideOrder2ButtonTextWidth)/2);
	startPageSlideOrder2ButtonTextY = startPageSlideOrder2ButtonY + startPageSlideOrder2ButtonTextHeight + (int) ((startPageSlideOrder2ButtonHeight-startPageSlideOrder2ButtonTextHeight)/2);;
	
	g.drawString(startPageSlideOrder2ButtonText, startPageSlideOrder2ButtonTextX, startPageSlideOrder2ButtonTextY);

	setSlideOrder2ButtonStuff = true;
}


////////////////////////////////****************************** */

public void drawResetQuestionsTextIndication()
{
	//--text--

	int startPageResetCompletedQuestionsButtonBottom = startPageResetCompletedQuestionsButtonY + startPageResetCompletedQuestionsButtonHeight;
	
	startPageResetQuestionsIndicationText = "The completed questions have been reset";
	startPageResetQuestionsIndicationTextWidth = g.getFontMetrics().stringWidth(startPageResetQuestionsIndicationText);
	startPageResetQuestionsIndicationTextHeight = g.getFontMetrics().getAscent();
	startPageResetQuestionsIndicationTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageResetQuestionsIndicationTextWidth)/2);
	startPageResetQuestionsIndicationTextY = startPageTitleY + startPageTitleHeight +startPageResetQuestionsIndicationTextHeight +  sideSpacing;//startPageResetCompletedQuestionsButtonBottom+ (int) ((startPageLaunchQuizButtonY-startPageResetCompletedQuestionsButtonBottom)/2) + (int) (startPageResetQuestionsIndicationTextHeight/2);
	setResetQuestionsIndicationText = true;

	if (startPageResetQuestionsIndicationTextActivated == true){
		g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
		g.setColor(new Color(0, 0, 128));
		g.drawString(startPageResetQuestionsIndicationText, startPageResetQuestionsIndicationTextX, startPageResetQuestionsIndicationTextY);


		if (startPageResetQuestionsIndicationTextActivatedTick == 30){
			startPageResetQuestionsIndicationTextActivated = false;
			startPageResetQuestionsIndicationTextActivatedTick = 0;
		}
		else{
			startPageResetQuestionsIndicationTextActivatedTick++;
		}
	}

	
}




//}}

//CLEARING THE COMPLETED QUESTIONS TEXT FILE FOR THE SELECTED POWERPOINT
    public void clearGivenTextFile(String powerpiontPdfFileLocation) {
        String textFilename = getFileNameFromLocation(powerpiontPdfFileLocation);
        String filenameAsTextFile = textFilename + "_completed_questions_array.txt";

        FileWriter fwOb;
        try {
            fwOb = new FileWriter(filenameAsTextFile, false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();

        } catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("could not clear the given text file");
        }

	}

	public void setAllSlidesToUnseen(String powerpointPdfFileLocation)
	{
		//
		String textFilename = getFileNameFromLocation(powerpointPdfFileLocation);
		String filenameAsTextFile = textFilename + "_completed_questions_array.txt";
		
		//stuff
		
		Deque<Integer> slideNumberQueue = new LinkedList<>();
		Deque<Integer> incorrectAttemptsQueue = new LinkedList<>();

		/*
		get each line
		parse values
		re-write with last two columns false
		*/
		File textfileToRead = new File(filenameAsTextFile);



        if(textfileToRead.exists() && !textfileToRead.isDirectory()) { 
			//
			//--GETTING THE CURRENT CONTENTS--
			int entriesFound = 0;
	
			int currentSlideNumber;
			int currentQuestionTimesIncorrect;
			try {            
				try (BufferedReader br = new BufferedReader(new FileReader(textfileToRead))) {
					String line;
					String[] splitLine;
					while ((line = br.readLine()) != null) {
						// process the line.
						splitLine = line.split(",");
						if (splitLine.length == 4){
							//
							currentSlideNumber = Integer.parseInt(splitLine[0]);
							currentQuestionTimesIncorrect = Integer.parseInt(splitLine[1]);
							
						}
						else{
							//if there are not 4 values
							currentSlideNumber = 0;
							currentQuestionTimesIncorrect = 0;
						}
	
						//
						slideNumberQueue.push(currentSlideNumber);
						incorrectAttemptsQueue.push(currentQuestionTimesIncorrect);
						//
						entriesFound++;
						
					}
					br.close();
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("could not set all of the slides to unseen");
				}
	
			} 
			finally{
				//
			} 

			//--RE-WRITING THE NEW VALUES--

			BufferedWriter fileWriter = null;
			FileWriter fileWriteHandle;
			String stringSlideNum;
			String stringIncorrectAttempts;
			String stringCurrentlyCompleted = "false";
			String stringWrongLastTime = "false";

			String lineToWrite;			
			try {
				fileWriteHandle = new FileWriter(filenameAsTextFile);
				fileWriter = new BufferedWriter(fileWriteHandle);
				for (int i = 0; i < entriesFound; i++) {
					// or
					stringSlideNum = String.valueOf(slideNumberQueue.pollLast());
					stringIncorrectAttempts = String.valueOf(incorrectAttemptsQueue.pollLast());
					lineToWrite = stringSlideNum + "," + stringIncorrectAttempts + "," +stringCurrentlyCompleted + "," + stringWrongLastTime;
					fileWriter.write(lineToWrite);
					fileWriter.newLine();
				}
				fileWriter.flush();
				fileWriter.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("could not re-write the new values to the file");
			}
		}

        
	}
	
	// need to validate this
    public static String getFileNameFromLocation(String location) {
        int lastSlash = location.lastIndexOf("/") + 1;
        String fullFilename = location.substring(lastSlash);
        int lastDotIndex = fullFilename.lastIndexOf('.');
        String fileName = fullFilename.substring(0, lastDotIndex);
        return fileName;
	}
	
//}}



}
  