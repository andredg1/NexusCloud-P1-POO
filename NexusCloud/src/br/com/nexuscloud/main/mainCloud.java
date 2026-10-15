package br.com.nexuscloud.main;

import br.com.nexuscloud.exception.CapacidadeExcedidaException;
import br.com.nexuscloud.model.Cliente;
import br.com.nexuscloud.model.Container;
import br.com.nexuscloud.model.Servidor;
import br.com.nexuscloud.services.CloudManager;

public class mainCloud {
    public static void main(String[] args) throws CapacidadeExcedidaException {
        CloudManager manager = new CloudManager();
        Cliente cliente = new Cliente("111.222.333-44", "Wayne Enterprises");
        Servidor servidor = new Servidor("192.168.0.1", 16.0, 5.50);
        manager.registrarCliente(cliente);
        manager.adicionarServidor(cliente, servidor);
        Container c1 = new Container("C-01", 10.0);
        Container c2 = new Container("C-02", 8.0);

        servidor.ligar();
        servidor.alocarContainer(c1);

        try {
            servidor.alocarContainer(c2);
        } catch (CapacidadeExcedidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Fatura ativa: R$ " + manager.calcularFaturaAtiva(cliente));
    }
}