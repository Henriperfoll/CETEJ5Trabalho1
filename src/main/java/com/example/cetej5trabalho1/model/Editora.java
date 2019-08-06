package com.example.cetej5trabalho1.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;


@Entity
public class Editora {

	@Id
	@GeneratedValue
	private long cod;
	private String name;
//	@OneToMany(mappedBy = "editora",fetch = FetchType.LAZY, targetEntity = Livro.class)
//	@Cascade(org.hibernate.annotations.CascadeType.ALL)
//	private Set<Livro> livros;
	
	public long getCod() {
		return cod;
	}
	public void setCod(long cod) {
		this.cod = cod;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public Set<Livro> getLivros() {
//		return livros;
//	}
//	public void setLivros(Set<Livro> livros) {
//		this.livros = livros;
//	}
	
}
