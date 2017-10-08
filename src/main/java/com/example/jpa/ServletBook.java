package com.example.jpa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Book;
import util.TemplateHtml;


public class ServletBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/* referencia por inyección */
	@EJB
	private BookService service; 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
		//listar
		
		String option = request.getParameter("option");
		TemplateHtml.setPathURL(request.getContextPath());
		
		
		if(option.equals("insert")){
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();	
			
			
			
			out.println(TemplateHtml.getHead("Insertar"));
			out.println(TemplateHtml.getMenu());
			out.println(getForm());
			out.println(TemplateHtml.getFooter());
			
		}else if(option.equals("list")){
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();			
			out.println(TemplateHtml.getHead("List"));
			out.println(TemplateHtml.getMenu());
			
			List<Book> list = service.getAllBooks();
			
			StringBuilder builder = new StringBuilder();
			
			for(Book book: list){
				builder.append("<tr>");
				builder.append("<td>" + book.getBookTitle() + "</td>");
				builder.append("<td>" + book.getDescription() + "</td>");
				builder.append("</tr>");
			}
			
			out.println(builder.toString());
			out.println(TemplateHtml.getFooter());
			
		}
		
		
		
	
	}
	
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//doGet(request, response);
		
		//inset
		
		
		String bookTitle = request.getParameter("bookTitle");
		String description = request.getParameter("description");
		
		
		Book book = new Book();
		book.setBookTitle(bookTitle);
		book.setDescription(description);
		
		service.addBook(book);
		
	}
	
	
	
	private String getForm() {
		
		StringBuilder str = new StringBuilder();		
		str.append("<h1 align=\"center\">Formulario Registro</h1>");					
		str.append("<form action='/servlet/book' method='post'>");	    
		str.append("<input type='text' name='bookTitle'>");
		str.append("<input type='text' name='description'>");
		str.append("<input type='submit' value='Save'>");
		str.append("</form>");
		return str.toString();
	}
	
	
	

}