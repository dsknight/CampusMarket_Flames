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

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean flag = false;//������������Ƿ�ɹ�
		//��������Ʒ����Ϣ����д�����ݿ�
		GoodsType currGoods = new GoodsType();
		int property = Integer.parseInt(request.getParameter("goodsProperty"));
		currGoods.setProperty(property);
		currGoods.setName(request.getParameter("goodsName"));
		currGoods.setLexemeName(Segmentation.IKAnalyze(currGoods.getName()));
		currGoods.setOwner(request.getParameter("goodsOwner"));
		currGoods.setDate(String.valueOf(new Date()));
		if(property == 0){//�ϴ�������ʵ��Ʒ����������
			currGoods.setPrice(Integer.parseInt(request.getParameter("goodsPrice")));
			currGoods.setImage(request.getParameter("goodsImage"));
			currGoods.setMainClass(Integer.parseInt(request.getParameter("goodsClass")));
			currGoods.setSubClass(0);
			currGoods.setIntroduction(request.getParameter("goodsIntroduction"));
			if(new GoodsInfo().addGoods(currGoods))
				flag = true;
		}
		else{
			GoodsInfo goodsInfo = new GoodsInfo();
			if(goodsInfo.needGoods(currGoods.getOwner()) == null){//���ԭ��û�У�����������
				if(goodsInfo.addGoods(currGoods))
					flag = true;
			}
			else{
				if(goodsInfo.updateNeeds(currGoods))//���ԭ���У��͸�������
					flag = true;
			}
		}
		
		if(flag)
			out.print("*");
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
