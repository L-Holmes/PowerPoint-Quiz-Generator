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

/**
 * @author Lindon Holmes
 * sections:
 * 
 */
class ClickPanel extends JPanel implements MouseListener, KeyListener {
	//Initialising drawing surface here
	private Graphics2D drawingSurface;
	private BufferedImage i;
	private Graphics2D g;

	// JFrame object
	private WindowHandle thisWindow;

	// used to close the window
	private boolean exiting = false;

	// determines whether graphics have been drawn onto the screen
	private boolean rendered = false;

	//location of the powerpoint
	private String slideImgLocation = "images/"; // converted images from pdf document are saved here
	private String slidePDFLocation = "more_complex_pp.pdf";

	//mouse coordinate stuff
	private Point currentPointerPosition;
	private int pointXCoord;
	private int pointYCoord;

	//drawing the start page handler stuff 
	private ClickPanelDrawStartPage startPageDrawer;
	private boolean startPageDrawerSet = false;	

	//drawing the quiz page
	private ClickPanelDrawQuizPage quizPageDrawer;
	private boolean quizPageDrawerSet = false;

	//pdf handler
	private ConvertPDFPagesToImages pdfHandler;
	private boolean pdfHandlerSet;

	//text box details
	private boolean textBoxEntered; 

	//--page indicator--
	private String currentPage;

	//--setting the window dimensions--
	private int windowWidth;
	private int windowHeight;
	private boolean setWindowDimensions;

	/**
	 * initialisation for the creation of the click panel object
	 */
	public ClickPanel(int width, int height, WindowHandle motherTable) {

		thisWindow = motherTable;

		// Add mouse Listener
		addMouseListener(this);

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
		//text box details
	
		textBoxEntered = false; 

		pdfHandlerSet = false;

		currentPage = "1";

		//set stuff
		setWindowDimensions = false;
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
		if (quizPageDrawerSet==true){
			quizPageDrawer.setNeedToConfirmCorrectness(state);
		}
	}

	/**
	 * @return the correctness variable indication
	 */
	public boolean getCorrectness()
	{
		if (quizPageDrawerSet == true){
			return quizPageDrawer.getCorrectness();
		}
		return false;
	}



//START OF TEXT INDICATOR METHODS

	/**
	 * most resent slide is the last answer slide of the most resent question 
	 * that was displayed by the user clicking the next question button
	 * @param status = the new indicator of whether the user is currently on the most recent slide or not
	 */
	public void setMostResentSlide(boolean status)
	{
		if (quizPageDrawerSet == true){
			quizPageDrawer.setMostRecentSlide(status);
		}
	}

	/**
	 * no more slides is displayed when 
	 * the user has visited all of the slides in the powerpoint
	 * and there are no slides that have not been checked
	 * @param status = the new indicator of whether to display the no more slides text
	 */
	public void setNoMoreSlideText(boolean status)
	{
		if (quizPageDrawerSet == true){
			quizPageDrawer.setDisplayNoMoreSlidesText(status);
		}
	}

	/**
	 * this text is displayed when the user is on the first slide that was displayed after initially clicking the 
	 * next question button, in order to initiate the quiz
	 * @param status = the new indicator relating to whether the user is on the first slide or not
	 */
	public void setFirstSlideStatus(boolean status)
	{
		if (quizPageDrawerSet == true)
		{
			quizPageDrawer.setFirstSlide(status);
		}
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


				//--DRAWNIG THE BACKGROUND--
				g.setColor(new Color(172, 200, 255));
				g.fillRect(0, 0, windowWidth, windowHeight);

				//--DRAWING THE REST OF THE PAGE--
				switch (currentPage){
					case "2" -> {
						if (quizPageDrawerSet == false){
							quizPageDrawer = new ClickPanelDrawQuizPage(windowWidth, windowHeight, this);
							quizPageDrawerSet = true;
						}
						quizPageDrawer.drawQuiz(g, slidePDFLocation, pointXCoord, pointYCoord);
					}
					case "1"-> {
						if (startPageDrawerSet == false){
							startPageDrawer = new ClickPanelDrawStartPage(windowWidth, windowHeight, g, slidePDFLocation);
							startPageDrawerSet = true;
						}
						startPageDrawer.drawPage(g, slidePDFLocation, pointXCoord, pointYCoord);
					}
				}

			}
            
