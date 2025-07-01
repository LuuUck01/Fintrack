// ===== ESTRUTURA DO PROJETO FINTRACK =====
// Projeto: FinTrack
// Estrutura de pastas no Eclipse:
// src/
//   ├── fintrack/
//   │   ├── Main.java
//   │   ├── controller/
//   │   │   └── AppController.java
//   │   ├── service/
//   │   │   └── UsuarioService.java
//   │   ├── model/
//   │   │   └── Usuario.java
//   │   └── util/
//   │       └── ValidadorUtil.java

// ===== ARQUIVO: src/fintrack/Main.java =====
package fintrack;

import fintrack.controller.AppController;

/**
 * Classe principal do sistema FinTrack
 * Responsável por inicializar a aplicação
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA FINTRACK ===");
        System.out.println("Iniciando aplicação...\n");
        
        AppController app = new AppController();
        app.iniciarSistema();
    }
}

// ===== ARQUIVO: src/fintrack/model/Usuario.java =====
package fintrack.model;

/**
 * Classe modelo para representar um usuário do sistema
 */
public class Usuario {
    private String nome;
    private double saldo;
    private String email;
    
    // Construtor
    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.saldo = 1250.00; // Saldo inicial padrão
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Métodos de negócio
    public boolean temSaldoSuficiente(double valor) {
        return this.saldo >= valor;
    }
    
    public void debitarSaldo(double valor) {
        if (temSaldoSuficiente(valor)) {
            this.saldo -= valor;
        }
    }
    
    public void creditarSaldo(double valor) {
        this.saldo += valor;
    }
    
    @Override
    public String toString() {
        return String.format("Usuário: %s | Email: %s | Saldo: R$ %.2f", 
                           nome, email, saldo);
    }
}

// ===== ARQUIVO: src/fintrack/util/ValidadorUtil.java =====
package fintrack.util;

/**
 * Classe utilitária para validações do sistema
 */
public class ValidadorUtil {
    
    /**
     * Valida se o nome não está vazio
     */
    public static boolean validarNome(String nome) {
        return nome != null && !nome.trim().isEmpty() && nome.length() >= 2;
    }
    
    /**
     * Valida formato básico de email
     */
    public static boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    /**
     * Valida se o valor é positivo
     */
    public static boolean validarValor(double valor) {
        return valor > 0;
    }
    
    /**
     * Formata valor monetário
     */
    public static String formatarMoeda(double valor) {
        return String.format("R$ %.2f", valor);
    }
}

// ===== ARQUIVO: src/fintrack/service/UsuarioService.java =====
package fintrack.service;

import fintrack.model.Usuario;
import fintrack.util.ValidadorUtil;

/**
 * Classe de serviço para operações relacionadas ao usuário
 */
public class UsuarioService {
    private Usuario usuarioAtual;
    
