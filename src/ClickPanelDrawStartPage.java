import java.awt.*;//used for layout managers
import java.util.LinkedList;

import buttons.ClickableColouredButton;
import buttons.SingleLineText;


/*
TO DO:
-move all of the instance variables from the methods to the class body

-make the outline colours static, and then add getters for them
-add a ClickPanel to buttons
-update the button clicked lambda functions to replace the ones in the click panel
-add aswell, the call to the static getters for the outline colour
*/

public class ClickPanelDrawStartPage
{
    //graphics handler
    private Graphics2D drawingLocation;

    //window dimensions
    private int windowWidth;
    private int windowHeight;

    //location of the slide that is to be loaded
    private String slidePDFLocation;

    //mouse coordinates
    private int currentMouseCursorCoordX;
    private int currentMouseCursorCoordY;
    
    //contains all of the buttons on this page
    private LinkedList<ClickableColouredButton> allButtons = new LinkedList<ClickableColouredButton>();

    //---GENERAL PAGE DRAWING---

    //--drawing the start page title--
	private int titleWidth;
	private int titleHeight;
	private int titleX;
	private int titleY;
	private String titleTextUpper;
	private int titleUpperTextWidth;
	private int titleUpperTextHeight;
	private int titleUpperTextX;
	private int titleUpperTextY;
	private String titleTextLower;
	private int titleLowerTextWidth;
	private int titleLowerTextHeight;
	private int titleLowerTextX;
	private int titleLowerTextY;

	//--drawing the start page main box--
	private int titleBottom;
	private int mainBoxOuterWidth;
	private int mainBoxOuterHeight;
	private int mainBoxOuterX;
	private int mainBoxOuterY;
	private int mainBoxInnerWidth;
	private int mainBoxInnerHeight;
	private int mainBoxInnerX;
	private int mainBoxInnerY;


	//--drawing the loaded powerpoint text--
	private String loadedPowerpointText;
	private int loadedPowerpointTextWidth;
	private int loadedPowerpointTextHeight;
	private int loadedPowerpointTextX;
	private int loadedPowerpointTextY;


	//--initiating the side spacing variables--
	private int sideSpacing;

	//--drawing the loaded filename--
	private int loadedFilenameBoxWidth;
	private int loadedFilenameBoxHeight;
	private int loadedFilenameBoxX;
	private int loadedFilenameBoxY;
	private String loadedFilenameText;
	private int loadedFilenameTextWidth;
	private int loadedFilenameTextHeight;
	private int loadedFilenameTextX;
	private int loadedFilenameTextY;


	//--drawnig the launch quiz button--
	private int launchQuizButtonWidth;
	private int launchQuizButtonHeight;
	private int launchQuizButtonX;
	private int launchQuizButtonY;
	private String launchQuizButtonText;
	private int launchQuizButtonTextWidth;
	private int launchQuizButtonTextHeight;
	private int launchQuizButtonTextX;
	private int launchQuizButtonTextY;
	private boolean launchQuizButtonClicked;


	//--drawing the change file button stuff--
	private int changeFileButtonWidth;
	private int changeFileButtonHeight;
	private int changeFileButtonX;
	private int changeFileButtonY;
	private String changeFileButtonText;
	private int changeFileButtonTextWidth;
	private int changeFileButtonTextHeight;
	private int changeFileButtonTextX;
	private int changeFileButtonTextY;
	private boolean changeFileButtonClicked;

	//--drawing the reset completed questions file stuff--
	private int resetCompletedQuestionsButtonWidth;
	private int resetCompletedQuestionsButtonHeight;
	private int resetCompletedQuestionsButtonX;
	private int resetCompletedQuestionsButtonY;
	private String resetCompletedQuestionsButtonText;
	private int resetCompletedQuestionsButtonTextWidth;
	private int resetCompletedQuestionsButtonTextHeight;
	private int resetCompletedQuestionsButtonTextX;
	private int resetCompletedQuestionsButtonTextY;
	private boolean resetCompletedQuestionsButtonClicked;	

	//--drawing the slide mode text--
	private String slideModeText;
	private int slideModeTextWidth ;
	private int slideModeTextHeight;
	private int slideModeTextX ;
	private int slideModeTextY;
	
	//--drawing the slide mode 1 button--
	private int slideMode1ButtonWidth;
	private int slideMode1ButtonHeight;
	private int slideMode1ButtonX;
	private int slideMode1ButtonY;
	private boolean slideMode1ButtonClicked;
	private String slideMode1ButtonText;
	private int slideMode1ButtonTextWidth;
	private int slideMode1ButtonTextHeight;
	private int slideMode1ButtonTextX;
	private int slideMode1ButtonTextY;
	private boolean slideMode1ButtonSelected = true;

	//--drawing the slide mode 2 button--
	private int slideMode2ButtonWidth;
	private int slideMode2ButtonHeight;
	private int slideMode2ButtonX;
	private int slideMode2ButtonY;
	private boolean slideMode2ButtonClicked;
	private String slideMode2ButtonText;
	private int slideMode2ButtonTextWidth;
	private int slideMode2ButtonTextHeight;
	private int slideMode2ButtonTextX;
	private int slideMode2ButtonTextY;
	private boolean slideMode2ButtonSelected = false;
	
	//--drawing the slide mode 3 button--
	private int slideMode3ButtonWidth;
	private int slideMode3ButtonHeight;
	private int slideMode3ButtonX;
	private int slideMode3ButtonY;
	private boolean slideMode3ButtonClicked;
	private String slideMode3ButtonText;
	private int slideMode3ButtonTextWidth;
	private int slideMode3ButtonTextHeight;
	private int slideMode3ButtonTextX;
	private int slideMode3ButtonTextY;
	private boolean slideMode3ButtonSelected = false;
	
	//--drawing the slide order text--
	private String slideOrderText;
	private int slideOrderTextWidth;
	private int slideOrderTextHeight;
	private int slideOrderTextX ;
	private int slideOrderTextY;
		
