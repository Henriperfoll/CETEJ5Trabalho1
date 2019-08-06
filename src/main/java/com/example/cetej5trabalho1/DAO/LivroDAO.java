package com.example.cetej5trabalho1.DAO;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.cetej5trabalho1.model.Livro;

@Service
public class LivroDAO {

	private EntityManagerFactory factory;
	private EntityManager manager;
	
	public LivroDAO() {
		this.factory = Persistence.createEntityManagerFactory("livraria");
		this.manager = this.factory.createEntityManager();
	}
	
	private void flushAndClean() {
		this.manager.clear();
		this.manager.flush();
	}
	
	public boolean saveLivro(Livro livro) {
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
        this.manager.persist(livro);
        this.manager.getTransaction().commit();
		return true;
	}
	
	public boolean updateLivro(Livro livro) {
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		Livro e = this.manager.find(Livro.class, livro.getCod());
		e.setNome(livro.getNome());
		e.setEditora(livro.getEditora());
		this.manager.getTransaction().commit();
		return true;
	}
	
//	public boolean deleteLivro(long id) {
//		if(!this.manager.getTransaction().isActive())
//			this.manager.getTransaction().begin();
//		Livro e = this.manager.find(Livro.class, id);
//		this.manager.remove(e);
//		this.manager.getTransaction().commit();
//		return true;
//	}
	
	public Livro getLivro(long id) {
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		Livro livro = this.manager.find(Livro.class, id);
		return livro;
	}
	
	public List<Livro> getLivros(){
		List<Livro> list = null;
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		Query query = manager.createQuery("SELECT e FROM Livro e");
		return query.getResultList(); 
	}
	
	public List<Livro> getLivrosFromEditora(Long cod){
		List<Livro> list = null;
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		Query query = manager.createQuery("SELECT e FROM Livro e WHERE e.editora.cod="+cod);
		return query.getResultList(); 
	}
	
	public Page<Livro> getLivros(Pageable pageable){
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		int pageSize = pageable.getPageSize();
		int current = pageable.getPageNumber();
		int start = current * pageSize;
		List<Livro> list;
		Query query = manager.createQuery("SELECT e FROM Livro e join fetch e.editora");
		list = query.getResultList();
		
		List<Livro> livros;
		
		if(list.size() < start) {
			livros = Collections.emptyList();
		}else {
			int toIndex = Math.min(start+pageSize, list.size());
			livros = list.subList(start, toIndex);
		}
		
		Page<Livro> livroPage = new PageImpl<Livro>(livros, PageRequest.of(current, pageSize),list.size());
		return livroPage;
	}
}
