package agent;

import java.util.ArrayList;

import help.BayesianNetwork;
import help.Factor;
import help.Node;

public class P3 extends P2{

	private ArrayList<String[]> evidence;
	public P3(BayesianNetwork bn, String variable, String value, ArrayList<String[]> evidence) {
		super(bn,variable,value);
		this.setEvidence(evidence);
	}
	
	public void varEliminationP3(String[] order) {
		createOrder(order);
//		System.out.println("order:" + finalOrder.size());
		createFactors();
//		System.out.println("factors:"+factors.size());
		
		// project evidence for related factors
		for(int k=0;k<evidence.size();k++) {
			String evid = evidence.get(k)[0];
			String evidValue = evidence.get(k)[1];
			for(int i=0;i<factors.size();i++) {
				ArrayList<String> vars=factors.get(i).getFactors();
				for(int j=0;j<vars.size();j++) {
					if(vars.get(j).equals(evid)) {
						factors.get(i).getCPT().changePValue(evid,evidValue);
//						factors.get(i).getCPT().printCPT();
						break;
					}
				}
			}
		}
		
		for (int i = 0; i < finalOrder.size(); i++) {
			String labelY = finalOrder.get(i);
			ArrayList<Factor> toSumOut = new ArrayList<Factor>();

			// Find all Factors that contain label Y
			// Remove all Factors containing Y from [Factors]

			for (int j = 0; j < factors.size(); j++) {
//				System.out.println(factors.get(j).getFactors().get(0));
				ArrayList<String> curVar = factors.get(j).getFactors();
				for (int k = 0; k < curVar.size(); k++) {
					if (curVar.get(k).equals(labelY)) {
						toSumOut.add(factors.get(j));
						factors.remove(j);
						j--;
					}
				}
			}
//			System.out.println("toSumOut:"+toSumOut.size());
			// Create New Factor
			Factor newFactor = joinMarginalise(toSumOut, labelY);

			factors.add(newFactor);

		}
//		System.out.print(factors.size());
		String[][] TValue = null;
		double[][] PValue = null;
		if (factors.size() == 1) {
			TValue = factors.get(0).getCPT().getTValue();
			PValue = factors.get(0).getCPT().getPValue();
			factors.get(0).getCPT().Normalise();
		}
		else if(factors.size() >= 2) {
			Factor finalFactor = joinProcess(factors,variable);
			finalFactor.getCPT().Normalise();
			TValue = finalFactor.getCPT().getTValue();
			PValue = finalFactor.getCPT().getPValue();
		}
		for (int i = 1; i < TValue.length; i++) {
			for (int j = 0; j < TValue[0].length; j++) {
				if (TValue[0][j].equals(variable) && TValue[i][j].equals(value)) {
					result = PValue[i - 1][0];
				}
			}
		}
		
	}
	

	@Override
	protected void createOrder(String[] order) {
		Node query = findNode(variable);
//		System.out.println(query);
		ArrayList<Node> ancestorOfQuery = getAncestor(query);
//		System.out.println("Ancestor size: " +ancestorOfQuery.size());
		boolean flag=false;
		for(int h=0;h<order.length;h++) {
			flag=false;
			String name = order[h];
			// If a node is the ancestor of Query
			for(int j=0;j<ancestorOfQuery.size();j++) {
				if(ancestorOfQuery.get(j).getLabel().equals(name)) {
					finalOrder.add(name);
//					System.out.println(name);
					flag=true;
					break;
				}
				
			}
			if(!flag) {
				for(int j=0;j<evidence.size();j++) {
					boolean flag2=false;
					String evidenceLabel = evidence.get(j)[0];
					Node evidenceNode = findNode(evidenceLabel);
//					System.out.print("Evid Label: "+evidenceLabel);
					// If a node is evidence
					if(name.equals(evidenceLabel)) {
						finalOrder.add(name);
//						System.out.println(name);
						break;
					}
					// If a node is ancestors of evidence.
					ArrayList<Node> ancestorOfEvid = getAncestor(evidenceNode);
					for(int k=0;k<ancestorOfEvid.size();k++) {
						if(ancestorOfEvid.get(k).getLabel().equals(name)) {
							finalOrder.add(name);
//							System.out.println(name);
							flag2=true;
							break;
						}
					}
					
					if(flag2) {
						break;
					}
				}
			}	
		}
	}
	public ArrayList<String[]> getEvidence() {
		return evidence;
	}
	public void setEvidence(ArrayList<String[]> evidence) {
		this.evidence = evidence;
	}
}
