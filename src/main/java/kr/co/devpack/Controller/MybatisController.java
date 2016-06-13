package kr.co.devpack.Controller;



import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.devpack.Members;
import kr.co.devpack.DAOService.MemberDAOService;
 
@Controller
public class MybatisController {
    
    @Autowired
    private MemberDAOService memberDAOService;
    
    private static final Logger logger = LoggerFactory.getLogger(MybatisController.class);
    
    
    //시작 메인화면.
    @RequestMapping("/main")
    public ModelAndView main(Locale locale, Model model) {
        logger.info("Welcome main.", locale);
 
        // view 화면인 main.jsp에 DB로부터 읽어온 데이터를 보여준다.
        ModelAndView result = new ModelAndView();
        //addObject view에 넘어가는 데이터
        List<Members> memberList = memberDAOService.getMembers();
        result.addObject("result", memberList);
        result.setViewName("main");
        return result;
    }
}
