package com.duanjiao.dpsTest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static com.duanjiao.dpsTest.memory.MemorySizeHepler.*;

public class FetchSize {
	
	private static BlockingQueue<ResultSet> fetchDataQueue = new ArrayBlockingQueue<>(100);
	
    static final String driver_class  = "oracle.jdbc.driver.OracleDriver";
    static final String connectionURL = "jdbc:oracle:thin:@192.168.8.22:1521:orcl";
    static final String userID        = "TNMSDB";
    static final String userPassword  = "tnmsdb";
    public void runTest(int fetchSize) {
        Connection  con = null;
        Statement   stmt = null;
        ResultSet   rset = null;
        long startTime =System.currentTimeMillis();
        String  query_string = "SELECT * FROM Site";//test有5万条记录
        try {
            Class.forName (driver_class).newInstance();
            con = DriverManager.getConnection(connectionURL, userID, userPassword);
            
            con.setReadOnly(true);
            
            stmt = con.prepareStatement(query_string,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            stmt.setQueryTimeout(1);
            
            stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
            
            stmt.setFetchSize(fetchSize);
            
            rset = stmt.executeQuery (query_string);
            
            int num =1;
            while (rset.next ()) {
            	
            	System.out.println("ResultSet data size:"+num);
            	
                String firstCell = rset.getString(1);
                String secondCell = rset.getString(2);
                String thirdCell = rset.getString(3);
                
                num ++;
               
               fetchDataQueue.put(rset);
               
               System.out.println("fetchDataQueue data size:"+sizeOf(fetchDataQueue));
            }
            
            rset.close();
            stmt.close();
            long endTime =System.currentTimeMillis();
            System.out.println("fetchsize为"+fetchSize+"---消耗的时间:"+(endTime-startTime));
        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	
    	new ThreadQueue().start();
    	
    	FetchSize fetchSize = new FetchSize();
    	
    	long startTime = System.currentTimeMillis();
        fetchSize.runTest(1000);
        
        System.out.println(fetchDataQueue.size());
        System.out.println("耗时:"+(System.currentTimeMillis()-startTime));
    }
    
    private static class ThreadQueue extends Thread{
    	
    	private static int SECONDS =6000;
    	@Override
    	public void run() {
    		
    		while(true) {
    			try {
        			System.out.println("this thread sleep :"+SECONDS);
    				Thread.sleep(SECONDS);
    				SECONDS += 1000;
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		
        		try {
        			ResultSet data =fetchDataQueue.take();
    				
    				System.out.println("Queue take data :"+data);
    				
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}
    }
}
