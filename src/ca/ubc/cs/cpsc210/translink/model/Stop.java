package ca.ubc.cs.cpsc210.translink.model;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import java.util.*;
/**
 * Represents a bus stop with an number, name, location (lat/lon)
 * set of routes which stop at this stop and a list of arrivals.
 */
public class Stop implements Iterable<Arrival> {
    private List<Arrival> arrivals;
    private Set<Route> routes;
    private int number;
    private String name;
    private LatLon locn;
    /**
     * Constructs a stop with given number, name and location.
     * Set of routes and list of arrivals are empty.
     *
     * @param number    the number of this stop
     * @param name  name of this stop
     * @param locn  location of this stop
     */
    public Stop(int number, String name, LatLon locn) {
        this.number = number;
        this.name = name;
        this.locn = locn;
        this.routes = new HashSet<>();
        this.arrivals = new ArrayList<>();
    }
    /**
     * getter for name
     * @return      the name
     */
    public String getName() {
        return this.name;
    }
    /**
     * getter for locn
     * @return      the location
     */
    public LatLon getLocn() {
        return this.locn;
    }
    /**
     * getter for number
     * @return      the number
     */
    public int getNumber() { return this.number; }
    /**
     * getter for set of routes
     * @return      the set of routes using this stop
     */
    public Set<Route> getRoutes() { return Collections.unmodifiableSet(routes);  }
    /**
     * Add route to set of routes with stops at this stop.
     *
     * @param route  the route to add
     */
    public void addRoute(Route route) {
        if (route.getStops().contains(this)) {
            routes.add(route);
        } else {
            routes.add(route);
            route.addStop(this);
        }
    }
    /**
     * Remove route from set of routes with stops at this stop
     *
     * @param route the route to remove
     */
    public void removeRoute(Route route) {
        if (route.getStops().contains(this)) {
            routes.remove(route);
            route.removeStop(this);
        }
    }
    /**
     * Determine if this stop is on a given route
     * @param route  the route
     * @return  true if this stop is on given route
     */
    public boolean onRoute(Route route) {
        return (route.getStops().contains(this));
    }
    /**
     * Add bus arrival travelling on a particular route at this stop.
     * Arrivals are to be sorted in order by arrival time
     *
     * @param arrival  the bus arrival to add to stop
     */
    public void addArrival(Arrival arrival) {
        this.arrivals.add(arrival);
        Collections.sort(arrivals);
    }
    /**
     * Remove all arrivals from this stop
     */
    public void clearArrivals() {
        arrivals.clear();
    }
    /**
     * Two stops are equal if their ids are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop r = (Stop) o;
        return (r.getNumber() == this.getNumber());
    }
    /**
     * Two stops are equal if their ids are equal.
     * Therefore hashCode only pays attention to number.
     */
    @Override
    public int hashCode() {
        int result = 31 * this.getNumber();
        return result;
        // hashCode of stop is just the number multiplied by a constant ?
    }
    @Override
    public Iterator<Arrival> iterator() {
        // Do not modify the implementation of this method!
        return arrivals.iterator();
    }
    /**
     * setter for name
     *
     * @param name      the new name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * setter for location
     * @param locn      the new location
     */
    public void setLocn(LatLon locn) {
        this.locn = locn;
    }

    public List<Arrival> getArrivals() {
        return Collections.unmodifiableList(arrivals);
    }
}
