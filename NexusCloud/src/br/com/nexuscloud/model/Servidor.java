package br.com.nexuscloud.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.nexuscloud.exception.CapacidadeExcedidaException;
import br.com.nexuscloud.exception.ServidorOfflineException;

public class Servidor {
    private String ip;
    private double ramTotal;
    private double precoPorHora;
    private boolean statusLigado;
    private List<Container> containers = new ArrayList<>();

    public Servidor(String ip, double ramTotal, double precoPorHora) {
        this.ip = ip;
        this.ramTotal = ramTotal;
        this.precoPorHora = precoPorHora;
    }

    public void ligar() { this.statusLigado = true; }
    public void desligar() { this.statusLigado = false; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public double getRamTotal() { return ramTotal; }
    public void setRamTotal(double r) { this.ramTotal = r; }
    public double getPrecoPorHora() { return precoPorHora; }
    public void setPrecoPorHora(double p) { this.precoPorHora = p; }
    public boolean isStatusLigado() { return statusLigado; }
    public List<Container> getContainers() { return containers; }

    public double calcularRamEmUso() {
        return containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    public void alocarContainer(Container c) throws CapacidadeExcedidaException {
        if (!statusLigado) {
            throw new ServidorOfflineException("Erro: Não é possível alocar containers em um servidor desligado.");
        }
        if (calcularRamEmUso() + c.getRamNecessaria() > ramTotal) {
            throw new CapacidadeExcedidaException("Falha Crítica: Memória RAM insuficiente no servidor " + ip + ".");
        }
        containers.add(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(ip, ((Servidor) o).ip);
    }

    @Override
    public int hashCode() { return Objects.hash(ip); }

    @Override
    public String toString() { return "Servidor " + ip; }
}