package org.market.serviceservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.market.controlmysql.GoodsInfo;
import org.market.types.GoodsType;

public class GoodsListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GoodsListServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("*** GoodsListServlet ***");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		int type = Integer.parseInt(request.getParameter("type"));
		String startID_Owner = request.getParameter("startID_Owner");//��Ϊ����ʾ�����һ��ID�����η���֮���ID
																	 //�����������û����������û���������Ʒ
		System.out.println("type = " + type + " startID_Owner = " + startID_Owner);
		if(type < 1 || type > 9)
			System.out.println("Wrong type of list, type = " + type);
		else if(type == 8){//��ѯĳ���û�����Ʒ
			GoodsInfo goodsInfo = new GoodsInfo();
			ArrayList<GoodsType> goodsList = goodsInfo.certainGoods(startID_Owner);
			if(goodsList != null){
				for(GoodsType tmpGoods : goodsList)
						out.print(tmpGoods);
			}
			else
				out.print("#");
		}
		else{//��ѯĳ���������Ʒ
			int startID = Integer.parseInt(startID_Owner);
			GoodsInfo goodsInfo = new GoodsInfo();
			ArrayList<GoodsType> goodsList = goodsInfo.certainGoods(type, 0);
			if(goodsList != null){
				int count = 0;
				for(GoodsType tmpGoods : goodsList){
					if((startID != 0 && tmpGoods.getGNO() != startID && count == 0))
						continue;
					else if(tmpGoods.getGNO() == startID)
						count ++;
					else{
						tmpGoods.setImage("");//��ʱ�Ȳ���ͼƬ��Ϊ�˸��û����ٲ���Ҫ������
						out.print(tmpGoods);
						count ++;
					}
					if(count >= 6)
						break;
				}
			}
			else
				out.print("#");
		}
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
