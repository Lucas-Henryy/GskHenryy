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


    public void salvarCargo(Cargo cargo) {
        try {
            cargoService.salvarCargo(cargo);
            System.out.println("Cargo cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro no cadastro");
        } catch (Exception e) {
            System.out.println("Falha ao cadastrar o cargo no sistema.");
          
        }
    }

    public void atualizarCargo(Cargo cargo) {
        try {
            cargoService.atualizarCargo(cargo);
            System.out.println("Cargo atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na atualização");
        } catch (Exception e) {
            System.out.println("Falha ao atualizar o cargo no sistema.");
            
        }
    }

    public List<Cargo> listarCargos() {
        try {
            return cargoService.listarCargos();
        } catch (Exception e) {
            System.out.println("Falha ao listar os cargos cadastrados.");
           
            return null;
        }
    }

    public Cargo buscarPorId(Long id) {
        try {
            return cargoService.buscarPorId(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na busca");
        } catch (Exception e) {
            System.out.println("Falha ao buscar o cargo pelo ID.");
           
        }
        return null;
    }

    public void excluirCargo(Long id) {
        try {
            cargoService.excluirCargo(id);
            System.out.println("Cargo removido com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na exclusão");
        } catch (Exception e) {
            System.out.println("Falha ao excluir o cargo do sistema.");
           
        }
    }
}

