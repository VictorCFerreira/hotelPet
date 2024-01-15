package com.hotelPet.controller;

import com.hotelPet.model.Pessoa;
import com.hotelPet.model.Pet;
import com.hotelPet.repository.PessoaRepository;
import com.hotelPet.repository.PetRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetsController {

    @Autowired
    private PetRepository repository;
    @Autowired
    private PessoaRepository pessoasRepository;

    @GetMapping("/pets/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("pet", new Pet());
        model.addAttribute("tutores", pessoasRepository.listar());
        return "pets/cadastrar";
    }

    @PostMapping("/pets/salvar")
    public String salvar(@ModelAttribute Pet pet) {
        pet.setHospedagemAtual("Nao hospedado");
            repository.salvar(pet);
            return "redirect:/";
    }

    @GetMapping("/pets/listar")
    public String listar(Model model) {
        List<Pet> pets = repository.listar();
        model.addAttribute("pets", pets);
        return "pets/listar";
    }

    @GetMapping("/pets/visualizar/{id}")
    public String visualizar(@PathVariable String id,
            Model model) {
        Pet pet = repository.obterId(id);
        model.addAttribute("pet", pet);
        return "pets/visualizar";
    }

    @GetMapping("/pets/excluir/{id}")
    public String excluir(@PathVariable String id) {
        repository.excluir(id);
        return "redirect:/pets/listar";
    }

    @GetMapping("/pets/atualizar/{id}")
    public String atualizar(@PathVariable String id,
            Model model) {
        Pet pet = repository.obterId(id);
        model.addAttribute("pet", pet);
        model.addAttribute("tutores", pessoasRepository.listar());
        return "pets/atualizar";
    }

    @PostMapping("/pets/editar/{id}")
    public String editar(@ModelAttribute Pet pet) {
        repository.salvar(pet);
        return "redirect:/pets/listar";
    }
}
