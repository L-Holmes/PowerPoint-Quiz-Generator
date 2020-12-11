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
need to sort out the rest of the red stuff here

then sort out the red stuff in the click panel
*/


public class ClickPanelDrawQuizPage extends JPanel
{
    //graphics handler
    private Graphics2D drawingLocation;

    //window dimensions
    private int windowWidth;
    private int windowHeight;

    //mouse coordinates
    private int pointXCoord;
    private int pointYCoord;
    

    //loca
    String slidePDFLocation; //set once

    //think its the slide number within the powerpoint(?)
    int imagePageNumber; //needs updating

    //
    private ConvertPDFPagesToImages pdfHandler;
    boolean pdfHandlerSet; //needs updating

    //
    boolean slideImageUpdated = true; //needs updating

    //count indicating the number of questions that the user has completed in the current session
    int questionsComplete; //needs updating

    //indicates the number of the question that the user is currently looking at in the session
    int currentQuestion; //needs updating

    //indicates whether the user needs to click the 'correct' or 'incorrect' buttons
    boolean needToConfirmCorrectness; //needs updating

    //indicator text stuff 
	boolean mostRecentSlide; //needs updating
	boolean displayNoMoreSlidesText; //needs updating
	boolean firstSlide; //needs updating


    //---GENERAL PAGE DRAWING---
	
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
    int backPageButtonWidth;
    int backPageButtonHeight;
	int backPageButtonRight;
    int backPageButtonBottom;
    boolean backwardPageButtonClicked = false;

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
    int forwardPageButtonHeight;
    int forwardPageButtonWidth;
	int nextPageButtonRight;
    int nextPageButtonBottom;
    boolean forwardPageButtonClicked = false;

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
    int nextQuestionButtonWidth;
    int nextQuestionButtonHeight;
	int nextQuestionButtonRight;
	int nextQuestionButtonBottom;
	int stringWidth;
	int stringHeight;
	int nextQuestionButtonTextX;
    int nextQuestionButtonTextY;
    boolean nextQuestionClicked = false;

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
    boolean xButtonClicked = false;

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
    boolean tickButtonClicked = false;

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
    int backToMenuButtonWidth;
    int backToMenuButtonHeight;
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
							

    /**
     * initialisation
     * @param windowW = the width of the window (in pixels)
     * @param windowH = the height of the window (in pixels)
     */
    public ClickPanelDrawQuizPage(int windowW, int windowH)
    {
        windowWidth = windowW;
        windowHeight = windowH;
    }

    //REQUIRED METHODS FOR THE DRAWING

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

    //DRAWING QUIZ STUFF START

