package com.win.win_market.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nome_fantasia", length = 100, nullable = false)
    private String nomeFantasia;

    @Column(name = "razao_social", length = 100)
    private String razaoSocial;

    @Column(length = 18, unique = true)
    private String cnpj;

    @Column(name = "data_abertura")
    private LocalDate dataAbertura;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "horario_funcionamento", columnDefinition = "jsonb")
    private Map<String, Object> horarioFuncionamento;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos;
}

