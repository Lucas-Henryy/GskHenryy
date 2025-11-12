package controllers;

import classes.Cargo;
import services.CargoService;
import validacoes.Alerta;
import java.util.List;

public class CargoController {

    private final CargoService cargoService;

    public CargoController() {
        this.cargoService = new CargoService();
    }

    // ======================= MÉTODOS DE CONTROLE =======================

    public void salvarCargo(Cargo cargo) {
        try {
            cargoService.salvarCargo(cargo);
            Alerta.Erro("Cadastro concluído!", "Cargo cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro no cadastro", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao cadastrar o cargo no sistema.");
          
        }
    }

    public void atualizarCargo(Cargo cargo) {
        try {
            cargoService.atualizarCargo(cargo);
            Alerta.Erro("Atualização concluída!", "Cargo atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na atualização", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao atualizar o cargo no sistema.");
            
        }
    }

    public List<Cargo> listarCargos() {
        try {
            return cargoService.listarCargos();
        } catch (Exception e) {
            Alerta.Erro("Erro na listagem", "Falha ao listar os cargos cadastrados.");
           
            return null;
        }
    }

    public Cargo buscarPorId(Long id) {
        try {
            return cargoService.buscarPorId(id);
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na busca", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao buscar o cargo pelo ID.");
           
        }
        return null;
    }

    public void excluirCargo(Long id) {
        try {
            cargoService.excluirCargo(id);
            Alerta.Erro("Exclusão concluída!", "Cargo removido com sucesso!");
        } catch (IllegalArgumentException e) {
            Alerta.Erro("Erro na exclusão", e.getMessage());
        } catch (Exception e) {
            Alerta.Erro("Erro inesperado", "Falha ao excluir o cargo do sistema.");
           
        }
    }
}

