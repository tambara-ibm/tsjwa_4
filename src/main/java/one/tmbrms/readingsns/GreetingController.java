package one.tmbrms.readingsns;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model){

        model.addAttribute("first","ファースト");
        model.addAttribute("second","セカンド");
        model.addAttribute("third","サード");

        var books = new ArrayList<Book>();

        var book = new Book("シャーロック★ホームズの凱旋", "9784120057342");
        books.add(book);

        book = new Book("入門 モダンLinux", "9784814400218");
        books.add(book);

        model.addAttribute("books", books);

        return "thymeleaf";
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("articles", getArticles());
        return "index";
    }

    private List<Article> getArticles(){
        List<Article> ret = new ArrayList<Article>();

        var article = new Article();

        article.setUser(1, "tambara", "tambara-icon.png");
        article.setBook("シャーロック・ホームズの凱旋", "9784120057342");
        article.setMessage(101, "2章まで読んだ。ホームズが腐れ大学生なんだが？", "2024-02-09T01:48:54.298Z");
        ret.add(article);

        article = new Article();
        article.setUser(2, "Eri KUWAHARA", "kuwahara-icon.png");
        article.setBook("AIリスク教本　攻めのディフェンスで危機回避＆ビジネス加速", "9784296204083");
        article.setMessage(102, "リスクの章の3つ目まで読んだ。17個は多くない！？", "2024-02-07T05:24:32.911Z");
        ret.add(article);

        article = new Article();
        article.setUser(3,"harimoto","harimoto-icon.png");
        article.setBook("入門 モダンLinux", "9784814400218");
        article.setMessage(103, "ワタシ、リナックスチョットデキル", "2024-01-30T10:07:32.929Z");
        ret.add(article);

        return ret;
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", getUsers());
        return "users";
    }

    public List<User> getUsers(){
        List<User> ret = new ArrayList<User>();

        ret.add(new User(1, "tambara", "tambara-icon.png"));
        ret.add(new User(2, "Eri KUWAHARA", "kuwahara-icon.png"));
        ret.add(new User(3, "harimoto", "harimoto-icon.png"));

        return ret;
    }

    @GetMapping("/user/{id}")
    public String user(Model model, @PathVariable int id){
        model.addAttribute("user", getUser(id));
        model.addAttribute("articles", getArticles().stream().filter(a -> a.getUser().getId() == id)); 
        return "user";
    }

    public User getUser(int id){
        User ret = null;

        switch(id){
            case 1:
                ret = new User(1, "tambara", "tambara-icon.png");
                break;
            case 2:
                ret = new User(2, "Eri KUWAHARA", "kuwahara-icon.png");
                break;
            case 3:
                ret = new User(3, "harimoto", "harimoto-icon.png");
                break;
        }

        return ret;
    }

    @PostMapping("/post")
    public String post(@RequestParam String userid, @RequestParam String isbn, @RequestParam String content){
        System.out.println(String.format("userid=%s, isbn=%s, content=%s", userid, isbn, content));
        return "redirect:/";
    }

}
