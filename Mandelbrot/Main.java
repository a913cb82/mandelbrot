

package Mandelbrot;

import java.io.IOException;

public class Main
{
  public static void main(String[] args) throws IOException
  {
    double xCentre = - 0.75;
    double yCentre = 0.0;
    long zoom = 1;
    int canvasX = 400;
    int canvasY = 300;
    int maxIterations = 1;
    boolean smooth = false;

    Plane p = Plane.PlaneAlt(xCentre, yCentre, zoom, canvasX, canvasY,
                             maxIterations, smooth);
    p.initialiseCanvas(canvasX, canvasY);
    p.update("draw");
  }
}
