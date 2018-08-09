import java.util.List;
import java.util.stream.Collectors;

public class CustosConta{
	private EstatisticaCliente estatistica;
	private PontuacaoCliente pontuacao;
    public CustosConta(EstatisticaCliente estatistica, PontuacaoCliente pontuacao){
    	this.estatistica=estatistica;
    	this.pontuacao=pontuacao;
    }

    public double jurosNoMes(int nroConta,int mes,int ano){
    	List<Double> lista = estatistica.saldosMes(nroConta,mes,ano)
                .stream()
                .filter((valor) -> valor < 0 )
                .collect(Collectors.toList());

    	if(lista.size() == 0){
    		throw new RuntimeException("Nenhum elemento na lista!");
    	}
    	double total = lista.stream().mapToDouble((valor)->valor).sum();
    	
    	return total*0.3;
    }

    public double taxaNoMes(int nroConta,int mes, int ano){
    	double pontos = pontuacao.pontuacaoSaldoMedio(nroConta, mes, ano) + pontuacao.pontuacaoSaldoMedioNegativoMes(nroConta, mes, ano)+pontuacao.pontuacaoValorMedioOperacoes(nroConta, mes, ano);
    	if(pontos>20000) {
    		return 0;
    	}
    	if(pontos>10000) {
    		return 100*0.5;
    	}
    	if(pontos>5000) {
    		return 100*0.3;
    	}
    	return 100;
    }
}
