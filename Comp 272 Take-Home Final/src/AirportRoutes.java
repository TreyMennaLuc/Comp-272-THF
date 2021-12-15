import java.io.*;
import java.util.*;

public class AirportRoutes {


    ArrayList<Edge> routes; //24,992
    // either use Edge class or your own class
    ArrayList<String> airports; //9135
    // or if you use integers for airports some other data structure
    int numAirports;
    // add something for DAG
    HashedDirectedGraph reduced;


    public AirportRoutes() {
// intialize your stuff
        routes = new ArrayList<>();
        airports = new ArrayList<>();
        numAirports = 0;
        reduced = new HashedDirectedGraph();
    }

    public HashedDirectedGraph buildHubs(DirectedGraph dg) {
        reduced = new HashedDirectedGraph(dg.numSCC);
        for (Edge e : routes) {
            if (!e.v1.equals(e.v2)) {
                String c1 = dg.components.get(e.v1);
                String c2 = dg.components.get(e.v2);

                if (!c1.equals(c2)) {
                    reduced.addVertex(c1);
                    reduced.addVertex(c2);
                    reduced.addEdge(c1, c2);
                }
            }
        }

        return reduced;
// parameter might be the directedGraph you built for the routes
        // this method builds the reducedGraph, I call it the Hubs.
    }

    public void readAndStoreData(String airportsFile, String routesFile) {
        try {
            Scanner sc = new Scanner(new File(airportsFile));
            Scanner sc2 = new Scanner(new File(routesFile));
            String s;
            // graph.add(new ArrayList<Integer>());

            while (sc.hasNextLine()) {
                s = sc.nextLine();
                airports.add(s);
                numAirports++;
            }
            while (sc2.hasNextLine()) {
                s = sc2.nextLine();
                if (s.isEmpty()) continue;
                String v1 = s.substring(0, 3);
                String v2 = s.substring(4, 7);
                routes.add(new Edge(v1, v2));
            }
        } catch (FileNotFoundException e) {
        }
    }


    // finds the minimum number of routes to be added from s for full connectivity
    public int minRoutes(String port) {
        int minRoutes = 0;
        for(String checkIn : reduced.hdg.keySet())
        {
            if(reduced.hdg.get(checkIn).getOutList().isEmpty())
            {
                minRoutes++;
            }
        }
        return minRoutes;
    }

    // returns the number of flight networks in the underlying undirected graph
// essentially the number of weakly connected components
    public int numFlightNetworks(DirectedGraph dg) {
       DirectedGraph wCC = new DirectedGraph(dg.numVertex);
        for (Edge e : this.routes)
        {
            wCC.addEdge(e.v1, e.v2);
            wCC.addEdge(e.v2, e.v1);
        }
        wCC.addPorts(this.airports);
        wCC.postOrderDepthFirstTraversal(true);
        wCC.resetMarked();
        wCC.depthFirstTraversal();
        //wCC.regularDepthFirstTraversal();
        //return wCC.stronglyCCs.size();
        return wCC.numSCC;
    }

    public static void main(String[] args) {
        AirportRoutes ar = new AirportRoutes();
        ar.readAndStoreData("airports-IATA-codes.txt", "routes.txt");
        DirectedGraph dg = new DirectedGraph(ar.numAirports);
        for (Edge e : ar.routes)
            dg.addEdge(e.v1, e.v2);
        dg.addPorts(ar.airports);
        dg.postOrderDepthFirstTraversal(true);
        dg.resetMarked();
        dg.depthFirstTraversal();
        HashedDirectedGraph hdg = ar.buildHubs(dg);
        System.out.println("Min # of Airport routes: " + ar.minRoutes("ORD") );
        System.out.println("Number of Flight Networks: " + ar.numFlightNetworks(dg));

    }
}