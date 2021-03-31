package br.edu.barbacena.ifsudestmg.ws.top.api.services;

import br.edu.barbacena.ifsudestmg.ws.top.api.controller.Mock;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.TopController;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Process;
import br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto.Top;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/top")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class TopService {

    TopController topController = new TopController();
    Mock mock = new Mock(topController);

    @GetMapping("")
    public Top top() {
        return topController.runTopCommand();
    }

    @GetMapping("/process/{processId}")
    public Process findProcessById(@PathVariable("processId") Integer processId) {
        try {
            return top().getProcessById(processId);
        } catch (Exception e) {
            return null;
        }
    }

    //region Fictitious data. Should be removed in the final version
    @GetMapping("/mock")
    public Top mock() {
        return mock.mockData();
    }

    /**
     * Retorna o processo, a partir do parâmetro idProcess, retorna o mesmo caso exista, caso não exista, retorna null
     *
     * @param processId id do processo alvo
     * @return processo cujo id corresponde ao id buscado, null se nao encontrado
     */
    @GetMapping(path = "/mock/process/{idProcess}")
    public Process mockProcessById(@PathVariable("idProcess") Integer processId) {
        try {
            return mock().getProcessById(processId);
        } catch (Exception e) {
            return null;
        }
    }
    //endregion
}