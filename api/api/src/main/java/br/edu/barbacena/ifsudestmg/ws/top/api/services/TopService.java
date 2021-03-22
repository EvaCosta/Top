package br.edu.barbacena.ifsudestmg.ws.top.api.services;

import br.edu.barbacena.ifsudestmg.ws.top.api.controller.Mock;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Process;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Top;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/top")
public class TopService {

    @GetMapping("/mock")
    public Top mock(){
        return new Mock().mockData();
    }

    /**
     * Retorna o processo, a partir do parâmetro idProcess, retorna o mesmo caso exista, caso não retorna um Objeto Process
     * com dados Nulos.
     * @param process
     * @return
     */
    @GetMapping(path = "/mock/{idProcess}")
    public Process findProcessId(@PathVariable("idProcess") Integer process){
        return new Mock().findProcessFromId(new Mock().mockData(), process);
    }//findProcessId
}

