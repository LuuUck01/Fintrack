package fintrack.controller;

import fintrack.service.UsuarioService;
import fintrack.util.ValidadorUtil;
import java.util.Scanner;

/**
 * Controlador principal da aplicação FinTrack
 * Gerencia a interface do usuário e fluxo do sistema
 * 
 * @author Desenvolvedor FinTrack
 * @version 1.0
 */
public class AppController {
    private final Scanner scanner;
    private final UsuarioService usuarioService;
    private boolean sistemaRodando;
    
    // Constantes para o menu
    private static final String DIVISOR = "=".repeat(35);
    private static final String LINHA = "─".repeat(35);
    
    /**
     * Construtor da classe AppController
     */
    public AppController() {
        this.scanner = new Scanner(System.in);
        this.usuarioService = new UsuarioService();
        this.sistemaRodando = true;
    }
    
    /**
     * Inicia o sistema FinTrack
     */
    public void iniciarSistema() {
        try {
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
            
        } catch (Exception e) {
            System.out.println("❌ Erro inesperado no sistema: " + e.getMessage());
        } finally {
            encerrarSistema();
        }
    }
    
    /**
     * Exibe mensagem de boas-vindas
     */
    private void exibirBoasVindas() {
        System.out.println(DIVISOR);
        System.out.println("💰 BEM-VINDO AO FINTRACK!");
        System.out.println("🏦 Sistema de Controle Financeiro");
        System.out.println("✨ Versão 1.0");
        System.out.println(DIVISOR);
        System.out.println("🔹 Gerencie suas finanças de forma simples");
        System.out.println("🔹 Realize transferências com segurança");
        System.out.println("🔹 Acompanhe seu saldo em tempo real");
        System.out.println(LINHA);
    }
    
    /**
     * Processa o login do usuário
     * @return true se login bem-sucedido, false caso contrário
     */
    private boolean realizarLogin() {
        System.out.println("\n🔐 ACESSO AO SISTEMA");
        System.out.println(LINHA);
        
        int tentativas = 0;
        final int MAX_TENTATIVAS = 3;
        
        while (tentativas < MAX_TENTATIVAS) {
            try {
                System.out.print("👤 Digite seu nome completo: ");
                String nome = lerEntrada();
                
                System.out.print("📧 Digite seu email: ");
                String email = lerEntrada();
                
                if (usuarioService.loginOuCriarUsuario(nome, email)) {
                    return true;
                }
                
                tentativas++;
                if (tentativas < MAX_TENTATIVAS) {
                    System.out.println("⚠️  Tente novamente (" + (MAX_TENTATIVAS - tentativas) + " tentativas restantes)");
                    pausarExecucao("Pressione ENTER para tentar novamente...");
                }
                
            } catch (Exception e) {
                System.out.println("❌ Erro durante o login: " + e.getMessage());
                tentativas++;
            }
        }
        
        System.out.println("🚫 Número máximo de tentativas excedido!");
        return false;
    }
    
    /**
     * Exibe o menu principal do sistema
     */
    private void exibirMenuPrincipal() {
        System.out.println("\n" + DIVISOR);
        System.out.println("📋 MENU PRINCIPAL");
        System.out.println(DIVISOR);
        System.out.println("1. 💰 Consultar Saldo");
        System.out.println("2. 💸 Realizar Transferência");
        System.out.println("3. 💵 Receber Dinheiro");
        System.out.println("4. 📋 Ver Histórico");
        System.out.println("5. 👤 Meus Dados");
        System.out.println("6. 🔄 Fazer Logout");
        System.out.println("7. 🚪 Sair do Sistema");
        System.out.println(LINHA);
        System.out.print("🔸 Escolha uma opção (1-7): ");
    }
    
