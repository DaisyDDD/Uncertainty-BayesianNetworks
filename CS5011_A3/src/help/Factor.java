package help;

import java.util.ArrayList;

public class Factor {
	
	String label;
	ArrayList<String> factors = new ArrayList<String>();
	private CPT fCPT;
	
	public Factor(Node n) {
		label = n.getLabel();
		fCPT = n.getCPT();
		factors.add(label);
		for(int i = 0;i<n.getParents().size();i++) {
			factors.add(n.getParents().get(i).getLabel());
		}
	}
	public Factor(ArrayList<String> factors) {
		this.factors=factors;
		fCPT = new CPT(factors);
		String[][] TValue = fCPT.getTValue();

	}
	public ArrayList<String> getFactors(){
		return factors;
	}
	public CPT getCPT(){
		return fCPT;
	}


}
