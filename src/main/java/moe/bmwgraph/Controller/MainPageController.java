package moe.bmwgraph.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/graph")
    public String displayMainPageAction(){
        return "index";
    }
}
