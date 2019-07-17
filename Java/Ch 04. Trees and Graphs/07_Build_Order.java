/*
Q:
Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of
projects, where the second project is dependent on the first project). All of a project's dependencies
must be built before the project is. Find a build order that will allow the projects to be built. If there
is no valid build order, return an error.
EXAMPLE
Input:
projects: a, b, c, d, e, f
dependencies: (a, d), (f, b), (b, d), (f, a), (d, c)
Output: f, e, a, b, d, c

A:
Visualizing the information as a graph probably works best. Be careful with the direction of the arrows. In
the graph below, an arrow from d to g means that d must be compiled before g. You can also draw them
in the opposite direction, but you need to consistent and clear about what you mean. Let's draw a fresh
example.

In drawing this example (which is not the example from the problem description), I looked for a few things.
I wanted the nodes labeled somewhat randomly. If I had instead put a at the top, with b and c as children,
then d and e, it could be misleading. The alphabetical order would match the compile order.
I wanted a graph with multiple parts/components, since a connected graph is a bit of a special case.
I wanted a graph where a node links to a node that cannot immediately follow it. For example, f links to
a but a cannot immediately follow it (since b and c must come before a and after f).
I wanted a larger graph since I need to figure out the pattern.
I wanted nodes with multiple dependencies.
Now that we have a good example, let's get started with an algorithm.

In below graph, lower nodes are dependent on upper nodes
So all arrows are like |
                       V 

         f                  d
       / | \                |
     c   |   b              g
       \ |  //
         a  /
         | /
         e 

Solution#l
Where do we start? Are there any nodes that we can definitely compile immediately?
Yes. Nodes with no incoming edges can be built immediately since they don't depend on anything. Let's
add all such nodes to the build order. In the earlier example, this means we have an order of f, d (or d, f).
Once we've done that, it's irrelevant that some nodes are dependent on d and f since d and f have already
been built. We can reflect this new state by removing d and f's outgoing edges.
build order: f, d

Next, we know that c , b, and g are free to build since they have no incoming edges. Let's build those and
then remove their outgoing edges.

Project a can be built next, so let's do that and remove its outgoing edges. This leaves just e. We build that
next, giving us a complete build order.
build order: f, d, c, b, g, a, e

Did this algorithm work, or did we just get lucky? Let's think about the logic.
1. We first added the nodes with no incoming edges. If the set of projects can be built, there must be some
"first" project, and that project can't have any dependencies. If a project has no dependencies (incoming
edges), then we certainly can't break anything by building it first.
2. We removed all outgoing edges from these roots. This is reasonable. Once those root projects were built,
it doesn't matter if another project depends on them.
3. After that, we found the nodes that now have no incoming edges. Using the same logic from steps 1 and
2, it's okay if we build these. Now we just repeat the same steps: find the nodes with no dependencies,
add them to the build order, remove their outgoing edges, and repeat.
4. What if there are nodes remaining, but all have dependencies (incoming edges)? This means there's no
way to build the system. We should return an error.

The implementation follows this approach very closely.
Initialization and setup:
  1. Build a graph where each project is a node and its outgoing edges represent the projects that depend
  on it. That is, if A has an edge to B (A-> B), it means B has a dependency on A and therefore A must be
  built before B. Each node also tracks the number of incoming edges.
  2. Initialize a buildOrder array. Once we determine a project's build order, we add it to the array. We also
  continue to iterate through the array, using a toBeProcessed pointer to point to the next node to be
  fully processed.
  3. Find all the nodes with zero incoming edges and add those to a buildOrder array. Set a toBeProcessed pointer to 
  the beginning of the array. Repeat until toBeProcessed is at the end of the buildOrder:
      1. Read node at toBeProcessed.
      » If node is null, then all remaining nodes have a dependency and we have detected a cycle.
      2. For each child of node:
      » Decrement child. dependencies (the number of incoming edges).
      » If child. dependencies is zero, add child to end of buildOrder.
      3. Increment toBeProcessed.


Solution #2
Alternatively, we can use depth-first search (DFS) to find the build path.

         f                  d
       / | \                |
     c   |   b              g
       \ |  // \
         a  /   h
         | /
         e 

Suppose we picked an arbitrary node (say b) and performed a depth-first search on it. When we get to the
end of a path and can't go any further (which will happen at h and e), we know that those terminating
nodes can be the last projects to be built. No projects depend on them.
DFS(b) // Step 1
  DFS(h) // Step 2
    build order ... , h // Step 3
  DFS(a) // Step 4
    DFS(e) // Step 5
      build order ... , e, h // Step 6
// Step 7+
Now, consider what happens at node a when we return from the DFS of e. We know a's children need to
appear after a in the build order. So, once we return from searching a's children (and therefore they have
been added), we can choose to add a to the front of the build order.

Once we return from a, and complete the DFS of b's other children, then everything that must appear after
b is in the list. Add b to the front.
DFS(b)                                // Step 1
  DFS(h)                              // Step 2
    build order. .. , h               // Step 3
  DFS(a)                              // Step 4
     DFS(e)                           // Step 5
        build order = ... , e, h      // Step 6
   build order = ... , a, e, h        // Step 7
  DFS(e) -> return                    // Step 8
  build order = ... , b, a, e, h      // Step 9

Let's mark these nodes as having been built too, just in case someone else needs to build them.

Now what? We can start with any old node again, doing a DFS on it and then adding the node to the front
of the build queue when the DFS is completed.
DFS(d)
    DFS(g)
        build order = ... , g, b, a, e, h
    build order = ... , d, g, b, a, e, h
DFS(f)
    DFS(c)
      build order = ... , c, d, g, b, a, e, h
    build order= f, c, d, g, b, a, e, h

In an algorithm like this, we should think about the issue of cycles. There is no possible build order if there
is a cycle. But still, we don't want to get stuck in an infinite loop just because there's no possible solution.
A cycle will happen if, while doing a DFS on a node, we run back into the same path. What we need therefore
is a signal that indicates "I'm still processing this node, so if you see the node again, we have a problem:'
What we can do for this is to mark each node as a "partial" (or "is visiting") state just before we start
the DFS on it. If we see any node whose state is partial, then we know we have a problem. When we're
done with this node's DFS, we need to update the state.
We also need a state to indicate "I've already processed/built this node" so we don't re-build the node. Our
state therefore can have three options: COMPLETED, PARTIAL, and BLANK.

Like the earlier algorithm, this solution is O(P+D) time, where P is the number of projects and D is the
number of dependency pairs.

By the way, this problem is called topological sort: linearly ordering the vertices in a graph such that for
every edge (a, b), a appears before b in the linear order.

*/
package Q4_07_Build_Order.EdgeRemoval;

