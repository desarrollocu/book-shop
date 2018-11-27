package soft.co.books.configuration.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
//@RequestMapping
@Controller
public class ForwardResource {

//    @GetMapping(value = "/**/{path:[^\\.]*}")
//    public ModelAndView forward() {
//        return new ModelAndView("/index.html");
//    }

    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}
