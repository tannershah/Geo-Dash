public class MobileSprite extends Sprite//insert code here
{
  private double vx;
  private double vy;
  
  public MobileSprite(double left, double top, int width, int height, String image,
                      double velocityX, double velocityY)
  {
    super(left, top, width, height, image);
    vx = velocityX;
    vy = velocityY;
  }
  
  public double getVY()
  {
    return vy;
  }
  
  public void setVY(double velocityY)
  {
    vy = velocityY;
  }

  public double getVX(){ return vx; }

  public void setVX(double velocityX){ vx = velocityX; }
  
  public void step(World world)
  {
    if (getLeft() < 0)
      vx = Math.abs(vx);
    if (getLeft() + getWidth() > world.getWidth())
      vx = -Math.abs(vx);
    if (getTop() + getHeight() > world.getHeight())
      vy = -Math.abs(vy);
    setLeft(getLeft() + vx);
    setTop(getTop() + vy);
    
  }
}