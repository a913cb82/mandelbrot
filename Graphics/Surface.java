

package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import Mandelbrot.Coordinate;
import Mandelbrot.Zoomer;

class Surface
    extends JPanel
{
  private static final long serialVersionUID = 1L;

  private Map<Coordinate, Color> toUpdate;

  // Constructor, initialise everything
  public Surface()
  {
    toUpdate = new HashMap<Coordinate, Color>();
    addMouseListener(new Zoomer());
  }

  // Update cell x,y with colour c
  public void updateCell(int x, int y, Color c)
  {
    toUpdate.put(new Coordinate(x, y), c);
  }

  // Called by paintComponent, recolour all cells to be recoloured
  private void updateCells(Graphics g)
  {
    Graphics2D g2d = (Graphics2D) g;

    // Update all cells to be updated
    for (Entry<Coordinate, Color> e : toUpdate.entrySet())
      {
        g2d.setColor(e.getValue());
        int x = e.getKey().x;
        int y = e.getKey().y;
        g2d.drawLine(x, y, x, y);
        // g2d.fillRect(x, y, 1, 1);
      }
    toUpdate = new HashMap<Coordinate, Color>();
  }

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    updateCells(g);
  }
}
