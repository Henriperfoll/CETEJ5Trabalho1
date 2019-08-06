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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.cetej5trabalho1.DAO.EditoraDAO;
import com.example.cetej5trabalho1.DAO.LivroDAO;
import com.example.cetej5trabalho1.model.Editora;
import com.example.cetej5trabalho1.model.Livro;

@Controller
public class LivroController {

	@Autowired
	private LivroDAO daoLivro;
	@Autowired
	private EditoraDAO daoEditora;
	private static final Logger logger = LoggerFactory.getLogger(EditoraController.class);

	@RequestMapping("/livro")
	public ModelAndView editora(Model model, @RequestParam("page") Optional<Integer> page) {
		int current = page.orElse(1);
		int pageSize = 5;
		Page<Livro> livros = this.daoLivro.getLivros(PageRequest.of(current - 1, pageSize));
		ModelAndView view = new ModelAndView("livro");
		for(Livro livro : livros) {
			if(livro.getEditora().getName()==null) {
				livro.getEditora().setName(this.daoEditora.getEditora(livro.getEditora().getCod()).getName());
			}
		}
		view.addObject("livros", livros);
		view.addObject("numberOfElements",livros.getNumberOfElements());
//		logger.info("TESTE - "+livros.getNumberOfElements()+" "+livros.getContent().get(1).getEditora().getName());

		int totalPages = livros.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			view.addObject("pageNumbers", pageNumbers);
		}
		return view;
	}
	
	@GetMapping("/livro/livroForm")
	public ModelAndView formLivro(Model model, @RequestParam("livro") Optional<Long> livroCod) {
		Livro livro;
		List<Editora> editoras = this.daoEditora.getEditoras();
		if(editoras.isEmpty()) {
			ModelAndView view = new ModelAndView("editora");
			view.addObject("fromLivro",1);
			return view;
		}
		ModelAndView view = new ModelAndView("livroForm");
		if (livroCod.isEmpty()) {
			livro = new Livro();
			view.addObject("update", 0);
		} else {
//			editora = this.dao.getEditora(editoraCod.get());
			livro = this.daoLivro.getLivro(livroCod.get());
			view.addObject("update", 1);
		}
		view.addObject("livro", livro);
		view.addObject("editoras",editoras);

		return view;
	}
	
	@PostMapping("/livro/livroForm/save")
	public String newLivro(@ModelAttribute Livro livro) {
		logger.info("SAVE");
		logger.info("Livro - "+livro.getNome()+" Editora - "+livro.getEditora().getCod());
		this.daoLivro.saveLivro(livro);
		
		return "sucesso";
	}
	
	@PostMapping("/livro/livroForm/update")
	public String editLivro(@ModelAttribute Livro livro, @RequestParam("cod") Long cod) {
		logger.info("UPDATE");
		livro.setCod(cod);
		this.daoLivro.updateLivro(livro);
		return "sucesso";
	}
	
//	@GetMapping("/livro/livroForm/delete")
//	public String deleteLivro(Model model, @RequestParam("cod") Long cod) {
//		logger.warn("DELETE");
//		this.daoLivro.deleteLivro(cod);
//		return "sucesso";
//	}
	
}
