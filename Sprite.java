import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.*;

public class Sprite
{
  private double left;  //the x-coordinate of the left edge of the sprite
  private double top;   //the y-coordinate of the top edge of the sprite
  private int width;
  private int height;
  private String image;
  private boolean isAlive;

  public Sprite(double theLeft, double theTop, int theWidth, int theHeight, String theImage) {
    left = theLeft;
    top = theTop;
    width = theWidth;
    height = theHeight;
    setImage(theImage);
    isAlive = true;
  }
  
  public double getLeft()
  {
    return left;
  }
  
  public void setLeft(double l)
  {
    left = l;
  }
  
  public double getTop()
  {
    return top;
  }
  
  public void setTop(double t)
  {
    top = t;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public void setWidth(int w)
  {
    width = w;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public void setHeight(int h)
  {
    height = h;
  }
  
  public String getImage()
  {
    return image;
  }
  
  public void setImage(String i)
  {
    image = i;
  }
  
  public void step(World world)
  {
    //do NOT insert any code here
  }
  public boolean isAlive(){
    return isAlive;
  }
  public void die(){
    isAlive = false;
  }
  public boolean overlap(Sprite s){
    return left < s.getLeft()+s.getWidth()
            && left+width > s.getLeft() && top < s.getTop()+s.getHeight() && top+height > s.getTop();
  }

  public LineSegment[] getHitBox(){
    LineSegment[] hitBox = new LineSegment[4];
    Point p1 = new Point((int)left , (int)top);
    Point p2 = new Point((int)left, (int)(top+height));
    Point p3 = new Point((int)(left+width) , (int)top+height);
    Point p4 = new Point((int)(left+width) , (int)(top));
    hitBox[0] = new LineSegment(p1,p2);
    hitBox[1] = new LineSegment(p2,p3);
    hitBox[2] = new LineSegment(p3,p4);
    hitBox[3] = new LineSegment(p1,p4);
    return hitBox;

  }
}
