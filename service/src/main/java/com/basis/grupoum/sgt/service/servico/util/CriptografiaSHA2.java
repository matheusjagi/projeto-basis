package com.basis.grupoum.sgt.service.servico.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaSHA2 {

    public static String geraCriptografia(String cpf) {

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(cpf.getBytes("UTF-8"));
            String msgDecode  = new String(messageDigest, "UTF-8");
            return msgDecode;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