import java.util.ArrayList;

public class Question {
	
	/* Build the graph, adding the edge (a, b) if b is dependent on a. 
	 * Assumes a pair is listed in “build order”. The pair (a, b) in 
	 * dependencies indicates that b depends on a and a must be built
	 * before b. */
	public static Graph buildGraph(String[] projects, String[][] dependencies) {
		Graph graph = new Graph();
		for (String project : projects) {
			graph.getOrCreateNode(project);
		}
		
		for (String[] dependency : dependencies) {
			String first = dependency[0];
			String second = dependency[1];
			graph.addEdge(first, second);
		}
		
		return graph;
	}
	
	// actual start here ...
	public static Project[] orderProjects(Graph graph) {
		ArrayList<Project> projects = graph.getNodes();
		Project[] output = new Project[projects.size()];
		
		/* Add “roots” to the build order first.*/
		int endOfList = addNonDependent(output, projects, 0);
		
		int toBeProcessed = 0;
		//output is like a queue here
		//toBeProcessed=order.length indicates all projects compiled
		while (toBeProcessed < output.length) { 
			Project current = output[toBeProcessed];
			
			/* We have a circular dependency since there are no remaining
			 * projects with zero dependencies. */ 
			if (current == null) {
				return null;
			}
			
			/* Remove myself as a dependency. */
			ArrayList<Project> children = current.getChildren();
			for (Project child : children) {
				child.decrementDependencies();
			}			
			
			/* Add children that have no one depending on them. */
			endOfList = addNonDependent(output, children, endOfList);
			
			toBeProcessed++;
		}
		
		return output;
	}
	
