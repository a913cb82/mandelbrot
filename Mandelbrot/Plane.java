

package Mandelbrot;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;

import Graphics.Universe;

/**
 * Mutable singleton map class for Mandelbrot Set 
 * Calling updates all cells on  map 
 * Obeying relation z(n+1) = z(n)^2 + c 
 * Storing time when absolute value of cell > 2
 */
public class Plane
{
  private static Plane mInstance;

  private Universe canvas;

  private long mZoom;

  private double mRealCentre, mImagCentre;

  private int mY, mX;

  private int maxIts;

  private boolean smoothColours;

  public long getZoom()
  {
    return mZoom;
  }

  public int getX()
  {
    return mX;
  }

  public int getY()
  {
    return mY;
  }

  public double getXCent()
  {
    return mRealCentre;
  }

  public double getYCent()
  {
    return mImagCentre;
  }

  public int getIts()
  {
    return maxIts;
  }

  public void setZoom(long zoom)
  {
    mZoom = zoom;
  }

  public void setXCent(double x)
  {
    mRealCentre = x;
  }

  public void setYCent(double y)
  {
    mImagCentre = y;
  }

  public void setIts(int it)
  {
    maxIts = it;
  }

  public static Plane getInstance()
  {
    return mInstance;
  }

  public static Plane PlaneAlt(double realCentre, double imagCentre, long zoom,
                               int xCells, int yCells, int maxIterations,
                               boolean cols)
  {
    // if (mInstance != null) {return mInstance;}
    // ratio = xCells/yCells
    // zoom = 1, 3.5 xRange, 2 yRange
    // xRange = 3.5
    mInstance = new Plane();
    mInstance.mZoom = zoom;
    mInstance.mRealCentre = realCentre;
    mInstance.mImagCentre = imagCentre;
    mInstance.mX = xCells;
    mInstance.mY = yCells;
    mInstance.maxIts = maxIterations;
    mInstance.smoothColours = cols;
    return mInstance;
  }

  public void initialiseCanvas(int xCells, int yCells)
  {
    // Initialise canvas for drawing
    Universe.setup(xCells, // canvas x
                   yCells, // canvas y
                   "Mandelbrot"); // canvas title
    canvas = Universe.getInstance();
  }

  public void update(String drawOrWrite) throws IOException
  {
    System.out.print("WAIT...");
    // Calculate values for each cell & update canvas as we go
    double re, im;
    Complex c;
    Complex z;
    boolean inSet;
    float k;
    BufferedImage bi = new BufferedImage(mX, mY, BufferedImage.TYPE_INT_RGB);
    boolean draw = drawOrWrite.equals("draw");

    double realRange = 3.5 / mZoom;
    double imagRange = realRange * mY / mX;
    // keep track, to detect cycles and see if we can exit early
    HashSet<Complex> values = new HashSet<Complex>();
    for (int i = 0; i < mX; i++)
      {
        re = ((double) i / mX) * realRange + (mRealCentre - realRange / 2);
        for (int j = 0; j < mY; j++)
          {
            z = new Complex(0, 0);
            k = 0;
            inSet = true;
            values = new HashSet<Complex>();
            im = ((double) j / mY) * imagRange + (mImagCentre - imagRange / 2);
            c = new Complex(re, im);
            while (k < maxIts & inSet)
              {
                // z(n+1) = z(n)^2 + c
                z = z.power(2);
                z = z.add(c);
                k++;
                // Kick out of set if necessary
                if (z.abs() > 2)
                  {
                    inSet = false;
                  }
                // detect cycles
                if (values.add(z) == false)
                  {
                    k = maxIts;
                  }
              }
            if (smoothColours)
              {
                if (inSet)
                  {
                    k = 0;
                    z = new Complex(0, 0);
                  }
                k = (float) (k + Math.log(Math.log(81)) - Math.log(Math.log(z.abs())
                                                                   / Math.log(2)));
                if (k > maxIts)
                  {
                    k = maxIts;
                  }
              }
            if (draw)
              {
                canvas.updateCell(i, j, getColor(k, maxIts));
              }
            else
              {
                bi.setRGB(i, j, getColor(k, maxIts).getRGB());
              }
          }
      }
    if (draw)
      {
        canvas.update();
      }
    else
      {
        String filename = "(" + Double.toString(mRealCentre) + ", "
                          + Double.toString(mImagCentre) + "), Zoom "
                          + Long.toString(mZoom) + ", Iterations "
                          + Integer.toString(maxIts) + ".png";
        File outputfile = new File(filename);
        ImageIO.write(bi, "png", outputfile);
        System.exit(0);
      }
    System.out.println(" DONE");
  }

  private static Color getColor(float its, int maxIts)
  {
    float k = its / maxIts;
    return Color.getHSBColor(
    /**
     * HUE COLOURS .0: red .1: orange .2: greenish .3: green .4: olive .5: cyan
     * .6: blue .7: blue .8: purple .9: pink 1.: red Patterns given by different
     * hues .0F+k : green tentacles .2F+k : electricity .4F+k : ugly .6F+k :
     * interesting, idk .8F+k : tendrils .0F-k : blue lightning .2F-k : similar
     * to .0F, bit more purple .4F-k : ugly pink and green tentacles .6F-k :
     * green vines .8F-k : green lightning
     */
    .0F - k, 1 - k, k);
  }
}
