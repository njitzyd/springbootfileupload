<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文件上传demo</title>
</head>
<body>
	
	 <form action="/fileupload3" method="post" enctype="multipart/form-data">
        <label>上传图片</label>
        <input type="file" name="file"/>
        <input type="submit" value="上传"/>
    </form>
    <p>图片:</p>
    <a href="/download?fileName=bottom_sm.png">文件下载</a>

</body>
</html>