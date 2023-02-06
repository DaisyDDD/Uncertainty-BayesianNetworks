package agent;

import java.util.ArrayList;
import java.util.Collections;

import help.BayesianNetwork;
import help.Node;

public class P4 extends P3 {

	private String[] finalOrder = new String[bn.getNodes().size()-1];

	public P4(BayesianNetwork bn, String variable, String value, ArrayList<String[]> evidence) {
		super(bn, variable, value, evidence);

	}

	public String[] maxCardinality() {
		bn.InducedGraph();
		ArrayList<Node> unmarked = new ArrayList<Node>();
		ArrayList<Node> marked = new ArrayList<Node>();
		ArrayList<String> order = new ArrayList<String>();
		unmarked.addAll(bn.getNodes());

		for (int i = 0; i < bn.getNodes().size(); i++) {
			int maxNum = 0;
			int flag = 0;
			int j;
			if(i==0) {
				for (int k = 0; k < bn.getNodes().size(); k++) {
					Node n=bn.getNodes().get(k);
					if(n.getLabel().equals(variable)) {
						flag = k;
					}
				}
			}
			else {
				// find the node in the unmarked with maximum n. of marked neighbours
				for (int k = 0; k < bn.getNodes().size(); k++) {
					Node n = bn.getNodes().get(k);
					if (unmarked.contains(n)) {
						
						// Count the number of marked neighbors of node n
						int numOfMarkedNeibor = 0;
						for (j = 0; j < n.getNeibors().size(); j++) {
							if (marked.contains(n.getNeibors().get(j))) {
								numOfMarkedNeibor++;
							}
						}
						if (numOfMarkedNeibor > maxNum) {
							maxNum = numOfMarkedNeibor;
							flag = k;
						}
					}
				}
			}

			
			
			
			order.add(bn.getNodes().get(flag).getLabel());
//			System.out.println("Add label "+bn.getNodes().get(flag).getLabel());
			unmarked.remove(bn.getNodes().get(flag));
			marked.add(bn.getNodes().get(flag));
		}
		
		Collections.reverse(order);
		for(int i=0;i<order.size();i++) {
			if(order.get(i).equals(variable)) {
				order.remove(i);
				break;
			}
		}
		System.out.print("Final Order: ");
		for(int i=0;i<order.size();i++) {
			finalOrder[i]=order.get(i);
			System.out.print(finalOrder[i]+" ");
			
		}
		System.out.println();
		return finalOrder;
	}

	public String[] getOrder() {
		return finalOrder;
	}

	public void setOrder(String[] order) {
		this.finalOrder = order;
	}

}
