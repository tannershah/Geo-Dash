import java.awt.geom.Line2D ;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class LineSegment extends Line2D.Double {


    public LineSegment(Point p1, Point p2) {
        super(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public Rectangle2D getBounds2D() {
        return null;
    }

}

