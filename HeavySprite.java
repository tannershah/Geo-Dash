public class HeavySprite extends MobileSprite//insert code here
{
  public HeavySprite(double left, double top, int width, int height, String image, double vx, double vy)
  {
    super(left, top, width, height, image, vx, vy);
  }

  @Override public void step(World world){
    super.step(world);
    setVY(getVY() + 0.1);

  }


}