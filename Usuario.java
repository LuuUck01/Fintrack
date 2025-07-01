package fintrack.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe modelo para representar um usuário do sistema FinTrack
 * Contém informações pessoais e operações básicas de conta
 * 
 * @author Arthur Oliveira Silva
 * @version 1.0
 */
public class Usuario {
    private String nome;
    private String email;
    private double saldo;
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimoAcesso;
    
    /**
     * Construtor da classe Usuario
     * @param nome Nome do usuário
     * @param email Email do usuário
     */
    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.saldo = 1250.00; // Saldo inicial padrão
        this.dataCadastro = LocalDateTime.now();
        this.ultimoAcesso = LocalDateTime.now();
    }
    
    // ===== GETTERS E SETTERS =====
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }
    
    public void atualizarUltimoAcesso() {
        this.ultimoAcesso = LocalDateTime.now();
    }
    
    // ===== MÉTODOS DE NEGÓCIO =====
    
    /**
     * Verifica se o usuário tem saldo suficiente para uma operação
     * @param valor Valor a ser verificado
     * @return true se tem saldo suficiente, false caso contrário
     */
    public boolean temSaldoSuficiente(double valor) {
        return this.saldo >= valor && valor > 0;
    }
    
    /**
     * Debita um valor do saldo do usuário
     * @param valor Valor a ser debitado
     * @return true se a operação foi bem-sucedida
     */
    public boolean debitarSaldo(double valor) {
        if (temSaldoSuficiente(valor)) {
            this.saldo -= valor;
            atualizarUltimoAcesso();
            return true;
        }
        return false;
    }
    
    /**
     * Credita um valor ao saldo do usuário
     * @param valor Valor a ser creditado
     */
    public void creditarSaldo(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            atualizarUltimoAcesso();
        }
    }
    
    /**
     * Formata a data de cadastro para exibição
     * @return Data formatada
     */
    public String getDataCadastroFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataCadastro.format(formatter);
    }
    
    /**
     * Formata a data do último acesso para exibição
     * @return Data formatada
     */
    public String getUltimoAcessoFormatado() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return ultimoAcesso.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("👤 Usuário: %s\n📧 Email: %s\n💰 Saldo: R$ %.2f\n📅 Cadastro: %s\n🕐 Último acesso: %s", 
                           nome, email, saldo, getDataCadastroFormatada(), getUltimoAcessoFormatado());
    }
}
