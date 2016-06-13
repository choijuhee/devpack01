package kr.co.devpack.Service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.devpack.Members;
import kr.co.devpack.Controller.HomeController;
import kr.co.devpack.DAOService.MemberDAOService;

@Repository
public class LoginService{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private MemberDAOService memberDAOService;
	
	public boolean checkLogin(Members member){
		
		boolean result = false;

		ArrayList<Members> selectMember = memberDAOService.selectMember(member);
		
		if( selectMember.size() > 0 ){
			if( selectMember.get(0).get_password().equals(member.get_password()) ){
				result = true;
			}				
        }
		
		return result;

	}
}

