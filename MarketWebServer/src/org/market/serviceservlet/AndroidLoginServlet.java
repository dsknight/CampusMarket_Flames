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

public class AndroidLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public AndroidLoginServlet() {
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
		System.out.println("*** AndroidLoginServlet ***");

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//DataOutputStream out = new DataOutputStream(response.getOutputStream());
		String usr = request.getParameter("username");
		String pwd = request.getParameter("password");
		System.out.println("Android usr:"+usr+"\nAndroid pwd:"+pwd);
		ClientType androidClient = new ClientType(usr,pwd);
		ClientInfo serveAndroid = new ClientInfo();
		GoodsInfo gInfo = new GoodsInfo();
		
		if(serveAndroid.login(androidClient)){
			System.out.println("Start serving Android...");
			ClientType respClient = serveAndroid.viewIndividualInfo(usr);
			String need = gInfo.needGoods(usr);
			System.out.println(usr+" needs " + need);
			//返回个人信息
			out.print(respClient.toString());
			//返回推荐信息
			out.print(gInfo.searchGoods(need));
			//返回需求
			out.print("!*C" + need);
			System.out.println("Succeed!!!!!!");
		}else{
			System.out.println("Android Search: no such guy");
			out.print("notFound");
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
