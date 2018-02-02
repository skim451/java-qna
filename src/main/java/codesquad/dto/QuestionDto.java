package codesquad.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Size;
import java.util.Objects;

public class QuestionDto {
    private static final Logger log = LoggerFactory.getLogger(QuestionDto.class);

    private long id;

    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3)
    private String contents;

    public QuestionDto() {

    }

    public QuestionDto(String title, String contents) {
        this(0, title, contents);
    }

    public QuestionDto(long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public QuestionDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public QuestionDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public QuestionDto setContents(String contents) {
        this.contents = contents;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        log.debug("{} {}", this, o);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDto that = (QuestionDto) o;
        return
                Objects.equals(title, that.title) &&
                Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, contents);
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
