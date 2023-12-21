package com.sebrown2023.model.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sebrown2023.model.dto.Exam;
import com.sebrown2023.model.dto.Examine;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateExamSessionResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class CreateExamSessionResponse {

  private UUID id;

  private String status;

  private Exam exam;

  private Examine examine;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startTimestamp;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime finishTimestamp;

  public CreateExamSessionResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateExamSessionResponse(UUID id, String status, Exam exam, Examine examine, OffsetDateTime startTimestamp, OffsetDateTime finishTimestamp) {
    this.id = id;
    this.status = status;
    this.exam = exam;
    this.examine = examine;
    this.startTimestamp = startTimestamp;
    this.finishTimestamp = finishTimestamp;
  }

  public CreateExamSessionResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull @Valid 
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CreateExamSessionResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @NotNull 
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CreateExamSessionResponse exam(Exam exam) {
    this.exam = exam;
    return this;
  }

  /**
   * Get exam
   * @return exam
  */
  @NotNull @Valid 
  @JsonProperty("exam")
  public Exam getExam() {
    return exam;
  }

  public void setExam(Exam exam) {
    this.exam = exam;
  }

  public CreateExamSessionResponse examine(Examine examine) {
    this.examine = examine;
    return this;
  }

  /**
   * Get examine
   * @return examine
  */
  @NotNull @Valid 
  @JsonProperty("examine")
  public Examine getExamine() {
    return examine;
  }

  public void setExamine(Examine examine) {
    this.examine = examine;
  }

  public CreateExamSessionResponse startTimestamp(OffsetDateTime startTimestamp) {
    this.startTimestamp = startTimestamp;
    return this;
  }

  /**
   * Get startTimestamp
   * @return startTimestamp
  */
  @NotNull @Valid 
  @JsonProperty("startTimestamp")
  public OffsetDateTime getStartTimestamp() {
    return startTimestamp;
  }

  public void setStartTimestamp(OffsetDateTime startTimestamp) {
    this.startTimestamp = startTimestamp;
  }

  public CreateExamSessionResponse finishTimestamp(OffsetDateTime finishTimestamp) {
    this.finishTimestamp = finishTimestamp;
    return this;
  }

  /**
   * Get finishTimestamp
   * @return finishTimestamp
  */
  @NotNull @Valid 
  @JsonProperty("finishTimestamp")
  public OffsetDateTime getFinishTimestamp() {
    return finishTimestamp;
  }

  public void setFinishTimestamp(OffsetDateTime finishTimestamp) {
    this.finishTimestamp = finishTimestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateExamSessionResponse createExamSessionResponse = (CreateExamSessionResponse) o;
    return Objects.equals(this.id, createExamSessionResponse.id) &&
        Objects.equals(this.status, createExamSessionResponse.status) &&
        Objects.equals(this.exam, createExamSessionResponse.exam) &&
        Objects.equals(this.examine, createExamSessionResponse.examine) &&
        Objects.equals(this.startTimestamp, createExamSessionResponse.startTimestamp) &&
        Objects.equals(this.finishTimestamp, createExamSessionResponse.finishTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, exam, examine, startTimestamp, finishTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateExamSessionResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    exam: ").append(toIndentedString(exam)).append("\n");
    sb.append("    examine: ").append(toIndentedString(examine)).append("\n");
    sb.append("    startTimestamp: ").append(toIndentedString(startTimestamp)).append("\n");
    sb.append("    finishTimestamp: ").append(toIndentedString(finishTimestamp)).append("\n");
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

