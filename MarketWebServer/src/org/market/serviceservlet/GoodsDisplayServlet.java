package org.market.serviceservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.market.controlmysql.ClientInfo;
import org.market.controlmysql.GoodsInfo;
import org.market.types.ClientType;
import org.market.types.GoodsType;

public class GoodsDisplayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GoodsDisplayServlet() {
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

		doPost(request,response);
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
		System.out.println("*** GoodsDisplayServlet ***");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		int goodsID = Integer.parseInt(request.getParameter("goodsID"));
		GoodsInfo goodsInfo = new GoodsInfo();
		GoodsType currGoods = goodsInfo.findGoods(goodsID);
		ClientType goodsOwner = new ClientInfo().viewIndividualInfo(currGoods.getOwner());
		System.out.println(currGoods.toString());
		System.out.println(goodsOwner.toString());
		if(currGoods != null && goodsOwner != null){
			System.out.println(currGoods.toString() + goodsOwner.toString());
			out.print(currGoods);
			out.print(goodsOwner);
		}
		else
			out.print("#");
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
