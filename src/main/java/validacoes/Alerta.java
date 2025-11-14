
package validacoes;

import telas.TelaCliente;
import telas.TelaErro;
import telas.TelaSucesso;

public class Alerta {
    public static void Erro(String mensagem, String titulo){
        new TelaErro(null, true, titulo, mensagem).setVisible(true);
    }
    
    public static void Cliente(String titulo, String cpf, String tel){
        new TelaCliente(null, true, titulo, cpf, tel).setVisible(true);
    }

      public static void Sucesso(String titulo, String mensagem) {
        new TelaSucesso(null, true, titulo, mensagem).setVisible(true);
    }
    
}
