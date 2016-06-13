package kr.co.devpack.Controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.devpack.Members;
import kr.co.devpack.DAOService.MemberDAOService;

@Controller
public class MemberController {
    
    @Autowired
    private MemberDAOService memberDAOService;
    
    @RequestMapping(value ="/insertMember", method = RequestMethod.POST)
    public ModelAndView insert(HttpServletRequest request){
        
        Members member = new Members();
        member.set_name((String) request.getParameter("name"));
        member.set_email((String) request.getParameter("email"));
        member.set_phone((String) request.getParameter("phone"));
        member.set_password((String) request.getParameter("password"));
        
        memberDAOService.insertMember(member);
        System.out.println("insert complete");
        
        ModelAndView result = new ModelAndView();        
        result.setViewName("/login/main");
        return result;
    }
    
    @RequestMapping(value ="/createMember")
    public ModelAndView create(HttpServletRequest request){
               
        memberDAOService.createMember();
        System.out.println("create table");
        
        ModelAndView result = new ModelAndView();
        memberDAOService.createMember();
        
        RedirectView rv = new RedirectView("/");
		rv.setExposeModelAttributes(true);
		result = (ModelAndView) new ModelAndView(rv);
        return result;
    }
    
    @RequestMapping(value ="/dropMember")
    public ModelAndView drop(HttpServletRequest request){
               
        memberDAOService.dropMember();
       
        ModelAndView result = new ModelAndView();
        
        RedirectView rv = new RedirectView("/");
		rv.setExposeModelAttributes(true);
		result = (ModelAndView) new ModelAndView(rv);
        return result;
    }
}
