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

import com.example.cetej5trabalho1.model.Editora;

@Service
public class EditoraDAO {
	
	private EntityManagerFactory factory;
	private EntityManager manager;
	
	public EditoraDAO() {
		this.factory = Persistence.createEntityManagerFactory("livraria");
		this.manager = this.factory.createEntityManager();
	}
	
	
	public boolean saveEditora(Editora editora) {
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
        this.manager.persist(editora);
        this.manager.getTransaction().commit();
		return true;
	}
	
	public boolean updateEditora(Editora editora) {
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		Editora e = this.manager.find(Editora.class, editora.getCod());
		e.setName(editora.getName());
		this.manager.getTransaction().commit();
		return true;
	}
	
//	public boolean deleteEditora(long id) {
//		if(!this.manager.getTransaction().isActive())
//			this.manager.getTransaction().begin();
//		Editora e = this.manager.find(Editora.class, id);
//		this.manager.remove(e);
//		this.manager.getTransaction().commit();
//		this.manager.clear();
//		this.manager.flush();
//		this.manager.close();
//		return true;
//	}
	
	public Editora getEditora(long id) {
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		Editora editora = this.manager.find(Editora.class, id);
		return editora;
	}
	
	public List<Editora> getEditoras(){
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		Query query = manager.createQuery("SELECT e FROM Editora e");
		return query.getResultList(); 
	}
	
	public Page<Editora> getEditoras(Pageable pageable){
		if(!this.manager.getTransaction().isActive())
			this.manager.getTransaction().begin();
		int pageSize = pageable.getPageSize();
		int current = pageable.getPageNumber();
		int start = current * pageSize;
		List<Editora> list;
		Query query = manager.createQuery("SELECT e FROM Editora e");
		list = query.getResultList();
		
		List<Editora> editoras;
		
		if(list.size() < start) {
			editoras = Collections.emptyList();
		}else {
			int toIndex = Math.min(start+pageSize, list.size());
			editoras = list.subList(start, toIndex);
		}
		
		Page<Editora> editoraPage = new PageImpl<Editora>(editoras, PageRequest.of(current, pageSize),list.size());
		return editoraPage;
	}

}
