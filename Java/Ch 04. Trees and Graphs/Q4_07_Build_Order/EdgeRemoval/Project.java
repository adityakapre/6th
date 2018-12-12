/*
Project is a Vertex is graph and has its children in Project are Vertexes to which this.Project object has edge

instance variable "dependencies" represents number of incoming edges in graph for this.Project object
*/
package Q4_07_Build_Order.EdgeRemoval;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {
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
