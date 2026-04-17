/**
 * main.js — Advocacia Dr. Luiz Henrique
 * Desenvolvido por Pedro Garcia
 * Última atualização: 2026-04-17
 * Scripts de comportamento: navbar, scroll reveal, formulário, etc.
 */

document.addEventListener('DOMContentLoaded', () => {

    // ============================================================
    // 1. NAVBAR: Efeito ao rolar a página + menu mobile
    // ============================================================
    const navbar = document.getElementById('navbar');
    const navbarToggle = document.getElementById('navbarToggle');
    const navbarMenu = document.getElementById('navbarMenu');

    // Adiciona classe 'scrolled' quando o usuário rola > 50px
    const handleNavbarScroll = () => {
        if (window.scrollY > 50) {
            navbar?.classList.add('scrolled');
        } else {
            navbar?.classList.remove('scrolled');
        }
    };

    window.addEventListener('scroll', handleNavbarScroll, { passive: true });
    handleNavbarScroll(); // Executa na carga inicial

    // Toggle do menu mobile (hamburguer)
    navbarToggle?.addEventListener('click', () => {
        const aberto = navbarMenu?.classList.toggle('aberto');
        navbarToggle.classList.toggle('ativo');
        navbarToggle.setAttribute('aria-expanded', aberto ? 'true' : 'false');
        // Impede scroll do body quando menu está aberto
        document.body.style.overflow = aberto ? 'hidden' : '';
    });

    // Fecha o menu ao clicar em um link
    navbarMenu?.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', () => {
            navbarMenu.classList.remove('aberto');
            navbarToggle?.classList.remove('ativo');
            navbarToggle?.setAttribute('aria-expanded', 'false');
            document.body.style.overflow = '';
        });
    });

    // Fecha o menu ao clicar fora dele
    document.addEventListener('click', (e) => {
        if (navbarMenu?.classList.contains('aberto') &&
            !navbar?.contains(e.target)) {
            navbarMenu.classList.remove('aberto');
            navbarToggle?.classList.remove('ativo');
            document.body.style.overflow = '';
        }
    });

    // ============================================================
    // 2. SCROLL REVEAL: Animação de entrada dos elementos
    // ============================================================
    const revelarElementos = () => {
        const elementos = document.querySelectorAll('.reveal');
        const alturaJanela = window.innerHeight;

        elementos.forEach((el, index) => {
            const rect = el.getBoundingClientRect();
            const visivel = rect.top < alturaJanela * 0.88;

            if (visivel) {
                // Atraso escalonado para elementos no mesmo grupo
                setTimeout(() => {
                    el.classList.add('visivel');
                }, index % 4 * 120); // Máx 4 por vez, com 120ms de intervalo
            }
        });
    };

    // Usa IntersectionObserver se disponível (mais performático)
    if ('IntersectionObserver' in window) {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry, i) => {
                if (entry.isIntersecting) {
                    setTimeout(() => {
                        entry.target.classList.add('visivel');
                    }, (i % 4) * 120);
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.12 });

        document.querySelectorAll('.reveal').forEach(el => observer.observe(el));
    } else {
        // Fallback para navegadores sem IntersectionObserver
        window.addEventListener('scroll', revelarElementos, { passive: true });
        revelarElementos();
    }

    // ============================================================
    // 3. FORMULÁRIO DE CONTATO: UX melhorada
    // ============================================================
    const form = document.getElementById('contatoForm');
    const btnEnviar = document.getElementById('btnEnviar');

    if (form && btnEnviar) {
        const btnTexto = btnEnviar.querySelector('.btn-texto');
        const btnLoading = btnEnviar.querySelector('.btn-loading');

        form.addEventListener('submit', () => {
            // Mostra estado de carregamento ao enviar
            if (btnTexto) btnTexto.style.display = 'none';
            if (btnLoading) btnLoading.style.display = 'inline';
            btnEnviar.disabled = true;
        });

        // Máscara de telefone simples
        const inputTelefone = document.getElementById('telefone');
        if (inputTelefone) {
            inputTelefone.addEventListener('input', (e) => {
                let v = e.target.value.replace(/\D/g, '');
                if (v.length <= 10) {
                    v = v.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, '($1) $2-$3');
                } else {
                    v = v.replace(/^(\d{2})(\d{5})(\d{0,4}).*/, '($1) $2-$3');
                }
                e.target.value = v;
            });
        }
    }

    // ============================================================
    // 4. SCROLL SUAVE para âncoras internas
    // ============================================================
    document.querySelectorAll('a[href^="#"]').forEach(link => {
        link.addEventListener('click', (e) => {
            const targetId = link.getAttribute('href').slice(1);
            const target = document.getElementById(targetId);
            if (target) {
                e.preventDefault();
                const offset = parseInt(getComputedStyle(document.documentElement)
                    .getPropertyValue('--navbar-height') || '80');
                const top = target.getBoundingClientRect().top + window.scrollY - offset;
                window.scrollTo({ top, behavior: 'smooth' });
            }
        });
    });

    // ============================================================
    // 5. AUTO-DISMISS de alertas de sucesso/erro
    // ============================================================
    const alertas = document.querySelectorAll('.alerta-sucesso, .alerta-erro');
    alertas.forEach(alerta => {
        setTimeout(() => {
            alerta.style.transition = 'opacity 0.5s ease';
            alerta.style.opacity = '0';
            setTimeout(() => alerta.remove(), 500);
        }, 6000);
    });

    console.log('✅ Advocacia Luiz Henrique — scripts carregados');
});
