/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package camara;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.util.Vector;
import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Camilo
 */
public class AdminCamara {
    private String dispositivo = "vfw:Microsoft WDM Image Capture (Win32):0";
    private Player player = null;
    Image img = null;
    
    public void dispositivosDisponibles()
    {
        Vector dispositivos = CaptureDeviceManager.getDeviceList(null);
        for(int i=0; i<dispositivos.size();i++)
        {
            CaptureDeviceInfo infoDisp = (CaptureDeviceInfo)dispositivos.elementAt(i);
            System.out.println(infoDisp.getName());
            Format[] formats = infoDisp.getFormats();
            for (int j = 0; j<formats.length; j++) 
            {
                System.out.println("  "+formats[j].toString());
            }
        }
    }
    public Component capturaCamara()
    {
        Component componente_video;
        try 
        {
            // Se obtiene el dispositivo
            CaptureDeviceInfo device = CaptureDeviceManager.getDevice(dispositivo);
            //se obtiene la fuente de datos de captura
            MediaLocator localizador = device.getLocator();
            //El localizador es del tipo "vfw://0" video para windows
            //se crea el PLAYER y se ejecuta
            player = Manager.createRealizedPlayer(localizador);
            player.start();
        } 
        catch (IOException | NoPlayerException | CannotRealizeException ex) 
        {
            System.out.println("No se puede cargar cámara");
        }
        //Si se pudo crear el PLAYER, se obtiene el componente de video
        if ((componente_video = player.getVisualComponent()) != null) 
        {
            //se da un tamaÃ±o al componente
            componente_video.setSize(160, 120);
            return componente_video;
        } 
        else 
        {
            JOptionPane.showMessageDialog(null,"No se pudo crear el video...");
            return null;
        }
    }
    
    public void capturarImagen(){
        FrameGrabbingControl ControlFG = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");
        Buffer buffer = ControlFG.grabFrame();
        // creamos la imagen awt
        BufferToImage imagen = new BufferToImage((VideoFormat)buffer.getFormat());
        img = imagen.createImage(buffer);
    }

    /**
     * Retorna la última imagen capturada por la cámara
     * @return 
     */
    public Image getImagen(){
        return img;
    }
    
    /**
     * Permite asignarle a un JLabel la última imagen capturada por la cámara
     * @param lb Label en el cual se mostrará la imagen
     */
    public void setImage(JLabel lb){
        capturarImagen();        
        lb.setIcon( new javax.swing.ImageIcon( img ) );
        System.out.println("ancho= " + img.getWidth(null));
        System.out.println("alto= " + img.getHeight(null));
    }
    
    public void Escaner()
    {
        //se recorre la cantidad de Dispositivos que encuentra disponibles
        for(int i=0; i<CaptureDeviceManager.getDeviceList(null).size();i++)
        {
            //se muestra uno por uno en pantalla
           System.out.println(((CaptureDeviceInfo)CaptureDeviceManager.getDeviceList(null).get(i)).getName());
        }
    }
        
}