    public void drawQuiz(Graphics2D graphicsHandle, String slideFilePath, int mouseXCoord, int mouseYCoord)
    {

        //updating variables

        drawingLocation = graphicsHandle;
        slidePDFLocation = slideFilePath;
        pointXCoord = mouseXCoord;
        pointYCoord = mouseYCoord;

        //drawing


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
            drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 40)); 
            titleString = (ConvertPDFPagesToImages.getFileNameFromLocation(slidePDFLocation));
            titleTextWidth = drawingLocation.getFontMetrics().stringWidth(titleString);
            titleTextHeight = drawingLocation.getFontMetrics().getAscent();
            titleTextX = (int) (windowWidth*0.5) - (int) (titleTextWidth/2) ;
            titleTextY = ((int) ((float) (windowWidth)*0.02)) + (int) (titleTextHeight);
            setTitleDrawingStuff = true;//indicates that the above variables can be referenced
        }
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 40)); 
        drawingLocation.setColor(new Color(0, 0, 0));
        

        drawingLocation.drawString(titleString, titleTextX, titleTextY);

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
                drawingLocation.drawImage(resizedImg, imgTopLeftX, imgTopLeftY, this);
        
                //drawing outline 
                drawingLocation.setColor(new Color(0, 0, 0));
                drawingLocation.drawRect(imgTopLeftX, imgTopLeftY, slideWidth, slideHeight);
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
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        questionsCompleteTextWidth = drawingLocation.getFontMetrics().stringWidth(questionsCompleteFullString);
        questionsCompleteTextHeight = drawingLocation.getFontMetrics().getAscent();
        questionsCompleteTextX = windowWidth - questionsCompleteTextWidth - slideLeftX;
        questionsCompleteTextY = (int) (windowWidth*0.02);
        
        
        

        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));

        drawingLocation.drawString(questionsCompleteFullString, questionsCompleteTextX, questionsCompleteTextY);

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
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        currentQuestionTextWidth = drawingLocation.getFontMetrics().stringWidth(currentQuestionFullString);
        currentQuestionTextHeight = drawingLocation.getFontMetrics().getAscent();
        currentQuestionTextX = slideLeftX;
        currentQuestionTextY = slideTopY - currentQuestionTextHeight;
            

        
        drawingLocation.setColor(new Color(0, 0, 0));

        drawingLocation.drawString(currentQuestionFullString, currentQuestionTextX, currentQuestionTextY);

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

            backPageButtonWidth = buttonWidth;
            backPageButtonHeight = buttonHeight;
            backPageButtonX= slideLeftX +  (int) (slideWidth*0.25);
            backPageButtonY= slideBottom + (distSlideBottomToScreenBottom/2 - backPageButtonHeight/2);

            //changing back page button colour to be darker if it is hovered over
            backPageButtonRight = backPageButtonX + backPageButtonWidth;
            backPageButtonBottom = backPageButtonY + backPageButtonHeight;

            setBackPageButtonStuff = true; //indicates that the above variables can be referenced

        }
        
        if (backwardPageButtonClicked){
            //
            drawingLocation.setColor(new Color(190, 190, 190));
            backwardPageButtonClicked = false;
        }
        else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, backPageButtonX, backPageButtonRight, backPageButtonY, backPageButtonBottom) == true){
            //user is hovering over this button
            drawingLocation.setColor(new Color(175, 175, 175));
        }
        else{
            //
            drawingLocation.setColor(new Color(180, 180, 180));
        }

        //drawing the button
        drawingLocation.fillRect(backPageButtonX, backPageButtonY, backPageButtonWidth, backPageButtonHeight);

        //drawing outline 
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(backPageButtonX, backPageButtonY, backPageButtonWidth, backPageButtonHeight);
        //

    }

    public void drawArrowOnBackPageButton()
    {
        if (setBackPageButtonArrowStuff == false){
            //
            backPageArrowCentreX = (int) (backPageButtonX + (backPageButtonWidth / 2));
            backPageArrowCentreY = (int) (backPageButtonY + (backPageButtonHeight / 2));
            
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
        

        drawingLocation.setColor(new Color(0, 0, 0));
        //making the line thicker
        drawingLocation.setStroke(new BasicStroke(5));
        drawingLocation.drawLine(backPageArrowTopStartX, backPageArrowTopStartY, backPageArrowTopEndX, backPageArrowTopEndY);

        
        drawingLocation.setColor(new Color(0, 0, 0));
        //making the line thicker
        drawingLocation.setStroke(new BasicStroke(5));
        drawingLocation.drawLine(backPageArrowBottomStartX, backPageArrowBottomStartY, backPageArrowBottomEndX, backPageArrowBottomEndY);

        drawingLocation.setStroke(new BasicStroke(1));//resets the stroke back to default


        
    }

    public void drawForwardPageButton()
    {
        if (setForwardPageButtonStuff == false){
            //
            forwardPageButtonWidth = buttonWidth;
            forwardPageButtonHeight = buttonHeight;
            forwardPageButtonX=slideLeftX + (int) (slideWidth*0.25) + forwardPageButtonWidth + buttonSpacing;
            forwardPageButtonY=slideBottom + (distSlideBottomToScreenBottom/2 - forwardPageButtonHeight/2);

            //changing next page button colour to be darker if it is hovered over
            nextPageButtonRight = forwardPageButtonX + forwardPageButtonWidth;
            nextPageButtonBottom = forwardPageButtonY + forwardPageButtonHeight;

            setForwardPageButtonStuff = true;//indicates that the above variables can be referenced

        }
        
        if (forwardPageButtonClicked){
            //
            drawingLocation.setColor(new Color(190, 190, 190));
            forwardPageButtonClicked = false;
        }
        else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, forwardPageButtonX, nextPageButtonRight, forwardPageButtonY, nextPageButtonBottom) == true){
            //user is hovering over this button
            drawingLocation.setColor(new Color(175, 175, 175));
        }
        else{
            //
            drawingLocation.setColor(new Color(180, 180, 180));
        }

        drawingLocation.fillRect(forwardPageButtonX, forwardPageButtonY, forwardPageButtonWidth, forwardPageButtonHeight);

        //drawing outline 
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(forwardPageButtonX, forwardPageButtonY, forwardPageButtonWidth, forwardPageButtonHeight);
        //

    }

    public void drawArrowOnForwardPageButton()
    {
        if (setForwardPageButtonArrowStuff == false){
            forwardPageArrowCentreX = (int) (forwardPageButtonX + (forwardPageButtonWidth / 2));
            forwardPageArrowCentreY = (int) (forwardPageButtonY + (forwardPageButtonHeight / 2));
            
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
        

        drawingLocation.setColor(new Color(0, 0, 0));
        //making the line thicker
        drawingLocation.setStroke(new BasicStroke(5));
        drawingLocation.drawLine(forwardPageArrowTopStartX, forwardPageArrowTopStartY, forwardPageArrowTopEndX, forwardPageArrowTopEndY);

        

        drawingLocation.setColor(new Color(0, 0, 0));
        //making the line thicker
        drawingLocation.setStroke(new BasicStroke(5));
        drawingLocation.drawLine(forwardPageArrowBottomStartX, forwardPageArrowBottomStartY, forwardPageArrowBottomEndX, forwardPageArrowBottomEndY);

        drawingLocation.setStroke(new BasicStroke(1));//resets the stroke back to default
        
    }

    public void drawNextQuestionButton()
    {
        
        //https://stackoverflow.com/questions/22628357/how-to-write-text-inside-a-rectangle
        if (setNextQuestionButtonStuff == false){
            //
            nextQuestionButtonWidth = buttonWidth;
            nextQuestionButtonHeight = buttonHeight;

            nextQuestionButtonX= slideLeftX + (int) (slideWidth*0.25) + nextQuestionButtonWidth + buttonSpacing + nextQuestionButtonWidth + buttonSpacing;
            nextQuestionButtonY=slideBottom + (distSlideBottomToScreenBottom/2 - nextQuestionButtonHeight/2);

            //changing next question button colour to be darker if it is hovered over
            nextQuestionButtonRight = nextQuestionButtonX + nextQuestionButtonWidth;
            nextQuestionButtonBottom = nextQuestionButtonY + nextQuestionButtonHeight;

            drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
            stringWidth = drawingLocation.getFontMetrics().stringWidth("Next");
            stringHeight = drawingLocation.getFontMetrics().getAscent();

            nextQuestionButtonTextX = nextQuestionButtonX  + ((nextQuestionButtonWidth-stringWidth)/2);
            nextQuestionButtonTextY = nextQuestionButtonY + stringHeight + (((nextQuestionButtonHeight-stringHeight)/2));

            setNextQuestionButtonStuff = true;//indicates that the above variables can be referenced

        }
                    
        
        if (nextQuestionClicked){
            drawingLocation.setColor(new Color(190, 190, 190));
            nextQuestionClicked = false;
        }
        else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, nextQuestionButtonX, nextQuestionButtonRight, nextQuestionButtonY, nextQuestionButtonBottom) == true){
            //user is hovering over this button
            drawingLocation.setColor(new Color(175, 175, 175));
        }
        else{
            //
            drawingLocation.setColor(new Color(180, 180, 180));
        }

        //box
        drawingLocation.fillRect(nextQuestionButtonX, nextQuestionButtonY, nextQuestionButtonWidth, nextQuestionButtonHeight);

        //drawing outline 
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(nextQuestionButtonX, nextQuestionButtonY, nextQuestionButtonWidth, nextQuestionButtonHeight);
        //

        //text
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        
        drawingLocation.setColor(new Color(0, 0, 0));

        drawingLocation.drawString("Next", nextQuestionButtonTextX, nextQuestionButtonTextY);

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

                drawingLocation.setColor(new Color(245, 197, 22));
                drawingLocation.fillRect(redXOutlineX, redXOutlineY, redXOutlineW, redXOutlineH);
            }

            //drawing to the screen
            drawingLocation.drawImage(redXBimgResized, redXButtonX, redXButtonY, this);

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

                drawingLocation.setColor(new Color(245, 197, 22));
                drawingLocation.fillRect(greenTickOutlineX, greenTickOutlineY, greenTickOutlineW, greenTickOutlineH);
            }

            //drawing to the screen
            drawingLocation.drawImage(greenTickBimgResized, greenTickButtonX, greenTickButtonY, this);


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
                    drawingLocation.drawImage(doneTickBimgResized, greenTickButtonX, greenTickButtonY, this);

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

        errorTextStringWidth = drawingLocation.getFontMetrics().stringWidth(errorTextString);
        errorTextStringHeight = drawingLocation.getFontMetrics().getAscent();
        errorTextStringX = slideLeftX;
        errorTextStringY = slideBottom + errorTextStringHeight*2;

        if ((firstSlide== true) || (mostRecentSlide== true) || (displayNoMoreSlidesText== true))
        {
            //
            drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
            drawingLocation.setColor(new Color(180, 10, 10));
            drawingLocation.drawString(errorTextString, errorTextStringX, errorTextStringY);
            
        }
        
        setErrorTextStuff = true;

    }

    public void drawBackToMenuButton()
    {
        if (setBackToMenuButtonStuff == false){
            //
            backToMenuButtonX= (int) (windowWidth*0.02);
            backToMenuButtonY= (int) (windowHeight*0.02);
            backToMenuButtonWidth = buttonWidth;
            backToMenuButtonHeight = backToMenuButtonHeight;

            //changing back page button colour to be darker if it is hovered over
            backToMenuButtonRight = backToMenuButtonX + backToMenuButtonWidth;
            backToMenuButtonBottom = backToMenuButtonY + backToMenuButtonHeight;

            //--drawing the text on the button--
            drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
            backToMenuButtonText = "Back";
            backToMenuButtonTextWidth = drawingLocation.getFontMetrics().stringWidth(backToMenuButtonText);;
            backToMenuButtonTextHeight = drawingLocation.getFontMetrics().getAscent();;
            backToMenuButtonTextX = backToMenuButtonX + (int) ((backToMenuButtonWidth - backToMenuButtonTextWidth)/2);
            backToMenuButtonTextY = backToMenuButtonY + (int) ((backToMenuButtonHeight - backToMenuButtonTextHeight)/2) + backToMenuButtonTextHeight;

            setBackToMenuButtonStuff = true; //indicates that the above variables can be referenced

        }
        
        if (backToMenuButtonClicked){
            //
            drawingLocation.setColor(new Color(190, 190, 190));
            backToMenuButtonClicked = false;
        }
        else if (isPointCollisionWithRectangle(pointXCoord, pointYCoord, backToMenuButtonX, backToMenuButtonRight, backToMenuButtonY, backToMenuButtonBottom) == true){
            //user is hovering over this button
            drawingLocation.setColor(new Color(175, 175, 175));
        }
        else{
            //
            drawingLocation.setColor(new Color(180, 180, 180));
        }

        //drawing the button
        drawingLocation.fillRect(backToMenuButtonX, backToMenuButtonY, backToMenuButtonWidth, backToMenuButtonHeight);

        //drawing outline 
        drawingLocation.setColor(new Color(0, 0, 0));
        drawingLocation.drawRect(backToMenuButtonX, backToMenuButtonY, backToMenuButtonWidth, backToMenuButtonHeight);
        //

        //--drawing the text on the button--
        drawingLocation.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
        drawingLocation.setColor(new Color(0, 0, 0));

        drawingLocation.drawString(backToMenuButtonText, backToMenuButtonTextX, backToMenuButtonTextY);

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
            typingTextBox  = new TextBox(drawingLocation, textBoxX, textBoxY, textBoxWidth, textBoxHeight);
            setTextBoxStuff = true;
        }

        //draw the text box
        typingTextBox.drawTextBox(drawingLocation);
    }

    //---GETTERS---

    /**
     * @return the object of the text box that this page is using as its main text box
     */
    public TextBox getTypingTextBox()
    {
        return typingTextBox;
    }


    public int getTextBoxX()
    {
        return textBoxX;
    }


    public int getTextBoxY()
    {
        return textBoxY;
    }


    public int getTextBoxW()
    {
        return textBoxWidth;
    }

    /**
     * @return 
     */
    public int getTextBoxH()
    {
        return textBoxHeight;
    }


    /**
     * @return 
     */
    public int getBackToMenuButtonX()
    {
        return backToMenuButtonX;
    }

    public int getBackToMenuButtonY()
    {
        return backToMenuButtonY;
    }

    /**
     * @return 
     */
    public int getBackToMenuButtonW()
    {
        return backToMenuButtonWidth;
    }

    /**
     * @return 
     */
    public int getBackToMenuButtonH()
    {
        return backToMenuButtonHeight;
    }


    /**
     * @return 
     */
    public int getXButtonX()
    {
        return redXButtonX;
    }

    /**
     * @return 
     */
    public int getXButtonY()
    {
        return redXButtonY;
    }

    /**
     * @return 
     */
    public int getXButtonW()
    {
        return redXButtonWidth;
    }

    /**
     * @return 
     */
    public int getXButtonH()
    {
        return redXButtonHeight;
    }


    /**
     * @return 
     */
    public int getTickButtonX()
    {
        return greenTickButtonX;
    }

    /**
     * @return 
     */
    public int getTickButtonY()
    {
        return greenTickButtonY;
    }

    public int getTickButtonW()
    {
        return greenTickButtonWidth;
    }

    /**
     * @return 
     */
    public int getTickButtonH()
    {
        return greenTickButtonHeight;
    }


    /**
     * @return 
     */
    public int getBackwardPageButtonX()
    {
        return backPageButtonX;
    }

    /**
     * @return 
     */
    public int getBackwardPageButtonY()
    {
        return backPageButtonY;
    }

    /**
     * @return 
     */
    public int getBackwardPageButtonW()
    {
        return backPageButtonWidth;
    }

    /**
     * @return 
     */
    public int getBackwardPageButtonH()
    {
        return backPageButtonHeight;
    }


    /**
     * @return 
     */
    public int getForwardPageButtonX()
    {
        return forwardPageButtonX;
    }

    /**
     * @return 
     */
    public int getForwardPageButtonY()
    {
        return forwardPageButtonY;
    }

    /**
     * @return 
     */
    public int getForwardPageButtonW()
    {
        return forwardPageButtonWidth;
    }

    /**
     * @return 
     */
    public int getForwardPageButtonH()
    {
        return forwardPageButtonHeight;
    }


    /**
     * @return 
     */
    public int getNextQuestionButtonButtonX()
    {
        return nextQuestionButtonX;
    }

    /**
     * @return 
     */
    public int getNextQuestionButtonButtonY()
    {
        return nextQuestionButtonY;
    }

    /**
     * @return 
     */
    public int getNextQuestionButtonButtonW()
    {
        return nextQuestionButtonWidth;
    }

    /**
     * @return 
     */
    public int getNextQuestionButtonButtonH()
    {
        return nextQuestionButtonHeight;
    }

    //---SETTERS---
    /**
     * sets the x to the newClickedValue
     * @param newClickedValue = the new value for x
     */
    public void setNextQuestionButtonClicked(boolean newClickedValue)
    {
        nextQuestionClicked = newClickedValue;
    }
    /**
     * sets the x to the newClickedValue
     * @param newClickedValue = the new value for x
     */
    public void setForwardPageButtonClicked(boolean newClickedValue)
    {
        forwardPageButtonClicked = newClickedValue;
    }
    /**
     * sets the x to the newClickedValue
     * @param newClickedValue = the new value for x
     */
    public void setBackwardPageButtonClicked(boolean newClickedValue)
    {
        backwardPageButtonClicked = newClickedValue;
    }
    /**
     * sets the x to the newClickedValue
     * @param newClickedValue = the new value for x
     */
    public void setTickButtonClicked(boolean newClickedValue)
    {
        tickButtonClicked = newClickedValue;
    }
    /**
     * sets the x to the newClickedValue
     * @param newClickedValue = the new value for x
     */
    public void setXButtonClicked(boolean newClickedValue)
    {
        xButtonClicked = newClickedValue;
    }
    /**
     * sets the x to the newClickedValue
     * @param newClickedValue = the new value for x
     */
    public void setBackToMenuButtonClicked(boolean newClickedValue)
    {
        backToMenuButtonClicked = newClickedValue;
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
     * updates the pdfHandler to the new value
     * @param newPDFHandler = the new ConvertPDFPagesToImages object that is to be 
     *                        used by the ClickPanel
     */
    public void setPDFHandler(ConvertPDFPagesToImages newPDFHandler)
    {
        pdfHandler = newPDFHandler;
        if (newPDFHandler!=null){
            pdfHandlerSet = true;
        }
        else{
            pdfHandlerSet = false;
        }
        
    }


}