package com.lgy.smile.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.ChattingInterface;
import com.lgy.smile.dto.ChattingDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChattingService implements ChattingInterface{
	
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private DevUtils devUtils;
	
	@Override
	public ArrayList<ChattingDto> list() {
		ChattingInterface dao = sqlSession.getMapper(ChattingInterface.class);
		ArrayList<ChattingDto> list = dao.list();
		return list;
	}

	@Override
	public ArrayList<ChattingDto> contentView(int chattingroom) {
		ChattingInterface dao = sqlSession.getMapper(ChattingInterface.class);
		return dao.contentView(chattingroom);
	}

	@Override
	public void write(HashMap<String, String> params) {
		ChattingInterface dao = sqlSession.getMapper(ChattingInterface.class);
		params.put("sendtime", devUtils.getDateCustom("yyyy년 MM월 dd일(E요일) a hh:mm"));
	
		log.info(""+params);
		dao.write(params);
	}

}