	/* A helper function to insert projects with zero dependencies 
	 * into the order array, starting at index offset. */
	public static int addNonDependent(Project[] output, ArrayList<Project> projects, int endOfList) {
		for (Project project : projects) {
			if (project.getNumberDependencies() == 0) {
				output[endOfList] = project;
				endOfList++;
			}
		}
		return endOfList;
	}
	
	public static String[] buildOrderWrapper(String[] projects, String[][] dependencies) {
		Project[] buildOrder = findBuildOrder(projects, dependencies);
		if (buildOrder == null) return null;
		String[] buildOrderString = convertToStringList(buildOrder);
		return buildOrderString;
	}
	
	public static Project[] findBuildOrder(String[] projects, String[][] dependencies) {
		Graph graph = buildGraph(projects, dependencies);
		return orderProjects(graph);
	}
	
	public static String[] convertToStringList(Project[] projects) {
		String[] buildOrder = new String[projects.length];
		for (int i = 0; i < projects.length; i++) {
			buildOrder[i] = projects[i].getName();
		}
		return buildOrder;
	}
	
	public static void main(String[] args) {
		String[] projects = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
		String[][] dependencies = {
				{"a", "b"},
				{"b", "c"},
				{"a", "c"},
				{"a", "c"},
				{"d", "e"},
				{"b", "d"},
				{"e", "f"},
				{"a", "f"},
				{"h", "i"},
				{"h", "j"},
				{"i", "j"},
				{"g", "j"}};
		String[] buildOrder = buildOrderWrapper(projects, dependencies);
		if (buildOrder == null) {
			System.out.println("Circular Dependency.");
		} else {
			for (String s : buildOrder) {
				System.out.println(s);
			}
		}
	}
}
	
class Project {
	private ArrayList<Project> children = new ArrayList<Project>();	//OUTGOING EDGES
	private HashMap<String, Project> map = new HashMap<String, Project>();
	private String name;
	private int dependencies = 0; //NO OF INCOMING EDGES
	
	public Project(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}
	
	public void addNeighbor(Project node) {
		if (!map.containsKey(node.getName())) {
			children.add(node);
			map.put(node.getName(), node);
			node.incrementDependencies();  //INCREMENT NO OF INCOMING EDGES I.E PROJECT DEPENDENCIES
		}
	}
	
	public void incrementDependencies() {
		dependencies++;
	}
	
	public ArrayList<Project> getChildren() {
		return children;
	}
	
	public void decrementDependencies() {
		dependencies--;
	}
	
	public int getNumberDependencies() {
		return dependencies;
	}
}

class Graph {
	private ArrayList<Project> nodes = new ArrayList<Project>();
	private HashMap<String, Project> map = new HashMap<String, Project>();
	
	public Project getOrCreateNode(String name) {
		if (!map.containsKey(name)) {
			Project node = new Project(name);
			nodes.add(node);
			map.put(name, node);
		}
		
		return map.get(name);
	}
	
	public void addEdge(String startName, String endName) {
		Project start = getOrCreateNode(startName);
		Project end = getOrCreateNode(endName);
		start.addNeighbor(end);
	}
	
	public ArrayList<Project> getNodes() {
		return nodes;
	}
}
	
}


