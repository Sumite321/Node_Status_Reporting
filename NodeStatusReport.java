/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.*;
/**
 *
 * @author admin
 */
public class NodeStatusReport {

    /**
     * @param args the command line arguments
     */
    static LinkedHashMap<String,String> report = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException{
      
       readNodeData(args[0]);
       displayReport();
}
        public static void readNodeData(String filename) throws IOException{
        
        BufferedReader br = new BufferedReader(new FileReader(filename)); 
 
        String line = br.readLine();
        
        while (line != null)  
        {   
            String []splitData =line.split(" ");
            //Node status 1 = outputs "Hello" means is Alive
            if (getNodeStatus(splitData) == 1){
                // add node to HashMap and respective timestamp + notification
                report.put(getNodeName(splitData),"ALIVE " + " " +getTimeStamp(splitData) + " " +getNotification(splitData,1));
                        }
            // Node status 2 = Node has found a new Node which is alive
            if (getNodeStatus(splitData) == 2){  
                
                //condition: Dead Node cannot report
                if(report.get(getNodeName(splitData)).contains("ALIVE")){
                // add node to HashMap and respective timestamp + notification
                report.put(getFoundNode(splitData),"ALIVE " + " " +getTimeStamp(splitData) + " " +getNotification(splitData,2));
                }
                // update timestamp and event of Node reporting
                report.put(getScannerNode(splitData),"ALIVE "+ "" +getTimeStamp(splitData) + " " +getNotification(splitData,2));
                        }   
            // Node status 3 = Node has found a dead node
            if (getNodeStatus(splitData) == 0){  
                // condition: Dead Node cannot report
                if(report.get(getNodeName(splitData)).contains("ALIVE")){
               // add node to HashMap and respective timestamp + notification
                report.put(getFoundNode(splitData),"DEAD " + " " + getTimeStamp(splitData) + " " +getNotification(splitData,0));
                }
                // update timestamp and event of Node reporting
                report.put(getScannerNode(splitData),"ALIVE "+ "" +getTimeStamp(splitData) + " " +getNotification(splitData,0));
            }
            line = br.readLine();  
        } 
}
    
        public static void displayReport(){
            for (Map.Entry<String,String> entry : report.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println(key + " "+value);
            }
        }
    
    
        public static String getTimeStamp(String[] line){
           return line[0];
        }
        
        public static String getNodeName(String[] line){
           return line[2];
        }
        
        public static int getNodeStatus(String[] line){

            int STATUS = -1;
       
            if(line[3].equals("HELLO")){STATUS=1;}
            if(line[3].equals("LOST")){STATUS=0;}
            if(line[3].equals("FOUND")){STATUS=2;}
            
        return STATUS;
        }
        
        
        public static String getNotification(String[] line, int stats){
            String notification = " HELLO";
            
            if (stats == 2){notification = line[2] + " FOUND " + line[4];} // Node A has found Node B, Node B is Alive
            if (stats == 0){notification = line[2] + " LOST " + line[4];} // Node A has found that Node B is DEAD
            
            return notification; // return HELLO as default   
        } 
        
        public static String getFoundNode(String[] line){
        return line[4];
        }
        
        public static String getScannerNode(String[] line){
        return line[2];
        }
        
        
}
 
        
        
        
        
        
   
   
   
   
   
   
   
   
   


