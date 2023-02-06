package agent;

import help.BayesianNetwork;
import help.Node;

public class P1 {
	BayesianNetwork bn = new BayesianNetwork();
	public P1(String network) {
		if(network.equals("CNX")) {
			//BayesianNetwork bn = new BayesianNetwork();
			// Add nodes in Bayesian Network
			Node attack = bn.addNode("attack");
			Node holiday = bn.addNode("holiday");
			Node planedMaintenance = bn.addNode("planedMaintenance");
			Node outOfDateMaintenance = bn.addNode("outOfDateMaintenance");
			Node vulnerability = bn.addNode("vulnerability");
			Node fireWallDeact = bn.addNode("fireWallDeact");
			Node unsafeBlocked = bn.addNode("unsafeBlocked");
			Node alert = bn.addNode("alert");
			Node correctLog = bn.addNode("correctLog");
			
			// Add edges in Bayesian Network
			bn.addEdge(planedMaintenance,fireWallDeact);
			bn.addEdge(outOfDateMaintenance,vulnerability);
			bn.addEdge(holiday,attack);
			bn.addEdge(fireWallDeact,attack);
			bn.addEdge(unsafeBlocked,attack);
			bn.addEdge(vulnerability,attack);
			bn.addEdge(attack,alert);
			bn.addEdge(attack,correctLog);
			
			// Add CPT values for each node
			holiday.addCPTvalues(0.125,0.875);
			unsafeBlocked.addCPTvalues(0.85,0.15);
			planedMaintenance.addCPTvalues(0.2,0.8);
			outOfDateMaintenance.addCPTvalues(0.05,0.95);
			fireWallDeact.addCPTvalues(0.03,0.97,0.01,0.99);
			vulnerability.addCPTvalues(0.05,0.95,0.01,0.99);
			attack.addCPTvalues(0.2,0.8,0.15,0.85,0.18,0.82,0.13,0.87,0.3,0.7,0.25,0.75,0.28,0.72,0.23,0.77,0.17,0.83,0.13,0.87,0.15,0.85,0.01,0.99,0.08,0.92,0.05,0.95,0.07,0.93,0.03,0.97);
			alert.addCPTvalues(0.95,0.05,0,1);
			correctLog.addCPTvalues(0.7,0.3,0,1);
			
//			holiday.getCPT().printCPT();
//			unsafeBlocked.getCPT().printCPT();
//			planedMaintenance.getCPT().printCPT();
//			outOfDateMaintenance.getCPT().printCPT();
//			fireWallDeact.getCPT().printCPT();
//			vulnerability.getCPT().printCPT();
//			attack.getCPT().printCPT();
//			alert.getCPT().printCPT();
//			correctLog.getCPT().printCPT();
		}
		else if (network.equals("BNA")) {
			// Add nodes in Bayesian Network
			Node a = bn.addNode("A");
			Node b = bn.addNode("B");
			Node c = bn.addNode("C");
			Node d = bn.addNode("D");
			// Add edges in Bayesian Network
			bn.addEdge(a, b);
			bn.addEdge(b, c);
			bn.addEdge(c, d);
			// Add CPT values for each node
			a.addCPTvalues(0.05,0.95);
			b.addCPTvalues(0.05,0.95,0.8,0.2);
			c.addCPTvalues(0.1,0.9,0.3,0.7);
			d.addCPTvalues(0.4,0.6,0.6,0.4);
		}
		else if (network.equals("BNB")) {
			// Add nodes in Bayesian Network
			Node J = bn.addNode("J");
			Node K = bn.addNode("K");
			Node L = bn.addNode("L");
			Node M = bn.addNode("M");
			Node N = bn.addNode("N");
			Node O = bn.addNode("O");
			// Add edges in Bayesian Network
			bn.addEdge(J, K);
			bn.addEdge(K, M);
			bn.addEdge(L, M);
			bn.addEdge(M, N);
			bn.addEdge(M, O);
			// Add CPT values for each node
			J.addCPTvalues(0.05,0.95);
			K.addCPTvalues(0.9,0.1,0.7,0.3);
			L.addCPTvalues(0.7,0.3);
			M.addCPTvalues(0.6,0.4,0.7,0.3,0.2,0.8,0.1,0.9);
			N.addCPTvalues(0.6,0.4,0.2,0.8);
			O.addCPTvalues(0.05,0.95,0.8,0.2);
			
		}
		else if (network.equals("BNC")) {
			Node P = bn.addNode("P");
			Node Q = bn.addNode("Q");
			Node R = bn.addNode("R");
			Node V = bn.addNode("V");
			Node S = bn.addNode("S");
			Node Z = bn.addNode("Z");
			Node U = bn.addNode("U");
			bn.addEdge(P, Q);
			bn.addEdge(Q, S);
			bn.addEdge(Q, V);
			bn.addEdge(R, S);
			bn.addEdge(R, V);
			bn.addEdge(V, Z);
			bn.addEdge(S, Z);
			bn.addEdge(S, U);
			P.addCPTvalues(0.05,0.95);
			R.addCPTvalues(0.7,0.3);
			Q.addCPTvalues(0.9,0.1,0.7,0.3);
			V.addCPTvalues(0.7,0.3,0.55,0.45,0.15,0.85,0.1,0.9);
			S.addCPTvalues(0.6,0.4,0.7,0.3,0.2,0.8,0.1,0.9);
			U.addCPTvalues(0.05,0.95,0.8,0.2);
			Z.addCPTvalues(0.65,0.35,0.7,0.3,0.4,0.6,0.2,0.8);
//			System.out.print(a.gettempValue());
//			System.out.print(b.gettempValue());
			
//			a.printCPT();
//			b.printCPT();
		}
		
	}
	
	public BayesianNetwork getNetwork() {
		return bn;
	}
}
