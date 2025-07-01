package fintrack.service;

import fintrack.model.Usuario;
import fintrack.util.ValidadorUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviço para operações relacionadas ao usuário
 * Gerencia todas as operações de negócio do sistema
 * 
 * @author Arthur Oliveira Silva
 * @version 1.0
 */
public class UsuarioService {
    private Usuario usuarioAtual;
    private List<String> historicoTransacoes;
    
    /**
     * Construtor da classe UsuarioService
     */
    public UsuarioService() {
        this.historicoTransacoes = new ArrayList<>();
    }
    
    /**
     * Realiza login ou cria novo usuário no sistema
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @return true se login bem-sucedido, false caso contrário
     */
    public boolean loginOuCriarUsuario(String nome, String email) {
        // Formatar dados de entrada
        String nomeFormatado = ValidadorUtil.formatarNome(nome);
        String emailFormatado = ValidadorUtil.formatarEmail(email);
        
        // Validar dados
        if (!ValidadorUtil.validarNome(nomeFormatado)) {
            System.out.println("❌ Nome inválido!");
            System.out.println("   • Deve ter entre 2 e 50 caracteres");
            System.out.println("   • Apenas letras e espaços são permitidos");
            return false;
        }
        
        if (!ValidadorUtil.validarEmail(emailFormatado)) {
            System.out.println("❌ Email inválido!");
            System.out.println("   • Formato deve ser: exemplo@email.com");
            return false;
        }
        
        // Criar usuário
        this.usuarioAtual = new Usuario(nomeFormatado, emailFormatado);
        this.historicoTransacoes.clear();
        
        System.out.println("✅ Login realizado com sucesso!");
        System.out.println("👤 Usuário: " + nomeFormatado);
        System.out.println("📧 Email: " + emailFormatado);
        System.out.println("💰 Saldo inicial: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
        
        adicionarTransacao("Login realizado - Saldo inicial: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
        
        return true;
    }
    
    /**
     * Exibe o saldo atual do usuário
     */
    public void exibirSaldo() {
        if (!verificarUsuarioLogado()) return;
        
        System.out.println("\n" + "=".repeat(35));
        System.out.println("💰 CONSULTA DE SALDO");
        System.out.println("=".repeat(35));
        System.out.println("👤 Usuário: " + usuarioAtual.getNome());
        System.out.println("💰 Saldo atual: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
        System.out.println("🕐 Última atualização: " + usuarioAtual.getUltimoAcessoFormatado());
        
        adicionarTransacao("Consulta de saldo realizada");
    }
    
    /**
     * Realiza transferência de valor
     * @param valor Valor a ser transferido
     * @param destino Destino da transferência
     * @return true se transferência bem-sucedida, false caso contrário
     */
    public boolean realizarTransferencia(double valor, String destino) {
        if (!verificarUsuarioLogado()) return false;
        
        // Validar valor
        if (!ValidadorUtil.validarValor(valor)) {
            System.out.println("❌ Valor inválido!");
            System.out.println("   • Deve ser maior que zero");
            System.out.println("   • Máximo permitido: R$ 999.999,99");
            return false;
        }
        
        // Validar destino
        if (!ValidadorUtil.validarTextoNaoVazio(destino)) {
            System.out.println("❌ Destino da transferência não pode estar vazio!");
            return false;
        }
        
        // Verificar saldo
        if (!usuarioAtual.temSaldoSuficiente(valor)) {
            System.out.println("❌ Saldo insuficiente!");
            System.out.println("💰 Saldo disponível: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
            System.out.println("💸 Valor solicitado: " + ValidadorUtil.formatarMoeda(valor));
            return false;
        }
        
        // Realizar transferência
        if (usuarioAtual.debitarSaldo(valor)) {
            System.out.println("✅ Transferência realizada com sucesso!");
            System.out.println("💸 Valor transferido: " + ValidadorUtil.formatarMoeda(valor));
            System.out.println("🎯 Destino: " + destino);
            System.out.println("💰 Novo saldo: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
            
            adicionarTransacao("Transferência: " + ValidadorUtil.formatarMoeda(valor) + " para " + destino);
            return true;
        }
        
        return false;
    }
    
    /**
     * Simula recebimento de dinheiro
     * @param valor Valor recebido
     * @param origem Origem do dinheiro
     * @return true se operação bem-sucedida, false caso contrário
     */
    public boolean receberDinheiro(double valor, String origem) {
        if (!verificarUsuarioLogado()) return false;
        
        // Validar valor
        if (!ValidadorUtil.validarValor(valor)) {
            System.out.println("❌ Valor inválido!");
            System.out.println("   • Deve ser maior que zero");
            System.out.println("   • Máximo permitido: R$ 999.999,99");
            return false;
        }
        
        // Validar origem
        if (!ValidadorUtil.validarTextoNaoVazio(origem)) {
            System.out.println("❌ Origem do dinheiro não pode estar vazia!");
            return false;
        }
        
        // Receber dinheiro
        usuarioAtual.creditarSaldo(valor);
        System.out.println("✅ Dinheiro recebido com sucesso!");
        System.out.println("💵 Valor recebido: " + ValidadorUtil.formatarMoeda(valor));
        System.out.println("📤 Origem: " + origem);
        System.out.println("💰 Novo saldo: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
        
        adicionarTransacao("Recebimento: " + ValidadorUtil.formatarMoeda(valor) + " de " + origem);
        return true;
    }
    
    /**
     * Exibe histórico de transações
     */
    public void exibirHistoricoTransacoes() {
        if (!verificarUsuarioLogado()) return;
        
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📋 HISTÓRICO DE TRANSAÇÕES");
        System.out.println("=".repeat(40));
        
        if (historicoTransacoes.isEmpty()) {
            System.out.println("📭 Nenhuma transação realizada ainda.");
        } else {
            for (int i = 0; i < historicoTransacoes.size(); i++) {
                System.out.println((i + 1) + ". " + historicoTransacoes.get(i));
            }
        }
    }
    
    /**
     * Obtém informações completas do usuário atual
     * @return Informações do usuário ou null se não logado
     */
    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }
    
    /**
     * Verifica se há usuário logado no sistema
     * @return true se há usuário logado, false caso contrário
     */
    public boolean temUsuarioLogado() {
        return usuarioAtual != null;
    }
    
    /**
     * Realiza logout do usuário atual
     */
    public void logout() {
        if (usuarioAtual != null) {
            adicionarTransacao("Logout realizado");
            System.out.println("👋 Logout realizado com sucesso!");
            System.out.println("🔒 Sessão encerrada para: " + usuarioAtual.getNome());
        }
        usuarioAtual = null;
        historicoTransacoes.clear();
    }
    
    // ===== MÉTODOS PRIVADOS =====
    
    /**
     * Verifica se há usuário logado e exibe mensagem se não houver
     * @return true se há usuário logado, false caso contrário
     */
    private boolean verificarUsuarioLogado() {
        if (usuarioAtual == null) {
            System.out.println("❌ Nenhum usuário logado no sistema!");
            return false;
        }
        return true;
    }
    
    /**
     * Adiciona uma transação ao histórico
     * @param transacao Descrição da transação
     */
    private void adicionarTransacao(String transacao) {
        String transacaoCompleta = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) 
            + " - " + transacao;
        historicoTransacoes.add(transacaoCompleta);
        
        // Manter apenas as últimas 50 transações
        if (historicoTransacoes.size() > 50) {
            historicoTransacoes.remove(0);
        }
    }
}
