package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;

    public StopParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse stop data from the file and add all stops to stop manager.
     */
    public void parse() throws IOException, StopDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param jsonResponse string encoding JSON data to be parsed
     * @throws JSONException            when JSON data does not have expected format
     * @throws StopDataMissingException when
     *                                  <ul>
     *                                  <li> JSON data is not an array </li>
     *                                  <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     *                                  </ul>
     */

    public void parseStops(String jsonResponse) throws JSONException, StopDataMissingException {

        JSONArray arr = new JSONArray(jsonResponse);

        for (int s = 0; s < arr.length(); s++) {
            try {
                JSONObject stop = arr.getJSONObject(s);
                String name = stop.getString("Name");
                int stopNumber = stop.getInt("StopNo");
                Double lat = stop.getDouble("Latitude");
                Double lon = stop.getDouble("Longitude");
                LatLon locn = new LatLon(lat, lon);
                Stop r = StopManager.getInstance().getStopWithId(stopNumber, name, locn);
                parseStopsHelper(stop, r);

            } catch (JSONException e) {
                throw new StopDataMissingException();
            }
        }
    }

    public void parseStopsHelper(JSONObject obj, Stop stop) throws JSONException, StopDataMissingException {
        try {
            String routes = obj.getString("Routes");
            String[] splitRoutes = routes.split(",");
            for (String s1 : splitRoutes) {
                Route route = RouteManager.getInstance().getRouteWithNumber(s1.trim());
                stop.addRoute(route);
                route.addStop(stop);
            }
        }
        catch (JSONException e) {
            throw new StopDataMissingException();
        }

    }
}

