package kr.co.devpack.Controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.rackspacecloud.client.cloudfiles.FilesClient;

import kr.co.devpack.Boards;
import kr.co.devpack.DAOService.BoardDAOService;
import kr.co.devpack.Service.StorageService;

@Controller
public class BoardMybatisController {
	 @Autowired
	 private BoardDAOService BoardDAOService;
	 private static final Logger logger = LoggerFactory.getLogger(BoardMybatisController.class);
	 
	@RequestMapping("/mainBoard")
	public ModelAndView main(Locale locale, Model model) {
	 
		logger.info("Welcome main board.", locale);
		
		ModelAndView result = new ModelAndView();
	
		List<Boards> boardList = BoardDAOService.getBoards();
		result.addObject("result", boardList);
		result.setViewName("/board/list");
		
		return result;
	}
	
	@RequestMapping(value ="/selectBoard")
	public ModelAndView selectBoard(HttpServletRequest request){
	    
	    Boards board = new Boards();
	    board.set_id((String) request.getParameter("id"));
	    
	    List<Boards> boardList = BoardDAOService.selectBoard(board);
	    
	    ModelAndView result = new ModelAndView();
	    result.addObject("result", boardList);
		result.setViewName("/board/content");
	    
		return result;
	}
		    
	@RequestMapping(value ="/insertBoard", method = RequestMethod.POST)
	public ModelAndView insert(MultipartHttpServletRequest request){

	    Boards boards = new Boards();
	    
	    Map<String, MultipartFile> files = request.getFileMap();
	    CommonsMultipartFile cmf = (CommonsMultipartFile) files.get("file");
	    StorageService storage = new StorageService();
	    FilesClient client;
		try {
			client = storage.getConn();
			storage.uploadFile(client, cmf);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		}
	    
	    boards.set_file(cmf.getOriginalFilename());
	    boards.set_title((String) request.getParameter("title"));
	    boards.set_content((String) request.getParameter("content"));
	    boards.set_writer((String) request.getParameter("writer"));
	    boards.set_write_date((String) request.getParameter("write_date"));
	    	    
	    BoardDAOService.insertBoard(boards);
	    
		ModelAndView result = new ModelAndView();
		result.setView( new RedirectView("/mainBoard"));
		return result;
	}
	@RequestMapping(value ="/insertBoard", method = RequestMethod.GET)
	public ModelAndView insertGet(HttpServletRequest request){

	    Boards boards = new Boards();
	    
	    boards.set_title((String) request.getParameter("title"));
	    logger.info("INSERT BBBBBBBOARD" + request.getParameter("title")); 
	    BoardDAOService.insertBoard(boards);
	    
		ModelAndView result = new ModelAndView();
		result.setView( new RedirectView("/mainBoard"));
		return result;
	}	
	
	@RequestMapping(value ="/updateBoard", method = RequestMethod.GET)
	public ModelAndView updateBoardPageChange(HttpServletRequest request) {
		
		logger.info("update board.");
		Boards board = new Boards();
	    board.set_id((String) request.getParameter("id"));
	    
	    List<Boards> boardList = BoardDAOService.selectBoard(board);
	    
	    ModelAndView result = new ModelAndView();
	    result.addObject("result", boardList);
		result.setViewName("board/update");
	    
		return result;
	}
	
