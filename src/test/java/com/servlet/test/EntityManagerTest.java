package com.servlet.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.Book;

public class EntityManagerTest {
	
	
	EntityManagerFactory factory;
	
	@Before
	public void before(){
		factory = Persistence
				.createEntityManagerFactory("persistence-unit");
		Assert.assertNotNull(factory);
		EntityManager entityManager = factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("TRUNCATE book").executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		
	}
	
	 
	
	//@Test
	public void entityManagerTest(){
		
		
		Date time = new Date(System.currentTimeMillis());
		
		
		//Se crea el entity
		EntityManager entityManager = factory.createEntityManager();		
		Assert.assertNotNull(entityManager);
				
		Book book = getMockBooks("Test", 1).get(0);
		
		entityManager.getTransaction().begin();
			entityManager.persist(book);
		entityManager.getTransaction().commit();
		
		//Assert.assertNotNull(book.getBookId());
		
		Assert.assertNotEquals(0, book.getBookId());
		
			
		entityManager.close();
		
		
		
		//Se crea el entity
		entityManager = factory.createEntityManager();	
		
		entityManager.getTransaction().begin();	
			Book resultBook = entityManager.find(Book.class, book.getBookId());					
			resultBook.setDescription("Edited dentro de un commit");		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		Assert.assertNotEquals(0, book.getBookId());
		
		
		
		
		// Se crea el entity
		entityManager = factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
			Book resultBook1 = entityManager.find(Book.class, book.getBookId());
			resultBook1.setDescription("Edited dentro de un commit nuevo");
			
			entityManager.flush();
			
			resultBook1.setDescription("");
			
		entityManager.getTransaction().commit();
		entityManager.close();

		Assert.assertNotEquals(0, book.getBookId());
				
	}
		
			
	
	@Test
	public void entityManagerTestQuery(){		
		
		ArrayList<Book> listBooks = getMockBooks("Test", 10);		
		EntityManager entityManager = factory.createEntityManager();	
		entityManager.getTransaction().begin();
		
		for(Book book:listBooks){
			entityManager.persist(book);
		}
		
		entityManager.getTransaction().commit();
		
			//Query
			Query query = entityManager.createQuery("SELECT c FROM Book c");	
			List<Book> result = query.getResultList();		
		entityManager.close();
		
		Assert.assertEquals(10, result.size());
		
	}




	private ArrayList<Book> getMockBooks(String name, int n) {		
		ArrayList<Book> list = new ArrayList<>();
		Date time = new Date(System.currentTimeMillis());
		
		for(int i=0; i<n;i++){			
			Book book = new Book();
			book.setBookTitle(name +" "+ i);
			book.setDescription(name + ", creado el " + time.toString());	
			list.add(book);
		}		
		return list;
	}
	
	
	
	
	
	
}
