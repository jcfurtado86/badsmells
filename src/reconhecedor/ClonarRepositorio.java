package reconhecedor;

import java.io.File;
import java.nio.file.Paths;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class ClonarRepositorio {
    
    public static void main(String[] args) {
        excluir(new File(System.getProperty("user.dir")+"\\Repositorio"));
        String repoUrl = "https://github.com/Matheuscsceil/Compilador.git";
        String cloneDirectoryPath = System.getProperty("user.dir")+"\\Repositorio";
        try {
            System.out.println("Clonando");
            Git.cloneRepository().setURI(repoUrl).setDirectory(Paths.get(cloneDirectoryPath).toFile()).call();
            System.out.println("Clonado com sucesso");
        } catch (GitAPIException e) {
            e.printStackTrace();
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
