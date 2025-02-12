/**
 * SocialNetwork
 * A class that represents a social network using a graph structure. It supports operations such as adding students, 
 * adding/removing connections, updating weights, printing the network, and finding the shortest path between students.
 * 
 * Subclass: Edge
 * Methods: addStudentName, addEdge, getEdges, removeEdge, updateWeight, printNetwork, findShortestPath, isInNetwork
 * 
 * @author Carter Mauer, Colton, Genesis , Bella
 */

import java.util.*;

public class SocialNetwork {
	//Edge class represents a connection between two students (target) with a weight (wait time) and implements the Comparable interface
    class Edge implements Comparable<Edge>{
    	//the student this edge points to 
        int target;
        //the wait time between the students
        int weight;
        //constructor
        public Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
        /**
         * Method: compareTo
         * Purpose: overrides a method in the interface, Comparable. Compare edges by their weight for priority queue sorting
         * @param Edge other - the object to compare the current object to
         * @return Integer - returns negative value if this.weight<other.weight, returns zero if this.weight= other.weight, returns positive value if this.weight>other.weight
         */
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
    //list to hold student names by their ID index
    private ArrayList<String> names = new ArrayList<>();

    /**
     * Method: addStudentName
     * Purpose:add a student's name to the network
     * @param String name - the name to be added to the network
     */
    public void addStudentName(String name) { 
        names.add(name);
    }
    
    // The adjacencyList is a hash map where each key is a student ID (an integer), and each value is a linked list of Edge objects 
    //Each Edge represents a connection to another student, with the target being the connected student's ID and the weight being wait time
    private Map<Integer, LinkedList<Edge>> adjacencyList = new HashMap<>();

    /**
     * Method: getEdges
     * Purpose: find the list of edges (connections) for a given student (source)
     * @param int source - the student ID for which the connections are fetched
     * @return LinkedList<Edge> - list of edges for the given student
     */
    public LinkedList<Edge> getEdges(int source) {
        return adjacencyList.get(source);
    }
    
    /**
     * Method: addEdge
     * Purpose: Adds an edge (connection) between two students with a specified weight (wait time)
     * @param source - the student who is making the connection
     * @param target - the student being connected to
     * @param weight - the weight of the connection (wait time)
     */
    public void addEdge(int source, int target, int weight) {
    	//if the source student doesn't already exist in the adjacency list, create a new entry for them
        if(!(adjacencyList.containsKey(source))) {
            adjacencyList.put(source, new LinkedList<>());
        }
        //add the connection (edge) between the source student and the target student
        adjacencyList.get(source).add(new Edge(target, weight));
    }
    
    /**
     * Method: removeEdge
     * Purpose: Removes an edge (connection) between two students
     * @param source - the student who is removing the connection
     * @param target - the student to be removed from the source's connections
     */
    public void removeEdge(int source, int target) {
    	//extract edges (connections) of source into new linked list
        LinkedList<Edge> edges = getEdges(source);
        //if there are no connections for the source, inform the user and return
        if (edges == null) {
            System.out.println("No connections exist for student " + source);
            return;
        }
        
        boolean removed = false;
        //iterate through the edges of the source student 
        for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext(); ) {
            Edge edge = iterator.next();
            //if target student is found, remove that connection
            if (edge.target == target) {
                iterator.remove(); 
                //update boolean
                removed = true;     
                break;  
            }
        }
        //if no connections were removed and therefore the target was not found, inform user
        if (!removed) System.out.println("Student " + target + " was not found in the network of student " + source);
    }
    /**
     * Method: updateWeight
     * Purpose: Updates the weights (wait times) of all connections for a specific student
     * @param source - the student whose connection weights are being updated
     * @param weightChange - the change to be added to each connection's weight
     */
    public void updateWeight(int source, int weightChange) {
    	//extract edges (connections) of source into new linked list
        LinkedList<Edge> edges = getEdges(source);
        //if no edges exist for the source, nothing happens
        if(edges != null) {
        	//if edges exist for the source, for each edge, update its weight by adding weightChange to the current weight
            for(Edge edge : edges) {
                edge.weight = edge.weight + weightChange;
            }
        }
    }
    
