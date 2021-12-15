import java.util.ArrayList;

public class DirectedNodeList
{
    ArrayList<String> inList;
    ArrayList<String> outList;
    
    
    public DirectedNodeList() {
        
        inList = new ArrayList<>();
        outList = new ArrayList<>();
       
        
    }
    
    public void addToInList(String p) {
        
        inList.add(p);
        
    }
    
    public void addToOutList(String p) {
        
        outList.add(p);
        
    }
    
    public DirectedNodeList(ArrayList<String> in, ArrayList<String> out) {
        inList = in;
        outList=out;
    }
    
    public int getInDegree(){
        return inList.size();
    }
    
    public int getOutDegree(){
        return outList.size();
    }
    
    public ArrayList<String> getInList(){
        return inList;
    }
    public ArrayList<String> getOutList(){
        return outList;
    }
    
    
    
}
