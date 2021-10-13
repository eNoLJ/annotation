package controller;

import annotation.Controller;
import annotation.GetMapping;
import annotation.PostMapping;

@Controller
public class QuestionController {

    @GetMapping("/question.html")
    public String getQuestion() {
        System.out.println("질문 페이지 요청");
        return "/question.html";
    }

    @PostMapping("/question")
    public String createQuestion() {
        System.out.println("질문 하기 요청");
        return "/question.html";
    }
}
