package rad.shipment.calculator.helpers;

import com.jcraft.jsch.*;
import rad.shipment.calculator.gui.Main;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SCPConnection {
    // Declaring variables
    private static final Logger LOGR = Logger.getLogger(SCPConnection.class.getName());  // getting logger
    private final JSch CHANNEL;
    private final String USER;
    private final String HOSTNAME;
    private final int PORT = Main.getInt("port");
    private final String PASSWORD;
    private Session connection;
    private final int TIMEOUT = Main.getInt("timeout");
    private OutputStream out;
    private InputStream in;

    public SCPConnection(String hostname, String user, String password){
        HOSTNAME = hostname;
        USER = user;
        PASSWORD = password;
        CHANNEL = new JSch();
    }

    /**
     * Function to start connection
     *
     * @param throwErr if true, connect will not catch any errors it encounters
     *                 if false, connect will try and catch errors
     *
     * @return null if successful
     *         or the error message produced on error
     */
    private String connect(boolean throwErr) throws JSchException {
        if(throwErr){
            if(Network.ping(HOSTNAME)) {
                connection = CHANNEL.getSession(USER, HOSTNAME, PORT);
                connection.setPassword(PASSWORD);
                UserInfo ui = new MyUserInfo(HOSTNAME, Main.getString("domain"));
                connection.setUserInfo(ui);
                connection.connect(TIMEOUT);
                LOGR.info("Connected to " + HOSTNAME + " within timeout");
                return null;
            } else throw new RuntimeException("PING FAILED");
        } else {
            try {
                if (Network.ping(HOSTNAME)) {
                    connection = CHANNEL.getSession(USER, HOSTNAME, PORT);
                    connection.setPassword(PASSWORD);
                    UserInfo ui = new MyUserInfo(HOSTNAME, Main.getString("domain"));
                    connection.setUserInfo(ui);
                    connection.connect(TIMEOUT);
                    LOGR.info("Connected to " + HOSTNAME + " within timeout");
                } else throw new RuntimeException("PING FAILED");
            } catch (JSchException e) {
                LOGR.log(Level.WARNING, "Failed to start scp session with " + HOSTNAME, e);
                return e.getMessage();
            }
            return null;
        }
    }

    /**
     * Overloaded function of {@link #connect(boolean)}
     * where throwError = false
     */
    public String connect() {
        try { return connect(false);
        } catch (JSchException e) { return e.getMessage(); }
    }
    
    /**
     * Function to copy the file located at {@code fileToCopyPath} to
     * {@code destinationPath}
     * @Author JCraft.com (http://www.jcraft.com/jsch/examples/ScpFrom.java.html)
     * 
     * @param fileToCopyPath the path of the remote file to be copied
     * @param destinationPath the path to copy the file to
     */
    public void copyToLocal(String fileToCopyPath, String destinationPath){
        FileOutputStream fos = null ;

        String prefix=null;
        if(new File(destinationPath).isDirectory()){
            prefix=destinationPath+File.separator;
        }
        
        try{
            // exec 'scp -f fileToCopyPath' remotely
            fileToCopyPath = fileToCopyPath.replace("'", "'\"'\"'");
            fileToCopyPath = "'" + fileToCopyPath + "'";
            String command = "scp -f " + fileToCopyPath;
            if(!connection.isConnected()) connect();
            Channel channel = connection.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // get I/O streams for remote scp
            out = channel.getOutputStream();
            in = channel.getInputStream();

            channel.connect();

            byte[] buf=new byte[1024];

            // send '\0'
            buf[0]=0; out.write(buf, 0, 1); out.flush();

            while(true){
                int c=checkAck(in);
                if(c!='C'){
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize=0L;
                while(true){
                    if(in.read(buf, 0, 1)<0){
                        // error
                        break;
                    }
                    if(buf[0]==' ')break;
                    filesize=filesize*10L+(long)(buf[0]-'0');
                }

                String file;
                for(int i=0;;i++){
                    in.read(buf, i, 1);
                    if(buf[i]==(byte)0x0a){
                        file=new String(buf, 0, i);
                        break;
                    }
                }

                // send '\0'
                buf[0]=0; out.write(buf, 0, 1); out.flush();

                // read a content of destinationPath
                fos=new FileOutputStream(prefix==null ? destinationPath : prefix+file);
                int foo;
                while(true){
                    if(buf.length<filesize) foo=buf.length;
                    else foo=(int)filesize;
                    foo=in.read(buf, 0, foo);
                    if(foo<0){
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize-=foo;
                    if(filesize==0L) break;
                }
                fos.close();
                fos=null;

                if(checkAck(in)!=0) return;

                // send '\0'
                buf[0]=0; out.write(buf, 0, 1); out.flush();
            }

            connection.disconnect();
        } catch(Exception e){
            LOGR.log(Level.SEVERE,"Failed to copy file. Error: ", e);
        } finally {
            try{if(fos!=null)fos.close();}catch(Exception ee){
                LOGR.log(Level.WARNING,"Failed to close fileOutputStream. Error: ", ee);
            }
        }
    }

    /**
     * Function to copy the file located at {@code fileToCopyPath} to
     * {@code destinationPath}
     * @Author JCraft.com (http://www.jcraft.com/jsch/examples/ScpTo.java.html)
     *
     * @param fileToCopyPath the path of the local file to be copied
     * @param destinationPath the path to copy the file to on the remote device
     */
    public void copyToRemote(String fileToCopyPath, String destinationPath){
        FileInputStream fis = null;

        try {
            // exec 'scp -t destinationPath' remotely
            destinationPath = destinationPath.replace("'", "'\"'\"'");
            destinationPath = "'"+destinationPath+"'";
            String command = "scp " + "-p -t " + destinationPath;
            if(!connection.isConnected()) connect();
            Channel channel = connection.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // get I/O streams for remote scp
            out = channel.getOutputStream();
            in = channel.getInputStream();

            channel.connect();

            if(checkAck(in)!=0) return;

            File _fileToCopyPath = new File(fileToCopyPath);

            command = "T" + (_fileToCopyPath.lastModified()/1000) +" 0";
            // The access time should be sent here,
            // but it is not accessible with JavaAPI ;-<
            command += (" " + (_fileToCopyPath.lastModified()/1000) + " 0\n");
            out.write(command.getBytes()); out.flush();

            if(checkAck(in) != 0) return;

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize=_fileToCopyPath.length();
            command="C0644 "+filesize+" ";
            if(fileToCopyPath.lastIndexOf('/')>0){
                command+=fileToCopyPath.substring(fileToCopyPath.lastIndexOf('/')+1);
            }
            else{
                command+=fileToCopyPath;
            }
            command+="\n";
            out.write(command.getBytes()); out.flush();
            if(checkAck(in)!=0) return;

            // send a content of fileToCopyPath
            fis=new FileInputStream(fileToCopyPath);
            byte[] buf=new byte[1024];
            while(true) {
                int len = fis.read(buf, 0, buf.length);
                if(len <= 0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis=null;
            // send '\0'
            buf[0] = 0; out.write(buf, 0, 1); out.flush();
            if(checkAck(in) != 0) return;
            out.close();

            channel.disconnect();
            connection.disconnect();
        }
        catch(Exception e){
            LOGR.log(Level.SEVERE, "Failed to copy file. Error: ", e);
        }  finally {
            try{if(fis!=null)fis.close();}catch(Exception ee){
                LOGR.log(Level.WARNING,"Failed to close fileInputStream. Error: ", ee);
            }
        }
    }

    /**
     *  @Author JCraft.com (http://www.jcraft.com/jsch/examples/ScpFrom.java.html)
     */
    private int checkAck(InputStream in) throws IOException{
        int b=in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if(b==0) return b;
        if(b==-1) return b;

        if(b==1 || b==2){
            StringBuilder sb=new StringBuilder();
            int c;
            do {
                c=in.read();
                sb.append((char)c);
            }
            while(c!='\n');
            if(b==1){ // error
                System.out.print(sb.toString());
            }
            if(b==2){ // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

    /**
     * Function to close the connection
     */
    public void close() { connection.disconnect(); }
}
