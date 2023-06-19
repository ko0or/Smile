package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lgy.smile.dto.TradeDto;

public interface TradeMapperInterface {
	
	public ArrayList<TradeDto> list();
	public void write(@RequestParam HashMap<String, String> param, MultipartFile[] uploadFile);
	public void write(@RequestParam HashMap<String, String> param, MultipartFile[] uploadFile, HttpSession session);
	
	public TradeDto contentView(@RequestParam HashMap<String, String> param);
	public void modify(@RequestParam HashMap<String, String> param);
	public void delete(@RequestParam HashMap<String, String> param);
}