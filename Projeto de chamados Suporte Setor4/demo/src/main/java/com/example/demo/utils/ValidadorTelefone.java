package com.example.demo.utils;
import java.util.regex.*;

public class ValidadorTelefone {
    public static boolean validarTelefone(String telefone) {
        // Expressão regular para validar números de telefone no formato (XX) XXXXX-XXXX
        String padrao = "^\\(\\d{2}\\) \\d{5}-\\d{4}$";
        
        // Compila a expressão regular
        Pattern pattern = Pattern.compile(padrao);
        
        // Cria um objeto Matcher para verificar se o número corresponde ao padrão
        Matcher matcher = pattern.matcher(telefone);
        
        // Retorna verdadeiro se o número corresponder ao padrão, caso contrário falso
        return matcher.matches();
    }
}