

package Mandelbrot;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Zoomer
    implements MouseListener
{
  public void mouseClicked(MouseEvent e)
  {
  }

  public void mousePressed(MouseEvent e)
  {
    Plane p = Plane.getInstance();
    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK)
      {
        // Right click: double iterations
        p.setIts(p.getIts() * 2);
        System.out.println("Iterations = " + p.getIts());
      }
    else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK)
      {
        // Left click: zoom
        int x = (int) e.getPoint().getX();
        int y = (int) e.getPoint().getY();
        double realRange = 3.5 / p.getZoom();
        double imagRange = realRange * p.getY() / p.getX();

        p.setZoom(p.getZoom() * 4);
        p.setXCent(((double) x / p.getX()) * realRange
                   + (p.getXCent() - realRange / 2));
        p.setYCent(((double) y / p.getY()) * imagRange
                   + (p.getYCent() - imagRange / 2));
        System.out.println("(" + p.getXCent() + ", " + p.getYCent()
                           + "), Zoom = " + p.getZoom());
      }
    else
      {
        // Middle click: output file
        double xCentre = p.getXCent();
        double yCentre = p.getYCent();
        int canvasX = 0;
        int canvasY = 0;
        long zoom = p.getZoom();
        int maxIterations = p.getIts();
        boolean smooth = false;

        // VERY MESSY BIT
        // Reads stuff from OutputSize.txt
        // Format:
        // x
        // y
        BufferedReader br = null;
        try
          {
            br = new BufferedReader(
                                    new InputStreamReader(
                                                          new FileInputStream(
                                                                              "OutputSize.txt")));
          }
        catch (FileNotFoundException e2)
          {
            // TODO Auto-generated catch block
            e2.printStackTrace();
          }
        try
          {
            canvasX = Integer.parseInt(br.readLine(), 10);
            canvasY = Integer.parseInt(br.readLine(), 10);
          }
        catch (Exception e1)
          {

          }
        finally
          {
            try
              {
                br.close();
              }
            catch (IOException e1)
              {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
          }

        // Now just make a new plane, write all the stuff to a file, then exit
        p = Plane.PlaneAlt(xCentre, yCentre, zoom, canvasX, canvasY,
                           maxIterations, smooth);
        try
          {
            p.update("write");
          }
        catch (IOException e1)
          {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
      }
    if (p.getZoom() > 1L << 42 - 1)
      {
        System.out.println("Approaching end of double precision");
      }
    try
      {
        p.update("draw");
      }
    catch (IOException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
  }

  public void mouseReleased(MouseEvent e)
  {
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }
}
