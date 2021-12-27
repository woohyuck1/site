package com.woo.blog.service;



import java.nio.channels.IllegalChannelGroupException;
import java.util.List;

import javax.activation.CommandMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.woo.blog.dto.ReplySaveRequestDto;
import com.woo.blog.model.Board;
import com.woo.blog.model.Reply;
import com.woo.blog.model.RoleType;
import com.woo.blog.model.User;
import com.woo.blog.repository.BoardRepository;
import com.woo.blog.repository.ReplyRepository;
import com.woo.blog.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	

	
	@Transactional // 트랜잭션으로 묶임
	public void write(Board board, User user) {//title ,content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	@Transactional(readOnly =  true)
	public Page<Board> list(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	@Transactional(readOnly =  true)
	public Board textread(int id) {
		return boardRepository.findById(id)
			.orElseThrow(()->{
				return new IllegalArgumentException("상세보기 실패:아이디못찾음");
		});
	}
	
	@Transactional
	public void textdel(int id) {
		 boardRepository.deleteById(id);
				}
	
	@Transactional
	public void textfix(int id, Board reqestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기실패:아이디못찾음");
			});	
		board.setTitle(reqestBoard.getTitle());
		board.setContent(reqestBoard.getContent());
		//해당 함수로 종료시 (service가 종료될때) 트랜잭션이 종료된다 이떄 더티채킹 - 자동업데이트가 db쪽으로 flush
	}
	@Transactional
	public void replywrite(ReplySaveRequestDto replySaveRequestDto) {
	replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());
	}
	@Transactional
	public void replydel(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
}

	
	
	


