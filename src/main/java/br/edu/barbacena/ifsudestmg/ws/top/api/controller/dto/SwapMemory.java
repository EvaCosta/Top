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
    public SwapMemory(String measureUnity, Double total, Double free, Double used, Double available) {
        setMeasureUnity(measureUnity);
        setTotal(total);
        setUsed(used);
        setFree(free);
        this.available = available;
    }
}
