package fintrack;

import fintrack.controller.AppController;

/**
 * @author Arthur Oliveira Silva
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA FINTRACK ===");
        System.out.println("Iniciando aplicação...\n");
        
        AppController app = new AppController();
        app.iniciarSistema();
    }
}