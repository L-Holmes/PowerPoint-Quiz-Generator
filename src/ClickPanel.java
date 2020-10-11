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
clicking only seems to go onto the first half of the second line- must
be something wrong with the new function
*/

/*
text box tip for myself:
don't add the newlines and dashes to the actual text as they can then not be distinguished from
the ones that the user actually added thenselves
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

	//drawing the start page handler stuff ++++++++++++++++++++++++++++++++++++++++
	private ClickPanelDrawStartPage startPageDrawer;
	private boolean startPageDrawerSet = false;

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

	//--drawing the answer text box--
	private TextBox typingTextBox;
	int textBoxSpacing;
	int textBoxWidth;
	int textBoxHeight;
	int textBoxX;
	int textBoxY; 
	int textBoxRight;
	int textBoxBottom;

	boolean setTextBoxStuff;
							
	
	//---}}---

	//---DRAWING THE START PAGE START---


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
		setRedXStuff = false;
		setGreenTickStuff = false;
		setDoneQuestionTickStuff = false;
		setErrorTextStuff = false;
		setBackToMenuButtonStuff = false;
		setTextBoxStuff = false;
		
	}


	

//--GETTERS & SETTERS--

	/**
	 * @return the slide mode 1 value
	 */
	public boolean getSlideMode1()
	{
		if (startPageDrawerSet == true){
			return startPageDrawer.getSlideMode1Selected();
		}
		System.out.println("Error: start page drawer not initialized");
		return false;
	}

	/**
	 * @return the slide mode 2 value
	 */
	public boolean getSlideMode2()
	{
		if (startPageDrawerSet == true){
			return startPageDrawer.getSlideMode2Selected();
		}
		System.out.println("Error: start page drawer not initialized");
		return false;
	}

	/**
	 * @return the slide mode 3 value
	 */
	public boolean getSlideMode3()
	{
		if (startPageDrawerSet == true){
			return startPageDrawer.getSlideMode3Selected();
		}
		System.out.println("Error: start page drawer not initialized");
		return false;
	}

	/**
	 * @return the slide order 1 value
	 */
	public boolean getSlideOrder1()
	{
		if (startPageDrawerSet == true){
			return startPageDrawer.getSlideOrder1Selected();
		}
		System.out.println("Error: start page drawer not initialized");
		return false;
	}

	/**
	 * @return the slide order 2 value
	 */
	public boolean getSlideOrder2()
	{
		if (startPageDrawerSet == true){
			return startPageDrawer.getSlideOrder2Selected();
		}
		System.out.println("Error: start page drawer not initialized");
		return false;
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
					//drawStartPage();++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					if (startPageDrawerSet == false){
						startPageDrawer = new ClickPanelDrawStartPage(windowWidth, windowHeight);
						startPageDrawerSet = true;
					}
					startPageDrawer.drawStartPage(g, slidePDFLocation, pointXCoord, pointYCoord);
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
			backToMenuButtonClickCheck(pointXCoord, pointYCoord);
			textBoxClickCheck(pointXCoord, pointYCoord);
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

	/**
	 * checks whether the textbox has been clicked
	 * 
	 */
	public void textBoxClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (setTextBoxStuff == true){
			//
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, textBoxX, textBoxY, textBoxWidth, textBoxHeight)){
					//if the user is already in the text box and wants to change their cursor position
					if (textBoxEntered == true){
						//update the text box
						typingTextBox.updateLeftAndRightText((int) clickedXCoord, (int) clickedYCoord);
					}
					typingTextBox.setEntered(true);
					textBoxEntered = true;
			}
			else{
				//resets the text box cursor
				typingTextBox.setEntered(false);
				textBoxEntered = false;
			}
		}
		else{
			//resets the text box cursor 
			typingTextBox.setEntered(false); //for the text box
			textBoxEntered = false; //for this class
		}
	}

