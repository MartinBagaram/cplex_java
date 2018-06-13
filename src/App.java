/**
 * 
 */

/**
 * @author Martins

 *
 */
import ilog.concert.*;
import ilog.cplex.*;
import java.util.*;
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		model();
	}
	
	public static void model() {
		try {
			IloCplex cplex = new IloCplex();
			
			// Decision variables
			IloNumVar x = cplex.numVar(0, Double.MAX_VALUE);
			IloNumVar y = cplex.numVar(0, Double.MAX_VALUE);
			
			// Expression
			IloLinearNumExpr objective = cplex.linearNumExpr();
			objective.addTerm(.12, x);
			objective.addTerm(.15, y);
			cplex.addMinimize(objective);
			
			// Constraints
			Map<String, IloRange> constraints = new HashMap<String, IloRange>();
			constraints.put("con1", cplex.addGe(cplex.sum(cplex.prod(60, x), cplex.prod(60, y)), 300));
			constraints.put("con2", cplex.addGe(cplex.sum(cplex.prod(12, x), cplex.prod(6, y)), 36));
			constraints.put("con3", cplex.addGe(cplex.sum(cplex.prod(10, x), cplex.prod(30, y)), 90));
			
			// Remove default results
			cplex.setParam(IloCplex.IntParam.SimDisplay, 0);
			
			// Solve
			if(cplex.solve()) {
				System.out.println();
				System.out.println("The objective value is " + cplex.getObjValue());
				System.out.println("The value of x is:  " + cplex.getValue(x));
				System.out.println("The value of y is:  " + cplex.getValue(y));
				System.out.println();
				System.out.println("The dual/slack variables are: ");
				for(String con:constraints.keySet()) {
					System.out.println("The dual of " + con +  " is : " + cplex.getDual(constraints.get(con)));
					System.out.println("The slack of " + con +  " is : " + cplex.getSlack(constraints.get(con)));
				}
				
			} else {
				System.out.println("The problem couldn't be solved ");
			}
			cplex.end();
		}
		catch (IloException exc) {
			exc.getStackTrace();
		}
		
		
	}
	

}
