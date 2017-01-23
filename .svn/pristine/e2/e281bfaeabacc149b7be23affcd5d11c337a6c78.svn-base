package ca.ubc.cs.cpsc210.translink.util;



/**
 * Compute relationships between points, lines, and rectangles represented by LatLon objects
 */
public class Geometry {

    /**
     * Return true if the point is inside of, or on the boundary of, the rectangle formed by northWest and southeast
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param point     the point in question
     * @return true if the point is on the boundary or inside the rectangle
     */
    public static boolean rectangleContainsPoint(LatLon northWest, LatLon southEast, LatLon point) {
        double upperBoundx = 0.0;
        double lowerBoundx = 0.0;
        double upperBoundy = 0.0;
        double lowerBoundy = 0.0;

        double x0 = northWest.getLatitude();
        double y0 = northWest.getLongitude();
        double x1 = southEast.getLatitude();
        double y1 = southEast.getLongitude();

        if (x0 > x1) {
            upperBoundx = x0;
            lowerBoundx = x1;
        } else if (x0 < x1) {
            upperBoundx = x1;
            lowerBoundx = x0;
        }
        if (y0 > y1) {
            upperBoundy = y0;
            lowerBoundy = y1;
        } else if (y1 > y0) {
            upperBoundy = y1;
            lowerBoundy = y0;
        }

        return between(lowerBoundx, upperBoundx, point.getLatitude()) && between(lowerBoundy, upperBoundy, point.getLongitude());
    }

    /**
     * Return true if the rectangle intersects the line
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param src       one end of the line in question
     * @param dst       the other end of the line in question
     * @return true if any point on the line is on the boundary or inside the rectangle
     */

    public static boolean rectangleIntersectsLine(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {
        if (rectangleContainsPoint(northWest, southEast, src) && rectangleContainsPoint(northWest, southEast, dst)) {
            return true;
        } else if (rectangleContainsPoint(northWest, southEast, src) || rectangleContainsPoint(northWest, southEast, dst)) {
            return true;
        }
        double point1 = ((src.getLatitude() - dst.getLatitude()) * northWest.getLongitude()
                + (src.getLongitude() - dst.getLongitude()) * northWest.getLatitude() +
                (src.getLongitude() * dst.getLatitude() - dst.getLongitude() * src.getLatitude()));

        double point2 = ((src.getLatitude() - dst.getLatitude()) * northWest.getLongitude() + (src.getLongitude() - dst.getLongitude()) * southEast.getLatitude() +
                (src.getLongitude() * dst.getLatitude() - dst.getLongitude() * src.getLatitude()));

        double point3 = ((src.getLatitude() - dst.getLatitude()) * southEast.getLongitude() + (src.getLongitude() - dst.getLongitude()) * southEast.getLatitude() +
                (src.getLongitude() * dst.getLatitude() - dst.getLongitude() * src.getLatitude()));

        double point4 = ((src.getLatitude() - dst.getLatitude()) * southEast.getLongitude() + (src.getLongitude() - dst.getLongitude()) * northWest.getLatitude() +
                (src.getLongitude() * dst.getLatitude() - dst.getLongitude() * src.getLatitude()));

        if ((point1 > 0 && point2 > 0 && point3 > 0 && point4 > 0) || (point1 < 0 && point2 < 0 && point3 < 0 && point4 < 0)) {
            return false;
        } else
        return lineSegment(northWest, southEast, src, dst);

    }

    public static boolean lineSegment(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {

        if (src.getLongitude() < northWest.getLongitude() && dst.getLongitude() < northWest.getLongitude()) {
            return false;

        } else if (src.getLongitude() > southEast.getLongitude() && dst.getLongitude() > southEast.getLongitude()) {
            return false;

        } else if (src.getLatitude() > northWest.getLatitude() && dst.getLatitude() > northWest.getLatitude()) {
            return false;

        } else if (src.getLatitude() < southEast.getLatitude() && dst.getLatitude() < southEast.getLatitude()) {
            return false;
        }
        return true;
    }

    /**
     * A utility method that you might find helpful in implementing the two previous methods
     * Return true if x is >= lwb and <= upb
     *
     * @param lwb the lower boundary
     * @param upb the upper boundary
     * @param x   the value in question
     * @return true if x is >= lwb and <= upb
     */
    private static boolean between(double lwb, double upb, double x) {
        return lwb <= x && x <= upb;
    }

}