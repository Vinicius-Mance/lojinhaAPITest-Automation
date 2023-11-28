package modulos.produto;


import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ProdutoPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes APIRest módulo de Produto ->")
public class ProdutoTest {

    private String token;
    private ProdutoPojo produto;

    @BeforeEach
    public void beforeEach() {
//                configuração dos dados da lojinha
        baseURI = "http://165.227.93.41";
//            port = 8080;
        basePath = "/lojinha";


        this.token = given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuario())
            .when()
                .post("/v2/login")
            .then()
                .extract()
                .path("data.token");

//        criação de um novo produto completamente válido
        this.produto = ProdutoDataFactory.criarProduto();

    }

    @Test
    @DisplayName("Validar limites proibidos do valor do produto ->")
    public void testValidarLimitesProibidosValorProduto() {

//        lista com todos os valores inválidos de um produto
        List<Double> valoresInvalidos = new ArrayList<>();

        valoresInvalidos.add(-0.01);
        valoresInvalidos.add(0.00);
        valoresInvalidos.add(7000.01);

        for (double valor : valoresInvalidos) {
            this.produto.setProdutoValor(valor);
//      Tentar inserir um produto com valores incorretos e retornar erro
//      status code retornado: 422
            given()
                    .contentType(ContentType.JSON)
                    .header("token",this.token)
                    .body(this.produto)
                .when()
                    .post("/v2/produtos")
                .then()
                    .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
        }


    }

}
