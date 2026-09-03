package controller;
import modelos.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import repositorios.AnimalRepositorio;

@Controller
public class AnimalController {

    @Autowired
    private AnimalRepositorio animalRepositorio;

    @GetMapping("/cadastroAnimal")
    public ModelAndView cadastrar(Animal animal){
        ModelAndView mv = new ModelAndView("/gerenciadorDeEntidades/animal");
        mv.addObject("animal", animal);
        return mv;
    }

    @PostMapping("/salvarAnimal")
    public ModelAndView salvar(Animal animal, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(animal);
        }
        animalRepositorio.saveAndFlush(animal);
        return cadastrar(new Animal());
    }



}
