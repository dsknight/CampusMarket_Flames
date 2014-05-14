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
	
	Connection conn = null;
	public GoodsInfo(){
		super();
	}
	
	public ArrayList<GoodsType> allGoods(){
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
				int i = 1;
				GoodsType temp = new GoodsType();
				temp.setGNO(rs.getInt(i++));
				temp.setName(rs.getString(i++));
				temp.setLexemeName(rs.getString(i++));
				temp.setOwner(rs.getInt(i++));
				temp.setPrice(rs.getDouble(i++));
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
		return list;
	}
	
	public boolean addGoods(GoodsType goods){
		boolean flag  = false;
		String sql = "INSERT INTO tb_goods(GName,GOwnerNO,GPrice,GImage,GClassID,GSubClassID,GIntroduction,GDate) " +
				"VALUES (?,?,?,?,?,?,?,?)";
		conn = ConnectMysql.connect();
		PreparedStatement pstmt = null;
		try{
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, goods.getName());
			pstmt.setInt(2, goods.getOwner());
			pstmt.setDouble(3, goods.getPrice());
			pstmt.setString(4, goods.getImage());
			pstmt.setInt(5, goods.getMainClass());
			pstmt.setInt(6, goods.getSubClass());
			pstmt.setString(7, goods.getIntroduction());
			pstmt.setString(8, goods.getDate());
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
		ArrayList<GoodsType> goodsList = new ArrayList<GoodsType>();
		String sql = "SELECT * FROM tb_goods WHERE GClassID = ? AND GSubClassID = ?";
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
				int i = 1;
				GoodsType temp = new GoodsType();
				temp.setGNO(rs.getInt(i++));
				temp.setName(rs.getString(i++));
				temp.setOwner(rs.getInt(i++));
				temp.setPrice(rs.getDouble(i++));
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
		return goodsList;
	}
	
	public String needGoods(int cno){
		//System.out.println("*******" + cno + "***********");
		String needs = "";
		String sql = "SELECT GName FROM tb_goods WHERE GOwnerNO = ? AND Gproperty=1";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cno);
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
	
	public String searchGoods(String key) throws IOException{
		if(key == null)
			return "*no1*no2*no3";
		
		//���ȶԹؼ��ִַ�
		String modifiedKey = Segmentation.IKAnalyze(key);
		
		//��ѯ
		String result = "";
		int numOfResult = 0;
		String sql = "SELECT GNO,GName FROM tb_goods WHERE MATCH(GLexeme) AGAINST(? WITH QUERY EXPANSION);";
		conn = ConnectMysql.connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, modifiedKey);
			rs = stmt.executeQuery();
			
			while(rs.next()){
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
				System.out.println("ClientInfo.login roll back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 
		return result;
	}
}
