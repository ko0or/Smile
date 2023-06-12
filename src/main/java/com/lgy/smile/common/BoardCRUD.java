package com.lgy.smile.common;

import javax.servlet.http.HttpSession;

public interface BoardCRUD {

	public void create(HttpSession session);
	public void read();
	public void update(HttpSession session);
	public void delete(HttpSession session);
	
}
