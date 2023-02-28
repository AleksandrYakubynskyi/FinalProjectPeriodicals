package com.project.entity;

import com.project.entity.enums.Topic;

import java.math.BigDecimal;
import java.util.Objects;

public class Publication {
    private String id;
    private String name;
    private Topic topic;
    private BigDecimal price;
    private String content;

    public Publication() {
    }

    public Publication(Topic topic, String name, BigDecimal price, String content) {
        this.topic = topic;
        this.name = name;
        this.price = price;
        this.content = content;
    }

    public Publication(String id, Topic topic, String name, BigDecimal price, String content) {
        this.id = id;
        this.topic = topic;
        this.name = name;
        this.price = price;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id='" + id + '\'' +
                ", topic=" + topic +
                ", price=" + price +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
