package com.sebrown2023.model.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sebrown2023.model.dto.Task;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Submission
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Submission {

  private Long id;

  private Task task;

  private String userSourceCode;

  public Submission id(Long id) {
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

  public Submission task(Task task) {
    this.task = task;
    return this;
  }

  /**
   * Get task
   * @return task
  */
  @Valid 
  @JsonProperty("task")
  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public Submission userSourceCode(String userSourceCode) {
    this.userSourceCode = userSourceCode;
    return this;
  }

  /**
   * Get userSourceCode
   * @return userSourceCode
  */
  
  @JsonProperty("userSourceCode")
  public String getUserSourceCode() {
    return userSourceCode;
  }

  public void setUserSourceCode(String userSourceCode) {
    this.userSourceCode = userSourceCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Submission submission = (Submission) o;
    return Objects.equals(this.id, submission.id) &&
        Objects.equals(this.task, submission.task) &&
        Objects.equals(this.userSourceCode, submission.userSourceCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, task, userSourceCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Submission {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    task: ").append(toIndentedString(task)).append("\n");
    sb.append("    userSourceCode: ").append(toIndentedString(userSourceCode)).append("\n");
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

