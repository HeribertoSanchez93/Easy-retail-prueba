/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptovta;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.gstreamer.GStreamerDriver;
import java.awt.BorderLayout;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;



/**
 *
 * @author Heriberto
 */
public class Scan extends JFrame implements WebcamMotionListener, Runnable, WebcamListener, WindowListener, Thread.UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener
{
    /*Variables que son necesarias para el uso automatico de la webcam*/
    private JTextField txtCods;
    private JButton btnCods;
    String dato="";
    private JButton btnPadre;
    static 
    {
        try
        {
            /*Se manda llamar la ejecucion del driver de las camaras*/
            Webcam.setDriver(new GStreamerDriver());
        }
        catch(Exception ex)
        {
            /*En caso de que no exista tal driver se atrapa el error*/
        }
    }
     private static final long serialVersionUID = 1L;

     /*Objetos que permiten la inicializacion de la webcam*/
	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private WebcamPicker picker = null;
        private WebcamMotionDetector detector=null;
         
        
	@Override
	public void run() {
            /*Se añaden los eventos especiales de la api de la webcam*/
		Webcam.addDiscoveryListener(this);
                /*Se le asigna un titulo a la ventana de la webcam*/
		setTitle("Scaner");
		/*para organizar el contenido se le asigna un layout*/
		setLayout(new BorderLayout());
                /*Se le asignan los eventos requeridos de una ventana*/
		addWindowListener(this);
                /*Se instancia el "picker" cuya funcion es obtener las camaras disponibles y poder elegir*/
                /*cualquiera de estas mediante la forma de un combo box*/
		picker = new WebcamPicker();
		picker.addItemListener(this);
                /*se instancia el objeto "webcam" con la camara por defecto que detecte el "picker" */
		webcam = picker.getSelectedWebcam();
                /*si es nulo entonces */
		if (webcam == null) 
                {
                        /*Enviar mensaje de que no se encontraron webcams*/
			System.out.println("No webcams found...");
			System.exit(1);
		}
                /*Se obtiene el mejor tamaño para la webcam segun la resolucion*/
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.addWebcamListener(Scan.this);

                /*se instancia un WebcamPanel, que lleva como parametro el objeto webcam y false*/
                /*Este panel es el mostrara a pantalla lo que este capturando la webcam*/
		panel = new WebcamPanel(webcam, false);
		panel.setFPSDisplayed(true);

                /*Se le dan las coordenadas al picker para pocisionarlo*/
		add(picker, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		pack();
                /*Se vuelve visible la ventana */
		setVisible(true);

		Thread t = new Thread() 
                {

			@Override
			public void run() 
                        {
                            /*Se inicia el panel para que empiece a mostrar a pantalla lo que se esta capturando*/
                            panel.start();
                            detector = new WebcamMotionDetector(Webcam.getDefault());
                            detector.setInterval(100); // one check per 500 ms
                            detector.addMotionListener(Scan.this);
                            detector.start();
			}
		};
                /*se dan los valores de un hilo para iniciar el panel*/
		t.setName("inicio");
		t.setDaemon(true);
		t.setUncaughtExceptionHandler(this);
		t.start();
                /*hilo para iniciar el proceso de lectura de el codigo de barras*/
             
       /*se inicia el hilo*/
           
	}

	//public static void main(String[] args) {
	//	SwingUtilities.invokeLater(new Scan());
	//}

	@Override
	public void webcamOpen(WebcamEvent we) {
		System.out.println("webcam abierta");
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		System.out.println("webcam cerrada");
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		System.out.println("eliminando recursos");
	}

	@Override
	public void webcamImageObtained(WebcamEvent we) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

        private void formWindowClosing(java.awt.event.WindowEvent evt) 
        {                                   
        
        /*Presiona el botón de salir*/
            
            webcam.close();
            btnPadre.setEnabled(true);
            this.dispose();
           
        }
	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) 
        {
            btnPadre.setEnabled(true);
           webcam.close();
            this.dispose();
            
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("webcam viewer resumed");
		panel.resume();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("webcam viewer paused");
		panel.pause();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(String.format("Exception in thread %s", t.getName()));
		e.printStackTrace();
	}
        
        @Override
	public void motionDetected(WebcamMotionEvent wme) 
        {
		try 
                            {
                                /*Si no se obtiene un codigo de barras estara mandando excepciones*/
                                Reader reader=new MultiFormatReader();
                                /*Se instancia este objeto que en base de un buffer de imagen detecta si hay un codigo de barras*/
                                LuminanceSource source = new BufferedImageLuminanceSource(webcam.getImage());
                        
                                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                                /*Se decodifica el mapa de bits para obtener el valor del codigo de barras*/
                                
                                Result result = reader.decode(bitmap);
                        
                                dato=result.getText();
                                /*Se guarda el dato leido en la caja de texto*/
                                txtCods.setText(dato);
                        
                        
                                txtCods.requestFocus();
                                txtCods.selectAll();
                                txtCods.transferFocus();
                                /*Si el campo de boton no es nulo entonces*/
                                if(btnCods!=null)
                                    /*Se vuelve a recuperar el foco en la caja de texto*/
                                    btnCods.doClick(1000);
                                /*Se detiene la camara por un tiempo para que no lea mas de lo debido*/
                                
                            }
                            catch (Exception ex)
                            {
                                 /*En caso de que no lea nada estara mandando excepciones*/
                            }
	}
        
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() != webcam) {
			if (webcam != null) {
                                
				panel.stop();
                                detector.stop();
				remove(panel);

				webcam.removeWebcamListener(this);
				webcam.close();

				webcam = (Webcam) e.getItem();
				webcam.setViewSize(WebcamResolution.VGA.getSize());
				webcam.addWebcamListener(this);

				System.out.println("selected " + webcam.getName());
                                
				panel = new WebcamPanel(webcam, false);
				panel.setFPSDisplayed(true);

				add(panel, BorderLayout.CENTER);
				pack();
                               
				Thread t = new Thread() 
                                {

					@Override
					public void run() {
						panel.start();
                                                detector = new WebcamMotionDetector(Webcam.getDefault());
                                                detector.setInterval(100); // one check per 500 ms
                                                detector.addMotionListener(Scan.this);
                                                detector.start();
					}
				};
				t.setName("example-stoper");
				t.setDaemon(true);
				t.setUncaughtExceptionHandler(this);
				t.start();
                           
			}
		}
	}

	@Override
	public void webcamFound(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.addItem(event.getWebcam());
		}
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.removeItem(event.getWebcam());
		}
	}
    public Scan(JTextField txtCods,JButton btnCods,JButton btnPadre) 
     {
         
       
        addWindowListener(new java.awt.event.WindowAdapter() 
                {
                @Override
                    public void windowClosing(java.awt.event.WindowEvent evt) 
                    {
                         formWindowClosing(evt);
                    }
                });
         this.txtCods= txtCods;
        this.btnCods=btnCods;
        this.btnPadre=btnPadre;
        this.setAlwaysOnTop(true);
     }
    public void cerrarCam()
    {
        webcam.close();
        btnPadre.setEnabled(true);        
        this.dispose();
       
       
    }
 }
