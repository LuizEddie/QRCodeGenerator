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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import qrcodegenerator.model.QrcodeModel;

/**
 *
 * @author Luiz
 */
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class QrcodeController{
    
    public void choosePath(String texto){
        if(texto == null || texto.isEmpty()){
            JOptionPane.showMessageDialog(null, "Insira o qrcode!");
        }else{
            JFileChooser chooser = new JFileChooser();
            
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                String path = chooser.getSelectedFile().getAbsolutePath();
                this.saveImage(texto, path);
            }
        }
    }
    /*public void gerarQRCode(String qrCode){
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
    }*/
    
    /*public void gerarComZXing(String path, String texto){
        try {
            QrcodeModel model = new QrcodeModel();
            model.setNome(texto);
            String nome = model.getNome();
            String formato = QrcodeModel.getFormato();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 350, 350);
            
            Path pathReal = FileSystems.getDefault().getPath(path + "/" + nome + "." +formato);
            MatrixToImageWriter.writeToPath(bitMatrix, formato, pathReal);
            QrcodeModel.setPath(pathReal.toString());
            System.out.println(QrcodeModel.getPath());
        } catch (WriterException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }  
    }*/    
    
    public void saveImage(String texto, String path){
        try {
            BitMatrix qrCode = this.gerarQrCode(texto);
            String formato = QrcodeModel.getFormato();
            
            Path pathReal = FileSystems.getDefault().getPath(path + "/" + texto + "." + formato);
            MatrixToImageWriter.writeToPath(qrCode, formato, pathReal);
            JOptionPane.showMessageDialog(null, "Imagem salva com sucesso");
        } catch (IOException ex) {
             JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }

    }
    
    public BitMatrix gerarQrCode(String texto){
        BitMatrix bitMatrix = null;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 350, 350);
            /*
            ByteArrayOutputStream tempQrCode = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, formato, tempQrCode);
            
            imageBytes = tempQrCode.toByteArray();
            */
        } catch (WriterException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return bitMatrix;
    }

    public ImageIcon gerarImageIcon(String texto){
        byte[] imageBytes = null;
        try {
            BitMatrix qrCode = this.gerarQrCode(texto);
            String formato = QrcodeModel.getFormato();        
            ByteArrayOutputStream tempQrCode = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(qrCode, formato, tempQrCode);
            
            imageBytes = tempQrCode.toByteArray();
            
            
        } catch (IOException ex) {
            Logger.getLogger(QrcodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ImageIcon(imageBytes);
    }
    
    public void printImage(Image image){
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new Printable() {
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                System.out.println(pageIndex);
                if(pageIndex != 0){
                    return NO_SUCH_PAGE;
                }
                graphics.drawImage(image,100,100, null);
                System.out.println("Imprimindo");
                return PAGE_EXISTS;
            }
        });
        
        try{
            pj.print();
        }catch(PrinterException e){
            e.printStackTrace();
        }
    }
}

