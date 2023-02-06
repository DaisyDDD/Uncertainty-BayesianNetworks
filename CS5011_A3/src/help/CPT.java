package help;

import java.util.ArrayList;
import java.util.Stack;

public class CPT {
	private String[][] TValue;
	private double[][] PValue;
	private static String[][] tempValue;
	private static int index = 0;
	private String label;
	public static Stack<String> stack = new Stack<String>();

	public CPT(double[] vals, String label, ArrayList<Node> parents) {
		int len = vals.length;
		this.label = label;
		PValue = new double[len][1];
		tempValue = new String[len + 1][(int) (Math.log(len) / Math.log(2))];

		int i;
		for (i = 0; i < parents.size(); i++) {
			String parentLabel = parents.get(i).getLabel();
			tempValue[0][i] = parentLabel;
		}

		tempValue[0][i] = label;
		String shu[] = { "0", "1" };
		index = 1;
		permutation(shu, (int) (Math.log(len) / Math.log(2)), 0);
		this.setTValue(tempValue.clone());

		for (int j = 0; j < vals.length; j++) {
			PValue[j][0] = vals[j];
		}
	}

	public CPT(ArrayList<String> vars) {
		int len = vars.size();
		tempValue = new String[(int) (Math.pow(2, len) + 1)][len];

		for (int i = 0; i < vars.size(); i++) {
			tempValue[0][i] = vars.get(i);
		}
		String shu[] = { "0", "1" };
		index = 1;
		permutation(shu, len, 0);
		this.setTValue(tempValue.clone());

	}

	private static void permutation(String[] shu, int targ, int cur) {

		if (cur == targ) {
			for (int j = 0; j < stack.size(); j++) {
				tempValue[index][j] = stack.get(j);
			}
			index++;
			return;
		}

		for (int i = 0; i < shu.length; i++) {
			stack.add(shu[i]);
			permutation(shu, targ, cur + 1);
			stack.pop();

		}
	}

	public void printCPT() {
		System.out.println("CPT");
		for (int i = 0; i < TValue.length; i++) {
			int l = TValue[0].length;
			for (int j = 0; j < l; j++) {
				System.out.print(TValue[i][j] + " ");
			}
			if (i > 0)
				System.out.println(PValue[i - 1][0]);
			else
				System.out.println();
		}
	}

	public void setTValue(String[][] tValue) {
		TValue = tValue;
	}

	public void setPValue(double[][] pValue) {
		PValue = pValue;
	}

	public String[][] getTValue() {
		return TValue;
	}

	public double[][] getPValue() {
		return PValue;
	}

	public void changePValue(String evid, String evidValue) {
		
		if (evidValue .equals("T")) {
			evidValue = "1";
		} else {
			evidValue = "0";
		}
//		System.out.println(evidValue);
		int flag=0;
		for(int i=0;i<TValue[0].length;i++) {
			if(TValue[0][i].equals(evid)) {
				flag = i;
				break;
			}
		}
//		System.out.println(flag);
		for (int i = 1; i < TValue.length; i++) {
//			System.out.println(TValue[i][flag]);
			if(TValue[i][flag].equals(evidValue)) {
//				System.out.println("i is "+i);
//				System.out.println(PValue[i-1][0]);
				PValue[i-1][0]=0.0;
				
			}
		}

	}

	public void Normalise() {
//		System.out.println("Normalise");
		double sum=0;
		for(int i=0;i<PValue.length;i++) {
			sum = sum + PValue[i][0];
		}
		for(int i=0;i<PValue.length;i++) {
			PValue[i][0]=PValue[i][0]/sum;
		}
		
	}

}
