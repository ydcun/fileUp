package servlet.upload;

public class UploadStatus {
	private long byteRead;//已上传的字节数，单位：字节
	private long contentLength;//所有文件的总长度
	private int items;//正在上传第几个文件
	private long startTime=System.currentTimeMillis();
	
	public long getByteRead() {
		return byteRead;
	}
	public void setByteRead(long byteRead) {
		this.byteRead = byteRead;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public int getItems() {
		return items;
	}
	public void setItems(int items) {
		this.items = items;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	
}
