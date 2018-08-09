import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import persistencia.*;

public class EstatisticaCliente {
	private Persistencia persistencia;
	private List<Operacao> listaOperacoes;
	private Map<Integer,Conta> contas;

	public EstatisticaCliente(Persistencia persistencia){
        this.persistencia = persistencia;
        
        this.listaOperacoes = this.persistencia.loadOperacoes();
        this.contas = this.persistencia.loadContas();
    }
    
    public double saldoMedioMes(int nroConta,int mes,int ano){
        List<Double> lista = this.saldosMes(nroConta,mes,ano);

        if(lista.size() == 0){
            throw new RuntimeException("Nenhum elemento na lista!");
        }

        double total = lista.stream().mapToDouble((valor)->valor).sum();

        return total / lista.size();
    }

    public double saldoMedioNegativoMes(int nroConta,int mes,int ano){
        List<Double> lista = this.saldosMes(nroConta,mes,ano)
                                .stream()
                                .filter((valor) -> valor < 0 )
                                .collect(Collectors.toList());

        if(lista.size() == 0){
            throw new RuntimeException("Nenhum elemento na lista!");
        }

        double total = lista.stream().mapToDouble((valor)->valor).sum();

        return total / lista.size();
    }


      
    public double valorMedioOperacoes(int nroConta,int mes,int ano){
        double somatorio = 0;
        int count = 0;
        
        List<Operacao> lista = this.listaOperacoes
                                        .stream()
                                        .filter( (op) -> 
                                            (op.getMes() == mes && op.getAno() == ano && op.getNumeroConta() == nroConta) 
                                        ).collect(Collectors.toList());
        if(lista.size() == 0) {
            throw new RuntimeException("Nenhum elemento na lista!");
        }
        count = lista.size();
        
        for(Operacao op : lista) {
            somatorio += op.getValorOperacao();
        }
        
        return somatorio / count;
    }

    public List<Double> saldosMes(int nroConta,int mes,int ano){
        List<Double> newList = new LinkedList<>();

        List<Operacao> lista = this.listaOperacoes
                                        .stream()
                                        .filter( (op) -> 
                                            (op.getMes() == mes && op.getAno() == ano && op.getNumeroConta() == nroConta) 
                                        ).collect(Collectors.toList());
        if(lista.size() == 0) {
            throw new RuntimeException("Nenhum elemento na lista!");
        }

        Conta conta = this.contas.get(nroConta);

        if(conta == null){
            throw new RuntimeException("Conta de usuário não encontrada!");
        }

        double saldoAntigo = conta.getSaldo();

        for(Operacao op : lista) {
            double valorOperacao = op.getValorOperacao();
            if(op.getTipoOperacao() == op.CREDITO){
                conta.deposito(valorOperacao);
            }else{
                conta.retirada(valorOperacao);
            }

            newList.add(conta.getSaldo());
        }

        /*
            ##############################################################
            ##  CODIGO PARA A CONTA NÃO SER ALTERADA APÓS OS CÁLCULOS.  ##
            ##############################################################
        */
        conta = new Conta(conta.getNumero(), conta.getCorrentista(), saldoAntigo);
        this.contas.remove(nroConta);
        this.contas.put(conta.getNumero(), conta);

        return newList;
    }

}
