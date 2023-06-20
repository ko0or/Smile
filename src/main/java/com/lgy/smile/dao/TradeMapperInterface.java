package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lgy.smile.dto.TradeDto;

public interface TradeMapperInterface {

	// ★ trade(중고 거래) 글 목록
	public ArrayList<TradeDto> list();
	// ★ trade(중고 거래) 글 작성
	public void write(@RequestParam HashMap<String, String> param);
	public void write(@RequestParam HashMap<String, String> param, MultipartFile[] uploadFile, HttpSession session);
	// ★ trade(중고 거래) 글 내용 보기
	public TradeDto contentView(@RequestParam HashMap<String, String> param);
	// ★ trade(중고 거래) 글 수정
	public void modify(@RequestParam HashMap<String, String> param);
	public void modify(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session);
	
	public void modifyWithImgPath(@RequestParam HashMap<String, String> param);
	public void modifyWithImgPath(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session);
	
	
	// ★ trade(중고 거래) 글 삭제
	public void delete(@RequestParam HashMap<String, String> param);
	public void delete(@RequestParam HashMap<String, String> param, HttpSession session);
	
}