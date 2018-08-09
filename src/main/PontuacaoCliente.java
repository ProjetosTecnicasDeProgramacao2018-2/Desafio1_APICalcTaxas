
public class PontuacaoCliente {
	private EstatisticaCliente estatistica;
	public PontuacaoCliente(EstatisticaCliente estatistica){
		this.estatistica=estatistica;
    }

    public double pontuacaoSaldoMedio(int nroConta,int mes,int ano){
    	return (estatistica.saldoMedioMes(nroConta, mes, ano)%1000)*1000;
    }

    public double pontuacaoValorMedioOperacoes(int nroConta,int mes,int ano){
        if(estatistica.valorMedioOperacoes(nroConta, mes, ano)>300) {
        	return (estatistica.valorMedioOperacoes(nroConta, mes, ano)%100)*100;
        }
        return 0;
    }

    public double pontuacaoSaldoMedioNegativoMes(int nroConta,int mes,int ano){
        if(estatistica.saldoMedioNegativoMes(nroConta, mes, ano)>500) {
        	return ((estatistica.valorMedioOperacoes(nroConta, mes, ano)%100)*100)/2;
        }
        return 0;
    }

}
