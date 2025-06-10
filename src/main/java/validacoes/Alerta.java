
package validacoes;

import telas.TelaCliente;
import telas.TelaErro;

public class Alerta {
    public static void Erro(String mensagem, String titulo){
        new TelaErro(null, true, titulo, mensagem).setVisible(true);
    }
    
    public static void Cliente(String titulo, String cpf, String tel){
        new TelaCliente(null, true, titulo, cpf, tel).setVisible(true);
    }
    
}
