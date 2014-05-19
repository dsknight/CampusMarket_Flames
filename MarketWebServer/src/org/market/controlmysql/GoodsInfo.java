package org.market.controlmysql;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.market.mysql.ConnectMysql;
import org.market.search.Segmentation;
import org.market.types.GoodsType;


public class GoodsInfo {
	
	//以下常量用来知名返回商品列表的种类
	public static final int GOODS_LIST_ALL = 1;
	public static final int GOODS_LIST_DIGITAL = 2;
	public static final int GOODS_LIST_COSMETICS = 3;
	public static final int GOODS_LIST_BOOKS = 4;
	public static final int GOODS_LIST_CLOTHING = 5;
	public static final int GOODS_LIST_DAILYTOOLS = 6;
	public static final int GOODS_LIST_FOOD = 7;
	
	
	Connection conn = null;
	public GoodsInfo(){
		super();
	}
	
	public ArrayList<GoodsType> allGoods(){
		int flag = 0;
		ArrayList<GoodsType> list = new ArrayList<GoodsType>();
		String sql = "SELECT * FROM tb_goods WHERE Gproperty=0";
		conn = ConnectMysql.connect();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				flag = 1;
				int i = 1;
				GoodsType temp = new GoodsType();
				temp.setGNO(rs.getInt(i++));
				temp.setName(rs.getString(i++));
				temp.setLexemeName(rs.getString(i++));
				temp.setOwner(rs.getString(i++));
				temp.setPrice(rs.getString(i++));
				temp.setImage(rs.getString(i++));
				temp.setMainClass(rs.getInt(i++));
				temp.setSubClass(rs.getInt(i++));
				temp.setIntroduction(rs.getString(i++));
				temp.setDate(rs.getString(i++));
				list.add(temp);
			}
			conn.setAutoCommit(true);
			stmt.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("GoodsInfo.AllGoods roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();	
		}
		if(flag == 1)
			return list;
		else
			return null;
	}
	
	public boolean addGoods(GoodsType goods){
		boolean flag  = false;
		String sql = "INSERT INTO " 
					+"tb_goods(GName,GLexeme,GOwnerName,GPrice,GImage,GMainClass,GSubClass,GIntroduction,GDate,GLabel,Gproperty) " 
					+"VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		conn = ConnectMysql.connect();
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, goods.getName());
			pstmt.setString(2, goods.getLexemeName());
			pstmt.setString(3, goods.getOwner());
			pstmt.setString(4, goods.getPrice());
			pstmt.setString(5, goods.getImage());
			pstmt.setInt(6, goods.getMainClass());
			pstmt.setInt(7, goods.getSubClass());
			pstmt.setString(8, goods.getIntroduction());
			pstmt.setString(9, goods.getDate());
			pstmt.setInt(10, goods.getLabel());
			pstmt.setInt(11, goods.getProperty());
			if(pstmt.executeUpdate() > 0){
				flag = true;
			}
			conn.setAutoCommit(true);
			pstmt.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("GoodsInfo.addGoods roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();	
		}
		return flag;
	}
	
	public boolean deleteGoods(int gno){
		boolean flag  = false;
		String sql = "DELETE FROM tb_goods WHERE GNO = ?";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, gno);
			if(stmt.executeUpdate()> 0){
				flag = true;
			}
			conn.setAutoCommit(true);
			stmt.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("GoodsInfo.deleteGoods roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<GoodsType> certainGoods(int mainClass,int subClass){
		if(mainClass == 1)
			return allGoods();//返回所有物品
		int flag = 0;
		ArrayList<GoodsType> goodsList = new ArrayList<GoodsType>();
		String sql = "SELECT * FROM tb_goods WHERE GMainClass = ? AND GSubClass = ?";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mainClass);
			stmt.setInt(2, subClass);
			rs = stmt.executeQuery();
			while(rs.next()){
				flag = 1;
				int i = 1;
				GoodsType temp = new GoodsType();
				temp.setGNO(rs.getInt(i++));
				temp.setName(rs.getString(i++));
				temp.setLexemeName(rs.getString(i++));
				temp.setOwner(rs.getString(i++));
				temp.setPrice(rs.getString(i++));
				temp.setImage(rs.getString(i++));
				temp.setMainClass(rs.getInt(i++));
				temp.setSubClass(rs.getInt(i++));
				temp.setIntroduction(rs.getString(i++));
				temp.setDate(rs.getString(i++));
				goodsList.add(temp);
			}
			conn.setAutoCommit(true);
			stmt.close();
			rs.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("GoodsInfo.certainGoods roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		if(flag == 1)
			return goodsList;
		else
			return null;
	}
	
	public ArrayList<GoodsType> certainGoods(String Owner){
		int flag = 0;
		ArrayList<GoodsType> goodsList = new ArrayList<GoodsType>();
		String sql = "SELECT * FROM tb_goods WHERE GOwnerName=? AND Gproperty=0;";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, Owner);
			rs = stmt.executeQuery();
			while(rs.next()){
				flag = 1;
				int i = 1;
				GoodsType temp = new GoodsType();
				temp.setGNO(rs.getInt(i++));
				temp.setName(rs.getString(i++));
				temp.setLexemeName(rs.getString(i++));
				temp.setOwner(rs.getString(i++));
				temp.setPrice(rs.getString(i++));
				temp.setImage(rs.getString(i++));
				temp.setMainClass(rs.getInt(i++));
				temp.setSubClass(rs.getInt(i++));
				temp.setIntroduction(rs.getString(i++));
				temp.setDate(rs.getString(i++));
				goodsList.add(temp);
			}
			conn.setAutoCommit(true);
			stmt.close();
			rs.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("GoodsInfo.certainGoods roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		if(flag == 1)
			return goodsList;
		else
			return null;
	}
	
	public String needGoods(String clientName){
		//System.out.println("*******" + cno + "***********");
		String needs = "";
		String sql = "SELECT GName FROM tb_goods WHERE GOwnerName = ? AND Gproperty=1";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientName);
			rs = stmt.executeQuery();
			if(rs.next() == true){
				needs = rs.getString(1);
			}else{
				needs = null;
			}
			conn.setAutoCommit(true);
			stmt.close();
			rs.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("GoodsInfo.needGoods roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return needs;
	}
	
	public boolean updateGoods(GoodsType goods){
		boolean flag = false;
		String sql = "UPDATE tb_goods SET GName=?,GLexeme=?,GOwenerName=?,GPrice=?,GImage=?,GMainClass=?," +
					 "GSubClass=?,GIntroduction=?,GDate=?,GLabel=?,Gproperty=? WHERE GNO=?;";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, goods.getName());
			stmt.setString(2, goods.getLexemeName());
			stmt.setString(3, goods.getDate());
			stmt.setString(4, goods.getPrice()+"");
			stmt.setString(5, goods.getImage());
			stmt.setString(6, goods.getMainClass()+"");
			stmt.setString(7, goods.getSubClass()+"");
			stmt.setString(8, goods.getDate());
			stmt.setString(9, goods.getLabel()+"");
			stmt.setString(10, goods.getProperty()+"");
			stmt.setString(11, goods.getGNO()+"");
			if(stmt.executeUpdate() > 0)
				flag = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean updateNeeds(GoodsType need){
		boolean flag = false;
		String sql = "UPDATE tb_goods SET GName=?,GLexeme=?,GDate=? WHERE GOwnerName=? AND Gproperty=1;";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, need.getName());
			stmt.setString(2, need.getLexemeName());
			stmt.setString(3, need.getDate());
			stmt.setString(4, need.getOwner());
			if(stmt.executeUpdate() > 0)
				flag = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
	}
	
	public GoodsType findGoods(int GNO){
		GoodsType temp = new GoodsType();
		int flag = 0;
		String sql = "SELECT * FROM tb_goods WHERE GNO = ?";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, GNO);
			rs = stmt.executeQuery();
			while(rs.next()){
				flag = 1;
				int i = 1;
				temp.setGNO(rs.getInt(i++));
				temp.setName(rs.getString(i++));
				temp.setLexemeName(rs.getString(i++));
				temp.setOwner(rs.getString(i++));
				temp.setPrice(rs.getString(i++));
				temp.setImage(rs.getString(i++));
				temp.setMainClass(rs.getInt(i++));
				temp.setSubClass(rs.getInt(i++));
				temp.setIntroduction(rs.getString(i++));
				temp.setDate(rs.getString(i++));
			}
			conn.setAutoCommit(true);
			stmt.close();
			rs.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		if(flag == 1)
			return temp;
		else
			return null;
	}
	
	public String searchGoods(String key) throws IOException{
		if(key == null)
			return "*";
		
		//首先对关键字分词
		String modifiedKey = Segmentation.IKAnalyze(key);
		
		//查询
		String result = "";
		int flag = 0;
		int numOfResult = 0;
		String sql = "SELECT GNO,GName FROM tb_goods WHERE MATCH(GLexeme) AGAINST(? WITH QUERY EXPANSION) AND Gproperty=0;";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, modifiedKey);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				flag = 1;
				numOfResult++;
				result += "*" + rs.getInt(1) + " " + rs.getString(2);
			}
			conn.setAutoCommit(true);
			stmt.close();
			rs.close();
			conn.close();
		}catch(SQLException e){
			try {
				conn.rollback();
				System.out.println("roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		if(flag == 1)
			return result;
		else
			return "*";
	}
}