            //draws onto the screen
			drawingSurface.drawImage(i, this.getInsets().left, this.getInsets().top, this);

		}

	}


//CLICK DETECTION & REACTION
  
    //Required methods for MouseListener, though the only one you care about is click
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

	/**
	 * Called whenever the mouse clicks.
	 * @param e = the fetched input from the action that the mouse performed 
	 */
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        double pointXCoord = p.getX();
		double pointYCoord = p.getY();
		switch (currentPage){
			case "1" -> checkStartPageClickCollisions(pointXCoord, pointYCoord);
			case "2" -> checkQuizPageClickCollisions(pointXCoord, pointYCoord);
		}
	}

	/**
	 * checks all buttons / clickable objects on the start page, to see if they have been 
	 * clicked by the passed click point coorindates
	 * @param pointXCoord = the x coordinate of the pixel on screen that was clicked
	 * @param pointYCoord = the y coordinate of the pixel on screen that was clicked
	 */
	public void checkStartPageClickCollisions(double pointXCoord, double pointYCoord)
	{
		startPageLaunchQuizButtonClickCheck(pointXCoord, pointYCoord);
		startPageChangeFileButtonClickCheck(pointXCoord, pointYCoord);
		startPageResetCompletedQuestionsButtonClickCheck(pointXCoord, pointYCoord);
		startPageSlideMode1ButtonClickCheck(pointXCoord, pointYCoord);
		startPageSlideMode2ButtonClickCheck(pointXCoord, pointYCoord);
		startPageSlideMode3ButtonClickCheck(pointXCoord, pointYCoord);
		startPageSlideOrder1ButtonClickCheck(pointXCoord, pointYCoord);
		startPageSlideOrder2ButtonClickCheck(pointXCoord, pointYCoord);


		if (startPageDrawerSet == true){
			startPageDrawer.clickCheckAllButtons((int) pointXCoord, (int) pointYCoord);
		}
	}

	/**
	 * checks all buttons / clickable objects on the quiz page, to see if they have been 
	 * clicked by the passed click point coorindates
	 * @param pointXCoord = the x coordinate of the pixel on screen that was clicked
	 * @param pointYCoord = the y coordinate of the pixel on screen that was clicked
	 */
	public void checkQuizPageClickCollisions(double pointXCoord, double pointYCoord)
	{
		nextQuestionButtonClickCheck(pointXCoord, pointYCoord);
		forwardButtonClickCheck(pointXCoord, pointYCoord);
		backwardButtonClickCheck(pointXCoord, pointYCoord);
		tickButtonClickCheck(pointXCoord, pointYCoord);
		xButtonClickCheck(pointXCoord, pointYCoord);
		backToMenuButtonClickCheck(pointXCoord, pointYCoord);
		textBoxClickCheck(pointXCoord, pointYCoord);
	}

	/**
	 * determines whether the  coordinates of a point (i.e. a point clicked on screen)
	 * intercept the area of a rectangle (acting as a button),
	 * whose dimensions are determined via passed parameters
	 * (checks if the button has been clicked)
	 * @param clickedXCoord = the x coordinate of the pixel on screen that was clicked
	 * @param clickedYCoord = the y coordinate of the pixel on screen that was clicked
	 * @param topLeftX =  the x coordinate of the top left corner of the rectangle
	 * @param topLeftY = the y coordinate of the top left corner of the rectangle
	 * @param buttonWidth = the width of the rectangle (in pixels)
	 * @param buttonHeight = the height of the rectangle (in pixels)
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


//---QUIZ PAGE CLICK CHECKS---

	/**
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 * checks if the mouse click was inside of the next question button
	 * if so, sets the button to clicked and gets the question
	 */
	public void nextQuestionButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (quizPageDrawerSet == true){
			int buttonX = quizPageDrawer.getNextQuestionButtonButtonX();
			int buttonY = quizPageDrawer.getNextQuestionButtonButtonY();
			int buttonW = quizPageDrawer.getNextQuestionButtonButtonW();
			int buttonH = quizPageDrawer.getNextQuestionButtonButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				//
				quizPageDrawer.setNextQuestionButtonClicked(true);
				getNextQuestion();
			}			
		}
	}


	/**
	 * checks whether the forward button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
	public void forwardButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (quizPageDrawerSet == true){
			int buttonX = quizPageDrawer.getForwardPageButtonX();
			int buttonY = quizPageDrawer.getForwardPageButtonY();
			int buttonW = quizPageDrawer.getForwardPageButtonW();
			int buttonH = quizPageDrawer.getForwardPageButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				quizPageDrawer.setForwardPageButtonClicked(true);
				getForwardSlide();
			}	
		}
	}

	/**
	 * checks whether the backward button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 * 
	 */
	public void backwardButtonClickCheck(double clickedXCoord, double clickedYCoord) 
	{
		if (quizPageDrawerSet == true){
			int buttonX = quizPageDrawer.getBackwardPageButtonX();
			int buttonY = quizPageDrawer.getBackwardPageButtonY();
			int buttonW = quizPageDrawer.getBackwardPageButtonW();
			int buttonH = quizPageDrawer.getBackwardPageButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord,buttonX, buttonY, buttonW, buttonH)){
				quizPageDrawer.setBackwardPageButtonClicked(true);
				getBackwardSlide();
			}
		}
	}

	/**
	 * checks whether the tick button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 * 
	 */
	public void tickButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (quizPageDrawerSet == true){
			int buttonX = quizPageDrawer.getTickButtonX();
			int buttonY = quizPageDrawer.getTickButtonY();
			int buttonW = quizPageDrawer.getTickButtonW();
			int buttonH = quizPageDrawer.getTickButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				quizPageDrawer.setTickButtonClicked(true);
				greenTickClicked();
			}
		}
	}

	/**
	 * checks whether the 'X button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 * 
	 */
	public void xButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (quizPageDrawerSet == true){
			int buttonX = quizPageDrawer.getXButtonX();
			int buttonY = quizPageDrawer.getXButtonY();
			int buttonW = quizPageDrawer.getXButtonW();
			int buttonH = quizPageDrawer.getXButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				quizPageDrawer.setXButtonClicked(true);
				redXClicked();
			}
		}
	}

	/**
	 * checks whether the back to menu button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
	public void backToMenuButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (quizPageDrawerSet == true){
			int buttonX = quizPageDrawer.getBackToMenuButtonX();
			int buttonY = quizPageDrawer.getBackToMenuButtonY();
			int buttonW = quizPageDrawer.getBackToMenuButtonW();
			int buttonH = quizPageDrawer.getBackToMenuButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				currentPage = "1";
				quizPageDrawer.setBackToMenuButtonClicked(true);
				//close the file since going back
				saveQuestionsCompleted();
				closePPFile();
				initializeDefaultValueVariables();

			}
		}
	}

	/**
	 * checks whether the textbox has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
	public void textBoxClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (quizPageDrawerSet == true){
			int textBoxX = quizPageDrawer.getTextBoxX();
			int textBoxY = quizPageDrawer.getTextBoxY();
			int textBoxW = quizPageDrawer.getTextBoxW();
			int textBoxH = quizPageDrawer.getTextBoxH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, textBoxX, textBoxY, textBoxW, textBoxH)){
					//if the user is already in the text box and wants to change their cursor position
					if (textBoxEntered == true){
						//update the text box
						quizPageDrawer.getTypingTextBox().updateLeftAndRightText((int) clickedXCoord, (int) clickedYCoord);
					}
					quizPageDrawer.getTypingTextBox().setEntered(true);
					textBoxEntered = true;
			}
			else{
				//resets the text box cursor
				quizPageDrawer.getTypingTextBox().setEntered(false);
				textBoxEntered = false;
			}
		}
		else{
			//resets the text box cursor 
			quizPageDrawer.getTypingTextBox().setEntered(false); //for the text box
			textBoxEntered = false; //for this class
		}
	}

//---START PAGE CLICK CHECKS---

	/**
	 * checks whether the launch quiz button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
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

	/**
	 * checks whether the change file button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
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

	/**
	 * checks whether the reset completed questions button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
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
	
	/**
	 * checks whether the slide mode 1 button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
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

	/**
	 * checks whether the slide mode 2 button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
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

	/**
	 * checks whether the slide mode 3 button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
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

	/**
	 * checks whether the slide order 1 button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
	public void startPageSlideOrder1ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getSlideOrder1ButtonX();
			int buttonY = startPageDrawer.getSlideOrder1ButtonY();
			int buttonW = startPageDrawer.getSlideOrder1ButtonW();
			int buttonH = startPageDrawer.getSlideOrder1ButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				if (startPageDrawer.getSlideOrder1ButtonSelected() == false){
					startPageDrawer.setSlideOrder2ButtonSelected(false);
					startPageDrawer.setSlideOrder1ButtonSelected(true);
				}			
				startPageDrawer.setSlideOrder1ButtonClicked(true);
			}
		}
	}

	/**
	 * checks whether the slide order 2 button has been clicked, and initiates the handling of that event, correspondingly
	 * @param clickedXCoord the y coordinate where the mouse was clicked
	 * @param clickedYCoord the x coordinate where the mouse was clicked
	 */
	public void startPageSlideOrder2ButtonClickCheck(double clickedXCoord, double clickedYCoord)
	{
		if (startPageDrawerSet == true){
			int buttonX = startPageDrawer.getSlideOrder2ButtonX();
			int buttonY = startPageDrawer.getSlideOrder2ButtonY();
			int buttonW = startPageDrawer.getSlideOrder2ButtonW();
			int buttonH = startPageDrawer.getSlideOrder2ButtonH();
			if (rectButtonClickCheck(clickedXCoord, clickedYCoord, buttonX, buttonY, buttonW, buttonH)){
				if (startPageDrawer.getSlideOrder2ButtonSelected() == false){
					startPageDrawer.setSlideOrder1ButtonSelected(false);
					startPageDrawer.setSlideOrder2ButtonSelected(true);
				}			
				startPageDrawer.setSlideOrder2ButtonClicked(true);
			}
		}
	}

