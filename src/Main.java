/*
Requires pdf file;
-questions slides are labled with: '(Q)'  (excluding the speach marks & inside a text box)
-must have at least 1 question slide
-answer slides must occur directly after their corresponding question slide
-can only have 1 slide for each question 

slide mode acts as: OR operator

shortcuts reference:
  --quiz page
    t = text box
    § = exit text box
    (enterkey) = next question 
    (r) = green tick button 
    (w) = red x button
    ']' = move right in seen slides
    '[' = move left in seen slides
    (b) = back
    (/) = reset the text box text
  --start page
    c = change file
    r = reset 
    (enterkey) = launch
    [1] [2] [3] = toggle slide modes
    '[' = toggle order left
    ']' = toggle order right
*/

public class Main {
    public static void main(String[] args) throws Exception {

        WindowHandle application = new WindowHandle();
        application.run();
        
       
    }

}

/**
 * what I am currently doing:
 *  seperating click panel into 2 seperate pages- I have got the start page one to a non-crashing state
 *  need to do the same with the quiz page
 * 
 * I think I need to make click panel call getters and setter on the quiz page instead now.
 */
