//Autor: Robert Reininger, c1210537020

package server;
import java.io.IOException;

import javax.swing.SwingUtilities;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class ServerGUI {

	protected Shell shell;
	private static Text txtPort;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerGUI window = new ServerGUI();
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
		shell.setText("Server");
		
		Button btnStartServer = new Button(shell, SWT.NONE);
		btnStartServer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] args1 = new String[] {txtPort.getText()};
				startServer(args1);
			}
		});
		btnStartServer.setBounds(10, 38, 108, 28);
		btnStartServer.setText("Start Server");
		
		Label lblPort = new Label(shell, SWT.NONE);
		lblPort.setBounds(10, 10, 36, 22);
		lblPort.setText("Port:");
		
		txtPort = new Text(shell, SWT.BORDER);
		txtPort.setBounds(54, 10, 64, 19);
	}
	
	
	private static void startServer(final String[] port) {
	    SwingUtilities.invokeLater(new Runnable() { //invokeLater - Client is able to display things on Server
	        @Override
	        public void run() {
	        	
	            try {
					server.Server.main(port);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	}
}
