import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class solver {

    private static ArrayList<ArrayList<Integer>> Clauses= new ArrayList<ArrayList<Integer>>();
    private static int  NumClauses;
    private static int NumVariables;

     public static void main(String[] args)throws Exception{
         

            var in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = in.readLine();
                if (line == null) break;
                    if (line.charAt(0) =='c'){
                        continue;
                    }
                    if( line.contains("p cnf")){

                        String clauses_vars = line.replace("p cnf","");
                        clauses_vars = clauses_vars.trim();
                        String clauses = clauses_vars.split(" ")[0];
                        String vars = clauses_vars.split(" ")[1];
                        
                        NumClauses = Integer.parseInt(clauses);
                        NumVariables =Integer.parseInt(vars);

                        break;
                    }


                    
                
            }

            while (true) {

                String line = in.readLine();

                if (line == null) break;
                
                    String[] vars_in_clause = line.split(" ");
                    ArrayList<Integer> clause = new ArrayList<Integer>();

                    for(String s: vars_in_clause){
                        if( Integer.parseInt(s)==0){
                            break;
                        }
                        clause.add( Integer.parseInt(s));
                    }
                    Clauses.add(clause);


                    
                
            }

            
            // System.out.println("Clauses:::::::::::::::");

            
            // System.out.println(Clauses);


            Clauses = EliminateTautologies(Clauses);
            Clauses = RemoveAllDoubleLiterals(Clauses);
            // System.out.println("New Clauses:::::::::::::::");
            // Clauses = PropogateUnit(Clauses,1);
            // Clauses = PropogateUnit(Clauses,2);
            // Clauses = PropogateUnit(Clauses,3);
            // Clauses = PropogateUnit(Clauses,4);
            
            //  OutputClauses(Clauses);
            // Integer unit = FindSingleLiteral(Clauses);
            // if(unit==0) unit = Clauses.get(0).get(0);

            // System.out.println("Unit " + unit);
            // Clauses = PropogateUnit(Clauses, unit);
            // OutputClauses(Clauses);

            // unit = FindSingleLiteral(Clauses);
            // if(unit==0) unit = Clauses.get(0).get(0);
            
            // System.out.println("Unit " + unit);
            // Clauses = PropogateUnit(Clauses, unit);
            // OutputClauses(Clauses);
            String tree ="B";
            System.out.println(Solve(Clauses,tree));
            
            
    }


    public static String Solve(ArrayList<ArrayList<Integer>> clauses, String tree){
        clauses= new ArrayList<ArrayList<Integer>>(clauses);

        //System.out.println("TREE STATE "+ tree);
        //OutputClauses(clauses);

        if ( clauses.size()==0){
            return "SATISFIABLE";
        }

        for(ArrayList<Integer> clause : clauses){
            if (IsEmptyClause(clause) == true){
                return "UNSATISFIABLE";
            }
        }

        Integer unit = FindSingleLiteral(clauses);

        if (unit ==0 ){
            unit = clauses.get(0).get(0);
        }
        //System.out.println("UNIT:::  "  + unit);

        
        if ( Solve(PropogateUnit(clauses, unit), tree+"F")=="SATISFIABLE"){
            return "SATISFIABLE";
        }
        else{
            return Solve(PropogateUnit(clauses, -unit),tree+"S");
        }


        

    }



    private static ArrayList<ArrayList<Integer>> PropogateUnit(ArrayList<ArrayList<Integer>> clauses, Integer literal){
        ArrayList<ArrayList<Integer>> newclauses = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> clause : clauses){

            if (!clause.contains(literal)){
                    
                newclauses.add( RemoveLiteral(clause, -literal));
            }
            // if (clause.contains(-literal)){
            // ArrayList<Integer> newclause = clause;
            // newclause.removeAll(Collections.singleton(-literal));
            // //}

            // newclauses.add(newclause);

        }
  

        return newclauses;
    }

    private static ArrayList<Integer> RemoveLiteral(ArrayList<Integer> clause, Integer literal){
        clause = new ArrayList<Integer>(clause);
        clause.removeAll(Collections.singleton(literal));
        return clause;
    }

    // returns a literal that occurs by itself in a clause, 0 if no such literal exists
    private static Integer FindSingleLiteral(ArrayList<ArrayList<Integer>> clauses){
        for(ArrayList<Integer> clause : clauses){
            if ( clause.size()==1){
                System.out.println("Single literal found : " + clause.get(0) );
                return clause.get(0);
            }
        }
        return 0;
    }

    public static boolean IsEmptyClause(ArrayList<Integer> clause){
        if( clause.size() ==0 ){
            return true;
        }
        
        return false;
    }

    public static ArrayList<ArrayList<Integer>> EliminateTautologies(ArrayList<ArrayList<Integer>> clauses){
        ArrayList<ArrayList<Integer>> newclauses = new ArrayList<ArrayList<Integer>>();

        for( ArrayList<Integer> clause: clauses){
            
            if ( !IsTautology(clause) ){
                newclauses.add(clause);
            }
        }

        return newclauses;

    }

    public static boolean IsTautology(ArrayList<Integer> clause){
        for (int var =1; var<=NumVariables;var++){

            if (clause.contains(var) && clause.contains(-var)){
                return true;
            }
        }
        return false;

    }

    // removes all double, triple ... occurences of a literal in a clause from all clauses
    public static ArrayList<ArrayList<Integer>> RemoveAllDoubleLiterals( ArrayList<ArrayList<Integer>> clauses ){

        ArrayList<ArrayList<Integer>> newclauses= new ArrayList<ArrayList<Integer>>();

        for( ArrayList<Integer> clause : clauses){
            newclauses.add( RemoveDoubleLiterals(clause));
        }
        return newclauses;
    }

    
    // removes all double, triple ... occurences of a literal in a clause
    public static ArrayList<Integer> RemoveDoubleLiterals( ArrayList<Integer> clause ){

        ArrayList<Integer> newclause= new ArrayList<Integer>();

        for( Integer literal : clause){
            if (!newclause.contains(literal)){
                newclause.add(literal);
            }
        }
        return newclause;
    }

    public static void OutputClauses(ArrayList<ArrayList<Integer>> clauses){
        System.out.println("CLAUSES");
        for( ArrayList<Integer> clause : clauses){
            System.out.println(clause);
            
        }
        System.out.println("=============================================================================");
    }


}