    /**
     * Processa a opção escolhida no menu
     */
    private void processarOpcaoMenu() {
        try {
            String opcao = lerEntrada();
            
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
                    exibirHistorico();
                    break;
                case "5":
                    exibirDadosUsuario();
                    break;
                case "6":
                    realizarLogout();
                    break;
                case "7":
                    sistemaRodando = false;
                    break;
                default:
                    System.out.println("❌ Opção inválida! Digite um número de 1 a 7.");
            }
            
            if (sistemaRodando && usuarioService.temUsuarioLogado()) {
                pausarExecucao();
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao processar opção: " + e.getMessage());
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
        System.out.println("\n💸 REALIZAR TRANSFERÊNCIA");
        System.out.println(LINHA);
        
        try {
            System.out.print("💰 Digite o valor (R$): ");
            String valorStr = lerEntrada();
            
            if (!ValidadorUtil.validarStringNumerica(valorStr)) {
                System.out.println("❌ Valor inválido! Digite apenas números.");
                return;
            }
            
            double valor = ValidadorUtil.converterStringParaDouble(valorStr);
            
            System.out.print("🏦 Para quem deseja transferir: ");
            String destino = lerEntrada();
            
            System.out.print("📝 Descrição (opcional): ");
            String descricao = lerEntrada();
            
            String destinoCompleto = destino + (descricao.isEmpty() ? "" : " - " + descricao);
            
            usuarioService.realizarTransferencia(valor, destinoCompleto);
            
        } catch (Exception e) {
            System.out.println("❌ Erro durante a transferência: " + e.getMessage());
        }
    }
    
    /**
     * Simula recebimento de dinheiro
     */
    private void receberDinheiro() {
        System.out.println("\n💵 RECEBER DINHEIRO");
        System.out.println(LINHA);
        
        try {
            System.out.print("💰 Digite o valor recebido (R$): ");
            String valorStr = lerEntrada();
            
            if (!ValidadorUtil.validarStringNumerica(valorStr)) {
                System.out.println("❌ Valor inválido! Digite apenas números.");
                return;
            }
            
            double valor = ValidadorUtil.converterStringParaDouble(valorStr);
            
            System.out.print("👤 De quem recebeu: ");
            String origem = lerEntrada();
            
            System.out.print("📝 Descrição (opcional): ");
            String descricao = lerEntrada();
            
            String origemCompleta = origem + (descricao.isEmpty() ? "" : " - " + descricao);
            
            usuarioService.receberDinheiro(valor, origemCompleta);
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao receber dinheiro: " + e.getMessage());
        }
    }
    
    /**
     * Exibe histórico de transações
     */
    private void exibirHistorico() {
        usuarioService.exibirHistoricoTransacoes();
    }
    
    /**
     * Exibe dados do usuário atual
     */
    private void exibirDadosUsuario() {
        System.out.println("\n👤 MEUS DADOS");
        System.out.println(LINHA);
        
        if (usuarioService.temUsuarioLogado()) {
            System.out.println(usuarioService.getUsuarioAtual().toString());
        } else {
            System.out.println("❌ Nenhum usuário logado!");
        }
    }
    
    /**
     * Realiza logout do usuário
     */
    private void realizarLogout() {
        System.out.println("\n🔄 FAZENDO LOGOUT...");
        usuarioService.logout();
        
        System.out.println("\n🔐 Deseja fazer login com outro usuário?");
        System.out.print("💭 Digite 's' para sim ou qualquer tecla para sair: ");
        String resposta = lerEntrada();
        
        if (resposta.toLowerCase().equals("s")) {
            if (!realizarLogin()) {
                System.out.println("❌ Falha no novo login. Encerrando sistema...");
                sistemaRodando = false;
            }
        } else {
            sistemaRodando = false;
        }
    }
    
    /**
     * Lê entrada do usuário com tratamento de erros
     * @return String digitada pelo usuário
     */
    private String lerEntrada() {
        try {
            return scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("❌ Erro ao ler entrada: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Pausa a execução aguardando input do usuário
     */
    private void pausarExecucao() {
        pausarExecucao("⏸️  Pressione ENTER para continuar...");
    }
    
    /**
     * Pausa a execução com mensagem customizada
     * @param mensagem Mensagem a ser exibida
     */
    private void pausarExecucao(String mensagem) {
        System.out.println("\n" + mensagem);
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignora erros de entrada
        }
    }
    
    /**
     * Encerra o sistema com mensagem de despedida
     */
    private void encerrarSistema() {
        System.out.println("\n" + DIVISOR);
        System.out.println("🚪 ENCERRANDO FINTRACK");
        System.out.println(DIVISOR);
        System.out.println("💰 Obrigado por usar nosso sistema!");
        System.out.println("🔒 Sessão encerrada com segurança");
        System.out.println("👋 Até a próxima!");
        System.out.println(DIVISOR);
        
        try {
            scanner.close();
        } catch (Exception e) {
            // Ignora erros ao fechar scanner
        }
    }
}