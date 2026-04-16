package com.advocacia.repository;

import com.advocacia.model.ContatoMensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA para operações de banco de dados das mensagens de contato.
 * Spring Data JPA gera automaticamente as implementações dos métodos.
 */
@Repository
public interface ContatoRepository extends JpaRepository<ContatoMensagem, Long> {

    /**
     * Retorna mensagens não lidas, ordenadas pela mais recente
     */
    List<ContatoMensagem> findByLidaFalseOrderByDataEnvioDesc();

    /**
     * Conta total de mensagens não lidas
     */
    long countByLidaFalse();

    /**
     * Retorna todas as mensagens ordenadas pela mais recente
     */
    List<ContatoMensagem> findAllByOrderByDataEnvioDesc();

    /**
     * Busca mensagens por e-mail do remetente
     */
    List<ContatoMensagem> findByEmailIgnoreCase(String email);
}
