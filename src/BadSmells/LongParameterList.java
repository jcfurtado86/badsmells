package BadSmells;

public class LongParameterList {
    
    public boolean muitosParametros(String p){
        if(!p.contains(",")) return false;
        
        else{
            char[] c = p.toCharArray();
            int aux = 0;
            
            for (int i = 0; i < c.length; i++) {
                
                if(c[i] == ',') aux++;
            }
            
            return aux >= 3;
        }
    }
}
