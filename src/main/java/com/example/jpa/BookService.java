/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.example.jpa;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.openejb.jee.DataSource;
import org.junit.Assert;

import entities.Book;


@Stateless
public class BookService {
	
	//@PersistenceContext(unitName = "persistence-unit" )
    
	
	
	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	
	public BookService(){
		factory = Persistence
				.createEntityManagerFactory("persistence-unit");
	}
	
	
		
	
//	@Resource(name = "dataSourcesLocal")
//	private DataSource dataSource;

	
	//@Resource(name="HSQLDB Database") DataSource ds; 

	
    public void addBook(Book book){
    	entityManager = factory.createEntityManager();
    	entityManager.getTransaction().begin();    	
    	entityManager.persist(book);    	
    	entityManager.getTransaction().commit();
    	entityManager.close();
    }
    
    
    public List<Book> getAllBooks(){
    	entityManager = factory.createEntityManager();	
    	Query query = entityManager.createQuery("SELECT c FROM Book c");	
		List<Book> list = query.getResultList();		
		entityManager.close();		
		return list;
    }
    
    
    
}
