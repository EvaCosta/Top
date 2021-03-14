package br.edu.barbacena.ifsudestmg.ws.top.api.controller.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class SystemMemory extends Memory {

    private double bufferCache;

    @Builder
    public SystemMemory(Double total, Double free, Double used, Double bufferCache) {
        setTotal(total);
        setUsed(used);
        setFree(free);
        this.bufferCache = bufferCache;
    }
}
