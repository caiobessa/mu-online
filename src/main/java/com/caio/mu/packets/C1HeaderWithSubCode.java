package com.caio.mu.packets;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class C1HeaderWithSubCode {

    private Byte type;
    private Byte length;
    private Byte code;
    private Byte subCode;



    protected List<Byte> convert(){
        List<Byte> data = new ArrayList<>();
        data.set(0, type);
        data.set(1, length);
        data.set(2, code);
        data.set(3, subCode);
        return data;
    }

}
