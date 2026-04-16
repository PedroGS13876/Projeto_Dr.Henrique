package com.advocacia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade que representa uma mensagem enviada pelo formulário de contato.
 * Persistida no banco de dados para acompanhamento posterior.
 */
@Entity
@Table(name = "contato_mensagens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoMensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    @Column(nullable = false, length = 150)
    private String email;

    @Size(max = 20, message = "Telefone inválido")
    @Column(length = 20)
    private String telefone;

    @NotBlank(message = "O assunto é obrigatório")
    @Size(min = 5, max = 200, message = "O assunto deve ter entre 5 e 200 caracteres")
    @Column(nullable = false, length = 200)
    private String assunto;

    @NotBlank(message = "A mensagem é obrigatória")
    @Size(min = 10, max = 2000, message = "A mensagem deve ter entre 10 e 2000 caracteres")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    @Column(name = "lida")
    private boolean lida = false;

    @PrePersist
    public void prePersist() {
        this.dataEnvio = LocalDateTime.now();
    }
}
