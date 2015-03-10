package pt.c01interfaces.s01knowledge.s02app.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;
	
	public Enquirer()
	{
	}
	
	
	@Override
	public void connect(IResponder responder)
	{
		ArrayList<String> listaNomes;
		HashMap<String, String> respondidos;
        IBaseConhecimento bc = new BaseConhecimento();
		
        listaNomes = new ArrayList<String>(Arrays.asList(bc.listaNomes()));
        Collections.shuffle(listaNomes);
        
        respondidos = new HashMap<String, String>();
        
        boolean acertei = false;
        boolean procurando = true;
        for( int i = 0; i < listaNomes.size() && procurando; i++ ){
	        
			obj = bc.recuperaObjeto(listaNomes.get(i));
			
			boolean animalEsperado = true;
			IDeclaracao decl = obj.primeira();
			while (decl != null && animalEsperado) {
				String pergunta = decl.getPropriedade();
				String respostaEsperada = decl.getValor();
				
				boolean foiEsperado = false;
				String resposta;
				if(respondidos.containsKey(pergunta))
					foiEsperado = respondidos.get(pergunta).equals(respostaEsperada);
				else {
					resposta = responder.ask(pergunta);
					foiEsperado = resposta.equalsIgnoreCase(respostaEsperada);
					respondidos.put(pergunta, resposta);
				}
				
				if (foiEsperado)
					decl = obj.proxima();
				else
					animalEsperado = false;
			}
			
			if( animalEsperado) {
				acertei = responder.finalAnswer(listaNomes.get(i));
				procurando = false;
			}
        }
        
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
