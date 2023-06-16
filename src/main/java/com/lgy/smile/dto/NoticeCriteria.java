package com.lgy.smile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoticeCriteria {
	private int pageNum;//페이지 번호
	private int amount;//페이지당 글 갯수
	
	public NoticeCriteria() {
		this(1, 10);
	}
}
