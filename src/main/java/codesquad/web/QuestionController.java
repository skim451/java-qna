package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.etc.CannotDeleteException;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QnaService qnaService;

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

        qnaService.create(loginUser, question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestionDetail(@PathVariable long id, Model model) {
        Question question = qnaService.findById(id);
        model.addAttribute("question", question);

        return "qna/show";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@LoginUser User loginUser, @PathVariable long id) {
        try {
            qnaService.deleteQuestion(loginUser, id);
        } catch (CannotDeleteException e) {
            log.debug(e.getMessage());
        }
        return "redirect:/";
    }
}
