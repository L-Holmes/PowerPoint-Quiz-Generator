import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;


//source;; https://stackoverflow.com/questions/18189314/convert-a-pdf-file-to-image 
public class ConvertPDFPagesToImages {

    // Pdf files are read from this folder
    String sourceDir;

    //determines which questions are shown to the reader
    // can combine all 3, but need at least 1 to be true
    boolean slideModeUncompletedQuestions = true;    //continue with uncompleted questions
    boolean slideModeWrongQuestions = false;    //3 = complete questions that were wrong >= 1 time(s)
    boolean slideModeWrongLastTimeQuestions = false;     //4 = complete wrong last time questions
    
    //slide order selection (XOR)
    boolean slideChoiceRandomOrder = true; //[random order]
    boolean slideChoiceDescendingWrongness = false; // [descending wrong-ness] --but random for the questions with equal wrongness

    // user can move back to answers and question
    // but cannot move to a different question, before clicking
    // the tick or the 'X'
    // after a click, the next question will be visited automatically
    // a completed indicator will be added
    // need to add an error text to screen indicating this must be completed
    boolean rightWrongBlock = false;

    // converted images from pdf document are saved here
    String destinationDir = "/Users/lindonholmes/Documents/personal_projects/revision_tool/revision_software/src/";

    // extension of output slide image file
    String fileExtension = "png";

    // init stuff
    int[] slidesUnchecked;
    boolean slidesFound = false;

    File sourceFile;
    File destinationFile;
    PDDocument document;
    PDPageTree list;
    int totalNumPages;
    String fileName;
    PDFRenderer pdfRenderer;

    // change slide stuff
    int pageNumber = -1;
    int previousPageNumber = -1;
    BufferedImage bim;
    File outPutFile;
    String outputFileName;

    // initiation indicator
    boolean ppLoaded = false;

    // stacks and queue for seen questions
    Stack previousSeenStack = new Stack();
    Stack forwardSeenStack = new Stack();

    // questions completed
    int numQuestionsCompleted = 0;
    int currentQuestion = 0;
    int[] completedQuestions;

    //slides completed
    //int currentSlide;
    int[] completedSlides;
    int [] slidesDoneBefore;
    int nextAddSlideIndex = 0;

    //getting the number of attempts wrong stuff
    Map<Integer, Integer> slideNumWithAttemptsWrongMap = new HashMap<Integer, Integer>();
    Map<Integer, Boolean> currentlyCompletedMap = new HashMap<Integer, Boolean>();
    Map<Integer, Boolean> wrongLastTimeMap = new HashMap<Integer, Boolean>();

    // mother click panel
    ClickPanel motherClickPanel;

    //

    public ConvertPDFPagesToImages(ClickPanel linkedClickPanel) {
        // init
        motherClickPanel = linkedClickPanel;
        sourceDir = motherClickPanel.getPowerPointLocation();
        setSlideOptions();
        
        loadPowerPoint();

    }

    public void setSlideOptions()
    {
        slideModeUncompletedQuestions = motherClickPanel.getSlideMode1();   
        slideModeWrongQuestions = motherClickPanel.getSlideMode2();   
        slideModeWrongLastTimeQuestions = motherClickPanel.getSlideMode3();    
        slideChoiceRandomOrder = motherClickPanel.getSlideOrder1(); 
        slideChoiceDescendingWrongness = motherClickPanel.getSlideOrder2();
    }


    public void loadPowerPoint(){
        sourceFile = new File(sourceDir);
        destinationFile = new File(destinationDir);
        if (!destinationFile.exists()) {
            destinationFile.mkdir();
        }
        if (sourceFile.exists()) {
            // loading the pdf document
            try {
                document = PDDocument.load(sourceFile);
                // getting a list of all the pdf pages
                list = document.getDocumentCatalog().getPages();

                // number of pages in the pdf
                totalNumPages = list.getCount();
                completedSlides = new int[totalNumPages];
                completedQuestions = new int[totalNumPages];
                slidesDoneBefore = new int[totalNumPages];
                importCompletedSlides(totalNumPages);


                fileName = sourceFile.getName().replace(".pdf", "");
                pdfRenderer = new PDFRenderer(document);

                if (slidesFound == false) {
                    // create slides unchecked, array of numbers from 1 to num of pages
                    // list of all slide numbers not checked
                    slidesUnchecked = getPopulatedNumberArray(1, totalNumPages);
                    removeCompletedSlidesFromSlidesLeft();
                    slidesFound = true;
                }

                if (slideChoiceDescendingWrongness == true){
                    //change from the default random to desending wrongness order
                    changeSlidesUncheckedToAscendingWrongness();
                }


                outputFileName = destinationDir + "slideImage" + "." + fileExtension;
                outPutFile = new File(outputFileName);
                ppLoaded = true;

                //load all of the slides
                importAllSlides();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("couldn't open the source file");
            }

            
        } else {
            System.err.println(sourceFile.getName() + " File not exists");

        }

    }

