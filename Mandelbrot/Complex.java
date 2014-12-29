package Mandelbrot;

/**
 * Immutable complex number class 
 * Complex c = new Complex(real, imag); 
 * Methods:
 * c.getReal() 
 * c.abs() 
 * c.conjugate() 
 * c.getImag() 
 * c.reciprocate() 
 * lhs.add(rhs)
 * lhs.subtract(rhs) 
 * lhs.multiply(rhs) 
 * c.power(exponent)
 */
public class Complex
{
  private final double mReal;

  private final double mImag;

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(mImag);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(mReal);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Complex other = (Complex) obj;
    if (Double.doubleToLongBits(mImag) != Double.doubleToLongBits(other.mImag))
      return false;
    if (Double.doubleToLongBits(mReal) != Double.doubleToLongBits(other.mReal))
      return false;
    return true;
  }

  public Complex(double r, double i)
  {
    mReal = r;
    mImag = i;
  }

  public double getReal()
  {
    return mReal;
  }

  public double getImag()
  {
    return mImag;
  }

  public Complex add(Complex c)
  {
    return new Complex(this.getReal() + c.getReal(), this.getImag()
                                                     + c.getImag());
  }

  public Complex subtract(Complex c)
  {
    return new Complex(this.getReal() - c.getReal(), this.getImag()
                                                     - c.getImag());
  }

  public double abs()
  {
    return Math.sqrt(mReal * mReal + mImag * mImag);
  }

  public Complex conjugate()
  {
    return new Complex(this.getReal(), - this.getImag());
  }

  public Complex multiply(Complex c)
  {
    return new Complex(mReal * c.mReal - mImag * c.mImag, mReal * c.mImag
                                                          + mImag * c.mReal);
  }

  public Complex reciprocate()
  {
    double r2 = mReal * mReal + mImag * mImag;
    return new Complex(mReal / r2, - mImag / r2);
  }

  public Complex power(int pow)
  {
    if (pow == 0)
      {
        return new Complex(1, 0);
      }
    else if (pow < 0)
      {
        return this.reciprocate().power(- pow);
      }
    else if (pow == 1)
      {
        return this;
      }
    else
      {
        return this.multiply(power(pow - 1));
      }
  }
}
