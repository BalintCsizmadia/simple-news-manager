package com.newsmanager.web.model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public final class News extends AbstractModel {

    private final String title;
    private final String content;
    private final Date postDate;
    private List<Label> labels;

    public News(int id, String name, String content, Date postDate) {
        super(id);
        this.title = name;
        this.content = content;
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        News news = (News) o;
        return Objects.equals(title, news.title) &&
                Objects.equals(content, news.content) &&
                Objects.equals(postDate, news.postDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, content, postDate);
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + super.getId() + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postDate=" + postDate +
                ", labels=" + labels +
                '}';
    }
}
