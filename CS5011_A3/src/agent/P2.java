package agent;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import help.BayesianNetwork;
import help.CPT;
import help.Factor;
import help.Node;

public class P2 {

	protected double result;
	protected String variable;
	protected String value;
	protected int numOfEntries=0;
	protected ArrayList<String> finalOrder = new ArrayList<String>();
	protected ArrayList<Factor> factors = new ArrayList<Factor>();
	BayesianNetwork bn = new BayesianNetwork();

	public P2(BayesianNetwork bn, String variable, String value) {
		this.bn = bn;
		this.variable = variable;
		if (value.equals("T"))
			this.value = "0";
		else
			this.value = "1";
//		this.order=order;

	}

	public void varElimination(String[] order) {
		createOrder(order);
//		System.out.println("order:" + finalOrder.size());
		createFactors();
//		System.out.println("factors:"+factors.size());

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
//		System.out.println("Final Factor Size:"+factors.size());
//		System.out.println(factors.get(0).getFactors().get(0));
		if (factors.size() == 1 && factors.get(0).getFactors().get(0).equals(variable)) {
			String[][] TValue = factors.get(0).getCPT().getTValue();
			for (int i = 1; i < TValue.length; i++) {
				for (int j = 0; j < TValue[0].length; j++) {
					if (TValue[0][j].equals(variable) && TValue[i][j].equals(value)) {
						result = (factors.get(0).getCPT().getPValue())[i - 1][0];
					}
				}
			}
		}
	}

	protected Factor joinMarginalise(ArrayList<Factor> toSumOut, String labelY) {
		
		Factor newFactor = null;
//		System.out.println("JoinM");
		// Join process

		if (toSumOut.size() > 1) {
			newFactor = joinProcess(toSumOut, labelY);
//		System.out.print(newFactor.getCPT().p);
			// Marginals process
			newFactor = margProcess(newFactor, labelY);
		}
		else if(toSumOut.size()==1) {
			newFactor = toSumOut.get(0);
//			newFactor.getCPT().printCPT();
			newFactor = margProcess(newFactor, labelY);
		}
		else {
			System.out.println("toSumOut size is 0 ");
		}
		return newFactor;
	}

	protected Factor margProcess(Factor newFactor, String labelY) {
		
//		System.out.println("Marg Process");
		String[][] TValue = newFactor.getCPT().getTValue();
		double[][] PValue = newFactor.getCPT().getPValue();
		String[][] NewTValue = new String[PValue.length / 2 + 1][TValue[0].length - 1];
		double[][] NewPValue = new double[PValue.length / 2][1];
		int[] marker = new int[PValue.length];
		int indexOfLabel = 0;
		int indexC = 0;
		// Find the index of label Y
		for (int j = 0; j < TValue[0].length; j++) {
			if (TValue[0][j].equals(labelY)) {
				indexOfLabel = j;
			} else {
				NewTValue[0][indexC] = TValue[0][j];
//				System.out.println(NewTValue[0][indexC]);
				indexC++;
			}
		}
		// Find the sum of two probability when labelY=T/F
		// marker is used to mark the rows will be deleted.
		int indexR = 0;
		for (int i = 1; i < TValue.length; i++) {
			String[] targetValues = new String[TValue[0].length];
			if (marker[i - 1] == 0) {
				String valueOfLabel = TValue[i][indexOfLabel];

				int index1 = 0;
				int factor = TValue[0].length - 1;
				for (int j = 0; j < TValue[0].length; j++) {
					targetValues[j] = TValue[i][j];
					index1 += (int) (Integer.valueOf(TValue[i][j]) * Math.pow(2, factor));
					factor--;
				}
//				marker[index1]=1;

				if (valueOfLabel.equals("1")) {
					targetValues[indexOfLabel] = "0";
				} else {
					targetValues[indexOfLabel] = "1";
				}

				int index2 = 0;
				factor = TValue[0].length - 1;
				for (int j = 0; j < TValue[0].length; j++) {
					index2 += (int) (Integer.valueOf(targetValues[j]) * Math.pow(2, factor));
					factor--;
				}
				marker[index2] = 1;
				indexC = 0;
				for (int k = 0; k < TValue[i].length; k++) {
					if (k != indexOfLabel) {
						NewTValue[indexR + 1][indexC] = TValue[i][k];
						indexC++;
					}
				}
//				NewTValueNewTValue[index+1][]
				NewPValue[indexR][0] = PValue[index1][0] + PValue[index2][0];
				indexR++;
			}
		}
		newFactor.getCPT().setPValue(NewPValue);
		newFactor.getCPT().setTValue(NewTValue);
		newFactor.getFactors().remove(labelY);

//		newFactor.getCPT().printCPT();
		return newFactor;
	}

