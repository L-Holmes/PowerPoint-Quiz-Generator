import java.awt.*;//used for layout managers


public class ClickPanelDrawStartPage
{
    //graphics handler
    Graphics2D drawingLocation;

    //window dimensions
    int windowWidth;
    int windowHeight;

    //location of the slide that is to be loaded
    String slidePDFLocation;

    //mouse coordinates
    int pointXCoord;
	int pointYCoord;

    //---GENERAL PAGE DRAWING---

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
	


    public ClickPanelDrawStartPage(int windowW, int windowH)
    {
        //
        windowWidth = windowW;
        windowHeight = windowH;
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

    public void drawStartPage(Graphics2D graphicsHandle, String slideFilePath, int mouseXCoord, int mouseYCoord)
    {
        //updating variables

        drawingLocation = graphicsHandle;
        slidePDFLocation = slideFilePath;
        pointXCoord = mouseXCoord;
        pointYCoord = mouseYCoord;

        //drawing

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
            drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 50)); 
            
            //top text
            startPageTitleTextUpper = "Powerpoint";
            startPageTitleUpperTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageTitleTextUpper);
            startPageTitleUpperTextHeight = drawingLocation.getFontMetrics().getAscent();
            startPageTitleUpperTextX = startPageTitleX + (int) ((startPageTitleX - startPageTitleUpperTextWidth)/2);
            startPageTitleUpperTextY = startPageTitleY + (int) (startPageTitleUpperTextHeight);

            drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 50)); 
            //bottom text
            startPageTitleTextLower = "Quiz Generator";
            startPageTitleLowerTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageTitleTextLower);
            startPageTitleLowerTextHeight = drawingLocation.getFontMetrics().getAscent();
            startPageTitleLowerTextX = startPageTitleX + (int) ((startPageTitleX - startPageTitleLowerTextWidth)/2);
            startPageTitleLowerTextY = startPageTitleUpperTextY + startPageTitleUpperTextHeight + (int) (windowHeight*0.015);

            setStartPageTitleStuff = true;

        }
        

        drawingLocation.setColor(new Color(255, 255, 255));
        drawingLocation.fillRect(startPageTitleX, startPageTitleY, startPageTitleWidth, startPageTitleHeight);

        //black border around the box
        drawingLocation.setColor(new Color(1, 1, 1));
        drawingLocation.drawRect(startPageTitleX, startPageTitleY, startPageTitleWidth, startPageTitleHeight);



        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 50)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        
        drawingLocation.drawString(startPageTitleTextUpper, startPageTitleUpperTextX, startPageTitleUpperTextY);
        
        drawingLocation.drawString(startPageTitleTextLower, startPageTitleLowerTextX, startPageTitleLowerTextY);




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
        

        drawingLocation.setColor(new Color(22, 122, 24));
        drawingLocation.drawRect(startPageMainBoxOuterX, startPageMainBoxOuterY, startPageMainBoxOuterWidth, startPageMainBoxOuterHeight);
        
        
        
        drawingLocation.setColor(new Color(22, 207, 159));
        drawingLocation.fillRect(startPageMainBoxInnerX, startPageMainBoxInnerY, startPageMainBoxInnerWidth, startPageMainBoxInnerHeight);

        //outline for the inner box
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(startPageMainBoxInnerX, startPageMainBoxInnerY, startPageMainBoxInnerWidth, startPageMainBoxInnerHeight);

    }



    public void drawLoadedPowerpointText()
    {
        //--text--

        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        startPageLoadedPowerpointText = "Loaded Powerpoint";
        startPageLoadedPowerpointTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageLoadedPowerpointText);
        startPageLoadedPowerpointTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageLoadedPowerpointTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageLoadedPowerpointTextWidth)/2);
        startPageLoadedPowerpointTextY = startPageMainBoxInnerY + startPageLoadedPowerpointTextHeight + (int) (windowHeight*0.01);
        
        drawingLocation.drawString(startPageLoadedPowerpointText, startPageLoadedPowerpointTextX, startPageLoadedPowerpointTextY);

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

        drawingLocation.setColor(new Color(250, 250, 250));
        drawingLocation.fillRect(startPageLoadedFilenameBoxX, startPageLoadedFilenameBoxY, startPageLoadedFilenameBoxWidth, startPageLoadedFilenameBoxHeight);

        //black border around the box
        drawingLocation.setColor(new Color(100, 100, 100));
        drawingLocation.drawRect(startPageLoadedFilenameBoxX, startPageLoadedFilenameBoxY, startPageLoadedFilenameBoxWidth, startPageLoadedFilenameBoxHeight);



        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        startPageLoadedFilenameText = (ConvertPDFPagesToImages.getFileNameFromLocation(slidePDFLocation));
        startPageLoadedFilenameTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageLoadedFilenameText);
        startPageLoadedFilenameTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageLoadedFilenameTextX = startPageLoadedFilenameBoxX + (int) (windowWidth*0.01);
        startPageLoadedFilenameTextY = startPageLoadedFilenameBoxY +startPageLoadedFilenameTextHeight + (int) (windowHeight*0.01);
        
        drawingLocation.drawString(startPageLoadedFilenameText, startPageLoadedFilenameTextX, startPageLoadedFilenameTextY);

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
            drawingLocation.setColor(new Color(110, 240, 25));

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
            drawingLocation.setColor(new Color(85, 215, 0));
        }
        else{
            //
            drawingLocation.setColor(new Color(92, 222, 8));
        }
        drawingLocation.fillRect(startPageLaunchQuizButtonX, startPageLaunchQuizButtonY, startPageLaunchQuizButtonWidth, startPageLaunchQuizButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(startPageLaunchQuizButtonX, startPageLaunchQuizButtonY, startPageLaunchQuizButtonWidth, startPageLaunchQuizButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageLaunchQuizButtonText = "Lauch Quiz";
        startPageLaunchQuizButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageLaunchQuizButtonText);
        startPageLaunchQuizButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageLaunchQuizButtonTextX = startPageLaunchQuizButtonX + (int) ((startPageLaunchQuizButtonWidth-startPageLaunchQuizButtonTextWidth)/2);
        startPageLaunchQuizButtonTextY = startPageLaunchQuizButtonY + startPageLaunchQuizButtonTextHeight + (int) ((startPageLaunchQuizButtonHeight-startPageLaunchQuizButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageLaunchQuizButtonText, startPageLaunchQuizButtonTextX, startPageLaunchQuizButtonTextY);

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
            drawingLocation.setColor(new Color(208, 208, 208));


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
            drawingLocation.setColor(new Color(183, 183, 183));
        }
        else{
            //
            drawingLocation.setColor(new Color(190, 190, 190));
        }
        drawingLocation.fillRect(startPageChangeFileButtonX, startPageChangeFileButtonY, startPageChangeFileButtonWidth, startPageChangeFileButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(startPageChangeFileButtonX, startPageChangeFileButtonY, startPageChangeFileButtonWidth, startPageChangeFileButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageChangeFileButtonText = "Change File";
        startPageChangeFileButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageLaunchQuizButtonText);
        startPageChangeFileButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageChangeFileButtonTextX = startPageChangeFileButtonX + (int) ((startPageChangeFileButtonWidth-startPageChangeFileButtonTextWidth)/2);
        startPageChangeFileButtonTextY = startPageChangeFileButtonY + startPageChangeFileButtonTextHeight + (int) ((startPageChangeFileButtonHeight-startPageChangeFileButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageChangeFileButtonText, startPageChangeFileButtonTextX, startPageChangeFileButtonTextY);

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
            drawingLocation.setColor(new Color(208, 208, 208));

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
            drawingLocation.setColor(new Color(183, 183, 183));
        }
        else{
            //
            drawingLocation.setColor(new Color(190, 190, 190));
        }
        drawingLocation.fillRect(startPageResetCompletedQuestionsButtonX, startPageResetCompletedQuestionsButtonY, startPageResetCompletedQuestionsButtonWidth, startPageResetCompletedQuestionsButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(startPageResetCompletedQuestionsButtonX, startPageResetCompletedQuestionsButtonY, startPageResetCompletedQuestionsButtonWidth, startPageResetCompletedQuestionsButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageResetCompletedQuestionsButtonText = "RESET";
        startPageResetCompletedQuestionsButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageLaunchQuizButtonText);
        startPageResetCompletedQuestionsButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageResetCompletedQuestionsButtonTextX = startPageResetCompletedQuestionsButtonX + (int) ((startPageResetCompletedQuestionsButtonWidth-startPageResetCompletedQuestionsButtonTextWidth)/2);
        startPageResetCompletedQuestionsButtonTextY = startPageResetCompletedQuestionsButtonY + startPageResetCompletedQuestionsButtonTextHeight + (int) ((startPageResetCompletedQuestionsButtonHeight-startPageResetCompletedQuestionsButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageResetCompletedQuestionsButtonText, startPageResetCompletedQuestionsButtonTextX, startPageResetCompletedQuestionsButtonTextY);

        setResetCompletedQuestionsButtonStuff = true;
    }

    ////////////////////////////////////////////***************************** */
    public void drawSlideModeText()
    {
        //--text--

        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        startPageSlideModeText = "Slide Mode";
        startPageSlideModeTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageSlideModeText);
        startPageSlideModeTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageSlideModeTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageSlideModeTextWidth)/2);
        startPageTextY = startPageResetCompletedQuestionsButtonY + startPageResetCompletedQuestionsButtonHeight + startPageSlideModeTextHeight + sideSpacing;
        
        drawingLocation.drawString(startPageSlideModeText, startPageSlideModeTextX, startPageTextY);

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

            drawingLocation.setColor(new Color(245, 197, 22));
            drawingLocation.fillRect(startPageSlideMode1ButtonOutlineX, startPageSlideMode1ButtonOutlineY, startPageSlideMode1ButtonOutlineW, startPageSlideMode1ButtonOutlineH);
        }

        //--box that the string goes in--
        startPageSlideMode1ButtonWidth = (int) ((startPageMainBoxInnerWidth- (4*sideSpacing)) *0.33);
        startPageSlideMode1ButtonHeight = (int) (windowHeight*0.1);
        startPageSlideMode1ButtonX = startPageMainBoxInnerX + sideSpacing;
        startPageSlideMode1ButtonY = startPageTextY + sideSpacing;
        int startPageSlideMode1ButtonRight = startPageSlideMode1ButtonX + startPageSlideMode1ButtonWidth;
        int startPageSlideMode1ButtonBottom = startPageSlideMode1ButtonY + startPageSlideMode1ButtonHeight;

        if (startPageSlideMode1ButtonClicked == true){
            drawingLocation.setColor(new Color(138, 243, 255));

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
            drawingLocation.setColor(new Color(113, 218, 230));
        }
        else{
            //
            drawingLocation.setColor(new Color(120, 225, 237));

        }
        drawingLocation.fillRect(startPageSlideMode1ButtonX, startPageSlideMode1ButtonY, startPageSlideMode1ButtonWidth, startPageSlideMode1ButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        if (startPageSlideMode1ButtonSelected == true){
            drawingLocation.setColor(new Color(245, 197, 22));
        }
        drawingLocation.drawRect(startPageSlideMode1ButtonX, startPageSlideMode1ButtonY, startPageSlideMode1ButtonWidth, startPageSlideMode1ButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageSlideMode1ButtonText = "Uncomplete";
        startPageSlideMode1ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageSlideMode1ButtonText);
        startPageSlideMode1ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageSlideMode1ButtonTextX = startPageSlideMode1ButtonX + (int) ((startPageSlideMode1ButtonWidth-startPageSlideMode1ButtonTextWidth)/2);
        startPageSlideMode1ButtonTextY = startPageSlideMode1ButtonY + startPageSlideMode1ButtonTextHeight + (int) ((startPageSlideMode1ButtonHeight-startPageSlideMode1ButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageSlideMode1ButtonText, startPageSlideMode1ButtonTextX, startPageSlideMode1ButtonTextY);


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

            drawingLocation.setColor(new Color(245, 197, 22));
            drawingLocation.fillRect(startPageSlideMode2ButtonOutlineX, startPageSlideMode2ButtonOutlineY, startPageSlideMode2ButtonOutlineW, startPageSlideMode2ButtonOutlineH);
        }

        //--box that the string goes in--
        startPageSlideMode2ButtonWidth = (int) ((startPageMainBoxInnerWidth- (4*sideSpacing)) *0.33);
        startPageSlideMode2ButtonHeight = (int) (windowHeight*0.1);
        startPageSlideMode2ButtonX = startPageMainBoxInnerX + sideSpacing + startPageSlideMode1ButtonWidth + sideSpacing;
        startPageSlideMode2ButtonY = startPageTextY + sideSpacing;
        int startPageSlideMode2ButtonRight = startPageSlideMode2ButtonX + startPageSlideMode2ButtonWidth;
        int startPageSlideMode2ButtonBottom = startPageSlideMode2ButtonY + startPageSlideMode2ButtonHeight;

        if (startPageSlideMode2ButtonClicked == true){
            drawingLocation.setColor(new Color(138, 248, 255));

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
            drawingLocation.setColor(new Color(113, 218, 230));
        }
        else{
            //
            drawingLocation.setColor(new Color(120, 225, 237));
        }
        drawingLocation.fillRect(startPageSlideMode2ButtonX, startPageSlideMode2ButtonY, startPageSlideMode2ButtonWidth, startPageSlideMode2ButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        if (startPageSlideMode2ButtonSelected == true){
            drawingLocation.setColor(new Color(245, 197, 22));
        }
        drawingLocation.drawRect(startPageSlideMode2ButtonX, startPageSlideMode2ButtonY, startPageSlideMode2ButtonWidth, startPageSlideMode2ButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageSlideMode2ButtonText = "Wrong";
        startPageSlideMode2ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageSlideMode2ButtonText);
        startPageSlideMode2ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageSlideMode2ButtonTextX = startPageSlideMode2ButtonX + (int) ((startPageSlideMode2ButtonWidth-startPageSlideMode2ButtonTextWidth)/2);
        startPageSlideMode2ButtonTextY = startPageSlideMode2ButtonY + startPageSlideMode2ButtonTextHeight + (int) ((startPageSlideMode2ButtonHeight-startPageSlideMode2ButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageSlideMode2ButtonText, startPageSlideMode2ButtonTextX, startPageSlideMode2ButtonTextY);

        

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

            drawingLocation.setColor(new Color(245, 197, 22));
            drawingLocation.fillRect(startPageSlideMode3ButtonOutlineX, startPageSlideMode3ButtonOutlineY, startPageSlideMode3ButtonOutlineW, startPageSlideMode3ButtonOutlineH);
        }

        //--box that the string goes in--
        startPageSlideMode3ButtonWidth = (int) ((startPageMainBoxInnerWidth- (4*sideSpacing)) *0.33);
        startPageSlideMode3ButtonHeight = (int) (windowHeight*0.1);
        startPageSlideMode3ButtonX = startPageMainBoxInnerX + sideSpacing + startPageSlideMode1ButtonWidth + sideSpacing + startPageSlideMode2ButtonWidth + sideSpacing;
        startPageSlideMode3ButtonY = startPageTextY + sideSpacing;
        int startPageSlideMode3ButtonRight = startPageSlideMode3ButtonX + startPageSlideMode3ButtonWidth;
        int startPageSlideMode3ButtonBottom = startPageSlideMode3ButtonY + startPageSlideMode3ButtonHeight;

        if (startPageSlideMode3ButtonClicked == true){
            drawingLocation.setColor(new Color(138, 248, 255));

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
            drawingLocation.setColor(new Color(113, 218, 230));
        }
        else{
            //
            drawingLocation.setColor(new Color(120, 225, 237));
        }
        drawingLocation.fillRect(startPageSlideMode3ButtonX, startPageSlideMode3ButtonY, startPageSlideMode3ButtonWidth, startPageSlideMode3ButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        if (startPageSlideMode3ButtonSelected == true){
            drawingLocation.setColor(new Color(245, 197, 22));
        }
        drawingLocation.drawRect(startPageSlideMode3ButtonX, startPageSlideMode3ButtonY, startPageSlideMode3ButtonWidth, startPageSlideMode3ButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageSlideMode3ButtonText = "Wrong Last Attempt";
        startPageSlideMode3ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageSlideMode3ButtonText);
        startPageSlideMode3ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageSlideMode3ButtonTextX = startPageSlideMode3ButtonX + (int) ((startPageSlideMode3ButtonWidth-startPageSlideMode3ButtonTextWidth)/2);
        startPageSlideMode3ButtonTextY = startPageSlideMode3ButtonY + startPageSlideMode3ButtonTextHeight + (int) ((startPageSlideMode3ButtonHeight-startPageSlideMode3ButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageSlideMode3ButtonText, startPageSlideMode3ButtonTextX, startPageSlideMode3ButtonTextY);

        setSlideMode3ButtonStuff = true;
    }

    public void drawSlideOrderText()
    {
        //--text--

        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        startPageSlideOrderText = "Slide Order";
        startPageSlideOrderTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageSlideOrderText);
        startPageSlideOrderTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageSlideOrderTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageSlideOrderTextWidth)/2);
        startPageSlideOrderTextY = startPageSlideMode1ButtonY + startPageSlideMode1ButtonHeight + sideSpacing + startPageSlideOrderTextHeight;
        
        drawingLocation.drawString(startPageSlideOrderText, startPageSlideOrderTextX, startPageSlideOrderTextY);

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

            drawingLocation.setColor(new Color(245, 197, 22));
            drawingLocation.fillRect(startPageSlideOrder1ButtonOutlineX, startPageSlideOrder1ButtonOutlineY, startPageSlideOrder1ButtonOutlineW, startPageSlideOrder1ButtonOutlineH);
        }

        //--box that the string goes in--
        startPageSlideOrder1ButtonWidth = (int) ((startPageMainBoxInnerWidth- (3*sideSpacing)) *0.5);
        startPageSlideOrder1ButtonHeight = (int) (windowHeight*0.1);
        startPageSlideOrder1ButtonX = startPageMainBoxInnerX + sideSpacing;
        startPageSlideOrder1ButtonY = startPageSlideOrderTextY + sideSpacing;
        int startPageSlideOrder1ButtonRight = startPageSlideOrder1ButtonX + startPageSlideOrder1ButtonWidth;
        int startPageSlideOrder1ButtonBottom = startPageSlideOrder1ButtonY + startPageSlideOrder1ButtonHeight;

        if (startPageSlideOrder1ButtonClicked == true){
            drawingLocation.setColor(new Color(138, 243, 255));

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
            drawingLocation.setColor(new Color(113, 218, 230));
        }
        else{
            //
            drawingLocation.setColor(new Color(129, 225, 237));
        }
        drawingLocation.fillRect(startPageSlideOrder1ButtonX, startPageSlideOrder1ButtonY, startPageSlideOrder1ButtonWidth, startPageSlideOrder1ButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        if (startPageSlideOrder1ButtonSelected == true){
            drawingLocation.setColor(new Color(245, 197, 22));
        }
        drawingLocation.drawRect(startPageSlideOrder1ButtonX, startPageSlideOrder1ButtonY, startPageSlideOrder1ButtonWidth, startPageSlideOrder1ButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageSlideOrder1ButtonText = "Random";
        startPageSlideOrder1ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageSlideOrder1ButtonText);
        startPageSlideOrder1ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageSlideOrder1ButtonTextX = startPageSlideOrder1ButtonX + (int) ((startPageSlideOrder1ButtonWidth-startPageSlideOrder1ButtonTextWidth)/2);
        startPageSlideOrder1ButtonTextY = startPageSlideOrder1ButtonY + startPageSlideOrder1ButtonTextHeight + (int) ((startPageSlideOrder1ButtonHeight-startPageSlideOrder1ButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageSlideOrder1ButtonText, startPageSlideOrder1ButtonTextX, startPageSlideOrder1ButtonTextY);

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

            drawingLocation.setColor(new Color(245, 197, 22));
            drawingLocation.fillRect(startPageSlideOrder2ButtonOutlineX, startPageSlideOrder2ButtonOutlineY, startPageSlideOrder2ButtonOutlineW, startPageSlideOrder2ButtonOutlineH);
        }

        //--box that the string goes in--
        startPageSlideOrder2ButtonWidth = (int) ((startPageMainBoxInnerWidth- (3*sideSpacing)) *0.5);
        startPageSlideOrder2ButtonHeight = (int) (windowHeight*0.1);
        startPageSlideOrder2ButtonX = startPageMainBoxInnerX + sideSpacing + startPageSlideOrder1ButtonWidth+ sideSpacing;
        startPageSlideOrder2ButtonY = startPageSlideOrderTextY + sideSpacing;
        int startPageSlideOrder2ButtonRight = startPageSlideOrder2ButtonX + startPageSlideOrder2ButtonWidth;
        int startPageSlideOrder2ButtonBottom = startPageSlideOrder2ButtonY + startPageSlideOrder2ButtonHeight;

        if (startPageSlideOrder2ButtonClicked == true){
            drawingLocation.setColor(new Color(138, 243, 255));

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
            drawingLocation.setColor(new Color(113, 218, 230));
        }
        else{
            //
            drawingLocation.setColor(new Color(120, 225, 237));
        }
        drawingLocation.fillRect(startPageSlideOrder2ButtonX, startPageSlideOrder2ButtonY, startPageSlideOrder2ButtonWidth, startPageSlideOrder2ButtonHeight);

        //outline of the button
        drawingLocation.setColor(new Color(0, 0, 0));
        if (startPageSlideOrder2ButtonSelected == true){
            drawingLocation.setColor(new Color(245, 197, 22));
        }
        drawingLocation.drawRect(startPageSlideOrder2ButtonX, startPageSlideOrder2ButtonY, startPageSlideOrder2ButtonWidth, startPageSlideOrder2ButtonHeight);

        //--text--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        
        //top text
        startPageSlideOrder2ButtonText = "Descending Wrongness";
        startPageSlideOrder2ButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageSlideOrder2ButtonText);
        startPageSlideOrder2ButtonTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageSlideOrder2ButtonTextX = startPageSlideOrder2ButtonX + (int) ((startPageSlideOrder2ButtonWidth-startPageSlideOrder2ButtonTextWidth)/2);
        startPageSlideOrder2ButtonTextY = startPageSlideOrder2ButtonY + startPageSlideOrder2ButtonTextHeight + (int) ((startPageSlideOrder2ButtonHeight-startPageSlideOrder2ButtonTextHeight)/2);;
        
        drawingLocation.drawString(startPageSlideOrder2ButtonText, startPageSlideOrder2ButtonTextX, startPageSlideOrder2ButtonTextY);

        setSlideOrder2ButtonStuff = true;
    }



    public void drawResetQuestionsTextIndication()
    {
        //--text--

        int startPageResetCompletedQuestionsButtonBottom = startPageResetCompletedQuestionsButtonY + startPageResetCompletedQuestionsButtonHeight;
        
        startPageResetQuestionsIndicationText = "The completed questions have been reset";
        startPageResetQuestionsIndicationTextWidth = drawingLocation.getFontMetrics().stringWidth(startPageResetQuestionsIndicationText);
        startPageResetQuestionsIndicationTextHeight = drawingLocation.getFontMetrics().getAscent();
        startPageResetQuestionsIndicationTextX = startPageMainBoxInnerX + (int) ((startPageMainBoxInnerWidth - startPageResetQuestionsIndicationTextWidth)/2);
        startPageResetQuestionsIndicationTextY = startPageTitleY + startPageTitleHeight +startPageResetQuestionsIndicationTextHeight +  sideSpacing;//startPageResetCompletedQuestionsButtonBottom+ (int) ((startPageLaunchQuizButtonY-startPageResetCompletedQuestionsButtonBottom)/2) + (int) (startPageResetQuestionsIndicationTextHeight/2);
        setResetQuestionsIndicationText = true;

        if (startPageResetQuestionsIndicationTextActivated == true){
            drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
            drawingLocation.setColor(new Color(0, 0, 128));
            drawingLocation.drawString(startPageResetQuestionsIndicationText, startPageResetQuestionsIndicationTextX, startPageResetQuestionsIndicationTextY);


            if (startPageResetQuestionsIndicationTextActivatedTick == 30){
                startPageResetQuestionsIndicationTextActivated = false;
                startPageResetQuestionsIndicationTextActivatedTick = 0;
            }
            else{
                startPageResetQuestionsIndicationTextActivatedTick++;
            }
        }

        
    }


    //---GETTERS AND SETTERS---

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode1Selected()
    {
        return startPageSlideMode1ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode2Selected()
    {
        return startPageSlideMode2ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode3Selected()
    {
        return startPageSlideMode3ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder1Selected()
    {
        return startPageSlideOrder1ButtonSelected;
    }

    /**
     * @return indicates whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder2Selected()
    {
        return startPageSlideOrder2ButtonSelected;
    }

    //--launch quiz button--

    /**
     * @return the x coordinate of the top left corner of the launch quiz button
     */
    public int getLaunchQuizButtonX()
    {
        return startPageLaunchQuizButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the launch quiz button
     */
    public int getLaunchQuizButtonY()
    {
        return startPageLaunchQuizButtonY;
    }

    /**
     * @return the width of the launch quiz button
     */
    public int getLaunchQuizButtonW()
    {
        return startPageLaunchQuizButtonWidth;
    }

    /**
     * @return the width of the launch quiz button
     */
    public int getLaunchQuizButtonH()
    {
        return startPageLaunchQuizButtonWidth;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setLaunchQuizButtonClicked(boolean newClicked)
    {
        startPageLaunchQuizButtonClicked = newClicked;
    }

    //--change file button--

    /**
     * @return the x coordinate of the top left corner of the change file button
     */
    public int getChangeFileButtonX()
    {
        return startPageChangeFileButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the change file button
     */
    public int getChangeFileButtonY()
    {
        return startPageChangeFileButtonY;
    }

    /**
     * @return the width of the change file button
     */
    public int getChangeFileButtonW()
    {
        return startPageChangeFileButtonWidth;
    }

    /**
     * @return the width of the change file button
     */
    public int getChangeFileButtonH()
    {
        return startPageChangeFileButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setChangeFileButtonClicked(boolean newClicked)
    {
        startPageChangeFileButtonClicked = newClicked;
    }

    //--reset completed questions button--

    /**
     * @return the x coordinate of the top left corner of the change file button
     */
    public int getResetCompletedQuestionsButtonX()
    {
        return startPageResetCompletedQuestionsButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the change file button
     */
    public int getResetCompletedQuestionsButtonY()
    {
        return startPageResetCompletedQuestionsButtonY;
    }

    /**
     * @return the width of the change file button
     */
    public int getResetCompletedQuestionsButtonW()
    {
        return startPageResetCompletedQuestionsButtonWidth;
    }

    /**
     * @return the width of the change file button
     */
    public int getResetCompletedQuestionsButtonH()
    {
        return startPageResetCompletedQuestionsButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setResetCompletedQuestionsButtonClicked(boolean newClicked)
    {
        startPageResetCompletedQuestionsButtonClicked = newClicked;
    }

    /**
     * @param newActivated the new value to determine whether the text is activated (true) or not (false)
     */
    public void setResetCompletedQuestionsIndicationTextActivated(boolean newActivated)
    {
        startPageResetQuestionsIndicationTextActivated = newActivated;
    }


    //--slide mode buttons--

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideMode1ButtonX()
    {
        return startPageSlideMode1ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideMode1ButtonY()
    {
        return startPageSlideMode1ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode1ButtonW()
    {
        return startPageSlideMode1ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode1ButtonH()
    {
        return startPageSlideMode1ButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideMode1ButtonClicked(boolean newClicked)
    {
        startPageSlideMode1ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideMode1ButtonSelected(boolean newSelected)
    {
        startPageSlideMode1ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode1ButtonSelected()
    {
        return startPageSlideMode1ButtonSelected;
    }


    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideMode2ButtonX()
    {
        return startPageSlideMode2ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideMode2ButtonY()
    {
        return startPageSlideMode2ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode2ButtonW()
    {
        return startPageSlideMode2ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode2ButtonH()
    {
        return startPageSlideMode2ButtonHeight;
    }

     /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideMode2ButtonClicked(boolean newClicked)
    {
        startPageSlideMode2ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideMode2ButtonSelected(boolean newSelected)
    {
        startPageSlideMode2ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode2ButtonSelected()
    {
        return startPageSlideMode2ButtonSelected;
    }

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideMode3ButtonX()
    {
        return startPageSlideMode3ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideMode3ButtonY()
    {
        return startPageSlideMode3ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode3ButtonW()
    {
        return startPageSlideMode3ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideMode3ButtonH()
    {
        return startPageSlideMode3ButtonHeight;
    }

     /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideMode3ButtonClicked(boolean newClicked)
    {
        startPageSlideMode3ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideMode3ButtonSelected(boolean newSelected)
    {
        startPageSlideMode3ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideMode3ButtonSelected()
    {
        return startPageSlideMode3ButtonSelected;
    }

    //--slide order buttons--

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideOrder1ButtonX()
    {
        return startPageSlideOrder1ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideOrder1ButtonY()
    {
        return startPageSlideOrder1ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder1ButtonW()
    {
        return startPageSlideOrder1ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder1ButtonH()
    {
        return startPageSlideOrder1ButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideOrder1ButtonClicked(boolean newClicked)
    {
        startPageSlideOrder1ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideOrder1ButtonSelected(boolean newSelected)
    {
        startPageSlideOrder1ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder1ButtonSelected()
    {
        return startPageSlideOrder1ButtonSelected;
    }

    /**
     * @return the x coordinate of the top left corner of the button
     */
    public int getSlideOrder2ButtonX()
    {
        return startPageSlideOrder2ButtonX;
    }

    /**
     * @return the y coordinate of the top left corner of the button
     */
    public int getSlideOrder2ButtonY()
    {
        return startPageSlideOrder2ButtonY;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder2ButtonW()
    {
        return startPageSlideOrder2ButtonWidth;
    }

    /**
     * @return the width of the button
     */
    public int getSlideOrder2ButtonH()
    {
        return startPageSlideOrder2ButtonHeight;
    }

    /**
     * @param newClicked the new value to determine whether the button is clicked (true) or not (false)
     */
    public void setSlideOrder2ButtonClicked(boolean newClicked)
    {
        startPageSlideOrder2ButtonClicked = newClicked;
    }

    /**
     * @param newSelected the new value to determine whether the button is selected (true) or not (false)
     */
    public void setSlideOrder2ButtonSelected(boolean newSelected)
    {
        startPageSlideOrder2ButtonSelected = newSelected;
    }

    /**
     * @return whether the button is selected (true) or not (false)
     */
    public boolean getSlideOrder2ButtonSelected()
    {
        return startPageSlideOrder2ButtonSelected;
    }
    
}