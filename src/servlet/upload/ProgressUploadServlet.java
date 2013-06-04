package servlet.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ProgressUploadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-store");// 禁止浏览器缓存
		resp.setHeader("Pragrma", "no-cache");// 禁止浏览器缓存
		resp.setDateHeader("Expires", 0);// 禁止浏览器缓存

		UploadStatus status = (UploadStatus) req.getSession(true).getAttribute(
				"uploadstatus");
		if (status == null) {
			this.log("没有上传文件!");
			return;
		}
		long startTime = status.getStartTime();
		long currentTime = System.currentTimeMillis();
		long time = (currentTime - startTime) / 1000 + 1; // 已传输的时间 单位：s
		double velocity = ((double) status.getByteRead()) / (double) time;// 传输速度
																			// bytes/s
		double totalTime = status.getContentLength() / velocity;// 估计时间
		double timeLeft = totalTime - time;// 剩余时间
		int percent = (int) (100 * (double) status.getByteRead() / (double) status
				.getContentLength());// 已完成的百分比
		double length = ((double) status.getByteRead() / 1024 / 1024);// 已完成数
																		// 单位：M
		double totalLength = ((double) status.getContentLength()) / 1024 / 1024;// 总长度：
																				// 单位：M
		// 格式:
		// 百分比||已完成数(M)||文件总长度(M)||传输速率(K)||已用时间(S)||估计总时间(S)||估计剩余时间(S)||正在上传第几个文件
		String value = percent + "||" + length + "||" + totalLength + "||"
				+ velocity + "||" + time + "||" + totalTime + "||" + timeLeft
				+ "||" + status.getItems();
		resp.getWriter().print(value);// 输出给浏览器进度条

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UploadStatus status = new UploadStatus();// 上传状态
		UploadListener listener = new UploadListener(status);// 监听器
		req.getSession(true).setAttribute("uploadstatus", status);
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());// 解析
		upload.setProgressListener(listener);// 设置上传的listener
		
		
		try {
//			upload.setSizeMax(10*1024*1024);//设置大小，超出则抛出FileUploadException
			List itemList = upload.parseRequest(req);
			for (Iterator it = itemList.iterator(); it.hasNext();) {
				FileItem item = (FileItem) it.next();
				if (!item.isFormField())// 如果是非表单数据
				{		
					if(item.getName().length()<=0)return;//表示后面的文件域没有选中文件
					
					this.log("file size: "+((double)item.getSize()/1024/1024)+"M");
					this.log("file type："+item.getContentType());//文件类型
					this.log("File: " + item.getName());
					this.log("File Extension："+item.getName().substring(item.getName().lastIndexOf(".")));
					
					//方式一：直接采用FileItem输出，注意C盘是否有此文件夹 
					 item.write(new File("C:", item.getName()));
//					 
					 //方式二：采用文件输入输出流，输出文件
					 
					/*// 统一Linux与windows的路径分隔符
					String fileName =item.getName().replace("/", "\\");
					//fileName = fileName.substring(fileName.lastIndexOf("\\"));
					File saved = new File("C:\\upload_test", fileName);
					saved.getParentFile().mkdirs();// 保证路径存在
					InputStream ins = item.getInputStream();
					OutputStream ous = new FileOutputStream(saved);
					byte[] tmp = new byte[1024];
					int len = -1;
					while ((len = ins.read(tmp)) != -1) {
						ous.write(tmp, 0, len);
					}
					ous.close();
					ins.close();
					this.log("已保存文件：" + saved);*/
				}
			}
		} catch (Exception e) {
			this.log("上传发生错误: " + e.getMessage());
		}
	}
}
