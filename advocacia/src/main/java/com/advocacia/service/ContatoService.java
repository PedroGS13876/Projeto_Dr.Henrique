package com.advocacia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.advocacia.model.ContatoMensagem;
import com.advocacia.repository.ContatoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço responsável pela lógica de negócio do formulário de contato.
 * Salva mensagens no banco e (opcionalmente) envia e-mails de notificação.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final JavaMailSender mailSender;

    // Injetado do application.properties
    @Value("${advocacia.email}")
    private String emailAdvogado;

    @Value("${advocacia.nome}")
    private String nomeAdvogado;

    /**
     * Salva a mensagem de contato no banco de dados.
     * Em produção, aqui também seria disparado o envio de e-mail.
     *
     * @param mensagem objeto com os dados do formulário (já validado pelo controller)
     * @return mensagem salva com ID gerado
        */
    @Transactional
    public ContatoMensagem salvarMensagem(ContatoMensagem mensagem) {
        log.info("Nova mensagem de contato recebida de: {} <{}>", mensagem.getNome(), mensagem.getEmail());

        // Salva no banco de dados
        ContatoMensagem salva = contatoRepository.save(mensagem);

        // Simula envio de e-mail (log apenas)
        // Para ativar envio real: injete JavaMailSender e configure o SMTP no application.properties
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailAdvogado);
        email.setFrom(emailAdvogado);
        email.setSubject("Novo contato: " + mensagem.getAssunto());
        email.setText(
            "Nome: " + mensagem.getNome() + "\n" +
            "E-mail: " + mensagem.getEmail() + "\n" +
            "Telefone: " + mensagem.getTelefone() + "\n\n" +
            "Mensagem:\n" + mensagem.getMensagem()
        );
        mailSender.send(email);
        log.info("   Assunto: Nova mensagem de contato - {}", mensagem.getAssunto());
        log.info("   Remetente: {} <{}>", mensagem.getNome(), mensagem.getEmail());

        return salva;
    }

    /**
     * Retorna todas as mensagens de contato, ordenadas por data (mais recente primeiro)
     */
    @Transactional(readOnly = true)
    public List<ContatoMensagem> listarTodas() {
        return contatoRepository.findAllByOrderByDataEnvioDesc();
    }

    /**
     * Retorna o total de mensagens não lidas
     */
    @Transactional(readOnly = true)
    public long contarNaoLidas() {
        return contatoRepository.countByLidaFalse();
    }

    /**
     * Marca uma mensagem como lida pelo ID
     */
    @Transactional
    public void marcarComoLida(Long id) {
        contatoRepository.findById(id).ifPresent(msg -> {
            msg.setLida(true);
            contatoRepository.save(msg);
            log.info("Mensagem ID {} marcada como lida", id);
        });
    }
}
