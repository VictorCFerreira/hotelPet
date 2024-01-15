package com.hotelPet.controller;

import com.hotelPet.model.CheckInOut;
import com.hotelPet.model.Pessoa;
import com.hotelPet.model.Pet;
import com.hotelPet.repository.CheckInOutRepository;
import com.hotelPet.repository.PessoaRepository;
import com.hotelPet.repository.PetRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChecksController {

    @Autowired
    private CheckInOutRepository repository;

    @Autowired
    private PetRepository petRepository;

    @PostMapping("/checks/salvar")
    public String salvar(@ModelAttribute CheckInOut check) {
       
        repository.salvar(check);
        return "redirect:/";
    }

    @GetMapping("/checks/salvar/{idPet}")
    public String novoCheck(@PathVariable String idPet) {
        Pet pet = petRepository.obterId(idPet);
        CheckInOut check = new CheckInOut();
        
        check.setIdPet(pet.getId());
        check.setNomePet(pet.getNome());
        check.setDataCheck(new Date());

        if (pet.getHospedagemAtual().equals("Nao hospedado")) {
            String novaHospedagem = switch (pet.getTipo()) {
                case "Cachorro" -> "1o Andar";
                case "Gato" -> "2o Andar";
                case "Passaro" -> "3o Andar";
                default -> "Nao hospedado";
            };
            pet.setHospedagemAtual(novaHospedagem);
            check.setTipo("Check in");
            
        }else{
            pet.setHospedagemAtual("Nao hospedado");
            check.setTipo("Check out");
        }
        
        petRepository.salvar(pet);
        repository.salvar(check);
        return "redirect:/pets/listar";
    }

    @GetMapping("/checks/listar")
    public String listar(Model model) {
        List<CheckInOut> checks = repository.listar();
        model.addAttribute("checks", checks);
        return "checks/listar";
    }

    @GetMapping("/checks/excluir/{id}")
    public String excluir(@PathVariable String id) {
        repository.excluir(id);
        return "redirect:/checks/listar";
    }

}
