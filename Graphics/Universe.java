

package Graphics;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Singleton Universe Frame
 */
public class Universe
    extends JFrame
{

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private Surface mSurface;

  private static Universe mInstance;

  public void addText(String text)
  {
    JLabel label = new JLabel(text);
    this.removeAll();
    this.add(label);
  }

  private Universe(int x, int y, String title)
  {
    // Initialise UI
    setTitle(title);
    mSurface = new Surface();

    add(mSurface);

    // Set size of content pane to be size of universe
    this.getContentPane().setPreferredSize(new Dimension(x, y));
    this.pack();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }

  public static Universe getInstance()
  {
    return mInstance;
  }

  public static void setup(final int x, final int y, final String title)
  {
    mInstance = new Universe(x, y, title);
    mInstance.setVisible(true);
  }

  // Methods to be called outside
  public void update()
  {
    mSurface.repaint();
  }

  public void updateCell(int x, int y, Color c)
  {
    mSurface.updateCell(x, y, c);
  }
}
