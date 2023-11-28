package dataFactory;

import pojo.ComponentePojo;
import pojo.ProdutoPojo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {

    public static ProdutoPojo criarProduto() {
        List<String> cores = new ArrayList<>();
        cores.add("preto");
        cores.add("branco");

        ComponentePojo componente1 = new ComponentePojo("Cabo p1-p2",2);
        ComponentePojo componente2 = new ComponentePojo("Cabo alimentação micro-usb",1);

        List<ComponentePojo> componentes = new ArrayList<>();
        componentes.add(componente1);
        componentes.add(componente2);

        ProdutoPojo produto = new ProdutoPojo("JBL Tune 650 BTNC",700.00,cores,"",componentes);
        return produto;
    }
}
