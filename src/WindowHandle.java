import javax.swing.*;//used for gui generation


import java.awt.*;//used for layout managers
//used to get the coordinates of the mouse click
import java.awt.event.*;


/**
 * This class provides a simple window in which grahical objects can be drawn.
 * 
 * @author Lindon Holmes
 */
public class WindowHandle extends JFrame {

	// window dimensions
	private int windowHeight;
	private int windowWidth;

	//
	private boolean endgame = false;

	// fps
	private int FPS = 30;

	// panel
	private ClickPanel panelMain;

	/**
	 * Create a view of a GameArena.
	 */
	public WindowHandle() {
		setScreenSize();

		this.setTitle("revision question asker");
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		setJPanel();
		setClosingOperations();
	}

	/**
	 * call the code declared in this function when
	 * the user clicks the red exit button
	 */
	public void setClosingOperations()
	{
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				// The window is closing
				System.out.println("window is closing buddeh");
				panelMain.saveQuestionsCompleted();
				panelMain.closePPFile();
			}
		});
	}

	/**
	 * sets up the panel that the graphics will be added to 
	 */
	public void setJPanel() {

		setLayout(new BorderLayout());
		panelMain = new ClickPanel(windowWidth, windowHeight, this);
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
		System.out.println("session ended");
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
	public ClickPanel getClickPanel() {
		return panelMain;
	}

}

