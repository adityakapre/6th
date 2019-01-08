package Q4_07_Build_Order.EdgeRemoval;

import java.util.ArrayList;

public class Question {
	
	//start here ...
	public static Project[] orderProjects(Graph graph) {
		ArrayList<Project> projects = graph.getNodes();
		Project[] output = new Project[projects.size()];
		
		/* Add “roots” to the build order first.*/
		int endOfList = addNonDependent(output, projects, 0);
		
		int toBeProcessed = 0;
		while (toBeProcessed < output.length) { //toBeProcessed=order.length indicates all projects compiled
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
	
	public static String[] convertToStringList(Project[] projects) {
		String[] buildOrder = new String[projects.length];
		for (int i = 0; i < projects.length; i++) {
			buildOrder[i] = projects[i].getName();
		}
		return buildOrder;
	}
}
	
class Project {
	private ArrayList<Project> children = new ArrayList<Project>();
	private HashMap<String, Project> map = new HashMap<String, Project>();
	private String name;
	private int dependencies = 0;
	
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
			node.incrementDependencies();
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
