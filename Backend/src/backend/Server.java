
package backend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.net.httpserver.*;

public class Server {

    public Server(Graph graph, FindClosestNode findClosestNode) throws IOException {

        // server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/dijkstra", new CalculateDistance(graph, findClosestNode)); // testen mit
                                                                                          // http://localhost:8000/test
        server.setExecutor(null); // creates a default executor
        server.start();

    }

    // response /( is mein handleer)
    static class CalculateDistance implements HttpHandler {

        Graph graph;
        FindClosestNode findClosestNode;

        public CalculateDistance(Graph graph, FindClosestNode findClosestNode) {
            this.graph = graph;
            this.findClosestNode = findClosestNode;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {

            try {

                var parameterString = t.getRequestURI().getQuery();
                System.out.println(parameterString);

                var parameters = getParameters(parameterString); // map von einzelnen parameter

                double startlong = Double.parseDouble(parameters.get("start_long"));
                double startlat = Double.parseDouble(parameters.get("start_lat"));
                double endlong = Double.parseDouble(parameters.get("end_long"));
                double endlat = Double.parseDouble(parameters.get("end_lat"));

                System.out.printf("Lats/Longs: %f, %f, %f, %f\n", startlong, startlat, endlong, endlat);

                var startnode = findClosestNode.findNode( startlat, startlong);
                var endnode = findClosestNode.findNode( endlat, endlong);

                System.out.printf("Nodes: %d, %d\n", startnode, endnode);

                var distance = graph.dijkstraOneToOne(startnode, endnode);
                t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

                String response = Integer.toString(distance);
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public Map<String, String> getParameters(String query) {
            return Stream.of(query.split("&"))
                    .filter(s -> !s.isEmpty())
                    .map(kv -> kv.split("=", 2))
                    .collect(Collectors.toMap(x -> x[0], x -> x[1]));

        }
    }

}
// zeigt response
// http://localhost:8000/dijkstra?start_long=1&start_lat=2&end_long=1000&end_lat=100