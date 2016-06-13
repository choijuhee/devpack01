package kr.co.devpack.DAOService;


import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.devpack.Boards;
import kr.co.devpack.Controller.HomeController;
import kr.co.devpack.DAO.BoardDAO;
import kr.co.devpack.Mapper.BoardMapper;

@Repository
public class BoardDAOService implements BoardDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public ArrayList<Boards> getBoards(){
		ArrayList<Boards> result = new ArrayList<Boards>();
		try {
			BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
	        result = boardMapper.getBoards();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
		}	
		
		return result;
	}
	
	@Override
	public ArrayList<Boards> selectBoard(Boards board){
		ArrayList<Boards> result = new ArrayList<Boards>();
		try {
			BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
			result = boardMapper.selectBoard(board);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return result;
	}
	
	@Override
	public void insertBoard(Boards board){
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);

        logger.info("writer service : " + board.get_writer());
	    logger.info("content service : " + board.get_content());
	    try {
	    	boardMapper.insertBoard(board);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
		}
	}
	
	@Override
	public void updateBoard(Boards board){
		try {
			BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
			boardMapper.updateBoard(board);
		} catch (Exception e) {
			logger.info(e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteBoard(Boards board){
		logger.info("REMOVE ID " + board.get_id());
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		boardMapper.deleteBoard(board);
	}
	
	@Override
	public void createBoard(){
		try {
			BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
			boardMapper.createBoard();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
		}	
	}
	
	@Override
	public void dropBoard(){
		try {
			BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
			boardMapper.dropBoard();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
		}		
	}
}
