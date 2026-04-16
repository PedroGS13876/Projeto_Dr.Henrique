# ⚖️ Advocacia Imobiliária — Dr. Rafael Mendonça
### Site profissional em Spring Boot + Thymeleaf

---

## 📋 Visão Geral

Site institucional completo para escritório de advocacia especializado em **Direito Imobiliário**, desenvolvido com:

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.2.3 | Framework web |
| Thymeleaf | 3.x | Motor de templates HTML |
| Spring Data JPA | 3.x | Persistência de dados |
| H2 Database | 2.x | Banco em memória (dev) |
| Bean Validation | 3.x | Validação de formulários |
| Lombok | 1.x | Redução de boilerplate |

---

## 📁 Estrutura do Projeto

```
advocacia-imobiliaria/
├── pom.xml                                  ← Dependências Maven
├── README.md
└── src/
    ├── main/
    │   ├── java/com/advocacia/
    │   │   ├── AdvocaciaApplication.java    ← Ponto de entrada
    │   │   ├── controller/
    │   │   │   └── SiteController.java      ← Rotas MVC
    │   │   ├── service/
    │   │   │   └── ContatoService.java      ← Lógica de negócio
    │   │   ├── repository/
    │   │   │   └── ContatoRepository.java   ← Acesso ao banco
    │   │   ├── model/
    │   │   │   └── ContatoMensagem.java     ← Entidade JPA
    │   │   └── config/
    │   │       └── WebConfig.java           ← Config MVC
    │   └── resources/
    │       ├── application.properties       ← Configurações
    │       ├── static/
    │       │   ├── css/style.css            ← Estilos completos
    │       │   ├── js/main.js               ← Scripts JS
    │       │   └── images/                  ← Imagens (adicionar)
    │       └── templates/
    │           ├── home.html                ← Página inicial
    │           ├── sobre.html               ← Sobre o advogado
    │           ├── areas.html               ← Áreas de atuação
    │           ├── contato.html             ← Formulário de contato
    │           └── fragments/
    │               ├── head.html            ← <head> reutilizável
    │               ├── navbar.html          ← Navbar fixa
    │               └── footer.html          ← Footer + WhatsApp
    └── test/
        └── java/com/advocacia/
            └── AdvocaciaApplicationTests.java
```

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

Certifique-se de ter instalado:
- **Java 17+** → `java -version`
- **Maven 3.8+** → `mvn -version`  
  *(ou use a wrapper `./mvnw` incluída automaticamente pelo Spring Initializr)*

---

### Passo 1 — Clonar / Baixar o projeto

```bash
# Se usar Git:
git clone https://github.com/seu-usuario/advocacia-imobiliaria.git
cd advocacia-imobiliaria

# Ou simplesmente descompacte o .zip e entre na pasta
cd advocacia-imobiliaria
```

---

### Passo 2 — Compilar e rodar

```bash
# Com Maven instalado:
mvn spring-boot:run

# OU usando a Maven Wrapper (não precisa Maven instalado):
./mvnw spring-boot:run        # Linux / Mac
mvnw.cmd spring-boot:run      # Windows
```

---

### Passo 3 — Acessar no navegador

```
http://localhost:8080          ← Site principal
http://localhost:8080/sobre    ← Sobre o advogado
http://localhost:8080/areas-de-atuacao ← Áreas de atuação
http://localhost:8080/contato  ← Formulário de contato
http://localhost:8080/h2-console ← Painel do banco H2 (dev)
```

---

### Passo 4 — Gerar JAR para produção

```bash
# Gera o arquivo JAR executável
mvn clean package -DskipTests

# Roda o JAR gerado
java -jar target/advocacia-imobiliaria-1.0.0.jar
```

---

## ⚙️ Personalização

### 1. Dados do escritório

Edite `src/main/resources/application.properties`:

```properties
advocacia.nome=Dr. Rafael Mendonça
advocacia.oab=OAB/SP 123.456
advocacia.telefone=(11) 99999-9999
advocacia.whatsapp=5511999999999     ← Formato: 55 + DDD + número (sem espaços)
advocacia.email=contato@escritorio.com.br
advocacia.endereco=Av. Paulista, 1000 - Sala 201, São Paulo - SP
```

