package reconhecedor;

import java.io.File;
import java.nio.file.Paths;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

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
            return false;
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
