package com.github.caoyeung.util;


public class FtpTools {
	
	/*private Logger logger = LoggerFactory.getLogger(FtpTools.class);
	
	private String localFileName;

	private String remoteFileName;

	private FtpClient ftpClient;*/
	/**
	 * JDK 1.6
	 * 连接FTP服务器
	 * @param ip 服务器名字
	 * @param port
	 * @param user 用户名
	 * @param password 密码
	 * @param path 服务器上的路径
	 * @param localFileName
	 * @param remoteFileName
	 */
	/*public void connectServer(String ip, int port, String user,
			String password, String path, String localFileName,String remoteFileName) {
		this.localFileName = localFileName;
		this.remoteFileName = remoteFileName;
		try {
			this.ftpClient = new FtpClient();
			this.login(ip, port, user, password);
			if (path.length() != 0) {
				this.ftpClient.cd(path);
			}
			this.ftpClient.binary();
		} catch (IOException e) {
			logger.error("连接FTP服务器失败", e);
		}
	}
	
	private void login(String ip, int port, String user, String password){
		try {
			this.ftpClient.openServer(ip, port);
			this.ftpClient.login(user, password);
			logger.info("登陆FTP服务器成功");
		} catch (IOException e) {
			logger.error("登陆FTP服务器失败", e);
		}
	}

	public void closeConnect() {
		try {
			this.ftpClient.closeServer();
			logger.info("关闭FTP服务器连接成功");
		} catch (IOException e) {
			logger.error("关闭FTP服务器连接失败", e);
		}
	}

	public void upload() {
		try {
			this.ftpClient.sendServer("quote PASV");
			TelnetOutputStream os = this.ftpClient.put(this.remoteFileName);
			java.io.File file_in = new java.io.File(this.localFileName);
			FileInputStream is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			logger.info("上传文件成功");
			is.close();
			os.close();
		} catch (IOException e) {
			logger.error("上传文件失败", e);
		}
	}

	public void download() {
		this.download(this.remoteFileName, this.localFileName);
	}

	public void download(String remotePath, String remoteFile, String localFile) {
		logger.info("下载文件 远程文件：" + remoteFile);
		logger.info("下载文件 本地文件：" + localFile);
		if (remotePath.length() != 0) {
			try {
				this.ftpClient.cd(remotePath);
			} catch (IOException e) {
				logger.error("下载文件失败", e);
			}
		}
		this.download(remoteFile, localFile);
	}

	public void download(String remoteFile, String localFile) {
		try {
			logger.info("下载文件 远程文件：" + remoteFile);
			logger.info("下载文件 本地文件：" + localFile);
			TelnetInputStream is = this.ftpClient.get(remoteFile);
			java.io.File file_in = new java.io.File(localFile);
			File parentDir = file_in.getParentFile();
			if(!parentDir.exists()){
				parentDir.mkdirs();
			}
			if(!file_in.exists()){
				file_in.createNewFile();
			}
			String p=file_in.getAbsolutePath();
			FileOutputStream os = new FileOutputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			logger.info("下载文件成功");
			os.close();
			is.close();
		} catch (IOException e) {
			logger.error("下载文件失败", e);
		}
	}
	
	public void deleteFile(String url){
		if(this.ftpClient!=null){
			this.ftpClient.sendServer("DELE " + url + "\r\n");
			try {
				int status = this.ftpClient.readServerResponse();
				if(status==250){
					logger.info("删除文件成功");
				}
			} catch (IOException e) {
				logger.error("删除文件失败", e);
			}
		}
	}
	
	*//**
	 * 返回ftp目录下的文件列表
	 * @param ftpdirectory
	 * @return
	 *//*
	 @SuppressWarnings({ "restriction", "deprecation" })
	public List<String> getFileNameList(String fileNameFlag,String ftpdirectory) 
	 { 
	    List<String> list = new ArrayList<String>(); 
	    try  { 
	       DataInputStream dis = new  DataInputStream(ftpClient.nameList(ftpdirectory)); 
	       String filename = ""; 
	       while((filename=dis.readLine())!=null){
	    	   if(fileNameFlag.contains(fileNameFlag)){
	    		   list.add(filename);  
	    	   }       
	       }   
	    }catch (Exception e)  { 
	    } 
	    return list; 
	 }*/
}
