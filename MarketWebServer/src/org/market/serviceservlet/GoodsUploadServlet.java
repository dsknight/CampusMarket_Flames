package org.market.serviceservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.market.controlmysql.GoodsInfo;
import org.market.search.Segmentation;
import org.market.types.GoodsType;

public class GoodsUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GoodsUploadServlet() {
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
		System.out.println("*** GoodsUploadServlet ***");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean flag = false;//用来标记最终是否成功
		String strUTF8;//用来转换字符
		//填入新物品的信息并且写入数据库
		GoodsType currGoods = new GoodsType();
		int property = Integer.parseInt(request.getParameter("goodsProperty"));
		currGoods.setProperty(property);
		strUTF8 = new String(request.getParameter("goodsName").getBytes("8859_1"),"utf-8");
		currGoods.setName(strUTF8);
		currGoods.setLexemeName(Segmentation.IKAnalyze(currGoods.getName()));
		currGoods.setOwner(request.getParameter("goodsOwner"));
		currGoods.setDate(String.valueOf(new Date()));
		if(property == 0){//上传的是真实物品而不是需求
			System.out.println("upload goods");
			currGoods.setPrice(Integer.parseInt(request.getParameter("goodsPrice")));
			currGoods.setImage(request.getParameter("goodsImage"));
			currGoods.setMainClass(Integer.parseInt(request.getParameter("goodsClass")));
			currGoods.setSubClass(0);
			strUTF8 = new String(request.getParameter("goodsIntroduction").getBytes("8859_1"),"utf-8");
			currGoods.setIntroduction(strUTF8);
			if(new GoodsInfo().addGoods(currGoods))
				flag = true;
		}
		else{
			GoodsInfo goodsInfo = new GoodsInfo();
			System.out.println("add/update needs,needs = " + currGoods.getName() + " property = " + property);
			if(goodsInfo.needGoods(currGoods.getOwner()) == null){//如果原先没有，就增加需求
				System.out.println("try to add needs");
				if(goodsInfo.addGoods(currGoods)){
					System.out.println("add needs successfully");
					flag = true;
				}
			}
			else{
				System.out.println("try to update needs");
				if(goodsInfo.updateNeeds(currGoods)){//如果原先有，就更改需求
					System.out.println("add needs successfully");
					flag = true;
				}
			}
		}
		
		if(flag)
			out.print(new GoodsInfo().searchGoods(currGoods.getName()));
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
