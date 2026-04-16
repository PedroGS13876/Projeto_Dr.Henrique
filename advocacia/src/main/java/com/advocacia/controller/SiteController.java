package com.advocacia.controller;

import com.advocacia.model.ContatoMensagem;
import com.advocacia.service.ContatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller principal do site.
 * Gerencia as rotas de todas as páginas públicas e o formulário de contato.
 *
 * Arquitetura MVC:
 *   - Model: dados passados para o Thymeleaf via Model
 *   - View: templates HTML em src/main/resources/templates/
 *   - Controller: esta classe, que orquestra tudo
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class SiteController {

    private final ContatoService contatoService;

    // Dados do escritório injetados do application.properties
    @Value("${advocacia.nome}")
    private String nomeAdvogado;

    @Value("${advocacia.oab}")
    private String oabAdvogado;

    @Value("${advocacia.telefone}")
    private String telefone;

    @Value("${advocacia.whatsapp}")
    private String whatsapp;

    @Value("${advocacia.email}")
    private String emailAdvogado;

    @Value("${advocacia.endereco}")
    private String endereco;

    @Value("${advocacia.instagram}")
    private String instagram;

    @Value("${advocacia.linkedin}")
    private String linkedin;

    // =========================================================
    // Método auxiliar: adiciona dados comuns a todas as views
    // =========================================================
    private void adicionarDadosGlobais(Model model) {
        model.addAttribute("nomeAdvogado", nomeAdvogado);
        model.addAttribute("oabAdvogado", oabAdvogado);
        model.addAttribute("telefone", telefone);
        model.addAttribute("whatsapp", whatsapp);
        model.addAttribute("emailAdvogado", emailAdvogado);
        model.addAttribute("endereco", endereco);
        model.addAttribute("instagram", instagram);
        model.addAttribute("linkedin", linkedin);
    }

    // =========================================================
    // ROTA: Página Inicial (Home)
    // =========================================================
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        adicionarDadosGlobais(model);
        model.addAttribute("paginaAtual", "home");
        model.addAttribute("pageTitle", "Dr. Rafael Mendonça | Advogado Imobiliário em São Paulo");
        model.addAttribute("pageDescription",
            "Advocacia especializada em Direito Imobiliário em São Paulo. " +
            "Compra e venda de imóveis, contratos, regularização fundiária e muito mais.");
        log.debug("Acessando página Home");
        return "home";
    }

    // =========================================================
    // ROTA: Sobre o Advogado
    // =========================================================
    @GetMapping("/sobre")
    public String sobre(Model model) {
        adicionarDadosGlobais(model);
        model.addAttribute("paginaAtual", "sobre");
        model.addAttribute("pageTitle", "Sobre | Dr. Rafael Mendonça - Advogado Imobiliário");
        model.addAttribute("pageDescription",
            "Conheça o Dr. Rafael Mendonça, advogado especialista em Direito Imobiliário " +
            "com mais de 15 anos de experiência em São Paulo.");
        return "sobre";
    }

    // =========================================================
    // ROTA: Áreas de Atuação
    // =========================================================
    @GetMapping("/areas-de-atuacao")
    public String areasAtuacao(Model model) {
        adicionarDadosGlobais(model);
        model.addAttribute("paginaAtual", "areas");
        model.addAttribute("pageTitle", "Áreas de Atuação | Direito Imobiliário - Dr. Rafael Mendonça");
        model.addAttribute("pageDescription",
            "Atuação em todas as frentes do Direito Imobiliário: compra e venda, " +
            "locação, regularização, usucapião, inventário e muito mais.");
        return "areas";
    }

    // =========================================================
    // ROTA: Contato - GET (exibe o formulário)
    // =========================================================
    @GetMapping("/contato")
    public String contato(Model model) {
        adicionarDadosGlobais(model);
        model.addAttribute("paginaAtual", "contato");
        model.addAttribute("pageTitle", "Contato | Dr. Rafael Mendonça - Advocacia Imobiliária");
        model.addAttribute("pageDescription",
            "Entre em contato com o escritório Dr. Rafael Mendonça Advocacia. " +
            "Atendimento em São Paulo e região.");

        // Adiciona objeto vazio para o formulário Thymeleaf
        if (!model.containsAttribute("contatoMensagem")) {
            model.addAttribute("contatoMensagem", new ContatoMensagem());
        }
        return "contato";
    }

    // =========================================================
    // ROTA: Contato - POST (processa o formulário)
    // =========================================================
    @PostMapping("/contato/enviar")
    public String enviarContato(
            @Valid @ModelAttribute("contatoMensagem") ContatoMensagem mensagem,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Se há erros de validação, retorna ao formulário com os erros
        if (bindingResult.hasErrors()) {
            adicionarDadosGlobais(model);
            model.addAttribute("paginaAtual", "contato");
            model.addAttribute("pageTitle", "Contato | Dr. Rafael Mendonça");
            model.addAttribute("pageDescription", "Entre em contato com nosso escritório.");
            model.addAttribute("erroValidacao", true);
            log.warn("Formulário de contato enviado com erros de validação");
            return "contato";
        }

        try {
            // Salva a mensagem via service
            contatoService.salvarMensagem(mensagem);

            // Flash attribute: persiste após o redirect
            redirectAttributes.addFlashAttribute("sucesso",
                "Mensagem enviada com sucesso! Retornaremos em até 24 horas úteis.");
            log.info("Contato salvo com sucesso para: {}", mensagem.getEmail());

        } catch (Exception e) {
            log.error("Erro ao salvar mensagem de contato: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("erro",
                "Ocorreu um erro ao enviar sua mensagem. Por favor, tente novamente.");
        }

        // Padrão PRG (Post-Redirect-Get) para evitar resubmissão do formulário
        return "redirect:/contato";
    }
}
