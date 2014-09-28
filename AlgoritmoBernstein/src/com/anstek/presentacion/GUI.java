/**
 * 
 */
package com.anstek.presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.anstek.negocio.Bernstein;
import com.anstek.utiles.LectorXML;


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
        txtArchivo.setEditable(false);
        
        txtResultado = new JTextArea(20, 30);
        txtResultado.setEditable(false);
        
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
 
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints cons = new GridBagConstraints();
        getContentPane().setLayout(layout);
        
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.weighty = 1;
        cons.fill = GridBagConstraints.NONE;
        getContentPane().add(label, cons);        
        cons.weighty = 0;
        
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.weightx = 1;
        cons.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(txtArchivo, cons);        
        cons.weighty = 0;
        
        cons.gridx = 2;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.weightx = 0;
        cons.fill = GridBagConstraints.NONE;
        getContentPane().add(btnBuscar, cons);    
        cons.weighty = 0;
        
        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.weightx = 0;
        cons.weighty = 1;
        cons.fill = GridBagConstraints.NONE;
        getContentPane().add(btnNormalizar, cons);       
        cons.weighty = 0;
        
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 3;
        cons.gridheight = 1;
        cons.weighty = 2;
        cons.weightx = 2;
        cons.fill = GridBagConstraints.BOTH;
        getContentPane().add(txtResultado, cons);
        
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
// 
//        layout.setHorizontalGroup(layout.createSequentialGroup()
//            .addGroup(layout.createParallelGroup(LEADING)
//        		.addComponent(label)
//        		.addComponent(btnNormalizar))
//            .addGroup(layout.createParallelGroup(LEADING)
//                .addComponent(txtArchivo))
//            .addGroup(layout.createParallelGroup(LEADING)
//                .addComponent(btnBuscar))            
//        );
//        
// 
//        layout.setVerticalGroup(layout.createSequentialGroup()
//            .addGroup(layout.createParallelGroup(BASELINE)
//                .addComponent(label)
//                .addComponent(txtArchivo)
//                .addComponent(btnBuscar))
//            .addGroup(layout.createParallelGroup(BASELINE)
//            	.addComponent(btnNormalizar))
//        );
// 
        //JPanel pnResultado = new JPanel();
        //this.getContentPane()
        
        setTitle("Normalizador de Bernstein");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(800, 600);
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
			xmlLector.cargarInformacion();
			
			// Algoritmo
			Bernstein bn = new Bernstein(xmlLector.getAtributos(), xmlLector.getDependenciasFuncionales());
						
			// Ejecuta algoritmo normalizador
			TreeMap<String,HashSet<String>> resultado = bn.NormalizadorBernstein();
			
			JOptionPane.showMessageDialog(this, "Normalizacion finalizada");
			
			// Pinta realciones en pantalla
			System.out.println(resultado.toString());
			String relaciones = "";
			int flag = 1;
			for (Map.Entry<String,HashSet<String>> rel : resultado.entrySet()) {
				relaciones += "RELACION "+String.valueOf(flag)+" => \n";
				relaciones += "\tLLAVE: \t\t"+rel.getKey();
				relaciones += "\n\tATRIBUTOS: \t"+rel.getValue().toString();
				relaciones += "\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n";
				flag++;
			}
			
			txtResultado.setText(relaciones);
			
			
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
