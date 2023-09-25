package moe.bmwgraph.Controller;

import moe.bmwgraph.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@Controller
public class GraphPageController {

    @Autowired
    FileStorageService fileStorageService;


    @GetMapping("/edit/{filename}")

    public String displayGraphPageAction(@PathVariable String filename, Model model) {

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileStorageService.getFilePath(filename).toString()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(records.size());

        model.addAttribute("records", records);


        return "graphEditPage";
    }
}
