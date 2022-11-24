/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camara;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.panamahitek.PanamaHitek_Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javax.swing.Icon;

/**
 *
 * @author YO
 */
public class UICamara extends javax.swing.JFrame {
        Image image;
        BufferedImage imagen,imagen1,imagen2,imagen3;
        String var1,var2;
        int diametro,columna,fila,colorR,colorG,colorB;
    /**
     * Creates new form UICamara
     */
    AdminCamara cam = new AdminCamara();
    public UICamara() {
        initComponents();
        Camara.setLayout(new javax.swing.BoxLayout(Camara, javax.swing.BoxLayout.LINE_AXIS));
        //se añade el componente de video
        Camara.add(cam.capturaCamara());
        jLabel1.setText("");
        conectar();
    }
    
    int estado=0,infra=0;
    private static final int TimeOut = 2000;
    private static final int Baudios = 9600;
    
    private final String puerto = "COM10";
    PanamaHitek_Arduino ard = new PanamaHitek_Arduino();
    
    SerialPortEventListener evento = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) 
        {
            String info;
            if(ard.isMessageAvailable())
            {
                System.out.println("Recibiendo datos de arduino");
                info= ard.printMessage();
                System.out.println(info);
                jLabel3.setText(info);
                
                /////////////////
            //    if( Integer.parseInt(jLabel3.getText())==1){ 
                    cam.capturarImagen();
                    jLabel1.setIcon(new ImageIcon(cam.getImagen()));
                    try {
                        ImageIO.write((RenderedImage) cam.getImagen(), "png", new File("imagen2.png"));
                    } catch (IOException ex) {  }
        
                    BufferedImage A,B;
                    A=CargaImagen("D:\\CESMAG\\OCTAVO\\vision\\Proyecto_Final\\Java\\Camara\\imagen2.png");
                    //A=CargaImagen("D:\\Naranja_b_p.jpg");
                    //A=CargaImagen("D:\\moyu.jpg");
        
                    //jLabel2.setIcon(new ImageIcon(A));
                    //Pasar imagen a gris
                    RGBaGRIS();
                    Binarizacion();
                    jLabel2.setIcon(new ImageIcon(imagen));
                    Medidas();
                    B=CargaImagen("D:\\CESMAG\\OCTAVO\\vision\\Proyecto_Final\\Java\\Camara\\imagen2.png");
                    Color();
                    jLabel3.setText(var2);
                    jLabel4.setText(var1);
                    jLabel1.setIcon(new ImageIcon(imagen));
                    
                    //B=imagen;      
                        
                    //jLabel2.setIcon(new ImageIcon(imagen));
                    if(diametro>300&diametro<335)
                    {
                       if(colorR>250&&colorG>250&&colorB>250){
                        enviarDatos("2");    
                       }else{
                           enviarDatos("3");
                       }
                        
                        //jLabel5.setText("tres");//System.out.println("3");
                    
                    }else{
                        enviarDatos("1");
                        ///jLabel5.setText("tres");//System.out.println("3");
                    }
                   }
        }
    };
    
    public void conectar()
    {
        try 
        {
            ard.ArduinoRXTX(puerto, TimeOut, Baudios,evento);
        } catch (Exception ex) 
        {
            System.out.println("no se pudo conectar");
            System.exit(0);
        }
    }
    
    public void enviarDatos(String datos)
    {
        try 
        {
            ard.sendData(datos);
        } catch (Exception ex) {
            System.out.println("No se pudo enviar dato");
        }
    }
     
    public BufferedImage CargaImagen(String Direccion)
    {
        File Dir;
        Dir = new File(Direccion);
        imagen=imagen1=imagen2=imagen3=null;
        try {
            imagen = ImageIO.read(Dir);
            imagen1 = ImageIO.read(Dir);
            imagen2 = ImageIO.read(Dir);
            imagen3 = ImageIO.read(Dir);
        } catch (IOException ex) {
            System.out.println("imagen no pudo ser leida");
        }
        return imagen;
    }
    /**
     * Permite visualizar la imagen contenida en un objeto de la clase MImagenes
     * en un JFrameForm
     */
    public int[] tamanoImagen()
    {
        int[] t=new int[2]; 
        t[0]= imagen.getWidth();
        t[1]= imagen.getHeight();
        return t;
    }
    /**
     * Función que permite obtener los valores RGB de la imagen a color contenida
     * en el objeto
     * @param x Fila en la que se encuentra el pixel
     * @param y Columna en la que se encuentra el pixel
     * @return Retorna un vector con los valores de intensidad de rojo, verde
     * y azul [R G B]
     */
    public int[] capturaRGB(int x,int y)
    {
        int[] rgb= new int[3];
        int color;
        color = imagen.getRGB(x, y);
        Color color_rgb = new Color(color);
        
        rgb[0]= color_rgb.getRed();
        rgb[1]= color_rgb.getGreen();
        rgb[2]= color_rgb.getBlue();
        return rgb;
    }
    /**
     * Función que permite configurar los valores RGB de una imagen a color
     * 
     * @param im Imagen a configurar sus colores
     * @param x Fila en la que se encuentra el pixel
     * @param y Columna en la que se encuentra el pixel
     * @param r Valor rojo a asignar a la imagen
     * @param g Valor verde a asignar a la imagen
     * @param b Valor azul a asignar a la imagen
     */
    public void configuraRGB(BufferedImage im,int x,int y,int r,int g,int b)
    {
        Color col = new Color(r, g, b);
        im.setRGB(x, y, col.getRGB());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Camara = new javax.swing.JPanel();
        Foto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Menu = new javax.swing.JPanel();
        TomaFoto = new javax.swing.JButton();
        CargaImagen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Camara.setBorder(javax.swing.BorderFactory.createTitledBorder("Camara"));

        javax.swing.GroupLayout CamaraLayout = new javax.swing.GroupLayout(Camara);
        Camara.setLayout(CamaraLayout);
        CamaraLayout.setHorizontalGroup(
            CamaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        CamaraLayout.setVerticalGroup(
            CamaraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 665, Short.MAX_VALUE)
        );

        Foto.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout FotoLayout = new javax.swing.GroupLayout(Foto);
        Foto.setLayout(FotoLayout);
        FotoLayout.setHorizontalGroup(
            FotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FotoLayout.createSequentialGroup()
                .addGroup(FotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(FotoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FotoLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(FotoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(157, 157, 157)))))
                .addContainerGap())
        );
        FotoLayout.setVerticalGroup(
            FotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FotoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FotoLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(FotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(345, 345, 345))
                    .addGroup(FotoLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Menu.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu"));

        TomaFoto.setText("Foto");
        TomaFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TomaFotoActionPerformed(evt);
            }
        });

        CargaImagen.setText("Imagen");

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TomaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CargaImagen)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(TomaFoto)
                .addComponent(CargaImagen))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Camara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Camara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void RGBaGRIS()
    {
        int f,c,g;
        //int[] prgb = new int[3];
        if(imagen!=null)
        {
            c=tamanoImagen()[0];
            f=tamanoImagen()[1];
            for(int i=0;i<c;i++)
            {
                for(int j=0;j<f;j++)
                {
                    int[] prgb;
                    prgb=capturaRGB(i, j);
                    g= (prgb[0]+prgb[1]+prgb[2])/3;
                    configuraRGB(imagen, i, j, g, g, g);
                }
            }
        }
    }
    
    public void Binarizacion()//127.5
    {
         int f,c,k1,k2,k3;
         if(imagen!=null)
        {
            c=tamanoImagen()[0];
            f=tamanoImagen()[1];
            int[] prgb;
            for(int i=0;i<c;i++)
            {
                for(int j=0;j<f;j++)
                {
                    
                    prgb=capturaRGB(i, j);
                    if(prgb[0]>127){
                        k1=1;
                    }else k1=0;
                    configuraRGB(imagen, i, j, k1, k1, k1);
                    //jLabel2.setIcon(new ImageIcon(imagen));
                }
            }
            imagen2=imagen;
            for(int i=2;i<=c-2;i++)
            {
                for(int j=2;j<=f-2;j++)
                {
                       k1=k2=0;
                       for(int x=0;x<3;x++){
                            for(int y=0;y<3;y++){
                                prgb=capturaRGB(i-1+y, j-1+x);
                                k1=k1+prgb[0];//if (prgb[0]==255){
//                                    k1=k1+1;
//                                }
                            }    
                        }
                        if (k1==9){
                            configuraRGB(imagen1, i, j, 1, 1, 1);
                        }else {
                            configuraRGB(imagen1, i, j, 0, 0, 0);
                        }
//                        ko=Integer.toString(k1);
//                        System.out.println( ko +" ");
                }
             } 
                for(int i=2;i<c-2;i++)
                {
                    for(int j=2;j<f-2;j++)
                    {
                       imagen=imagen1;
                       prgb=capturaRGB(i, j);
                       k1=prgb[0];
                       imagen=imagen2;
                       prgb=capturaRGB(i, j);
                       k2=prgb[0];
                       configuraRGB(imagen3, i, j, 255*(k2-k1), 255*(k2-k1), 255*(k2-k1));

                    }
                }
                imagen=imagen3;
        }
        return;
    }
    
    public void Medidas()
    {
        int f,c,g;
        if(imagen!=null)
        {
            c=tamanoImagen()[0];
            f=tamanoImagen()[1];
            for(int i=150;i<c-10;i++)
            {
                for(int j=10;j<f;j++)
                {
                    int[] prgb;
                    prgb=capturaRGB(i,j);
                    if(prgb[0]==255){
                        for(int k=c-10;k>i;k--){
                            prgb=capturaRGB(k,j);
                            if(prgb[0]==255){
                                diametro=k-i;
                                fila=i;
                                columna=j;
                                g=(k-i);
                                var1=String.valueOf(g); 
                                return;
                            }
                            if(k-i<250){
                                diametro=k-i;
                                fila=i;
                                columna=j;
                                g=(k-i);
                                var1=String.valueOf(g); 
                                return;
                            }
                        }    
                    }
                    
                }
            }
        }
    }

     public void Color()
    {
        int pos;
        float k1=0,k2=0,k3=0;
        int[] prgb = null;
        colorR=0;colorG=0;colorB=0;
        if(imagen!=null)
        {
            /*for(int i=1;i<4;i++){ 
                k1=0;k2=0;k3=0;
                pos=diametro*i*2/10;
                for(int eni=0;eni<3;eni++){
                    for(int enj=0;enj<3;enj++){
                        prgb=capturaRGB(fila-1+eni, columna-1+enj+pos);
                        //prgb=capturaRGB(fila,columna+());
                        k1=prgb[0]+k1;
                        k2=prgb[1]+k2;
                        k3=prgb[2]+k3;
                    }    
                }
                colorR=(int) ((k1/9)+colorR);
                colorG=(int) ((k2/9)+colorG);
                colorB=(int) ((k3/9)+colorB);
            }
            colorR=colorR/3;
            colorG=colorG/3;
            colorB=colorB/3;
            var2=String.valueOf(colorR)+"/"+String.valueOf(colorG)+"/"+String.valueOf(colorB);*/
            
            prgb=capturaRGB(fila+50, columna+50);
            colorR=prgb[0];
            colorG=prgb[1];
            colorB=prgb[2];
            var2=String.valueOf(colorR)+"/"+String.valueOf(colorG)+"/"+String.valueOf(colorB);
        }
        return;
    }
    private void TomaFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TomaFotoActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_TomaFotoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UICamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICamara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UICamara().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Camara;
    private javax.swing.JButton CargaImagen;
    private javax.swing.JPanel Foto;
    private javax.swing.JPanel Menu;
    private javax.swing.JButton TomaFoto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables

    private BufferedImage getIconImage(JLabel jLabel2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
