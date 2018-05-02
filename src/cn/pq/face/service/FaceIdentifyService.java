package cn.pq.face.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.pq.face.GetInstance;
import cn.pq.faceService.FaceService;
import cn.pq.faceServiceImpl.BaiDuFaceService;
import cn.pq.test.Identify;

/**
 * Servlet implementation class FaceIdentifyService
 */
public class FaceIdentifyService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FaceIdentifyService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String image,String group_id,String ext_fields,String user_top_num);
		String image = request.getParameter("image");
		String group_id = request.getParameter("group_id");
		String ext_fields = request.getParameter("ext_fields");
		String user_top_num = request.getParameter("user_top_num");
		
		String identify = GetInstance.getFS().identify(image, group_id, ext_fields, user_top_num);
		response.getWriter().write(identify);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
