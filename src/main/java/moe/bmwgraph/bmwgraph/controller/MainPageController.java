package moe.bmwgraph.bmwgraph.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainPageController {
    @GetMapping("/upload")
    public String displayUploadPage(){
        return "index";
    }
}
