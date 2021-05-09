package com.basis.grupoum.sgt.service.servico.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaSHA2 {

    public static String geraCriptografia(String cpf) {
        String msgDecode = null;

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            algorithm.update(cpf.getBytes(StandardCharsets.UTF_8));
            msgDecode = String.format("%064x", new BigInteger(1, algorithm.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return msgDecode;
    }
}
