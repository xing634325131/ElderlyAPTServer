package com.kingtime.elderlyapt.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kingtime.elderlyapt.dao.ImageDao;
import com.kingtime.elderlyapt.dao.impl.ImageDaoImpl;
import com.kingtime.elderlyapt.entity.PictureUpload;

public class UploadImageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uid = null;
		String requestName = null;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		ImageDao imageDao = new ImageDaoImpl();
		PictureUpload pictureUpload = new PictureUpload();

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter outPrintWriter = response.getWriter();

		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					System.out.println("fieldname-->>" + fieldName);
					if (fieldName.toLowerCase().equals("uid")) {
						uid = URLDecoder.decode(item.getString("UTF-8"),
								"UTF-8");
						pictureUpload.setUid(Integer.valueOf(uid));
					}
					if (fieldName.toLowerCase().equals("requestname")) {
						requestName = item.getString("UTF-8");
					}
				} else {
					String fullFileName = item.getName();
					System.out.println("fullname:" + fullFileName);
					String path = null;
					if (requestName.equals("uploadActivityImage")) {
						path = this.getServletContext().getRealPath("\\Image\\Picture");
					} else if (requestName.equals("uploadUserPhoto")) {
						path = this.getServletContext().getRealPath("\\Image\\Photo");
					}
					File f = new File(path + "\\" + fullFileName);
					item.write(f);
					System.out.println("upload filename:" + f.getAbsolutePath());
					pictureUpload.setPicName(fullFileName);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(uid + "--" + requestName);
		if (requestName.equals("uploadActivityImage")) {
			boolean uploadResult = imageDao.saveUploadActivityImage(pictureUpload);
			System.out.println("return :" + uploadResult);
			outPrintWriter.write(uploadResult ? "true" : "false");
		} else if (requestName.equals("uploadUserPhoto")) {
			boolean uploadResult = imageDao.saveUploadUserPhoto(pictureUpload);
			System.out.println("return :" + uploadResult);
			outPrintWriter.write(uploadResult ? "true" : "false");
		}
		outPrintWriter.flush();
		outPrintWriter.close();
	}

}
