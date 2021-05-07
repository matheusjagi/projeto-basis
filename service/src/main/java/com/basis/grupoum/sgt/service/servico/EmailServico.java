package com.basis.grupoum.sgt.service.servico;

import com.basis.grupoum.sgt.service.configuracao.ApplicationProperties;
import com.basis.grupoum.sgt.service.servico.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServico {

    private final JavaMailSender javaMailSender;
    private final ApplicationProperties applicationProperties;

    public void sendEmail(EmailDTO emailDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            message.setTo(emailDTO.getDestinatario());
            message.setFrom(applicationProperties.getEnderecoRemetente(),
                    applicationProperties.getNomeRemetente());
            message.setSubject(emailDTO.getAssunto());

            for(String c: emailDTO.getCopias()){
                message.addCc(c);
            }

            message.setText(emailDTO.getCorpo(), true);

            javaMailSender.send(mimeMessage);
        }catch (MessagingException | UnsupportedEncodingException e){
            throw new RuntimeException();
        }
    }

}
