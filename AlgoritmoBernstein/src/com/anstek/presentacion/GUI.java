/**
 * 
 */
package com.anstek.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.anstek.negocio.Bernstein;
import com.anstek.utiles.LectorXML;

import static javax.swing.GroupLayout.Alignment.*;

/**
 * @author ddmurillo
 *
 */

public class GUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1239309416969831276L;
	
	/**
	 * 
	 */
	private JLabel label;
	private JTextField txtArchivo;
	private JButton btnBuscar;
	private JButton btnNormalizar;
	private File archivo;
	private JTextArea txtResultado;
	
	public GUI(){
		label = new JLabel("Archivo XML:");;
        txtArchivo = new JTextField();
        txtResultado = new JTextArea(20, 30);
        
        btnNormalizar = new JButton("Normalizar");
        btnNormalizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Normalizar();
			}
		});
        
        btnBuscar = new JButton("Buscar archivo...");
        btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SeleccionaArchivo();
			}
		});
 
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(LEADING)
        		.addComponent(label)
        		.addComponent(btnNormalizar))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(txtArchivo))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(btnBuscar))            
        );
        
 
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(label)
                .addComponent(txtArchivo)
                .addComponent(btnBuscar))
            .addGroup(layout.createParallelGroup(BASELINE)
            	.addComponent(btnNormalizar))
        );
 
        //JPanel pnResultado = new JPanel();
        //this.getContentPane()
        
        setTitle("Normalizador de Bernstein");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(640, 480);
		this.setMinimumSize(getSize());
	}
	
	protected void SeleccionaArchivo(){
		//
        JFileChooser filechooser = new JFileChooser();

        filechooser.addChoosableFileFilter(new FileFilter() {			
			@Override
			public String getDescription() {
				return "Archivos XML";
			}
			
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()){ 
					return true; 
				}
				return f.getName().endsWith(".xml");
			}
		});
        
        filechooser.setAcceptAllFileFilterUsed(false);
        filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
	    if(filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    	archivo = filechooser.getSelectedFile();
	    	txtArchivo.setText(archivo.getAbsolutePath());
	        System.out.println("Archivo seleccionado: " + archivo);
	    } 
	    else {
	    	System.out.println("Ningun archivo seleccionado");
	    };
	}
	
	protected void Normalizar(){
		try {
			if (archivo == null) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar un archivo XML para normalizar");
				return;
			}
			
			// Carga Archivo
			LectorXML xmlLector = new LectorXML(archivo.getAbsolutePath());
			
			// Algoritmo
			Bernstein bn = new Bernstein(xmlLector.getAtributos(), xmlLector.getDependenciasFuncionales());
						
			// Pinta realciones en pantalla
			TreeMap<String,HashSet<String>> resultado = bn.NormalizadorBernstein();
			
			JOptionPane.showMessageDialog(this, "Normalizacion finalizada");
			
			txtResultado.setText(resultado.toString());
			
			
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			JOptionPane.showMessageDialog(this, "Error en el proceso de Normalizacion\n["+e.getMessage()+"]");
		}
	}
	
	public static void main (String[] args){
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                                 // "javax.swing.plaf.metal.MetalLookAndFeel");
                                //"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                                // UIManager.getCrossPlatformLookAndFeelClassName());
                    		UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new GUI().setVisible(true);
            }
        });
	}
}
