package help;

import java.util.ArrayList;
import help.Node;
public class BayesianNetwork {

	private ArrayList<Node> nodes = new ArrayList<Node>();
	

	public Node addNode(String label) {
		Node n = new Node(label);
		nodes.add(n);
		return n;
	}


	public void addEdge(Node a, Node b) {
		if(nodes.contains(b)&& nodes.contains(a)) {
			a.addChild(b);
			b.addParent(a);
		}
	}
	
	public ArrayList<Node> getNodes(){
		return nodes;
	}


	public void InducedGraph() {
		for(int num=0;num<nodes.size();num++) {
			Node n=nodes.get(num);
			if(n.getParents().size()>=2) {
				ArrayList<Node> parents = n.getParents();
				for(int i=0;i<parents.size()-1;i++) {
					Node nParent=parents.get(i);
					for(int j=i+1;j<parents.size();j++) {
						Node mParent=parents.get(j);
						if(!nParent.getNeibors().contains(mParent)) {
							nParent.addNeibor(mParent);
						}
						if(!mParent.getNeibors().contains(nParent)) {
							mParent.addNeibor(nParent);
						}
					}
				}
			}
		}
		
	}
	
}
