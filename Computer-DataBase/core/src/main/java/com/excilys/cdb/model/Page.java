package com.excilys.cdb.model;

public class Page {
	
	private Integer currentPage = 1;
	
	private Integer nbPage;
	
	private Integer itemsByPage = 10;
	
	private String order = "computer.id";
	
	private String ascending = "ASC";
	
	public Page() {
	}
	
	public void incrementCurrentPage() {
		currentPage++;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		if(currentPage < 1) {
			this.currentPage = 1;
		}
		else {
			this.currentPage = currentPage;
		}	
	}

	public Integer getNbPage() {
		return nbPage;
	}

	public void setNbPage(Integer nbPage) {
		this.nbPage = nbPage;
	}

	public Integer getItemsByPage() {
		return itemsByPage;
	}

	public void setItemsByPage(Integer itemsByPage) {
		this.itemsByPage = itemsByPage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getAscending() {
		return ascending;
	}

	public void setAscending(String ascending) {
		this.ascending = ascending;
	}
	
	

}
