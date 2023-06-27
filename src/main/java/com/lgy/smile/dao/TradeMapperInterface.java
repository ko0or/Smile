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
	public ArrayList<TradeDto> list(@RequestParam HashMap<String, String> param);
		
	// ★ trade(중고 거래) 글 작성
	public boolean write(@RequestParam HashMap<String, String> param);
	public boolean write(@RequestParam HashMap<String, String> param, MultipartFile[] uploadFile, HttpSession session);
	
	// ★ trade(중고 거래) 글 내용 보기
	public TradeDto contentView(@RequestParam HashMap<String, String> param);
	
	// ★ trade(중고 거래) 글 수정
	public boolean modify(@RequestParam HashMap<String, String> param);
	public boolean modify(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session);
	
	public void modifyWithImgPath(@RequestParam HashMap<String, String> param);
	public void modifyWithImgPath(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session);
	
	// ★ trade(중고 거래) 글 삭제
	public boolean delete(@RequestParam HashMap<String, String> param);
	public boolean delete(@RequestParam HashMap<String, String> param, HttpSession session);
	
	// ★ trade(중고 거래) 회원 휴대폰 번호 (or 인증 번호) 확인	
	public int telCheck(@RequestParam HashMap<String, String> param);
	public int telCheck(@RequestParam HashMap<String, String> param, HttpSession session);
	
	// ★ trade(중고 거래) 인증된 휴대폰 번호 (or 회원에게 발송된 인증번호) 등록
	public void telUpdate(@RequestParam HashMap<String, String> params);
	public void telUpdate(@RequestParam HashMap<String, String> params, HttpSession session);
}