### 2. Foto do advogado

Substitua o placeholder visual na página `sobre.html`:

```html
<!-- Remova o div placeholder e adicione: -->
<img src="/static/images/foto-advogado.jpg" alt="Dr. Rafael Mendonça">
```

Coloque a foto em: `src/main/resources/static/images/foto-advogado.jpg`

### 3. Cores e identidade visual

Edite as variáveis no topo de `src/main/resources/static/css/style.css`:

```css
:root {
    --azul-profundo: #0a1628;   /* Fundo principal */
    --ouro:          #c9a84c;   /* Cor de destaque */
    --branco:        #ffffff;   /* Fundo claro */
    /* ... */
}
```

---

## 📧 Ativar Envio de E-mail Real

Por padrão, o envio de e-mail é **simulado** (apenas um log no console).

Para ativar com Gmail:

**1.** Gere uma "Senha de App" no Google:
→ [myaccount.google.com](https://myaccount.google.com) → Segurança → Verificação em 2 etapas → Senhas de App

**2.** Descomente e configure no `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=xxxx-xxxx-xxxx-xxxx   ← Senha de App (16 chars)
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**3.** Em `ContatoService.java`, injete e use `JavaMailSender`:

```java
@Autowired
private JavaMailSender mailSender;

// No método salvarMensagem(), adicione:
SimpleMailMessage mail = new SimpleMailMessage();
mail.setTo(emailAdvogado);
mail.setSubject("Novo contato: " + mensagem.getAssunto());
mail.setText("De: " + mensagem.getNome() + "\n" + mensagem.getMensagem());
mailSender.send(mail);
```

---

## 🗄️ Banco de Dados

### Desenvolvimento (padrão): H2 em memória
- Console web: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:advocaciadb`
- Usuário: `sa` | Senha: *(vazia)*

### Produção: PostgreSQL

**1.** Adicione ao `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

**2.** Atualize o `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/advocacia_db
spring.datasource.username=postgres
spring.datasource.password=sua-senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=false
```

---

## 🌐 Deploy em Produção

### Opção 1 — Servidor VPS (Ubuntu)

```bash
# 1. Copie o JAR para o servidor
scp target/advocacia-imobiliaria-1.0.0.jar usuario@servidor:/opt/advocacia/

# 2. Crie um serviço systemd
sudo nano /etc/systemd/system/advocacia.service
```

```ini
[Unit]
Description=Advocacia Imobiliaria Spring Boot
After=network.target

[Service]
User=ubuntu
WorkingDirectory=/opt/advocacia
ExecStart=/usr/bin/java -jar advocacia-imobiliaria-1.0.0.jar
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
```

```bash
sudo systemctl enable advocacia
sudo systemctl start advocacia
```

### Opção 2 — Railway / Render (cloud gratuito)

1. Faça push para um repositório GitHub
2. Conecte ao [Railway](https://railway.app) ou [Render](https://render.com)
3. A plataforma detecta o `pom.xml` automaticamente e faz o deploy

---

## 🔧 Expansões Futuras

O projeto já está estruturado para receber:

- [ ] **Blog jurídico** → novo Controller + entidade `Artigo`
- [ ] **Painel administrativo** → Spring Security + área restrita
- [ ] **Agendamento online** → entidade `Consulta` + calendário
- [ ] **WhatsApp API** → integração com Twilio ou Z-API
- [ ] **Google Analytics** → adicione o script no fragment `head.html`
- [ ] **reCAPTCHA** → proteja o formulário de contato contra spam

---

## 🐛 Solução de Problemas

| Problema | Solução |
|---|---|
| `Port 8080 already in use` | Encerre o processo ou troque a porta: `server.port=8081` |
| `Java version not supported` | Certifique-se de usar Java 17+: `java -version` |
| `Could not resolve dependencies` | Execute `mvn clean install` ou verifique a conexão |
| Templates não atualizam | Salve o arquivo e recarregue (DevTools faz hot reload) |
| H2 console não abre | Confirme `spring.h2.console.enabled=true` |

---

## 📄 Licença

Projeto desenvolvido para uso comercial do escritório. Personalize livremente.

---

*Desenvolvido com Spring Boot 3 · Java 17 · Thymeleaf · Design Luxury Law*
