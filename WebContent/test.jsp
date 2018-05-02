<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="faceUpload" enctype="multipart/form-data" method="post" >  
          
               姓名：<input type="text" name="username"> <br/>
               身份证号：<input type="text" name="idcardnum"> <br/>
                性别：<input type="text" name="gender"> <br/>
               电话:<input type="text" name="phone"> <br/>
               联系地址:<input type="text" name="address"> <br/>
              
        
               上传文件：<input type="file" name="file1"><br/>  
              上传文件： <input type="file" name="file2"><br/>  
              <input type="submit" value="提交"/>  
       
     </form>  
       
</body>
</html>