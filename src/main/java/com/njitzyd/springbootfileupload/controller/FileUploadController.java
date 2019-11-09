package com.njitzyd.springbootfileupload.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@Controller
public class FileUploadController {

	@Value("${file.uploadFolder}")
	private String uploadFolder;

	@GetMapping("/fileupload")
	public String fileupload() {
		return "fileupload";
	}

	/**
	 * 上传到本地
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/fileUpload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "上传失败，请选择文件";
		}
		String fileName = file.getOriginalFilename();
		String filePath = "D:/test/";
		File dest = new File(filePath + fileName);
		try {
			file.transferTo(dest);
			return "上传成功";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "上传失败！";
	}

	/**
	 * 跨服务器文件上传
	 * 
	 */
	@ResponseBody
	@PostMapping("/fileupload3")
	public String fileuoload3(@RequestParam("file") MultipartFile file) {
		System.out.println("跨服务器文件上传...");

		// 定义上传文件服务器路径
		String path = uploadFolder;

		// 获取上传文件的名称
		String filename = file.getOriginalFilename();

		// 创建客户端的对象
		Client client = Client.create();
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 将文件传入文件服务器
		WebResource resource = client.resource(path + filename);
		resource.put(String.class, inputStream);

		return "success";
	}

	/**
	 * 实现文件下载
	 * @param request
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	   @RequestMapping(value = "/download", method = RequestMethod.GET)
	    public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileName") String fileName) throws IOException {
	       
	    	 BufferedInputStream in = null;
	         ByteArrayOutputStream out = null;
	         URLConnection conn = null;
	         HttpHeaders headers = new HttpHeaders();
	         headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);;
			 headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
	         try {
	             URL url = new URL(uploadFolder+fileName);
	             conn = url.openConnection();
	             in = new BufferedInputStream(conn.getInputStream());
	             out = new ByteArrayOutputStream(1024);
	             byte[] temp = new byte[1024];
	             int size = 0;
	             while ((size = in.read(temp)) != -1) {
	                 out.write(temp, 0, size);
	             }
	             byte[] content = out.toByteArray();
	             return new ResponseEntity<>(content, headers, HttpStatus.OK);
	         } catch (Exception e) {
	             e.printStackTrace();
	         } finally {
	                 in.close();
	                 out.close();
	         }
	    	return null;
	    }

}
