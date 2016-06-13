package kr.co.devpack.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.olleh.ucloudbiz.ucloudstorage.FilesClientExt;
import com.rackspacecloud.client.cloudfiles.FilesAuthorizationException;
import com.rackspacecloud.client.cloudfiles.FilesClient;
import com.rackspacecloud.client.cloudfiles.FilesConstants;
import com.rackspacecloud.client.cloudfiles.FilesContainer;
import com.rackspacecloud.client.cloudfiles.FilesException;
import com.rackspacecloud.client.cloudfiles.FilesObject;

import kr.co.devpack.Controller.BoardMybatisController;


@Repository
public class StorageService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardMybatisController.class);
	public FilesClient getConn() throws IOException, HttpException {
		String username = "";
		String password = "";
		String authUrl  = "https://ssproxy.ucloudbiz.olleh.com/auth/v1.0";
		
		FilesClientExt client = null;
		
		try {
			client = new FilesClientExt(username, password, authUrl, 10000);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
		}
		try {
			boolean login = client.login();
			logger.info("login result===================" + login);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("login failed...: " + e.toString());
		}
		
		return client;
	}
	
	public void createContainer( FilesClient clientObj, String containerName ) throws FilesAuthorizationException, FilesException, IOException, HttpException{
		
		clientObj.createContainer(containerName);
	}
	
	public List<FilesContainer> listContainer( FilesClient clientObj ) throws FilesAuthorizationException, FilesException, IOException, HttpException{
		
		List<FilesContainer> containers = clientObj.listContainers();
		return containers;
	}
	
	public void uploadFile(FilesClient client, CommonsMultipartFile cmf){
		logger.info(cmf.getOriginalFilename());

		if(cmf.getOriginalFilename() != ""){
		
			File file = new File( cmf.getOriginalFilename() );
		    try {
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(cmf.getBytes());
				fos.close();			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    
		    int dotIndex = cmf.getOriginalFilename().lastIndexOf(".");
			String fileExtension = cmf.getOriginalFilename().substring(0, dotIndex);
			
		    String mimeType = FilesConstants.getMimetype(fileExtension);
			StorageService storage = new StorageService();
			try {
				client = storage.getConn();
				client.storeObject("CONT1", file , mimeType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return;
		}
		
	}
	
	public File downloadFile(FilesClient client, String fileName){
		
		FilesObject obj;
		File outfile = new File(fileName);
		List<FilesObject> objects;
		File file = null;
		try {
			objects = client.listObjects("CONT1");
			for (FilesObject object : objects) {
		        String name = object.getName();
		        
		        if (name.equals(fileName)) {
		                obj = object;
		                obj.writeObjectToFile(outfile);
		                file = new File(obj.getName());
		        }
		        
			}
		} catch (FilesAuthorizationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FilesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
}