	protected Factor joinProcess(ArrayList<Factor> toSumOut, String labelY) {
//		System.out.println("Join Process");
		Factor fC = null;
		for (int i = 0; i < toSumOut.size() - 1; i++) {
			
			Factor fA = toSumOut.get(i);
			Factor fB = toSumOut.get(i + 1);
			ArrayList<String> varA = fA.getFactors();
			ArrayList<String> varB = fB.getFactors();
//			System.out.println(varA.size());
//			System.out.println(varB.size());
			ArrayList<String> V1 = findV1(varA, varB);
			ArrayList<String> V2 = findV2(varA, varB);
			ArrayList<String> V3 = findV3(varA, varB);
			// ArrayList<String> varC = receiveUnionList(V1,V2,V3)
//			fA.getCPT().printCPT();
//			fB.getCPT().printCPT();
//			System.out.println(V1.size());
//			System.out.println(V1.get(0));
//			System.out.println(V2.size());
//			System.out.println(V3.size());
//			System.out.println(V3.get(0));
			ArrayList<String> V1clone = (ArrayList<String>) V1.clone();
			ArrayList<String> V2clone = (ArrayList<String>) V2.clone();
			ArrayList<String> V3clone = (ArrayList<String>) V3.clone();
			// V1 = V1&V2
			V2clone.removeAll(V1clone);
			V1clone.addAll(V2clone);
			// V1 = V1&V2&V3
			V3clone.removeAll(V1clone);
			V1clone.addAll(V3clone);
			ArrayList<String> varC = V1clone;

//			System.out.println(varC.size());

			// combination of truth values for V1, V2, V3
			// fA*fB
			numOfEntries+=Math.pow(2, varC.size());
			fC = new Factor(varC);
			double[][] pValue = new double[fC.getCPT().getTValue().length - 1][1];
//			CPT fc_CPT = fC.getCPT();
			String[][] TValueC = fC.getCPT().getTValue();
			double[][] PValueC = fC.getCPT().getPValue();
			String[][] TValueA = fA.getCPT().getTValue();
			double[][] PValueA = fA.getCPT().getPValue();
			String[][] TValueB = fB.getCPT().getTValue();
			double[][] PValueB = fB.getCPT().getPValue();

			for (int k = 1; k < TValueC.length; k++) {

				// Find probability value p1 in fA
				ArrayList<String> target1 = new ArrayList<String>();
				for (int m = 0; m < TValueA[0].length; m++) {
					String var = TValueA[0][m];
					for (int n = 0; n < TValueC[0].length; n++) {
						String label = TValueC[0][n];
						if (label.equals(var)) {
							target1.add(TValueC[k][n]);
							break;
						}
					}
				}
//				System.out.print(target.get(0));
				int index = 0;
				int factor = 0;
				for (int len = target1.size() - 1; len >= 0; len--) {
					index += (int) (Integer.valueOf(target1.get(len)) * Math.pow(2, factor));
					factor++;
				}
				double p1 = PValueA[index][0];
//				System.out.println("p1 "+p1);

				// Find probability value p2 in fB
				ArrayList<String> target2 = new ArrayList<String>();
//				System.out.println(var);
				for (int m = 0; m < TValueB[0].length; m++) {
					String var = TValueB[0][m];
					for (int n = 0; n < TValueC[0].length; n++) {
						String label = TValueC[0][n];
						if (label.equals(var)) {
							target2.add(TValueC[k][n]);
						}
					}
				}
				index = 0;
				factor = 0;
				for (int len = target2.size() - 1; len >= 0; len--) {
					index += (int) (Integer.valueOf(target2.get(len)) * Math.pow(2, factor));
					factor++;
				}
				double p2 = PValueB[index][0];
//				System.out.println("p2 " + p2);

				pValue[k - 1][0] = p1 * p2;
			}
			fC.getCPT().setPValue(pValue);
//			fC.getCPT().printCPT();

			toSumOut.add(fC);
			i++;

		}
		return fC;

	}

