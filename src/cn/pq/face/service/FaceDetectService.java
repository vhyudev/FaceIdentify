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
 * Servlet implementation class FaceDetect
 */
public class FaceDetectService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FaceDetectService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String image = request.getParameter("image");
		String face_fields=request.getParameter("face_fields");
		String max_face_num=request.getParameter("max_face_num");
		
		
		String detect = GetInstance.getFS().detect(image,max_face_num,face_fields);
		response.getWriter().write(detect);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
