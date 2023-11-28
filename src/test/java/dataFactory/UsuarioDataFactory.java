package dataFactory;

import pojo.UsuarioPojo;

public class UsuarioDataFactory {

    public static UsuarioPojo criarUsuario(){
        UsuarioPojo usuario = new UsuarioPojo("vinny","senha123");
        return usuario;
    }

}
