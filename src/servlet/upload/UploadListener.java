package servlet.upload;

import org.apache.commons.fileupload.ProgressListener;

/**注意commons-fileupload1.2.1.jar 只有此版或以上版本才有ProgressListener*/
public class UploadListener implements ProgressListener {
	private UploadStatus status;//记录上传信息为Java Bean
	
	public UploadListener(UploadStatus status){
		this.status=status;
	}
	
	public void update(long byteRead,long contentLength,int items){
		status.setByteRead(byteRead);//已读取的数据长度
		status.setContentLength(contentLength);//文件的总长度 
		status.setItems(items);//正在保存第几个文件
	}
}
