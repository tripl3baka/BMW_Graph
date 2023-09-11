package moe.bmwgraph.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphPageController {

    @GetMapping("/graph")
    public String displayGraphPageAction(){
        return "graphPage";
    }
}
