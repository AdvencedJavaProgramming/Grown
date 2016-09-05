package com.Polodz.View;


import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import net.miginfocom.swing.MigLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.Polodz.controller.IMainController;
import com.Polodz.controller.MainController;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;



//@org.springframework.stereotype.Component
public class MainWindow extends javax.swing.JFrame {

	/**
	 * serialVersion
	 */
	private static final long serialVersionUID = 4307777235040180150L;
	@Autowired
	private IMainController mainController;

	/**
	 * Creates new form
	 */
	public MainWindow() {
		setNimbus();
		initComponents();
	}

	public MainWindow(IMainController mainController) {
		this.mainController = mainController;
		setNimbus();
		initComponents();
		this.setCentralText(((MainController) mainController).getLastMtcResponse() + "\n");
	}

	/**
	 * Initialize the form.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("EasyMTC");
		getContentPane().setLayout(new MigLayout("", "[4px:6px:11px][100px:150px:180px,grow][8px:18:25px][grow][61px]",
				"[2px:6px:11px][25px:25:25px][][2px:6px:11px][100px:386px,grow][25px:40:60]"));

		btnNewButton = new JButton("Status");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
	               
			}
		});
		getContentPane().add(btnNewButton, "flowx,cell 3 1");

		txtpnConsole = new JTextPane();
		txtpnConsole.setBackground(SystemColor.controlHighlight);
		txtpnConsole.setEditable(false);
		txtpnConsole.setText("Console");
		getContentPane().add(txtpnConsole, "cell 3 2,grow");

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, "cell 1 4,grow");

		rootNode = new DefaultMutableTreeNode("Network");
		tree = new JTree(rootNode);
		modelTree = (DefaultTreeModel) tree.getModel();
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //tree.addTreeSelectionListener(new TreeListener());
		tree.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	                DefaultMutableTreeNode node = getSelectedItemDefaultMutableTreeNode();
	                Boolean iSuperAdminPrivellages=rootNode.isNodeChild(node)||(node==rootNode);
	                btnNewButton_3.setEnabled(!iSuperAdminPrivellages);
	                btnNewButton_1.setEnabled(!iSuperAdminPrivellages);
	                //selectedElement=node.toString();
	               // node.removeAllChildren();
	                //node.removeFromParent();
	                //setCentralText(selectedElement);
	                //if (node == null) return;
	                //Object nodeInfo = node.getUserObject();
	                if (e.getClickCount() == 2) {
	                	//if 
	                	//rootNode.remove(0);
	                	//node.removeAllChildren();
	                	//node.remove(0);
	                	String reply=((MainController) mainController).getMtcResponse((String)node.getUserObject());
	                	setCentralText(reply);
	                }
	        }
	    });
//		tree.addTreeSelectionListener(new TreeSelectionListener() {
//			public void valueChanged(TreeSelectionEvent e) {
//				selectedElement=e.getPath().getLastPathComponent().toString();
//				setCentralText(selectedElement);
//			}
//		});
//		addToSelectedSubTree("test");
//		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
//		model.insertNodeInto(new DefaultMutableTreeNode("another_child"), root, root.getChildCount());
//		model.insertNodeInto(new DefaultMutableTreeNode("another_child"), root, root.getChildCount());
		
		scrollPane.setViewportView(tree);

		jButton1 = new javax.swing.JButton();

		jButton1.setText("Close");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane_1, "cell 3 4,grow");
		jTextArea1 = new javax.swing.JTextArea();
		jTextArea1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int line = jTextArea1.getLineOfOffset(jTextArea1.getCaretPosition());
						int start = jTextArea1.getLineStartOffset(line);
						int end = jTextArea1.getLineEndOffset(line);
						String text = jTextArea1.getDocument().getText(start, end - (start));
						setCentralText("\n" + ((MainController) mainController).getMtcResponse(text));

					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		scrollPane_1.setViewportView(jTextArea1);
		jTextArea1.setColumns(20);
		jTextArea1.setLineWrap(true);
		jTextArea1.setRows(5);
		jTextArea1.setWrapStyleWord(true);
		getContentPane().add(jButton1, "cell 4 5,alignx left,aligny center");

		btnNewButton_3 = new JButton("Audit");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		getContentPane().add(btnNewButton_3, "cell 3 1");

		btnNewButton_2 = new JButton("Delete Product");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = getSelectedItemDefaultMutableTreeNode();
				DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();
				mainController.deleteMembersProduct(new Long(rootNode.getIndex(nodeParent)),nodeParent.getIndex(node));
				modelTree.removeNodeFromParent(node);
				//rootNode.remove(node);
				//modelTree.nodeChanged(node);//
				//modelTree.reload();
			}
		});
		getContentPane().add(btnNewButton_2, "cell 3 1");

		btnNewButton_1 = new JButton("Return Product");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToSelectedSubTree((String)getSelectedItemDefaultMutableTreeNode().getUserObject(),rootNode.getChildCount()-1);
				modelTree.removeNodeFromParent(getSelectedItemDefaultMutableTreeNode());
				//rootNode.remove(node);
				//modelTree.nodeChanged(node);//
				//modelTree.reload();
			}
		});

		
		getContentPane().add(btnNewButton_1, "cell 3 1");

		pack();
	}// </editor-fold>//GEN-END:initComponents
	private DefaultMutableTreeNode getSelectedItemDefaultMutableTreeNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
	public Integer addToTree(String name) {
		modelTree.insertNodeInto(new DefaultMutableTreeNode(name), rootNode, rootNode.getChildCount());
		return  rootNode.getChildCount()-1;
		//this.addToSelectedSubTree("test");
	}
	
	public void addToSelectedSubTree(String name,int number) {
		DefaultMutableTreeNode test = (DefaultMutableTreeNode) modelTree.getChild(rootNode, number);
		modelTree.insertNodeInto(new DefaultMutableTreeNode(name), test, test.getChildCount());
	}


	public void setCentralText(String text) {

		jTextArea1.setText(jTextArea1.getText() + text);
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		dispose();
	}// GEN-LAST:event_jButton1ActionPerformed

	/**
	 * TestComp
	 * @param args
	 *           
	 */
	private static void setNimbus() {
		/* 
		 * Set the Nimbus 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
	}
	public static void main(String args[]) {
		
		setNimbus();
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainWindow().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JTextArea jTextArea1;
	private JTree tree;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JTextPane txtpnConsole;
	private DefaultTreeModel modelTree;
	private DefaultMutableTreeNode rootNode;
	private String selectedElement;
	

	// End of variables declaration//GEN-END:variables
}