	//--drawing the slide order 1 button--
	private int slideOrder1ButtonWidth;
	private int slideOrder1ButtonHeight;
	private int slideOrder1ButtonX;
	private int slideOrder1ButtonY;
	private boolean slideOrder1ButtonClicked;
	private String slideOrder1ButtonText;
	private int slideOrder1ButtonTextWidth;
	private int slideOrder1ButtonTextHeight;
	private int slideOrder1ButtonTextX;
	private int slideOrder1ButtonTextY;
	private boolean slideOrder1ButtonSelected = true;


	//--drawing the slide order 2 button--
	private int slideOrder2ButtonWidth;
	private int slideOrder2ButtonHeight;
	private int slideOrder2ButtonX;
	private int slideOrder2ButtonY;
	private boolean slideOrder2ButtonClicked;
	private String slideOrder2ButtonText;
	private int slideOrder2ButtonTextWidth;
	private int slideOrder2ButtonTextHeight;
	private int slideOrder2ButtonTextX;
	private int slideOrder2ButtonTextY;
	private boolean slideOrder2ButtonSelected = false;

	//--drawing the reset completed questions indication text--
	private String resetQuestionsIndicationText;
	private int resetQuestionsIndicationTextWidth;
	private int resetQuestionsIndicationTextHeight;
	private int resetQuestionsIndicationTextX;
	private int resetQuestionsIndicationTextY;
	private boolean resetQuestionsIndicationTextActivated = false;
	private int resetQuestionsIndicationTextActivatedTick;
	
