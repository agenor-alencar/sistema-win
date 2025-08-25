# WIN Market Backend - Documentação Técnica

**Versão:** 2.0.0  
**Data:** Dezembro 2024  
**Autor:** Equipe WIN Market  
**Tecnologia:** Spring Boot 3.4.5 + Java 17

---

## 📑 Índice

1. [Visão Geral](#-visão-geral)
2. [Arquitetura](#-arquitetura)
3. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
4. [Estrutura do Projeto](#-estrutura-do-projeto)
5. [Configurações](#-configurações)
6. [Segurança](#-segurança)
7. [Banco de Dados](#-banco-de-dados)
8. [APIs e Endpoints](#-apis-e-endpoints)
9. [DTOs e Mappers](#-dtos-e-mappers)
10. [Sistema de Exceções](#-sistema-de-exceções)
11. [Validações](#-validações)
12. [Docker e Deployment](#-docker-e-deployment)
13. [Testes](#-testes)
14. [Monitoramento](#-monitoramento)

---

## 🎯 Visão Geral

O **WIN Market Backend** é uma API REST robusta desenvolvida para um marketplace completo, oferecendo funcionalidades de:

- **E-commerce Completo**: Produtos, categorias, carrinho de compras
- **Sistema de Usuários**: Clientes, vendedores, administradores
- **Gestão de Pedidos**: Desde criação até entrega
- **Sistema de Pagamentos**: Integração com múltiplos provedores
- **Avaliações e Reviews**: Sistema completo de feedback
- **Notificações**: Sistema de comunicação em tempo real

### Características Principais

- ✅ **Alta Segurança**: Proteção contra XSS, SQL Injection, CSRF
- ✅ **Escalabilidade**: Arquitetura modular e cache inteligente
- ✅ **Performance**: Otimizada para alta concorrência
- ✅ **Observabilidade**: Logs estruturados e métricas
- ✅ **Docker Ready**: Containerização completa

---

## 🏗️ Arquitetura

### Arquitetura em Camadas

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│  Controllers + Exception Handlers       │
├─────────────────────────────────────────┤
│             Service Layer               │
│     Business Logic + Validations       │
├─────────────────────────────────────────┤
│           Repository Layer              │
│    Data Access + JPA Repositories      │
├─────────────────────────────────────────┤
│            Database Layer               │
│         PostgreSQL + Flyway            │
└─────────────────────────────────────────┘
```

### Padrões Arquiteturais

- **Repository Pattern**: Abstração da camada de dados
- **DTO Pattern**: Transferência de dados otimizada
- **Mapper Pattern**: Conversão entre entidades e DTOs (MapStruct)
- **Exception Handler Pattern**: Tratamento centralizado de erros
- **Strategy Pattern**: Múltiplos provedores de pagamento

---

## 🛠️ Tecnologias Utilizadas

### Core Framework
- **Spring Boot**: 3.4.5
- **Java**: 17
- **Maven**: 3.9+

### Dependências Principais
```xml
<!-- Spring Boot Starters -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Database -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>

<!-- Security -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

<!-- Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>

<!-- Mapping -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>

<!-- Utils -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

---

## 📂 Estrutura do Projeto

### Estrutura Atual

```
src/main/java/com/win/win_market/
├── WinMarketApplication.java           # Classe principal
├── config/                             # Configurações
│   ├── CorsConfig.java                # CORS configuration
│   ├── RateLimitFilter.java           # Rate limiting
│   ├── SecurityConfig.java            # Security configuration
│   ├── SwaggerConfig.java             # API documentation
│   └── XSSFilter.java                 # XSS protection
├── controller/                         # REST Controllers
│   ├── AuthController.java
│   ├── LojaController.java
│   ├── ProdutoController.java
│   └── UsuarioController.java
├── dto/                               # Data Transfer Objects
│   ├── mapper/                        # MapStruct mappers
│   ├── request/                       # Request DTOs
│   └── response/                      # Response DTOs
├── exceptions/                        # Exception handling
│   ├── BaseException.java             # Base exception class
│   ├── GlobalExceptionHandler.java    # Global error handler
│   └── [Specific exceptions...]
├── model/                             # JPA Entities
│   ├── Usuario.java
│   ├── Produto.java
│   ├── Pedido.java
│   └── [Other entities...]
├── repository/                        # Data repositories
│   ├── UsuarioRepository.java
│   ├── ProdutoRepository.java
│   └── [Other repositories...]
├── security/                          # Security components
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── Jwt.java
│   └── JwtRequestFilter.java
├── service/                           # Business logic
│   ├── AuthService.java
│   ├── UsuarioService.java
│   └── [Other services...]
├── util/                              # Utilities
│   └── SecurityValidator.java
└── validation/                        # Custom validations
    ├── NoXSS.java
    ├── NoSQLInjection.java
    └── StrongPassword.java
```

### Estrutura Recomendada (Modular)

```
src/main/java/com/win/win_market/
├── config/                            # Configurações globais
├── shared/                            # Componentes compartilhados
│   ├── exceptions/
│   ├── validation/
│   ├── util/
│   └── constants/
└── modules/                           # Módulos de negócio
    ├── auth/                          # Autenticação
    │   ├── controller/
    │   ├── service/
    │   ├── dto/
    │   └── security/
    ├── user/                          # Usuários
    ├── product/                       # Produtos
    ├── order/                         # Pedidos
    ├── payment/                       # Pagamentos
    └── delivery/                      # Entregas
```

---

## ⚙️ Configurações

### Profiles de Ambiente

#### application.yml (Base)
```yaml
spring:
  application:
    name: win-market
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}
  
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/winmarket}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:password}
    driver-class-name: org.postgresql.Driver

app:
  security:
    jwt-secret: ${JWT_SECRET:mySecretKey}
    jwt-expiration-ms: ${JWT_EXPIRATION_MS:86400000}
  storage:
    type: ${STORAGE_TYPE:local}
    base-path: ${STORAGE_BASE_PATH:./uploads}
```

#### application-dev.yml (Desenvolvimento)
```yaml
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update

logging:
  level:
    com.win.win_market: DEBUG
```

#### application-prod.yml (Produção)
```yaml
spring:
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate

logging:
  level:
    com.win.win_market: INFO
  file:
    name: /var/log/win-market/application.log
```

### Configurações Type-Safe

```java
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    private Database database = new Database();
    private Security security = new Security();
    private Storage storage = new Storage();
    // ... nested classes
}
```

---

## 🔐 Segurança

### Configuração de Segurança

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self'")
                )
                .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block"))
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }
}
```

### Proteções Implementadas

#### 1. **Filtro XSS**
```java
@Component
public class XSSFilter implements Filter {
    // Remove/sanitiza conteúdo malicioso das requisições
    private String stripXSS(String value) {
        // Implementação de sanitização
    }
}
```

#### 2. **Rate Limiting**
```java
@Component
public class RateLimitFilter implements Filter {
    // Limite: 100 requisições por minuto por IP
    private static final long MAX_REQUESTS_PER_MINUTE = 100;
}
```

#### 3. **Validações Customizadas**
```java
@NoXSS
@NoSQLInjection
@StrongPassword
public record RegisterRequestDTO(
    String nome,
    String email,
    String senha
) {}
```

### Headers de Segurança

- **HSTS**: Force HTTPS
- **CSP**: Content Security Policy restritiva
- **X-XSS-Protection**: Proteção XSS do browser
- **X-Content-Type-Options**: Prevent MIME sniffing
- **Permissions-Policy**: Controle de APIs do browser

---

## 🗄️ Banco de Dados

### Entidades Principais

#### Usuario
```java
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 100, unique = true, nullable = false)
    private String email;
    
    @Column(length = 255, nullable = false)
    private String senha;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Endereco> enderecos;
    
    @CreationTimestamp
    private OffsetDateTime dataCriacao;
}
```

#### Produto
```java
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    
    @Column(length = 255, nullable = false)
    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal preco;
    
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque;
    
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ImagemProduto> imagens;
}
```

### Migrations (Flyway)

#### V1__criar_tabela_usuario.sql
```sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    ultimo_acesso TIMESTAMP WITH TIME ZONE
);
```

### Repositórios Seguros

```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContaining(@Param("nome") String nome);
    
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
```

---

## 🌐 APIs e Endpoints

### Estrutura de Controllers

#### AuthController
```java
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDTO request) {
        // Implementação de login
    }
    
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        // Implementação de registro
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        // Refresh do token JWT
    }
}
```

#### ProdutoController
```java
@RestController
@RequestMapping("/api/produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProdutoController {
    
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutos(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String busca
    ) {
        // Listagem com paginação e filtros
    }
    
    @PostMapping
    @PreAuthorize("hasRole('VENDEDOR') or hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@Valid @RequestBody ProdutoCreateRequestDTO request) {
        // Criação de produto
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("@produtoService.isOwner(#id, authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
        @PathVariable UUID id,
        @Valid @RequestBody ProdutoUpdateRequestDTO request
    ) {
        // Atualização de produto
    }
}
```

### Documentação OpenAPI/Swagger

Acesso: `http://localhost:8080/swagger-ui.html`

Configuração:
```java
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("WIN Market API")
                .version("2.0.0")
                .description("API completa para marketplace WIN Market")
            )
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
```

---

## 📊 DTOs e Mappers

### DTOs como Records

#### Request DTOs
```java
public record LoginRequestDTO(
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @NoXSS
    String email,
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, max = 255)
    String senha
) {}

public record ProdutoCreateRequestDTO(
    @NotBlank @Size(min = 2, max = 255) @NoXSS @NoSQLInjection
    String nome,
    
    @Size(max = 1000) @NoXSS @NoSQLInjection
    String descricao,
    
    @NotNull @DecimalMin("0.01") @Digits(integer = 8, fraction = 2)
    BigDecimal preco,
    
    @NotNull @Min(0) @Max(999999)
    Integer quantidadeEstoque,
    
    UUID categoriaId,
    UUID vendedorId
) {}
```

#### Response DTOs
```java
public record UsuarioResponseDTO(
    UUID id,
    String nome,
    String email,
    String telefone,
    List<EnderecoResponseDTO> enderecos,
    OffsetDateTime dataCriacao,
    OffsetDateTime ultimoAcesso
) {}

public record ProdutoResponseDTO(
    UUID id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer quantidadeEstoque,
    Boolean ativo,
    CategoriaResponseDTO categoria,
    VendedorResponseDTO vendedor,
    List<ImagemProdutoResponseDTO> imagens,
    OffsetDateTime dataCriacao,
    Double avaliacaoMedia,
    Integer totalAvaliacoes
) {}
```

### Mappers MapStruct

```java
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "ultimoAcesso", ignore = true)
    Usuario toEntity(RegisterRequestDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(RegisterRequestDTO dto, @MappingTarget Usuario usuario);
}
```

---

## 🚨 Sistema de Exceções

### Hierarquia de Exceções

```java
// Base Exception
public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final int httpStatus;
}

// Exceções Específicas
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
            String.format("%s não encontrado com %s: %s", resourceName, fieldName, fieldValue),
            "RESOURCE_NOT_FOUND",
            HttpStatus.NOT_FOUND.value()
        );
    }
}

public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message, "BUSINESS_RULE_VIOLATION", HttpStatus.BAD_REQUEST.value());
    }
}

public class InsufficientStockException extends BusinessException {
    public InsufficientStockException(String productName, int requested, int available) {
        super(String.format("Estoque insuficiente para o produto '%s'. Solicitado: %d, Disponível: %d", 
              productName, requested, available), "INSUFFICIENT_STOCK");
    }
}
```

### Handler Global

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.of(
            ex.getHttpStatus(),
            HttpStatus.valueOf(ex.getHttpStatus()).getReasonPhrase(),
            ex.getMessage(),
            ex.getErrorCode(),
            request.getRequestURI()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ErrorResponse.FieldError(
                error.getField(),
                error.getRejectedValue(),
                error.getDefaultMessage()
            ))
            .collect(Collectors.toList());
        
        ErrorResponse error = ErrorResponse.of(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed",
            "Erro de validação nos campos enviados",
            "VALIDATION_ERROR",
            request.getRequestURI(),
            fieldErrors
        );
        
        return ResponseEntity.badRequest().body(error);
    }
}
```

### Response Padronizada

```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    OffsetDateTime timestamp,
    int status,
    String error,
    String message,
    String errorCode,
    String path,
    List<FieldError> fieldErrors
) {
    public record FieldError(
        String field,
        Object rejectedValue,
        String message
    ) {}
}
```

---

## ✅ Validações

### Validações Customizadas

#### @NoXSS
```java
@Documented
@Constraint(validatedBy = NoXSSValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoXSS {
    String message() default "Entrada contém conteúdo potencialmente perigoso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class NoXSSValidator implements ConstraintValidator<NoXSS, String> {
    @Autowired
    private SecurityValidator securityValidator;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !(securityValidator.containsXSS(value) || securityValidator.containsSQLInjection(value));
    }
}
```

#### @StrongPassword
```java
@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "A senha deve ter pelo menos 8 caracteres, incluindo maiúscula, minúscula, número e caractere especial";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

### SecurityValidator

```java
@Component
public class SecurityValidator {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
        "(?i)(union|select|insert|update|delete|drop|create|alter|exec|execute|script)",
        Pattern.CASE_INSENSITIVE
    );

    public boolean containsXSS(String input) {
        if (input == null) return false;
        String lowerInput = input.toLowerCase();
        String[] xssPatterns = {
            "<script", "</script>", "javascript:", "vbscript:", 
            "onload=", "onerror=", "onclick=", "document.cookie"
        };
        return Arrays.stream(xssPatterns)
            .anyMatch(pattern -> lowerInput.contains(pattern.toLowerCase()));
    }
    
    public boolean containsSQLInjection(String input) {
        if (input == null) return false;
        return SQL_INJECTION_PATTERN.matcher(input).find();
    }
    
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0);
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
```

---

## 🐳 Docker e Deployment

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/win-market-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: ${DB_NAME}
      POSTGRES_USER: ${DB_USERNAME}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "${DB_PORT}:5432"

  redis:
    image: redis:7-alpine
    ports:
      - "${REDIS_PORT}:6379"

  backend:
    build: ./win-backend
    environment:
      SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE}
      DB_URL: jdbc:postgresql://postgres:5432/${DB_NAME}
      DB_USERNAME: ${DB_USERNAME}
      DB_PASSWORD: ${DB_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
    depends_on:
      - postgres
      - redis
    ports:
      - "8080:8080"
    volumes:
      - ./uploads:/app/uploads

volumes:
  postgres_data:
```

### Variáveis de Ambiente (.env)

```env
# AMBIENTE
SPRING_PROFILES_ACTIVE=dev
NODE_ENV=development

# DATABASE
DB_HOST=postgres
DB_PORT=5432
DB_NAME=winmarket
DB_USERNAME=postgres
DB_PASSWORD=postgres123
DB_URL=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}