    /**
     * Realiza login ou cria novo usuário
     */
    public boolean loginOuCriarUsuario(String nome, String email) {
        // Validações
        if (!ValidadorUtil.validarNome(nome)) {
            System.out.println("❌ Nome inválido! Deve ter pelo menos 2 caracteres.");
            return false;
        }
        
        if (!ValidadorUtil.validarEmail(email)) {
            System.out.println("❌ Email inválido! Formato deve ser: exemplo@email.com");
            return false;
        }
        
        // Criar usuário (simulando criação/login)
        this.usuarioAtual = new Usuario(nome, email);
        System.out.println("✅ Usuário '" + nome + "' logado com sucesso!");
        System.out.println("📧 Email: " + email);
        System.out.println("💰 Saldo inicial: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
        return true;
    }
    
    /**
     * Exibe o saldo atual do usuário
     */
    public void exibirSaldo() {
        if (usuarioAtual == null) {
            System.out.println("❌ Nenhum usuário logado!");
            return;
        }
        
        System.out.println("\n=== CONSULTA DE SALDO ===");
        System.out.println("👤 Usuário: " + usuarioAtual.getNome());
        System.out.println("💰 Saldo atual: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
    }
    
    /**
     * Realiza transferência de valor
     */
    public boolean realizarTransferencia(double valor) {
        if (usuarioAtual == null) {
            System.out.println("❌ Nenhum usuário logado!");
            return false;
        }
        
        // Validar valor
        if (!ValidadorUtil.validarValor(valor)) {
            System.out.println("❌ Valor inválido! Deve ser maior que zero.");
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
        usuarioAtual.debitarSaldo(valor);
        System.out.println("✅ Transferência realizada com sucesso!");
        System.out.println("💸 Valor transferido: " + ValidadorUtil.formatarMoeda(valor));
        System.out.println("💰 Novo saldo: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
        return true;
    }
    
    /**
     * Simula recebimento de dinheiro
     */
    public void receberDinheiro(double valor) {
        if (usuarioAtual == null) {
            System.out.println("❌ Nenhum usuário logado!");
            return;
        }
        
        if (!ValidadorUtil.validarValor(valor)) {
            System.out.println("❌ Valor inválido! Deve ser maior que zero.");
            return;
        }
        
        usuarioAtual.creditarSaldo(valor);
        System.out.println("✅ Dinheiro recebido com sucesso!");
        System.out.println("💰 Valor recebido: " + ValidadorUtil.formatarMoeda(valor));
        System.out.println("💰 Novo saldo: " + ValidadorUtil.formatarMoeda(usuarioAtual.getSaldo()));
    }
    
    /**
     * Obtém informações do usuário atual
     */
    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }
    
    /**
     * Verifica se há usuário logado
     */
    public boolean temUsuarioLogado() {
        return usuarioAtual != null;
    }
}

// ===== ARQUIVO: src/fintrack/controller/AppController.java =====
package fintrack.controller;

import fintrack.service.UsuarioService;
import java.util.Scanner;

/**
 * Controlador principal da aplicação
 * Gerencia a interface do usuário e fluxo do sistema
 */
public class AppController {
    private final Scanner scanner;
    private final UsuarioService usuarioService;
    private boolean sistemaRodando;
    
    public AppController() {
        this.scanner = new Scanner(System.in);
        this.usuarioService = new UsuarioService();
        this.sistemaRodando = true;
    }
    
    /**
     * Inicia o sistema FinTrack
     */
    public void iniciarSistema() {
        exibirBoasVindas();
        
        // Realizar login
        if (!realizarLogin()) {
            System.out.println("❌ Falha no login. Encerrando sistema...");
            return;
        }
        
        // Menu principal
        while (sistemaRodando) {
            exibirMenuPrincipal();
            processarOpcaoMenu();
        }
        
        encerrarSistema();
    }
    
    /**
     * Exibe mensagem de boas-vindas
     */
    private void exibirBoasVindas() {
        System.out.println("💰 Bem-vindo ao FinTrack!");
        System.out.println("🏦 Seu sistema de controle financeiro pessoal");
        System.out.println("─".repeat(50));
    }
    
    /**
     * Processa o login do usuário
     */
    private boolean realizarLogin() {
        System.out.println("\n=== LOGIN ===");
        
        int tentativas = 0;
        while (tentativas < 3) {
            System.out.print("👤 Digite seu nome: ");
            String nome = scanner.nextLine().trim();
            
            System.out.print("📧 Digite seu email: ");
            String email = scanner.nextLine().trim();
            
            if (usuarioService.loginOuCriarUsuario(nome, email)) {
                return true;
            }
            
            tentativas++;
            if (tentativas < 3) {
                System.out.println("⚠️  Tente novamente (" + (3 - tentativas) + " tentativas restantes)");
            }
        }
        
        return false;
    }
    
    /**
     * Exibe o menu principal
     */
    private void exibirMenuPrincipal() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("📋 MENU PRINCIPAL");
        System.out.println("=".repeat(30));
        System.out.println("1. 💰 Consultar Saldo");
        System.out.println("2. 💸 Realizar Transferência");
        System.out.println("3. 💵 Receber Dinheiro");
        System.out.println("4. 👤 Meus Dados");
        System.out.println("5. 🚪 Sair");
        System.out.println("─".repeat(30));
        System.out.print("🔸 Escolha uma opção (1-5): ");
    }
    
    /**
     * Processa a opção escolhida no menu
     */
    private void processarOpcaoMenu() {
        String opcao = scanner.nextLine().trim();
        
        switch (opcao) {
            case "1":
                consultarSaldo();
                break;
            case "2":
                realizarTransferencia();
                break;
            case "3":
                receberDinheiro();
                break;
            case "4":
                exibirDadosUsuario();
                break;
            case "5":
                sistemaRodando = false;
                break;
            default:
                System.out.println("❌ Opção inválida! Digite um número de 1 a 5.");
        }
        
        if (sistemaRodando) {
            pausarExecucao();
        }
    }
    
    /**
     * Consulta o saldo do usuário
     */
    private void consultarSaldo() {
        usuarioService.exibirSaldo();
    }
    
    /**
     * Realiza uma transferência
     */
    private void realizarTransferencia() {
        System.out.println("\n=== TRANSFERÊNCIA ===");
        
        try {
            System.out.print("💸 Digite o valor para transferir: R$ ");
            double valor = Double.parseDouble(scanner.nextLine().replace(",", "."));
            
            System.out.print("🏦 Digite o destino da transferência: ");
            String destino = scanner.nextLine().trim();
            
            if (destino.isEmpty()) {
                System.out.println("❌ Destino não pode estar vazio!");
                return;
            }
            
            if (usuarioService.realizarTransferencia(valor)) {
                System.out.println("🎯 Destino: " + destino);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor inválido! Digite apenas números.");
        }
    }
    
    /**
     * Simula recebimento de dinheiro
     */
    private void receberDinheiro() {
        System.out.println("\n=== RECEBER DINHEIRO ===");
        
        try {
            System.out.print("💵 Digite o valor recebido: R$ ");
            double valor = Double.parseDouble(scanner.nextLine().replace(",", "."));
            
            System.out.print("👤 Digite a origem do dinheiro: ");
            String origem = scanner.nextLine().trim();
            
            if (origem.isEmpty()) {
                System.out.println("❌ Origem não pode estar vazia!");
                return;
            }
            
            usuarioService.receberDinheiro(valor);
            System.out.println("📤 Origem: " + origem);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor inválido! Digite apenas números.");
        }
    }
    
    /**
     * Exibe dados do usuário
     */
    private void exibirDadosUsuario() {
        System.out.println("\n=== MEUS DADOS ===");
        if (usuarioService.temUsuarioLogado()) {
            System.out.println(usuarioService.getUsuarioAtual().toString());
        } else {
            System.out.println("❌ Nenhum usuário logado!");
        }
    }
    
    /**
     * Pausa a execução aguardando input do usuário
     */
    private void pausarExecucao() {
        System.out.println("\n⏸️  Pressione ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Encerra o sistema
     */
    private void encerrarSistema() {
        System.out.println("\n🚪 Encerrando o FinTrack...");
        System.out.println("💰 Obrigado por usar nosso sistema!");
        System.out.println("👋 Até logo!");
        scanner.close();
    }
}

// ===== INSTRUÇÕES PARA EXECUTAR NO ECLIPSE =====
/*
COMO CONFIGURAR O PROJETO NO ECLIPSE:

1. Criar novo projeto Java:
   - File → New → Java Project
   - Nome: FinTrack
   - Use default location
   - Finish

2. Criar estrutura de packages:
   - Clique com botão direito na pasta 'src'
   - New → Package
   - Criar os packages: fintrack, fintrack.controller, fintrack.service, fintrack.model, fintrack.util

3. Criar as classes:
   - Para cada package, criar as respectivas classes
   - Copiar o código de cada classe para o arquivo correspondente

4. Executar:
   - Clique com botão direito em Main.java
   - Run As → Java Application

FUNCIONALIDADES IMPLEMENTADAS:
✅ Login com validação de nome e email
✅ Consulta de saldo
✅ Transferência de dinheiro com validações
✅ Recebimento de dinheiro
✅ Visualização de dados do usuário
✅ Interface amigável com emojis
✅ Tratamento de erros
✅ Validações de entrada
✅ Estrutura de projeto organizada
✅ Documentação completa
*/