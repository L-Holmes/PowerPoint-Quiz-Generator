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
  * TO DO:
  * (just done launch quiz button so far)
  * -need to sort out how to add the code for the button clicks to the activateClickedProcedure for each button 
  * -polish up the rectangle stuff.
  * -implement the rectangle stuff.
  * -add comments / doc strings to everything
  *     --docstrings after click panel, and then add the docstring to the click panel class def. 
  * -sort out the file system & defualt files
  * -add a method that resets the instance variables for 2 click panel subclasses.
  * -add comments explaining what each instance variable does
  * -seperate variable type declarations: e.g. int x;  -- from variable initiations: e.g. x = 5;
  * -add the concurrency/threads for the loading of files, add a loading symbol (e.g. dots)
  *
  *
  * -look at some readme examples off of other github projects, and make mine match
  */


