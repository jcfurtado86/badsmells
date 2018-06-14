package reconhecedor;

import java.io.File;
import java.nio.file.Paths;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class ClonarRepositorio {
    
    public static boolean clonar(String repoUrl, String nomeProjeto){
        excluir(new File(System.getProperty("user.dir")+"\\Repositorio\\"+nomeProjeto));
        String cloneDirectoryPath = System.getProperty("user.dir")+"\\Repositorio\\"+nomeProjeto;
        try {
            System.out.println("Clonando");
            Git.cloneRepository().setURI(repoUrl).setDirectory(Paths.get(cloneDirectoryPath).toFile()).call();
            System.out.println("Clonado com sucesso");
                        
            return true;
        } catch (GitAPIException e) {
            System.out.println("Falha ao clonar: "+e.getMessage());
            
            try {
                String user = JOptionPane.showInputDialog(null, "Usuário/Email");
                
		JPasswordField password = new JPasswordField(10);
		password.setEchoChar('*'); 
		JLabel rotulo = new JLabel("Senha:");
		JPanel entUsuario = new JPanel();
		entUsuario.add(rotulo);
		entUsuario.add(password);
		JOptionPane.showMessageDialog(null, entUsuario, "Acesso restrito", JOptionPane.PLAIN_MESSAGE);
		String senha = password.getText();
                
                //String password = JOptionPane.showInputDialog(null, "Senha");
                
                System.out.println("Clonando Com Credenciais");
                CloneCommand cloneCommand = Git.cloneRepository();
                cloneCommand.setURI(repoUrl);
                cloneCommand.setDirectory(Paths.get(cloneDirectoryPath).toFile());
                cloneCommand.setCredentialsProvider( new UsernamePasswordCredentialsProvider(user, senha));
                cloneCommand.call();
                System.out.println("Clonado com sucesso");
                
                return true;
            } catch (GitAPIException e2) {
                System.out.println("Falha ao clonar: "+e2.getMessage());
                return false;
            }
            
        }   
    }
    
    public static void excluir(File arq){
        if(arq.isDirectory()){
            File[] arquivos = arq.listFiles();
            for(int i=0;i<arquivos.length;i++){
               excluir(arquivos[i]);
            }
        }
        arq.delete();
    }
    
      
}
