package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class SwapMemory extends Memory {

    private double available;

    @Builder
    public SwapMemory(Double total, Double free, Double used, Double available) {
        setTotal(total);
        setUsed(used);
        setFree(free);
        this.available = available;
    }
}
