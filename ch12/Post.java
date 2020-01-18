package ch12;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A small graphical application that demonstrates use of the
 * HTTP POST mechanism. Provide a POST-able URL to the command line
 * and use the "Post" button to send sample name and password
 * data to the URL.
 * 
 * See the servlet section of this chapter for the ShowParameters
 * example that can serve (ha!) as the receiving (server) side.
 */
public class Post extends JPanel implements ActionListener {
  JTextField nameField;
  JPasswordField passwordField;
  String postURL;

  GridBagConstraints constraints = new GridBagConstraints(  );
  
  void addGB( Component component, int x, int y ) {
    constraints.gridx = x;  constraints.gridy = y;
    add ( component, constraints );
  }

  public Post( String postURL ) {
	  
    this.postURL = postURL;  
	  
    setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
    JButton postButton = new JButton("Post");
    postButton.addActionListener( this );
    setLayout( new GridBagLayout(  ) );
    constraints.fill = GridBagConstraints.HORIZONTAL;
    addGB( new JLabel("Name ", JLabel.TRAILING), 0, 0 );
    addGB( nameField = new JTextField(20), 1, 0 );
    addGB( new JLabel("Password ", JLabel.TRAILING), 0, 1 );
    addGB( passwordField = new JPasswordField(20), 1, 1 );
    constraints.fill = GridBagConstraints.NONE;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.EAST;
    addGB( postButton, 1, 2 );
  }

  public void actionPerformed(ActionEvent e) {
    postData(  );
  }

  protected void postData(  ) {
    StringBuilder sb = new StringBuilder();
    String pw = new String(passwordField.getPassword());
    try {
      sb.append( URLEncoder.encode("Name", "UTF-8") + "=" );
      sb.append( URLEncoder.encode(nameField.getText(), "UTF-8") );
      sb.append( "&" + URLEncoder.encode("Password", "UTF-8") + "=" );
      sb.append( URLEncoder.encode(pw, "UTF-8") );
    } catch (UnsupportedEncodingException uee) {
      System.out.println(uee);
    }
    String formData = sb.toString(  );

    try {
      URL url = new URL( postURL );
      HttpURLConnection urlcon =
          (HttpURLConnection) url.openConnection(  );
      urlcon.setRequestMethod("POST");
      urlcon.setRequestProperty("Content-type",
          "application/x-www-form-urlencoded");
      urlcon.setDoOutput(true);
      urlcon.setDoInput(true);
      PrintWriter pout = new PrintWriter( new OutputStreamWriter(
          urlcon.getOutputStream(  ), "8859_1"), true );
      pout.print( formData );
      pout.flush(  );

      // Did the post succeed?
      if ( urlcon.getResponseCode() == HttpURLConnection.HTTP_OK )
        System.out.println("Posted ok!");
      else {
        System.out.println("Bad post...");
        return;
      }
      // Hooray! Go ahead and read the results...
      //InputStream in = urlcon.getInputStream(  );
      // ...

    } catch (MalformedURLException e) {
      System.out.println(e);     // bad postURL
    } catch (IOException e2) {
      System.out.println(e2);    // I/O error
    }
  }

  public static void main( String [] args ) {
    if (args.length != 1) {
      System.err.println("Must specify URL on command line. Exiting.");
      System.exit(1);
    }
    JFrame frame = new JFrame("SimplePost");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add( new Post(args[0]), "Center" );
    frame.pack();
    frame.setVisible(true);
  }
}