    public ClickPanelDrawStartPage(int windowW, int windowH, Graphics2D graphicsHandle, String slideFilePath)
    {
        drawingLocation = graphicsHandle;
        slidePDFLocation = slideFilePath;
        windowWidth = windowW;
        windowHeight = windowH;
        initialiseAllElements();
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

    //DRAWING START PAGE STUFF START 

    public void drawPage(Graphics2D graphicsHandle, String slideFilePath, int mouseXCoord, int mouseYCoord)
    {
        //updating variables
        drawingLocation = graphicsHandle;
        slidePDFLocation = slideFilePath;
        currentMouseCursorCoordX = mouseXCoord;
        currentMouseCursorCoordY = mouseYCoord;

        //drawing
        drawTitle();

        drawMainBox();

        drawLoadedPowerpointText();

        drawLoadedFilename();

        drawSlideModeText();

        drawSlideOrderText();

        drawResetQuestionsTextIndication();

        drawAllButtons();
    }

    public void initialiseTitle()
    {
        Font titleTextFont = new Font ("Monospaced", Font.PLAIN, 50);
        titleTextUpper = "Powerpoint";
        titleTextLower = "Quiz Generator";

        //--box that the string goes in--
        titleWidth = (int) (windowWidth*0.33);
        titleHeight = (int) (windowHeight*0.15);
        titleX = (int) (windowWidth*0.5 - titleWidth*0.5);
        titleY = (int) (windowHeight * 0.01);

        //--text--
        
        drawingLocation.setFont(titleTextFont); 
        //top text
        titleUpperTextWidth = drawingLocation.getFontMetrics().stringWidth(titleTextUpper);
        titleUpperTextHeight = drawingLocation.getFontMetrics().getAscent();
        titleUpperTextX = titleX + (int) ((titleX - titleUpperTextWidth)/2);
        titleUpperTextY = titleY + (int) (titleUpperTextHeight);

        drawingLocation.setFont(titleTextFont); 
        //bottom text
        titleLowerTextWidth = drawingLocation.getFontMetrics().stringWidth(titleTextLower);
        titleLowerTextHeight = drawingLocation.getFontMetrics().getAscent();
        titleLowerTextX = titleX + (int) ((titleX - titleLowerTextWidth)/2);
        titleLowerTextY = titleUpperTextY + titleUpperTextHeight + (int) (windowHeight*0.015);
    }

    public void drawTitle()
    {
        Color titleBoxColour = new Color (255, 255, 255);
        Color titleBoxBorderColour = new Color(1,1,1);
        Font titleTextFont = new Font ("Monospaced", Font.PLAIN, 50);
        Color titleTextColour = new Color (0,0,0);

        drawingLocation.setColor(titleBoxColour);
        drawingLocation.fillRect(titleX, titleY, titleWidth, titleHeight);

        //black border around the box
        drawingLocation.setColor(titleBoxBorderColour);
        drawingLocation.drawRect(titleX, titleY, titleWidth, titleHeight);

        //--text--
        drawingLocation.setFont(titleTextFont); 
        drawingLocation.setColor(titleTextColour);
        
        drawingLocation.drawString(titleTextUpper, titleUpperTextX, titleUpperTextY);
        drawingLocation.drawString(titleTextLower, titleLowerTextX, titleLowerTextY);
    }

    public void initialiseMainBox()
    {
        //other stuff/ dependencies
        titleBottom = (titleY + titleHeight);
        //--outer box--
        mainBoxOuterWidth = (int) (windowWidth*0.8);
        mainBoxOuterHeight = (int) (windowHeight-titleBottom - 2*(windowHeight*0.05));
        mainBoxOuterX =(int)  ((windowWidth - mainBoxOuterWidth)/2);
        mainBoxOuterY = titleBottom + (int) ((windowHeight - titleBottom - mainBoxOuterHeight)/2);

        //--inner box-m
        mainBoxInnerWidth = mainBoxOuterWidth - (int) (2*(windowWidth*0.01));
        mainBoxInnerHeight = mainBoxOuterHeight - (int) (2*(windowHeight*0.01));
        mainBoxInnerX = mainBoxOuterX + (int) ((mainBoxOuterWidth - mainBoxInnerWidth)/2);
        mainBoxInnerY = mainBoxOuterY + (int) ((mainBoxOuterHeight - mainBoxInnerHeight)/2);
    }

    public void drawMainBox()
    {
        Color mainBoxOuterColour = new Color(22, 122,24);
        Color mainBoxInnerColour = new Color(22, 207,159);
        Color mainBoxInnerOutlineColour = new Color (0,0,0);

        drawingLocation.setColor(mainBoxOuterColour);
        drawingLocation.drawRect(mainBoxOuterX, mainBoxOuterY, mainBoxOuterWidth, mainBoxOuterHeight);
    
        drawingLocation.setColor(mainBoxInnerColour);
        drawingLocation.fillRect(mainBoxInnerX, mainBoxInnerY, mainBoxInnerWidth, mainBoxInnerHeight);

        //outline for the inner box
        drawingLocation.setColor(mainBoxInnerOutlineColour);
        drawingLocation.drawRect(mainBoxInnerX, mainBoxInnerY, mainBoxInnerWidth, mainBoxInnerHeight);
    }

    public void initialiseLoadedPowerpointText()
    {
        Font loadedPowerpointTextFont = new Font("Monospaced", Font.PLAIN, 20);
        loadedPowerpointText = "Loaded Powerpoint";

        drawingLocation.setFont(loadedPowerpointTextFont); 
        
        loadedPowerpointTextWidth = drawingLocation.getFontMetrics().stringWidth(loadedPowerpointText);
        loadedPowerpointTextHeight = drawingLocation.getFontMetrics().getAscent();
        loadedPowerpointTextX = mainBoxInnerX + (int) ((mainBoxInnerWidth - loadedPowerpointTextWidth)/2);
        loadedPowerpointTextY = mainBoxInnerY + loadedPowerpointTextHeight + (int) (windowHeight*0.01);
    }

    public void drawLoadedPowerpointText()
    {
        Font loadedPowerpointTextFont = new Font("Monospaced", Font.PLAIN, 20);
        Color loadedPowerpointTextColour = new Color(0,0,0);

        drawingLocation.setFont(loadedPowerpointTextFont); 
        drawingLocation.setColor(loadedPowerpointTextColour);

        drawingLocation.drawString(loadedPowerpointText, loadedPowerpointTextX, loadedPowerpointTextY);
    }

    public void initinitialiseBoxSpacing()
    {   
        sideSpacing = (int) (windowWidth*0.01);        
    }

    public void initialiseLoadedFilename()
    {
        Font loadedFilenameTextFont = new Font("Monospaced", Font.PLAIN, 20);

        //--box that the string goes in--
        loadedFilenameBoxWidth = mainBoxInnerWidth - (int) (2*sideSpacing);
        loadedFilenameBoxHeight = (int) (windowHeight*0.05);
        loadedFilenameBoxX = mainBoxInnerX + sideSpacing;
        loadedFilenameBoxY = loadedPowerpointTextY + (int) (windowHeight*0.02);

        //--text--
        drawingLocation.setFont(loadedFilenameTextFont); 
        
        loadedFilenameText = (ConvertPDFPagesToImages.getFileNameFromLocation(slidePDFLocation));
        loadedFilenameTextWidth = drawingLocation.getFontMetrics().stringWidth(loadedFilenameText);
        loadedFilenameTextHeight = drawingLocation.getFontMetrics().getAscent();
        loadedFilenameTextX = loadedFilenameBoxX + (int) (windowWidth*0.01);
        loadedFilenameTextY = loadedFilenameBoxY +loadedFilenameTextHeight + (int) (windowHeight*0.01);
    }

    public void drawLoadedFilename()
    {
        Color loadedFilenameBoxColour = new Color(250, 250, 250);
        Color loadedFilenameBoxBorderColour = new Color(100,100,100);
        Font loadedFilenameTextFont = new Font("Monospaced", Font.PLAIN, 20);
        Color loadedFilenameTextColour = new Color(0,0,0);

        
        //--draw the box that the text goes in--
        drawingLocation.setColor(loadedFilenameBoxColour);
        drawingLocation.fillRect(loadedFilenameBoxX, loadedFilenameBoxY, loadedFilenameBoxWidth, loadedFilenameBoxHeight);

        //black border around the box
        drawingLocation.setColor(loadedFilenameBoxBorderColour);
        drawingLocation.drawRect(loadedFilenameBoxX, loadedFilenameBoxY, loadedFilenameBoxWidth, loadedFilenameBoxHeight);

        //--text--
        drawingLocation.setFont(loadedFilenameTextFont); 
        drawingLocation.setColor(loadedFilenameTextColour);
        
        drawingLocation.drawString(loadedFilenameText, loadedFilenameTextX, loadedFilenameTextY);
    }

    public void initialiseLaunchQuizButton()
    {
        launchQuizButtonText = "Lauch Quiz";
        int[] launchQuizButtonColour = {92, 222, 8};
        int[] launchQuizButtonHoveredOverColour = {85, 215, 0};
        int[] launchQuizButtonClickedColour = {110, 240, 25};
        int[] launchQuizButtonBorderColour = {0,0,0};
        int laucnQuizButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font defaultButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color defaultButtonFontColour = new Color (0,0,0);

        ClickableColouredButton launchQuizButton;

        //--initialise the button dimesions--
        launchQuizButtonWidth = (int) (mainBoxInnerWidth- (2*sideSpacing));
        launchQuizButtonHeight = (int) (windowHeight*0.15);
        launchQuizButtonX = mainBoxInnerX + sideSpacing;
        launchQuizButtonY = (mainBoxInnerY + mainBoxInnerHeight - launchQuizButtonHeight) - sideSpacing;
        
        //--create the button object--
        launchQuizButton = new ClickableColouredButton(launchQuizButtonX, launchQuizButtonY, launchQuizButtonWidth, launchQuizButtonHeight, launchQuizButtonColour, launchQuizButtonClickedColour, launchQuizButtonHoveredOverColour, laucnQuizButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the launch quiz button stuff");
            }
        };
        launchQuizButton.handleNewWindowClick(0,0);
        launchQuizButton.addBorder(borderMultiplier, launchQuizButtonBorderColour);
        allButtons.add(launchQuizButton);
        
        //--initialise the button text data--
        drawingLocation.setFont(defaultButtonFont); 
        launchQuizButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(launchQuizButtonText);
        launchQuizButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        launchQuizButtonTextX = launchQuizButton.getXCoordinate() + (int) ((launchQuizButton.getWidth()-launchQuizButtonTextWidth)/2);
        launchQuizButtonTextY = launchQuizButton.getYCoordinate() + launchQuizButtonTextHeight + (int) ((launchQuizButton.getHeight()-launchQuizButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        launchQuizButton.addText(new SingleLineText(launchQuizButtonText,  launchQuizButtonTextX,  launchQuizButtonTextY,  defaultButtonFont,  defaultButtonFontColour));
    }

    /*c
     * draws the button onto the drawing location
     * @param buttonToBeDrawn = the button object which contains the necessary information for it to be drawn onto the screen
     */
    private void drawButton(ClickableColouredButton buttonToBeDrawn)
    {
        if (buttonToBeDrawn.isSorroundedByBorder()){
            //--draw the outline of the button--
            drawingLocation.setColor(buttonToBeDrawn.getBorderColour());
            drawingLocation.fillRect(buttonToBeDrawn.getXCoordinate(), buttonToBeDrawn.getYCoordinate(), buttonToBeDrawn.getWidth(), buttonToBeDrawn.getHeight());

            //--draw the button--
            drawingLocation.setColor(buttonToBeDrawn.getDisplayColour(currentMouseCursorCoordX, currentMouseCursorCoordY));
            drawingLocation.fillRect(buttonToBeDrawn.getXCoordinate() + buttonToBeDrawn.getBorderWidth(), buttonToBeDrawn.getYCoordinate() + buttonToBeDrawn.getBorderWidth(), buttonToBeDrawn.getWidth() - 2*(buttonToBeDrawn.getBorderWidth()), buttonToBeDrawn.getHeight() - 2*(buttonToBeDrawn.getBorderWidth()));
        }
        else{
            //--draw the button--
            drawingLocation.setColor(buttonToBeDrawn.getDisplayColour(currentMouseCursorCoordX, currentMouseCursorCoordY));
            drawingLocation.fillRect(buttonToBeDrawn.getXCoordinate(), buttonToBeDrawn.getYCoordinate(), buttonToBeDrawn.getWidth(), buttonToBeDrawn.getHeight());
        }

        //--draw all of the button's text--
        SingleLineText[] buttonsText = buttonToBeDrawn.getText();
        if (buttonsText.length > 0){
            for (SingleLineText buttonTextEntry : buttonsText){
                drawingLocation.setFont(buttonTextEntry.getFont()); 
                drawingLocation.setColor(buttonTextEntry.getColour());
        
                //--draw the single line of text onto the button--
                drawingLocation.drawString(buttonTextEntry.getText(), buttonTextEntry.getX(), buttonTextEntry.getY());
            }
        }
    }

    /**
     * calls all of the initialisation methods in this class, in order to calculate / set
     * all of the data for the objects that are being drawn onto the screen 
     * (and create objects for all of the objects that are being drawn onto the screen)
     * 
     * NOTE:
     * these methods must be called in a particular order, since some of the initialised variables
     * depend on other initialised variables (created by higher up initialse calls).
     * i.e. elements are created relative to other elements on the page, so depend on them being initialised.
     */
    private void initialiseAllElements()
    {

        initialiseTitle();

        initialiseMainBox();

        initialiseLoadedPowerpointText();

        initinitialiseBoxSpacing();

        initialiseLoadedFilename();

        initialiseLaunchQuizButton();

        initialiseChangeFileButton();

        initialiseResetCompletedQuestionsButton();

        initialiseSlideModeText();

        initialiseSlideMode1Button();

        initialiseSlideMode2Button();

        initialiseSlideMode3Button();

        initialiseSlideOrderText();

        initialiseSlideOrder1Button();

        initialiseSlideOrder2Button();

        initialiseResetQuestionsTextIndication();
    }


    /**
     * updates the click counters for all button objects on this page
     * note: click counters = used to determine which state to display the colour in: 'clicked down' OR not been clicked
     */
    public void updateAllButtonsClickCounters()
    {
        for (ClickableColouredButton buttonObj : allButtons){
            buttonObj.updateClickCounter();
        }
    }

    /**
     * draws all of the created buttons for this page onto the drawingLocation (i.e. the Graphics2D object for this window)
     */
    public void drawAllButtons()
    {
        updateAllButtonsClickCounters();

        for (ClickableColouredButton buttonObj : allButtons){
            drawButton(buttonObj);
        }
    }

    /**
     * determines if any of the buttons have been clicked and sets them to their clicked state if necessary
     * @param clickedXCoordinate = the x coordinate of the pixel on screen that was clicked
     * @param clickedYCoordinate = the y coordinate of the pixel on screen that was clicked
     */
    public void clickCheckAllButtons(int clickedXCoordinate, int clickedYCoordinate)
    {
        for (ClickableColouredButton buttonObj : allButtons){
            buttonObj.handleNewWindowClick(clickedXCoordinate, clickedYCoordinate);
        }
    }

    public void initialiseChangeFileButton()
    {
        changeFileButtonText = "Change File";
        int[] changeFileButtonColour = {190, 190, 190};
        int[] changeFileButtonHoveredOverColour = {183, 183, 183};
        int[] changeFileButtonClickedColour = {208, 208, 208};
        int[] changeFileButtonBorderColour = {0,0,0};
        int changeFileButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font changeFileButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color changeFileButtonFontColour = new Color (0,0,0);

        ClickableColouredButton changeFileButton;

        //--initialise the button dimesions--
        changeFileButtonWidth = (int) ((mainBoxInnerWidth- (3*sideSpacing)) *0.5);
        changeFileButtonHeight = (int) (windowHeight*0.1);
        changeFileButtonX = mainBoxInnerX + sideSpacing;
        changeFileButtonY = loadedFilenameBoxY + loadedFilenameBoxHeight + sideSpacing;
        
        //--create the button object--
        changeFileButton = new ClickableColouredButton(changeFileButtonX, changeFileButtonY, changeFileButtonWidth, changeFileButtonHeight, changeFileButtonColour, changeFileButtonClickedColour, changeFileButtonHoveredOverColour,changeFileButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the change file button stuff");
            }
        };
        changeFileButton.addBorder(borderMultiplier, changeFileButtonBorderColour);
        allButtons.add(changeFileButton);
        
        //--initialise the button text data--
        drawingLocation.setFont(changeFileButtonFont); 
        changeFileButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(launchQuizButtonText);
        changeFileButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        changeFileButtonTextX = changeFileButtonX + (int) ((changeFileButtonWidth-changeFileButtonTextWidth)/2);
        changeFileButtonTextY = changeFileButtonY + changeFileButtonTextHeight + (int) ((changeFileButtonHeight-changeFileButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        changeFileButton.addText(new SingleLineText(changeFileButtonText,  changeFileButtonTextX,  changeFileButtonTextY, changeFileButtonFont, changeFileButtonFontColour));

    }

    public void initialiseResetCompletedQuestionsButton()
    {
        resetCompletedQuestionsButtonText = "RESET";
        int[] resetCompletedQuestionsButtonColour = {190, 190, 190};
        int[] resetCompletedQuestionsButtonHoveredOverColour = {183,183,183};
        int[] resetCompletedQuestionsButtonClickedColour = {208, 208, 208};
        int[] resetCompletedQuestionsButtonBorderColour = {0,0,0};
        int resetCompletedQuestionsButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font resetCompletedQuestionsButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color resetCompletedQuestionsButtonFontColour = new Color (0,0,0);

        ClickableColouredButton resetCompletedQuestionsButton;

        //--initialise the button dimesions--
        resetCompletedQuestionsButtonWidth = (int) ((mainBoxInnerWidth- (3*sideSpacing)) *0.5);
        resetCompletedQuestionsButtonHeight = (int) (windowHeight*0.1);
        resetCompletedQuestionsButtonX = mainBoxInnerX + sideSpacing + changeFileButtonWidth + sideSpacing;
        resetCompletedQuestionsButtonY =loadedFilenameBoxY + loadedFilenameBoxHeight + sideSpacing;
        
        //--create the button object--
        resetCompletedQuestionsButton = new ClickableColouredButton(resetCompletedQuestionsButtonX, resetCompletedQuestionsButtonY, resetCompletedQuestionsButtonWidth, resetCompletedQuestionsButtonHeight, resetCompletedQuestionsButtonColour, resetCompletedQuestionsButtonClickedColour, resetCompletedQuestionsButtonHoveredOverColour,resetCompletedQuestionsButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the resetCompletedQuestions button stuff");
            }
        };
        resetCompletedQuestionsButton.addBorder(borderMultiplier, resetCompletedQuestionsButtonBorderColour);
        allButtons.add(resetCompletedQuestionsButton);
        
        //--initialise the button text data--
        drawingLocation.setFont(resetCompletedQuestionsButtonFont); 
        resetCompletedQuestionsButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(launchQuizButtonText);
        resetCompletedQuestionsButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        resetCompletedQuestionsButtonTextX = resetCompletedQuestionsButtonX + (int) ((resetCompletedQuestionsButtonWidth-resetCompletedQuestionsButtonTextWidth)/2);
        resetCompletedQuestionsButtonTextY = resetCompletedQuestionsButtonY + resetCompletedQuestionsButtonTextHeight + (int) ((resetCompletedQuestionsButtonHeight-resetCompletedQuestionsButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        resetCompletedQuestionsButton.addText(new SingleLineText(resetCompletedQuestionsButtonText,  resetCompletedQuestionsButtonTextX,  resetCompletedQuestionsButtonTextY, resetCompletedQuestionsButtonFont, resetCompletedQuestionsButtonFontColour));

    }

    public void initialiseSlideModeText()
    {
        Font slideModeTextFont = new Font("Monospaced", Font.PLAIN, 20);
        slideModeText = "Slide Mode";


        drawingLocation.setFont(slideModeTextFont); 

        slideModeTextWidth = drawingLocation.getFontMetrics().stringWidth(slideModeText);
        slideModeTextHeight = drawingLocation.getFontMetrics().getAscent();
        slideModeTextX = mainBoxInnerX + (int) ((mainBoxInnerWidth - slideModeTextWidth)/2);
        slideModeTextY = resetCompletedQuestionsButtonY + resetCompletedQuestionsButtonHeight + slideModeTextHeight + sideSpacing;

    }

    public void drawSlideModeText()
    {
        Font slideModeTextFont = new Font("Monospaced", Font.PLAIN, 20);
        Color slideModeTextColour = new Color(0,0,0);
        slideModeText = "Slide Mode";


        drawingLocation.setFont(slideModeTextFont); 
        drawingLocation.setColor(slideModeTextColour);
        
        drawingLocation.drawString(slideModeText, slideModeTextX, slideModeTextY);
    }

    /**
     * 
     */
    public void initialiseSlideMode1Button()
    {
        slideMode1ButtonText = "Uncomplete";
        int[] slideMode1ButtonColour = {120, 225, 237};
        int[] slideMode1ButtonHoveredOverColour = {113, 218, 230};
        int[] slideMode1ButtonClickedColour = {138, 243, 255};
        int[] slideMode1ButtonBorderColour = {0,0,0};
        int[] slideMode1ButtonSelectedBorderColour = {245, 197, 22};
        int slideMode1ButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font slideMode1ButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color slideMode1ButtonFontColour = new Color (0,0,0);

        ClickableColouredButton slideMode1Button;

        //--initialise the button dimesions--
        slideMode1ButtonWidth = (int) ((mainBoxInnerWidth- (4*sideSpacing)) *0.33);
        slideMode1ButtonHeight = (int) (windowHeight*0.1);
        slideMode1ButtonX = mainBoxInnerX + sideSpacing;
        slideMode1ButtonY = slideModeTextY + sideSpacing;
        
        //--create the button object--
        slideMode1Button = new ClickableColouredButton(slideMode1ButtonX, slideMode1ButtonY, slideMode1ButtonWidth, slideMode1ButtonHeight, slideMode1ButtonColour, slideMode1ButtonClickedColour, slideMode1ButtonHoveredOverColour,slideMode1ButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the slideMode1 button stuff");
            }
        };
        slideMode1Button.addBorder(borderMultiplier, slideMode1ButtonBorderColour);
        allButtons.add(slideMode1Button);
        
        //--initialise the button text data--
        drawingLocation.setFont(slideMode1ButtonFont); 
        slideMode1ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(slideMode1ButtonText);
        slideMode1ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        slideMode1ButtonTextX = slideMode1ButtonX + (int) ((slideMode1ButtonWidth-slideMode1ButtonTextWidth)/2);
        slideMode1ButtonTextY = slideMode1ButtonY + slideMode1ButtonTextHeight + (int) ((slideMode1ButtonHeight-slideMode1ButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        slideMode1Button.addText(new SingleLineText(slideMode1ButtonText,  slideMode1ButtonTextX,  slideMode1ButtonTextY, slideMode1ButtonFont, slideMode1ButtonFontColour));
    }

    /**
     * 
     */
    public void initialiseSlideMode2Button()
    {
        slideMode2ButtonText = "Wrong";
        int[] slideMode2ButtonColour = {120, 225, 237};
        int[] slideMode2ButtonHoveredOverColour = {113, 218, 230};
        int[] slideMode2ButtonClickedColour = {138, 248, 255};
        int[] slideMode2ButtonBorderColour = {0,0,0};
        int[] slideMode2ButtonSelectedBorderColour = {245, 197, 22};
        int slideMode2ButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font slideMode2ButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color slideMode2ButtonFontColour = new Color (0,0,0);

        ClickableColouredButton slideMode2Button;

        //--initialise the button dimesions--
        slideMode2ButtonWidth = (int) ((mainBoxInnerWidth- (4*sideSpacing)) *0.33);
        slideMode2ButtonHeight = (int) (windowHeight*0.1);
        slideMode2ButtonX = mainBoxInnerX + sideSpacing + slideMode1ButtonWidth + sideSpacing;
        slideMode2ButtonY = slideModeTextY + sideSpacing;
        
        //--create the button object--
        slideMode2Button = new ClickableColouredButton(slideMode2ButtonX, slideMode2ButtonY, slideMode2ButtonWidth, slideMode2ButtonHeight, slideMode2ButtonColour, slideMode2ButtonClickedColour, slideMode2ButtonHoveredOverColour,slideMode2ButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the slideMode2 button stuff");
            }
        };
        slideMode2Button.addBorder(borderMultiplier, slideMode2ButtonBorderColour);
        allButtons.add(slideMode2Button);
        
        //--initialise the button text data--
        drawingLocation.setFont(slideMode2ButtonFont); 
        slideMode2ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(slideMode2ButtonText);
        slideMode2ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        slideMode2ButtonTextX = slideMode2ButtonX + (int) ((slideMode2ButtonWidth-slideMode2ButtonTextWidth)/2);
        slideMode2ButtonTextY = slideMode2ButtonY + slideMode2ButtonTextHeight + (int) ((slideMode2ButtonHeight-slideMode2ButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        slideMode2Button.addText(new SingleLineText(slideMode2ButtonText,  slideMode2ButtonTextX,  slideMode2ButtonTextY, slideMode2ButtonFont, slideMode2ButtonFontColour));
    }

    /**
     * 
     */
    public void initialiseSlideMode3Button()
    {
        slideMode3ButtonText = "Wrong Last Attempt";
        int[] slideMode3ButtonColour = {120, 225, 237};
        int[] slideMode3ButtonHoveredOverColour = {113, 218, 230};
        int[] slideMode3ButtonClickedColour = {138, 248, 255};
        int[] slideMode3ButtonBorderColour = {0,0,0};
        int[] slideMode3ButtonSelectedBorderColour = {245, 197, 22};
        int slideMode3ButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font slideMode3ButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color slideMode3ButtonFontColour = new Color (0,0,0);

        ClickableColouredButton slideMode3Button;

        //--initialise the button dimesions--
        slideMode3ButtonWidth = (int) ((mainBoxInnerWidth- (4*sideSpacing)) *0.33);
        slideMode3ButtonHeight = (int) (windowHeight*0.1);
        slideMode3ButtonX = mainBoxInnerX + sideSpacing + slideMode1ButtonWidth + sideSpacing + slideMode2ButtonWidth + sideSpacing;
        slideMode3ButtonY = slideModeTextY + sideSpacing;
        
        //--create the button object--
        slideMode3Button = new ClickableColouredButton(slideMode3ButtonX, slideMode3ButtonY, slideMode3ButtonWidth, slideMode3ButtonHeight, slideMode3ButtonColour, slideMode3ButtonClickedColour, slideMode3ButtonHoveredOverColour,slideMode3ButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the slideMode3 button stuff");
            }
        };
        slideMode3Button.addBorder(borderMultiplier, slideMode3ButtonBorderColour);
        allButtons.add(slideMode3Button);
        
        //--initialise the button text data--
        drawingLocation.setFont(slideMode3ButtonFont); 
        slideMode3ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(slideMode3ButtonText);
        slideMode3ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        slideMode3ButtonTextX = slideMode3ButtonX + (int) ((slideMode3ButtonWidth-slideMode3ButtonTextWidth)/2);
        slideMode3ButtonTextY = slideMode3ButtonY + slideMode3ButtonTextHeight + (int) ((slideMode3ButtonHeight-slideMode3ButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        slideMode3Button.addText(new SingleLineText(slideMode3ButtonText,  slideMode3ButtonTextX,  slideMode3ButtonTextY, slideMode3ButtonFont, slideMode3ButtonFontColour));
    }

    public void initialiseSlideOrderText()
    {
        slideOrderText = "Slide Order";
        Font slideOrderTextFont = new Font("Monospaced", Font.PLAIN, 20);

        drawingLocation.setFont(slideOrderTextFont); 
        
        slideOrderTextWidth = drawingLocation.getFontMetrics().stringWidth(slideOrderText);
        slideOrderTextHeight = drawingLocation.getFontMetrics().getAscent();
        slideOrderTextX = mainBoxInnerX + (int) ((mainBoxInnerWidth - slideOrderTextWidth)/2);
        slideOrderTextY = slideMode1ButtonY + slideMode1ButtonHeight + sideSpacing + slideOrderTextHeight;
    }

    public void drawSlideOrderText()
    {
        Color slideOrderTextColour = new Color(0,0,0);
        Font slideOrderTextFont = new Font("Monospaced", Font.PLAIN, 20);

        //--text--
        drawingLocation.setFont(slideOrderTextFont); 
        drawingLocation.setColor(slideOrderTextColour);        

        drawingLocation.drawString(slideOrderText, slideOrderTextX, slideOrderTextY);
    }

    /**
     * 
     */
    public void initialiseSlideOrder1Button()
    {
        slideOrder1ButtonText = "Random";
        int[] slideOrder1ButtonColour = {129, 225, 237};
        int[] slideOrder1ButtonHoveredOverColour = {113, 218, 230};
        int[] slideOrder1ButtonClickedColour = {138, 243, 255};
        int[] slideOrder1ButtonBorderColour = {0,0,0};
        int[] slideOrder1ButtonSelectedBorderColour = {245, 197, 22};
        int slideOrder1ButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font slideOrder1ButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color slideOrder1ButtonFontColour = new Color (0,0,0);

        ClickableColouredButton slideOrder1Button;

        //--initialise the button dimesions--
        slideOrder1ButtonWidth = (int) ((mainBoxInnerWidth- (3*sideSpacing)) *0.5);
        slideOrder1ButtonHeight = (int) (windowHeight*0.1);
        slideOrder1ButtonX = mainBoxInnerX + sideSpacing;
        slideOrder1ButtonY = slideOrderTextY + sideSpacing;
        
        //--create the button object--
        slideOrder1Button = new ClickableColouredButton(slideOrder1ButtonX, slideOrder1ButtonY, slideOrder1ButtonWidth, slideOrder1ButtonHeight, slideOrder1ButtonColour, slideOrder1ButtonClickedColour, slideOrder1ButtonHoveredOverColour,slideOrder1ButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the slideOrder1 button stuff");
            }
        };
        slideOrder1Button.addBorder(borderMultiplier, slideOrder1ButtonBorderColour);
        allButtons.add(slideOrder1Button);
        
        //--initialise the button text data--
        drawingLocation.setFont(slideOrder1ButtonFont); 
        slideOrder1ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(slideOrder1ButtonText);
        slideOrder1ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        slideOrder1ButtonTextX = slideOrder1ButtonX + (int) ((slideOrder1ButtonWidth-slideOrder1ButtonTextWidth)/2);
        slideOrder1ButtonTextY = slideOrder1ButtonY + slideOrder1ButtonTextHeight + (int) ((slideOrder1ButtonHeight-slideOrder1ButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        slideOrder1Button.addText(new SingleLineText(slideOrder1ButtonText,  slideOrder1ButtonTextX,  slideOrder1ButtonTextY, slideOrder1ButtonFont, slideOrder1ButtonFontColour));
    }

    /**
     * 
     */
    public void initialiseSlideOrder2Button()
    {
        slideOrder2ButtonText = "Descending Wrongness";
        int[] slideOrder2ButtonColour = {120, 225, 237};
        int[] slideOrder2ButtonHoveredOverColour = {113, 218, 230};
        int[] slideOrder2ButtonClickedColour = {138, 243, 255};
        int[] slideOrder2ButtonBorderColour = {0,0,0};
        int[] slideOrder2ButtonSelectedBorderColour = {245, 197, 22};
        int slideOrder2ButtonMaxClickedCount = 3;
        double borderMultiplier = 0.1;
        Font slideOrder2ButtonFont = new Font("Monospaced", Font.PLAIN, 20);
        Color slideOrder2ButtonFontColour = new Color (0,0,0);

        ClickableColouredButton slideOrder2Button;

        //--initialise the button dimesions--
        slideOrder2ButtonWidth = (int) ((mainBoxInnerWidth- (3*sideSpacing)) *0.5);
        slideOrder2ButtonHeight = (int) (windowHeight*0.1);
        slideOrder2ButtonX = mainBoxInnerX + sideSpacing + slideOrder1ButtonWidth+ sideSpacing;
        slideOrder2ButtonY = slideOrderTextY + sideSpacing;
        
        //--create the button object--
        slideOrder2Button = new ClickableColouredButton(slideOrder2ButtonX, slideOrder2ButtonY, slideOrder2ButtonWidth, slideOrder2ButtonHeight, slideOrder2ButtonColour, slideOrder2ButtonClickedColour, slideOrder2ButtonHoveredOverColour,slideOrder2ButtonMaxClickedCount){
            @Override
            public void activateClickedProcedure(){
                System.out.println("doing all of the slideOrder2 button stuff");
            }
        };
        slideOrder2Button.addBorder(borderMultiplier, slideOrder2ButtonBorderColour);
        allButtons.add(slideOrder2Button);
        
        //--initialise the button text data--
        drawingLocation.setFont(slideOrder2ButtonFont); 
        slideOrder2ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(slideOrder2ButtonText);
        slideOrder2ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        slideOrder2ButtonTextX = slideOrder2ButtonX + (int) ((slideOrder2ButtonWidth-slideOrder2ButtonTextWidth)/2);
        slideOrder2ButtonTextY = slideOrder2ButtonY + slideOrder2ButtonTextHeight + (int) ((slideOrder2ButtonHeight-slideOrder2ButtonTextHeight)/2);

        //--create the button's font object, and add to the button--
        slideOrder2Button.addText(new SingleLineText(slideOrder2ButtonText,  slideOrder2ButtonTextX,  slideOrder2ButtonTextY, slideOrder2ButtonFont, slideOrder2ButtonFontColour));
    }

    public void initialiseResetQuestionsTextIndication()
    {
        resetQuestionsIndicationText = "The completed questions have been reset";
        Font resetQuestionsTextFont = new Font("Monospaced", Font.PLAIN, 20);

        drawingLocation.setFont(resetQuestionsTextFont); 
        resetQuestionsIndicationTextWidth = drawingLocation.getFontMetrics().stringWidth(resetQuestionsIndicationText);
        resetQuestionsIndicationTextHeight = drawingLocation.getFontMetrics().getAscent();
        resetQuestionsIndicationTextX = mainBoxInnerX + (int) ((mainBoxInnerWidth - resetQuestionsIndicationTextWidth)/2);
        resetQuestionsIndicationTextY = titleY + titleHeight +resetQuestionsIndicationTextHeight +  sideSpacing;
    }

    public void drawResetQuestionsTextIndication()
    {
        //--text--
        Font resetQuestionsTextFont = new Font("Monospaced", Font.PLAIN, 20);
        Color resetQuestionsTextColour = new Color(0,0, 128);
        int resetQuestionsTextDisplayCount = 30;

        if (resetQuestionsIndicationTextActivated == true){
            drawingLocation.setFont(resetQuestionsTextFont); 
            drawingLocation.setColor(resetQuestionsTextColour);
            drawingLocation.drawString(resetQuestionsIndicationText, resetQuestionsIndicationTextX, resetQuestionsIndicationTextY); 

            if (resetQuestionsIndicationTextActivatedTick >= resetQuestionsTextDisplayCount){
                resetQuestionsIndicationTextActivated = false;
                resetQuestionsIndicationTextActivatedTick = 0;
            }
            else{
                resetQuestionsIndicationTextActivatedTick++;
            }
        }
    }


    //---GETTERS AND SETTERS---

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode1Selected()
    {
        return slideMode1ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode2Selected()
    {
        return slideMode2ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode3Selected()
    {
        return slideMode3ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder1Selected()
    {
        return slideOrder1ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder2Selected()
    {
        return slideOrder2ButtonSelected;
    }

    //--launch quiz button--

    /**
     * @return the x coordinate of the top left corner of the launch quiz button
     */
    public int getLaunchQuizButtonX()
    {
        return launchQuizButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the launch quiz button
     */
    public int getLaunchQuizButtonY()
    {
        return launchQuizButtonY;
    }

    /**
     * @return the width of the launch quiz button
     */
    public int getLaunchQuizButtonW()
    {
        return launchQuizButtonWidth;
    }

    /**
     * @return the width of the launch quiz button
     */
    public int getLaunchQuizButtonH()
    {
        return launchQuizButtonWidth;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setLaunchQuizButtonClicked(boolean newClicked)
    {
        launchQuizButtonClicked = newClicked;
    }

    //--change file button--

    /**
     * @return the x coordinate of the top left corner of the change file button
     */
    public int getChangeFileButtonX()
    {
        return changeFileButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the change file button
     */
    public int getChangeFileButtonY()
    {
        return changeFileButtonY;
    }

    /**
     * @return the width of the change file button
     */
    public int getChangeFileButtonW()
    {
        return changeFileButtonWidth;
    }

    /**
     * @return the width of the change file button
     */
    public int getChangeFileButtonH()
    {
        return changeFileButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setChangeFileButtonClicked(boolean newClicked)
    {
        changeFileButtonClicked = newClicked;
    }

    //--reset completed questions button--

    /**
     * @return the x coordinate of the top left corner of the change file button
     */
    public int getResetCompletedQuestionsButtonX()
    {
        return resetCompletedQuestionsButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the change file button
     */
    public int getResetCompletedQuestionsButtonY()
    {
        return resetCompletedQuestionsButtonY;
    }

    /**
     * @return the width of the change file button
     */
    public int getResetCompletedQuestionsButtonW()
    {
        return resetCompletedQuestionsButtonWidth;
    }

    /**
     * @return the width of the change file button
     */
    public int getResetCompletedQuestionsButtonH()
    {
        return resetCompletedQuestionsButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setResetCompletedQuestionsButtonClicked(boolean newClicked)
    {
        resetCompletedQuestionsButtonClicked = newClicked;
    }

    /**
     * @param newActivated the new value to determine whether the text is activated (true) or not (false)
     */
    public void setResetCompletedQuestionsIndicationTextActivated(boolean newActivated)
    {
        resetQuestionsIndicationTextActivated = newActivated;
    }


    //--slide mode buttons--

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideMode1ButtonX()
    {
        return slideMode1ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideMode1ButtonY()
    {
        return slideMode1ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode1ButtonW()
    {
        return slideMode1ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode1ButtonH()
    {
        return slideMode1ButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideMode1ButtonClicked(boolean newClicked)
    {
        slideMode1ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideMode1ButtonSelected(boolean newSelected)
    {
        slideMode1ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode1ButtonSelected()
    {
        return slideMode1ButtonSelected;
    }


    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideMode2ButtonX()
    {
        return slideMode2ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideMode2ButtonY()
    {
        return slideMode2ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode2ButtonW()
    {
        return slideMode2ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode2ButtonH()
    {
        return slideMode2ButtonHeight;
    }

     /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideMode2ButtonClicked(boolean newClicked)
    {
        slideMode2ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideMode2ButtonSelected(boolean newSelected)
    {
        slideMode2ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode2ButtonSelected()
    {
        return slideMode2ButtonSelected;
    }

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideMode3ButtonX()
    {
        return slideMode3ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideMode3ButtonY()
    {
        return slideMode3ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode3ButtonW()
    {
        return slideMode3ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode3ButtonH()
    {
        return slideMode3ButtonHeight;
    }

     /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideMode3ButtonClicked(boolean newClicked)
    {
        slideMode3ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideMode3ButtonSelected(boolean newSelected)
    {
        slideMode3ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode3ButtonSelected()
    {
        return slideMode3ButtonSelected;
    }

    //--slide order buttons--

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideOrder1ButtonX()
    {
        return slideOrder1ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideOrder1ButtonY()
    {
        return slideOrder1ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder1ButtonW()
    {
        return slideOrder1ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder1ButtonH()
    {
        return slideOrder1ButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideOrder1ButtonClicked(boolean newClicked)
    {
        slideOrder1ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideOrder1ButtonSelected(boolean newSelected)
    {
        slideOrder1ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder1ButtonSelected()
    {
        return slideOrder1ButtonSelected;
    }

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideOrder2ButtonX()
    {
        return slideOrder2ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideOrder2ButtonY()
    {
        return slideOrder2ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder2ButtonW()
    {
        return slideOrder2ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder2ButtonH()
    {
        return slideOrder2ButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideOrder2ButtonClicked(boolean newClicked)
    {
        slideOrder2ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideOrder2ButtonSelected(boolean newSelected)
    {
        slideOrder2ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder2ButtonSelected()
    {
        return slideOrder2ButtonSelected;
    }
    
}