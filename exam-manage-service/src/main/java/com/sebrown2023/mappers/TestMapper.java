package com.sebrown2023.mappers;

import com.sebrown2023.dto.deprecated.PostTestDto;
import com.sebrown2023.dto.deprecated.TestDto;
import com.sebrown2023.model.db.Test;
import com.sebrown2023.model.dto.TestComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestDto testToTestDto (Test test);
    Test postTestDtoToTest(PostTestDto postTestDto);
    ////
    @Mapping(target = "expectedOutputData", source = "outputData")
    Test testComponentToTest(TestComponent testComponent);

    TestComponent testToTestComponent(Test test);

}
