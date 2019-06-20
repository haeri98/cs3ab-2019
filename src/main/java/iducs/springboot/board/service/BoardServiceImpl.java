package iducs.springboot.board.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import iducs.springboot.board.domain.Answer;
import iducs.springboot.board.domain.Board;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.entity.AnswerEntity;
import iducs.springboot.board.entity.BoardEntity;
import iducs.springboot.board.entity.UserEntity;
import iducs.springboot.board.repository.BoardRepository;

@Service("questionService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired BoardRepository repository;

	@Override
	public Board getQuestionById(long id) {
		BoardEntity entity = repository.findById(id).get();
		Board question = entity.buildDomain();
		
		List<Answer> answerList = new ArrayList<Answer>();
		for(AnswerEntity answerEntity : entity.getAnswers())
			answerList.add(answerEntity.buildDomain());
		question.setAnswers(answerList);
		
		return question;
	}
	
	public List<Board> getQuestions(PageRequest pageRequest) {
		List<Board> boards = new ArrayList<Board>();
		Page<BoardEntity> entities = repository.findAll(pageRequest);
		for(BoardEntity entity : entities) {
			Board board = entity.buildDomain();
			boards.add(board);
		}
		return boards;
	}
	
	@Override
	public List<Board> getQuestions(Long pageNo) {
		PageRequest pageRequest = PageRequest.of((int) (pageNo - 1), 2, new Sort(Sort.Direction.ASC, "id"));
		Page<BoardEntity> entities = repository.findAll(pageRequest);
		List<Board> boards = new ArrayList<Board>();
		for(BoardEntity entity : entities) {
			Board board = entity.buildDomain();
			boards.add(board);
		}
		return boards;
	}	

	@Override
	public List<Board> getQuestions() {
		/*
		 * 1. Repository로 부터 모든 자료를 가져와 Enitiy 리스트에 저장한다.
		 * 2. 
		 */
		List<BoardEntity> entities = repository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
														// new Sort(Sort.Direction.ASC,"writer");
		List<Board> questions = new ArrayList<Board>();
		for(BoardEntity entity : entities) {
			Board question = entity.buildDomain();
			questions.add(question);
		}
		return questions;			
	}

	@Override
	public List<Board> getQuestionsByUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Board> getQuestionsByPage(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveQuestion(Board question) {
		BoardEntity entity = new BoardEntity();
		entity.buildEntity(question);
		repository.save(entity);
		
	}

	@Override
	public void updateQuestion(Board question) {
		BoardEntity entity = new BoardEntity();
		entity.buildEntity(question);
		repository.save(entity);
		
	}

	@Override
	public void deleteQuestion(Board question) {
		BoardEntity entity = new BoardEntity();
		entity.buildEntity(question);
		repository.delete(entity);
		
	}

	

}
