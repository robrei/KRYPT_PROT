//Autor: Robert Reininger, c1210537020

package ecc;
public class PointOperation {
	static Point tmp = new Point();
	
    private static Point pAdd(Point a, Point b) {
        double x3 = Math.pow(((b.y - a.y) / (b.x - a.x)),2) - a.x - b.x;
        double y3 = ((b.y - a.y) / (b.x - a.x)) * (a.x - x3) - a.y;
        return new Point(x3,y3);
    }	
    private static Point pDouble(Point p, double a) {
        double x3 = Math.pow(((3 * p.x * p.x + a) / (2 * p.y)), 2) - 2 * p.x;
        double y3 = ((3 * p.x * p.x + a) / (2 * p.y)) * (p.x - x3) - p.y;
        return new Point(x3,y3);
    }    
    public static Point pMultiply(Point p, double a, int k) {
        Point s = new Point();
        char[] temp = Integer.toBinaryString(k).toCharArray();
        for (int i=0; temp.length>i; i++) {
        	if (temp[i] == '1') {
                s = pAdd(s, p);
                //System.out.println("sx: "+ s.x + "  sy: " +s.y);
            }
            p = pDouble(p, a);
            //System.out.println("px: "+ p.x + "  py: "+ p.y);
        }
        	return s;
        }
    
}
