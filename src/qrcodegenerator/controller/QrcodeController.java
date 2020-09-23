/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qrcodegenerator.controller;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import qrcodegenerator.model.QrcodeModel;

/**
 *
 * @author Luiz
 */
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class QrcodeController {
    
    
    public void gerarQRCode(String qrCode){
        if(qrCode == null || qrCode.isEmpty()){
            JOptionPane.showMessageDialog(null, "Insira o qrcode");
        }else{
            JFileChooser chooser = new JFileChooser();
            
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                String caminho = chooser.getSelectedFile().getAbsolutePath();
                gerarComZXing(caminho, qrCode);
            }
        }
    }
    
    public void gerarComZXing(String path, String texto){
        try {
            QrcodeModel model = new QrcodeModel();
            model.setNome(texto);
            String nome = model.getNome();
            String formato = model.getFormato();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 350, 350);
            
            Path pathReal = FileSystems.getDefault().getPath(path + "/" + nome + "." +formato);
            MatrixToImageWriter.writeToPath(bitMatrix, formato, pathReal);
            QrcodeModel.setPath(pathReal.toString());
            System.out.println(QrcodeModel.getPath());
        } catch (WriterException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        
    }
    
    
    
    
}
