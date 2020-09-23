/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qrcodegenerator.model;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Luiz
 */
public class QrcodeModel {
    private String nome;
    private final String formato = "png";
    private static String path;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFormato() {
        return formato;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        QrcodeModel.path = path;
    }
       
}
