package com.expertpeople.modules.zone.Vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneResult<T> {
    private T zone;
    private T allZone;

    public ZoneResult(T zone, T allZone){
        this.zone=zone;
        this.allZone=allZone;
    }
}
