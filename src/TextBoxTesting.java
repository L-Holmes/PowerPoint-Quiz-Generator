import javax.swing.*;//used for gui generation


import java.awt.*;//used for layout managers
//used to get the coordinates of the mouse click
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;//used for gui generation


import java.awt.image.*;
import java.io.File;
import java.io.IOException;


/*
click detection issues

I think that trying to click onto a formatted line does not work.

also formatting not updating properly but I am not sure of the context behind this
*/

/*
this is the line|

this is the next
*/

public class TextBoxTesting {
    public static void main(String[] args){
		//
		System.out.println("{{{ starting application }}} \n");
        TestingWindowHandle application = new TestingWindowHandle();
		application.run();
		System.out.println("\n.. application ended ..");
    }
}





/**
 * This class provides a simple window in which grahical objects can be drawn.
 * 
 * @author Lindon Holmes
 */
class TestingWindowHandle extends JFrame {

	// window dimensions
	private int windowHeight;
	private int windowWidth;

	//
	private boolean endgame = false;

	// fps
	private int FPS = 30;

	// panel
	TestingClickPanel panelMain;

	/**
	 * Create a view of a GameArena.
	 */
	public TestingWindowHandle() {
		setScreenSize();

		this.setTitle("basic JFrame");
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		setJPanel();

	}

	/**
	 * sets up the panel that the graphics will be added to 
	 */
	public void setJPanel() {

		setLayout(new BorderLayout());
		panelMain = new TestingClickPanel(windowWidth, windowHeight, this);
		add(panelMain, BorderLayout.CENTER);
		pack();
		repaint();
		setVisible(true);

		this.addKeyListener(panelMain);
	}

	/**
	 * Returns the height (in pixels) of the window
	 */
	public int getHeight() {
		return windowHeight;
	}

	/**
	 * Returns the width (in pixels) of the window
	 */
	public int getWidth() {
		return windowWidth;
	}

	/**
	 * Main loop
	 * window updates are allowed by this loop
	 */	public void run() {
		while (true) {
			// draws on the updated output to the screen
            panelMain.repaint();
            

			// this is were the call to update would be


			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (endgame == true) {
				break;
			}
		}

		//closes the window when the user wants to exit
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

	}

	/**
	 * Update the size of the window.
	 *
	 * @param width  the new width of the window in pixels.
	 * @param height the new height of the window in pixels.
	 */
	public void setSize(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;

		super.setSize(windowWidth, windowHeight);
	}

	/**
	 * Gets the width of the GameArena window, in pixels.
	 * 
	 * @return the width in pixels
	 */
	public int getwindowWidth() {
		return windowWidth;
	}

	/**
	 * Gets the height of the GameArena window, in pixels.
	 * 
	 * @return the height in pixels
	 */
	public int getwindowHeight() {
		return windowHeight;
	}

	/*
	 * attempts to set the screen to the maximum size
	 * 
	 * assuming single monitor setup solution that is consistent across operating
	 * systems. found dimension technique using:
	 * -https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-
	 * in-java -accessed: 05/03/2020 -answer by users: Colin Hebert & Devon_C_Miller
	 */
	public void setScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = (int) (screenSize.getWidth());
		windowHeight = (int) (screenSize.getHeight() * 0.9);
	}

	/**
	 * @return the fps value 
	 */
	public int getFPS() {
		return FPS;
	}

	/**
	 * @return the JPanel that the graphics are added to 
	 */
	public TestingClickPanel getClickPanel() {
		return panelMain;
	}

}


class TestingClickPanel extends JPanel implements MouseListener, KeyListener  {
     
	// JFrame object
	TestingWindowHandle thisWindow;

	// used to close the window
	private boolean exiting = false;

	// determines whether graphics have been drawn onto the screen
    private boolean rendered = false;
    
    //
    private TextBox typingTextBox;
    private boolean setTextBox = false;


	public TestingClickPanel(int width, int height, TestingWindowHandle motherTable) {

		thisWindow = motherTable;

		// Add mouse Listener
		addMouseListener(this);

		setPreferredSize(new Dimension(width, height));

	}

    /**
     * Called by the OS to
     * draw onto the screen
     */
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);


		// getting the items from the table JFrame
		int windowWidth = thisWindow.getwindowWidth();
		int windowHeight = thisWindow.getwindowHeight();

        //sets the size of the JPanel if it has not been rendered
		if (!rendered) {
			this.setSize(windowWidth, windowHeight);
			rendered = true;
		}

        //setting up the graphical elements 
		Graphics2D drawingSurface = (Graphics2D) gr;
		BufferedImage i = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = i.createGraphics();

		//drawingSurface.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//drawingSurface.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		// no more than one thread will be able to access the code inside that block.
		// make sure that in a multi-threaded application,
		// only one thread can access a critical data structure at a given time.

		// prevents multiple [things] from trying to access this at once
		// to prevent corruption
		synchronized (this) {
			// if the window is running
			if (!this.exiting) {
                // this is where to draw on screen

                //--DRAWNIG THE BACKGROUND--
				g.setColor(new Color(0, 0, 0));
				g.fillRect(0, 0, windowWidth, windowHeight);

				//--DRAWING THE REST OF THE PAGE--
                if (setTextBox == false){
					typingTextBox  = new TextBox(g, 10, 10, 500, 500);
					System.out.println(" created new text box ");
                    setTextBox = true;
                }
                typingTextBox.drawTextBox(g);

			}
            
            //draws onto the screen
			drawingSurface.drawImage(i, this.getInsets().left, this.getInsets().top, this);
		}

	}


	/*
	https://stackoverflow.com/questions/9417356/bufferedimage-resize
	*/
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 

		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
	
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
	
		return dimg;
	}
				



  
    //Required methods for MouseListener, though the only one you care about is click
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    /** Called whenever the mouse clicks.
        * Could be replaced with setting the value of a JLabel, etc. */
    public void mouseClicked(MouseEvent e) {

        Point p = e.getPoint();
        int pointXCoord = (int)p.getX();
		int pointYCoord = (int)p.getY();
		
		//
		typingTextBox.updateLeftAndRightText(pointXCoord, pointYCoord);

        
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

		/////
		typingTextBox.typeLetter(typedKey, e.getExtendedKeyCode());
		

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
  


