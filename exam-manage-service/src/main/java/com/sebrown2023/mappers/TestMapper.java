package com.sebrown2023.mappers;

import com.sebrown2023.dto.PostTestDto;
import com.sebrown2023.dto.TestDto;
import com.sebrown2023.model.db.Test;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestDto testToTestDto (Test test);

    Test postTestDtoToTest(PostTestDto postTestDto);
}