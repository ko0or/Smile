package com.lgy.smile.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgy.smile.dao.ChattingRoomInterface;
import com.lgy.smile.dto.ChattingRoomDto;
import com.lgy.smile.dto.MyChattingRoomDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChattingRoomService implements ChattingRoomInterface{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public ArrayList<ChattingRoomDto> list() {
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		ArrayList<ChattingRoomDto> list = dao.list();
		return list;
	}
	
	@Override
	public ArrayList<MyChattingRoomDto> myChatRoomList(HashMap<String, String> params) {
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		ArrayList<MyChattingRoomDto> list = dao.myChatRoomList(params);
		return list;
	}
	
	@Override
	public ArrayList<ChattingRoomDto> sellerList(HashMap<String, String> params) {
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		ArrayList<ChattingRoomDto> list = dao.sellerList(params);
		return list;
	}


	@Override
	public ChattingRoomDto contentView(HashMap<String, String> params) {
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		return dao.contentView(params);
	}

	@Override
	public int write(HashMap<String, String> params) {
		
		log.info("========= chattingroom write ========");
		
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		
		try {
			dao.write(params);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void delete(HashMap<String, String> params) {
		log.info("========= chattingroom delete ========");
		
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		
		dao.delete(params);
	}
}
