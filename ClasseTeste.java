package PacoteTeste;

import a1.subpacote.outrosubpacote.Classe;
import a1.subpacote.outrosubpacote.Classe2;

public abstract class ClasseTeste {
	
	private ImageIcon iconePag = new ImageIcon();
	private Icon volatil;
	private MouseListener ml, ml2, ml3, ml4, ml5;
	private ArrayList<Pagina> papeis;
	private static String fileTemp;
	
	protected String a;
	double b;
	public int c;
	
	public ClasseTeste(){
		
	}
	
	public ClasseTeste(String a){
	}
	
	public ClasseTeste(String a,double b){
		this.a = a;
		this.b = b;
	}
	
	protected abstract void metodoAbstratoTeste(String a1);
	
	public void metodoTeste(String sei){
		System.out.println("oi");
				
		this.cor = cores.get(aux);
		
		this.maximoDeQuestoes = numMaxQuest;

		try{
			janela = new JFrame();

			mouse();
		}catch(Exception e){
			e.printTrackTracer();
		}

		Integer valorTamanho = 0;
		
		tam.getText().isEmpty().ji().jh().fssaa();
		
		
		
		
		Classe objeto = new Classe(dd);

		objeto = new Classe();

		metodo(fff);

		objeto = metodo();

		Classe objeto = metodo(dd);

		objeto = valor;

		objeto.variavel = metodo();

		objeto.metodo(); 
		objeto.metodo1().metodo2().metodo3();

		Classe objeto = objeto.metodo();
		Classe objeto = objeto.metodo1("rgb").metodo2().metodo3();

		objeto = objeto.metodo1("parametro1").metodo2().metodo3();
		
		return "Ainda sei";
	}
	
}