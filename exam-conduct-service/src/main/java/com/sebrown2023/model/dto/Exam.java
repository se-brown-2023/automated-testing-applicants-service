package com.sebrown2023.model.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sebrown2023.model.dto.Task;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Exam
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Exam {

  private Long id;

  private Long examinerId;

  private String name;

  private String description;

  private String programmingLanguage;

  private String maxDuration;

  private String ttl;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime creationDate;

  @Valid
  private List<@Valid Task> tasks;

  public Exam id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Exam examinerId(Long examinerId) {
    this.examinerId = examinerId;
    return this;
  }

  /**
   * Get examinerId
   * @return examinerId
  */
  
  @JsonProperty("examinerId")
  public Long getExaminerId() {
    return examinerId;
  }

  public void setExaminerId(Long examinerId) {
    this.examinerId = examinerId;
  }

  public Exam name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Exam description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Exam programmingLanguage(String programmingLanguage) {
    this.programmingLanguage = programmingLanguage;
    return this;
  }

  /**
   * Get programmingLanguage
   * @return programmingLanguage
  */
  
  @JsonProperty("programmingLanguage")
  public String getProgrammingLanguage() {
    return programmingLanguage;
  }

  public void setProgrammingLanguage(String programmingLanguage) {
    this.programmingLanguage = programmingLanguage;
  }

  public Exam maxDuration(String maxDuration) {
    this.maxDuration = maxDuration;
    return this;
  }

  /**
   * Get maxDuration
   * @return maxDuration
  */
  
  @JsonProperty("maxDuration")
  public String getMaxDuration() {
    return maxDuration;
  }

  public void setMaxDuration(String maxDuration) {
    this.maxDuration = maxDuration;
  }

  public Exam ttl(String ttl) {
    this.ttl = ttl;
    return this;
  }

  /**
   * Get ttl
   * @return ttl
  */
  
  @JsonProperty("ttl")
  public String getTtl() {
    return ttl;
  }

  public void setTtl(String ttl) {
    this.ttl = ttl;
  }

  public Exam creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
  */
  @Valid 
  @JsonProperty("creationDate")
  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public Exam tasks(List<@Valid Task> tasks) {
    this.tasks = tasks;
    return this;
  }

  public Exam addTasksItem(Task tasksItem) {
    if (this.tasks == null) {
      this.tasks = new ArrayList<>();
    }
    this.tasks.add(tasksItem);
    return this;
  }

  /**
   * Get tasks
   * @return tasks
  */
  @Valid 
  @JsonProperty("tasks")
  public List<@Valid Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<@Valid Task> tasks) {
    this.tasks = tasks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Exam exam = (Exam) o;
    return Objects.equals(this.id, exam.id) &&
        Objects.equals(this.examinerId, exam.examinerId) &&
        Objects.equals(this.name, exam.name) &&
        Objects.equals(this.description, exam.description) &&
        Objects.equals(this.programmingLanguage, exam.programmingLanguage) &&
        Objects.equals(this.maxDuration, exam.maxDuration) &&
        Objects.equals(this.ttl, exam.ttl) &&
        Objects.equals(this.creationDate, exam.creationDate) &&
        Objects.equals(this.tasks, exam.tasks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, examinerId, name, description, programmingLanguage, maxDuration, ttl, creationDate, tasks);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Exam {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    examinerId: ").append(toIndentedString(examinerId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    programmingLanguage: ").append(toIndentedString(programmingLanguage)).append("\n");
    sb.append("    maxDuration: ").append(toIndentedString(maxDuration)).append("\n");
    sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

