package kr.co.devpack.DAOService;


import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.devpack.Members;
import kr.co.devpack.Controller.HomeController;
import kr.co.devpack.DAO.MemberDAO;
import kr.co.devpack.Mapper.MemberMapper;

@Repository
public class MemberDAOService implements MemberDAO {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SqlSession sqlSession;
    
    @Override
    public ArrayList<Members> getMembers() {
        ArrayList<Members> result = new ArrayList<Members>();
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
        result = memberMapper.getMembers();
        
        return result;
    }
    
    @Override
    public ArrayList<Members> selectMember(Members member) {
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
        ArrayList<Members> result = memberMapper.selectMember(member);
        
        return result;
    }
    
    @Override
    public void insertMember(Members member) {
    	logger.info("before insert mapper");
    	try {
            MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
            memberMapper.insertMember(member);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString() );
		}
        logger.info("after insert mapper");
    }
    
    @Override
    public void createMember() {
    	
    	logger.info("create member");
    	try {
    		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
            memberMapper.createMember();
		} catch (Exception e) {
			logger.info(e.toString() );
		}
        
        
        logger.info("create success");
    }
    
    @Override
    public void updateMember(Members member) {
    }
    
    @Override
    public void deleteMember(Members member) {
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
        memberMapper.deleteMember(member);
    }
    
    @Override
    public void dropMember() {
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
        logger.info("drop...");
       	memberMapper.dropMember();
       
        logger.info("dropped...");
    }

}