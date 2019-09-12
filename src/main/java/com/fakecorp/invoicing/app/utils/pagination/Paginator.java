package com.fakecorp.invoicing.app.utils.pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class Paginator<T> {
	
	private String url;
	private Page<T> page;
	
	private int totalPages;
	private int maxResultsPerPage;
	private int currentPage;
	
	private List<PageItem> pages;
	
	public Paginator(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<PageItem>();
		
		maxResultsPerPage = page.getSize();
		totalPages = page.getTotalPages();
		currentPage = page.getNumber() + 1;
		
		int from, to;
		if(totalPages <= maxResultsPerPage) {
			from = 1;
			to = totalPages;
		} else {
			if (currentPage <= maxResultsPerPage / 2) {
				from = 1;
				to = maxResultsPerPage;
			} else if (currentPage >= totalPages - maxResultsPerPage / 2){
				from = totalPages - maxResultsPerPage + 1;
				to = maxResultsPerPage;
			} else {
				from = currentPage - maxResultsPerPage / 2;
				to = maxResultsPerPage;
			}
		}
		
		for (int i = 0; i < to; i++) {
			pages.add(new PageItem (from + i, currentPage == from + i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean hasNext() {
		return page.hasNext();
	}
	
	public boolean hasPrevious() {
		return page.hasPrevious();
	}
	
}
