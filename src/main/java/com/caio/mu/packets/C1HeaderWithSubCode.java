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



    protected byte[] toArray(){
       return new byte[]{ type,length,code,subCode};
    }

}
