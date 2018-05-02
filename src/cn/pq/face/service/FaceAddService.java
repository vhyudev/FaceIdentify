package cn.pq.face.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.pq.face.GetInstance;
import cn.pq.faceService.FaceService;
import cn.pq.faceServiceImpl.BaiDuFaceService;

/**
 * Servlet implementation class FaceAdd
 */
public class FaceAddService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FaceAddService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String group_id = request.getParameter("group_id");
		String image = request.getParameter("image");
		String user_info = request.getParameter("user_info");
		String action_type = request.getParameter("action_type");
		
		
		String add = GetInstance.getFS().add(uid, group_id, image, user_info, action_type);
		response.getWriter().write(add);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
