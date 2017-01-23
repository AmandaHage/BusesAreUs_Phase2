package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;



/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {

    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop         stop to which parsed arrivals are to be added
     * @param jsonResponse the JSON response produced by Translink
     * @throws JSONException                when JSON response does not have expected format
     * @throws ArrivalsDataMissingException when no arrivals are found in the reply
     */
    public static void parseArrivals(Stop stop, String jsonResponse) throws JSONException, ArrivalsDataMissingException {
        JSONArray arrivals = new JSONArray(jsonResponse);

        for (int i = 0; i < arrivals.length(); i++) {
            try {
                String name = arrivals.getJSONObject(i).getString("RouteName");
                String routeNumber = arrivals.getJSONObject(i).getString("RouteNo");
                Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber, name);
                if (name.isEmpty() || routeNumber.isEmpty()) {
                    throw new ArrivalsDataMissingException();
                }
                stop.addRoute(r);
                JSONArray schedules = arrivals.getJSONObject(i).getJSONArray("Schedules");

                for (int j = 0; j < schedules.length(); j++) {
                    int countdown = schedules.getJSONObject(j).getInt("ExpectedCountdown");
                    String destination = schedules.getJSONObject(j).getString("Destination");
                    String status = schedules.getJSONObject(j).getString("ScheduleStatus");
                    Arrival arrival = new Arrival(countdown, destination, r);
                    arrival.setStatus(status);
                    stop.addArrival(arrival);
                }

                if (stop.getArrivals().isEmpty()) {
                    throw new ArrivalsDataMissingException();
                }

            } catch (JSONException e) {
                throw new ArrivalsDataMissingException();
            }
        }


       /*
        JSONArray arr = new JSONArray(jsonResponse);

        for (int s=0; s < arr.length(); s++) {
            try {
                String arrivalName = arr.getJSONObject(s).getString("RouteName");
                String number = arr.getJSONObject(s).getString("RouteNo");
                Route route = RouteManager.getInstance().getRouteWithNumber(number, arrivalName);

                JSONArray sch = arr.getJSONObject(s).getJSONArray("Schedules");
                parseArrivalsHelper(sch, route, stop);
                if (stop.getArrivals().isEmpty()) {
                    throw new ArrivalsDataMissingException();
                }
            } catch (JSONException e) {
                throw new ArrivalsDataMissingException();
            }
        }
    }

    public static void parseArrivalsHelper(JSONArray sch, Route route, Stop stop) throws JSONException, ArrivalsDataMissingException {
        for (int z = 0; z < sch.length(); z++) {
            try {
                String stat = sch.getJSONObject(z).getString("ScheduleStatus");
                int cd = sch.getJSONObject(z).getInt("ExpectedCountdown");
                String dest = sch.getJSONObject(z).getString("Destination");
                Arrival arri = new Arrival(cd, dest, route);
                stop.addArrival(arri);
                stop.addRoute(route);
                arri.setStatus(stat);
            }
            catch (JSONException e) {
                throw new ArrivalsDataMissingException();
            }
        }

    */
    }
}