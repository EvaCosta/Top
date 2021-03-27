package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class SystemMemory extends Memory {

    private double bufferCache;

    @Builder
    public SystemMemory(String measureUnity, Double total, Double free, Double used, Double bufferCache) {
        setTotal(total);
        setMeasureUnity(measureUnity);
        setUsed(used);
        setFree(free);
        this.bufferCache = bufferCache;
    }
}
