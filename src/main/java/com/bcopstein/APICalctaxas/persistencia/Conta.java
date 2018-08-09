package com.bcopstein.APICalctaxas.persistencia;

public class Conta {
	private int numero;
	private String correntista;
	private double saldo;

	public Conta(int umNumero, String umNome) {
		numero = umNumero;
		correntista = umNome;
		saldo = 0.0;
	}

	public Conta(int umNumero, String umNome,double umSaldo) {
		numero = umNumero;
		correntista = umNome;
		saldo = umSaldo;
	}

	public double getSaldo() {
		return saldo;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getCorrentista() {
		return correntista;
	}

	public void deposito(double valor) {
        if (valor <= 0){
            throw new IllegalArgumentException("Valor inválido para deposito");
        }
		saldo += valor;
	}

	public void retirada(double valor) {
        if (valor <= 0){
            throw new IllegalArgumentException("Valor inválido para retirada");
        }
		saldo = saldo - valor;
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", correntista=" + correntista + ", saldo=" + saldo + "]";
	}
}
