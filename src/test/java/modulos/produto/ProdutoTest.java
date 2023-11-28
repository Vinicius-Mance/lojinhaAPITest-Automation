package modulos.produto;


import dataFactory.ProdutoDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

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

        UsuarioPojo usuario = new UsuarioPojo("vinny","senha123");
//        obter token do usuario


        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuario)
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
        
//        alterando produto para que tenha valor inválido
        this.produto.setProdutoValor(0.00);

//        Tentar inserir um produto com valor 0.00 e validar que um erro foi apresentado
//        status code retornado: 422
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
