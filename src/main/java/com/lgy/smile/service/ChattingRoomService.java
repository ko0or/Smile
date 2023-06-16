package com.lgy.smile.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.lgy.smile.dao.ChattingRoomInterface;
import com.lgy.smile.dto.ChattingRoomDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChattingRoomService implements ChattingRoomInterface{
	
	private SqlSession sqlSession;
	
	@Override
	public ArrayList<ChattingRoomDto> list() {
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		ArrayList<ChattingRoomDto> list = dao.list();
		return list;
	}

	@Override
	public ChattingRoomDto contentView() {
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		return dao.contentView();
	}

	@Override
	public void write(HashMap<String, String> params) {
		ChattingRoomInterface dao = sqlSession.getMapper(ChattingRoomInterface.class);
		dao.write(params);
	}

}