//CHANGE TO START PAGE CLICK CHECKS

	public void startPageLaunchQuizButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{

		
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getLaunchQuizButtonX() ;
			int buttonY = startPageDrawer.getLaunchQuizButtonY() ;
			int buttonW = startPageDrawer.getLaunchQuizButtonW() ;
			int buttonH = startPageDrawer.getLaunchQuizButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				currentPage = "2";
				startPageDrawer.setLaunchQuizButtonClicked(true);
			}
		}
	}


	public void startPageChangeFileButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getChangeFileButtonX();
			int buttonY = startPageDrawer.getChangeFileButtonY() ;
			int buttonW = startPageDrawer.getChangeFileButtonW() ;
			int buttonH = startPageDrawer.getChangeFileButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				changeLoadedFile();
				startPageDrawer.setChangeFileButtonClicked(true);
			}
		}
	}

	public void startPageResetCompletedQuestionsButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getResetCompletedQuestionsButtonX();
			int buttonY = startPageDrawer.getResetCompletedQuestionsButtonY();
			int buttonW = startPageDrawer.getResetCompletedQuestionsButtonW();
			int buttonH = startPageDrawer.getResetCompletedQuestionsButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				setAllSlidesToUnseen(slidePDFLocation);
				startPageDrawer.setResetCompletedQuestionsIndicationTextActivated(true);
				startPageDrawer.setResetCompletedQuestionsButtonClicked(true);
			}
		}
	}
	
	public void startPageSlideMode1ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getSlideMode1ButtonX();
			int buttonY = startPageDrawer.getSlideMode1ButtonY();
			int buttonW = startPageDrawer.getSlideMode1ButtonW();
			int buttonH = startPageDrawer.getSlideMode1ButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				//do stuff
				if (startPageDrawer.getSlideMode1ButtonSelected() == false){
					startPageDrawer.setSlideMode1ButtonSelected(true);
				}
				else{
					if ((startPageDrawer.getSlideMode2ButtonSelected() == true) || (startPageDrawer.getSlideMode3ButtonSelected() == true)){
						//
						startPageDrawer.setSlideMode1ButtonSelected(false);
					}
				}
				//
				startPageDrawer.setSlideMode1ButtonClicked(true);
			}
		}
	}

	public void startPageSlideMode2ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getSlideMode2ButtonX();
			int buttonY = startPageDrawer.getSlideMode2ButtonY();
			int buttonW = startPageDrawer.getSlideMode2ButtonW();
			int buttonH = startPageDrawer.getSlideMode2ButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				//do stuff
				if (startPageDrawer.getSlideMode2ButtonSelected() == false){
					startPageDrawer.setSlideMode2ButtonSelected(true);				}
				else{
					if ((startPageDrawer.getSlideMode1ButtonSelected() == true) || (startPageDrawer.getSlideMode3ButtonSelected() == true)){
						//
						startPageDrawer.setSlideMode2ButtonSelected(false);
					}
				}
				//
				startPageDrawer.setSlideMode2ButtonClicked(true);

			}
		}
	}

	public void startPageSlideMode3ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getSlideMode3ButtonX();
			int buttonY = startPageDrawer.getSlideMode3ButtonY();
			int buttonW = startPageDrawer.getSlideMode3ButtonW();
			int buttonH = startPageDrawer.getSlideMode3ButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				//do stuff
				if (startPageDrawer.getSlideMode3ButtonSelected() == false){
					startPageDrawer.setSlideMode3ButtonSelected(true);				}
				else{
					if ((startPageDrawer.getSlideMode1ButtonSelected() == true) || (startPageDrawer.getSlideMode2ButtonSelected() == true)){
						//
						startPageDrawer.setSlideMode3ButtonSelected(false);
					}
				}
				//
				startPageDrawer.setSlideMode3ButtonClicked(true);

			}
		}
	}

	public void startPageSlideOrder1ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getSlideOrder1ButtonX();
			int buttonY = startPageDrawer.getSlideOrder1ButtonY();
			int buttonW = startPageDrawer.getSlideOrder1ButtonW();
			int buttonH = startPageDrawer.getSlideOrder1ButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				//do stuff
				if (startPageDrawer.getSlideOrder1ButtonSelected() == false){
					startPageDrawer.setSlideOrder2ButtonSelected(false);
					startPageDrawer.setSlideOrder1ButtonSelected(true);
				}			
				//
				startPageDrawer.setSlideOrder1ButtonClicked(true);
			}
		}
	}

	public void startPageSlideOrder2ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getSlideOrder2ButtonX();
			int buttonY = startPageDrawer.getSlideOrder2ButtonY();
			int buttonW = startPageDrawer.getSlideOrder2ButtonW();
			int buttonH = startPageDrawer.getSlideOrder2ButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				//do stuff
				if (startPageDrawer.getSlideOrder2ButtonSelected() == false){
					startPageDrawer.setSlideOrder1ButtonSelected(false);
					startPageDrawer.setSlideOrder2ButtonSelected(true);
				}			
				//
				startPageDrawer.setSlideOrder2ButtonClicked(true);
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
		//textBoxTextLeftOfCursor = "";
		if (pdfHandlerSet == true){
			//write green tick stuff here 
			pdfHandler.handleGreenTickClicked();	
		}
	}
	
	public void redXClicked() 
	{
		//textBoxTextLeftOfCursor = "";
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


		HandleTextEntered(typedKey, e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void HandleTextEntered(String typedChar, KeyEvent keyEvent)
	{
		if (currentPage == "1"){
			//start page
			if (typedChar == "enter"){
				//launch quiz button click
				currentPage = "2";
				startPageDrawer.setLaunchQuizButtonClicked(true);
			}
			else if (typedChar.equals("c")){
				//change file button click
				changeLoadedFile();
				startPageDrawer.setChangeFileButtonClicked(true);
			}
			else if (typedChar.equals("r")){
				//reset button click
				setAllSlidesToUnseen(slidePDFLocation);
				startPageDrawer.setResetCompletedQuestionsIndicationTextActivated(true);
				startPageDrawer.setResetCompletedQuestionsButtonClicked(true);
			}
			else if (typedChar.equals("1")){
				//slide mode 1 button click
				if (startPageDrawer.getSlideMode1ButtonSelected() == false){
					startPageDrawer.setSlideMode1ButtonSelected(true);
				}
				else{
					if ((startPageDrawer.getSlideMode2ButtonSelected() == true) || (startPageDrawer.getSlideMode3ButtonSelected() == true)){
						//
						startPageDrawer.setSlideMode1ButtonSelected(false);
					}
				}
				//
				startPageDrawer.setSlideMode1ButtonClicked(true);
				
			}
			else if (typedChar.equals("2")){
				//slide mode 2 button click
				if (startPageDrawer.getSlideMode2ButtonSelected() == false){
					startPageDrawer.setSlideMode2ButtonSelected(true);				}
				else{
					if ((startPageDrawer.getSlideMode1ButtonSelected() == true) || (startPageDrawer.getSlideMode3ButtonSelected() == true)){
						//
						startPageDrawer.setSlideMode2ButtonSelected(false);
					}
				}
				//
				startPageDrawer.setSlideMode2ButtonClicked(true);
				
			}
			else if (typedChar.equals("3")){
				//slide mode 3 button click
				if (startPageDrawer.getSlideMode3ButtonSelected() == false){
					startPageDrawer.setSlideMode3ButtonSelected(true);				}
				else{
					if ((startPageDrawer.getSlideMode1ButtonSelected() == true) || (startPageDrawer.getSlideMode2ButtonSelected() == true)){
						//
						startPageDrawer.setSlideMode3ButtonSelected(false);
					}
				}
				//
				startPageDrawer.setSlideMode3ButtonClicked(true);
				
			}
			else if (typedChar.equals("[")){
				//slide option 1 button click
				if (startPageDrawer.getSlideOrder1ButtonSelected() == false){
					startPageDrawer.setSlideOrder2ButtonSelected(false);
					startPageDrawer.setSlideOrder1ButtonSelected(true);
				}			
				//
				startPageDrawer.setSlideOrder1ButtonClicked(true);
				
			}
			else if (typedChar.equals("]")){
				//slide option 2 button click
				if (startPageDrawer.getSlideOrder2ButtonSelected() == false){
					startPageDrawer.setSlideOrder1ButtonSelected(false);
					startPageDrawer.setSlideOrder2ButtonSelected(true);
				}			
				//
				startPageDrawer.setSlideOrder2ButtonClicked(true);
				
			}
		}
		else if (currentPage == "2"){
			//quiz page
			if (textBoxEntered == true){
				//user typed into the text box
				typingTextBox.typeLetter(typedChar, keyEvent.getExtendedKeyCode());
				
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
					typingTextBox.setEntered(true);
					textBoxEntered = true;
					
				}
				else if (typedChar.equals("/")){
					//text box clicked
					//textBoxTextLeftOfCursor = ""; reset the text in the text box
					
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

	//--DRAWING THE TEXT BOX--
	drawTypingTextBox();
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
	

//https://stackoverflow.com/questions/3976616/how-to-find-nth-occurrence-of-character-in-a-string
//accessed: 22/08/2020
public static int ordinalIndexOf(String str, String substr, int n) {
    int pos = str.indexOf(substr);
    while (--n > 0 && pos != -1){
		pos = str.indexOf(substr, pos + 1);
	}
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

public void drawTypingTextBox()
{
	if (setTextBoxStuff == false){

		//initiate the dimension & location
		textBoxSpacing = (int) ((float) (windowWidth)*0.02);
		textBoxWidth = (int) (windowWidth*0.34);
		textBoxHeight=(int) (textBoxWidth*0.56);

		textBoxX = windowWidth - textBoxSpacing - textBoxWidth;
		textBoxY=(int) (windowHeight/2 - (windowWidth*0.6*0.56)/2); //lines up with slide image

		//create the text box object
		typingTextBox  = new TextBox(g, textBoxX, textBoxY, textBoxWidth, textBoxHeight);
		setTextBoxStuff = true;
	}

	//draw the text box
	typingTextBox.drawTextBox(g);
}

//DRAWING  QUIZ STUFF END


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
  