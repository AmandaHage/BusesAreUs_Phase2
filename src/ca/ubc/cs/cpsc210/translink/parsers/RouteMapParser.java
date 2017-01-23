package ca.ubc.cs.cpsc210.translink.parsers;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Parser for routes stored in a compact format in a txt file
 */
public class RouteMapParser {
    private String fileName;
    public RouteMapParser(String fileName) {
        this.fileName = fileName;
    }
    /**
     * Parse the route map txt file
     */
    public void parse() {
        DataProvider dataProvider = new FileDataProvider(fileName);
        try {
            String c = dataProvider.dataSourceToString();
            if (!c.equals("")) {
                int posn = 0;
                while (posn < c.length()) {
                    int endposn = c.indexOf('\n', posn);
                    String line = c.substring(posn, endposn);
                    parseOnePattern(line);
                    posn = endposn + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Parse one route pattern, adding it to the route that is named within it
     * @param str
     */
    private void parseOnePattern(String str) {
        /*
        String routeN = str.substring(str.indexOf("N") + 1, str.indexOf("-"));
        String patternN = str.substring(str.indexOf("-") + 1, str.indexOf(";"));
        if (!routeN.isEmpty() && !patternN.isEmpty()) {
            String s = str.substring(str.indexOf(";") +1, str.length());
            if (!s.isEmpty()) {
                List<LatLon> paths = new ArrayList<>();
                String[] split = s.split(";");
                for (int i = 0; i < split.length; i+=2) {
                    double lat = Double.parseDouble(split[i]);
                    double lon = Double.parseDouble(split[i + 1]);
                    LatLon locn = new LatLon(lat, lon);
                    paths.add(locn);
                }
                storeRouteMap(routeN, patternN, paths);
                paths.clear();
            }
        }
    }
    */

            String routeNumber = str.substring(str.indexOf("N") +1, str.indexOf("-"));
            String patternName = str.substring(str.indexOf("-") + 1, str.indexOf(";"));

            if (!routeNumber.isEmpty() && !patternName.isEmpty()) {
                String remainder = str.substring(str.indexOf(";") +1, str.length());

                List<LatLon> elements = new ArrayList<>();

                if (!remainder.isEmpty()) {
                    String[] string = remainder.split(";");
                    for (int i = 0; i < string.length; i+=2) {
                        double lat = Double.parseDouble(string[i]);
                        double lon = Double.parseDouble(string[i + 1]);
                        LatLon l = new LatLon(lat, lon);
                        elements.add(l);
                    }
                    storeRouteMap(routeNumber, patternName, elements);
                    //elements.clear();
                }

            }
        }
    /**
     * Store the parsed pattern into the named route
     * Your parser should call this method to insert each route pattern into the corresponding route object
     * There should be no need to change this method
     *
     * @param routeNumber       the number of the route
     * @param patternName       the name of the pattern
     * @param elements          the coordinate list of the pattern
     */
    private void storeRouteMap(String routeNumber, String patternName, List<LatLon> elements) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
        RoutePattern rp = r.getPattern(patternName);
        rp.setPath(elements);
    }
}