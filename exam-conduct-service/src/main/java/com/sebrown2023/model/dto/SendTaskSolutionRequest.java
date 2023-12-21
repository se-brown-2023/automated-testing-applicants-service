package com.sebrown2023.model.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sebrown2023.model.dto.Submission;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SendTaskSolutionRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class SendTaskSolutionRequest {

  private Submission submission;

  public SendTaskSolutionRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SendTaskSolutionRequest(Submission submission) {
    this.submission = submission;
  }

  public SendTaskSolutionRequest submission(Submission submission) {
    this.submission = submission;
    return this;
  }

  /**
   * Get submission
   * @return submission
  */
  @NotNull @Valid 
  @JsonProperty("submission")
  public Submission getSubmission() {
    return submission;
  }

  public void setSubmission(Submission submission) {
    this.submission = submission;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SendTaskSolutionRequest sendTaskSolutionRequest = (SendTaskSolutionRequest) o;
    return Objects.equals(this.submission, sendTaskSolutionRequest.submission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(submission);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SendTaskSolutionRequest {\n");
    sb.append("    submission: ").append(toIndentedString(submission)).append("\n");
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

