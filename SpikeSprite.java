import java.awt.geom.Line2D;
import java.util.*;
public class SpikeSprite extends Sprite{

    private LineSegment[] hitBox;

    public SpikeSprite(double theLeft, double theTop, int theWidth, int theHeight, String theImage){
        super(theLeft, theTop, theWidth, theHeight, theImage);
        hitBox = new LineSegment[3];
        Point p1 = new Point((int)theLeft, (int)theTop+theHeight);
        Point p2 = new Point((int)(theLeft+((double)theWidth/2)),(int)theTop);
        Point p3 = new Point((int)(theLeft+theWidth) , (int)(theTop+theHeight));
        hitBox[0] = new LineSegment(p1,p2);
        hitBox[1] = new LineSegment(p2,p3);
        hitBox[2] = new LineSegment(p1,p3);
    }

    @Override
    public LineSegment[] getHitBox(){
        return hitBox;
    }

    @Override
    public boolean overlap(Sprite s) {
        LineSegment[] box = s.getHitBox();
        for(int i = 0; i < hitBox.length; i++){
            for(int j = 0; j < box.length; j++){
                if(hitBox[i].intersectsLine(box[j])) {
//                    System.out.println("spiked");
//                    System.out.println("________");
//                    for(LineSegment l  : hitBox)System.out.print(" | " + l.getP1() + " , " +l.getP2());
//                    //System.out.println(hitBox[1].getP1() + " , " + hitBox[1].getP2());
//                    System.out.println();
//                    for(LineSegment k  : box)System.out.print(" | " + k.getP1() + " , " + k.getP2());
//                    //System.out.println(box[2].getP1() + " , " + box[2].getP2());
//                    System.out.println();
//                    System.out.println("________");
                    return true;
                }
            }
        }
        return false;
    }

    public LineSegment getSegment(int n){
        return hitBox[n];
    }
}
