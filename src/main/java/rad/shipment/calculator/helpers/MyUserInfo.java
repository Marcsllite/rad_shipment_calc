package rad.shipment.calculator.helpers;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 *  @Author JCraft.com (http://www.jcraft.com/jsch/examples/ScpFrom.java.html)
 */
public class MyUserInfo implements UserInfo, UIKeyboardInteractive {
    final String host, domain;

    /**
     * Constructs a UserInfo object with given host and domain
     *
     * @param host hostname of connection
     * @param domain domain name of current network
     */
    public MyUserInfo(String host, String domain) {
        this.host = host.toLowerCase();
        this.domain = domain.toLowerCase();
    }

    public String getPassword(){ return passwd; }
    public boolean promptYesNo(String str){
        Object[] options={ "yes", "no" };

        // automatically accepts devices on domain's network
        // if not on domain network or domain network not provided
        // asks user if they want to accept
        int foo = domain == null? 1 : Objects.requireNonNull(host).contains(domain)? 0 : 1;
        if(foo == 1){
            // Asks user if they accept unknown key, clicking no ends connection
            foo = JOptionPane.showOptionDialog(null,
                    str,
                    "Warning",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
        }
        return foo == 0;
    }

    String passwd;

    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return false; }
    public boolean promptPassword(String message){ return false;}
    public void showMessage(String message){ }
    final GridBagConstraints gbc =
            new GridBagConstraints(0,0,1,1,1,1,
                    GridBagConstraints.NORTHWEST,
                    GridBagConstraints.NONE,
                    new Insets(0,0,0,0),0,0);

    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
        Container panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;
        panel.add(new JLabel(instruction), gbc);
        gbc.gridy++;

        gbc.gridwidth = GridBagConstraints.RELATIVE;

        JTextField[] texts=new JTextField[prompt.length];
        for(int i=0; i<prompt.length; i++){
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.weightx = 1;
            panel.add(new JLabel(prompt[i]),gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weighty = 1;
            if(echo[i]){
                texts[i]=new JTextField(20);
            }
            else{
                texts[i]=new JPasswordField(20);
            }
            panel.add(texts[i], gbc);
            gbc.gridy++;
        }

        if(JOptionPane.showConfirmDialog(null, panel,
                destination + ": " + name,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE)
                ==JOptionPane.OK_OPTION){
            String[] response=new String[prompt.length];
            for(int i=0; i<prompt.length; i++){
                response[i]=texts[i].getText();
            }
            return response;
        }
        else{
            return null;  // cancel
        }
    }
}
