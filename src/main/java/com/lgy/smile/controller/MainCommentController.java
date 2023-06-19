package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.MainCommentDto;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.MainCommentService;
import com.lgy.smile.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main/comment")
public class MainCommentController {

	@Autowired private MainCommentService commentService;
	@Autowired private DevUtils devUtils;

	
	@GetMapping("/getComments")
	@ResponseBody
	public ResponseEntity<List<MainCommentDto>> getPosts(@RequestParam HashMap<String, String> params) {

		ArrayList<MainCommentDto> dtos = commentService.list(params);
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

}