	protected ArrayList<String> findV1(ArrayList<String> varA, ArrayList<String> varB) {
		ArrayList<String> V1 = new ArrayList<String>();
		for (int i = 0; i < varA.size(); i++) {
			String labelA = varA.get(i);
			for (int j = 0; j < varB.size(); j++) {
				String labelB = varB.get(j);
				if (labelA == labelB) {
					V1.add(varA.get(i));
					break;
				}
			}
		}
		return V1;
	}

	protected ArrayList<String> findV2(ArrayList<String> varA, ArrayList<String> varB) {
		ArrayList<String> V2 = new ArrayList<String>();
		boolean flag = false;
		for (int i = 0; i < varA.size(); i++) {
			String labelA = varA.get(i);
			flag = false;
			for (int j = 0; j < varB.size(); j++) {
				String labelB = varB.get(j);
				if (labelA.equals(labelB)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				// Didn't find labelA in f2
				V2.add(varA.get(i));
			}
		}
		return V2;
	}

	protected ArrayList<String> findV3(ArrayList<String> varA, ArrayList<String> varB) {
		ArrayList<String> V3 = new ArrayList<String>();
		boolean flag = false;
		for (int i = 0; i < varB.size(); i++) {
			String labelB = varB.get(i);
			flag = false;
			for (int j = 0; j < varA.size(); j++) {
				String labelA = varA.get(j);
				if (labelA == labelB) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				// Didn't find labelA in f2
				V3.add(varB.get(i));
			}

		}
		return V3;
	}

	protected void createOrder(String[] order) {
		Node query = findNode(variable);
//		System.out.println(query);
		ArrayList<Node> ancestor = getAncestor(query);
//		System.out.println("Ancestor size: " +ancestor.size());
		if (ancestor.size() > 0) {
			for (int i = 0; i < order.length; i++) {
				String name = order[i];
				Node nd = findNode(name);
				for (int j = 0; j < ancestor.size(); j++) {
					if (ancestor.get(j).getLabel().equals(nd.getLabel())) {
						finalOrder.add(name);
					}
				}
			}
		}

	}

	protected void createFactors() {
		ArrayList<Node> nodes = bn.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			String label = n.getLabel();

			if (label.equals(variable)) {
				Factor f = new Factor(n);
				factors.add(f);
			}

			for (int j = 0; j < finalOrder.size(); j++) {
				String label2 = finalOrder.get(j);

				if (label2.equals(label)) {
//					System.out.println(label2);
					Factor f = new Factor(n);
					factors.add(f);
				}
			}

		}
	}

	protected ArrayList<Node> getAncestor(Node n) {
		ArrayList<Node> ancestors = new ArrayList<Node>();
		ArrayList<Node> parents = n.getParents();
//		System.out.print(parents.size());
		for (int i = 0; i < parents.size(); i++) {
			Node oneParent = parents.get(i);
			if (!ancestors.contains(oneParent)) {
				ancestors.add(oneParent);
			}
			ancestors.addAll(getAncestor(oneParent));
		}
		return ancestors;
	}

	protected Node findNode(String variable) {
		ArrayList<Node> nodes = bn.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getLabel().equals(variable)) {
				return nodes.get(i);
			}
		}
		return null;
	}

	public double getResult() {
		return result;
	}
	public int getNum() {
		return numOfEntries;
	}
}
