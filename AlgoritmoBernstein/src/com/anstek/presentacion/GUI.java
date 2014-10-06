/**
 * 
 */
package com.anstek.presentacion;

import java.awt.Font;
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

import com.anstek.clases.Atributo;
import com.anstek.clases.DependenciaFuncional;
import com.anstek.negocio.AtributosExtranios;
//import com.anstek.negocio.Bernstein;
import com.anstek.negocio.DependenciasRedundantes;
import com.anstek.negocio.LDS;
import com.anstek.negocio.Particion;
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
        txtResultado.setFont(new Font("arial", NORMAL, 11));
        txtResultado.setEditable(false);
        
        JScrollPane scroll = new JScrollPane(txtResultado);
        
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
        cons.weighty = 0.2;
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
        cons.weighty = 0.2;
        cons.fill = GridBagConstraints.NONE;
        getContentPane().add(btnNormalizar, cons);       
        cons.weighty = 0;
        
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridwidth = 3;
        cons.gridheight = 3;
        cons.weighty = 3;
        cons.weightx = 2;
        cons.fill = GridBagConstraints.BOTH;
        getContentPane().add(scroll, cons);
        
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
		this.setSize(960, 700);
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
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
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
			if (archivo == null || !archivo.getName().endsWith(".xml")) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar un archivo XML para normalizar");
				return;
			}
			
			txtResultado.setText("");
			
			// Carga Archivo
			LectorXML xmlLector = new LectorXML(archivo.getAbsolutePath());
			xmlLector.cargarInformacion();
			
			// Algoritmo
			//Bernstein bn = new Bernstein(xmlLector.getAtributos(), xmlLector.getDependenciasFuncionales());
			// Ejecuta algoritmo normalizador
			//TreeMap<String,HashSet<String>> resultado = bn.NormalizadorBernstein();
			
			// Lado derecho Sipmle
			LDS.listaAtributos = xmlLector.getAtributos();
			DependenciaFuncional[] df1 = LDS.LadoDerechoSimple(xmlLector.getDependenciasFuncionales());
			TextoTextarea("Lado Derecho Simple:\t\t"+DependenciaToString(df1, xmlLector.getAtributos()));
			
			// Limpia atributos extraï¿½os
			AtributosExtranios.listaAtributos = xmlLector.getAtributos();
			DependenciaFuncional[] df2 = AtributosExtranios.LimpiaAtributosExtranios(df1);
			TextoTextarea("Atributos extraños:\t\t"+DependenciaToString(df2, xmlLector.getAtributos()));
			
			// Quita DF redundantes
			DependenciasRedundantes dr = new DependenciasRedundantes(xmlLector.getAtributos());
			DependenciaFuncional[] df3 = dr.eliminarDependenciasRedundantes(df2);
			TextoTextarea("Dependencias reduntantes:\t"+DependenciaToString(df3, xmlLector.getAtributos()));
			
			// Obtiene relaciones 
			TreeMap<String,HashSet<Integer>> rel = Particion.ParticionarRelaciones(df3);
			System.out.println("Relaciones: "+rel);
			
			// Obtiene el nombre de los atributos de acuerdo a los Ids de las relaciones
			TreeMap<String,HashSet<String>> result = new TreeMap<String, HashSet<String>>();		
			for (Map.Entry<String,HashSet<Integer>> r : rel.entrySet()) {
				String k = r.getKey().replace("[","").replace("]", "");
				String[] keys = k.split(",");
				
				HashSet<String> knames = new HashSet<String>();
				
				for (int i = 0; i < keys.length; i++) {
					//System.out.println(keys[i].length());
					knames.add(Atributo.retornarAtributoPorCodigo(Integer.valueOf(keys[i].trim()), xmlLector.getAtributos()).getNombre());
				}
				
				HashSet<String> vnames = new HashSet<String>();
				for (Integer s : r.getValue()) {
					vnames.add(Atributo.retornarAtributoPorCodigo(s, xmlLector.getAtributos()).getNombre());
				}
				
				result.put(knames.toString(), vnames);
			}	
			
			// Mensaje de fin
			JOptionPane.showMessageDialog(this, "Normalizacion finalizada");
						
			// Imprime
			TextoTextarea(PrintResultado(result));
			
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			JOptionPane.showMessageDialog(this, "Error en el proceso de Normalizacion\n["+e.getMessage()+"]");
		}
	}
	
	private String PrintResultado(TreeMap<String,HashSet<String>> result){		
		// Pinta relaciones en pantalla
		System.out.println(result.toString());
		String relaciones = "\n\nRELACIONES OBTENIDAS\n--------------------------------------------------------------------------------------------------------------------------------------------\n";
		int flag = 1;
		for (Map.Entry<String,HashSet<String>> rel : result.entrySet()) {
			relaciones += "RELACION "+String.valueOf(flag)+" => ";
			relaciones += "LLAVE:    "+rel.getKey();
			relaciones += "\tATRIBUTOS:    "+rel.getValue().toString();
			relaciones += "\n--------------------------------------------------------------------------------------------------------------------------------------------\n";
			flag++;
		}
		
		return relaciones;
	}
	
	private String DependenciaToString(DependenciaFuncional[] dep, Atributo[] attr){
		String txt = "{";
		
		for (int i = 0; i < dep.length; i++) {
			txt += " [";
			int f = 0;
			for (Integer a : dep[i].getImplicante()) {
				if(f > 0){
					txt += ",";
				}
				txt += Atributo.retornarAtributoPorCodigo(a, attr).getNombre();
				f++;
			}
			txt += " => ";
			f = 0;
			for (Integer a : dep[i].getImplicado()) {
				if(f > 0){
					txt += ",";
				}
				txt += Atributo.retornarAtributoPorCodigo(a, attr).getNombre();
				f++;
			}
			txt += "] ";
		}
		
		return txt+"}";
	}
	
	private void TextoTextarea(String text){
		txtResultado.setText(txtResultado.getText() + "\n" + text);
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
