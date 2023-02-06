package help;

import java.util.ArrayList;

public class Node {

	private String label;
	private ArrayList<Node> parents = new ArrayList<Node>();
	private ArrayList<Node> children = new ArrayList<Node>();
	private ArrayList<Node> neibors = new ArrayList<Node>();
	private  CPT cpt;
	
	
	public Node(String label) {
		this.setLabel(label);
	}
	
	public void addCPTvalues(double ... vals) {
		this.cpt = new CPT(vals,label,parents);
	}

	
	public void addChild(Node n) {
		children.add(n);	
		neibors.add(n);
	}

	public void addParent(Node n) {
		parents.add(n);
		neibors.add(n);	
	}
	public void addNeibor(Node n) {
		neibors.add(n);
	}
	
	public CPT getCPT(){
		return cpt;
	}
	public ArrayList<Node> getParents() {
		return parents;
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Node> getNeibors() {
		return neibors;
	}

	public void setNeibors(ArrayList<Node> neibors) {
		this.neibors = neibors;
	}


}
