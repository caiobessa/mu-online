package com.caio.mu.packets;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class C1HeaderWithSubCode {

    public C1HeaderWithSubCode(List<Byte> packet){

    }


    private List<Byte> data =  new ArrayList<>();

    private Byte type;
    private Byte length;
    private Byte code;
    private Byte subCode;


    private List<Data> getData(){
        data.set(0, type);
        data.set(1, length);
        data.set(2, code);
        data.set(3, subCode);
    }

}
