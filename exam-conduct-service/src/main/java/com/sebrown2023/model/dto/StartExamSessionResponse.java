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
 * StartExamSessionResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class StartExamSessionResponse {

  private UUID id;

  private String status;

  private Exam exam;

  private Examine examine;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startTimestamp;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime finishTimestamp;

  public StartExamSessionResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public StartExamSessionResponse(UUID id, String status, Exam exam, Examine examine, OffsetDateTime startTimestamp, OffsetDateTime finishTimestamp) {
    this.id = id;
    this.status = status;
    this.exam = exam;
    this.examine = examine;
    this.startTimestamp = startTimestamp;
    this.finishTimestamp = finishTimestamp;
  }

  public StartExamSessionResponse id(UUID id) {
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

  public StartExamSessionResponse status(String status) {
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

  public StartExamSessionResponse exam(Exam exam) {
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

  public StartExamSessionResponse examine(Examine examine) {
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

  public StartExamSessionResponse startTimestamp(OffsetDateTime startTimestamp) {
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

  public StartExamSessionResponse finishTimestamp(OffsetDateTime finishTimestamp) {
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
    StartExamSessionResponse startExamSessionResponse = (StartExamSessionResponse) o;
    return Objects.equals(this.id, startExamSessionResponse.id) &&
        Objects.equals(this.status, startExamSessionResponse.status) &&
        Objects.equals(this.exam, startExamSessionResponse.exam) &&
        Objects.equals(this.examine, startExamSessionResponse.examine) &&
        Objects.equals(this.startTimestamp, startExamSessionResponse.startTimestamp) &&
        Objects.equals(this.finishTimestamp, startExamSessionResponse.finishTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, exam, examine, startTimestamp, finishTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StartExamSessionResponse {\n");
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