# SECURITY & JWT
JWT_SECRET=myVerySecretJWTKeyThatShouldBeVeryLongAndSecure123456789
JWT_EXPIRATION_MS=86400000
REFRESH_TOKEN_EXPIRATION_MS=604800000

# STORAGE
STORAGE_TYPE=local
STORAGE_BASE_PATH=./uploads
STORAGE_MAX_FILE_SIZE=10485760

# EMAIL
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=
EMAIL_PASSWORD=
EMAIL_ENABLED=false

# PAYMENT
PAYMENT_PROVIDER=stripe
PAYMENT_API_KEY=
PAYMENT_WEBHOOK_SECRET=
PAYMENT_SANDBOX_MODE=true

# REDIS
REDIS_HOST=redis
REDIS_PORT=6379

# FRONTEND
FRONTEND_URL=http://localhost:5173
CORS_ALLOWED_ORIGINS=http://localhost:5173,http://localhost:3000
```

---

## 🧪 Testes

### Estrutura de Testes

```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class WinMarketApplicationTests {

    @Test
    void contextLoads() {
        // Teste de carregamento do contexto
    }
}

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveRetornarUsuarioPorEmail() {
        // Teste unitário do service
    }
}

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void deveRetornar200AoBuscarUsuarios() throws Exception {
        // Teste de integração do controller
    }
}
```

---

## 📊 Monitoramento

### Actuator Endpoints

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: when-authorized
```

### Métricas Disponíveis

- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`
- **Prometheus**: `/actuator/prometheus`

### Logs Estruturados

```yaml
logging:
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

---

## 🔄 Roadmap

### Versão 2.1 (Próxima)
- [ ] Implementação de Cache Redis
- [ ] Websockets para notificações em tempo real
- [ ] Sistema de cupons de desconto
- [ ] Integração com provedores de pagamento

### Versão 2.2 (Futuro)
- [ ] Microserviços com Spring Cloud
- [ ] Event Sourcing com Apache Kafka
- [ ] Métricas avançadas com Prometheus/Grafana
- [ ] CI/CD completo com GitHub Actions

---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://spring.io/projects/spring-security)
- [MapStruct Documentation](https://mapstruct.org/)
- [OpenAPI Specification](https://swagger.io/specification/)

---

## 👥 Equipe

**Desenvolvedor Principal**: Arthur  
**Arquitetura**: Clean Architecture + Domain Driven Design  
**Metodologia**: Agile/Scrum  

---

*Documentação atualizada em: Dezembro 2024*  
*Versão do Sistema: 2.0.0*
