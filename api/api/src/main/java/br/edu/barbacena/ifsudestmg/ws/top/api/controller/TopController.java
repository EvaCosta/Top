package br.edu.barbacena.ifsudestmg.ws.top.api.controller;

import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Top;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/top")
public class TopController {

    @GetMapping("/mock")
    public Top mock(){
        return new Mock().mockData();
    }
}
