package fintrack.util;

import java.util.regex.Pattern;

/**
 * Classe utilitária para validações do sistema FinTrack
 * Contém métodos estáticos para validar dados de entrada
 * 
 * @author Arthur Oliveira Silva
 * @version 1.0
 */
public class ValidadorUtil {
    
    // Regex para validação de email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    /**
     * Valida se o nome é válido
     * @param nome Nome a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean validarNome(String nome) {
        return nome != null && 
               !nome.trim().isEmpty() && 
               nome.trim().length() >= 2 && 
               nome.trim().length() <= 50 &&
               nome.matches("^[a-zA-ZÀ-ÿ\\s]+$"); // Apenas letras e espaços
    }
    
    /**
     * Valida formato de email usando regex
     * @param email Email a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean validarEmail(String email) {
        return email != null && 
               !email.trim().isEmpty() && 
               EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Valida se o valor monetário é positivo
     * @param valor Valor a ser validado
     * @return true se válido, false caso contrário
     */
    public static boolean validarValor(double valor) {
        return valor > 0 && valor <= 999999.99; // Limite máximo para segurança
    }
    
    /**
     * Valida se uma string não está vazia
     * @param texto Texto a ser validado
     * @return true se não está vazio, false caso contrário
     */
    public static boolean validarTextoNaoVazio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    
    /**
     * Formata valor monetário para exibição
     * @param valor Valor a ser formatado
     * @return String formatada com R$
     */
    public static String formatarMoeda(double valor) {
        return String.format("R$ %.2f", valor);
    }
    
    /**
     * Limpa e formata nome (primeira letra maiúscula)
     * @param nome Nome a ser formatado
     * @return Nome formatado
     */
    public static String formatarNome(String nome) {
        if (!validarNome(nome)) return nome;
        
        String[] palavras = nome.trim().toLowerCase().split("\\s+");
        StringBuilder nomeFormatado = new StringBuilder();
        
        for (int i = 0; i < palavras.length; i++) {
            if (i > 0) nomeFormatado.append(" ");
            nomeFormatado.append(Character.toUpperCase(palavras[i].charAt(0)))
                         .append(palavras[i].substring(1));
        }
        
        return nomeFormatado.toString();
    }
    
    /**
     * Limpa e formata email (minúsculo)
     * @param email Email a ser formatado
     * @return Email formatado
     */
    public static String formatarEmail(String email) {
        return email != null ? email.trim().toLowerCase() : "";
    }
    
    /**
     * Valida se um valor de string pode ser convertido para double
     * @param valorStr String a ser testada
     * @return true se pode ser convertido, false caso contrário
     */
    public static boolean validarStringNumerica(String valorStr) {
        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            return validarValor(valor);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Converte string para double, tratando vírgula como separador decimal
     * @param valorStr String a ser convertida
     * @return Valor convertido ou 0 se inválido
     */
    public static double converterStringParaDouble(String valorStr) {
        try {
            return Double.parseDouble(valorStr.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