	@RequestMapping(value ="/updateBoard", method = RequestMethod.POST)
	public ModelAndView updateBoard(MultipartHttpServletRequest request) {
		
		Boards boards = new Boards();
	    
	    Map<String, MultipartFile> files = request.getFileMap();
	    CommonsMultipartFile cmf = (CommonsMultipartFile) files.get("file");
	    StorageService storage = new StorageService();
	    FilesClient client;
		try {
			client = storage.getConn();
			storage.uploadFile(client, cmf);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		}
	    
	    boards.set_file(cmf.getOriginalFilename());
	    boards.set_id((String) request.getParameter("id"));
	    boards.set_title((String) request.getParameter("title"));
	    boards.set_content((String) request.getParameter("content"));
	    boards.set_writer((String) request.getParameter("writer"));
	    boards.set_write_date((String) request.getParameter("write_date"));
	    logger.info("UPDATE ID" + boards.get_id());
	    BoardDAOService.updateBoard(boards);
	    
		ModelAndView result = new ModelAndView();
		result.setView( new RedirectView("/selectBoard?id="+boards.get_id()));
		return result;
	}
	
	
	@RequestMapping(value ="/deleteBoard")
	public ModelAndView delete(HttpServletRequest request){

		Boards boards = new Boards();
	    boards.set_id((String) request.getParameter("id"));
	    logger.info("GET ID " + (String) request.getParameter("id"));
	    BoardDAOService.deleteBoard(boards);
	    
	    ModelAndView result = new ModelAndView();
		result.setView( new RedirectView("/mainBoard"));
		return result;
	}
	
	@RequestMapping(value ="/createBoard")
	public ModelAndView create(HttpServletRequest request){
		    
	    BoardDAOService.createBoard();
	    
	    ModelAndView result = new ModelAndView();
	    result.setView( new RedirectView("/mainBoard"));
		return result;
	}
	
	@RequestMapping(value ="/dropBoard")
	public ModelAndView drop(HttpServletRequest request){
		    
	    BoardDAOService.dropBoard();
	    
	    ModelAndView result = new ModelAndView();
	    result.setView( new RedirectView("/mainBoard"));
		return result;
	}
	
	@RequestMapping(value ="/downloadFile")
	public ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		logger.info("FILE DOWNLOAD");
	    String fileName = (String) request.getParameter("file");
	
	    StorageService storage = new StorageService();
	    FilesClient client = null;
	    File file = null;
		try {
			client = storage.getConn();
			file = storage.downloadFile(client, fileName);
		} catch (HttpException e1) {
			e1.printStackTrace();
			logger.info("DOWNLOAD ERROR" + e1.toString());
		}
		
		logger.info("1111111111111111");
		if(!file.exists()) {
	        throw new FileNotFoundException();
		}
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		logger.info(file.toString());
		try {	
		        outStream = response.getOutputStream();
		        inputStream = new FileInputStream(file);                
		        
		        //Setting Resqponse Header		
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition",                     
		                           "attachment;filename=\""+file.getName()+"\"");
		        //Writing InputStream to OutputStream
		        byte[] outByte = new byte[4096];
		        while(inputStream.read(outByte, 0, 4096) != -1) {
		        	outStream.write(outByte, 0, 4096);
		        }
		} catch (Exception e) {
			logger.info(e.toString());
			e.printStackTrace();
			throw new IOException(); 
		} finally {
		        inputStream.close();
		        outStream.flush();
		        outStream.close();
		}
		
	    ModelAndView result = new ModelAndView();
	    String id = (String) request.getParameter("id");
	    result.setView( new RedirectView("/selectBoard?id=" + id));
	    
		return result;
	}
	
	
	@RequestMapping(value ="/totalBoard")
	public ModelAndView sum(HttpServletRequest request){

		Boards boards = new Boards();
		String randStr;
		// 숫자를 제외한 길이 32의 랜덤 문자열 생성.
		randStr =  new RandomStringUtils().random(16);
		logger.info(randStr + " ======================");
		boards.set_content(randStr);
	    
		
		BoardDAOService.insertBoard(boards);
		List<Boards> boardList = BoardDAOService.getBoards();
		int length = boardList.size();
		Boards b = boardList.get(length-1);
		
		boards.set_id(b.get_id());
		randStr =  new RandomStringUtils().random(16);
		logger.info(randStr + " ======================");
		boards.set_content(randStr);
		BoardDAOService.updateBoard(boards);
		BoardDAOService.deleteBoard(boards);
		ModelAndView result = new ModelAndView();
		result.setView( new RedirectView("/mainBoard"));
		return result;
	}
}
