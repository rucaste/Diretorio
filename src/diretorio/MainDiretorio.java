package diretorio;


public class MainDiretorio {

	public static void main(String[] args) {

		if(args.length == 0){
			System.out.println("É necessário indicar o porto de acesso ao Diretorio como argumento");
			System.exit(1);
		}
		else if(args.length > 1){
			System.out.println("Os argumentos a seguir ao primeiro foram ignorados");
		}
		Diretorio diretorio = new Diretorio(Integer.parseInt(args[0]));
		diretorio.startDiretorio();
	}

}
