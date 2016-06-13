package kr.co.devpack.Controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.devpack.Members;
import kr.co.devpack.Service.LoginService;

@Controller
public class LoginController {
	@Autowired 
	private LoginService loginService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request){		
		Members member = new Members();
		member.set_email((String) request.getParameter("email"));
		member.set_password((String) request.getParameter("password"));
		
		
		boolean checkLogin = loginService.checkLogin(member);
		logger.info("login info : " + checkLogin);
		
		String view = "/";
		if(checkLogin){
			view = "/mainBoard";
		}
		
		logger.info("login info : "+ view);
		ModelAndView result = new ModelAndView();
		
		RedirectView rv = new RedirectView(view);
		rv.setExposeModelAttributes(true);
		result = (ModelAndView) new ModelAndView(rv);
		
		return result;
	}
}
