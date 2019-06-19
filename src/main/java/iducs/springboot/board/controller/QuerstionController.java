package iducs.springboot.board.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import iducs.springboot.board.domain.Question;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.exception.ResourceNotFoundException;
import iducs.springboot.board.repository.UserRepository;
import iducs.springboot.board.service.QuestionService;
import iducs.springboot.board.service.UserService;
import iducs.springboot.board.util.HttpSessionUtils;
import iducs.springboot.board.util.PageRequest;

@Controller
@RequestMapping("/questions")
public class QuerstionController {
	@Autowired QuestionService questionService; // 의존성 주입(Dependency Injection) : 
	
	@GetMapping("")
	public String getAllUser(Model model, HttpSession session) {
		List<Question> questions = questionService.getQuestions();
		model.addAttribute("questions", questions);
		return "/questions/list"; 
	}	
	
	@PostMapping("")
	// public String createUser(Question question, Model model, HttpSession session) {
	public String createUser(String title, String contents, Model model, HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		Question newQuestion = new Question(title, sessionUser, contents);		
		// Question newQuestion = new Question(question.getTitle(), writer, question.getContents());	
		questionService.saveQuestion(newQuestion);
		return "redirect:/questions"; // get 방식으로  리다이렉션 - Controller를 통해 접근
	}
	
	@GetMapping("/{id}")
	public String getQuestionById(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		Question question = questionService.getQuestionById(id);
		User writer = question.getWriter();
		if(sessionUser.equals(writer)) {
		//if(sessionUser.getUserId().equals(writer.getUserId())) {
			model.addAttribute("same", "같다");
		}
		model.addAttribute("question", question);
		return "/questions/info";
	}
	@GetMapping("/{id}/form")
	public String getUpdateForm(@PathVariable(value = "id") Long id, Model model) {
		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "/questions/info";
	}
	@PutMapping("/{id}/edit")
	public String updateQuestionById(@PathVariable(value = "id") Long id, String title, String contents, Model model, HttpSession session) {
		User writer = (User) session.getAttribute("user");
		if(HttpSessionUtils.isLogined(writer))
				return "redirect:/users/login-form";
		model.addAttribute("writer", writer);
		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "/questions/edit";
	}
	@DeleteMapping("/{id}")
	public String deleteQuestionById(@PathVariable(value = "id") Long id, Model model) {
		Question question = questionService.getQuestionById(id);
		questionService.deleteQuestion(question);
		model.addAttribute("userId", question.getWriter().getUserId());
		return "/questions";
	}
}
