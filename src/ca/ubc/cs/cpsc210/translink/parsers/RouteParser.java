package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;


    public RouteParser(String filename) {
        this.filename = filename;
    }
    /**
     * Parse route data from the file and add all route to the route manager.
     *
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException{
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }
    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException   when JSON data does not have expected format
     * @throws RouteDataMissingException when
     * <ul>
     *  <li> JSON data is not an array </li>
     *  <li> JSON data is missing Name, StopNo, Routes or location elements for any stop</li>
     * </ul>
     */

    public void parseRoutes(String jsonResponse) throws JSONException, RouteDataMissingException {
        JSONArray arr = new JSONArray(jsonResponse);

        for (int s=0; s < arr.length(); s++) {
            try {
                JSONObject stop = arr.getJSONObject(s);
                String name = stop.getString("Name");
                String stopNumber = stop.getString("RouteNo");
                Route r = RouteManager.getInstance().getRouteWithNumber(stopNumber, name);
                JSONArray patterns = arr.getJSONObject(s).getJSONArray("Patterns");
                parseRoutesHelper(patterns, r);

            } catch (JSONException e) {
                throw new RouteDataMissingException();
            }
        }
    }

    public void parseRoutesHelper(JSONArray patterns, Route r) throws JSONException, RouteDataMissingException {
        for (int i = 0; i < patterns.length(); i++) {
            try {
                JSONObject listOfPatterns = patterns.getJSONObject(i);
                String destination = listOfPatterns.getString("Destination");
                String direction = listOfPatterns.getString("Direction");
                String patternNo = listOfPatterns.getString("PatternNo");
                RoutePattern routePattern = new RoutePattern(patternNo, destination, direction, r);
                r.addPattern(routePattern);

            } catch (JSONException e) {
                throw new RouteDataMissingException();
            }
        }
    }
}
