package com.example.cetej5trabalho1.controler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.cetej5trabalho1.DAO.EditoraDAO;
import com.example.cetej5trabalho1.DAO.LivroDAO;
import com.example.cetej5trabalho1.model.Editora;
import com.example.cetej5trabalho1.model.Livro;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditoraController {

	@Autowired
	private EditoraDAO dao;
	@Autowired
	private LivroDAO daoLivros;

	private static final Logger logger = LoggerFactory.getLogger(EditoraController.class);

	@RequestMapping("/editora")
	public ModelAndView editora(Model model, @RequestParam("page") Optional<Integer> page) {
		int current = page.orElse(1);
		int pageSize = 5;
		Page<Editora> editoras = this.dao.getEditoras(PageRequest.of(current - 1, pageSize));
		ModelAndView view = new ModelAndView("editora");
		view.addObject("editoras", editoras);
		view.addObject("fromLivro",0);
		view.addObject("numberOfElements",editoras.getNumberOfElements());
		int totalPages = editoras.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			view.addObject("pageNumbers", pageNumbers);
		}
		return view;
	}

	@GetMapping("/editora/editoraForm")
	public ModelAndView newEditora(Model model, @RequestParam("editora") Optional<Long> editoraCod) {
		Editora editora;
		ModelAndView view = new ModelAndView("editoraForm");
		if (editoraCod.isEmpty()) {
			editora = new Editora();
			view.addObject("update", 0);
		} else {
			editora = this.dao.getEditora(editoraCod.get());
			view.addObject("update", 1);
		}
		view.addObject("editora", editora);

		return view;
	}
	
	@PostMapping("/editora/editoraForm/update")
	public String editEditora(@ModelAttribute Editora editora, @RequestParam("cod") Long cod) {
		logger.info("Editora "+editora.getName()+" id:"+editora.getCod());
		
		logger.info("UPDATE");
		this.dao.updateEditora(editora);
		
		return "sucesso";
	}
	
//	@GetMapping("/editora/editoraForm/delete")
//	public String deleteEditora(@RequestParam("cod") Long cod) {
//		logger.info("Editora id:"+cod);
//		logger.warn("DELETE");
//		this.dao.deleteEditora(cod);
//		
//		return "sucesso";
//	}

	@PostMapping("/editora/editoraForm/save")
	public String newEditora(@ModelAttribute Editora editora) {
		logger.info("Editora "+editora.getName()+" id:"+editora.getCod());
		logger.info("SAVE");
		this.dao.saveEditora(editora);
		
		return "sucesso";
	}

	
}
