import java.util.HashMap;

 public class HashedDirectedGraph {

     HashMap<String,DirectedNodeList> hdg;
     int numVertex;
     int numEdges;
     public HashedDirectedGraph(){

         numVertex = 0;
         numEdges=0;
         hdg = new HashMap<>();

     }

     public HashedDirectedGraph(int n){

         numVertex = n;
         numEdges=0;
         hdg = new HashMap<>(n);

     }

     public void addVertex(String k)
     {
         if (hdg.get(k)==null) {
             hdg.put(k, new DirectedNodeList());
         }
     }

     //assume vertices u and v exist
     public void addEdge(String u, String v){
         if (!hdg.get(u).getOutList().contains(v) && !hdg.get(v).getOutList().contains(v)){
         DirectedNodeList d1= hdg.get(u);
         DirectedNodeList d2 = hdg.get(v);

         d1.addToOutList(v);
         d2.addToInList(u);
         numEdges++;
     }
     }

     public int getNumVertex(){
         return numVertex;
     }
     public int getNumEdges(){
         return numEdges;
     }



 }
