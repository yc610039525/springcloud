package com.github.i24x.springcloud.commons.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
@RequestMapping("/import")
@Controller
public class FileImportAction {
	public static String fileRoot = "D:\\";
	private static Logger logger = LoggerFactory.getLogger(FileImportAction.class);
	@RequestMapping(value="/upload")
	public String upload(HttpServletRequest request,HttpServletResponse response) throws IOException  {
		String fileParam = request.getParameter("username"); 
		logger.info("fileParam:"+fileParam);
		StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
//		multipartRequest.getFiles("filename");
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for(MultipartFile file : fileMap.values()){
			String fileName = file.getOriginalFilename();
			logger.info("fileName:"+fileName);
			FileOutputStream fos = new FileOutputStream(new File(fileRoot+fileName));
			BufferedOutputStream bos=new BufferedOutputStream(fos);
			BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
			byte[] bytes = new byte[1024];
			while(bis.read(bytes)!=-1){
				bos.write(bytes);
			}
			bos.close();
			bis.close();
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("上传"+fileMap.size()+"个文件成功!");
		return null;
	}
	
	@RequestMapping(value="/uploadmulti")
	public @ResponseBody String upload(@RequestParam("username") String username,@RequestParam("password") String password,
			HttpServletRequest request,@RequestParam("file0") MultipartFile file0) throws IOException  {
		return username; 
	}
}
