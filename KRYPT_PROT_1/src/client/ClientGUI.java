//Autor: Robert Reininger, c1210537020

package client;

import java.io.IOException;

import javax.swing.SwingUtilities;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class ClientGUI {

	protected Shell shell;
	private Text txtPort;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClientGUI window = new ClientGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Client");
		
		Button btnStartClient = new Button(shell, SWT.NONE);
		btnStartClient.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] args1 = new String[] {txtPort.getText()};
				startServer(args1);
			}
		});
		btnStartClient.setBounds(10, 38, 108, 28);
		btnStartClient.setText("Start Client");
		
		txtPort = new Text(shell, SWT.BORDER);
		txtPort.setBounds(40, 10, 64, 19);
		
		Label lblPort = new Label(shell, SWT.NONE);
		lblPort.setBounds(10, 13, 59, 14);
		lblPort.setText("Port:");

	}
	
	
	private static void startServer(final String[] port) {
	    SwingUtilities.invokeLater(new Runnable() { //invokeLater - Client is able to display things on Server
	        @Override
	        public void run() {
	        	
	            try {
					client.Client.main(port);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	}
	
}
