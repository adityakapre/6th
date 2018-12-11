/*
This problem can be solved by just simple graph traversal, such as depth-first search or breadth-first search.
We start with one of the two nodes and, during traversal, check if the other node is found. We should mark
any node found in the course of the algorithm as "already visited" to avoid cycles and repetition of the
nodes.
It may be worth discussing with your interviewer the tradeoffs between breadth-first search and depth-first
search for this and other problems. For example, depth-first search is a bit simpler to implement since it can
be done with simple recursion. Breadth-first search can also be useful to find the shortest path, whereas
depth-first search may traverse one adjacent node very deeply before ever going onto the immediate
neighbors.
*/
package Q4_01_Route_Between_Nodes;

import java.util.LinkedList;

public class Question {
	public enum State {
		Unvisited, Visited, Visiting;
	} 

	public static void main(String a[])
	{
		Graph g = createNewGraph();
		Node[] n = g.getNodes();
		Node start = n[3];
		Node end = n[5];
		System.out.println(search(g, start, end));
	}
	
	public static Graph createNewGraph()
	{
		Graph g = new Graph();        
		Node[] temp = new Node[6];

		temp[0] = new Node("a", 3);
		temp[1] = new Node("b", 0);
		temp[2] = new Node("c", 0);
		temp[3] = new Node("d", 1);
		temp[4] = new Node("e", 1);
		temp[5] = new Node("f", 0);

		temp[0].addAdjacent(temp[1]);
		temp[0].addAdjacent(temp[2]);
		temp[0].addAdjacent(temp[3]);
		temp[3].addAdjacent(temp[4]);
		temp[4].addAdjacent(temp[5]);
		for (int i = 0; i < 6; i++) {
			g.addNode(temp[i]);
		}
		return g;
	}

    public static boolean search(Graph g,Node start,Node end) {  
        LinkedList<Node> q = new LinkedList<Node>();
        for (Node u : g.getNodes()) {
            u.state = State.Unvisited;
        }
        start.state = State.Visiting;
        q.add(start);
        Node u;
        while(!q.isEmpty()) {
            u = q.removeFirst();
            if (u != null) {
	            for (Node v : u.getAdjacent()) {
	                if (v.state == State.Unvisited) {
	                    if (v == end) {
	                        return true;
	                    } else {
	                        v.state = State.Visiting;
	                        q.add(v);
	                    }
	                }
	            }
	            u.state = State.Visited;
            }
        }
        return false;
    }
}
