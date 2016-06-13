package kr.co.devpack.Controller;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardMybatisController.class);
	 
	@RequestMapping("/writeBoard")
	public ModelAndView newBoard(Locale locale, Model model) {
	 
		logger.info("write board.", locale);
	 
		ModelAndView result = new ModelAndView();
	
		result.setViewName("board/write");
		return result;
	}

}

