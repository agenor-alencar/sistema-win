package com.win.win_market.service;

import com.win.win_market.dto.request.NotificacaoRequestDTO;
import com.win.win_market.dto.response.NotificacaoResponseDTO;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.model.Notificacao;
import com.win.win_market.model.Usuario;
import com.win.win_market.repository.NotificacaoRepository;
import com.win.win_market.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public NotificacaoResponseDTO criarNotificacao(NotificacaoRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setTitulo(requestDTO.titulo());
        notificacao.setMensagem(requestDTO.mensagem());
        notificacao.setTipo(requestDTO.tipo());
        notificacao.setLida(false);

        Notificacao notificacaoSalva = notificacaoRepository.save(notificacao);
        return mapToResponseDTO(notificacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listarNotificacoesPorUsuario(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return notificacaoRepository.findByUsuarioOrderByDataCriacaoDesc(usuario)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listarNotificacoesNaoLidas(UUID usuarioId) {
        return notificacaoRepository.findByUsuarioIdAndLidaFalse(usuarioId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Long contarNotificacoesNaoLidas(UUID usuarioId) {
        return notificacaoRepository.countNotificacoesNaoLidas(usuarioId);
    }

    @Transactional
    public NotificacaoResponseDTO marcarComoLida(UUID id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));

        notificacao.setLida(true);
        Notificacao notificacaoAtualizada = notificacaoRepository.save(notificacao);
        return mapToResponseDTO(notificacaoAtualizada);
    }

    @Transactional
    public void marcarTodasComoLidas(UUID usuarioId) {
        List<Notificacao> notificacoes = notificacaoRepository.findByUsuarioIdAndLidaFalse(usuarioId);
        notificacoes.forEach(notificacao -> notificacao.setLida(true));
        notificacaoRepository.saveAll(notificacoes);
    }

    @Transactional
    public void deletarNotificacao(UUID id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));

        notificacaoRepository.delete(notificacao);
    }

    @Transactional
    public void enviarNotificacaoParaTodos(String titulo, String mensagem, String tipo) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<Notificacao> notificacoes = usuarios.stream()
                .map(usuario -> {
                    Notificacao notificacao = new Notificacao();
                    notificacao.setUsuario(usuario);
                    notificacao.setTitulo(titulo);
                    notificacao.setMensagem(mensagem);
                    notificacao.setTipo(tipo);
                    notificacao.setLida(false);
                    return notificacao;
                })
                .collect(Collectors.toList());

        notificacaoRepository.saveAll(notificacoes);
    }

    private NotificacaoResponseDTO mapToResponseDTO(Notificacao notificacao) {
        return new NotificacaoResponseDTO(
            notificacao.getId(),
            notificacao.getUsuario().getId(),
            notificacao.getTitulo(),
            notificacao.getMensagem(),
            notificacao.getTipo(),
            notificacao.isLida(),
            notificacao.getDataCriacao()
        );
    }
}