    /**
     * converts all of the slides to seperate image files which 
     * can then be displayed within the application
     */
    public void importAllSlides()
    {
        String fileNameToCreate;
        File fileToExportTo;
        if (ppLoaded == true){
            //create images of all of the slides
            for (int pageIndex = 0; pageIndex<document.getNumberOfPages(); pageIndex++){
                fileNameToCreate = "images/slideImage" + (pageIndex+1)+".png";
                fileToExportTo = new File(fileNameToCreate);
                


                try {
                    bim = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);
                    ImageIO.write(bim, fileExtension, fileToExportTo);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("could not import all of the slides");
                }
            }

        }
    }


    public  int  changeSlide(String nextSlideOperation) {
        //
        if (ppLoaded == true) {
            //
            if (nextSlideOperation == "newQuestion") {
                System.out.println("getting the next question");
                getNextQuestion();

            } else if (nextSlideOperation == "back") {
                moveBackwardInSeenSlides();

            } else if (nextSlideOperation == "forward") {
                moveForewardInSeenSlides();
            } else {
                pageNumber = -1;
            }

            //setting (or not) the 'on first slide' text
            if (currentQuestion == 1){
                if (isQuestion(pageNumber, document) == true) {
                    motherClickPanel.setFirstSlideStatus(true);
                } else {

                    motherClickPanel.setFirstSlideStatus(false);
                }
    
            }
            else{
                motherClickPanel.setFirstSlideStatus(false);
            }
            //}}

            //updating (or not) the slide image
            if (previousPageNumber != pageNumber){
                //moved onto a new slide
                motherClickPanel.setJustChangedSlideForQuizPage(true);
            }
            //}}


            //updates the previous page number
            previousPageNumber = pageNumber;

            if ((0 < pageNumber) && (pageNumber <= document.getNumberOfPages())) {                
                return pageNumber;
            }
            else{
                System.out.println("page number: "+ pageNumber+" not in valid range");
                return -1;
            }
        }
        return -1;
    }

    /***
     * when the text question button is clicked this gets the slide of the next
     * available question, that has not been asked yet
     * 
     */
    public void getNextQuestion()
    {
        pageNumber = getNewQuestionSlide(document);
    }

    /**
     * shows the next slide in the queue of questions/answer slides that have
     * already been asked to the user
     * 
     */
    public void moveForewardInSeenSlides() {
        if (pageNumber != -1) {
            // first question has been displayed

            if (forwardSeenStack.size() > 0) {
                // check if next stack item is an answer
                int nextStackItem = (int) forwardSeenStack.peek();
                if (isQuestion(nextStackItem, document) == false) {
                    // retrieves and removes the top of the stack
                    previousSeenStack.push(pageNumber);
                    pageNumber = (int) forwardSeenStack.pop();

                } else {
                    // if next stack item is a question,try to get the next answer
                    int pageBeforeFunctionCall = pageNumber;
                    getNextAnswer();
                    if (pageBeforeFunctionCall == pageNumber) {
                        // no answers left, so move back onto the next question
                        // by popping off the forward stack
                        if (motherClickPanel.getCorrectness() == false) {
                            // but make sure the user has said their correctness first
                            previousSeenStack.push(pageNumber);
                            pageNumber = (int) forwardSeenStack.pop();

                            // since moved back onto the next question
                            handleCurrentQuestion(true);
                        }
                    } else {
                        // moved onto the next answer
                    }
                }

            } else {
                // are on the most resent answer/question,
                // nothing else left
                getNextAnswer();
            }
        }
        
    }

    public void getNextAnswer() {
        int nextSlide = pageNumber + 1;
        // check if next slide is within the boundaries
        if ((0 < nextSlide) && (nextSlide <= totalNumPages)) {
            // if it is a valid page number

            boolean questionStatus = isQuestion(nextSlide, document);
            if (questionStatus == false) {
                // is not a question (is answer)

                // check if this is the last answer for the current question
                int slideAfterThis = nextSlide + 1;
                if ((isQuestion(slideAfterThis, document) == true) || (slideAfterThis > totalNumPages)) {
                    // if it is, question completed
                    handleQuestionCompleted();
                }

                // adds the (previous) slide to previously seen
                previousSeenStack.push(pageNumber);

                // sets the new answer as the current page
                pageNumber = nextSlide;
                // removes this answer slide from the slides unchecked list
                slidesUnchecked = ArrayUtils.removeElement(slidesUnchecked, nextSlide);

            } else {
                // there is no answer to the current question
                // or there are no more answer slides left for
                // the given question
                motherClickPanel.setMostResentSlide(true);
            }
        } else {
            // the inputted slide number is not within the valid range
            // get a random slide number
            // that is a question

            motherClickPanel.setNoMoreSlideText(true);
        }
        
    }

    /**
     * shows the next slide in the queue of questions/answer slides that have
     * already been asked to the user
     * 
     */
    public void moveBackwardInSeenSlides() {
        if (pageNumber != -1) {
            // first question has been displayed

            // ensures that there are available previous slides to view
            if (previousSeenStack.size() > 0) {
                forwardSeenStack.push(pageNumber);

                int previousPageNumber = pageNumber;

                pageNumber = (int) previousSeenStack.pop();

                // checking whether to move the current question indicator back
                if (isQuestion(previousPageNumber, document) == true) {
                    // if you were on a question,
                    // but moved back
                    // then you have moved to the
                    // previous question
                    if (motherClickPanel.getCorrectness() == false) {
                        // but make sure the user has said their correctness first
                        handleCurrentQuestion(false);
                    } else {
                        // user needs to stay on the current question,
                        // so revert back to the prevoius slides.
                        previousSeenStack.push(pageNumber);
                        pageNumber = (int) forwardSeenStack.pop();
                    }

                }

            } else {
                // On first slide
                // motherClickPanel.setFirstSlideStatus(true);

            }
        }
    }

    /**
     * repeatedly chooses a random slide from those not checked, until it finds a
     * question slide then returns the question slide number
     * 
     */
    private int getNewQuestionSlide(PDDocument pDocument) {
        boolean foundNewQuestion = false;

        System.out.println("getting a new question slide");
        // get random number
        while (foundNewQuestion == false) {
            if (slidesUnchecked.length > 0) {
                int randIndex;
                if (slideChoiceRandomOrder == true) {
                    randIndex = getRandomNumber(0, slidesUnchecked.length - 1);
                } else if (slideChoiceDescendingWrongness == true) {
                    // need to change this to get descending wrongness
                    // start at top and move down until a question is found,
                    randIndex = 0;

                } else {
                    randIndex = getRandomNumber(0, slidesUnchecked.length - 1);
                }
                // use as index for slidesUnchecked
                int possibleQuestion = slidesUnchecked[randIndex];
                // remove that index from slidesUnchecked
                slidesUnchecked = remove(randIndex, slidesUnchecked);
                // check if that slide is a question
                boolean slideQuestionStatus = isQuestion(possibleQuestion, pDocument);
                // if question, return
                if (slideQuestionStatus == true) {
                    if (motherClickPanel.getCorrectness() == false) {
                        // but make sure the user has said their correctness first

                        // add question to seen slides stack
                        if (pageNumber != -1) {
                            previousSeenStack.push(pageNumber);
                        }
                        // increments the current question display
                        handleCurrentQuestion(true);

                        // if the fwd stack is not empty
                        // add the contents of the forward stack to the previous queue
                        int stackSize = forwardSeenStack.size();
                        if (stackSize > 0) {
                            for (int fwdStackIndex = 0; fwdStackIndex < stackSize; fwdStackIndex++) {
                                previousSeenStack.push(forwardSeenStack.pop());
                            }
                        }

                        System.out.println("returning (1) "+ possibleQuestion);
                        // returns the new question
                        return possibleQuestion;
                    }
                }
                // else, start again
            } else {
                // all slides have been looked at
                motherClickPanel.setNoMoreSlideText(true);
                System.out.println("returning (2) "+ pageNumber);
                return pageNumber;
            }
        }
        System.out.println("returning (3) "+ pageNumber);
        return pageNumber;
    }

    private void changeSlidesUncheckedToAscendingWrongness()
    {
        
        SortTree myNumbersTree = new SortTree();
        
        for (int index = 0; index < slidesUnchecked.length; index++)
        {
            
            
            if (slideNumWithAttemptsWrongMap.get(slidesUnchecked[index]) != null){
                //
                myNumbersTree.addNode(slidesUnchecked[index], slideNumWithAttemptsWrongMap.get(slidesUnchecked[index]));
            }
            else{
                //
                myNumbersTree.addNode(slidesUnchecked[index], 0);
            }
        }

        slidesUnchecked = myNumbersTree.getIdentifierArraySortedWeightDescending();
        
        
    }

    

    /**
     * called once a question and its answers have been viewed keeps track of number
     * of questions completed
     */
    public void handleQuestionCompleted() {
        rightWrongBlock = true;
        motherClickPanel.setNeedToConfirmCorrectness(true);

        
        
    }

    /**
     * updates the current question number
     */
    public void handleCurrentQuestion(boolean increment) {
               
        if (increment == true) {
            currentQuestion++;
        } else {
            currentQuestion--;
        } 

        motherClickPanel.setCurrentQuestion(currentQuestion);
       
            

    }

    public void handleGreenTickClicked() {
        if (motherClickPanel.getCorrectness() == true){
            rightWrongBlock = false;
            motherClickPanel.setNeedToConfirmCorrectness(false);

            // handling question complete
            numQuestionsCompleted++;
            completedQuestions[numQuestionsCompleted - 1] = currentQuestion;
            motherClickPanel.incrementQuestionsCompleted();
            updateCompletedSlidesArray(false);
        }
    }

    public void handleRedXClicked() {
        if (motherClickPanel.getCorrectness() == true){
            //
            rightWrongBlock = false;
            motherClickPanel.setNeedToConfirmCorrectness(false);

            // handling question complete
            numQuestionsCompleted++;
            completedQuestions[numQuestionsCompleted - 1] = currentQuestion;
            motherClickPanel.incrementQuestionsCompleted();
            updateCompletedSlidesArray(true);
        }
        
    }

    /*
    if a question is complete
    call a function that gets the page number, 
    finds the page number of the question,
    finds all answers associated with that question,
    adds all of those page numbers to that completedSlides array
    */
    public void updateCompletedSlidesArray(boolean incorrect)
    {

        //pageNumber = the answer of the current question

        int checkSlideNumForwards = pageNumber;
        int checkSlideNumBackwards = pageNumber;
        
        Stack foundSlides = new Stack();
        // until question is found add 1 to page number
        // adds the current answer and all answers after this
        while ((isQuestion(checkSlideNumForwards, document) == false) && (checkSlideNumForwards <= totalNumPages)) {
            foundSlides.push(checkSlideNumForwards);
            checkSlideNumForwards++;
        }

        // adds any previous answers and the question
        boolean foundQuestion = false;
        while (foundQuestion == false) {
            checkSlideNumBackwards--;
            foundSlides.push(checkSlideNumBackwards);
            if ((isQuestion(checkSlideNumBackwards, document) == true) || (checkSlideNumBackwards < 1)) {
                foundQuestion = true;
            }
        }

        // adds all of the found page numbers to the completed slides array
        int stackSize = foundSlides.size();
        if (stackSize > 0) {
            for (int fwdStackIndex = 0; fwdStackIndex < stackSize; fwdStackIndex++) {
                int currentValueFromTheFound = (int) foundSlides.pop();
                boolean alreadyCompletedThisSlide = isInCompletedSlides(currentValueFromTheFound);

                if (alreadyCompletedThisSlide == false) {
                    //
                    completedSlides[nextAddSlideIndex] = currentValueFromTheFound;
                    // might have the see wrong questions turned on, so need to check
                    if (slideNumWithAttemptsWrongMap.containsKey(currentValueFromTheFound) == false) {
                        // if it doesn't contain it, then set the value to zero
                        slideNumWithAttemptsWrongMap.put(currentValueFromTheFound, 0);
                    }
                    currentlyCompletedMap.put(currentValueFromTheFound, true);
                    wrongLastTimeMap.put(currentValueFromTheFound, incorrect);
                    nextAddSlideIndex++;
                } else {
                    // slide already completed
                    currentlyCompletedMap.put(currentValueFromTheFound, true);
                    wrongLastTimeMap.put(currentValueFromTheFound, incorrect);
                }

            }
        }

        
        
        
    }

    /**
     * checks if the given slide number is a question slide returns true if it is a
     * question slide returns false if not a question slide
     * 
     * @param currentSlide
     * @param pDoc
     * @return
     */
    private boolean isQuestion(int currentSlide, PDDocument pDoc)  {
        //
        try{
            PDFTextStripper reader = new PDFTextStripper();
            reader.setStartPage(currentSlide);
            reader.setEndPage(currentSlide);

            // aquiring all of the text on screen
            String pageText = reader.getText(pDoc);

            if (pageText.contains("(Q)")) {
                // next page is a question
                // need to get a new slide
                // that is not the current slide
                // that is a question
                return true;
            } else {
                // next page is an answer (or not a question)
                return false;
            }
        }
        catch(IOException e){
            System.out.println("io error when checking if question");
            return false;
        }
        
    }

    /**
     * removes the given index returns the same array, minus the removed index
     * 
     * @param index
     * @param arr
     * @return
     */
    private int[] remove(int index, int[] arr) {
        int[] newArr = new int[arr.length - 1];
        if (index < 0 || index > arr.length) {
            return arr;
        }
        boolean foundIndex = false;
        for (int position = 0; position < arr.length; position++) {
            if (foundIndex == false) {
                //
                if (position == index) {
                    foundIndex = true;
                } else {
                    //
                    newArr[position] = arr[position];
                }

            } else {
                newArr[position - 1] = arr[position];
            }

        }

        return newArr;
    }

    /**
     * returns an integer array containing all numbers from min to max (increments
     * of 1 between numbers) (including min and max)
     * 
     * @param min
     * @param max
     * @return
     */
    private int[] getPopulatedNumberArray(int min, int max) {
        int[] a;
        int diff;
        if (max > min) {
            //
            diff = Math.abs(max - min);
            a = new int[diff + 1];
            for (int i = 0; i <= diff; ++i) {
                a[i] = min + i;
                int tempNum = min + i;
            }
        } else {
            // invalid
            a = new int[1];
            a[0] = 0;
        }

        return a;
    }

    

    /**
     * returns a random number from min to max (including min and max as possible
     * outputs) not sure what happens with negatives
     */
    private int getRandomNumber(int min, int max) {
        Random rand = new Random();
        int out = rand.nextInt((max - min) + 1) + min;
        return out;
    }

    /**
     * closes the document needs to be called before exiting
     */
    public void closeDocument() {
        try {
            document.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("could not close the document");
        }
    }

    /**
     * check if the slide is in the complete list, if so return true
     */
    public boolean isComplete(int checkComplete) {
        // https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        // check if it is in the complete list, if so return true
        boolean contains = false;
        try{
            contains = IntStream.of(completedQuestions).anyMatch(x -> x == checkComplete);
        }
        catch(Exception e){
            System.out.println("int stream failed");
        }
        if (contains == true) {
            //
            return true;
        }
        return false;
    }

    

    // need to validate this
    public static String getFileNameFromLocation(String location) {
        int lastSlash = location.lastIndexOf("/") + 1;
        String fullFilename = location.substring(lastSlash);
        int lastDotIndex = fullFilename.lastIndexOf('.');
        String fileName = fullFilename.substring(0, lastDotIndex);
        return fileName;
    }

    /**
     * updates the completed slides array
     * to hold any previously completed slides 
     * numbers
     * @param numSlides
     */
    public void importCompletedSlides(int numSlides) {
        // check if the completed questions text file for this
        String textFilename = getFileNameFromLocation(sourceDir);
        String filenameAsTextFile = textFilename + "_completed_questions_array.txt";

        File textfileToRead = new File(filenameAsTextFile);

        if(textfileToRead.exists() && !textfileToRead.isDirectory()) { 
            //do stuff
            // if it does, import the data and copy to the completed slides array
            readCompletedSlidesArrayFromThisTextFile(textfileToRead, numSlides);

        }
        
        
    }

    public void exportCompletedSlides(boolean clearFirst) {
        //
        
        if (completedSlides.length > 0) {
            

            // check if text file exists
            // if it does, write whilst keeping original contents
            // if not, create one and write

            String textFilename = getFileNameFromLocation(sourceDir);
            

            if (clearFirst == true) {
                writeIntArrayToTextFile(textFilename, completedSlides);
            } else {
                addIntArrayToTextFile(textFilename, completedSlides);
            }
        }
    }

    // https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
    // 15/07/2020
    public void writeIntArrayToTextFile(String filename, int[] intArray) {
        String filenameAsTextFile = filename + "_completed_questions_array.txt";
        BufferedWriter fileWriter = null;
        FileWriter fileWriteHandle;
        String lineToWrite;

        
        
        try {
            

            fileWriteHandle = new FileWriter(filenameAsTextFile);
            fileWriter = new BufferedWriter(fileWriteHandle);
            

            for (int i = 0; i < intArray.length; i++) {
                // or
                


                lineToWrite = getSlideNumAndIncorrectCountString(intArray[i]);
                fileWriter.write(lineToWrite);
                fileWriter.newLine();
            }
            fileWriter.flush();
            fileWriter.close();
            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("could not write int array to file");
        }

    }

    // https://stackoverflow.com/questions/13707223/how-to-write-an-array-to-a-file-java
    // 15/07/2020
    public void addIntArrayToTextFile(String filename, int[] intArray) {
        String filenameAsTextFile = filename + "_completed_questions_array.txt";
        String lineToWrite;

        
        try (FileWriter fw = new FileWriter(filenameAsTextFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter fileWriter = new PrintWriter(bw)) {
            for (int i = 0; i < intArray.length; i++) {
                lineToWrite = getSlideNumAndIncorrectCountString(intArray[i]);
                fileWriter.println(lineToWrite);
            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // exception handling left as an exercise for the reader
        }
    }

    // https://stackoverflow.com/questions/29878237/java-how-to-clear-a-text-file-without-deleting-it/42282671
    // 15/07/2020
    public void clearThisTextFile() {
        String textFilename = getFileNameFromLocation(sourceDir);
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
            System.out.println("could not clear the text file");
        }

    }

    
    /**
     * opens the file containing the questions that have been completed gets each
     * integer (represting the slide number of a question that has been completed)
     * adds to the completedSlides array
     */
    public void readCompletedSlidesArrayFromThisTextFile(File textFileToRead, int totalNumberOfSlides) {

        // do something
        Scanner scanner;
        double nextDouble;
        int entriesFound = 0;
        int slidesDoneFound = 0;

        int currentSlideNumber;
        int currentQuestionTimesIncorrect;
        boolean currentlyCompleted;
        boolean wrongLastTime;
        try {            
            try (BufferedReader br = new BufferedReader(new FileReader(textFileToRead))) {
                String line;
                String[] splitLine;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    splitLine = line.split(",");
                    if (splitLine.length == 4){
                        //
                        currentSlideNumber = Integer.parseInt(splitLine[0]);
                        currentQuestionTimesIncorrect = Integer.parseInt(splitLine[1]);
                        currentlyCompleted = Boolean.parseBoolean(splitLine[2]);
                        wrongLastTime = Boolean.parseBoolean(splitLine[3]);
                    }
                    else{
                        //if there are not 4 values
                        currentSlideNumber = 0;
                        currentQuestionTimesIncorrect = 0;
                        currentlyCompleted = false;
                        wrongLastTime = false;
                    }

                    //choosing whether to remove the slide or not 
                    
                    //
                    boolean removeQuestionFromUncompletedSlides = true;
                    if (slideModeUncompletedQuestions == true){
                        //want to remove question if currentlyCompleted == true
                        if (currentlyCompleted == false){
                            //dont from uncompleted slides
                            //since this question has not been completed yet
                            removeQuestionFromUncompletedSlides = false;
                        }
                    }
                    if (slideModeWrongQuestions == true){
                        //want to remove question if currentQuestionTimesIncorrect <= 0
                        if (currentQuestionTimesIncorrect > 0){
                            //want to not remove from uncompleted slides
                            //so that the user will get questions
                            //that they have got wrong
                            removeQuestionFromUncompletedSlides = false;
                        }
                    }
                    if (slideModeWrongLastTimeQuestions == true){
                        //want to remove question if wrongLastTime == false
                        if (wrongLastTime == true){
                            //don't remove this slide
                            //since the user got it wrong on their last attempt
                            removeQuestionFromUncompletedSlides = false;
                        }
                    }
                    
                    if (removeQuestionFromUncompletedSlides == true){
                        //
                        slidesDoneBefore[slidesDoneFound] = currentSlideNumber;
                        slidesDoneFound++;  
                        /////////
                    }
                    
                    //
                    completedSlides[entriesFound] = currentSlideNumber;
                    entriesFound++;
                    if (currentSlideNumber != 0){
                        //
                        nextAddSlideIndex++;//makes sure old (fetched here) values aren't overwritten when new questions are completed                
                    }
                    // now need to add to the wrong attempts map
                    slideNumWithAttemptsWrongMap.put(currentSlideNumber, currentQuestionTimesIncorrect);
                    //now need to add to the currently completed map
                    currentlyCompletedMap.put(currentSlideNumber, currentlyCompleted);
                    //now need to add to the wrong last time map
                    wrongLastTimeMap.put(currentSlideNumber, wrongLastTime);
                    
                }
                br.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("could not read the completed slides array");
            }

        } 
        finally{
            //
        } 

        
      

    }

    /**
     * removes all of the slides that have been found in the completedSlides array
     * (fetched from the completed questions text file) from the slidesUnchecked
     * array, which prevents completed questions from being shown again to the user
     */
    public void removeCompletedSlidesFromSlidesLeft() {
        // slidesUnchecked
        // completedSlides
        int indexInSlidesUnchecked;

        // for each item in completed slides:
        for (int i = 0; i < slidesDoneBefore.length; i++) {
            // check if slidesUnchecked contains it
            if (arrayContains(slidesUnchecked, slidesDoneBefore[i])) {
                //check if the question was completed last time
                if (currentlyCompletedMap.get(slidesDoneBefore[i]) == true){
                    // search for the item and remove it
                    slidesUnchecked = ArrayUtils.removeElement(slidesUnchecked, slidesDoneBefore[i]);
                }
                
            }
        }
    }

    // https://stackoverflow.com/questions/12020361/java-simplified-check-if-int-array-contains-int
    // 16/07/2020
    public static boolean arrayContains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    // STUFF FOR SWITCHING TO RECORDING ATTEMPTS INCORRECT

    /*
     * --do bottom thing first at end, when exporting
     * 
     * actally seperate with a dot, and store as a double
     * 
     * when user imports, remove and replace items that are already there add
     * incorrect slides to an incorrect slides stack when exporting; check if in the
     * imported questions and dashes array, if so, get the dash value if the user
     * got it wrong again, add 1 to the value write back into the text file
     * 
     */

    private String[] getPopulatedNumberArrayWithDashAttemptsIncorrect(int min, int max) {
        String[] a;
        int diff;
        if (max > min) {
            //
            diff = Math.abs(max - min);
            a = new String[diff + 1];
            for (int i = 0; i <= diff; ++i) {
                a[i] = String.valueOf(min + i) + "-0";
                int tempNum = min + i;
            }
        } else {
            // invalid
            a = new String[1];
            a[0] = "0-0";
        }

        return a;
    }

    /**
     * returns the page number by removing the part of the string (after the dash)
     * that indicates the number of attempts that the user got this slide incorrect
     * 
     * @param value
     * @return
     */
    private int getNumberFromSlidesUnchecked(String value) {
        int lastDash = value.lastIndexOf("-") + 1;
        String stringInteger = value.substring(0, lastDash);
        int foundInteger;
        try {
            foundInteger = Integer.parseInt(stringInteger);
        } catch (NumberFormatException e) {
            foundInteger = 0;
        }
        return foundInteger;
    }

    /**
     * removes the given index returns the same array, minus the removed index
     * 
     * @param index
     * @param arr
     * @return
     */
    private String[] removeFromStringArray(int index, String[] arr) {
        String[] newArr = new String[arr.length - 1];
        if (index < 0 || index > arr.length) {
            return arr;
        }
        boolean foundIndex = false;
        for (int position = 0; position < arr.length; position++) {
            if (foundIndex == false) {
                //
                if (position == index) {
                    foundIndex = true;
                } else {
                    //
                    newArr[position] = arr[position];
                }

            } else {
                newArr[position - 1] = arr[position];
            }

        }
        return newArr;
    }

    

    public String getSlideNumAndIncorrectCountString(int slideNumber)
    {
        //
        //then convert the slideNum to string and concatenate it with the previous attempts wrong converted to string(with a decimal point between)
        //
        

        String slideNumberAsString = String.valueOf(slideNumber);
        
        String incorrectCount = String.valueOf(getNumTimesQuestionFailed(slideNumber));
        
        String currentlyCompletedString = String.valueOf(getCurrentlyCompleted(slideNumber));
        
        String gotWrongString = String.valueOf(getWrongLastTime(slideNumber));
        
        String slideNumAndIncorrectCount = slideNumberAsString + "," + incorrectCount + "," + currentlyCompletedString  + "," + gotWrongString;
        return slideNumAndIncorrectCount;

    }

    /**
     * assumes that this question was completed
     * @param slideNumber
     * @return
     */
    private int getNumTimesQuestionFailed(int slideNumber)
    {
        
        //check whether the user got his question right or wrong 
        boolean wrong;
        int previousAttemptsWrong = -1;
        //check if slide number is in the completedSlides array
        //isInCompletedSlides(slideNumber) == true
        if (slideNumWithAttemptsWrongMap.containsKey(slideNumber)){
            //if it is: use the slide num as the key for: slideNumWithAttemptsWrongMap to get the previous attempts wrong
            wrong = wrongLastTimeMap.get(slideNumber);
            

            if (slideNumber != 0){
                
                previousAttemptsWrong = slideNumWithAttemptsWrongMap.get(slideNumber);
                
                //---if user got it wrong: +1 to preveious attempts wrong; else do nothing
                if (wrong == true){
                    previousAttemptsWrong = previousAttemptsWrong + 1;

                }
                
            }
            else{
                //one of the slots where the question hasn't been answered
                previousAttemptsWrong = 0;
            }
                    
        }
        else{
            //if it isnt: set the previous attempts wrong to 0
            previousAttemptsWrong = 0;
        }
        
        return previousAttemptsWrong;
    }

    private boolean getCurrentlyCompleted(int slideNumber)
    {
        boolean currentlyCompletedIndication;
        //check if slide number is in the completedSlides array
        //isInCompletedSlides(slideNumber) == true
        if (currentlyCompletedMap.containsKey(slideNumber)){
            if (slideNumber != 0){
                currentlyCompletedIndication = currentlyCompletedMap.get(slideNumber);                
            }
            else{
                //one of the slots where the question hasn't been answered
                currentlyCompletedIndication = false;
            }
            
        }
        else{
            //if it isnt: set the previous attempts wrong to 0
            currentlyCompletedIndication = false; 
        }
        return currentlyCompletedIndication;
    }

    private boolean getWrongLastTime(int slideNumber)
    {
        boolean wrongLastTimeIndication;
        //check if slide number is in the completedSlides array
        //isInCompletedSlides(slideNumber) == true
        if (wrongLastTimeMap.containsKey(slideNumber)){
            if (slideNumber != 0){
                wrongLastTimeIndication = wrongLastTimeMap.get(slideNumber);
                
            }
            else{
                //one of the slots where the question hasn't been answered
                wrongLastTimeIndication = false;
            }
            
        }
        else{
            //if it isnt: set the previous attempts wrong to 0
            wrongLastTimeIndication = false;
        }
        return wrongLastTimeIndication;
        
    }

    

    /**
     * check if it is in the completeSlides array, if so return true
     */
    public boolean isInCompletedSlides(int checkComplete) {
        // https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        // check if it is in the complete list, if so return true
        boolean contains = IntStream.of(completedSlides).anyMatch(x -> x == checkComplete);
        if (contains == true) {
            //
            return true;
        }
        return false;
    }
   

}