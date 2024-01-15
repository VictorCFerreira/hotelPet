package com.hotelPet.controller;

import com.hotelPet.model.Pessoa;
import com.hotelPet.repository.PessoaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PessoasController {
    
    @Autowired
    private PessoaRepository repository;
    
    @GetMapping("/pessoas/cadastrar")
    public String cadastrar(Model model){
        model.addAttribute("pessoa", new Pessoa());
        return"pessoas/cadastrar";
    }
    
    @PostMapping("/pessoas/salvar")
    public String salvar(@ModelAttribute Pessoa pessoa){
        System.out.println("Salvando");
        repository.salvar(pessoa);
        return"redirect:/";
    }
    
    @GetMapping("/pessoas/listar")
    public String listar(Model model){
        List<Pessoa> pessoas = repository.listar();
        model.addAttribute("pessoas", pessoas);
        return"pessoas/listar";
    }
    
    @GetMapping("/pessoas/visualizar/{id}")
    public String visualizar(@PathVariable String id,
            Model model){
        Pessoa tutor = repository.obterId(id);
        model.addAttribute("pessoa", tutor);
        return "pessoas/visualizar";        
    }
    
    @GetMapping("/pessoas/excluir/{id}")
    public String excluir(@PathVariable String id){        
        repository.excluir(id);
        return"redirect:/pessoas/listar";
    }
    
    @GetMapping("/pessoas/atualizar/{id}")
    public String atualizar(@PathVariable String id,
            Model model){
        Pessoa pessoa = repository.obterId(id);
        model.addAttribute("pessoa", pessoa);
        return "pessoas/atualizar";        
    }
    
    @PostMapping("/pessoas/editar/{id}")
    public String editar(@ModelAttribute Pessoa pessoa){
        repository.salvar(pessoa);
        return"redirect:/pessoas/listar";
    }
}
