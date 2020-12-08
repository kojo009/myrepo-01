package 数据库百万级插入;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class sqlmillion {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	  private String user = "root";
	  private String password = "123456";	  
	  public void Test(){
	    Connection conn = null;
	    PreparedStatement pstm =null;
	    ResultSet rt = null;
	    try {
	      Class.forName(JDBC_DRIVER);
	      conn = DriverManager.getConnection(DB_URL, user, password); 
	      if(conn!=null)
	      {
	    	  System.out.println("连接成功!");
	      }
	      else
	      {
	    	  System.out.println("连接失败!");
	      }
	      String sql = "INSERT INTO t(uid,pid,datetime) VALUES(?,?,?)";
	      pstm = conn.prepareStatement(sql);
	      conn.setAutoCommit(false);
	      Long startTime = System.currentTimeMillis();
	      Random rand = new Random();
	      int a,b;
	      long c;	      
	      for (int i = 1; i <= 1000000; i++) {	          
	          a = rand.nextInt(10);
	          b = rand.nextInt(10);
	          pstm.setInt(1, a);
	          pstm.setInt(2, b);	          
	          c = System.currentTimeMillis();
	          SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
	          Date date = new Date(c);
	          System.out.println(formatter.format(date));
	          pstm.setString(3, formatter.format(date));
	          pstm.addBatch();
	      }
	      pstm.executeBatch();
	      conn.commit();
	      Long endTime = System.currentTimeMillis();
	      System.out.println("OK,用时：" + (endTime - startTime)); 
	    } catch (Exception e) {
	      e.printStackTrace();
	      throw new RuntimeException(e);
	    }finally{
	      if(pstm!=null){
	        try {
	          pstm.close();
	        } catch (SQLException e) {
	          e.printStackTrace();
	          throw new RuntimeException(e);
	        }
	      }
	      if(conn!=null){
	        try {
	          conn.close();
	        } catch (SQLException e) {
	          e.printStackTrace();
	          throw new RuntimeException(e);
	        }
	      }
	    }
	  }	
	  public static void main(String[] args) {
		  sqlmillion sl=new sqlmillion();
		  sl.Test();
	}
}
