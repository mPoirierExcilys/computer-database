package com.excilys.cdb.model;

public class Page {
	
	private Integer currentPage;
	
	private Integer nbPage;
	
	private Integer itemsByPage;
	
	public Page() {
	}
	
	public Page(Integer currentPage, Integer itemsByPage) {
		this.currentPage = currentPage;
		this.itemsByPage = itemsByPage;
	}
	
	public void incrementCurrentPage() {
		currentPage++;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
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
	
	

}
