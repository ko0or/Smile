package com.lgy.smile.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.NoticeDto;

public interface NoticeMapperinterface {
	public ArrayList<NoticeDto> list(@RequestParam HashMap<String, String> params);
	public void write(@RequestParam HashMap<String, String> param);
	public NoticeDto contentView(@RequestParam HashMap<String, String> param);
	public void modify(@RequestParam HashMap<String, String> param);
	public void delete(@RequestParam HashMap<String, String> param);
	int getCount();
}