/*
A:
DFS approach
We start by finding nodes that can be built last (no nodes depend on them) and add such nodes to stack
So stack starts by pushing nodes that can be built last.
therefore, Stack top contains nodes that can be built first
*/

package Q4_07_Build_Order.DFS;

import java.util.ArrayList;
import java.util.Stack;

public class Question {
	
	//start here ...
	public static Stack<Project> orderProjects(Graph graph) {
		ArrayList<Project> projects = graph.getNodes();
		Stack<Project> stack = new Stack<Project>();
		for (Project project : projects) {
			if (project.getState() == Project.State.BLANK) {
				if (!doDFS(project, stack)) {
					return null;
				}
			}
		}
		return stack;
	}
	
	public static boolean doDFS(Project project, Stack<Project> stack) {
		if (project.getState() == Project.State.PARTIAL) { //Check if project was seen before
			return false; // Cycle
		}
		
		if (project.getState() == Project.State.BLANK) {
			project.setState(Project.State.PARTIAL);
			ArrayList<Project> children = project.getChildren();
			for (Project child : children) {
				if (!doDFS(child, stack)) {
					return false;
				}
			}
			project.setState(Project.State.COMPLETE);
			/*
			push leaf nodes (dependent projects) to bottom of stack
			so that when we unwind stack, projects with no dependencies pop out first
			*/
			stack.push(project);
		}
		return true;
	}
	
	public static void main(String[] args) {
		String[] projects = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
		String[][] dependencies = {
				{"a", "b"},
				{"b", "c"},
				{"a", "c"},
				{"d", "e"},
				{"b", "d"},
				{"e", "f"},
				{"a", "f"},
				{"h", "i"},
				{"h", "j"},
				{"i", "j"},
				{"g", "j"}};
		String[] buildOrder = buildOrderWrapper(projects, dependencies);
		if (buildOrder == null) {
			System.out.println("Circular Dependency.");
		} else {
			for (String s : buildOrder) {
				System.out.println(s);
			}
		}
	}
	
	public static String[] buildOrderWrapper(String[] projects, String[][] dependencies) {
		Stack<Project> buildOrder = findBuildOrder(projects, dependencies);
		if (buildOrder == null) return null;
		String[] buildOrderString = convertToStringList(buildOrder);
		return buildOrderString;
	}

	public static Stack<Project> findBuildOrder(String[] projects, String[][] dependencies) {
		Graph graph = buildGraph(projects, dependencies);
		return orderProjects(graph);
	}
	
	/* Build the graph, adding the edge (a, b) if b is dependent on a. 
	 * Assumes a pair is listed in “build order” (which is the reverse 
	 * of dependency order). The pair (a, b) in dependencies indicates
	 * that b depends on a and a must be built before a. */
	public static Graph buildGraph(String[] projects, String[][] dependencies) {
		Graph graph = new Graph();
		
		for (String[] dependency : dependencies) {
			String first = dependency[0];
			String second = dependency[1];
			graph.addEdge(first, second);
		}
		
		return graph;
	}
	
	public static String[] convertToStringList(Stack<Project> projects) {
		String[] buildOrder = new String[projects.size()];
		for (int i = 0; i < buildOrder.length; i++) {
			buildOrder[i] = projects.pop().getName();
		}
		return buildOrder;
	}
}

public class Project {
	public enum State {COMPLETE, PARTIAL, BLANK};
	private ArrayList<Project> children = new ArrayList<Project>();
	private HashMap<String, Project> map = new HashMap<String, Project>();
	private String name;
	private State state = State.BLANK;
	
	public Project(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}
	
	public void addNeighbor(Project node) {
		if (!map.containsKey(node.getName())) {
			children.add(node);
			map.put(node.getName(), node);
		}
	}
	
	public State getState() { 
		return state;
	}
	
	public void setState(State st) {
		state = st;
	}
	
	public ArrayList<Project> getChildren() {
		return children;
	}
}
