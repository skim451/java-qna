package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String getQuestionForm() {
        return "qna/form";
    }

    @PostMapping("")
    public String createQuestion(@LoginUser User loginUser, String title, String contents) {
        if(loginUser == null)
            return "redirect:/login";

        Question question = new Question()
                .setContents(contents)
                .setTitle(title)
                .setWriter(loginUser);

        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestionDetail(@PathVariable long id, Model model) {
        Question question = questionRepository.findOne(id);
        model.addAttribute("question", question);

        return "qna/show";
    }
}
