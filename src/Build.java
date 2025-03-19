import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    Set<String> visited = new HashSet<>();
    shortHelper(vertex, k, visited);
  }

  public static void shortHelper(Vertex<String> vertex, int k, Set<String> visited) {
    if(vertex == null) return;
    if(visited.contains(vertex.data)) return;

    if(vertex.data.length() < k) {
      System.out.println(vertex.data);
    }
    
    visited.add(vertex.data);

    for(Vertex<String> neighbor : vertex.neighbors) {
      shortHelper(neighbor, k, visited);
    }

  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<String> visited = new HashSet<>();
    return longHelper(vertex, visited);
  }

  public static String longHelper(Vertex<String> vertex, Set<String> visited) {
    if(vertex == null) return "";

    if(visited.contains(vertex.data)) return "";

    visited.add(vertex.data);

    String longest = vertex.data;

    for(Vertex<String> neighbor : vertex.neighbors) {

      String check = longHelper(neighbor, visited);

      if(check.length() > longest.length()) {
        longest = check;
      }
    }
    
    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    Set<T> visited = new HashSet<>();
    selfHelper(vertex, visited);
  }

  public static <T> void selfHelper(Vertex<T> vertex, Set<T> visited){
    if(vertex == null) return;
    if(visited.contains(vertex.data)) return;

    visited.add(vertex.data);

    if(vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }

    for(var neighbor : vertex.neighbors) {
      selfHelper(neighbor, visited); 
    }

  };

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    Set<Airport> visited = new HashSet<>();
    return reachHelper(start, destination, visited);
  }

  public static boolean reachHelper(Airport start, Airport destination, Set<Airport> visited) {
    if(start == null || destination == null) return false;
    if(start.equals(destination)) return true;
    if(visited.contains(start)) return false;

    visited.add(start);

    for(Airport next : start.getOutboundFlights()) {
      if(reachHelper(next, destination, visited)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> visited = new HashSet<>();
    if(graph == null || !graph.containsKey(starting)) return graph.keySet(); 

    unreachHelper(graph, starting, visited);

    Set<T> unreach = new HashSet<>(graph.keySet());

    unreach.removeAll(visited);

    return unreach;
  }

  public static <T> void unreachHelper(Map<T, List<T>> graph, T starting, Set<T> visited) {
    if(visited.contains(starting)) return;
    visited.add(starting);
    
    for(T neighbor : graph.getOrDefault(starting, new ArrayList<>())) {
      unreachHelper(graph, neighbor, visited);
    }

}

}