    /**
     * Method: printNetwork
     * Purpose: Prints the list of students that a specific student is connected to, along with their wait times
     * @param source - the student whose network is being printed
     */
    public void printNetwork(int source) {
    	//extract edges (connections) of source into new linked list
        LinkedList<SocialNetwork.Edge> edges = getEdges(source);
        StringBuilder result = new StringBuilder("[");
        //iterate through the edges to create a string representation of the network
        for (int i = 0; i < edges.size(); i++) {
            result.append(edges.get(i).target + " : " + names.get(edges.get(i).target - 1));
            //if there is more than one edge left in the list, add a comma and space
            if (i < edges.size() - 1) {
                result.append(", "); 
            }
        }
        result.append("]");
        System.out.println("Student "+ source + "'s social network: " + result.toString());
    }
    
    /**
     * Method: findShortestPath
     * Purpose: Uses Dijkstra's algorithm to find the shortest path between two students
     * The algorithm calculates the minimum "wait time" (or "distance") to travel from the source student to the target student.
     * It uses a priority queue to explore the shortest paths incrementally.
     * @param source - the student from which the shortest path starts
     * @param target - the student to reach from the source student
     */
    public void findShortestPath(int source, int target) {
    	//array to hold the shortest distance from the source to each student
        int[] distances = new int[adjacencyList.size() + 1];
        //array to track the previous student in the shortest path
        int[] previous = new int[adjacencyList.size() + 1];
        //initialize distances to infinity
        Arrays.fill(distances, Integer.MAX_VALUE);
        //initialize previous nodes to -1 (no previous node)
        Arrays.fill(previous, -1);
        //distance to source is 0
        distances[source] = 0;
        
        //priority queue to store the students to visit next, ordered by their current distance
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        //add the source node to the priority queue
        queue.add(new Edge(source, 0));
        //track which students have been visited
        Set<Integer> visited = new HashSet<>();
    
        // Dijkstra's algorithm loop to find the shortest path
        //loop breaks when queue is empty
        while (!queue.isEmpty()) {
        	//poll the student with the smallest current distance from the queue
            Edge current = queue.poll();
            //get the current student ID
            int currentStudent = current.target;
            
            //skip if this student has already been visited
            if (visited.contains(currentStudent)) continue;
            //mark the student as visited
            visited.add(currentStudent);
            //if the current student is the target, we're done
            if (currentStudent == target) break;
            
            //get the neighbors (connections) of the current student
            LinkedList<Edge> neighbors = getEdges(currentStudent);
            //if no neighbors, skip to the next student
            if (neighbors == null) continue;
            
            //explore each neighbor
            for (Edge neighbor : neighbors) {
            	//calculate the new possible distance to this neighbor
                int newDistance = distances[currentStudent] + neighbor.weight;
                //if the new calculated distance is shorter, update the distance and previous node
                if (newDistance < distances[neighbor.target]) {
                	//update the shortest known distance
                    distances[neighbor.target] = newDistance;
                    //update the previous student in the path
                    previous[neighbor.target] = currentStudent;
                    //add the neighbor to the queue with updated distance
                    queue.add(new Edge(neighbor.target, newDistance));
                }
            }
        }
        //if the target is unreachable, inform the user and return
        if (distances[target] == Integer.MAX_VALUE) {
            System.out.println("No path exists between students " + source + " and " + target + ".");
            return;
        }
    
        //Reconstruct the path by tracing back from the target to derive length and total weight
        List<Integer> path = new ArrayList<>();
        for (int at = target; at != -1; at = previous[at]) {
        	//add each student to the path list
            path.add(at);
        }
        //reverse the path list to show the path from source to target
        Collections.reverse(path);
        //print the shortest path and its length
        System.out.println("\nThe shortest path between students " + source + " and " + target +
                " takes " + distances[target] + " days, and passes through " + path.size() + " students.");
        System.out.println("Path: " + path);

    }
    
    /**
     * Method: printGraph
     * Purpose: Prints the entire graph (network) with all students and their connections
     */
    public void printGraph() {
    	//for each entry in the hash map, print the student's ID
        for (Map.Entry<Integer, LinkedList<Edge>> entry : adjacencyList.entrySet()) {
            System.out.print("Student " + entry.getKey() + ": ");
            //print each edge (target and weight) for the student
            for (Edge edge : entry.getValue()) {
                System.out.print("-> (" + edge.target + ", " + edge.weight + ") ");
            }
            System.out.println();
        }
    }
    
    /**
     * Method: isInNetwork
     * Purpose: Checks if a student exists in the network
     * @param source - the student ID to check
     * @return boolean - returns true if the student is in the network, otherwise false
     */
    public boolean isInNetwork(int source) {
    	//check if the source exists as a key in the hash map
        return adjacencyList.containsKey(source);
    }
}

