import java.util.*;
//Used in comparing map entries
class MapCompare implements Comparator {
	public MapCompare(){
		
	}
	
	public int compare(Object o1, Object o2){
	   Map.Entry entry1 = (Map.Entry)o1;
	   Map.Entry entry2 = (Map.Entry)o2;
	   
	   if(((Integer)entry1.getValue()).intValue() < ((Integer)entry2.getValue()).intValue()){
	   	return 1;
	   }
	   if(((Integer)entry1.getValue()).intValue() > ((Integer)entry2.getValue()).intValue()){
	   	return -1;
	   }
	   return 0;
	}
}