//---CLOSING THE POWERPOINT AND SAVING---

	/**
	 * closes the pdf file that is being used as a source for the 
	 * slide images, if said file exists / in use.
	 */
	public void closePPFile()
	{
		if(pdfHandlerSet == true){
			//
			pdfHandler.closeDocument();
			pdfHandlerSet = false;
		}
	}

	/**
	 * saves the completed questions data to a non-volatile storage location
	 */
	public void saveQuestionsCompleted()
	{
		if(pdfHandlerSet == true){
			pdfHandler.exportCompletedSlides(true);
		}
	}
	
//--- ---

	/**
	 * changes the slide reference image to the appropriate question if a valid one if found
	 * if no valid new question slide is found, no changes are made.
	 */
	public void getNextQuestion()
	{
		if (pdfHandlerSet == false){
			pdfHandler = new ConvertPDFPagesToImages(this);
			pdfHandlerSet = true;
		}

		if (quizPageDrawerSet == true){
			int newPageNumber = pdfHandler.changeSlide("newQuestion");
			quizPageDrawer.setImagePageNumber(newPageNumber);
			setMostResentSlide(false);
		}
	}

	/**
	 * changes the slide reference image to the appropriate slide
	 * moving forward through the seen slides
	 */
	public void getForwardSlide()
	{
		if (pdfHandlerSet == true){
			if (quizPageDrawerSet == true){
				int newPageNumber = pdfHandler.changeSlide("forward");
				quizPageDrawer.setImagePageNumber(newPageNumber);
			}
		}
	}

	/**
	 * changes the slide reference image to the appropriate slide
	 * moving backward through the seen slides
	 */
	public void getBackwardSlide()
	{
		if (pdfHandlerSet == true){
			if (quizPageDrawerSet == true){
				int newPageNumber = pdfHandler.changeSlide("back");
				quizPageDrawer.setImagePageNumber(newPageNumber);
				setNoMoreSlideText(false);
				setMostResentSlide(false);
			}
		}
	}

	/**
	 * activates the procedure for the green tick button being clicked
	 * this marks the current question as 'completed correctly'
	 */
	public void greenTickClicked()
	{
		if (pdfHandlerSet == true){
			//write green tick stuff here 
			pdfHandler.handleGreenTickClicked();	
		}
	}
	
	/**
	 * activates the procedure for the red X button being clicked
	 * this marks the current question as 'completed, but answer was wrong / did not gain full marks'
	 */
	public void redXClicked() 
	{
		if (pdfHandlerSet == true){
			pdfHandler.handleRedXClicked();
		}
	}

	/**
	 * increments the questions completed count by 1
	 * i.e. 'completed question' = a question, where the user has clicked the 'red X' or 'green tick' buttons 
	 * 							 when prompted to, in order to indicate that they have finished looking at the question
	 * 		'questions completed' = an integer total, of the number of 'completed questions' from within the current session only
	 */
	public void incrementQuestionsCompleted()
	{
		if (quizPageDrawerSet == true){
			quizPageDrawer.incrementQuestionsCompleted();
		}
	}

	/**
	 * sets the 'current questions' to the parameter passed value.
	 * i.e. 'current question' = 'the question number' relating to the current slide.
	 * 		'question number' = an integer value, linked to the order of which a 'question slide group' has been displayed 
	 * 							in the current quiz session. 
	 * 							e.g. the seen after starting the quiz would be question 1, and all of the following answer slides
	 * 									would also be question 1. 
	 * 								 when the user goes onto the next question, the 'question number' would be '2'. 
	 * 							
	 * 		'question slide group' = question slide and its corresponding answer slides
	 */
	public void setCurrentQuestion(int currentQuestionNumber)
	{
		if (quizPageDrawerSet == true){
			quizPageDrawer.setCurrentQuestion(currentQuestionNumber);
		}

		//if not on page 1
		if (currentQuestionNumber > 1){
			setFirstSlideStatus(false);
		}
	}



	//--key listener required methods--
	
	@Override
	public void keyTyped(KeyEvent e) {
		String typedKey = String.valueOf(e.getKeyChar());
		int typedKeysExtendedKeyCode = e.getExtendedKeyCode();
		
		switch (typedKeysExtendedKeyCode){
			case 8 -> typedKey = "backspace";
			case 10 -> typedKey = "enter";
			case 16777383 -> typedKey = "exit";
		}
		HandleTextEntered(typedKey, e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

//---HANDLING TEXT INPUTS---

	/**
	 * responds to the appropriate key that was pressed, by activating a shortcut, or entering text into a text box.
	 * @param typedChar = the character, which corresponds to the character relating to the key that was pressed
	 * @param keyEvent = the corresponding event relating to the key that was interacted with
	 */
	public void HandleTextEntered(String typedChar, KeyEvent keyEvent)
	{
		switch (currentPage){
			case "1"->{
				switch (typedChar){
					case "enter" -> {
						//launch quiz button click
						currentPage = "2";
						startPageDrawer.setLaunchQuizButtonClicked(true);
					}
					case "c" ->  {
						//change file button click
						changeLoadedFile();
						startPageDrawer.setChangeFileButtonClicked(true);
					}
					case "r" -> {
						//reset button click
						setAllSlidesToUnseen(slidePDFLocation);
						startPageDrawer.setResetCompletedQuestionsIndicationTextActivated(true);
						startPageDrawer.setResetCompletedQuestionsButtonClicked(true);
					}
					case "1" -> {
						//slide mode 1 button click
						if (startPageDrawer.getSlideMode1ButtonSelected() == false){
							startPageDrawer.setSlideMode1ButtonSelected(true);
						}
						else{
							if ((startPageDrawer.getSlideMode2ButtonSelected() == true) || (startPageDrawer.getSlideMode3ButtonSelected() == true)){
								startPageDrawer.setSlideMode1ButtonSelected(false);
							}
						}
						startPageDrawer.setSlideMode1ButtonClicked(true);
					}
					case "2" -> {
						//slide mode 2 button click
						if (startPageDrawer.getSlideMode2ButtonSelected() == false){
							startPageDrawer.setSlideMode2ButtonSelected(true);				}
						else{
							if ((startPageDrawer.getSlideMode1ButtonSelected() == true) || (startPageDrawer.getSlideMode3ButtonSelected() == true)){
								startPageDrawer.setSlideMode2ButtonSelected(false);
							}
						}
						startPageDrawer.setSlideMode2ButtonClicked(true);
					}
					case "3" -> {
						//slide mode 3 button click
						if (startPageDrawer.getSlideMode3ButtonSelected() == false){
							startPageDrawer.setSlideMode3ButtonSelected(true);				}
						else{
							if ((startPageDrawer.getSlideMode1ButtonSelected() == true) || (startPageDrawer.getSlideMode2ButtonSelected() == true)){
								startPageDrawer.setSlideMode3ButtonSelected(false);
							}
						}
						startPageDrawer.setSlideMode3ButtonClicked(true);
					}
					case "[" -> {
						//slide option 1 button click
						if (startPageDrawer.getSlideOrder1ButtonSelected() == false){
							startPageDrawer.setSlideOrder2ButtonSelected(false);
							startPageDrawer.setSlideOrder1ButtonSelected(true);
						}			
						startPageDrawer.setSlideOrder1ButtonClicked(true);
					}
					case "]" -> {
						//slide option 2 button click
						if (startPageDrawer.getSlideOrder2ButtonSelected() == false){
							startPageDrawer.setSlideOrder1ButtonSelected(false);
							startPageDrawer.setSlideOrder2ButtonSelected(true);
						}			
						startPageDrawer.setSlideOrder2ButtonClicked(true);
					}
				}
			}
			case "2" -> {
				if (textBoxEntered == true){
					//user typed into the text box
					quizPageDrawer.getTypingTextBox().typeLetter(typedChar, keyEvent.getExtendedKeyCode());
					if (quizPageDrawer.getTypingTextBox().isEntered()){
						textBoxEntered = true;
					}
					else{
						textBoxEntered = false;
					}
				}
				else{
					//not in text box
					switch (typedChar){
						case "enter" -> {
							//next question button clicked
							quizPageDrawer.setNextQuestionButtonClicked(true);
							getNextQuestion();
						}
						case "t" -> {
							//text box clicked
							textBoxEntered = true;
							quizPageDrawer.getTypingTextBox().setEntered(true);
						}
						case "/" -> {
							//text box clicked
							//textBoxTextLeftOfCursor = ""; reset the text in the text box
						}
						case "r" -> {
							//green tick button clicked
							quizPageDrawer.setTickButtonClicked(true);
							greenTickClicked();
						}
						case "w" -> {
							//red x button clicked
							quizPageDrawer.setXButtonClicked(true);
							redXClicked();
						}
						case "[" -> {
							//move left in seen slides button clicked
							quizPageDrawer.setBackwardPageButtonClicked(true);
							getBackwardSlide();
						}
						case "]" -> {
							//move right in seen slides button clicked
							quizPageDrawer.setForwardPageButtonClicked(true);
							getForwardSlide();
						}
						case "b" -> {
							//back to menu button clicked
							currentPage = "1";
							quizPageDrawer.setBackToMenuButtonClicked(true);
							//close the file since going back
							saveQuestionsCompleted();
							closePPFile();
							initializeDefaultValueVariables();
						}
					}					
				}
			}
		}
	}

	/**
	 * opens up an interface which allows
	 * the user to select the pdf file which they wish 
	 * to use as the quiz basis. 
	 * It then promptly updates the variable 
	 * pointing to the pdf source file
	 */
	public void changeLoadedFile()
	{
		String foundPath = FileSelector.selectPpfFile();
        if (foundPath != null){
			slidePDFLocation = foundPath;
        }   
	}


//CLEARING THE COMPLETED QUESTIONS TEXT FILE FOR THE SELECTED POWERPOINT

	/**
	 * clears/resets the text file, so that it becomes empty
	 * @param powerpiontPdfFileLocation = the path to the text file that is to be cleared
	 */
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
			System.out.println("could not clear the given text file");
        }
	}

	/**
	 * Updates the meta data file, associated with the parameter passed pdf, to have all slides 'unseen'
	 * @param powerpointPdfFileLocation = the name of the pdf, whose 'slides seen' metadata is to be reset
	 * 									  (so that all slides are 'unseen')
	 */
	public void setAllSlidesToUnseen(String powerpointPdfFileLocation)
	{
		String textFilename = getFileNameFromLocation(powerpointPdfFileLocation);
		String filenameAsTextFile = textFilename + "_completed_questions_array.txt";
		
		Deque<Integer> slideNumberQueue = new LinkedList<>();
		Deque<Integer> incorrectAttemptsQueue = new LinkedList<>();

		/*
		get each line
		parse values
		re-write with last two columns false
		*/
		File textfileToRead = new File(filenameAsTextFile);

        if(textfileToRead.exists() && !textfileToRead.isDirectory()) { 
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
							currentSlideNumber = Integer.parseInt(splitLine[0]);
							currentQuestionTimesIncorrect = Integer.parseInt(splitLine[1]);
						}
						else{
							//if there are not 4 values
							currentSlideNumber = 0;
							currentQuestionTimesIncorrect = 0;
						}
	
						slideNumberQueue.push(currentSlideNumber);
						incorrectAttemptsQueue.push(currentQuestionTimesIncorrect);
						entriesFound++;
						
					}
					br.close();
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("could not set all of the slides to unseen");
				}
	
			} 
			finally{
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
	/**
	 * @param location = the path to the file
	 * @return the name of the file at the end of the path (without the file extention)
	 */
    public static String getFileNameFromLocation(String location) {
        int lastSlash = location.lastIndexOf("/") + 1;
        String fullFilename = location.substring(lastSlash);
        int lastDotIndex = fullFilename.lastIndexOf('.');
        String fileName = fullFilename.substring(0, lastDotIndex);
        return fileName;
	}

//--- ---

	/**
	 * sets the slide image update variable for the quiz page to the passed value
	 * @param needsUpdating = the new value of the slideImageUpdated 
	 */
	public void setJustChangedSlideForQuizPage(boolean needsUpdating)
	{
		if (quizPageDrawerSet == true){
			quizPageDrawer.setJustChangedSlide(needsUpdating);
		}
	}
	
	/**
	 * @return true if the pdfHandler object has been created; false otherwise
	 */
	public boolean isPDFHandlerSet()
	{
		return pdfHandlerSet;
	}

	/**
	 * @param questionNumber = the question number, i.e. the 'n'th question returned by the pdfHander 
	 * @return true, if the passed question number has been completed (i.e. the user has clicked the tick or cross
	 * 				 to indicate a true/incorrect answer respectively);
	 * 		   false, if the either the pdfHandler / quizPageDrawer have not been initialised; if the question number is invalid; if 
	 * 				  the question has not been completed.
	 */
	public boolean isTheQuestionComplete(int questionNumber)
	{
		if (pdfHandlerSet == true){
			if (quizPageDrawerSet == true){
				return pdfHandler.isComplete(quizPageDrawer.getCurrentQuestion()); 
			}
		}
		return false;
	}
}
  