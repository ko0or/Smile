package com.lgy.smile.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.ChattingInterface;
import com.lgy.smile.dto.ChattingDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChattingService implements ChattingInterface{
	
	private SqlSession sqlSession;
	private DevUtils devUtils;
	
	@Override
	public ArrayList<ChattingDto> list() {
		ChattingInterface dao = sqlSession.getMapper(ChattingInterface.class);
		ArrayList<ChattingDto> list = dao.list();
		return list;
	}

	@Override
	public ChattingDto contentView(HashMap<String, String> params) {
		ChattingInterface dao = sqlSession.getMapper(ChattingInterface.class);
		return dao.contentView(params);
	}

	@Override
	public void write(HashMap<String, String> params) {
		ChattingInterface dao = sqlSession.getMapper(ChattingInterface.class);
		params.put("sendtime", devUtils.getDate());
		dao.write(params);
	}

}
