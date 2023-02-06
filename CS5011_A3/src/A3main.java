import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import agent.P1;
import agent.P2;
import agent.P3;
import agent.P4;
import help.BayesianNetwork;

/********************Starter Code
 * 
 * This class contains some examples on how to handle the required inputs and outputs 
 * 
 * @author at258
 * 
 * run with 
 * java A3main <Pn> <NID>
 * 
 * Feel free to change and delete parts of the code as you prefer
 * 
 */


public class A3main {

	public static void main(String[] args) {

		Scanner sc=new Scanner(System.in);

		switch(args[0]) {
			case "P1": {
				//construct the network in args[1]
				System.out.println("Network "+args[1]);
				P1 p1 = new P1(args[1]);
				BayesianNetwork bn=p1.getNetwork();
				//print the network 
			}break;
	
			case "P2":  {
				//construct the network in args[1]
				String[] query=getQueriedNode(sc);
				String variable=query[0];
				String value=query[1];
				String[] order=getOrder(sc);
				P1 p1 = new P1(args[1]);
				BayesianNetwork bn = p1.getNetwork();
				P2 p2 = new P2(bn,variable,value);
				p2.varElimination(order);
				double result = p2.getResult();
				// execute query of p(variable=value) with given order of elimination

				printResult(result);
//				System.out.print("num "+ p2.getNum());
			}break;
	
			case "P3":{
				//construct the network in args[1]
				String[] query=getQueriedNode(sc);
				String variable=query[0];
				String value=query[1];
				String[] order=getOrder(sc);
				ArrayList<String[]> evidence=getEvidence(sc);
				P1 p1 = new P1(args[1]);
				BayesianNetwork bn = p1.getNetwork();

				P3 p3 = new P3(bn,variable,value,evidence);
				p3.varEliminationP3(order);
				// execute query of p(variable=value|evidence) with given order of elimination
				double result=p3.getResult();
				printResult(result);
//				System.out.print("num "+ p3.getNum());
			}break;
	
			case "P4":{
				//construct the network in args[1]
				String[] query=getQueriedNode(sc);
				String variable=query[0];
				String value=query[1];
//				String order=  "A,B";
				ArrayList<String[]> evidence=getEvidence(sc);
				P1 p1 = new P1(args[1]);
				BayesianNetwork bn = p1.getNetwork();
				P4 p4 = new P4(bn,variable,value,evidence);
				String[] order=p4.maxCardinality();
				p4.varEliminationP3(order);
				// execute query of p(variable=value|evidence) with given order of elimination
	
				double result=p4.getResult();
				printResult(result);
//				System.out.print("num "+ p4.getNum());
			}break;
		}
		
		sc.close();
	}

	//method to obtain the evidence from the user
	private static ArrayList<String[]> getEvidence(Scanner sc) {

		System.out.println("Evidence:");
		ArrayList<String[]> evidence=new ArrayList<String[]>();
		String[] line=sc.nextLine().split(" ");

		for(String st:line) {
			String[] ev=st.split(":");
			
			evidence.add(ev);
			
		}
//		System.out.println(evidence.size());
		return evidence;
	}

	//method to obtain the order from the user
	private static String[] getOrder(Scanner sc) {

		System.out.println("Order:");
		String[] val=sc.nextLine().split(",");
		return val;
	}


	//method to obtain the queried node from the user
	private static String[] getQueriedNode(Scanner sc) {

		System.out.println("Query:");
		String[] val=sc.nextLine().split(":");

		return val;

	}

	//method to format and print the result 
	private static void printResult(double result) {

		DecimalFormat dd = new DecimalFormat("#0.00000");
		System.out.println(dd.format(result));
	}

}
