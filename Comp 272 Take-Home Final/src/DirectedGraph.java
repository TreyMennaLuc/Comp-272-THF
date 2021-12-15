import java.util.ArrayList;
import java.util.HashMap;

public class DirectedGraph {
    HashMap<String,DirectedNodeList> dGraph;
    int numVertex;
   HashMap<String, Boolean> marked;
   String[] finishingNumber;
   int finCount;
   int maxCC;
   int numSCC;
   HashMap<String, ArrayList<String>> stronglyCCs;
   ArrayList<String> scc;
   
    HashMap<String, String> components;
    
    
    public DirectedGraph() {
        dGraph = new HashMap<>();
        marked= new HashMap<>();
        components= new HashMap<>();
        finishingNumber= new String[0];
        numVertex=0;
        finCount=0;
        maxCC=0;
        numSCC=0;
        stronglyCCs = new HashMap<>();
        scc = new ArrayList<>();
        
    }
    
    public int getNumVertex(){
        return numVertex;
    }
    
    public DirectedGraph(int n) {
      numVertex =n;
      dGraph = new HashMap<>();
      marked= new HashMap<>();
      finishingNumber= new String[n];
      finCount=0;
      scc = new ArrayList<>();
      stronglyCCs=new HashMap<>();
     // maxCC=0;
      //numSCC=0;
      components= new HashMap<>();
      ///addPorts
       
    }
    public void addPorts(ArrayList<String> airports)
    {
        if(finishingNumber.length ==0)
        {
            finishingNumber= new String[numVertex];
        }
        for(String portChar : airports)
        {
            dGraph.put(portChar, new DirectedNodeList());
            marked.put(portChar,false);
            components.put(portChar,portChar);
        }
    }
    public void resetMarked()
    {
        for(String unCheck : marked.keySet())
        {
            marked.put(unCheck,false);
        }
    }
    
    public boolean isEdge (String u, String v){
        if (this.dGraph.get(u) == null) {
            {
                dGraph.put(u, new DirectedNodeList());
            }
        }
        if (this.dGraph.get(v) == null) {
            {
                dGraph.put(v, new DirectedNodeList());
            }
        }
            if (!u.equals(v))
           return getNeighborList(u).getOutList().contains(v);
            else return false;
}
    
    public void addEdge(String u, String v) {
       // assume all vertices are created
       // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.
       //System.out.println(numVertex);
       //System.out.println("u ="+u+"v = "+v);
           if (!u.equals(v) && !isEdge(u,v)) {
           getNeighborList(u).addToOutList(v);
           getNeighborList(v).addToInList(u);
        }
    }
    
    public DirectedNodeList getNeighborList(String u) {
        return dGraph.get(u);
    }
    
    public void printAdjacency(String u) {
       DirectedNodeList dnl = getNeighborList(u);
       System.out.println ("vertices going into "+u+"  "+dnl.getInList());
       System.out.println ("vertices going out of "+u+"  "+dnl.getOutList());
       System.out.println();
    }
    
    public void postOrderDepthFirstTraversal(boolean reverse) {
        for (String check : components.keySet()) {
            if (!marked.get(check))
                postOrderDFT(check,reverse);
        }
       
    }
    public void postOrderDFT(String v,boolean reverse){
        marked.put(v,true);
        if (reverse) {
        for (String u:dGraph.get(v).getInList()) {
            if (!marked.get(u))
            {
                postOrderDFT(u,reverse);
            }
        }
    }
        else {
             for (String u:dGraph.get(v).getOutList()) {
                 if (!marked.get(u)){
                     postOrderDFT(u, reverse);
                 }
             }
            }
        finishingNumber[finCount++]=v;
    }
    
    public void depthFirstTraversal() {
       for (int j=numVertex-1;j>=0;j--) {
           String u=finishingNumber[j];
       if (!marked.get(u)) {
           numSCC++;
           stronglyCCs.put(u, new ArrayList<>());
           scc=new ArrayList<>();
           dFT(u);
           for (String k: scc) {
               components.put(k, u);  //Sets what SCC the current port is in
               stronglyCCs.get(u).add(k);
           }
       }
    }
}
    public void dFT(String v){
       // System.out.println(v);
        scc.add(v);
        marked.put(v,true);
            for (String u : dGraph.get(v).getOutList()) {
                if (!marked.get(u)) {
                    dFT(u);
                }
            }

    }
    public void regularDepthFirstTraversal() {
        int weakCC = 0;
        for (String port : components.keySet())
            if (!marked.get(port))
            { rDFT (port);}
        weakCC++;
            System.out.println("Num of weakCC: " + weakCC);

    }
    public void rDFT(String v){

        marked.put(v,true);
        for (String u:dGraph.get(v).getOutList())
            if (!marked.get(u)) dFT(u);

    }


}
