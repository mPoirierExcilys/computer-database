package com.excilys.cdb.model;

public class Page {
	
	private Integer currentPage = 1;
	
	private Integer nbPage = 10;
	
	private Integer itemsByPage = 10;
	
	public Page() {
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
