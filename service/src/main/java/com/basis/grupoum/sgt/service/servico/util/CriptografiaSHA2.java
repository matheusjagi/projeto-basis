package com.basis.grupoum.sgt.service.servico.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaSHA2 {

    public static String geraCriptografia(String cpf) {
        String msgDecode = null;

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(cpf.getBytes("UTF-8"));
            msgDecode  = new String(messageDigest, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return msgDecode;
    }
}
